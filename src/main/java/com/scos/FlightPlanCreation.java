package com.scos;

import com.scos.XSDToJava3.*;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FlightPlanCreation {

    public void createFlightPlanXML (String flightPlanFile, long missionId, long schedulingId, String taskId) throws IOException, DatatypeConfigurationException, JAXBException {
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
                    SequenceHeader sequenceHeaderXML = createSequenceHeader(rowSplit);

                    sequence.setSequenceHeader(sequenceHeaderXML);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean seqParam = checkSequencePARS(rowSplit[2]);
                    Integer seqParam = Integer.valueOf(rowSplit[2]);

                   if(seqParam > 0) {
                       for(int i=0; i<seqParam; i++ ) {
                           row = reader.readLine();
                           rowNo++;
                           String[] rowSplitParam = row.split("\\|");
                           SequenceParameter sequenceParameterXML = createSequenceParameter(rowSplitParam);
                           sequence.getSequenceParameter().add(sequenceParameterXML);
                       }
                   }
                   task.getElements().add(sequence);

                } else {
                    Command command = new Command();
                    CommandHeader commandHeaderXML = createCommandHeader(rowSplit);

                    command.setCommandHeader(commandHeaderXML);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean commParam = checkCommandPARS(rowSplit[13]);
                    Integer commParam = Integer.valueOf(rowSplit[13]);

                    if(commParam > 0) {
                        for(int i=0; i<commParam; i++) {
                            row = reader.readLine();
                            rowNo++;
                            String[] rowSplitParam = row.split("\\|");
                            CommandParameter commandParameterXML = createCommandParameter(rowSplitParam);
                            command.getCommandParameter().add(commandParameterXML);
                        }
                    }
                    task.getElements().add(command);
                }

            } else {
               BaseHeader baseHeaderXML = createBaseHeader(rowSplit);
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

    public BaseHeader createBaseHeader(String[] baseHeaderRow) {
        //BASEHEADER
        BaseHeader baseHeader = new BaseHeader();
        //CATEGORY
        BaseHeader.Field baseHeaderCategory = new BaseHeader.Field();
        baseHeaderCategory.setName("CATEGORY");
        baseHeaderCategory.setValue(baseHeaderRow[0]);
        baseHeaderCategory.setType("number");
        baseHeader.getField().add(baseHeaderCategory);
        //SOURCE
        BaseHeader.Field baseHeaderSource = new BaseHeader.Field();
        baseHeaderSource.setName("SOURCE");
        baseHeaderSource.setValue(baseHeaderRow[1]);
        baseHeaderSource.setType("char");
        baseHeader.getField().add(baseHeaderSource);
        //GEN TIME
        BaseHeader.Field baseHeaderGenTime = new BaseHeader.Field();
        baseHeaderGenTime.setName("GEN_TIME");
        baseHeaderGenTime.setValue(baseHeaderRow[2]);
        baseHeaderGenTime.setType("number");
        baseHeaderGenTime.setUnits("seconds");
        baseHeader.getField().add(baseHeaderGenTime);
        //RELTYPE
        BaseHeader.Field baseHeaderFieldRelType = new BaseHeader.Field();
        baseHeaderFieldRelType.setName("RELTYPE");
        baseHeaderFieldRelType.setValue(baseHeaderRow[3]);
        baseHeaderFieldRelType.setType("number");
        baseHeader.getField().add(baseHeaderFieldRelType);
        //VERSION
        BaseHeader.Field baseHeaderFieldVersion = new BaseHeader.Field();
        baseHeaderFieldVersion.setName("VERSION");
        baseHeaderFieldVersion.setValue(baseHeaderRow[4]);
        baseHeaderFieldVersion.setType("char");
        baseHeader.getField().add(baseHeaderFieldVersion);
        //START TIME
        BaseHeader.Field baseHeaderFieldStartTime = new BaseHeader.Field();
        baseHeaderFieldStartTime.setName("START_TIME");
        baseHeaderFieldStartTime.setValue(baseHeaderRow[5]);
        baseHeaderFieldStartTime.setType("number");
        baseHeaderFieldStartTime.setUnits("seconds");
        baseHeader.getField().add(baseHeaderFieldStartTime);

        return baseHeader;
    }

    public CommandHeader createCommandHeader(String[] commandHeaderRow) {
        /** check if position 13 > 0 -> if it is true call method commParam */
        //COMMAND HEADER
        CommandHeader commandHeader = new CommandHeader();
        //CMDTYPE
        CommandHeader.Field commHeaderCmdType = new CommandHeader.Field();
        commHeaderCmdType.setName("CMDTYPE");
        commHeaderCmdType.setValue(commandHeaderRow[0]);
        commHeaderCmdType.setType("char");
        commandHeader.getField().add(commHeaderCmdType);
        //ID
        CommandHeader.Field commHeaderId = new CommandHeader.Field();
        commHeaderId.setName("ID");
        commHeaderId.setValue(commandHeaderRow[1]);
        commHeaderId.setType("char");
        commandHeader.getField().add(commHeaderId);
        //MAN DISPATCH
        CommandHeader.Field commHeaderManDispatch = new CommandHeader.Field();
        commHeaderManDispatch.setName("MAN DISPATCH");
        commHeaderManDispatch.setValue(commandHeaderRow[2]);
        commHeaderManDispatch.setType("number");
        commandHeader.getField().add(commHeaderManDispatch);
        //RELEASE
        CommandHeader.Field commHeaderRelease = new CommandHeader.Field();
        commHeaderRelease.setName("RELEASE");
        commHeaderRelease.setValue(commandHeaderRow[3]);
        commHeaderRelease.setType("number");
        commandHeader.getField().add(commHeaderRelease);
        //RELTIME
        CommandHeader.Field commHeaderRelTime = new CommandHeader.Field();
        commHeaderRelTime.setName("RELTIME");
        commHeaderRelTime.setValue(commandHeaderRow[4]);
        commHeaderRelTime.setType("number");
        commHeaderRelTime.setUnits("seconds");
        commandHeader.getField().add(commHeaderRelTime);
        //RELTIME2
        CommandHeader.Field commHeaderRelTime2 = new CommandHeader.Field();
        commHeaderRelTime2.setName("RELTIME2");
        commHeaderRelTime2.setValue(commandHeaderRow[5]);
        commHeaderRelTime2.setType("number");
        commHeaderRelTime2.setUnits("microseconds");
        commandHeader.getField().add(commHeaderRelTime2);
        //GROUP
        CommandHeader.Field commHeaderGroup = new CommandHeader.Field();
        commHeaderGroup.setName("GROUP");
        commHeaderGroup.setValue(commandHeaderRow[6]);
        commHeaderGroup.setType("number");
        commandHeader.getField().add(commHeaderGroup);
        //BLOCK
        CommandHeader.Field commHeaderBlock = new CommandHeader.Field();
        commHeaderBlock.setName("BLOCK");
        commHeaderBlock.setValue(commandHeaderRow[7]);
        commHeaderBlock.setType("number");
        commandHeader.getField().add(commHeaderBlock);
        //INTERLOCK
        CommandHeader.Field commHeaderInterlock = new CommandHeader.Field();
        commHeaderInterlock.setName("INTERLOCK");
        commHeaderInterlock.setValue(commandHeaderRow[8]);
        commHeaderInterlock.setType("number");
        commandHeader.getField().add(commHeaderInterlock);
        //ILSTAGE
        if(commandHeaderRow[9].length() > 0) {
            CommandHeader.Field commHeaderIlstage = new CommandHeader.Field();
            commHeaderIlstage.setName("ILSTAGE");
            commHeaderIlstage.setValue(commandHeaderRow[9]);
            commHeaderIlstage.setType("char");
            commandHeader.getField().add(commHeaderIlstage);
        }
        //STATIC PTV
        CommandHeader.Field commHeaderStaticPTV = new CommandHeader.Field();
        commHeaderStaticPTV.setName("STATIC PTV");
        commHeaderStaticPTV.setValue(commandHeaderRow[10]);
        commHeaderStaticPTV.setType("number");
        commandHeader.getField().add(commHeaderStaticPTV);
        //DYNAMIC PTV
        CommandHeader.Field commHeaderDynamicPTV = new CommandHeader.Field();
        commHeaderDynamicPTV.setName("DYNAMIC PTV");
        commHeaderDynamicPTV.setValue(commandHeaderRow[11]);
        commHeaderDynamicPTV.setType("number");
        commandHeader.getField().add(commHeaderDynamicPTV);
        //CEV
        CommandHeader.Field commHeaderCev = new CommandHeader.Field();
        commHeaderCev.setName("CEV");
        commHeaderCev.setValue(commandHeaderRow[12]);
        commHeaderCev.setType("number");
        commandHeader.getField().add(commHeaderCev);
        //PARS
        /** CHECK VALUE */
        CommandHeader.Field commHeaderPars = new CommandHeader.Field();
        commHeaderPars.setName("PARS");
        commHeaderPars.setValue(commandHeaderRow[13]);
        commHeaderPars.setType("number");
        commandHeader.getField().add(commHeaderPars);
        //TIME TAGGED
        CommandHeader.Field commHeaderTimeTagged = new CommandHeader.Field();
        commHeaderTimeTagged.setName("TIME TAGGED");
        commHeaderTimeTagged.setValue(commandHeaderRow[14]);
        commHeaderTimeTagged.setType("number");
        commandHeader.getField().add(commHeaderTimeTagged);
        //PLANNED
        CommandHeader.Field commHeaderPlanned = new CommandHeader.Field();
        commHeaderPlanned.setName("PLANNED");
        commHeaderPlanned.setValue(commandHeaderRow[15]);
        commHeaderPlanned.setType("number");
        commandHeader.getField().add(commHeaderPlanned);
        //EXEC TIME
        CommandHeader.Field commHeaderExecTime = new CommandHeader.Field();
        commHeaderExecTime.setName("EXEC TIME");
        commHeaderExecTime.setValue(commandHeaderRow[16]);
        commHeaderExecTime.setType("number");
        commHeaderExecTime.setUnits("seconds");
        commandHeader.getField().add(commHeaderExecTime);
        //EXEC TIME2
        CommandHeader.Field commHeaderExecTime2 = new CommandHeader.Field();
        commHeaderExecTime2.setName("EXEC TIME2");
        commHeaderExecTime2.setValue(commandHeaderRow[17]);
        commHeaderExecTime2.setType("number");
        commHeaderExecTime2.setUnits("microseconds");
        commandHeader.getField().add(commHeaderExecTime2);
        //PARENT -> OPTIONAL
        if(commandHeaderRow[18].length() > 0) {
            CommandHeader.Field commHeaderParent = new CommandHeader.Field();
            commHeaderParent.setName("PARENT");
            commHeaderParent.setValue(commandHeaderRow[18]);
            commHeaderParent.setType("char");
            commandHeader.getField().add(commHeaderParent);
        }
        //STARTTIME -> OPTIONAL
        if(commandHeaderRow[19].length() > 0) {
            CommandHeader.Field commHeaderStartTime = new CommandHeader.Field();
            commHeaderStartTime.setName("STARTTIME");
            commHeaderStartTime.setValue(commandHeaderRow[19]);
            commHeaderStartTime.setType("number");
            commandHeader.getField().add(commHeaderStartTime);
        }
        //SUBSYSTEM -> OPTIONAL
        if(commandHeaderRow[20].length() > 0) {
            CommandHeader.Field commHeaderSubSystem = new CommandHeader.Field();
            commHeaderSubSystem.setName("SUBSYSTEM");
            commHeaderSubSystem.setValue(commandHeaderRow[20]);
            commHeaderSubSystem.setType("number");
            commandHeader.getField().add(commHeaderSubSystem);
        }
        //SOURCE -> OPTIONAL
        if(commandHeaderRow[21].length() > 0) {
            CommandHeader.Field commHeaderSource = new CommandHeader.Field();
            commHeaderSource.setName("SOURCE");
            commHeaderSource.setValue(commandHeaderRow[21]);
            commHeaderSource.setType("number");
            commandHeader.getField().add(commHeaderSource);
        }
        //EARLIEST -> OPTIONAL
        if(commandHeaderRow[22].length() > 0) {
            CommandHeader.Field commHeaderEarliest = new CommandHeader.Field();
            commHeaderEarliest.setName("EARLIEST");
            commHeaderEarliest.setValue(commandHeaderRow[22]);
            commHeaderEarliest.setType("number");
            commandHeader.getField().add(commHeaderEarliest);
        }
        //LATEST -> OPTIONAL
        if(commandHeaderRow[23].length() > 0) {
            CommandHeader.Field commHeaderLatest = new CommandHeader.Field();
            commHeaderLatest.setName("LATEST");
            commHeaderLatest.setValue(commandHeaderRow[23]);
            commHeaderLatest.setType("number");
            commandHeader.getField().add(commHeaderLatest);
        }
        //TC REQUEST ID -> OPTIONAL
        if(commandHeaderRow[24].length() > 0) {
            CommandHeader.Field commHeaderTCRequestId = new CommandHeader.Field();
            commHeaderTCRequestId.setName("TC REQUEST ID");
            commHeaderTCRequestId.setValue(commandHeaderRow[24]);
            commHeaderTCRequestId.setType("number");
            commandHeader.getField().add(commHeaderTCRequestId);
        }
        //SUB-SCHED ID
        CommandHeader.Field commHeaderSubSchedId = new CommandHeader.Field();
        commHeaderSubSchedId.setName("SUB-SCHED ID");
        commHeaderSubSchedId.setValue(commandHeaderRow[25]);
        commHeaderSubSchedId.setType("number");
        commandHeader.getField().add(commHeaderSubSchedId);
        //ACK-FLAGS -> OPTIONAL
        if(commandHeaderRow.length > 26) {
            CommandHeader.Field commHeaderAckFlags = new CommandHeader.Field();
            commHeaderAckFlags.setName("ACK-FLAGS");
            //control when last position is EMPTY
            //commHeaderAckFlags.setValue(commandHeaderRow.length > 26 ? commandHeaderRow[26] : "" );
            commHeaderAckFlags.setValue(commandHeaderRow[26]);
            commHeaderAckFlags.setType("number");
            commandHeader.getField().add(commHeaderAckFlags);
        }

        return commandHeader;
    }

    public CommandParameter createCommandParameter(String[] commandParameterRow) {
        //COMMAND PARAMETER
        CommandParameter commandParameter = new CommandParameter();
        //ID
        CommandParameter.Field commParamID = new CommandParameter.Field();
        commParamID.setName("ID");
        commParamID.setValue(commandParameterRow[0]);
        commParamID.setType("char");
        commandParameter.getField().add(commParamID);
        //FORMPOS
        CommandParameter.Field commParamFormPos = new CommandParameter.Field();
        commParamFormPos.setName("FORMPOS");
        commParamFormPos.setValue(commandParameterRow[1]);
        commParamFormPos.setType("number");
        commandParameter.getField().add(commParamFormPos);
        //TYPE
        CommandParameter.Field commParamType = new CommandParameter.Field();
        commParamType.setName("TYPE");
        commParamType.setValue(commandParameterRow[2]);
        commParamType.setType("number");
        commandParameter.getField().add(commParamType);
        //EDITABLE
        CommandParameter.Field commParamEditable = new CommandParameter.Field();
        commParamEditable.setName("EDITABLE");
        commParamEditable.setValue(commandParameterRow[3]);
        commParamEditable.setType("number");
        commandParameter.getField().add(commParamEditable);
        //REPTYPE
        CommandParameter.Field commParamRepType = new CommandParameter.Field();
        commParamRepType.setName("REPTYPE");
        commParamRepType.setValue(commandParameterRow[4]);
        commParamRepType.setType("number");
        commandParameter.getField().add(commParamRepType);
        //VALUE -> OPTIONAL
        if(commandParameterRow[5].length() > 0) {
            CommandParameter.Field commParamValue = new CommandParameter.Field();
            commParamValue.setName("VALUE");
            //commParamValue.setValue(commandParameterRow[5].length() > 0 ? commandParameterRow[5] : "");
            commParamValue.setValue(commandParameterRow[5]);
            commParamValue.setType("char");
            commandParameter.getField().add(commParamValue);
        }
        //DYNAMIC -> OPTIONAL
        if(commandParameterRow[6].length() > 0) {
            CommandParameter.Field commParamDynamic = new CommandParameter.Field();
            commParamDynamic.setName("DYNAMIC");
            //commParamDynamic.setValue(commandParameterRow.length > 6 ? commandParameterRow[6] : "");
            commParamDynamic.setValue(commandParameterRow[6]);
            commParamDynamic.setType("number");
            commandParameter.getField().add(commParamDynamic);
        }

        return commandParameter;
    }

    public SequenceHeader createSequenceHeader(String[] sequenceHeaderRow) {
        /** check if position 3 > 0 -> if it is true call method seqParam */
        //SEQUENCE HEADER
        SequenceHeader sequenceHeader = new SequenceHeader();
        //SEQ TYPE
        SequenceHeader.Field seqHeaderSeqType = new SequenceHeader.Field();
        seqHeaderSeqType.setName("SEQ TYPE");
        seqHeaderSeqType.setValue(sequenceHeaderRow[0]);
        seqHeaderSeqType.setType("char");
        sequenceHeader.getField().add(seqHeaderSeqType);
        //ID
        SequenceHeader.Field seqHeaderId = new SequenceHeader.Field();
        seqHeaderId.setName("ID");
        seqHeaderId.setValue(sequenceHeaderRow[1]);
        seqHeaderId.setType("char");
        sequenceHeader.getField().add(seqHeaderId);
        //PARS
        SequenceHeader.Field seqHeaderPars = new SequenceHeader.Field();
        seqHeaderPars.setName("PARS");
        seqHeaderPars.setValue(sequenceHeaderRow[2]);
        seqHeaderPars.setType("number");
        sequenceHeader.getField().add(seqHeaderPars);
        //CMDS
        /** number of commands in the sequence */
        SequenceHeader.Field seqHeaderCmds = new SequenceHeader.Field();
        seqHeaderCmds.setName("CMDS");
        seqHeaderCmds.setValue(sequenceHeaderRow[3]);
        seqHeaderCmds.setType("number");
        sequenceHeader.getField().add(seqHeaderCmds);
        //STARTTIME
        SequenceHeader.Field seqHeaderStartTime = new SequenceHeader.Field();
        seqHeaderStartTime.setName("STARTTIME");
        seqHeaderStartTime.setValue(sequenceHeaderRow[4]);
        seqHeaderStartTime.setType("number");
        seqHeaderStartTime.setUnits("seconds");
        sequenceHeader.getField().add(seqHeaderStartTime);
        //STARTTIME2
        SequenceHeader.Field seqHeaderStartTime2 = new SequenceHeader.Field();
        seqHeaderStartTime2.setName("STARTTIME2");
        seqHeaderStartTime2.setValue(sequenceHeaderRow[5]);
        seqHeaderStartTime2.setType("number");
        seqHeaderStartTime.setUnits("microseconds");
        sequenceHeader.getField().add(seqHeaderStartTime2);
        //SUBSYSTEM -> OPTIONAL
        if(sequenceHeaderRow[6].length() > 0) {
            SequenceHeader.Field seqHeaderSubsystem = new SequenceHeader.Field();
            seqHeaderSubsystem.setName("SUBSYSTEM");
            seqHeaderSubsystem.setValue(sequenceHeaderRow[6]);
            seqHeaderSubsystem.setType("number");
            sequenceHeader.getField().add(seqHeaderSubsystem);
        }
        //SOURCE -> OPTIONAL
        if(sequenceHeaderRow[7].length() > 0) {
            SequenceHeader.Field seqHeaderSource = new SequenceHeader.Field();
            seqHeaderSource.setName("SOURCE");
            seqHeaderSource.setValue(sequenceHeaderRow[7]);
            seqHeaderSource.setType("number");
            sequenceHeader.getField().add(seqHeaderSource);
        }
        //TC REQUEST ID -> OPTIONAL
        if(sequenceHeaderRow[8].length() > 0) {
            SequenceHeader.Field seqHeaderTCRequestId = new SequenceHeader.Field();
            seqHeaderTCRequestId.setName("TC REQUEST ID");
            seqHeaderTCRequestId.setValue(sequenceHeaderRow[8]);
            seqHeaderTCRequestId.setType("number");
            sequenceHeader.getField().add(seqHeaderTCRequestId);
        }
        //SUB-SCHED ID -> MANDATORY
        SequenceHeader.Field seqHeaderSubSchedId = new SequenceHeader.Field();
        seqHeaderSubSchedId.setName("SUB-SCHED ID");
        seqHeaderSubSchedId.setValue(sequenceHeaderRow[9]);
        seqHeaderSubSchedId.setType("number");
        sequenceHeader.getField().add(seqHeaderSubSchedId);

        return sequenceHeader;
    }

    public SequenceParameter createSequenceParameter(String[] sequenceParameterRow) {
        //SEQUENCE PARAMETER
        SequenceParameter sequenceParameter = new SequenceParameter();
        //ID
        SequenceParameter.Field seqParamId = new SequenceParameter.Field();
        seqParamId.setName("ID");
        seqParamId.setValue(sequenceParameterRow[0]);
        seqParamId.setType("char");
        sequenceParameter.getField().add(seqParamId);
        //TYPE
        SequenceParameter.Field seqParamType = new SequenceParameter.Field();
        seqParamType.setName("TYPE");
        seqParamType.setValue(sequenceParameterRow[1]);
        seqParamType.setType("number");
        sequenceParameter.getField().add(seqParamType);
        //REPTYPE
        SequenceParameter.Field seqParamRepType = new SequenceParameter.Field();
        seqParamRepType.setName("REPTYPE");
        seqParamRepType.setType(sequenceParameterRow[2]);
        seqParamRepType.setType("number");
        sequenceParameter.getField().add(seqParamRepType);
        //VALUE
        SequenceParameter.Field seqParamValue = new SequenceParameter.Field();
        seqParamValue.setName("VALUE");
        seqParamValue.setType(sequenceParameterRow[3]);
        seqParamValue.setType("char");
        sequenceParameter.getField().add(seqParamValue);

        return sequenceParameter;
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
