package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CPC\"")
public class CPC {

    @Id
    @Column(name = "\"CPC_PNAME\"", nullable = false)
    private String cpcPname;

    @Column(name = "\"CPC_DESCR\"")
    private String cpcDescr;

    @Column(name = "\"CPC_PTC\"", nullable = false)
    private double cpcPtc;

    @Column(name = "\"CPC_PFC\"", nullable = false)
    private int cpcPfc;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_DISPFMT\"")
    private CpcDispfmt cpcDispfmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_RADIX\"")
    private _RADIX cpcRadix;

    @Column(name = "\"CPC_UNIT\"")
    private String cpcUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_CATEG\"")
    private CpcCateg cpcCateg;

    @Column(name = "\"CPC_PRFREF\"")
    private String cpcPrfref;

    @Column(name = "\"CPC_CCAREF\"")
    private String cpcCcaref;

    @Column(name = "\"CPC_PAFREF\"")
    private String cpcPafref;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_INTER\"")
    private CpcInter cpcInter;

    @Column(name = "\"CPC_DEFVAL\"")
    private String cpcDefval;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_CORR\"")
    private _YN cpcCorr;

    @Column(name = "\"CPC_OBTID\"")
    private int cpcObtid;

    @Column(name = "\"CPC_DESCR2\"")
    private String cpcDescr2;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_ENDIAN\"")
    private CpcEndian cpcEndian;

    public static final int MAX_COLUMN = 17;

    public static final String DESCRIPTION = "Command parameter characteristic  file";

    public enum CpcDispfmt {
        A,
        I,
        U,
        R,
        T,
        D
    }

    public enum CpcCateg {
        C,
        T,
        B,
        A,
        P,
        N
    }

    public enum CpcInter {
        R,
        E
    }

    public enum CpcEndian {
        B,
        L
    }

}
