package com.chalon.nio1.replace;

import java.io.File;

public class ReplaceWebp {

    public static void main(String[] args) {
        //文件夹路径
//        C:\Users\pengw\Downloads\img
        String dirPath = "C:" + File.separator + "Users" + File.separator + "pengw" + File.separator + "Downloads" + File.separator + "img";
        ReplaceWebpToPng(dirPath);
    }

    public static void ReplaceWebpToPng(String dirPath) {
        File dir = new File(dirPath);
        //过滤器，找出所有以webp结尾的文件
        File[] files = dir.listFiles(new WebpFilenameFilter());
        if (files != null) {
            for (File f : files) {
                //System.out.println("old:"+f.getName());
                //获取文件名
                String filename = f.getName().substring(0, f.getName().length() - 5);
                //System.out.println(filename);
                String newFilename = filename + ".jpg";
                RenameFile(dirPath, f.getName(), newFilename);
            }
        }
    }

    public static void RenameFile(String path, String filename, String newFilename) {
        String oldFilename = path + File.separator + filename;
        File oldFile = new File(oldFilename);
        String newName = path + File.separator + newFilename;
        File newFile = new File(newName);
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.renameTo(newFile);
        }
    }

}
