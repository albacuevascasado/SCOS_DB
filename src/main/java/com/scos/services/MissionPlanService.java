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

/** BUSINESS LOGIC */
@NoArgsConstructor //REQUIRED TO BE ABLE TO INVOKE METHOD
@Setter
@Getter
public class MissionPlanService {

    static String pipe = "|";
//    static String new_line = "\n";

    @Autowired
    MissionPlanRepository missionplanRepository;

    public void createTaskScheduled() {
        TaskScheduled taskScheduled = new TaskScheduled();
        taskScheduled.setTaskName("AAAA");
        taskScheduled.setSchedulingId(BigInteger.valueOf(123L));

        if(missionplanRepository != null) {
            missionplanRepository.saveTaskScheduledRecord(taskScheduled);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public void createBaseHeaderRecord(String[] baseheaderRow) {
        BaseHeader baseHeader = new BaseHeader();
        //ADDED NOT IN THE RECORD FROM FILE
        baseHeader.setTaskName("AAAA");
        baseHeader.setSchedulingId(BigInteger.valueOf(123L));

        baseHeader.setCategory(Integer.valueOf(baseheaderRow[0]));
        baseHeader.setSource(baseheaderRow[1]);
        baseHeader.setGenTime(BigInteger.valueOf(Integer.valueOf(baseheaderRow[2])));
        baseHeader.setRelType(Integer.valueOf(baseheaderRow[3]));
        baseHeader.setVersion(baseheaderRow[4]);
        baseHeader.setStartTime(BigInteger.valueOf(Integer.valueOf(baseheaderRow[5])));

        if(missionplanRepository != null) {
            missionplanRepository.saveBaseHeaderRecord(baseHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public String baseHeaderLine(BigInteger schedulingId) {
        List<BaseHeader> baseheaderFromDB = missionplanRepository.baseHeaderRecord(schedulingId);
        String baseHeader = baseheaderFromDB.get(0).getCategory() + pipe
                + baseheaderFromDB.get(0).getSource() + pipe
                + baseheaderFromDB.get(0).getGenTime() + pipe
                + baseheaderFromDB.get(0).getRelType() + pipe
                + baseheaderFromDB.get(0).getVersion() + pipe
                + baseheaderFromDB.get(0).getStartTime() + pipe;

        System.out.println("Base Header: " + baseHeader);
        return baseHeader;
    }

    public void createSequenceHeaderRecord(String[] sequenceheaderRow) {
        SequenceHeader sequenceHeader = new SequenceHeader();
        //ADDED NOT IN THE FILE RECORD
        sequenceHeader.setTaskName("AAAA");

        sequenceHeader.setSequenceId(sequenceheaderRow[1]);
        sequenceHeader.setPars(Integer.valueOf(sequenceheaderRow[2]));
        sequenceHeader.setCmds(Integer.valueOf(sequenceheaderRow[3]));
        sequenceHeader.setStartTime(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[4])));
        sequenceHeader.setStartTime2(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[5])));

        sequenceHeader.setSubsystem(sequenceheaderRow[6].length() > 0?  Integer.valueOf(sequenceheaderRow[6]): null);
        sequenceHeader.setSource(sequenceheaderRow[7].length() > 0? Integer.valueOf(sequenceheaderRow[7]): null);
        sequenceHeader.setTcRequestId(sequenceheaderRow[8].length() > 0? Integer.valueOf(sequenceheaderRow[8]): null);

        sequenceHeader.setSubSchedId(BigInteger.valueOf(Integer.valueOf(sequenceheaderRow[9])));

        if(missionplanRepository != null) {
            missionplanRepository.saveSequenceHeaderRecord(sequenceHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public List<String> sequenceHeaderLine(String taskName) {
        List<SequenceHeader> seqheaderFromDB = missionplanRepository.sequenceHeaderRecords(taskName);
        List<String> sequenceFile = new ArrayList<>();
        for(int i=0; i<seqheaderFromDB.size(); i++) {
            String sequenceHeader = seqheaderFromDB.get(i).getSeqType() + pipe
                    + seqheaderFromDB.get(i).getSequenceId() + pipe
                    + seqheaderFromDB.get(i).getPars() + pipe
                    + seqheaderFromDB.get(i).getCmds() + pipe
                    + seqheaderFromDB.get(i).getStartTime() + pipe
                    + seqheaderFromDB.get(i).getStartTime2() + pipe
                    + (seqheaderFromDB.get(i).getSubsystem() != null? seqheaderFromDB.get(i).getSubsystem():"") + pipe
                    + (seqheaderFromDB.get(i).getSource() != null? seqheaderFromDB.get(i).getSource():"") + pipe
                    + (seqheaderFromDB.get(i).getTcRequestId() != null? seqheaderFromDB.get(i).getTcRequestId():"") + pipe
                    + seqheaderFromDB.get(i).getSubSchedId() + pipe;

            sequenceFile.add(sequenceHeader);
            List<String> sequenceParameters = sequenceParameterLines(seqheaderFromDB.get(i).getSequenceId());

            for(int j=0; j<sequenceParameters.size(); j++) {
                sequenceFile.add(sequenceParameters.get(j));
            }

            //commands? -> how to know which commands are inside the sequences / nested sequences
            //seqheader (CMDS) = there are commands + commheader(parent) = sequenceId
            if(seqheaderFromDB.get(i).getCmds() > 0) {
                List<String> seqCommands = commandHeaderLine(seqheaderFromDB.get(i).getSequenceId());
                for(int k=0; k<seqCommands.size(); k++) {
                    sequenceFile.add(seqCommands.get(k));
                }
            }

        }
        return sequenceFile;
    }

    public void createSequenceParameterRecord(String[] sequenceparameterRow) {
        SequenceParameter sequenceParameter = new SequenceParameter();
        //ADDED NOT IN THE FILE RECORD
        sequenceParameter.setSequenceId("SEQUENCE");

        sequenceParameter.setParameterId(sequenceparameterRow[0]);
        sequenceParameter.setType(Integer.valueOf(sequenceparameterRow[1]));
        sequenceParameter.setRepType(Integer.valueOf(sequenceparameterRow[2]));
        sequenceParameter.setValue(sequenceparameterRow[3]);

        if(missionplanRepository != null) {
            missionplanRepository.saveSequenceParameterRecord(sequenceParameter);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public List<String> sequenceParameterLines(String sequenceId) {
        List<SequenceParameter> seqparamFromDB = missionplanRepository.sequenceParameterRecords(sequenceId);
        List<String> seqparamFile = new ArrayList<>();
        for(int i=0; i<seqparamFromDB.size(); i++) {
            String sequenceParam = seqparamFromDB.get(i).getParameterId() + pipe
                    + seqparamFromDB.get(i).getType() + pipe
                    + seqparamFromDB.get(i).getRepType() + pipe
                    + seqparamFromDB.get(i).getValue() + pipe;

            seqparamFile.add(sequenceParam);
        }
        return seqparamFile;
    }

    public void createCommandHeaderRecord(String[] commandheaderRow) {
        CommandHeader commandHeader = new CommandHeader();
        //ADDED NOT IN THE FILE RECORD
        commandHeader.setTaskName("AAAA");

        commandHeader.setCmdType(CommandHeader.Cmdtype.valueOf(commandheaderRow[0]));
        commandHeader.setCommandId(commandheaderRow[1]);
        commandHeader.setManDispatch(Integer.valueOf(commandheaderRow[2]));
        commandHeader.setRelease(Integer.valueOf(commandheaderRow[3]));
        commandHeader.setRelTime(BigInteger.valueOf(Integer.valueOf(commandheaderRow[4])));
        commandHeader.setRelTime2(BigInteger.valueOf(Integer.valueOf(commandheaderRow[5])));
        commandHeader.setGroup(Integer.valueOf(commandheaderRow[6]));
        commandHeader.setBlock(Integer.valueOf(commandheaderRow[7]));
        commandHeader.setInterlock(Integer.valueOf(commandheaderRow[8]));
        commandHeader.setIlStage(commandheaderRow[9].length() > 0? commandheaderRow[9]:null);
        commandHeader.setStaticPTV(Integer.valueOf(commandheaderRow[10]));
        commandHeader.setDynamicPTV(Integer.valueOf(commandheaderRow[11]));
        commandHeader.setCev(Integer.valueOf(commandheaderRow[12]));
        commandHeader.setPars(BigInteger.valueOf(Integer.valueOf(commandheaderRow[13])));
        commandHeader.setTimeTagged(Integer.valueOf(commandheaderRow[14]));
        commandHeader.setPlanned(Integer.valueOf(commandheaderRow[15]));
        commandHeader.setExecTime(BigInteger.valueOf(Integer.valueOf(commandheaderRow[16])));
        commandHeader.setExecTime2(BigInteger.valueOf(Integer.valueOf(commandheaderRow[17])));
        commandHeader.setParent(commandheaderRow[18].length() > 0? commandheaderRow[18]:null);
        commandHeader.setStartTime(commandheaderRow[19].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[19])):null);
        commandHeader.setSubSystem(commandheaderRow[20].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[20])):null);
        commandHeader.setSource(commandheaderRow[21].length() > 0? Integer.valueOf(commandheaderRow[21]):null);
        commandHeader.setEarliest(commandheaderRow[22].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[22])):null);
        commandHeader.setLatest(commandheaderRow[23].length() > 0? BigInteger.valueOf(Integer.valueOf(commandheaderRow[23])):null);
        commandHeader.setTcRequestId(commandheaderRow[24].length() > 0? Integer.valueOf(commandheaderRow[24]):null);
        commandHeader.setSubSchedId(BigInteger.valueOf(Integer.valueOf(commandheaderRow[25])));
        commandHeader.setAckFlags(commandheaderRow.length > 26? Integer.valueOf(commandheaderRow[26]):null);

        if(missionplanRepository != null) {
            missionplanRepository.saveCommandHeaderRecord(commandHeader);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public List<String> commandHeaderLine(String taskName) {
        List<CommandHeader> commheaderFromDB = missionplanRepository.commandHeaderRecords(taskName);
        List<String> commandFile = new ArrayList<>();
        /** i<commheaderFromDb.size() do the check when there is not a header */
        for(int i=0; i<commheaderFromDB.size(); i++) {
            String commandHeader = commheaderFromDB.get(i).getCmdType() + pipe
                    + commheaderFromDB.get(i).getCommandId() + pipe
                    + commheaderFromDB.get(i).getManDispatch() + pipe
                    + commheaderFromDB.get(i).getRelease() + pipe
                    + commheaderFromDB.get(i).getRelTime() + pipe
                    + commheaderFromDB.get(i).getRelTime2() + pipe
                    + commheaderFromDB.get(i).getGroup() + pipe
                    + commheaderFromDB.get(i).getBlock() + pipe
                    + commheaderFromDB.get(i).getInterlock() + pipe
                    + (commheaderFromDB.get(i).getIlStage() != null? commheaderFromDB.get(i).getIlStage():"") + pipe
                    + commheaderFromDB.get(i).getStaticPTV() + pipe
                    + commheaderFromDB.get(i).getDynamicPTV() + pipe
                    + commheaderFromDB.get(i).getCev() + pipe
                    + commheaderFromDB.get(i).getPars() + pipe
                    + commheaderFromDB.get(i).getTimeTagged() + pipe
                    + commheaderFromDB.get(i).getPlanned() + pipe
                    + commheaderFromDB.get(i).getExecTime() + pipe
                    + commheaderFromDB.get(i).getExecTime2() + pipe
                    + (commheaderFromDB.get(i).getParent() != null? commheaderFromDB.get(i).getParent():"") + pipe
                    + (commheaderFromDB.get(i).getStartTime() != null? commheaderFromDB.get(i).getStartTime():"") + pipe
                    + (commheaderFromDB.get(i).getSubSystem() != null? commheaderFromDB.get(i).getSubSystem():"") + pipe
                    + (commheaderFromDB.get(i).getSource() != null? commheaderFromDB.get(i).getSource():"") + pipe
                    + (commheaderFromDB.get(i).getEarliest() != null? commheaderFromDB.get(i).getEarliest():"") + pipe
                    + (commheaderFromDB.get(i).getLatest() != null? commheaderFromDB.get(i).getLatest():"") + pipe
                    + (commheaderFromDB.get(i).getTcRequestId() != null? commheaderFromDB.get(i).getTcRequestId():"") + pipe
                    + commheaderFromDB.get(i).getSubSchedId() + pipe
                    + (commheaderFromDB.get(i).getAckFlags() != null? commheaderFromDB.get(i).getAckFlags():"") + pipe;
            System.out.println("Command Header: " + commandHeader);
            commandFile.add(commandHeader);
            List<String> commandParameters = commandParameterLines(commheaderFromDB.get(i).getCommandId());
            /** j<commandParameter.size() do the check when there are not parameters */
            for(int j=0; j<commandParameters.size(); j++) {
                commandFile.add(commandParameters.get(j));
            }
        }
        return commandFile;
    }

    public void createCommandParameterRecord(String[] commandparameterRow) {
        CommandParameter commandParameter = new CommandParameter();
        //ADDED NOT IN THE FILE RECORD
        commandParameter.setCommandId("HUA50201");

        commandParameter.setParameterId(commandparameterRow[0]);
        commandParameter.setFormpos(Integer.valueOf(commandparameterRow[1]));
        commandParameter.setType(Integer.valueOf(commandparameterRow[2]));
        commandParameter.setEditable(Integer.valueOf(commandparameterRow[3]));
        commandParameter.setRepType(Integer.valueOf(commandparameterRow[4]));
        commandParameter.setValue(commandparameterRow[5]);
        commandParameter.setDynamic(Integer.valueOf(commandparameterRow[6]));

        if(missionplanRepository != null) {
            missionplanRepository.saveCommandParamenterRecord(commandParameter);
        } else {
            System.out.println("missionplanRepository has not been injected");
        }
    }

    public List<String> commandParameterLines(String commandId) {
        List<CommandParameter> commparamFromDB = missionplanRepository.commandParameterRecords(commandId);
        List<String> commparamFile = new ArrayList<>();
        if(commparamFromDB.size() != 0) {
            for(int i=0; i<commparamFromDB.size(); i++) {
                String commandParam = commparamFromDB.get(i).getParameterId() + pipe
                        + commparamFromDB.get(i).getFormpos() + pipe
                        + commparamFromDB.get(i).getType() + pipe
                        + commparamFromDB.get(i).getEditable() + pipe
                        + commparamFromDB.get(i).getRepType() + pipe
                        + commparamFromDB.get(i).getValue() + pipe
                        + commparamFromDB.get(i).getDynamic() + pipe;
                System.out.println("Command Parameter: " + commandParam);
                commparamFile.add(commandParam);
            }
        }
        return commparamFile;
    }

}