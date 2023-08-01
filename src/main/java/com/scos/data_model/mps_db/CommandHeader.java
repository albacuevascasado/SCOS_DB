package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "mps_schema" , name = "\"T_COMMAND_HEADER\"")
public class CommandHeader implements Serializable {

    public enum COLUMNS {
        CMDTYPE ("char",null),
        ID ("char",null),
        MAN_DISPATCH ("number",null),
        RELEASE ("number",null),
        RELTIME ("number","seconds"),
        RELTIME2 ("number","microseconds"),
        GROUP ("number",null),
        BLOCK ("number",null),
        INTERLOCK ("number",null),
        ILSTAGE ("char",null),
        STATIC_PTV ("number",null),
        DYNAMIC_PTV ("number",null),
        CEV ("number",null),
        PARS ("number",null),
        TIME_TAGGED ("number",null),
        PLANNED ("number",null),
        EXEC_TIME ("number","seconds"),
        EXEC_TIME2 ("number","microseconds"),
        PARENT ("char",null),
        STARTTIME ("number",null),
        SUBSYSTEM ("number",null),
        SOURCE ("number",null),
        EARLIEST ("number",null),
        LATEST ("number",null),
        TC_REQUEST_ID ("number",null),
        SUB_SCHED_ID ("number",null),
        ACK_FLAGS ("number",null);

        private String type;
        private String units;

        COLUMNS(final String type, final String units) {
            this.type = type;
            this.units = units;
        }

        public String getType() {
            return type;
        }

        public String getUnits() {
            return units;
        }
    }

//    @Id
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
//    private TaskScheduled taskScheduled;

//    @Id
//    @Column(name = "\"COMMAND_PROGRESSIVE_ID\"", nullable = false)
//    private BigInteger commandProgressiveId;

    @Id
    @Column(name = "\"TASK_NAME\"")
    private String taskName;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CMDTYPE\"", nullable = false)
    private Cmdtype cmdType;

    @Id
    @Column(name = "\"COMMAND_ID\"", nullable = false)
    private String commandId;

    @Column(name = "\"MAN_DISPATCH\"", nullable = false)
    private Integer manDispatch;

    @Column(name = "\"RELEASE\"", nullable = false)
    private Integer release;

    @Column(name = "\"RELTIME\"", nullable = false)
    private BigInteger relTime;

    @Column(name = "\"RELTIME2\"", nullable = false)
    private BigInteger relTime2;

    @Column(name = "\"GROUP\"", nullable = false)
    private Integer group;

    @Column(name = "\"BLOCK\"", nullable = false)
    private Integer block;

    @Column(name = "\"INTERLOCK\"", nullable = false)
    private Integer interlock;

    @Column(name = "\"ILSTAGE\"")
    private String ilStage;

    @Column(name = "\"STATIC_PTV\"", nullable = false)
    private Integer staticPTV;

    @Column(name = "\"DYNAMIC_PTV\"", nullable = false)
    private Integer dynamicPTV;

    @Column(name = "\"CEV\"", nullable = false)
    private Integer cev;

    @Column(name = "\"PARS\"", nullable = false)
    private BigInteger pars;

    @Column(name = "\"TIME_TAGGED\"", nullable = false)
    private Integer timeTagged;

    @Column(name = "\"PLANNED\"", nullable = false)
    private Integer planned;

    @Column(name = "\"EXEC_TIME\"", nullable = false)
    private BigInteger execTime;

    @Column(name = "\"EXEC_TIME2\"", nullable = false)
    private BigInteger execTime2;

    @Column(name = "\"PARENT\"")
    private String parent;

    @Column(name = "\"STARTTIME\"")
    private BigInteger startTime;

    @Column(name = "\"SUBSYSTEM\"")
    private BigInteger subSystem;

    @Column(name = "\"SOURCE\"")
    private Integer source;

    @Column(name = "\"EARLIEST\"")
    private BigInteger earliest;

    @Column(name = "\"LATEST\"")
    private BigInteger latest;

    @Column(name = "\"TC_REQUEST_ID\"")
    private Integer tcRequestId;

    @Column(name = "\"SUB-SCHED_ID\"", nullable = false)
    private BigInteger subSchedId;

    @Column(name = "\"ACK-FLAGS\"")
    private Integer ackFlags;

    public enum Cmdtype {
        C,
        R,
        F
    }
}
