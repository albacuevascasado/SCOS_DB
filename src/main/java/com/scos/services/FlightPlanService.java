package com.scos.services;

import com.scos.XSDToJava3.*;
import com.scos.data_model.mps_db.*;
import com.scos.repositories.FlightPlanRepository;
import com.sun.xml.internal.fastinfoset.util.StringArray;
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
public class FlightPlanService {

    static String pipe = "|";

    @Autowired
    FlightPlanRepository flightplanRepository;

    /** CREATE XML FROM FILE */

    public BaseHeader createBaseHeader(String[] baseHeaderRow) {
        //BASEHEADER
        BaseHeader baseHeader = new BaseHeader();

        for(int i = 0; i< SysBaseHeader.COLUMNS.values().length; i++) {
            BaseHeader.Field baseHeaderField = new BaseHeader.Field();
            baseHeaderField.setName(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getName())); //CATEGORY|SOURCE|GEN_TIME|RELTYPE|VERSION|START_TIME
            baseHeaderField.setValue(baseHeaderRow[i]);
            baseHeaderField.setType(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getType()));
            if(SysBaseHeader.COLUMNS.values()[i].getUnits() != null) {
                baseHeaderField.setUnits(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getUnits()));
            }
            baseHeader.getField().add(baseHeaderField);
        }
//        //CATEGORY
//        BaseHeader.Field baseHeaderCategory = new BaseHeader.Field();
//        baseHeaderCategory.setName("CATEGORY");
//        baseHeaderCategory.setValue(baseHeaderRow[0]);
//        baseHeaderCategory.setType("number");
//        baseHeader.getField().add(baseHeaderCategory);
//        //SOURCE
//        BaseHeader.Field baseHeaderSource = new BaseHeader.Field();
//        baseHeaderSource.setName("SOURCE");
//        baseHeaderSource.setValue(baseHeaderRow[1]);
//        baseHeaderSource.setType("char");
//        baseHeader.getField().add(baseHeaderSource);
//        //GEN TIME
//        BaseHeader.Field baseHeaderGenTime = new BaseHeader.Field();
//        baseHeaderGenTime.setName("GEN_TIME");
//        baseHeaderGenTime.setValue(baseHeaderRow[2]);
//        baseHeaderGenTime.setType("number");
//        baseHeaderGenTime.setUnits("seconds");
//        baseHeader.getField().add(baseHeaderGenTime);
//        //RELTYPE
//        BaseHeader.Field baseHeaderFieldRelType = new BaseHeader.Field();
//        baseHeaderFieldRelType.setName("RELTYPE");
//        baseHeaderFieldRelType.setValue(baseHeaderRow[3]);
//        baseHeaderFieldRelType.setType("number");
//        baseHeader.getField().add(baseHeaderFieldRelType);
//        //VERSION
//        BaseHeader.Field baseHeaderFieldVersion = new BaseHeader.Field();
//        baseHeaderFieldVersion.setName("VERSION");
//        baseHeaderFieldVersion.setValue(baseHeaderRow[4]);
//        baseHeaderFieldVersion.setType("char");
//        baseHeader.getField().add(baseHeaderFieldVersion);
//        //START TIME
//        BaseHeader.Field baseHeaderFieldStartTime = new BaseHeader.Field();
//        baseHeaderFieldStartTime.setName("START_TIME");
//        baseHeaderFieldStartTime.setValue(baseHeaderRow[5]);
//        baseHeaderFieldStartTime.setType("number");
//        baseHeaderFieldStartTime.setUnits("seconds");
//        baseHeader.getField().add(baseHeaderFieldStartTime);

        return baseHeader;
    }

    public CommandHeader createCommandHeader(String[] commandHeaderRow) {
        /** check if position 13 > 0 -> if it is true call method commParam */
        //COMMAND HEADER
        CommandHeader commandHeader = new CommandHeader();
        //commandHeaderRow.length because the LAST INDEX IS OPTIONAL
        for(int i=0; i< commandHeaderRow.length; i++) {
            //optional fields commandHeaderRow[i].length() > 0
            if(commandHeaderRow[i].length() > 0) {
                CommandHeader.Field commandHeaderField = new CommandHeader.Field();
                commandHeaderField.setName(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getName()));
                commandHeaderField.setValue(commandHeaderRow[i]);
                commandHeaderField.setType(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getType()));
                if(SysCommandHeader.COLUMNS.values()[i].getUnits() != null) {
                    commandHeaderField.setUnits(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getUnits()));
                }
                commandHeader.getField().add(commandHeaderField);
            }
        }

