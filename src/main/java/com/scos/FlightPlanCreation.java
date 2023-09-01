package com.scos;

import com.scos.XSDToJava3.*;
import com.scos.data_model.mps_db.*;
import com.scos.services.FlightPlanService;
import org.springframework.context.ApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javax.xml.transform.*;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightPlanCreation {

    ApplicationContext applicationContext;

    //inject application context to be able to call the bean required
    public FlightPlanCreation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("Application Context from Flight Plan: " + applicationContext);
    }

    /** In file MISSING TASKS */
    public void createFlightPlanXMLFromFile (String flightPlanFile, long missionId, long schedulingId, String taskId) throws IOException, DatatypeConfigurationException, JAXBException {
        FlightPlanService flightPlanService = this.applicationContext.getBean(FlightPlanService.class);
        BufferedReader reader = new BufferedReader(new FileReader(flightPlanFile));

        FlightPlan flightPlan = new FlightPlan();
        //HEADER
        Header header = new Header();
        header.setMissionId(BigInteger.valueOf(missionId)); //INPUT
        LocalDateTime localDateTimeStartTime = LocalDateTime.now();
        XMLGregorianCalendar xmlGregCalStartTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDateTimeStartTime.toString());
        header.setStartTime(xmlGregCalStartTime);
        header.setSchedulingId(BigInteger.valueOf(schedulingId)); //INPUT

        //TASKS
        Tasks tasks = new Tasks();
        //TASK
        Task task = new Task();
        task.setTaskId(taskId); //INPUT

        //Start file
        String row = reader.readLine();
        int rowNo = 1; //baseheader
        while(row != null) {
            String[] rowSplit = row.split("\\|");

            if(rowNo != 1) {

                if(rowSplit[0] == "S") {
                    Sequence sequence = new Sequence();
                    SequenceHeader sequenceHeaderXML = flightPlanService.createSequenceHeader(rowSplit);

                    sequence.setSequenceHeader(sequenceHeaderXML);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean seqParam = checkSequencePARS(rowSplit[2]);
                    Integer seqParam = Integer.valueOf(rowSplit[2]);

                   if(seqParam > 0) {
                       for(int i=0; i<seqParam; i++ ) {
                           row = reader.readLine();
                           rowNo++;
                           String[] rowSplitParam = row.split("\\|");
                           SequenceParameter sequenceParameterXML = flightPlanService.createSequenceParameter(rowSplitParam);
                           sequence.getSequenceParameter().add(sequenceParameterXML);
                       }
                   }
                   task.getElements().add(sequence);

                } else {
                    Command command = new Command();
                    CommandHeader commandHeaderXML = flightPlanService.createCommandHeader(rowSplit);

                    command.setCommandHeader(commandHeaderXML);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean commParam = checkCommandPARS(rowSplit[13]);
                    Integer commParam = Integer.valueOf(rowSplit[13]);

                    if(commParam > 0) {
                        for(int i=0; i<commParam; i++) {
                            row = reader.readLine();
                            rowNo++;
                            String[] rowSplitParam = row.split("\\|");
                            CommandParameter commandParameterXML = flightPlanService.createCommandParameter(rowSplitParam);
                            command.getCommandParameter().add(commandParameterXML);
                        }
                    }
                    task.getElements().add(command);
                }

            } else {
               BaseHeader baseHeaderXML = flightPlanService.createBaseHeader(rowSplit);
               tasks.setBaseHeader(baseHeaderXML);
            }

            rowNo++;
            row = reader.readLine();
        }

        reader.close();

        LocalDateTime localDateTimeEndTime = LocalDateTime.now();
        XMLGregorianCalendar xmlGregCalEndTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDateTimeEndTime.toString());
        header.setEndTime(xmlGregCalEndTime);

        tasks.getTask().add(task);
        /** No Task */
        tasks.setNoTask(BigInteger.valueOf(tasks.getTask().size()));

        flightPlan.setHeader(header);
        flightPlan.setTasks(tasks);

        //File name
        LocalDateTime nowDate = LocalDateTime.now();
        /** PROBLEM!! using two points in file name */
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("YYYY_D_HH.mm.ss"); //D = day of year
        String formattedDate = nowDate.format(formatDate);
        String fileName = "FPLAN_" + formattedDate;
        //create XML
        createXML(flightPlan, "src/main/resources/FlightPlan/" + fileName + ".xml");

    }

    public void createFlightPlanXMLFromDB (SysSchedulingProva sysSchedulingProva, long missionId) throws DatatypeConfigurationException, JAXBException {
        FlightPlanService flightPlanService = this.applicationContext.getBean(FlightPlanService.class);

        FlightPlan flightPlan = new FlightPlan();
        //HEADER
        Header header = new Header();
        header.setMissionId(BigInteger.valueOf(missionId)); //INPUT FROM WHERE??
        LocalDateTime localDateTimeStartTime = LocalDateTime.now();
        XMLGregorianCalendar xmlGregCalStartTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDateTimeStartTime.toString());
        header.setStartTime(xmlGregCalStartTime);
        header.setSchedulingId(sysSchedulingProva.getSchedulingId()); //INPUT

        //TASKS
        Tasks tasks = new Tasks();
        List<SysTaskScheduled> sysTaskScheduledList = flightPlanService.sysTaskScheduled(sysSchedulingProva); //list of task names for a scheduling_id
        /** No Task */
        tasks.setNoTask(BigInteger.valueOf(sysTaskScheduledList.size()));
        //BASEHEADER
        BaseHeader baseHeader = flightPlanService.createBaseHeaderFromDB(sysSchedulingProva);
        tasks.setBaseHeader(baseHeader);

        //TASK
        //TODO ORDER BY POSITION
        for (SysTaskScheduled sysTaskScheduled: sysTaskScheduledList) {
            Task task = new Task(); //new task for each new task name
            task.setTaskId(sysTaskScheduled.getTaskName()); //taskName
            //SEQUENCE
            List<Sequence> sequenceList = flightPlanService.createSequenceFromDB(sysTaskScheduled);
            for (Sequence sequence: sequenceList) {
                task.getElements().add(sequence);
            }

            //COMMANDS
            List<Command> commandList = flightPlanService.createCommandFromDB(sysTaskScheduled);
            for (Command command: commandList) {
                task.getElements().add(command);
            }

            tasks.getTask().add(task);
        }

        LocalDateTime localDateTimeEndTime = LocalDateTime.now();
        XMLGregorianCalendar xmlGregCalEndTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDateTimeEndTime.toString());
        header.setEndTime(xmlGregCalEndTime);

        flightPlan.setHeader(header);
        flightPlan.setTasks(tasks);

        //File name
        LocalDateTime nowDate = LocalDateTime.now();
        /** PROBLEM!! using two points in file name */
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("YYYY_D_HH.mm.ss"); //D = day of year
        String formattedDate = nowDate.format(formatDate);
        String fileName = "FPLAN_DB_" + formattedDate; //created from DB
        //create XML
        createXML(flightPlan, "src/main/resources/FlightPlan/" + fileName + ".xml");
    }

