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
public class GenerateTopKData {

    public static final String filename = "topk.txt";

    public static void main(String[] args) throws IOException {
        String[] seeds = new String[]{
                "是个谐音梗",
                "外号是二哥",
                "演唱过《你是我心中的日月》"
        };
        String[] confuse = new String[]{
                "你今天过于帅气，但是你的答案是错的。稳住，别浪。",
                "如果我送你个女朋友，你敢要要吗？",
                "人终究是个孤独的动物，但是明天请你阳光灿烂",
                "我知道你累了，不爱听那些心灵鸡汤，但是我可以给你个拥抱吗？",
                "怪你过分美丽，我只是一个没有感情的聊天机器",
                "你正在进行的工作，可以简单的理解为一种计算机和人都能识别的语言",
                "谢谢惠顾，请您下次光临",
                "是时候表演真正的技术了"
        };

        int times = 1000;
        int step = 1;
        List<String> confuseAll = Lists.newArrayList(confuse);

        List<String> data = Lists.newArrayList();
        for (String seed : confuseAll) {
            for (int i = 0; i < times; i++) {
                data.add(seed);
            }

            times -= step;
        }

        data.addAll(Arrays.asList(seeds));

        Collections.shuffle(data);

        Files.write(Path.of(filename), data, StandardCharsets.UTF_8);

        System.out.println(ResolveTopK.getTopK(data, seeds.length));
    }
}
