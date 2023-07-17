package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"OCF\"")
public class OCF {

    public enum COLUMNS {
        OCF_NAME,
        OCF_NBCHCK,
        OCF_NBOOl,
        OCF_INTER,
        OCF_CODIN
    }

    @Id
    @Column(name = "\"OCF_NAME\"", nullable = false)
    private String ocfName;

    @Column(name = "\"OCF_NBCHCK\"", nullable = false)
    private Integer ocfNbchck;

    @Column(name = "\"OCF_NBOOl\"", nullable = false)
    private Integer ocfNbool;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"OCF_INTER\"", nullable = false)
    private OcfInter ocfInter;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"OCF_CODIN\"", nullable = false)
    private OcfCodin ocfCodin;

    public static final int MAX_COLUMN = 5;

    public static final String DESCRIPTION = "Out of limits checks file";

    public enum OcfInter {
        U,
        C
    }

    public enum OcfCodin {
        R,
        I,
        A
    }

}
