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
@Table(schema = "mps_schema" , name = "\"T_BASE_HEADER\"")
public class BaseHeader implements Serializable {
    //values = [CATEGORY,SOURCE,GEN_TIME,RELTYPE,VERSION,START_TIME]
    public enum COLUMNS {
        CATEGORY ("number", null),
        SOURCE ("char", null),
        GEN_TIME ("number", "seconds"),
        RELTYPE ("number", null),
        VERSION ("char",null),
        START_TIME ("number", "seconds");

//        private String value;
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

//        public String setValue(final String value) {
//           return this.value = value;
//        }

//        public String getValue() {
//            return value;
//        }

    }

    //@Id -> MISSION_ID from T_SYS_MISSION

//    @Id
//    @OneToOne(optional = false)
//    @JoinColumns({
//            @JoinColumn(name = "\"SCHEDULING_ID\"", referencedColumnName = "\"SCHEDULING_ID\"", nullable = false),
//            @JoinColumn(name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
//    })
//    private TaskScheduled taskScheduled;

    @Id
    @Column(name = "\"SCHEDULING_ID\"", nullable = false)
    private BigInteger schedulingId;

    @Id
    @Column(name = "\"TASK_NAME\"", nullable = false)
    private String taskName;

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

}
