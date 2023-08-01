package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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

//    @Id
//    @Column(name = "\"TASK_NAME\"", nullable = false)
//    private String taskName;
//
//    @Id
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "\"COMMAND_ID\"", referencedColumnName = "\"COMMAND_ID\"", nullable = false)
//    private CommandHeader commandHeader;

//    //part of the composite primary key + unique field to identify parameter
//    @Id
//    @Column(name = "\"COMMAND_PROGRESSIVE_ID\"", nullable = false)
//    private String commandProgressiveId;

    @Id
    @Column(name = "\"COMMAND_ID\"", nullable = false)
    private String commandId;

    @Id
    @Column(name = "\"PARAMETER_ID\"", nullable = false)
    private String parameterId;

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

}
