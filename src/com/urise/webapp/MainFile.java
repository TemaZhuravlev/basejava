package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static String underscore = "";

    public static void main(String[] args) {
        File dir1 = new File("./src/com/urise/webapp");
        fileRecurs(dir1);
    }

    public static void fileRecurs(File file) {
        if (file.isDirectory()) {
            System.out.println(underscore + file.getName());
            underscore = " ";
            File[] list = file.listFiles();
            if (list != null) {
                for (File value : list) {
                    fileRecurs(value);
                }
            }
        } else {
            System.out.println(" " + underscore + file.getName());
        }
    }
}
