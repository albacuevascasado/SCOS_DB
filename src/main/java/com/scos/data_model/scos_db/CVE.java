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
@Table(schema = "scos_schema", name = "\"CVE\"")
public class CVE implements Serializable {

    public enum COLUMNS {
        CVE_CVSID,
        CVE_PARNAM,
        CVE_INTER,
        CVE_VAL,
        CVE_TOL,
        CVE_CHECK
    }

    @Id
    @Column(name = "\"CVE_CVSID\"", nullable = false)
    private int cveCvsid;

    @Id
    @Column(name = "\"CVE_PARNAM\"", nullable = false)
    private String cveParnam;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CVE_INTER\"")
    private CveInter cveInter = CveInter.R;

    @Column(name = "\"CVE_VAL\"")
    private String cveVal;

    @Column(name = "\"CVE_TOL\"")
    private String cveTol;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CVE_CHECK\"")
    private CveCheck cveCheck = CveCheck.B;

    public static final int MAX_COLUMN = 6;

    public static final String DESCRIPTION = "Command verification expression file";

    public enum CveInter {
        R,
        E,
        C
    }

    public enum CveCheck {
        B,
        S
    }

}
