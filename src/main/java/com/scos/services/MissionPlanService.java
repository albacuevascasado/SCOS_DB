package com.scos.services;

import com.scos.data_model.mps_db.*;
import com.scos.repositories.MissionPlanRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** BUSINESS LOGIC */
@NoArgsConstructor //REQUIRED TO BE ABLE TO INVOKE METHOD
@Setter
@Getter
public class MissionPlanService {

    static String pipe = "|";
//    static String new_line = "\n";

    @Autowired
    MissionPlanRepository missionplanRepository;

    public SysSchedulingProva createSysSchedulingProva() {
        SysSchedulingProva sysSchedulingProva = new SysSchedulingProva();
        sysSchedulingProva.setSchedulingType(SysSchedulingProva.SchedulingType.MANUAL);

        if(missionplanRepository != null) {
            missionplanRepository.saveSysSchedulingProva(sysSchedulingProva);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }

        return sysSchedulingProva;
    }

    //TO KNOW WHICH IS THE SCHEDULING_ID -> ONE RESULT (QUERY)
    public SysSchedulingProva searchSchedulingProva(BigInteger schedulingId) {
        SysSchedulingProva schedulingProvaFromDB = missionplanRepository.schedulingProvaRecord(schedulingId);
        return schedulingProvaFromDB;
    }

    public SysTaskScheduled createSysTaskScheduled(SysSchedulingProva sysSchedulingProva) {
        SysTaskScheduled sysTaskScheduled = new SysTaskScheduled();
        sysTaskScheduled.setTaskName("AAAA");
        sysTaskScheduled.setSysScheduling(sysSchedulingProva);

        if(missionplanRepository != null) {
            missionplanRepository.saveTaskScheduledRecord(sysTaskScheduled);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }

        return sysTaskScheduled;
    }

    public List<SysTaskScheduled> sysTaskScheduled(SysSchedulingProva sysSchedulingProva) {
        List<SysTaskScheduled> sysTaskScheduledList = missionplanRepository.taskScheduledRecord(sysSchedulingProva);
        return sysTaskScheduledList;
    }

    public void createBaseHeaderRecord(String[] baseheaderRow, SysSchedulingProva sysSchedulingProva) {
        SysBaseHeader sysBaseHeader = new SysBaseHeader();
        //ADDED NOT IN THE RECORD FROM FILE
        sysBaseHeader.setSysScheduling(sysSchedulingProva);

        sysBaseHeader.setCategory(Integer.valueOf(baseheaderRow[0]));
        sysBaseHeader.setSource(baseheaderRow[1]);
        sysBaseHeader.setGenTime(BigInteger.valueOf(Integer.valueOf(baseheaderRow[2])));
        sysBaseHeader.setRelType(Integer.valueOf(baseheaderRow[3]));
        sysBaseHeader.setVersion(baseheaderRow[4]);
        sysBaseHeader.setStartTime(BigInteger.valueOf(Integer.valueOf(baseheaderRow[5])));

        if(missionplanRepository != null) {
            missionplanRepository.saveBaseHeaderRecord(sysBaseHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public String baseHeaderLine(SysSchedulingProva sysSchedulingProva) {
        SysBaseHeader baseheaderFromDB = missionplanRepository.baseHeaderRecord(sysSchedulingProva);
        String baseHeader = baseheaderFromDB.getCategory() + pipe
                + baseheaderFromDB.getSource() + pipe
                + baseheaderFromDB.getGenTime() + pipe
                + baseheaderFromDB.getRelType() + pipe
                + baseheaderFromDB.getVersion() + pipe
                + baseheaderFromDB.getStartTime() + pipe;

        System.out.println("Base Header: " + baseHeader);
        return baseHeader;
    }

    public SysSequenceHeader createSequenceHeaderRecord(String[] sequenceheaderRow, SysTaskScheduled sysTaskScheduled) {
        SysSequenceHeader sysSequenceHeader = new SysSequenceHeader();
        //ADDED NOT IN THE FILE RECORD
        sysSequenceHeader.setSysTaskScheduled(sysTaskScheduled);

        sysSequenceHeader.setSequenceId(sequenceheaderRow[1]);
        sysSequenceHeader.setPars(Integer.valueOf(sequenceheaderRow[2]));
        sysSequenceHeader.setCmds(Integer.valueOf(sequenceheaderRow[3]));
        sysSequenceHeader.setStartTime(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[4])));
        sysSequenceHeader.setStartTime2(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[5])));

