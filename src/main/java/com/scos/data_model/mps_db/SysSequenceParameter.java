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
@Table(schema = "mps_schema" , name = "\"T_SYS_SEQUENCE_PARAMETER\"")
public class SysSequenceParameter implements Serializable {

    public enum COLUMNS {
        ID ("ID","char",null),
        TYPE ("TYPE","number",null),
        REPTYPE ("REPTYPE","number",null),
        VALUE ("VALUE","char",null);

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

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"S_SEQUENCE_PARAMETER_PROGRESSIVE_ID\"")
    @SequenceGenerator(schema = "mps_schema", name = "\"S_SEQUENCE_PARAMETER_PROGRESSIVE_ID\"", sequenceName = "\"S_SEQUENCE_PARAMETER_PROGRESSIVE_ID\"", allocationSize = 1)
    @Id
    @Column(name = "\"SEQUENCE_PARAMETER_PROGRESSIVE_ID\"", nullable = false)
    private BigInteger sequenceParameterProgressiveId;

    @Id
    @Column(name = "\"SEQUENCE_PARAMETER_ID\"", nullable = false)
    private String sequenceParameterId;

    @Column(name = "\"TYPE\"", nullable = false)
    private Integer type;

    @Column(name = "\"REPTYPE\"", nullable = false)
    private Integer repType;

    @Column(name = "\"VALUE\"", nullable = false)
    private String value;

    @ManyToOne(optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "\"T_SYS_SEQUENCE_PARAMETER_SEQUENCE_ID_STARTTIME_fkey\""),
            value = {
                @JoinColumn(name = "\"STARTTIME\"", referencedColumnName = "\"STARTTIME\"", nullable = false),
                @JoinColumn(name = "\"SEQUENCE_ID\"", referencedColumnName = "\"SEQUENCE_ID\"", nullable = false)
    })
    private SysSequenceHeader sysSequenceHeader;

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "\"T_SYS_SEQUENCE_PARAMETER_TASK_NAME_fkey\""),
                name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
    private SysTaskScheduled sysTaskScheduled;

}