//        //CMDTYPE
//        CommandHeader.Field commHeaderCmdType = new CommandHeader.Field();
//        commHeaderCmdType.setName("CMDTYPE");
//        commHeaderCmdType.setValue(commandHeaderRow[0]);
//        commHeaderCmdType.setType("char");
//        commandHeader.getField().add(commHeaderCmdType);
//        //ID
//        CommandHeader.Field commHeaderId = new CommandHeader.Field();
//        commHeaderId.setName("ID");
//        commHeaderId.setValue(commandHeaderRow[1]);
//        commHeaderId.setType("char");
//        commandHeader.getField().add(commHeaderId);
//        //MAN DISPATCH
//        CommandHeader.Field commHeaderManDispatch = new CommandHeader.Field();
//        commHeaderManDispatch.setName("MAN DISPATCH");
//        commHeaderManDispatch.setValue(commandHeaderRow[2]);
//        commHeaderManDispatch.setType("number");
//        commandHeader.getField().add(commHeaderManDispatch);
//        //RELEASE
//        CommandHeader.Field commHeaderRelease = new CommandHeader.Field();
//        commHeaderRelease.setName("RELEASE");
//        commHeaderRelease.setValue(commandHeaderRow[3]);
//        commHeaderRelease.setType("number");
//        commandHeader.getField().add(commHeaderRelease);
//        //RELTIME
//        CommandHeader.Field commHeaderRelTime = new CommandHeader.Field();
//        commHeaderRelTime.setName("RELTIME");
//        commHeaderRelTime.setValue(commandHeaderRow[4]);
//        commHeaderRelTime.setType("number");
//        commHeaderRelTime.setUnits("seconds");
//        commandHeader.getField().add(commHeaderRelTime);
//        //RELTIME2
//        CommandHeader.Field commHeaderRelTime2 = new CommandHeader.Field();
//        commHeaderRelTime2.setName("RELTIME2");
//        commHeaderRelTime2.setValue(commandHeaderRow[5]);
//        commHeaderRelTime2.setType("number");
//        commHeaderRelTime2.setUnits("microseconds");
//        commandHeader.getField().add(commHeaderRelTime2);
//        //GROUP
//        CommandHeader.Field commHeaderGroup = new CommandHeader.Field();
//        commHeaderGroup.setName("GROUP");
//        commHeaderGroup.setValue(commandHeaderRow[6]);
//        commHeaderGroup.setType("number");
//        commandHeader.getField().add(commHeaderGroup);
//        //BLOCK
//        CommandHeader.Field commHeaderBlock = new CommandHeader.Field();
//        commHeaderBlock.setName("BLOCK");
//        commHeaderBlock.setValue(commandHeaderRow[7]);
//        commHeaderBlock.setType("number");
//        commandHeader.getField().add(commHeaderBlock);
//        //INTERLOCK
//        CommandHeader.Field commHeaderInterlock = new CommandHeader.Field();
//        commHeaderInterlock.setName("INTERLOCK");
//        commHeaderInterlock.setValue(commandHeaderRow[8]);
//        commHeaderInterlock.setType("number");
//        commandHeader.getField().add(commHeaderInterlock);
//        //ILSTAGE
//        if(commandHeaderRow[9].length() > 0) {
//            CommandHeader.Field commHeaderIlstage = new CommandHeader.Field();
//            commHeaderIlstage.setName("ILSTAGE");
//            commHeaderIlstage.setValue(commandHeaderRow[9]);
//            commHeaderIlstage.setType("char");
//            commandHeader.getField().add(commHeaderIlstage);
//        }
//        //STATIC PTV
//        CommandHeader.Field commHeaderStaticPTV = new CommandHeader.Field();
//        commHeaderStaticPTV.setName("STATIC PTV");
//        commHeaderStaticPTV.setValue(commandHeaderRow[10]);
//        commHeaderStaticPTV.setType("number");
//        commandHeader.getField().add(commHeaderStaticPTV);
//        //DYNAMIC PTV
//        CommandHeader.Field commHeaderDynamicPTV = new CommandHeader.Field();
//        commHeaderDynamicPTV.setName("DYNAMIC PTV");
//        commHeaderDynamicPTV.setValue(commandHeaderRow[11]);
//        commHeaderDynamicPTV.setType("number");
//        commandHeader.getField().add(commHeaderDynamicPTV);
//        //CEV
//        CommandHeader.Field commHeaderCev = new CommandHeader.Field();
//        commHeaderCev.setName("CEV");
//        commHeaderCev.setValue(commandHeaderRow[12]);
//        commHeaderCev.setType("number");
//        commandHeader.getField().add(commHeaderCev);
//        //PARS
//        /** CHECK VALUE */
//        CommandHeader.Field commHeaderPars = new CommandHeader.Field();
//        commHeaderPars.setName("PARS");
//        commHeaderPars.setValue(commandHeaderRow[13]);
//        commHeaderPars.setType("number");
//        commandHeader.getField().add(commHeaderPars);
//        //TIME TAGGED
//        CommandHeader.Field commHeaderTimeTagged = new CommandHeader.Field();
//        commHeaderTimeTagged.setName("TIME TAGGED");
//        commHeaderTimeTagged.setValue(commandHeaderRow[14]);
//        commHeaderTimeTagged.setType("number");
//        commandHeader.getField().add(commHeaderTimeTagged);
//        //PLANNED
//        CommandHeader.Field commHeaderPlanned = new CommandHeader.Field();
//        commHeaderPlanned.setName("PLANNED");
//        commHeaderPlanned.setValue(commandHeaderRow[15]);
//        commHeaderPlanned.setType("number");
//        commandHeader.getField().add(commHeaderPlanned);
//        //EXEC TIME
//        CommandHeader.Field commHeaderExecTime = new CommandHeader.Field();
//        commHeaderExecTime.setName("EXEC TIME");
//        commHeaderExecTime.setValue(commandHeaderRow[16]);
//        commHeaderExecTime.setType("number");
//        commHeaderExecTime.setUnits("seconds");
//        commandHeader.getField().add(commHeaderExecTime);
//        //EXEC TIME2
//        CommandHeader.Field commHeaderExecTime2 = new CommandHeader.Field();
//        commHeaderExecTime2.setName("EXEC TIME2");
//        commHeaderExecTime2.setValue(commandHeaderRow[17]);
//        commHeaderExecTime2.setType("number");
//        commHeaderExecTime2.setUnits("microseconds");
//        commandHeader.getField().add(commHeaderExecTime2);
//        //PARENT -> OPTIONAL
//        if(commandHeaderRow[18].length() > 0) {
//            CommandHeader.Field commHeaderParent = new CommandHeader.Field();
//            commHeaderParent.setName("PARENT");
//            commHeaderParent.setValue(commandHeaderRow[18]);
//            commHeaderParent.setType("char");
//            commandHeader.getField().add(commHeaderParent);
//        }
//        //STARTTIME -> OPTIONAL
//        if(commandHeaderRow[19].length() > 0) {
//            CommandHeader.Field commHeaderStartTime = new CommandHeader.Field();
//            commHeaderStartTime.setName("STARTTIME");
//            commHeaderStartTime.setValue(commandHeaderRow[19]);
//            commHeaderStartTime.setType("number");
//            commandHeader.getField().add(commHeaderStartTime);
//        }
//        //SUBSYSTEM -> OPTIONAL
//        if(commandHeaderRow[20].length() > 0) {
//            CommandHeader.Field commHeaderSubSystem = new CommandHeader.Field();
//            commHeaderSubSystem.setName("SUBSYSTEM");
//            commHeaderSubSystem.setValue(commandHeaderRow[20]);
//            commHeaderSubSystem.setType("number");
//            commandHeader.getField().add(commHeaderSubSystem);
//        }
//        //SOURCE -> OPTIONAL
//        if(commandHeaderRow[21].length() > 0) {
//            CommandHeader.Field commHeaderSource = new CommandHeader.Field();
//            commHeaderSource.setName("SOURCE");
//            commHeaderSource.setValue(commandHeaderRow[21]);
//            commHeaderSource.setType("number");
//            commandHeader.getField().add(commHeaderSource);
//        }
//        //EARLIEST -> OPTIONAL
//        if(commandHeaderRow[22].length() > 0) {
//            CommandHeader.Field commHeaderEarliest = new CommandHeader.Field();
//            commHeaderEarliest.setName("EARLIEST");
//            commHeaderEarliest.setValue(commandHeaderRow[22]);
//            commHeaderEarliest.setType("number");
//            commandHeader.getField().add(commHeaderEarliest);
//        }
//        //LATEST -> OPTIONAL
//        if(commandHeaderRow[23].length() > 0) {
//            CommandHeader.Field commHeaderLatest = new CommandHeader.Field();
//            commHeaderLatest.setName("LATEST");
//            commHeaderLatest.setValue(commandHeaderRow[23]);
//            commHeaderLatest.setType("number");
//            commandHeader.getField().add(commHeaderLatest);
//        }
//        //TC REQUEST ID -> OPTIONAL
//        if(commandHeaderRow[24].length() > 0) {
//            CommandHeader.Field commHeaderTCRequestId = new CommandHeader.Field();
//            commHeaderTCRequestId.setName("TC REQUEST ID");
//            commHeaderTCRequestId.setValue(commandHeaderRow[24]);
//            commHeaderTCRequestId.setType("number");
//            commandHeader.getField().add(commHeaderTCRequestId);
//        }
//        //SUB-SCHED ID
//        CommandHeader.Field commHeaderSubSchedId = new CommandHeader.Field();
//        commHeaderSubSchedId.setName("SUB-SCHED ID");
//        commHeaderSubSchedId.setValue(commandHeaderRow[25]);
//        commHeaderSubSchedId.setType("number");
//        commandHeader.getField().add(commHeaderSubSchedId);
//        //ACK-FLAGS -> OPTIONAL
//        if(commandHeaderRow.length > 26) {
//            CommandHeader.Field commHeaderAckFlags = new CommandHeader.Field();
//            commHeaderAckFlags.setName("ACK-FLAGS");
//            //control when last position is EMPTY
//            //commHeaderAckFlags.setValue(commandHeaderRow.length > 26 ? commandHeaderRow[26] : "" );
//            commHeaderAckFlags.setValue(commandHeaderRow[26]);
//            commHeaderAckFlags.setType("number");
//            commandHeader.getField().add(commHeaderAckFlags);
//        }

        return commandHeader;
    }

    public CommandParameter createCommandParameter(String[] commandParameterRow) {
        //COMMAND PARAMETER
        CommandParameter commandParameter = new CommandParameter();
        //commandParameterRow.length because the LAST INDEX IS OPTIONAL
        for(int i=0; i< commandParameterRow.length; i++) {
            //optional fields commandParameterRow[i].length() > 0
            if(commandParameterRow[i].length() > 0) {
                CommandParameter.Field commandParameterField = new CommandParameter.Field();
                commandParameterField.setName(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getName()));
                commandParameterField.setValue(commandParameterRow[i]);
                commandParameterField.setType(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getType()));
                if(SysCommandParameter.COLUMNS.values()[i].getUnits() != null) {
                    commandParameterField.setUnits(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getUnits()));
                }
                commandParameter.getField().add(commandParameterField);
            }
        }

