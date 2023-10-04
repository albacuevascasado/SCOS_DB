package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._RADIX;
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
@Table(schema = "scos_schema", name = "\"CSP\"")
public class CSP implements Serializable {

    public enum COLUMNS {
        CSP_SQNAME,
        CSP_FPNAME,
        CSP_FPNUM,
        CSP_DESCR,
        CSP_PTC,
        CSP_PFC,
        CSP_DISPFMT,
        CSP_RADIX,
        CSP_TYPE,
        CSP_VTYPE,
        CSP_DEFVAL,
        CSP_CATEG,
        CSP_PRFREF,
        CSP_CCAREF,
        CSP_PAFREF,
        CSP_UNIT
    }

    @Id
    @Column(name = "\"CSP_SQNAME\"", nullable = false)
    private String cspSqname;

    @Id
    @Column(name = "\"CSP_FPNAME\"", nullable = false)
    private String cspFpname;

    @Column(name = "\"CSP_FPNUM\"", nullable = false)
    private Integer cspFpnum;

    @Column(name = "\"CSP_DESCR\"")
    private String cspDescr;

    @Column(name = "\"CSP_PTC\"", nullable = false)
    private Integer cspPtc;

    @Column(name = "\"CSP_PFC\"", nullable = false)
    private Integer cspPfc;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSP_DISPFMT\"")
    private CspDispfmt cspDispfmt = CspDispfmt.R;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSP_RADIX\"")
    private _RADIX cspRadix = _RADIX.D;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSP_TYPE\"", nullable = false)
    private CspType cspType;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSP_VTYPE\"")
    private CspVtype cspVtype;

    @Column(name = "\"CSP_DEFVAL\"")
    private String cspDefval;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSP_CATEG\"")
    private CspCateg cspCateg = CspCateg.N;

    @Column(name = "\"CSP_PRFREF\"")
    private String cspPrfref;

    @Column(name = "\"CSP_CCAREF\"")
    private String cspCcaref;

    @Column(name = "\"CSP_PAFREF\"")
    private String cspPafref;

    @Column(name = "\"CSP_UNIT\"")
    private String cspUnit;

    public static final int MAX_COLUMN = 16;

    public static final String DESCRIPTION = "Command sequence formal parameters";

    public enum CspDispfmt {
        A,
        I,
        U,
        R,
        T,
        D
    }

    public enum CspType {
        C,
        S,
        P
    }

    public enum CspVtype {
        R,
        E
    }

    public enum CspCateg {
        C,
        T,
        A,
        P,
        B,
        N
    }

}
