package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "mps_schema" , name = "\"T_SYS_TASK_SCHEDULED\"")
public class SysTaskScheduled {

    /** missing FK relation due to non existence of T_POR_FILES(POR_FILE_ID, POR_FILE_NAME) */

    @Id
    @Column(name = "\"TASK_NAME\"", nullable = false)
    private String taskName;

//    @Id
//    @Enumerated(EnumType.STRING)
//    @Column(name = "\"TASK_TYPE\"", nullable = false)
//    private TaskType taskType;

    @ManyToOne (optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "\"T_SYS_TASK_SCHEDULED_SCHEDULING_ID_fkey\""),
                  name = "\"SCHEDULING_ID\"",referencedColumnName = "\"SCHEDULING_ID\"" ,nullable = false)
    private SysSchedulingProva sysScheduling;

    //NOT NULL -> to avoid errors
    @Column(name = "\"POR_FILE_ID\"")
    private BigInteger porFileId;

    //NOT NULL -> to avoid errors
    @Column(name = "\"POR_FILE_NAME\"")
    private String porFileName;

//    public enum TaskType {
//        COMMAND,
//        SEQUENCE,
//        EVENT,
//        PROCEDURE
//    }

}
