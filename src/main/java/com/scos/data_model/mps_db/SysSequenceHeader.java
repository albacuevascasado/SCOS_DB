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
@Table(schema = "mps_schema" , name = "\"T_SYS_SEQUENCE_HEADER\"")
public class SysSequenceHeader implements Serializable {

    public enum COLUMNS {
        SEQ_TYPE ("SEQ TYPE","char",null),
        ID ("ID","char",null),
        PARS ("PARS","number",null),
        CMDS ("CMDS","number",null),
        STARTTIME ("STARTTIME","number","seconds"),
        STARTIME2 ("STARTTIME2","number","microseconds"),
        SUBSYSTEM ("SUBSYSTEM","number",null),
        SOURCE ("SOURCE","number",null),
        TC_REQUEST_ID ("TC REQUEST ID","number",null),
        SUB_SCHED_ID("SUB-SCHED ID","number",null);

        private String name;
        private String type;
        private String units;

        COLUMNS(final String name, final String type, final String units) {
            this.name = name;
            this.type = type;
            this.units = units;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getUnits() {
            return units;
        }
    }

    //even if it is always "S", save the value in DB?
    @Column(name = "\"SEQ_TYPE\"", nullable = false)
    private char seqType = "S".charAt(0);

    @Id
    @Column(name = "\"SEQUENCE_ID\"", nullable = false)
    private String sequenceId;

//    @Id
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
//    private TaskScheduled taskScheduled;

    @Id
    @Column(name = "\"TASK_NAME\"", nullable = false)
    private String taskName;

    @Column(name = "\"PARS\"", nullable = false)
    private Integer pars;

    @Column(name = "\"CMDS\"", nullable = false)
    private Integer cmds;

    @Id
    @Column(name = "\"STARTTIME\"", nullable = false)
    private BigInteger startTime;

    @Column(name = "\"STARTTIME2\"", nullable = false)
    private BigInteger startTime2;

    @Column(name = "\"SUBSYSTEM\"")
    private Integer subsystem;

    @Column(name = "\"SOURCE\"")
    private Integer source;

    @Column(name = "\"TC_REQUEST_ID\"")
    private Integer tcRequestId;

    @Column(name = "\"SUB-SCHED_ID\"", nullable = false)
    private BigInteger subSchedId;

}
