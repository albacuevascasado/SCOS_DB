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
@Table(schema = "scos_schema", name = "\"PSM\"")
public class PSM implements Serializable {

    public enum COLUMNS {
        PSM_NAME,
        PSM_TYPE,
        PSM_PARSET
    }

    @Id
    @Column(name = "\"PSM_NAME\"", nullable = false)
    private String psmName;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "\"PSM_TYPE\"", nullable = false)
    private PsmType psmType;

    @Id
    @Column(name = "\"PSM_PARSET\"", nullable = false)
    private String psmParset;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Parameter sets mapping file";

    public enum PsmType {
        C,
        S
    }

}
