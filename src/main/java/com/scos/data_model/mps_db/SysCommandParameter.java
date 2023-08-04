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
@Table(schema = "mps_schema" , name = "\"T_SYS_COMMAND_PARAMETER\"")
public class SysCommandParameter implements Serializable {

    public enum COLUMNS {
        ID ("ID","char",null),
        FORMPOS ("FORMPOS","number",null),
        TYPE ("TYPE","number",null),
        EDITABLE ("EDITABLE","number",null),
        REPTYPE ("REPTYPE","number",null),
        VALUE ("VALUE","char",null),
        DYNAMIC ("DYNAMIC","number",null);

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

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"S_COMMAND_PARAMETER_PROGRESSIVE_ID\"")
    @SequenceGenerator(schema = "mps_schema",name = "\"S_COMMAND_PARAMETER_PROGRESSIVE_ID\"", sequenceName = "\"S_COMMAND_PARAMETER_PROGRESSIVE_ID\"", allocationSize = 1)
    @Id
    @Column(name = "\"COMMAND_PARAMETER_PROGRESSIVE_ID\"", nullable = false)
    private BigInteger commandParameterProgressiveId;

    @Id
    @Column(name = "\"COMMAND_PARAMETER_ID\"", nullable = false)
    private String commandParameterId;

    @Column(name = "\"FORMPOS\"", nullable = false)
    private Integer formpos;

    @Column(name = "\"TYPE\"", nullable = false)
    private Integer type;

    @Column(name = "\"EDITABLE\"", nullable = false)
    private Integer editable;

    @Column(name = "\"REPTYPE\"", nullable = false)
    private Integer repType;

    @Column(name = "\"VALUE\"")
    private String value;

    @Column(name = "\"DYNAMIC\"")
    private Integer dynamic;

    @ManyToOne(optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "\"T_SYS_COMMAND_PARAMETER_COMMAND_ID_COMMAND_PROGRESSIVE_ID_fkey\""),
            value = {
                @JoinColumn(name = "\"COMMAND_PROGRESSIVE_ID\"", referencedColumnName = "\"COMMAND_PROGRESSIVE_ID\"", nullable = false),
                @JoinColumn(name = "\"COMMAND_ID\"", referencedColumnName = "\"COMMAND_ID\"", nullable = false)
    })
    private SysCommandHeader sysCommandHeader;

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "\"T_SYS_COMMAND_PARAMETER_TASK_NAME_fkey\""),
                name = "\"TASK_NAME\"", referencedColumnName = "\"TASK_NAME\"", nullable = false)
    private SysTaskScheduled sysTaskScheduled;

}
