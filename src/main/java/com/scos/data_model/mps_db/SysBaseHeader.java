package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "mps_schema" , name = "\"T_SYS_BASE_HEADER\"")
public class SysBaseHeader {
    //values = [CATEGORY,SOURCE,GEN_TIME,RELTYPE,VERSION,START_TIME]
    public enum COLUMNS {
        CATEGORY ("CATEGORY","number", null),
        SOURCE ("SOURCE","char", null),
        GEN_TIME ("GEN TIME","number", "seconds"),
        RELTYPE ("RELTYPE","number", null),
        VERSION ("VERSION","char",null),
        START_TIME ("START TIME","number", "seconds");

//        private String value;
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

//        public String setValue(final String value) {
//           return this.value = value;
//        }

//        public String getValue() {
//            return value;
//        }

    }

    //@Id -> MISSION_ID from T_SYS_MISSION

//    @Id
//    @Column(name = "\"SCHEDULING_ID\"", nullable = false)
//    private BigInteger schedulingId;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"S_SYS_BASE_HEADER_ID\"")
    @SequenceGenerator(schema = "mps_schema" ,name = "\"S_SYS_BASE_HEADER_ID\"", sequenceName = "\"S_SYS_BASE_HEADER_ID\"", allocationSize = 1)
    @Id
    @Column(name = "\"SYS_BASE_HEADER_ID\"", nullable = false)
    private BigInteger baseHeaderId;

    @Column(name = "\"CATEGORY\"", nullable = false)
    private Integer category;

    @Column(name = "\"SOURCE\"", nullable = false)
    private String source;

    @Column(name = "\"GEN_TIME\"", nullable = false)
    private BigInteger genTime;

    @Column(name = "\"RELTYPE\"", nullable = false)
    private Integer relType;

    @Column(name = "\"VERSION\"", nullable = false)
    private String version;

    @Column(name = "\"START_TIME\"", nullable = false)
    private BigInteger startTime;


    @OneToOne(optional = false)
    @JoinColumn( foreignKey = @ForeignKey(name = "\"T_SYS_BASE_HEADER_SCHEDULING_ID_fkey\""),
                 name = "\"SCHEDULING_ID\"", referencedColumnName = "\"SCHEDULING_ID\"", nullable = false)
    private SysSchedulingProva sysScheduling;

    //TASK_NAME IS NOT PART OF THIS TABLE ONLY SCHEDULING_ID
//    @OneToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "\"T_SYS_BASE_HEADER_TASK_NAME_fkey\""),
//                name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
//    private SysTaskScheduled sysTaskScheduled;

}
