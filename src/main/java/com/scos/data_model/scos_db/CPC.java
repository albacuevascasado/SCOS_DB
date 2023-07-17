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

//    public enum COLUMNS {
//        CPC_PNAME (0),
//        CPC_DESCR (1),
//        CPC_PTC (2),
//        CPC_PFC (3),
//        CPC_DISPFMT (4),
//        CPC_RADIX (5),
//        CPC_UNIT (6),
//        CPC_CATEG (7),
//        CPC_PRFREF (8),
//        CPC_CCAREF (9),
//        CPC_PAFREF (10),
//        CPC_INTER (11),
//        CPC_DEFVAL (12),
//        CPC_CORR (13),
//        CPC_OBTID (14),
//        CPC_DESCR2 (15),
//        CPC_ENDIAN (16);
//
//        private int hierarchy;
//
//        COLUMNS(final int hierarchy) {
//            this.hierarchy = hierarchy;
//        }
//
//        public int getHierarchy() {
//            return hierarchy;
//        }
//
//    }

    public enum COLUMNS {
        CPC_PNAME,
        CPC_DESCR,
        CPC_PTC,
        CPC_PFC,
        CPC_DISPFMT,
        CPC_RADIX,
        CPC_UNIT,
        CPC_CATEG,
        CPC_PRFREF,
        CPC_CCAREF,
        CPC_PAFREF,
        CPC_INTER ,
        CPC_DEFVAL,
        CPC_CORR,
        CPC_OBTID ,
        CPC_DESCR2,
        CPC_ENDIAN
    }

    @Id
    @Column(name = "\"CPC_PNAME\"", nullable = false)
    private String cpcPname;

    @Column(name = "\"CPC_DESCR\"")
    private String cpcDescr;

    @Column(name = "\"CPC_PTC\"", nullable = false)
    private double cpcPtc;

    @Column(name = "\"CPC_PFC\"", nullable = false)
    private Integer cpcPfc;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_DISPFMT\"")
    private CpcDispfmt cpcDispfmt = CpcDispfmt.R;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_RADIX\"")
    private _RADIX cpcRadix =_RADIX.D;

    @Column(name = "\"CPC_UNIT\"")
    private String cpcUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_CATEG\"")
    private CpcCateg cpcCateg = CpcCateg.N;

    @Column(name = "\"CPC_PRFREF\"")
    private String cpcPrfref;

    @Column(name = "\"CPC_CCAREF\"")
    private String cpcCcaref;

    @Column(name = "\"CPC_PAFREF\"")
    private String cpcPafref;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_INTER\"")
    private CpcInter cpcInter = CpcInter.R;

    @Column(name = "\"CPC_DEFVAL\"")
    private String cpcDefval;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_CORR\"")
    private _YN cpcCorr = _YN.Y;

    @Column(name = "\"CPC_OBTID\"")
    private Integer cpcObtid;

    @Column(name = "\"CPC_DESCR2\"")
    private String cpcDescr2;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CPC_ENDIAN\"")
    private CpcEndian cpcEndian = CpcEndian.B;

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
