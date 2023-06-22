package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

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

    @Column(name = "\"CAF_ENGFMT\"", nullable = false)
    private char cafEngfmt;

    @Column(name = "\"CAF_RAWFMT\"", nullable = false)
    private char cafRawfmt;

    @Column(name = "\"CAF_RADIX\"")
    private char cafRadix;

    @Column(name = "\"CAF_UNIT\"")
    private String cafUnit;

    @Column(name = "\"CAF_NCURVE\"")
    private BigInteger cafNcurve;

    @Column(name = "\"CAF_INTER\"")
    private char cafInter = 'F';

    private static final int nColumn = 8; // == cte

}
