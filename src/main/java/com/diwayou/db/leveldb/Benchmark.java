/*
 * Copyright (C) 2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diwayou.db.leveldb;

import com.alipay.sofa.jraft.rhea.storage.KVEntry;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Benchmark {
    private final boolean useExisting;
    private final Integer writeBufferSize;
    private final File databaseDir;
    private final double compressionRatio;
    private long startTime;

    enum Order {
        SEQUENTIAL,
        RANDOM
    }

    enum DBState {
        FRESH,
        EXISTING
    }

    //    Cache cache_;
    private final List<String> benchmarks;
    private final int num;
    private int reads;
    private final int valueSize;
    private int heapCounter;
    private double lastOpFinish;
    private long bytes;
    private String message;
    private String postMessage;

    private final Random random;

    // State kept for progress messages
    private int done;
    private int nextReport;     // When to report next

    final Client client;
    
    private boolean snappyAvailable = false;

    public Benchmark(Map<Flag, Object> flags)
            throws Exception {
        benchmarks = (List<String>) flags.get(Flag.benchmarks);
        num = (Integer) flags.get(Flag.num);
        reads = (Integer) (flags.get(Flag.reads) == null ? flags.get(Flag.num) : flags.get(Flag.reads));
        valueSize = (Integer) flags.get(Flag.value_size);
        writeBufferSize = (Integer) flags.get(Flag.write_buffer_size);
        compressionRatio = (Double) flags.get(Flag.compression_ratio);
        useExisting = (Boolean) flags.get(Flag.use_existing_db);
        heapCounter = 0;
        bytes = 0;
        random = new Random(301);

        databaseDir = new File((String) flags.get(Flag.db));
        
        client = new Client();
        client.init();
    }

    private void run()
            throws IOException {
        printHeader();
        open();

        for (String benchmark : benchmarks) {
            start();

            boolean known = true;

            if (benchmark.equals("fillseq")) {
                write(Order.SEQUENTIAL, DBState.FRESH, num, valueSize, 1);
            } else if (benchmark.equals("fillbatch")) {
                write(Order.SEQUENTIAL, DBState.FRESH, num, valueSize, 1000);
            } else if (benchmark.equals("fillrandom")) {
                write(Order.RANDOM, DBState.FRESH, num, valueSize, 1);
            } else if (benchmark.equals("overwrite")) {
                write(Order.RANDOM, DBState.EXISTING, num, valueSize, 1);
            } else if (benchmark.equals("fill100K")) {
                write(Order.RANDOM, DBState.FRESH, num / 1000, 100 * 1000, 1);
            } else if (benchmark.equals("readreverse")) {
                readReverse();
            } else if (benchmark.equals("readrandom")) {
                readRandom();
            } else if (benchmark.equals("readhot")) {
                readHot();
            } else if (benchmark.equals("readrandomsmall")) {
                int n = reads;
                reads /= 1000;
                readRandom();
                reads = n;
            } else if (benchmark.equals("acquireload")) {
                acquireLoad();
            } else if (benchmark.equals("heapprofile")) {
                heapProfile();
            } else if (benchmark.equals("stats")) {
                printStats();
            } else {
                known = false;
                System.err.println("Unknown benchmark: " + benchmark);
            }
            if (known) {
                stop(benchmark);
            }
        }
    }

    private void printHeader()
            throws IOException {
        int kKeySize = 16;
        printEnvironment();
        System.out.printf("Keys:       %d bytes each\n", kKeySize);
        System.out.printf("Values:     %d bytes each (%d bytes after compression)\n",
                valueSize,
                (int) (valueSize * compressionRatio + 0.5));
        System.out.printf("Entries:    %d\n", num);
        System.out.printf("RawSize:    %.1f MB (estimated)\n",
                ((kKeySize + valueSize) * num) / 1048576.0);
        System.out.printf("FileSize:   %.1f MB (estimated)\n",
                (((kKeySize + valueSize * compressionRatio) * num)
                        / 1048576.0));
        printWarnings();
        System.out.printf("------------------------------------------------\n");
    }

    static void printWarnings() {
        boolean assertsEnabled = true;
        assert assertsEnabled; // Intentional side effect!!!
        if (assertsEnabled) {
            System.out.printf("WARNING: Assertions are enabled; benchmarks unnecessarily slow\n");
        }

        // See if snappy is working by attempting to compress a compressible string
        String text = "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy";
        byte[] compressedText = null;
        try {
            compressedText = text.getBytes(UTF_8);
        } catch (Exception ignored) {
        }
        if (compressedText == null) {
            System.out.printf("WARNING: Snappy compression is not enabled\n");
        } else if (compressedText.length > text.length()) {
            System.out.printf("WARNING: Snappy compression is not effective\n");
        }
    }

    void printEnvironment()
            throws IOException {
        System.out.printf("Date:       %tc\n", new Date());

        File cpuInfo = new File("/proc/cpuinfo");
        if (cpuInfo.canRead()) {
            int numberOfCpus = 0;
            String cpuType = null;
            String cacheSize = null;
            for (String line : CharStreams.readLines(Files.newReader(cpuInfo, UTF_8))) {
                ImmutableList<String> parts = ImmutableList.copyOf(Splitter.on(':').omitEmptyStrings().trimResults().limit(2).split(line));
                if (parts.size() != 2) {
                    continue;
                }
                String key = parts.get(0);
                String value = parts.get(1);

                if (key.equals("model name")) {
                    numberOfCpus++;
                    cpuType = value;
                } else if (key.equals("cache size")) {
                    cacheSize = value;
                }
            }
            System.out.printf("CPU:        %d * %s\n", numberOfCpus, cpuType);
            System.out.printf("CPUCache:   %s\n", cacheSize);
        }
    }

    private void open()
            throws IOException {

    }

    private void start() {
        startTime = System.nanoTime();
        bytes = 0;
        message = null;
        lastOpFinish = startTime;
        // hist.clear();
        done = 0;
        nextReport = 100;
    }

    private void stop(String benchmark) {
        long endTime = System.nanoTime();
        double elapsedSeconds = 1.0d * (endTime - startTime) / TimeUnit.SECONDS.toNanos(1);

        // Pretend at least one op was done in case we are running a benchmark
        // that does nto call FinishedSingleOp().
        if (done < 1) {
            done = 1;
        }

        if (bytes > 0) {
            String rate = String.format("%6.1f MB/s", (bytes / 1048576.0) / elapsedSeconds);
            if (message != null) {
                message = rate + " " + message;
            } else {
                message = rate;
            }
        } else if (message == null) {
            message = "";
        }

        System.out.printf("%-12s : %11.5f micros/op;%s%s\n",
                benchmark,
                elapsedSeconds * 1.0e6 / done,
                (message == null ? "" : " "),
                message);
//        if (FLAGS_histogram) {
//            System.out.printf("Microseconds per op:\n%s\n", hist_.ToString().c_str());
//        }

        if (postMessage != null) {
            System.out.printf("\n%s\n", postMessage);
            postMessage = null;
        }

    }

    private void write(Order order, DBState state, int numEntries, int valueSize, int entriesPerBatch)
            throws IOException {
        if (state == DBState.FRESH) {
            if (useExisting) {
                message = "skipping (--use_existing_db is true)";
                return;
            }
            destroyDb();
            open();
            start(); // Do not count time taken to destroy/open
        }

        if (numEntries != num) {
            message = String.format("(%d ops)", numEntries);
        }

        for (int i = 0; i < numEntries; i += entriesPerBatch) {
            List<KVEntry> kvs = Lists.newArrayListWithCapacity(entriesPerBatch);
            for (int j = 0; j < entriesPerBatch; j++) {
                int k = (order == Order.SEQUENTIAL) ? i + j : random.nextInt(num);
                byte[] key = formatNumber(k);
                kvs.add(new KVEntry(key, generate(valueSize)));
                bytes += valueSize + key.length;
                finishedSingleOp();
            }
            client.getRheaKVStore().bPut(kvs);
        }
    }

    private static byte[] generate(int length) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final byte[] valeBytes = new byte[length];
        random.nextBytes(valeBytes);

        return valeBytes;
    }

    public static byte[] formatNumber(long n) {
        checkArgument(n >= 0, "number must be positive");

        byte[] slice = new byte[16];

        int i = 15;
        while (n > 0) {
            slice[i--] = (byte) ((long) '0' + (n % 10));
            n /= 10;
        }
        while (i >= 0) {
            slice[i--] = '0';
        }
        return slice;
    }

    private void finishedSingleOp() {
//        if (histogram) {
//            todo
//        }
        done++;
        if (done >= nextReport) {
            if (nextReport < 1000) {
                nextReport += 100;
            } else if (nextReport < 5000) {
                nextReport += 500;
            } else if (nextReport < 10000) {
                nextReport += 1000;
            } else if (nextReport < 50000) {
                nextReport += 5000;
            } else if (nextReport < 100000) {
                nextReport += 10000;
            } else if (nextReport < 500000) {
                nextReport += 50000;
            } else {
                nextReport += 100000;
            }
            System.out.printf("... finished %d ops%30s\r", done, "");

        }
    }

    private void readReverse() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void readRandom() {
        for (int i = 0; i < reads; i++) {
            byte[] key = formatNumber(random.nextInt(num));
            byte[] value = client.getRheaKVStore().bGet(key);
            if (value == null) {
                throw new NullPointerException(String.format("db.get(%s) is null", new String(key, UTF_8)));
            }
            bytes += key.length + value.length;
            finishedSingleOp();
        }
    }

    private void readHot() {
        int range = (num + 99) / 100;
        for (int i = 0; i < reads; i++) {
            byte[] key = formatNumber(random.nextInt(range));
            byte[] value = client.getRheaKVStore().bGet(key);
            bytes += key.length + value.length;
            finishedSingleOp();
        }
    }

    private void acquireLoad() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void heapProfile() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void destroyDb() {

    }

    private void printStats() {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static void main(String[] args)
            throws Exception {
        Map<Flag, Object> flags = new EnumMap<>(Flag.class);
        for (Flag flag : Flag.values()) {
            flags.put(flag, flag.getDefaultValue());
        }
        for (String arg : args) {
            boolean valid = false;
            if (arg.startsWith("--")) {
                try {
                    ImmutableList<String> parts = ImmutableList.copyOf(Splitter.on("=").limit(2).split(arg.substring(2)));
                    Flag key = Flag.valueOf(parts.get(0));
                    Object value = key.parseValue(parts.get(1));
                    flags.put(key, value);
                    valid = true;
                } catch (Exception e) {
                }
            }

            if (!valid) {
                System.err.println("Invalid argument " + arg);
                System.exit(1);
            }

        }
        new Benchmark(flags).run();
    }

    private enum Flag {
        // Comma-separated list of operations to run in the specified order
        //   Actual benchmarks:
        //      fillseq       -- write N values in sequential key order in async mode
        //      fillrandom    -- write N values in random key order in async mode
        //      overwrite     -- overwrite N values in random key order in async mode
        //      fillsync      -- write N/100 values in random key order in sync mode
        //      fill100K      -- write N/1000 100K values in random order in async mode
        //      readseq       -- read N times sequentially
        //      readreverse   -- read N times in reverse order
        //      readrandom    -- read N times in random order
        //      readhot       -- read N times in random order from 1% section of DB
        //      crc32c        -- repeated crc32c of 4K of data
        //      acquireload   -- load N*1000 times
        //   Meta operations:
        //      compact     -- Compact the entire DB
        //      stats       -- Print DB stats
        //      heapprofile -- Dump a heap profile (if supported by this port)
        benchmarks(ImmutableList.of(
                "fillseq",
                "fillseq",
                "fillseq",
                "fillsync",
                "fillrandom",
                "overwrite",
                "fillseq",
                "readrandom",
                "readrandom",  // Extra run to allow previous compactions to quiesce
                "readseq",
                // "readreverse",
                "compact",
                "readrandom",
                "readseq",
                // "readreverse",
                "fill100K",
                // "crc32c",
                "snappycomp",
                "unsnap-array",
                "unsnap-direct"
                // "acquireload"
        )) {
            @Override
            public Object parseValue(String value) {
                return ImmutableList.copyOf(Splitter.on(",").trimResults().omitEmptyStrings().split(value));
            }
        },

        // Arrange to generate values that shrink to this fraction of
        // their original size after compression
        compression_ratio(0.5d) {
            @Override
            public Object parseValue(String value) {
                return Double.parseDouble(value);
            }
        },

        // Print histogram of operation timings
        histogram(false) {
            @Override
            public Object parseValue(String value) {
                return Boolean.parseBoolean(value);
            }
        },

        // If true, do not destroy the existing database.  If you set this
        // flag and also specify a benchmark that wants a fresh database, that
        // benchmark will fail.
        use_existing_db(false) {
            @Override
            public Object parseValue(String value) {
                return Boolean.parseBoolean(value);
            }
        },

        // Number of key/values to place in database
        num(1000000) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Number of read operations to do.  If negative, do FLAGS_num reads.
        reads(null) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Size of each value
        value_size(100) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Number of bytes to buffer in memtable before compacting
        // (initialized to default value by "main")
        write_buffer_size(null) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Number of bytes to use as a cache of uncompressed data.
        // Negative means use default settings.
        cache_size(-1) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Maximum number of files to keep open at the same time (use default if == 0)
        open_files(0) {
            @Override
            public Object parseValue(String value) {
                return Integer.parseInt(value);
            }
        },

        // Use the db with the following name.
        db("/tmp/dbbench") {
            @Override
            public Object parseValue(String value) {
                return value;
            }
        };

        private final Object defaultValue;

        Flag(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        protected abstract Object parseValue(String value);

        public Object getDefaultValue() {
            return defaultValue;
        }
    }

}
