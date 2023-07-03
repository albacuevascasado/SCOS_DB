package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._YN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PCF\"")
public class PCF {

    public enum COLUMNS {
        PCF_NAME,
        PCF_DESCR,
        PCF_PID,
        PCF_UNIT,
        PCF_PTC,
        PCF_PFC,
        PCF_WIDTH,
        PCF_VALID,
        PCF_RELATED,
        PCF_CATEG,
        PCF_NATUR,
        PCF_CURTX,
        PCF_INTER,
        PCF_USCON,
        PCF_DECIM,
        PCF_PARVAL,
        PCF_SUBSYS,
        PCF_VALPAR,
        PCF_SPTYPE,
        PCF_CORR,
        PCF_OBTID,
        PCF_DARC,
        PCF_ENDIAN,
        PCF_DESCR2

    }

    @Id
    @Column(name = "\"PCF_NAME\"", nullable = false)
    private String pcfName;

    @Column(name = "\"PCF_DESCR\"")
    private String pcfDescr;

    @Column(name = "\"PCF_PID\"")
    private BigInteger pcfPid;

    @Column(name = "\"PCF_UNIT\"")
    private String pcfUnit;

    @Column(name = "\"PCF_PTC\"", nullable = false)
    private int pcfPtc;

    @Column(name = "\"PCF_PFC\"", nullable = false)
    private int pcfPfc;

    @Column(name = "\"PCF_WIDTH\"")
    private BigInteger pcfWidth;

    @Column(name = "\"PCF_VALID\"")
    private String pcfValid;

    @Column(name = "\"PCF_RELATED\"")
    private String pcfRelated;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_CATEG\"", nullable = false)
    private PcfCateg pcfCateg;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_NATUR\"", nullable = false)
    private PcfNatur pcfNatur;

    @Column(name = "\"PCF_CURTX\"")
    private String pcfCurtx;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_INTER\"")
    private PcfInter pcfInter = PcfInter.F;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_USCON\"")
    private _YN pcfUscon = _YN.N;

    @Column(name = "\"PCF_DECIM\"")
    private int pcfDecim;

    @Column(name = "\"PCF_PARVAL\"")
    private String pcfParval;

    @Column(name = "\"PCF_SUBSYS\"")
    private String pcfSubsys;

    @Column(name = "\"PCF_VALPAR\"")
    private int pcfValpar = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_SPTYPE\"")
    private PcfSptype pcfSptype;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_CORR\"")
    private _YN pcfCorr = _YN.Y;

    @Column(name = "\"PCF_OBTID\"")
    private int pcfObtid;

    @Column(name = "\"PCF_DARC\"")
    private char pcfDarc = '0';

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCF_ENDIAN\"")
    private PcfEndian pcfEndian = PcfEndian.B;

    @Column(name = "\"PCF_DESCR2\"")
    private String pcfDescr2 = "";

    public static final int MAX_COLUMN = 24;

    public static final String DESCRIPTION = "Parameter characteristic file";

    public enum PcfCateg {
        N,
        S,
        T
    }

    public enum PcfNatur {
        R,
        D,
        P,
        H,
        S,
        C
    }

    public enum PcfInter {
        P,
        F
    }

    public enum PcfSptype {
        E,
        R
    }

    public enum PcfEndian {
        B,
        L
    }

    public static final char[] arrayPCFDarc = new char[]{'0','1'};

}