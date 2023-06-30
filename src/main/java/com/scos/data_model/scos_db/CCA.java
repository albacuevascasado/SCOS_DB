package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._FMT;
import com.scos.data_model.scos_db.common._RADIX;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CCA\"")
public class CCA {

    public enum COLUMNS {
        CCA_NUMBR,
        CCA_DESCR,
        CCA_ENGFMT,
        CCA_RAWFMT,
        CCA_RADIX,
        CCA_UNIT,
        CCA_NCURVE
    }

    @Id
    @Column(name = "\"CCA_NUMBR\"", nullable = false)
    private String ccaNumbr;

    @Column(name = "\"CCA_DESCR\"")
    private String ccaDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_ENGFMT\"")
    private _FMT ccaEngfmt = _FMT.R;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_RAWFMT\"")
    private _FMT ccaRawfmt = _FMT.U;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_RADIX\"")
    private _RADIX ccaRadix = _RADIX.D;

    @Column(name = "\"CCA_UNIT\"")
    private String ccaUnit;

    @Column(name = "\"CCA_NCURVE\"")
    private int ccaNcurve;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Calibration curve file";
}