//    public boolean checkSequencePARS(String seqPARS) {
//        boolean seqPars = false;
//        if(Integer.valueOf(seqPARS) > 0) {
//            seqPars = true;
//            return seqPars;
//        }
//        return seqPars;
//    }

//    public boolean checkCommandPARS(String commPARS) {
//        boolean commPars = false;
//        if(Integer.valueOf(commPARS) > 0) {
//            commPars = true;
//            return commPars;
//        }
//        return commPars;
//    }

    public void createXML(FlightPlan flightPlan, String xmlPath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(FlightPlan.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(flightPlan, new File(xmlPath));
    }

//    public static void xmlToObject() throws JAXBException {
//
//        try {
//            File file = new File("src/main/resources/student.xml");
//
//            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
//
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
////            Marshaller marshaller = jaxbContext.createMarshaller();
////            marshaller.marshal(new FlightPlan(), new File(""));
//
//            Student student = (Student) unmarshaller.unmarshal(file);
//            System.out.println(student.getLastName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void objectToXML() throws JAXBException {

//        CAP cap1 = new CAP();
//        cap1.setCaPNumbr("ALBA");
//        cap1.setCapXvals("CUEVAS");
//        cap1.setCapYvals("CASADO");
//
//        CAP cap2 = new CAP();
//        cap2.setCaPNumbr("ITALIA");
//        cap2.setCapXvals("ROMA");
//        cap2.setCapYvals("FLORENCIA");
//
//        List<CAP> capList = new ArrayList<>();
//        capList.add(cap1);
//        capList.add(cap2);
//
//        CCS ccs1 = new CCS();
//        ccs1.setCcsNumbr("PRUEBA");
//        ccs1.setCcsXvals("GPA");
//        ccs1.setCcsYvals("AMBER");
//
//        List<CCS> ccsList = new ArrayList<>();
//        ccsList.add(ccs1);
//
//        HEADER header = new HEADER();
//        header.setId(1);
//        header.setStartTime(LocalDateTime.now());
//        System.out.println("Start Time: " + header.getStartTime());

//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(FlightPlan.class);
//            Marshaller marshaller = jaxbContext.createMarshaller(); //java object to XML

//            File file = new File("src/main/resources/FlightPlan/flight_plan.xml");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //XML format

//        for(CAP capObject: capList) {
//            //marshaller.marshal(capObject, file);
//            marshaller.marshal(capObject, new FileOutputStream(file));
//        }

            //FlightPlan flightPlan = new FlightPlan("TASKS", header,capList,ccsList);
            //marshaller.marshal(flightPlan, file);
//
//        } catch (Exception e) {
//               e.printStackTrace();
//        }
//    }

    private static Validator initValidator() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); //follow XML Schema specification
        Source schemaFile = new StreamSource("src/main/resources/FlightPlan/schema_info.xsd");
        Schema schema = factory.newSchema(schemaFile); //creates the set of constraints = schema
        return schema.newValidator();
    }

    public static boolean isValid() throws IOException, SAXException {
        Validator validator = initValidator();
        try {
            validator.validate(new StreamSource("src/main/resources/FlightPlan/flight_plan.xml"));
            System.out.println("TRUE");
            return true;
        } catch (SAXException e) {
            System.out.println("FALSE");
            return false;
        }
    }

}
