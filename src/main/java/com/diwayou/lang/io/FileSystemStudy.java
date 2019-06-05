package com.diwayou.lang.io;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStudy {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("D:\\opensource\\acm\\target\\acm-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        FileSystem fs = FileSystems.newFileSystem(file, null);
        Path path = fs.getPath("javafx.properties");

        System.out.println(Files.readAllLines(path));
    }
}
