package com.scos;

import com.scos.data_model.mps_db.SysBaseHeader;
import com.scos.data_model.mps_db.SysSchedulingProva;
import com.scos.data_model.mps_db.SysSequenceHeader;
import com.scos.data_model.mps_db.SysTaskScheduled;
import com.scos.repositories.MissionPlanRepository;
import com.scos.services.FlightPlanService;
import com.scos.services.MissionPlanService;
import com.scos.services.SCOSService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, JAXBException, SAXException, DatatypeConfigurationException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        //ApplicationContextFactory applicationContextFactory = new ApplicationContextFactory();
        //System.out.println("Application Context: " + applicationContextFactory.getApplicationContext());

        /** Watch for NEW_ENTRY */
        //OPTION 1
//        FileWatcherService fileWatcherService = applicationContext.getBean(FileWatcherService.class);
//        fileWatcherService.run();

        //OPTION 2
//        ReadFileUtility readFile = new ReadFileUtility(applicationContext);
//        readFile.listFilesFromFolder();

        //JAXB XML to Java Class
//        FlightPlanCreation.xmlToObject();
        //JAXB Java Class to XML
//        FlightPlanCreation.objectToXML();
//        FlightPlanCreation.isValid();

        //FlightPlan from file
//        FlightPlanCreation flightPlanCreation = new FlightPlanCreation();
//        flightPlanCreation.createFlightPlanXML("src/main/resources/FlightPlan/MPLAN_2023_209_17.51.29.ssf", 23665, 7582, "AAR987");
        //FlightPlan from DB
//        FlightPlanService flightPlanService = applicationContext.getBean(FlightPlanService.class);
//        SysSchedulingProva sysSchedulingProvaFP = flightPlanService.searchSchedulingProva(BigInteger.ONE);
//        FlightPlanCreation flightPlanCreation = new FlightPlanCreation(applicationContext);
//        flightPlanCreation.createFlightPlanXMLFromDB(sysSchedulingProvaFP, 384764);


        //MissionPlan
        MissionPlanService missionPlanService = applicationContext.getBean(MissionPlanService.class);
//        MissionPlanRepository missionPlanRepository = applicationContext.getBean(MissionPlanRepository.class);
//        SysSchedulingProva sysSchedulingProva = missionPlanService.createSysSchedulingProva();
//        System.out.println("Scheduling_ID " + sysSchedulingProva.getSchedulingId());
        SysSchedulingProva sysSchedulingProva = missionPlanService.searchSchedulingProva(BigInteger.ONE);

        MissionPlanCreation missionPlanCreation = new MissionPlanCreation(applicationContext);
//        missionPlanCreation.readMissionPlan("src/main/resources/FlightPlan/MissionPlan.ssf");
//
        missionPlanCreation.createMissionPlanSSF(sysSchedulingProva);

    }
}