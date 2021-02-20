package com.diwayou.code.gaokao;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng
 * @date 2020/8/21
 */
public class SortGaoKao {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Path.of(ClassLoader.getSystemResource("gaokao/like.txt").toURI()));

        List<String> result = lines.stream()
                .map(l -> l.split(","))
                .sorted(Comparator.comparingDouble(a -> Double.parseDouble(a[2])))
                .map(a -> StringUtils.join(a, ","))
                .collect(Collectors.toList());

        Collections.reverse(result);

        Files.write(Path.of("2020理科排行.csv"), result);
    }
}