//        //ID
//        CommandParameter.Field commParamID = new CommandParameter.Field();
//        commParamID.setName("ID");
//        commParamID.setValue(commandParameterRow[0]);
//        commParamID.setType("char");
//        commandParameter.getField().add(commParamID);
//        //FORMPOS
//        CommandParameter.Field commParamFormPos = new CommandParameter.Field();
//        commParamFormPos.setName("FORMPOS");
//        commParamFormPos.setValue(commandParameterRow[1]);
//        commParamFormPos.setType("number");
//        commandParameter.getField().add(commParamFormPos);
//        //TYPE
//        CommandParameter.Field commParamType = new CommandParameter.Field();
//        commParamType.setName("TYPE");
//        commParamType.setValue(commandParameterRow[2]);
//        commParamType.setType("number");
//        commandParameter.getField().add(commParamType);
//        //EDITABLE
//        CommandParameter.Field commParamEditable = new CommandParameter.Field();
//        commParamEditable.setName("EDITABLE");
//        commParamEditable.setValue(commandParameterRow[3]);
//        commParamEditable.setType("number");
//        commandParameter.getField().add(commParamEditable);
//        //REPTYPE
//        CommandParameter.Field commParamRepType = new CommandParameter.Field();
//        commParamRepType.setName("REPTYPE");
//        commParamRepType.setValue(commandParameterRow[4]);
//        commParamRepType.setType("number");
//        commandParameter.getField().add(commParamRepType);
//        //VALUE -> OPTIONAL
//        if(commandParameterRow[5].length() > 0) {
//            CommandParameter.Field commParamValue = new CommandParameter.Field();
//            commParamValue.setName("VALUE");
//            //commParamValue.setValue(commandParameterRow[5].length() > 0 ? commandParameterRow[5] : "");
//            commParamValue.setValue(commandParameterRow[5]);
//            commParamValue.setType("char");
//            commandParameter.getField().add(commParamValue);
//        }
//        //DYNAMIC -> OPTIONAL
//        if(commandParameterRow[6].length() > 0) {
//            CommandParameter.Field commParamDynamic = new CommandParameter.Field();
//            commParamDynamic.setName("DYNAMIC");
//            //commParamDynamic.setValue(commandParameterRow.length > 6 ? commandParameterRow[6] : "");
//            commParamDynamic.setValue(commandParameterRow[6]);
//            commParamDynamic.setType("number");
//            commandParameter.getField().add(commParamDynamic);
//        }

        return commandParameter;
    }

    public SequenceHeader createSequenceHeader(String[] sequenceHeaderRow) {
        /** check if position 3 > 0 -> if it is true call method seqParam */
        //SEQUENCE HEADER
        SequenceHeader sequenceHeader = new SequenceHeader();

        for(int i = 0; i < SysSequenceHeader.COLUMNS.values().length; i++) {
            //optional fields sequenceHeaderRow[i].length() > 0
            if(sequenceHeaderRow[i].length() > 0) {
                SequenceHeader.Field sequenceHeaderField = new SequenceHeader.Field();
                sequenceHeaderField.setName(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getName()));
                sequenceHeaderField.setValue(sequenceHeaderRow[i]);
                sequenceHeaderField.setType(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getType()));
                if(SysSequenceHeader.COLUMNS.values()[i].getUnits() != null) {
                    sequenceHeaderField.setUnits(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getUnits()));
                }
                sequenceHeader.getField().add(sequenceHeaderField);
            }
        }
