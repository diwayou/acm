package com.diwayou.code.test1024;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaopeng
 * @date 2020/10/12
 */
public class ResolveTopK {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(GenerateTopKData.filename), StandardCharsets.UTF_8);

        System.out.println(getTopK(lines, 4));
    }

    public static List<String> getTopK(List<String> lines, int k) {
        Map<String, Integer> counts = new HashMap<>();
        for (String line : lines) {
            counts.merge(line, 1, Integer::sum);
        }

        return counts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(k)
                .collect(Collectors.toList());
    }
}
