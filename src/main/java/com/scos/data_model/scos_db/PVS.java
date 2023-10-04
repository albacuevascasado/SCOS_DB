package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PVS\"")
public class PVS implements Serializable {

    public enum COLUMNS {
        PVS_ID,
        PVS_PSID,
        PVS_PNAME,
        PVS_INTER,
        PVS_VALS,
        PVS_BIT
    }

    @Id
    @Column(name = "\"PVS_ID\"", nullable = false)
    private String pvsId;

    @Column(name = "\"PVS_PSID\"", nullable = false)
    private String pvsPsid;

    @Column(name = "\"PVS_PNAME\"", nullable = false)
    private String pvsPname;

    @Column(name = "\"PVS_INTER\"")
    private PvsInter pvsInter = PvsInter.R;

    @Column(name = "\"PVS_VALS\"")
    private String pvsVals;

    @Id
    @Column(name = "\"PVS_BIT\"", nullable = false)
    private Integer pvsBit;

    public static final int MAX_COLUMN = 6;

    public static final String DESCRIPTION = "Command/sequence parameter value set file";

    public enum PvsInter {
        R,
        E
    }

}
