package com.scos.data_model.scos_db;

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
@Table(schema = "scos_schema", name = "\"PRF\"")
public class PRF {

    public enum COLUMNS {
        PRF_NUMBR,
        PRF_DESCR,
        PRF_INTER,
        PRF_DSPFMT,
        PRF_RADIX,
        PRF_NRANGE,
        PRF_UNIT
    }

    @Id
    @Column(name = "\"PRF_NUMBR\"", nullable = false)
    private String prfNumbr;

    @Column(name = "\"PRF_DESCR\"")
    private String prfDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PRF_INTER\"")
    private PrfInter prfInter = PrfInter.R;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PRF_DSPFMT\"")
    private PrfDspfmt prfDspfmt = PrfDspfmt.U;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PRF_RADIX\"")
    private _RADIX prfRadix = _RADIX.D;

    @Column(name = "\"PRF_NRANGE\"")
    private Integer prfNrange;

    @Column(name = "\"PRF_UNIT\"")
    private String prfUnit;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Parameter range file";

    public enum PrfInter {
        R,
        E
    }

    public enum PrfDspfmt {
        A,
        I,
        U,
        R,
        T,
        D
    }

}
