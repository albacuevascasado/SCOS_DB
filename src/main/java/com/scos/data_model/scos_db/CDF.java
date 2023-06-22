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
@Table(schema = "scos_schema", name = "\"CDF\"")
public class CDF implements Serializable {
    @Id
    @Column(name = "\"CDF_CNAME\"", nullable = false)
    private String cdfCname;

    @Column(name = "\"CDF_ELTYPE\"", nullable = false)
    private char cdfEltype;

    @Column(name = "\"CDF_DESCR\"")
    private String cdfDescr;

    @Column(name = "\"CDF_ELLEN\"", nullable = false)
    private BigInteger cdfEllen;

    @Id
    @Column(name = "\"CDF_BIT\"", nullable = false)
    private BigInteger cdfBit;

    @Id
    @Column(name = "\"CDF_GRPSIZE\"", nullable = false)
    private BigInteger cdfGrpsize = BigInteger.ZERO;

    @Column(name = "\"CDF_PNAME\"")
    private String cdfPname;

    @Column(name = "\"CDF_INTER\"")
    private char cdfInter = 'R';

    @Column(name = "\"CDF_VALUE\"")
    private String cdfValue;

    @Column(name = "\"CDF_TMID\"")
    private String cdfTmid;

}
