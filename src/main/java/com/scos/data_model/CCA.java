package com.scos.data_model;

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
@Table(schema = "scos_schema", name = "\"CCA\"")
public class CCA {
    @Id
    @Column(name = "\" CCA_NUMBR\"", nullable = false)
    private String ccaNumbr;

    @Column(name = "\" CCA_DESCR\"")
    private String ccaDescr;

    @Column(name = "\" CCA_ENGFMT\"")
    private char ccaEngfmt = 'R';
    
    @Column(name = "\" CCA_RAWFMT\"")
    private char ccaRawfmt = 'U';

    @Column(name = "\" CCA_RADIX\"")
    private char ccaRadix = 'D';

    @Column(name = "\" CCA_UNIT\"")
    private String ccaUnit;

    @Column(name = "\" CCA_NCURVE\"")
    private BigInteger ccaNcurve;
}
