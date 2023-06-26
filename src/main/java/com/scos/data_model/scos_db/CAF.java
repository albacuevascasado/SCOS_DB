package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._FMT;
import com.scos.data_model.scos_db.common._RADIX;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema" , name = "\"CAF\"")
public class CAF {

    @Id
    @Column(name = "\"CAF_NUMBR\"", nullable = false)
    private String cafNumbr;

    @Column(name = "\"CAF_DESCR\"")
    private String cafDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CAF_ENGFMT\"", nullable = false)
    private _FMT cafEngfmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CAF_RAWFMT\"", nullable = false)
    private _FMT cafRawfmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CAF_RADIX\"")
    private _RADIX cafRadix;

    @Column(name = "\"CAF_UNIT\"")
    private String cafUnit;

    @Column(name = "\"CAF_NCURVE\"")
    private int cafNcurve;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CAF_INTER\"")
    private CafInter cafInter;

    public static final int MAX_COLUMN = 8;

    public static final String DESCRIPTION = "Numerical calibration curve file";

    public enum CafInter {
        P,
        F
    }

}
