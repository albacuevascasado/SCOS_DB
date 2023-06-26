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

    @Id
    @Column(name = "\"CCA_NUMBR\"", nullable = false)
    private String ccaNumbr;

    @Column(name = "\"CCA_DESCR\"")
    private String ccaDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_ENGFMT\"")
    private _FMT ccaEngfmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_RAWFMT\"")
    private _FMT ccaRawfmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCA_RADIX\"")
    private _RADIX ccaRadix;

    @Column(name = "\"CCA_UNIT\"")
    private String ccaUnit;

    @Column(name = "\"CCA_NCURVE\"")
    private int ccaNcurve;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Calibration curve file";
}
