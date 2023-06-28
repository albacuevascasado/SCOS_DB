package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CVP\"")
public class CVP implements Serializable {

    @Id
    @Column(name = "\"CVP_TASK\"", nullable = false)
    private String cvpTask;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "\"CVP_TYPE\"", nullable = false)
    private CvpType cvpType = CvpType.C;

    @Id
    @Column(name = "\"CVP_CVSID\"", nullable = false)
    private int cvpCvsid;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Command verification profile";

    public enum CvpType {
        C,
        S
    }

}