//        //SEQ TYPE
//        SequenceHeader.Field seqHeaderSeqType = new SequenceHeader.Field();
//        seqHeaderSeqType.setName("SEQ TYPE");
//        seqHeaderSeqType.setValue(sequenceHeaderRow[0]);
//        seqHeaderSeqType.setType("char");
//        sequenceHeader.getField().add(seqHeaderSeqType);
//        //ID
//        SequenceHeader.Field seqHeaderId = new SequenceHeader.Field();
//        seqHeaderId.setName("ID");
//        seqHeaderId.setValue(sequenceHeaderRow[1]);
//        seqHeaderId.setType("char");
//        sequenceHeader.getField().add(seqHeaderId);
//        //PARS
//        SequenceHeader.Field seqHeaderPars = new SequenceHeader.Field();
//        seqHeaderPars.setName("PARS");
//        seqHeaderPars.setValue(sequenceHeaderRow[2]);
//        seqHeaderPars.setType("number");
//        sequenceHeader.getField().add(seqHeaderPars);
//        //CMDS
//        /** number of commands in the sequence */
//        SequenceHeader.Field seqHeaderCmds = new SequenceHeader.Field();
//        seqHeaderCmds.setName("CMDS");
//        seqHeaderCmds.setValue(sequenceHeaderRow[3]);
//        seqHeaderCmds.setType("number");
//        sequenceHeader.getField().add(seqHeaderCmds);
//        //STARTTIME
//        SequenceHeader.Field seqHeaderStartTime = new SequenceHeader.Field();
//        seqHeaderStartTime.setName("STARTTIME");
//        seqHeaderStartTime.setValue(sequenceHeaderRow[4]);
//        seqHeaderStartTime.setType("number");
//        seqHeaderStartTime.setUnits("seconds");
//        sequenceHeader.getField().add(seqHeaderStartTime);
//        //STARTTIME2
//        SequenceHeader.Field seqHeaderStartTime2 = new SequenceHeader.Field();
//        seqHeaderStartTime2.setName("STARTTIME2");
//        seqHeaderStartTime2.setValue(sequenceHeaderRow[5]);
//        seqHeaderStartTime2.setType("number");
//        seqHeaderStartTime.setUnits("microseconds");
//        sequenceHeader.getField().add(seqHeaderStartTime2);
//        //SUBSYSTEM -> OPTIONAL
//        if(sequenceHeaderRow[6].length() > 0) {
//            SequenceHeader.Field seqHeaderSubsystem = new SequenceHeader.Field();
//            seqHeaderSubsystem.setName("SUBSYSTEM");
//            seqHeaderSubsystem.setValue(sequenceHeaderRow[6]);
//            seqHeaderSubsystem.setType("number");
//            sequenceHeader.getField().add(seqHeaderSubsystem);
//        }
//        //SOURCE -> OPTIONAL
//        if(sequenceHeaderRow[7].length() > 0) {
//            SequenceHeader.Field seqHeaderSource = new SequenceHeader.Field();
//            seqHeaderSource.setName("SOURCE");
//            seqHeaderSource.setValue(sequenceHeaderRow[7]);
//            seqHeaderSource.setType("number");
//            sequenceHeader.getField().add(seqHeaderSource);
//        }
//        //TC REQUEST ID -> OPTIONAL
//        if(sequenceHeaderRow[8].length() > 0) {
//            SequenceHeader.Field seqHeaderTCRequestId = new SequenceHeader.Field();
//            seqHeaderTCRequestId.setName("TC REQUEST ID");
//            seqHeaderTCRequestId.setValue(sequenceHeaderRow[8]);
//            seqHeaderTCRequestId.setType("number");
//            sequenceHeader.getField().add(seqHeaderTCRequestId);
//        }
//        //SUB-SCHED ID -> MANDATORY
//        SequenceHeader.Field seqHeaderSubSchedId = new SequenceHeader.Field();
//        seqHeaderSubSchedId.setName("SUB-SCHED ID");
//        seqHeaderSubSchedId.setValue(sequenceHeaderRow[9]);
//        seqHeaderSubSchedId.setType("number");
//        sequenceHeader.getField().add(seqHeaderSubSchedId);

        return sequenceHeader;
    }

    public SequenceParameter createSequenceParameter(String[] sequenceParameterRow) {
        //SEQUENCE PARAMETER
        SequenceParameter sequenceParameter = new SequenceParameter();

        for(int i = 0; i < SysSequenceParameter.COLUMNS.values().length; i++) {
            SequenceParameter.Field sequenceParameterField = new SequenceParameter.Field();
            sequenceParameterField.setName(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getName()));
            sequenceParameterField.setValue(sequenceParameterRow[i]);
            sequenceParameterField.setType(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getType()));
            if(SysSequenceParameter.COLUMNS.values()[i].getUnits() != null) {
                sequenceParameterField.setUnits(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getUnits()));
            }
            sequenceParameter.getField().add(sequenceParameterField);
        }
