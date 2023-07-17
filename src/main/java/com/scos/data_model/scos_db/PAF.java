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
@Table(schema = "scos_schema", name = "\"PAF\"")
public class PAF {

    public enum COLUMNS{
        PAF_NUMBR,
        PAF_DESCR,
        PAF_RAWFMT,
        PAF_NALIAS
    }

    @Id
    @Column(name = "\"PAF_NUMBR\"", nullable = false)
    private String PAF_NUMBR;

    @Column(name = "\"PAF_DESCR\"")
    private String PAF_DESCR;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PAF_RAWFMT\"")
    private _FMT PAF_RAWFMT = _FMT.U;

    @Column(name = "\"PAF_NALIAS\"")
    private Integer PAF_NALIAS;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Parameter alias file";

}
