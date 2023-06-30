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
        PCF_NAME (0),
        PCF_DESCR (1),
        PCF_PID (2),
        PCF_UNIT (3),
        PCF_PTC (4),
        PCF_PFC (5),
        PCF_WIDTH (6),
        PCF_VALID (7),
        PCF_RELATED (8),
        PCF_CATEG (9),
        PCF_NATUR (10),
        PCF_CURTX (11),
        PCF_INTER (12),
        PCF_USCON (13),
        PCF_DECIM (14),
        PCF_PARVAL (15),
        PCF_SUBSYS (16),
        PCF_VALPAR (17),
        PCF_SPTYPE (18),
        PCF_CORR (19),
        PCF_OBTID (20),
        PCF_DARC (21),
        PCF_ENDIAN (22),
        PCF_DESCR2 (23);

        private int hierarchy;

        private COLUMNS (final int hierarchy) {
            this.hierarchy = hierarchy;
        }

        public int getHierarchy() {
            return hierarchy;
        }

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