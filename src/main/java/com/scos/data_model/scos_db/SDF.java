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
@Table(schema = "scos_schema", name = "\"SDF\"")
public class SDF implements Serializable {

    public enum COLUMNS {
        SDF_SQNAME,
        SDF_ENTRY,
        SDF_ELEMID,
        SDF_POS,
        SDF_PNAME,
        SDF_FTYPE,
        SDF_VTYPE,
        SDF_VALUE,
        SDF_VALSET,
        SDF_REPPOS
    }

    @Id
    @Column(name = "\"SDF_SQNAME\"", nullable = false)
    private String sdfSqname;

    @Id
    @Column(name = "\"SDF_ENTRY\"", nullable = false)
    private Integer sdfEntry;

    @Column(name = "\"SDF_ELEMID\"", nullable = false)
    private String sdfElemid;

    @Id
    @Column(name = "\"SDF_POS\"", nullable = false)
    private Integer sdfPos;

    @Id
    @Column(name = "\"SDF_PNAME\"", nullable = false)
    private String sdfPname;

    @Column(name = "\"SDF_FTYPE\"")
    private SdfFtype sdfFtype = SdfFtype.E;

    @Column(name = "\"SDF_VTYPE\"", nullable = false)
    private SdfVtype sdfVtype;

    @Column(name = "\"SDF_VALUE\"")
    private String sdfValue;

    @Column(name = "\"SDF_VALSET\"")
    private String sdfValset;

    @Column(name = "\"SDF_REPPOS\"")
    private Integer sdfReppos;

    public static final int MAX_COLUMN = 10;

    public static final String DESCRIPTION = "Sequence details file";

    public enum SdfFtype {
        F,
        E
    }

    public enum SdfVtype {
        R,
        E,
        F,
        P,
        S,
        D
    }

}
