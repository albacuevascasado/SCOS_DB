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
@Table(schema = "mps_schema" , name = "\"T_SYS_TASK_SCHEDULED\"")
public class TaskScheduled implements Serializable {

    @Id
    @Column(name = "\"TASK_NAME\"", nullable = false)
    private String taskName;

//    @Id
//    @Enumerated(EnumType.STRING)
//    @Column(name = "\"TASK_TYPE\"", nullable = false)
//    private TaskType taskType;

    @Id
    @Column(name = "\"SCHEDULING_ID\"", nullable = false)
    private BigInteger schedulingId;

    //NOT NULL
    @Column(name = "\"POR_FILE_ID\"")
    private BigInteger porFileId;

    //NOT NULL
    @Column(name = "\"POR_FILE_NAME\"")
    private String porFileName;

    public enum TaskType {
        COMMAND,
        SEQUENCE,
        EVENT,
        PROCEDURE
    }

}