        sysSequenceHeader.setSubsystem(sequenceheaderRow[6].length() > 0?  Integer.valueOf(sequenceheaderRow[6]): null);
        sysSequenceHeader.setSource(sequenceheaderRow[7].length() > 0? Integer.valueOf(sequenceheaderRow[7]): null);
        sysSequenceHeader.setTcRequestId(sequenceheaderRow[8].length() > 0? Integer.valueOf(sequenceheaderRow[8]): null);

        sysSequenceHeader.setSubSchedId(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[9])));

        if(missionplanRepository != null) {
            missionplanRepository.saveSequenceHeaderRecord(sysSequenceHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
        return sysSequenceHeader;
    }

    public List<String> sequenceHeaderLine(List<SysTaskScheduled> sysTaskScheduled) {
        List<SysSequenceHeader> seqheaderFromDB = missionplanRepository.sequenceHeaderRecords(sysTaskScheduled);

        /**GROUP BY TASK*/
        //Streams are mainly used to perform operations on data | Collections are mainly used to store and group the data
        Map<SysTaskScheduled, List<SysSequenceHeader>> sequenceheaderByTaskName = seqheaderFromDB.stream().collect(Collectors.groupingBy(SysSequenceHeader :: getSysTaskScheduled));

        List<String> sequenceFile = new ArrayList<>();
        //sequenceheaderByTaskName.entrySet() -> to iterate through map
        //sequenceheaderByTaskName.size() = size of keys
        for (Map.Entry<SysTaskScheduled,List<SysSequenceHeader>> entry: sequenceheaderByTaskName.entrySet()) {
            //entry.getValue().size() = size of values for EACH KEY
            //System.out.println("Sequence Header with task name: " + entry.getKey().getTaskName());

            //INDEX(i) -> iterates through values and restart (i=0) when there is a new key
            for(int i = 0; i < entry.getValue().size(); i++) {
                boolean seqparamExists = false;
                String sequenceHeader = entry.getValue().get(i).getSeqType() + pipe
                        + entry.getValue().get(i).getSequenceId() + pipe
                        + entry.getValue().get(i).getPars() + pipe   //sequence parameters?
                        + entry.getValue().get(i).getCmds() + pipe   //commands? -> NO ORDER
                        + entry.getValue().get(i).getStartTime() + pipe
                        + entry.getValue().get(i).getStartTime2() + pipe
                        + (entry.getValue().get(i).getSubsystem() != null? entry.getValue().get(i).getSubsystem():"") + pipe
                        + (entry.getValue().get(i).getSource() != null? entry.getValue().get(i).getSource():"") + pipe
                        + (entry.getValue().get(i).getTcRequestId() != null? entry.getValue().get(i).getTcRequestId():"") + pipe
                        + (entry.getValue().get(i).getSubSchedId() != null? entry.getValue().get(i).getSubSchedId():"") + pipe;

                sequenceFile.add(sequenceHeader);
                //System.out.println("Sequence Header Add: " + sequenceHeader);

                /** SEQUENCE PARAMETERS */
                System.out.println("PARS " + entry.getValue().get(i).getSequenceId() + " " + entry.getValue().get(i).getPars());
                seqparamExists = entry.getValue().get(i).getPars() > 0 ? true : false;
                //when is TRUE add sequence parameters
                if(seqparamExists) {
                    List<String> sequenceParameters = sequenceParameterLines(entry.getValue().get(i).getSysTaskScheduled(), entry.getValue().get(i).getSequenceId());
                    for(int j=0; j<sequenceParameters.size(); j++) {
                        sequenceFile.add(sequenceParameters.get(j));
                    }
                }
            }
        }

        for(int k = 0; k<sequenceFile.size();k++) {
            System.out.println("Sequence: " + k + " " + sequenceFile.get(k));
        }

//        List<String> sequenceFile = new ArrayList<>();
//        for(int i=0; i<seqheaderFromDB.size(); i++) {
//            String sequenceHeader = seqheaderFromDB.get(i).getSeqType() + pipe
//                    + seqheaderFromDB.get(i).getSequenceId() + pipe
//                    + seqheaderFromDB.get(i).getPars() + pipe
//                    + seqheaderFromDB.get(i).getCmds() + pipe
//                    + seqheaderFromDB.get(i).getStartTime() + pipe
//                    + seqheaderFromDB.get(i).getStartTime2() + pipe
//                    + (seqheaderFromDB.get(i).getSubsystem() != null? seqheaderFromDB.get(i).getSubsystem():"") + pipe
//                    + (seqheaderFromDB.get(i).getSource() != null? seqheaderFromDB.get(i).getSource():"") + pipe
//                    + (seqheaderFromDB.get(i).getTcRequestId() != null? seqheaderFromDB.get(i).getTcRequestId():"") + pipe
//                    + seqheaderFromDB.get(i).getSubSchedId() + pipe;
//
//            sequenceFile.add(sequenceHeader);
//            List<String> sequenceParameters = sequenceParameterLines(seqheaderFromDB.get(i).getSysTaskScheduled(), seqheaderFromDB.get(i));
//
//            for(int j=0; j<sequenceParameters.size(); j++) {
//                sequenceFile.add(sequenceParameters.get(j));
//            }
//
//            //commands? -> how to know which commands are inside the sequences / nested sequences
//            //seqheader (CMDS) = there are commands + commheader(parent) = sequenceId
//            if(seqheaderFromDB.get(i).getCmds() > 0) {
//                //List<String> seqCommands = commandHeaderLine(seqheaderFromDB.get(i).getSysTaskScheduled(), seqheaderFromDB.get(i));
//                List<String> seqCommands = commandHeaderLine(seqheaderFromDB.get(i).getSysTaskScheduled());
//                for(int k=0; k<seqCommands.size(); k++) {
//                    sequenceFile.add(seqCommands.get(k));
//                }
//            }
//
//        }
        return sequenceFile;
    }

    public void createSequenceParameterRecord(String[] sequenceparameterRow, SysTaskScheduled sysTaskScheduled, SysSequenceHeader sysSequenceHeader) {
        SysSequenceParameter sysSequenceParameter = new SysSequenceParameter();
        //ADDED NOT IN THE FILE RECORD
        sysSequenceParameter.setSysSequenceHeader(sysSequenceHeader);
        sysSequenceParameter.setSysTaskScheduled(sysTaskScheduled);

        sysSequenceParameter.setSequenceParameterId(sequenceparameterRow[0]);
        sysSequenceParameter.setType(Integer.valueOf(sequenceparameterRow[1]));
        sysSequenceParameter.setRepType(Integer.valueOf(sequenceparameterRow[2]));
        sysSequenceParameter.setValue(sequenceparameterRow[3]);

        if(missionplanRepository != null) {
            missionplanRepository.saveSequenceParameterRecord(sysSequenceParameter);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    /** FILTER BY TASK AND SEQUENCE ID */
    public List<String> sequenceParameterLines(SysTaskScheduled sysTaskScheduled, String sequenceId) {
        //List<SysSequenceParameter> seqparamFromDB = missionplanRepository.sequenceParameterRecords(sysSequenceHeader);
        List<SysSequenceParameter> seqparamFromDB = missionplanRepository.sequenceParametersForEachSequenceHeader(sysTaskScheduled, sequenceId);
        List<String> seqparamFile = new ArrayList<>();
        for(int i=0; i<seqparamFromDB.size(); i++) {
            String sequenceParam = seqparamFromDB.get(i).getSequenceParameterId() + pipe
                    + seqparamFromDB.get(i).getType() + pipe
                    + seqparamFromDB.get(i).getRepType() + pipe
                    + seqparamFromDB.get(i).getValue() + pipe;

            seqparamFile.add(sequenceParam);
        }
        return seqparamFile;
    }

    public SysCommandHeader createCommandHeaderRecord(String[] commandheaderRow, SysTaskScheduled sysTaskScheduled) {
        SysCommandHeader sysCommandHeader = new SysCommandHeader();
        //ADDED NOT IN THE FILE RECORD
        sysCommandHeader.setSysTaskScheduled(sysTaskScheduled);

        sysCommandHeader.setCmdType(SysCommandHeader.Cmdtype.valueOf(commandheaderRow[0]));
        sysCommandHeader.setCommandId(commandheaderRow[1]);
        sysCommandHeader.setManDispatch(Integer.valueOf(commandheaderRow[2]));
        sysCommandHeader.setRelease(Integer.valueOf(commandheaderRow[3]));
        sysCommandHeader.setRelTime(BigInteger.valueOf(Integer.valueOf(commandheaderRow[4])));
        sysCommandHeader.setRelTime2(BigInteger.valueOf(Integer.valueOf(commandheaderRow[5])));
        sysCommandHeader.setGroup(Integer.valueOf(commandheaderRow[6]));
        sysCommandHeader.setBlock(Integer.valueOf(commandheaderRow[7]));
        sysCommandHeader.setInterlock(Integer.valueOf(commandheaderRow[8]));
        sysCommandHeader.setIlStage(commandheaderRow[9].length() > 0? commandheaderRow[9]:null);
        sysCommandHeader.setStaticPTV(Integer.valueOf(commandheaderRow[10]));
        sysCommandHeader.setDynamicPTV(Integer.valueOf(commandheaderRow[11]));
        sysCommandHeader.setCev(Integer.valueOf(commandheaderRow[12]));
        sysCommandHeader.setPars(Integer.valueOf(commandheaderRow[13]));
        sysCommandHeader.setTimeTagged(Integer.valueOf(commandheaderRow[14]));
        sysCommandHeader.setPlanned(Integer.valueOf(commandheaderRow[15]));
        sysCommandHeader.setExecTime(BigInteger.valueOf(Integer.valueOf(commandheaderRow[16])));
        sysCommandHeader.setExecTime2(BigInteger.valueOf(Integer.valueOf(commandheaderRow[17])));
        sysCommandHeader.setParent(commandheaderRow[18].length() > 0? commandheaderRow[18]:null);
        sysCommandHeader.setStartTime(commandheaderRow[19].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[19])):null);
        sysCommandHeader.setSubSystem(commandheaderRow[20].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[20])):null);
        sysCommandHeader.setSource(commandheaderRow[21].length() > 0? Integer.valueOf(commandheaderRow[21]):null);
        sysCommandHeader.setEarliest(commandheaderRow[22].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[22])):null);
        sysCommandHeader.setLatest(commandheaderRow[23].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[23])):null);
        sysCommandHeader.setTcRequestId(commandheaderRow[24].length() > 0? Integer.valueOf(commandheaderRow[24]):null);
        sysCommandHeader.setSubSchedId(BigInteger.valueOf(Integer.valueOf(commandheaderRow[25])));
        sysCommandHeader.setAckFlags(commandheaderRow.length > 26? Integer.valueOf(commandheaderRow[26]):null);

        if(missionplanRepository != null) {
            missionplanRepository.saveCommandHeaderRecord(sysCommandHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
        return sysCommandHeader;
    }

    public List<String> commandHeaderLine(List<SysTaskScheduled> sysTaskScheduled) {
        List<SysCommandHeader> commheaderFromDB = missionplanRepository.commandHeaderRecords(sysTaskScheduled);

        /**GROUP BY TASK*/
        //Streams are mainly used to perform operations on data | Collections are mainly used to store and group the data
        Map<SysTaskScheduled, List<SysCommandHeader>> commandheaderByTaskName = commheaderFromDB.stream().collect(Collectors.groupingBy(SysCommandHeader :: getSysTaskScheduled));

        List<String> commandFile = new ArrayList<>();
        for (Map.Entry<SysTaskScheduled,List<SysCommandHeader>> entry: commandheaderByTaskName.entrySet()) {
            //INDEX(i) -> iterates through values and restart (i=0) when there is a new key
            for(int i = 0; i < entry.getValue().size(); i++) {
                boolean commparamExists = false;
                String commandheader = entry.getValue().get(i).getCmdType() + pipe
                        + entry.getValue().get(i).getCommandId() + pipe
                        + entry.getValue().get(i).getManDispatch() + pipe
                        + entry.getValue().get(i).getRelease() + pipe
                        + entry.getValue().get(i).getRelTime() + pipe
                        + entry.getValue().get(i).getRelTime2() + pipe
                        + entry.getValue().get(i).getGroup() + pipe
                        + entry.getValue().get(i).getBlock() + pipe
                        + entry.getValue().get(i).getInterlock() + pipe
                        + (entry.getValue().get(i).getIlStage() != null? entry.getValue().get(i).getIlStage():"") + pipe
                        + entry.getValue().get(i).getStaticPTV() + pipe
                        + entry.getValue().get(i).getDynamicPTV() + pipe
                        + entry.getValue().get(i).getCev() + pipe
                        + entry.getValue().get(i).getPars() + pipe
                        + entry.getValue().get(i).getTimeTagged() + pipe
                        + entry.getValue().get(i).getPlanned() + pipe
                        + entry.getValue().get(i).getExecTime() + pipe
                        + entry.getValue().get(i).getExecTime2() + pipe
                        + (entry.getValue().get(i).getParent() != null? entry.getValue().get(i).getParent():"") + pipe
                        + (entry.getValue().get(i).getStartTime() != null? entry.getValue().get(i).getStartTime():"") + pipe
                        + (entry.getValue().get(i).getSubSystem() != null? entry.getValue().get(i).getSubSystem():"") + pipe
                        + (entry.getValue().get(i).getSource() != null? entry.getValue().get(i).getSource():"") + pipe
                        + (entry.getValue().get(i).getEarliest() != null? entry.getValue().get(i).getEarliest():"") + pipe
                        + (entry.getValue().get(i).getLatest() != null? entry.getValue().get(i).getLatest():"") + pipe
                        + (entry.getValue().get(i).getTcRequestId() != null? entry.getValue().get(i).getTcRequestId():"") + pipe
                        + entry.getValue().get(i).getSubSchedId() + pipe
                        + (entry.getValue().get(i).getAckFlags() != null? entry.getValue().get(i).getAckFlags():"");

                commandFile.add(commandheader);

                /** COMMAND PARAMETERS */
                System.out.println("PARS " + entry.getValue().get(i).getCommandId() + " " + entry.getValue().get(i).getPars());
                commparamExists = entry.getValue().get(i).getPars().intValue() > 0? true : false;
                //when is TRUE add sequence parameters
                if(commparamExists) {
                    List<String> commandParameters = commandParameterLines(entry.getValue().get(i).getSysTaskScheduled(), entry.getValue().get(i).getCommandId());
                    for(int j=0; j<commandParameters.size(); j++) {
                        commandFile.add(commandParameters.get(j));
                    }
                }
            }
        }

//        /** i<commheaderFromDb.size() do the check when there is not a header */
//        for(int i=0; i<commheaderFromDB.size(); i++) {
//            String commandHeader = commheaderFromDB.get(i).getCmdType() + pipe
//                    + commheaderFromDB.get(i).getCommandId() + pipe
//                    + commheaderFromDB.get(i).getManDispatch() + pipe
//                    + commheaderFromDB.get(i).getRelease() + pipe
//                    + commheaderFromDB.get(i).getRelTime() + pipe
//                    + commheaderFromDB.get(i).getRelTime2() + pipe
//                    + commheaderFromDB.get(i).getGroup() + pipe
//                    + commheaderFromDB.get(i).getBlock() + pipe
//                    + commheaderFromDB.get(i).getInterlock() + pipe
//                    + (commheaderFromDB.get(i).getIlStage() != null? commheaderFromDB.get(i).getIlStage():"") + pipe
//                    + commheaderFromDB.get(i).getStaticPTV() + pipe
//                    + commheaderFromDB.get(i).getDynamicPTV() + pipe
//                    + commheaderFromDB.get(i).getCev() + pipe
//                    + commheaderFromDB.get(i).getPars() + pipe
//                    + commheaderFromDB.get(i).getTimeTagged() + pipe
//                    + commheaderFromDB.get(i).getPlanned() + pipe
//                    + commheaderFromDB.get(i).getExecTime() + pipe
//                    + commheaderFromDB.get(i).getExecTime2() + pipe
//                    + (commheaderFromDB.get(i).getParent() != null? commheaderFromDB.get(i).getParent():"") + pipe
//                    + (commheaderFromDB.get(i).getStartTime() != null? commheaderFromDB.get(i).getStartTime():"") + pipe
//                    + (commheaderFromDB.get(i).getSubSystem() != null? commheaderFromDB.get(i).getSubSystem():"") + pipe
//                    + (commheaderFromDB.get(i).getSource() != null? commheaderFromDB.get(i).getSource():"") + pipe
//                    + (commheaderFromDB.get(i).getEarliest() != null? commheaderFromDB.get(i).getEarliest():"") + pipe
//                    + (commheaderFromDB.get(i).getLatest() != null? commheaderFromDB.get(i).getLatest():"") + pipe
//                    + (commheaderFromDB.get(i).getTcRequestId() != null? commheaderFromDB.get(i).getTcRequestId():"") + pipe
//                    + commheaderFromDB.get(i).getSubSchedId() + pipe
//                    + (commheaderFromDB.get(i).getAckFlags() != null? commheaderFromDB.get(i).getAckFlags():"") + pipe;
//            System.out.println("Command Header: " + commandHeader);
//            System.out.println("Command Parameter Task Name: " + commheaderFromDB.get(i).getSysTaskScheduled().getTaskName());
//            commandFile.add(commandHeader);
//            List<String> commandParameters = commandParameterLines(commheaderFromDB.get(i).getSysTaskScheduled(), commheaderFromDB.get(i));
//            /** <commandParameter.size() do the check when there are not parameters */
//            for(int j=0; j<commandParameters.size(); j++) {
//                commandFile.add(commandParameters.get(j));
//            }
//        }
        return commandFile;
    }

    public void createCommandParameterRecord(String[] commandparameterRow, SysTaskScheduled sysTaskScheduled, SysCommandHeader sysCommandHeader) {
        SysCommandParameter sysCommandParameter = new SysCommandParameter();
        //ADDED NOT IN THE FILE RECORD
        sysCommandParameter.setSysTaskScheduled(sysTaskScheduled);
        sysCommandParameter.setSysCommandHeader(sysCommandHeader);

        sysCommandParameter.setCommandParameterId(commandparameterRow[0]);
        sysCommandParameter.setFormpos(Integer.valueOf(commandparameterRow[1]));
        sysCommandParameter.setType(Integer.valueOf(commandparameterRow[2]));
        sysCommandParameter.setEditable(Integer.valueOf(commandparameterRow[3]));
        sysCommandParameter.setRepType(Integer.valueOf(commandparameterRow[4]));
        sysCommandParameter.setValue(commandparameterRow[5]);
        sysCommandParameter.setDynamic(Integer.valueOf(commandparameterRow[6]));

        if(missionplanRepository != null) {
            missionplanRepository.saveCommandParamenterRecord(sysCommandParameter);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public List<String> commandParameterLines(SysTaskScheduled sysTaskScheduled, String commandId) {
//        List<SysCommandParameter> commparamFromDB = missionplanRepository.commandParameterRecords(sysTaskScheduled, sysCommandHeader);
        List<SysCommandParameter> commparamFromDB = missionplanRepository.commandParametersForEachCommandHeader(sysTaskScheduled, commandId);
        List<String> commparamFile = new ArrayList<>();
        if(commparamFromDB.size() != 0) {
            for(int i=0; i<commparamFromDB.size(); i++) {
                String commandParam = commparamFromDB.get(i).getCommandParameterId() + pipe
                        + commparamFromDB.get(i).getFormpos() + pipe
                        + commparamFromDB.get(i).getType() + pipe
                        + commparamFromDB.get(i).getEditable() + pipe
                        + commparamFromDB.get(i).getRepType() + pipe
                        + commparamFromDB.get(i).getValue() + pipe
                        + commparamFromDB.get(i).getDynamic() + pipe;
                commparamFile.add(commandParam);
            }
        }
        return commparamFile;
    }

}
