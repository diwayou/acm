package com.diwayou.game.go.ui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class GoAudioFilter extends FileFilter {
    // Accept all directories and all go files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = GoFilterUtils.getExtension(f);
        if (extension != null) {
            for (int i = 0; i < GoFilterUtils.audio.length; i++) {
                if (extension.equals(GoFilterUtils.audio[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    // The description of this filter
    public String getDescription() {
        return "Music or Sound Files";
    }
}