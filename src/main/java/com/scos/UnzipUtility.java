package com.scos;

import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtility {

    private final int BUFFER_SIZE = 4096;

    public String unZip (String source, String target) throws IOException {

        byte[] buffer = new byte[BUFFER_SIZE];
        File destDir = new File(target);

        //target directory DOES NOT EXIST must be created
        if(!destDir.exists()) {
            destDir.mkdir();
        }

        //UNZIP
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(source));
        //any file/folder INSIDE zip
        ZipEntry zipEntry = zipInput.getNextEntry();

        while(zipEntry != null) {
            System.out.println("Zip Entry: " + zipEntry);
            String filePath = target + File.separator + zipEntry.getName();
            System.out.println("File Path: " + filePath);
            //If it is a file copy the content
            if(!zipEntry.isDirectory()) {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                int len;
                while((len = zipInput.read(buffer)) > 0) {
                    bos.write(buffer, 0, len );
                }
                System.out.println("Contenido escrito");
                bos.close();
            } else {
                //If it is a directory creates one inside target folder
                File dir = new File(filePath);
                dir.mkdir();
                System.out.println("Nueva carpeta " + dir);
            }
           zipEntry = zipInput.getNextEntry();
        }
        zipInput.close();

        return target;
    }

    public boolean deleteUnZipFolder(File directoryPath) {
        File[] targetDirectoryFiles = directoryPath.listFiles();
        System.out.println("Directory: " + directoryPath.getName());
        boolean folderIsDeleted = false;
        for(File file : targetDirectoryFiles) {
            System.out.println("File: " + file.getName());
            //System.out.println("Directory Target Folder: " + file.getPath());
            folderIsDeleted = FileSystemUtils.deleteRecursively(file);
            //Files.delete(file.toPath()); //ERROR DIRECTORY NOT EMPTY EXCEPTION -> synthetic folder
            //FileUtils.deleteDirectory(file.getAbsoluteFile());  //NEW DEPENDENCY
        }
        //System.out.println("folderIsDeleted: " + folderIsDeleted);
        return folderIsDeleted;
    }
}