package com.scos;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        //ApplicationContextFactory applicationContextFactory = new ApplicationContextFactory();
        //System.out.println("Application Context: " + applicationContextFactory.getApplicationContext());

        /** Watch for NEW_ENTRY */
        //OPTION 1
        FileWatcherService fileWatcherService = applicationContext.getBean(FileWatcherService.class);
        fileWatcherService.run();

        //OPTION 2
//        ReadFileUtility readFile = new ReadFileUtility(applicationContext);
//        readFile.listFilesFromFolder();
    }
}