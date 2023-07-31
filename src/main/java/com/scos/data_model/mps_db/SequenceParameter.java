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
@Table(schema = "mps_schema" , name = "\"T_SEQUENCE_PARAMETER\"")
public class SequenceParameter implements Serializable {

    @Id
    @Column(name = "\"SEQUENCE_ID\"", nullable = false)
    private String sequenceId;

    @Id
    @Column(name = "\"PARAMETER_ID\"", nullable = false)
    private String parameterId;

//    @Id
//    @Column(name = "\"TASK_NAME\"", nullable = false)
//    private String taskName;
//
//    @Id
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "\"SEQUENCE_ID\"", referencedColumnName = "\"SEQUENCE_ID\"", nullable = false)
//    private SequenceHeader sequenceHeader;

//    //part of the composite primary key + unique field to identify parameter
//    @Id
//    @Column(name = "\"STARTTIME\"", nullable = false)
//    private String startTime;

    @Column(name = "\"TYPE\"", nullable = false)
    private Integer type;

    @Column(name = "\"REPTYPE\"", nullable = false)
    private Integer repType;

    @Column(name = "\"VALUE\"", nullable = false)
    private String value;

}
