package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CDF\"")
public class CDF implements Serializable {

    @Id
    @Column(name = "\"CDF_CNAME\"", nullable = false)
    private String cdfCname;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CDF_ELTYPE\"", nullable = false)
    private CdfType cdfEltype;

    @Column(name = "\"CDF_DESCR\"")
    private String cdfDescr;

    @Column(name = "\"CDF_ELLEN\"", nullable = false)
    private int cdfEllen;

    @Id
    @Column(name = "\"CDF_BIT\"", nullable = false)
    private int cdfBit;

    @Id
    @Column(name = "\"CDF_GRPSIZE\"", nullable = false)
    private int cdfGrpsize = 0;

    @Column(name = "\"CDF_PNAME\"")
    private String cdfPname;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CDF_INTER\"")
    private CdfInter cdfInter = CdfInter.R;

    @Column(name = "\"CDF_VALUE\"")
    private String cdfValue;

    @Column(name = "\"CDF_TMID\"")
    private String cdfTmid;

    public static final int MAX_COLUMN = 10;

    public static final String DESCRIPTION = "Command definition file";

    public enum CdfType {
        A,
        F,
        E
    }

    public enum CdfInter {
        R,
        E,
        D,
        T
    }
}
