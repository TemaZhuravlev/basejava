package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir1 = new File("./src");
        fileRecurs(dir1);
    }
    public static void fileRecurs(File file){
        if(file.isDirectory()){
            System.out.println("directory: " + file.getName());
            File[] list = file.listFiles();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    fileRecurs(list[i]);
                }
            }
        }
        else{
            System.out.println("file: " + file.getName());
        }
    }
}
