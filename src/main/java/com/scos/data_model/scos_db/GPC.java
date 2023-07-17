package com.scos.data_model.scos_db;

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
@Table(schema = "scos_schema", name = "\"GPC\"")
public class GPC implements Serializable {

    public enum COLUMNS {
        GPC_NUMBE,
        GPC_POS,
        GPC_WHERE,
        GPC_NAME,
        GPC_RAW,
        GPC_MINIM,
        GPC_MAXIM,
        GPC_PRCLR,
        GPC_SYMB0,
        GPC_LINE,
        GPC_DOMAIN
    }

    @Id
    @Column(name = "\"GPC_NUMBE\"", nullable = false)
    private String gpcNumbe;

    @Id
    @Column(name = "\"GPC_POS\"", nullable = false)
    private Integer gpcPos;

    @Column(name = "\"GPC_WHERE\"", nullable = false)
    private char gpcWhere;

    @Column(name = "\"GPC_NAME\"", nullable = false)
    private String gpcName;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"GPC_RAW\"")
    private GpcRaw gpcRaw = GpcRaw.C;

    @Column(name = "\"GPC_MINIM\"", nullable = false)
    private String gpcMinim;

    @Column(name = "\"GPC_MAXIM\"", nullable = false)
    private String gpcMaxim;

    @Column(name = "\"GPC_PRCLR\"", nullable = false)
    private char gpcPrclr;

    @Column(name = "\"GPC_SYMB0\"")
    private char gpcSymb0 = '0';

    @Column(name = "\"GPC_LINE\"")
    private char gpcLine = '0';

    @Column(name = "\"GPC_DOMAIN\"")
    private Integer gpcDomain;

    public static final int MAX_COLUMN = 11;

    public static final String DESCRIPTION = "Graphic display definition";

    public enum GpcRaw {
        C,
        U
    }

    public static final char[] arrayGPCWhere = new char[] {'1','P','2'};
    public static final char[] arrayGPCPrclr = new char[] {'1','2','3','4','5','6','7'};
    public static final char[] arrayGPCSymb0 = new char[] {'0','1','2','3','4','5','6'};
    public static final char[] arrayGPCLine = new char[] {'0','1','2','3','4','5'};

}
