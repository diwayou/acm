/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package com.diwayou.db.lucene.ik.dic;

import com.diwayou.db.lucene.ik.cfg.Configuration;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.support.FilenameUtil;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 词典管理类,单子模式
 */
public class Dictionary {

    /*
     * 词典单子实例
     */
    private static Dictionary singleton;

    private DictSegment _MainDict;

    private DictSegment _QuantifierDict;

    private DictSegment _StopWords;

    /**
     * 配置对象
     */
    private Configuration configuration;

    private static final Logger log = AppLog.getBrowser();

    private static final String PATH_DIC_MAIN = "main.dic";
    private static final String PATH_DIC_SURNAME = "surname.dic";
    private static final String PATH_DIC_QUANTIFIER = "quantifier.dic";
    private static final String PATH_DIC_SUFFIX = "suffix.dic";
    private static final String PATH_DIC_PREP = "preposition.dic";
    private static final String PATH_DIC_STOP = "stopword.dic";

    private final static String FILE_NAME = "IKAnalyzer.cfg.xml";
    private final static String EXT_DICT = "ext_dict";
    private final static String REMOTE_EXT_DICT = "remote_ext_dict";
    private final static String EXT_STOP = "ext_stopwords";
    private final static String REMOTE_EXT_STOP = "remote_ext_stopwords";

    private String confDir;
    private Properties props;

    private Dictionary(Configuration cfg) {
        this.configuration = cfg;
        this.props = new Properties();
        this.confDir = "config";

        String configFile = FilenameUtil.path(confDir, FILE_NAME);

        log.log(Level.INFO, String.format("try load config from %s", configFile));
        try (InputStream input = getInputStream(configFile)) {
            if (input != null) {
                try {
                    props.loadFromXML(input);
                } catch (IOException e) {
                    log.log(Level.WARNING, "ik-analyzer", e);
                }
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }

    }

    private InputStream getInputStream(String path) throws FileNotFoundException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("没有找到配置文件path=" + path);
        }

