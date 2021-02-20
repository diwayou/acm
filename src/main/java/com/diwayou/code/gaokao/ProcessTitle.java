package com.diwayou.code.gaokao;

import com.diwayou.util.Json;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaopeng
 * @date 2020/8/6
 */
public class ProcessTitle {

    public static void main(String[] args) throws IOException {
        String content = Files.readString(Path.of("titles.json"));

        List<Title> titles = Json.nonNull().fromJsonToList(content, Title.class);

        List<String> lines = titles.stream()
                .filter(t -> StringUtils.isNotBlank(t.getTitle()))
                .map(t -> "http://www.dlymgz.cn/newsdisplay.do?id=" + t.getId() + "," + t.getTitle())
                .collect(Collectors.toList());

        Files.write(Path.of("titles.csv"), lines);
    }
}