//        //ID
//        SequenceParameter.Field seqParamId = new SequenceParameter.Field();
//        seqParamId.setName("ID");
//        seqParamId.setValue(sequenceParameterRow[0]);
//        seqParamId.setType("char");
//        sequenceParameter.getField().add(seqParamId);
//        //TYPE
//        SequenceParameter.Field seqParamType = new SequenceParameter.Field();
//        seqParamType.setName("TYPE");
//        seqParamType.setValue(sequenceParameterRow[1]);
//        seqParamType.setType("number");
//        sequenceParameter.getField().add(seqParamType);
//        //REPTYPE
//        SequenceParameter.Field seqParamRepType = new SequenceParameter.Field();
//        seqParamRepType.setName("REPTYPE");
//        seqParamRepType.setType(sequenceParameterRow[2]);
//        seqParamRepType.setType("number");
//        sequenceParameter.getField().add(seqParamRepType);
//        //VALUE
//        SequenceParameter.Field seqParamValue = new SequenceParameter.Field();
//        seqParamValue.setName("VALUE");
//        seqParamValue.setType(sequenceParameterRow[3]);
//        seqParamValue.setType("char");
//        sequenceParameter.getField().add(seqParamValue);

        return sequenceParameter;
    }

    /** CREATE XML FROM DB */

    public SysSchedulingProva createSysSchedulingProva() {
        SysSchedulingProva sysSchedulingProva = new SysSchedulingProva();
        sysSchedulingProva.setSchedulingType(SysSchedulingProva.SchedulingType.MANUAL);

        if(flightplanRepository != null) {
            flightplanRepository.saveSysSchedulingProva(sysSchedulingProva);
        } else {
            System.out.println("flightplanRepository has not been injected");
        }

        return sysSchedulingProva;
    }

    //TO KNOW WHICH IS THE SCHEDULING_ID -> ONE RESULT (QUERY)
    public SysSchedulingProva searchSchedulingProva(BigInteger schedulingId) {
        SysSchedulingProva schedulingProvaFromDB = flightplanRepository.schedulingProvaRecord(schedulingId);
        return schedulingProvaFromDB;
    }

    public List<SysTaskScheduled> sysTaskScheduled(SysSchedulingProva sysSchedulingProva) {
        List<SysTaskScheduled> sysTaskScheduledList = flightplanRepository.taskScheduledRecords(sysSchedulingProva);
        return sysTaskScheduledList;
    }

    public BaseHeader createBaseHeaderFromDB(SysSchedulingProva sysSchedulingProva) {
        SysBaseHeader sysBaseHeader = flightplanRepository.baseHeaderRecord(sysSchedulingProva);
        //TODO TRY TO CONVERT TO STRING[]
//        String baseheaderString = sysBaseHeader.toString();
//        System.out.println("Base Header String[]: " + baseheaderString);
        List<String> sysBaseHeaderValues = new ArrayList<>();

        sysBaseHeaderValues.add(0,String.valueOf(sysBaseHeader.getCategory()));
        sysBaseHeaderValues.add(1,sysBaseHeader.getSource());
        sysBaseHeaderValues.add(2,String.valueOf(sysBaseHeader.getGenTime()));
        sysBaseHeaderValues.add(3,String.valueOf(sysBaseHeader.getRelType()));
        sysBaseHeaderValues.add(4,sysBaseHeader.getVersion());
        sysBaseHeaderValues.add(5,String.valueOf(sysBaseHeader.getStartTime()));

        //BASEHEADER
        BaseHeader baseHeader = new BaseHeader();

        for(int i = 0; i< SysBaseHeader.COLUMNS.values().length; i++) {
            BaseHeader.Field baseHeaderField = new BaseHeader.Field();
            baseHeaderField.setName(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getName())); //CATEGORY|SOURCE|GEN_TIME|RELTYPE|VERSION|START_TIME
            baseHeaderField.setValue(sysBaseHeaderValues.get(i));
            baseHeaderField.setType(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getType()));
            if(SysBaseHeader.COLUMNS.values()[i].getUnits() != null) {
                baseHeaderField.setUnits(String.valueOf(SysBaseHeader.COLUMNS.values()[i].getUnits()));
            }
            baseHeader.getField().add(baseHeaderField);
        }
        return baseHeader;
    }

    public  List<Sequence> createSequenceFromDB(SysTaskScheduled sysTaskScheduled) {
        //SEQUENCE
        List<Sequence> sequenceList = new ArrayList<>();

        //SEQUENCE HEADER
        List<SysSequenceHeader> seqheaderFromDB = flightplanRepository.sequenceHeaderRecords(sysTaskScheduled);
        boolean seqparamExists = false;

        for(SysSequenceHeader sysSequenceHeaderRecord: seqheaderFromDB) {
            Sequence sequence = new Sequence();
            //TODO TRY TO CONVERT TO STRING[]
            List<String> sysSequenceHeaderValues = new ArrayList<>(); //values FOR EACH FIELD

            sysSequenceHeaderValues.add(0,String.valueOf(sysSequenceHeaderRecord.getSeqType()));
            sysSequenceHeaderValues.add(1,String.valueOf(sysSequenceHeaderRecord.getSequenceId())); //It is a string and NEVER NULL -> String.valueOf ?
            sysSequenceHeaderValues.add(2,String.valueOf(sysSequenceHeaderRecord.getPars()));
            sysSequenceHeaderValues.add(3,String.valueOf(sysSequenceHeaderRecord.getCmds()));
            sysSequenceHeaderValues.add(4,String.valueOf(sysSequenceHeaderRecord.getStartTime()));
            sysSequenceHeaderValues.add(5,String.valueOf(sysSequenceHeaderRecord.getStartTime2()));
            sysSequenceHeaderValues.add(6,String.valueOf(sysSequenceHeaderRecord.getSubsystem()));
            sysSequenceHeaderValues.add(7,String.valueOf(sysSequenceHeaderRecord.getSource()));
            sysSequenceHeaderValues.add(8,String.valueOf(sysSequenceHeaderRecord.getTcRequestId()));
            sysSequenceHeaderValues.add(9,String.valueOf(sysSequenceHeaderRecord.getSubSchedId()));

            SequenceHeader sequenceHeader = new SequenceHeader();

            for(int i = 0; i < SysSequenceHeader.COLUMNS.values().length; i++) {
//                if(sysSequenceHeaderValues.get(i).length() > 0) {
                if(sysSequenceHeaderValues.get(i) != "null") {
                    SequenceHeader.Field sequenceHeaderField = new SequenceHeader.Field();
                    sequenceHeaderField.setName(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getName()));
                    sequenceHeaderField.setValue(sysSequenceHeaderValues.get(i));
                    sequenceHeaderField.setType(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getType()));
                    if(SysSequenceHeader.COLUMNS.values()[i].getUnits() != null) {
                        sequenceHeaderField.setUnits(String.valueOf(SysSequenceHeader.COLUMNS.values()[i].getUnits()));
                    }
                    sequenceHeader.getField().add(sequenceHeaderField);
                }
                sequence.setSequenceHeader(sequenceHeader);
            }

            //SEQUENCE PARAMETERS
            seqparamExists = sysSequenceHeaderRecord.getPars() > 0? true:false;
            if(seqparamExists) {
                List<SysSequenceParameter> seqparamFromDB = flightplanRepository.sequenceParameterRecords(sysSequenceHeaderRecord);

                for(SysSequenceParameter sysSequenceParameter: seqparamFromDB) {
                    //TODO TRY TO CONVERT TO STRING[]
                    List<String> sysSequenceParameterValues = new ArrayList<>();

                    sysSequenceParameterValues.add(0, sysSequenceParameter.getSequenceParameterId()); //It is a string and NEVER NULL -> String.valueOf ?
                    sysSequenceParameterValues.add(1, String.valueOf(sysSequenceParameter.getType()));
                    sysSequenceParameterValues.add(2, String.valueOf(sysSequenceParameter.getRepType()));
                    sysSequenceParameterValues.add(3, sysSequenceParameter.getValue());

                    SequenceParameter sequenceParameter = new SequenceParameter();

                    for(int i = 0; i < SysSequenceParameter.COLUMNS.values().length; i++) {
                        SequenceParameter.Field sequenceParameterField = new SequenceParameter.Field();
                        sequenceParameterField.setName(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getName()));
                        sequenceParameterField.setValue(sysSequenceParameterValues.get(i));
                        sequenceParameterField.setType(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getType()));
                        if(SysSequenceParameter.COLUMNS.values()[i].getUnits() != null) {
                            sequenceParameterField.setUnits(String.valueOf(SysSequenceParameter.COLUMNS.values()[i].getUnits()));
                        }
                        sequenceParameter.getField().add(sequenceParameterField);
                    }
                    sequence.getSequenceParameter().add(sequenceParameter);
                }
            }
            sequenceList.add(sequence);
        }
        return sequenceList;
    }

    public  List<Command> createCommandFromDB(SysTaskScheduled sysTaskScheduled) {
        //COMMAND
        List<Command> commandList = new ArrayList<>();

        //COMMAND HEADER
        List<SysCommandHeader> commheaderFromDB = flightplanRepository.commandHeaderRecords(sysTaskScheduled);
        boolean commparamExists = false;

        for(SysCommandHeader sysCommadHeaderRecord: commheaderFromDB) {
            Command command = new Command();
            //TODO TRY TO CONVERT TO STRING[]
            List<String> sysCommandHeaderValues = new ArrayList<>(); //values FOR EACH FIELD
            /** All with String.valueOf() because if not there are issues to filter EMPTY FIELDS */
            sysCommandHeaderValues.add(0, String.valueOf(sysCommadHeaderRecord.getCmdType()));
            sysCommandHeaderValues.add(1, String.valueOf(sysCommadHeaderRecord.getCommandId())); //It is a string and NEVER NULL -> String.valueOf ?
            sysCommandHeaderValues.add(2, String.valueOf(sysCommadHeaderRecord.getManDispatch()));
            sysCommandHeaderValues.add(3, String.valueOf(sysCommadHeaderRecord.getRelease()));
            sysCommandHeaderValues.add(4, String.valueOf(sysCommadHeaderRecord.getRelTime()));
            sysCommandHeaderValues.add(5, String.valueOf(sysCommadHeaderRecord.getRelTime2()));
            sysCommandHeaderValues.add(6, String.valueOf(sysCommadHeaderRecord.getGroup()));
            sysCommandHeaderValues.add(7, String.valueOf(sysCommadHeaderRecord.getBlock()));
            sysCommandHeaderValues.add(8, String.valueOf(sysCommadHeaderRecord.getInterlock()));
//            sysCommandHeaderValues.add(9, String.valueOf(sysCommadHeaderRecord.getIlStage()));
            sysCommandHeaderValues.add(9, sysCommadHeaderRecord.getIlStage() != null? sysCommadHeaderRecord.getIlStage():"null");
            sysCommandHeaderValues.add(10, String.valueOf(sysCommadHeaderRecord.getStaticPTV()));
            sysCommandHeaderValues.add(11, String.valueOf(sysCommadHeaderRecord.getDynamicPTV()));
            sysCommandHeaderValues.add(12, String.valueOf(sysCommadHeaderRecord.getCev()));
            sysCommandHeaderValues.add(13, String.valueOf(sysCommadHeaderRecord.getPars()));
            sysCommandHeaderValues.add(14, String.valueOf(sysCommadHeaderRecord.getTimeTagged()));
            sysCommandHeaderValues.add(15, String.valueOf(sysCommadHeaderRecord.getPlanned()));
            sysCommandHeaderValues.add(16, String.valueOf(sysCommadHeaderRecord.getExecTime()));
            sysCommandHeaderValues.add(17, String.valueOf(sysCommadHeaderRecord.getExecTime2()));
//            sysCommandHeaderValues.add(18, String.valueOf(sysCommadHeaderRecord.getParent()));
            sysCommandHeaderValues.add(18, sysCommadHeaderRecord.getParent() != null? sysCommadHeaderRecord.getParent():"null");
            sysCommandHeaderValues.add(19, String.valueOf(sysCommadHeaderRecord.getStartTime()));
            sysCommandHeaderValues.add(20, String.valueOf(sysCommadHeaderRecord.getSubSystem()));
            sysCommandHeaderValues.add(21, String.valueOf(sysCommadHeaderRecord.getSource()));
            sysCommandHeaderValues.add(22, String.valueOf(sysCommadHeaderRecord.getEarliest()));
            sysCommandHeaderValues.add(23, String.valueOf(sysCommadHeaderRecord.getLatest()));
            sysCommandHeaderValues.add(24, String.valueOf(sysCommadHeaderRecord.getTcRequestId()));
            sysCommandHeaderValues.add(25, String.valueOf(sysCommadHeaderRecord.getSubSchedId()));
            sysCommandHeaderValues.add(26, String.valueOf(sysCommadHeaderRecord.getAckFlags()));
//            System.out.println("Command - Ack Flag Field: " + sysCommadHeaderRecord.getAckFlags());

            CommandHeader commandHeader = new CommandHeader();

            //sysCommandHeaderValues.size() because the LAST INDEX IS OPTIONAL
            for(int i=0; i< SysCommandHeader.COLUMNS.values().length; i++) {
                //optional fields sysCommandHeaderValues.get(i).length() > 0
//                if(sysCommandHeaderValues.get(i).length() > 0) {
                /** if(sysCommandHeaderValues.get(i) != "null" (the value is converted for String.valueOf into a String) || sysCommandHeaderValues.get(i) != null) DOES NOT WORK */
                if(sysCommandHeaderValues.get(i) != "null") {
//                    System.out.println("Command Header Values != null: " + "i= " + i + " " + sysCommandHeaderValues.get(i));

                    CommandHeader.Field commandHeaderField = new CommandHeader.Field();
                    commandHeaderField.setName(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getName()));
                    commandHeaderField.setValue(sysCommandHeaderValues.get(i));
                    commandHeaderField.setType(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getType()));
                    if(SysCommandHeader.COLUMNS.values()[i].getUnits() != null) {
                        commandHeaderField.setUnits(String.valueOf(SysCommandHeader.COLUMNS.values()[i].getUnits()));
                    }
                    commandHeader.getField().add(commandHeaderField);
                }
                command.setCommandHeader(commandHeader);
            }

            //COMMAND PARAMETERS
            commparamExists = sysCommadHeaderRecord.getPars() > 0? true:false;

            if(commparamExists) {
                List<SysCommandParameter> commparamFromDB = flightplanRepository.commandParameterRecords(sysCommadHeaderRecord);

//                for (int i=0; i<commparamFromDB.size();i++) {
//                    System.out.println("Command Parameter: " + commparamFromDB.get(i).getCommandParameterId());
//                }

                for(SysCommandParameter sysCommandParameter: commparamFromDB) {
                    //TODO TRY TO CONVERT TO STRING[]
                    List<String> sysCommandParameterValues = new ArrayList<>();

                    sysCommandParameterValues.add(0,String.valueOf(sysCommandParameter.getCommandParameterId()));
                    sysCommandParameterValues.add(1,String.valueOf(sysCommandParameter.getFormpos()));
                    sysCommandParameterValues.add(2,String.valueOf(sysCommandParameter.getType()));
                    sysCommandParameterValues.add(3,String.valueOf(sysCommandParameter.getEditable()));
                    sysCommandParameterValues.add(4,String.valueOf(sysCommandParameter.getRepType()));
                    sysCommandParameterValues.add(5,(sysCommandParameter.getValue() != null? sysCommandParameter.getValue(): "null")); //getValue() = "null" control when is null instead of an empty string
                    sysCommandParameterValues.add(6,(String.valueOf(sysCommandParameter.getDynamic() != null? String.valueOf(sysCommandParameter.getDynamic()):"null")));

//                    System.out.println("Command Parameter Id: " + sysCommandParameter.getCommandParameterId());

                    CommandParameter commandParameter = new CommandParameter();
                    //sysCommandParameterValues.size() because the LAST INDEX IS OPTIONAL
                    for(int i=0; i<SysCommandParameter.COLUMNS.values().length ; i++) {
                        //optional fields sysCommandParameterValues.get(i).length() > 0
//                    if(sysCommandParameterValues.get(i).length() > 0) {
                        if(sysCommandParameterValues.get(i) != "null") {
//                            System.out.println("Command Parameter Values != null: " + "i= " + i + " " + sysCommandParameterValues.get(i));
                            CommandParameter.Field commandParameterField = new CommandParameter.Field();
                            commandParameterField.setName(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getName()));
                            commandParameterField.setValue(sysCommandParameterValues.get(i));
                            commandParameterField.setType(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getType()));
                            if(SysCommandParameter.COLUMNS.values()[i].getUnits() != null) {
                                commandParameterField.setUnits(String.valueOf(SysCommandParameter.COLUMNS.values()[i].getUnits()));
                            }
                            commandParameter.getField().add(commandParameterField);
                        }
                    }
                    command.getCommandParameter().add(commandParameter);
                }
            }
            commandList.add(command);
        }
        return commandList;
    }


}
