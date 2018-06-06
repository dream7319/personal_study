package com.lierl.outer.tomcat;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/6 7:16
 */
public class Test {
    public static void main(String[] args) {
        File file = new File("F:\\");
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".zip");
            }
        });

        for (File file1 : files) {
            System.out.println(file1.getName());
        }
    }
}
