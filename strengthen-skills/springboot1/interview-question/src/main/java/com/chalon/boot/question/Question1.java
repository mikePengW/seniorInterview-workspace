package com.chalon.boot.question;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.peng wrote on 2024-03-05
 * @version 1.0
 */
public class Question1 {

    @PostConstruct
    public void printFileNames() {
        String rootPath = "/data/";
        List<String> allFileNames = new ArrayList<>();
        getAllFileNames(rootPath, allFileNames);
        allFileNames.forEach(System.out::println);
    }

    public void getAllFileNames(String rootPath, List<String> allFileNames) {
        File folder = new File(rootPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                allFileNames.add(file.getName());
                if (file.isDirectory()) {
                    getAllFileNames(file.getPath(), allFileNames);
                    break;
                }
            }
        }
    }



}
