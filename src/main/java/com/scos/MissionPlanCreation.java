package com.scos;

import com.scos.XSDToJava3.FlightPlan;
import com.scos.data_model.mps_db.SysCommandHeader;
import com.scos.data_model.mps_db.SysSchedulingProva;
import com.scos.data_model.mps_db.SysSequenceHeader;
import com.scos.data_model.mps_db.SysTaskScheduled;
import com.scos.services.MissionPlanService;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MissionPlanCreation {

    ApplicationContext applicationContext;

    static String new_line = "\n";

    //inject application context to be able to call the bean required
    public MissionPlanCreation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("Application Context from Mission Plan: " + applicationContext);
    }

    public void createMissionPlanSSF (SysSchedulingProva sysSchedulingProva) throws IOException {
        MissionPlanService missionPlanService = this.applicationContext.getBean(MissionPlanService.class);
        //BufferedReader reader = new BufferedReader(new FileReader(missionPlanFile));
        //TASK SCHEDULED -> LIST
        List<SysTaskScheduled> sysTaskScheduledList = missionPlanService.sysTaskScheduled(sysSchedulingProva);

        //File name + creation
        LocalDateTime nowDate = LocalDateTime.now();
        /** PROBLEM!! using two points in file name */
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("YYYY_D_HH.mm.ss"); //D = day of year
        String formattedDate = nowDate.format(formatDate);
        String fileName = "MPLAN_TASKS_" + formattedDate;
        File fout = new File("src/main/resources/FlightPlan/" + fileName + ".ssf");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //CONTENT
        bw.write(missionPlanService.baseHeaderLine(sysSchedulingProva));
        bw.newLine();
        //TODO ORDER BY POSITION
        List<String> sequenceFile = missionPlanService.sequenceHeaderLine(sysTaskScheduledList);
        for(int i=0; i<sequenceFile.size(); i++) {
            bw.write(sequenceFile.get(i));
            bw.newLine();
        }

        List<String> commandFile = missionPlanService.commandHeaderLine(sysTaskScheduledList);
        for(int i=0; i<commandFile.size(); i++) {
            bw.write(commandFile.get(i));
            bw.newLine();
        }

        bw.close();
    }

    //TODO INSERT COLUMN POSITION ROWNO
    //read file is USELESS or at least, use it to insert data in DB -> data comes from DB
    public void readMissionPlan (String missionPlanFile) throws IOException {
        MissionPlanService missionPlanService = this.applicationContext.getBean(MissionPlanService.class);
        //SCHEDULING
        SysSchedulingProva sysSchedulingProva = missionPlanService.createSysSchedulingProva();
        //TASK_SCHEDULED
        SysTaskScheduled taskScheduled = missionPlanService.createSysTaskScheduled(sysSchedulingProva);

        BufferedReader reader = new BufferedReader(new FileReader(missionPlanFile));
        //Start file
        String row = reader.readLine();
        int rowNo = 1; //baseheader
        while (row != null) {
            String[] rowSplit = row.split("\\|");

            if(rowNo != 1) {
                if(rowSplit[0] == "S") {
                    SysSequenceHeader sysSequenceHeader = missionPlanService.createSequenceHeaderRecord(rowSplit,taskScheduled);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean seqParam = checkSequencePARS(rowSplit[2]);

                    if(Integer.valueOf(rowSplit[2]) > 0) {
                        for(int i=0; i<Integer.valueOf(rowSplit[2]); i++ ) {
                            //PARAMETER ROW
                            row = reader.readLine();
                            rowNo++;
                            String[] rowSplitParam = row.split("\\|");
                            missionPlanService.createSequenceParameterRecord(rowSplitParam,taskScheduled,sysSequenceHeader);
                        }
                    }
                } else {
                    SysCommandHeader sysCommandHeader = missionPlanService.createCommandHeaderRecord(rowSplit,taskScheduled);
                    /** after a header could be a parameter or another seq/comm header */
                    //boolean commParam = checkCommandPARS(rowSplit[13]);

                    if(Integer.valueOf(rowSplit[13]) > 0) {
                        for(int i=0; i<Integer.valueOf(rowSplit[13]); i++) {
                            //PARAMETER ROW
                            row = reader.readLine();
                            rowNo++;
                            String[] rowSplitParam = row.split("\\|");
                            missionPlanService.createCommandParameterRecord(rowSplitParam,taskScheduled,sysCommandHeader);
                    }
                }
            }
        } else {
                missionPlanService.createBaseHeaderRecord(rowSplit,sysSchedulingProva);
            }
            rowNo++;
            row = reader.readLine();
        }

        reader.close();
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
}