        return inputStream;
    }

    private String getProperty(String key) {
        if (props != null) {
            return props.getProperty(key);
        }
        return null;
    }

    /**
     * 词典初始化 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
     * 只有当Dictionary类被实际调用时，才会开始载入词典， 这将延长首次分词操作的时间 该方法提供了一个在应用加载阶段就初始化字典的手段
     *
     * @return Dictionary
     */
    public static synchronized void initial(Configuration cfg) {
        if (singleton == null) {
            synchronized (Dictionary.class) {
                if (singleton == null) {

                    singleton = new Dictionary(cfg);
                    singleton.loadMainDict();
                    singleton.loadSurnameDict();
                    singleton.loadQuantifierDict();
                    singleton.loadSuffixDict();
                    singleton.loadPrepDict();
                    singleton.loadStopWordDict();
                }
            }
        }
    }

    private void loadDictFile(DictSegment dict, String file, boolean critical, String name) {
        try (InputStream is = getInputStream(file)) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"), 512);
            String word = br.readLine();
            if (word != null) {
                if (word.startsWith("\uFEFF"))
                    word = word.substring(1);
                for (; word != null; word = br.readLine()) {
                    word = word.trim();
                    if (word.isEmpty()) continue;
                    dict.fillSegment(word.toCharArray());
                }
            }
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "ik-analyzer: " + name + " not found", e);
            if (critical) throw new RuntimeException("ik-analyzer: " + name + " not found!!!", e);
        } catch (IOException e) {
            log.log(Level.WARNING, "ik-analyzer: " + name + " loading failed", e);
        }
    }

    private String getDictRoot() {
        return confDir;
    }


    /**
     * 获取词典单子实例
     *
     * @return Dictionary 单例对象
     */
    public static Dictionary getSingleton() {
        if (singleton == null) {
            throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
        }
        return singleton;
    }


    /**
     * 批量加载新词条
     *
     * @param words Collection<String>词条列表
     */
    public void addWords(Collection<String> words) {
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    // 批量加载词条到主内存词典中
                    singleton._MainDict.fillSegment(word.trim().toCharArray());
                }
            }
        }
    }

    /**
     * 批量移除（屏蔽）词条
     */
    public void disableWords(Collection<String> words) {
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    // 批量屏蔽词条
                    singleton._MainDict.disableSegment(word.trim().toCharArray());
                }
            }
        }
    }

    /**
     * 检索匹配主词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray) {
        return singleton._MainDict.match(charArray);
    }

    /**
     * 检索匹配主词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray, int begin, int length) {
        return singleton._MainDict.match(charArray, begin, length);
    }

    /**
     * 检索匹配量词词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInQuantifierDict(char[] charArray, int begin, int length) {
        return singleton._QuantifierDict.match(charArray, begin, length);
    }

    /**
     * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
     *
     * @return Hit
     */
    public Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
        DictSegment ds = matchedHit.getMatchedDictSegment();
        return ds.match(charArray, currentIndex, 1, matchedHit);
    }

    /**
     * 判断是否是停止词
     *
     * @return boolean
     */
    public boolean isStopWord(char[] charArray, int begin, int length) {
        return singleton._StopWords.match(charArray, begin, length).isMatch();
    }

    /**
     * 加载主词典及扩展词典
     */
    private void loadMainDict() {
        // 建立一个主词典实例
        _MainDict = new DictSegment((char) 0);

        // 读取主词典文件
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_MAIN);
        loadDictFile(_MainDict, file, false, "Main Dict");
        // 加载扩展词典
        this.loadExtDict();
    }

    /**
     * 加载用户配置的扩展词典到主词库表
     */
    private void loadExtDict() {
        // 加载扩展词典配置
        List<String> extDictFiles = getExtDictionarys();
        if (extDictFiles != null) {
            for (String extDictName : extDictFiles) {
                // 读取扩展词典文件
                log.info("[Dict Loading] " + extDictName);
                String file = FilenameUtil.path(extDictName);
                loadDictFile(_MainDict, file, false, "Extra Dict");
            }
        }
    }

    private List<String> getExtDictionarys() {
        return null;
    }

    /**
     * 加载用户扩展的停止词词典
     */
    private void loadStopWordDict() {
        // 建立主词典实例
        _StopWords = new DictSegment((char) 0);

        // 读取主词典文件
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_STOP);
        loadDictFile(_StopWords, file, false, "Main Stopwords");
    }

    /**
     * 加载量词词典
     */
    private void loadQuantifierDict() {
        // 建立一个量词典实例
        _QuantifierDict = new DictSegment((char) 0);
        // 读取量词词典文件
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_QUANTIFIER);
        loadDictFile(_QuantifierDict, file, false, "Quantifier");
    }

    private void loadSurnameDict() {
        DictSegment _SurnameDict = new DictSegment((char) 0);
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_SURNAME);
        loadDictFile(_SurnameDict, file, true, "Surname");
    }

    private void loadSuffixDict() {
        DictSegment _SuffixDict = new DictSegment((char) 0);
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_SUFFIX);
        loadDictFile(_SuffixDict, file, true, "Suffix");
    }

    private void loadPrepDict() {
        DictSegment _PrepDict = new DictSegment((char) 0);
        String file = FilenameUtil.path(getDictRoot(), Dictionary.PATH_DIC_PREP);
        loadDictFile(_PrepDict, file, true, "Preposition");
    }

    void reLoadMainDict() {
        log.info("重新加载词典...");
        // 新开一个实例加载词典，减少加载过程对当前词典使用的影响
        Dictionary tmpDict = new Dictionary(configuration);
        tmpDict.configuration = getSingleton().configuration;
        tmpDict.loadMainDict();
        tmpDict.loadStopWordDict();
        _MainDict = tmpDict._MainDict;
        _StopWords = tmpDict._StopWords;
        log.info("重新加载词典完毕...");
    }

}
