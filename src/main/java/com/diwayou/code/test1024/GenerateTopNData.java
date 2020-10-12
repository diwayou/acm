package com.diwayou.code.test1024;

import com.beust.jcommander.internal.Lists;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author gaopeng
 * @date 2020/10/12
 */
public class GenerateTopNData {

    public static final String filename = "topn.txt";

    public static void main(String[] args) throws IOException {
        String[] seeds = new String[]{
                "a",
                "bc",
                "cd",
                "def"
        };
        String[] confuse = new String[]{
                "xfds",
                "yg",
                "zf"
        };

        int times = 10;
        int step = 1;
        List<String> seedAll = Lists.newArrayList(seeds);
        seedAll.addAll(Arrays.asList(confuse));

        List<String> data = Lists.newArrayList();
        for (String seed : seedAll) {
            for (int i = 0; i < times; i++) {
                data.add(seed);
            }

            times -= step;
        }

        Collections.shuffle(data);

        Files.write(Path.of(filename), data, StandardCharsets.UTF_8);

        System.out.println(ResolveTopN.getTopN(data, seeds.length));
    }
}
