package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        File dir1 = new File("src");
        fileRecurs(dir1, "");
    }

    public static void fileRecurs(File file, String underscore) {
        if (file.isDirectory()) {
            System.out.println(underscore + file.getName());
            File[] list = file.listFiles();
            if (list != null) {
                for (File value : list) {
                    fileRecurs(value, underscore + "  ");
                }
            }
        } else {
            System.out.println(underscore + file.getName());
        }
    }
}
