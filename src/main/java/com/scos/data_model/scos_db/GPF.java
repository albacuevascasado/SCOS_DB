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
@Table(schema = "scos_schema", name = "\"GPF\"")
public class GPF {

    @Id
    @Column(name = "\"GPF_NUMBE\"", nullable = false)
    private String gpfNumbe;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"GPF_TYPE\"", nullable = false)
    private GpfType gpfType;

    @Column(name = "\"GPF_HEAD\"")
    private String gpfHead;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"GPF_SCROL\"")
    private _YN gpfScrol = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"GPF_HCOPY\"")
    private _YN gpfHcopy = _YN.N;

    @Column(name = "\"GPF_DAYS\"", nullable = false)
    private int gpfDays;

    @Column(name = "\"GPF_HOURS\"", nullable = false)
    private int gpfHours;

    @Column(name = "\"GPF_MINUT\"", nullable = false)
    private int gpfMinut;

    @Column(name = "\"GPF_AXCLR\"", nullable = false)
    private char gpfAxclr;

    @Column(name = "\"GPF_XTICK\"", nullable = false)
    private int gpfXtic;

    @Column(name = "\"GPF_YTICK\"", nullable = false)
    private int gpfYtic;

    @Column(name = "\"GPF_XGRID\"", nullable = false)
    private int gpfXgrid;

    @Column(name = "\"GPF_YGRID\"", nullable = false)
    private int gpfYgrid;

    @Column(name = "\"GPF_UPUN\"")
    private int gpfUpun;

    public static final int MAX_COLUMN = 14;

    public static final String DESCRIPTION = "Graphic display";

    public enum GpfType {
        F,
        H,
        Q,
        S
    }

    public static final char[] arrayGpfAxclr = new char[] {'1','2','3','4','5','6','7'};

}
