package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PLF\"")
public class PLF implements Serializable {

    public enum COLUMNS {
        PLF_NAME,
        PLF_SPID,
        PLF_OFFBY,
        PLF_OFFBI,
        PLF_NBOCC,
        PLF_LGOCC,
        PLF_TIME,
        PLF_TDOCC
    }

    @Id
    @Column(name = "\"PLF_NAME\"", nullable = false)
    private String plfName;

    @Id
    @Column(name = "\"PLF_SPID\"", nullable = false)
    private BigInteger plfSpid;

    @Column(name = "\"PLF_OFFBY\"", nullable = false)
    private int plfOffby;

    @Column(name = "\"PLF_OFFBI\"", nullable = false)
    private int plfOffbi;

    @Column(name = "\"PLF_NBOCC\"")
    private int plfNbocc = 1 ;

    @Column(name = "\"PLF_LGOCC\"")
    private int plfLgocc = 0;

    @Column(name = "\"PLF_TIME\"")
    private BigInteger plfTime = BigInteger.ZERO;

    @Column(name = "\"PLF_TDOCC\"")
    private BigInteger plfTdocc = BigInteger.ONE;

    public static final int MAX_COLUMN = 8;

    public static final String DESCRIPTION = "Parameters location file";

}
