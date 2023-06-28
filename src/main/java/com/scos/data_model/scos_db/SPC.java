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
@Table(schema = "scos_schema", name = "\"SPC\"")
public class SPC implements Serializable {

    @Id
    @Column(name = "\"SPC_NUMBE\"", nullable = false)
    private String spcNumbe;

    @Id
    @Column(name = "\"SPC_POS\"", nullable = false)
    private int spcPos;

    @Column(name = "\"SPC_NAME\"", nullable = false)
    private String spcName;

    @Column(name = "\"SPC_UPDT\"")
    private char spcUpdt = ' ';

    @Column(name = "\"SPC_MODE\"")
    private char spcMode = ' ';

    @Enumerated(EnumType.STRING)
    @Column(name = "\"SPC_FORM\"")
    private SpcForm spcForm = SpcForm.N;

    @Column(name = "\"SPC_BACK\"")
    private char spcBack = '0';

    @Column(name = "\"SPC_FORE\"", nullable = false)
    private char spcFore;

    public static final int MAX_COLUMN = 8;

    public static final String DESCRIPTION = "Scrolling display definition";

    public static final char[] arraySPCUpdt = new char[]{' ','N'};
    public static final char[] arraySPCMode = new char[]{' ','N'};
    public static final char[] arraySPCBack = new char[]{'0','1','2','3','4','5','6','7'};
    public static final char[] arraySPCFore = new char[]{'0','1','2','3','4','5','6','7'};

    public enum SpcForm {
        B,
        O,
        D,
        H,
        N
    }

}
