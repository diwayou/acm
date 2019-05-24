package com.diwayou.swing;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenDirectoryStudy {
    public static void main(String[] args) throws IOException {
        Desktop.getDesktop().open(new File("D:\\aws.jpg"));
    }
}
