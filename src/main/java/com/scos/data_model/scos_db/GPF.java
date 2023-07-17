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

    public enum COLUMNS {
        GPF_NUMBE,
        GPF_TYPE,
        GPF_HEAD,
        GPF_SCROL,
        GPF_HCOPY,
        GPF_DAYS,
        GPF_HOURS,
        GPF_MINUT,
        GPF_AXCLR,
        GPF_XTICK,
        GPF_YTICK,
        GPF_XGRID,
        GPF_YGRID,
        GPF_UPUN

    }

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
    private Integer gpfDays;

    @Column(name = "\"GPF_HOURS\"", nullable = false)
    private Integer gpfHours;

    @Column(name = "\"GPF_MINUT\"", nullable = false)
    private Integer gpfMinut;

    @Column(name = "\"GPF_AXCLR\"", nullable = false)
    private char gpfAxclr;

    @Column(name = "\"GPF_XTICK\"", nullable = false)
    private Integer gpfXtic;

    @Column(name = "\"GPF_YTICK\"", nullable = false)
    private Integer gpfYtic;

    @Column(name = "\"GPF_XGRID\"", nullable = false)
    private Integer gpfXgrid;

    @Column(name = "\"GPF_YGRID\"", nullable = false)
    private Integer gpfYgrid;

    @Column(name = "\"GPF_UPUN\"")
    private Integer gpfUpun;

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
