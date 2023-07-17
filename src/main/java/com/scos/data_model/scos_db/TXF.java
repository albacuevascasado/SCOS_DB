package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._FMT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"TXF\"")
public class TXF {

    public enum COLUMNS {
        TXF_NUMBR,
        TXF_DESCR,
        TXF_RAWFMT,
        TXF_NALIAS
    }

    @Id
    @Column(name = "\"TXF_NUMBR\"", nullable = false)
    private String txfNumbr;

    @Column(name = "\"TXF_DESCR\"")
    private String txfDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"TXF_RAWFMT\"", nullable = false)
    private _FMT txfRawfmt;

    @Column(name = "\"TXF_NALIAS\"")
    private Integer txfNalias;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Text string calibration curves file";

}
