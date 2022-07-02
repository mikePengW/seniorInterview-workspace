package com.chalon.nio1.replace;

import java.io.File;
import java.io.FilenameFilter;

public class WebpFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        File file = new File(dir, name);
        boolean isFile = file.isFile();
        boolean flag = name.endsWith("webp");
        if (isFile && flag) {
            return true;
        } else {
            return false;
        }
    }

}
