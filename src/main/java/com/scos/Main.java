package com.scos;

import com.scos.services.SCOSService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, JAXBException, SAXException {

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

        //JAXB XML to Java Class
//        FlightPlanCreation.xmlToObject();
        //JAXB Java Class to XML
//        FlightPlanCreation.objectToXML();
//        FlightPlanCreation.isValid();
    }
}