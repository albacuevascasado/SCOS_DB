package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._RADIX;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PCDF\"")
public class PCDF implements Serializable {

    public enum COLUMNS {
        PCDF_TCNAME,
        PCDF_DESC,
        PCDF_TYPE,
        PCDF_LEN,
        PCDF_BIT,
        PCDF_PNAME,
        PCDF_VALUE,
        PCDF_RADIX
    }

    @Id
    @Column(name = "\"PCDF_TCNAME\"", nullable = false)
    private String pcdfTcname;

    @Column(name = "\"PCDF_DESC\"")
    private String pcdfDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCDF_TYPE\"", nullable = false)
    private PcdfType pcdfType;

    @Column(name = "\"PCDF_LEN\"", nullable = false)
    private Integer pcdfLen;

    @Id
    @Column(name = "\"PCDF_BIT\"", nullable = false)
    private Integer pcdfBit;

    @Column(name = "\"PCDF_PNAME\"")
    private String pcdfPname;

    @Column(name = "\"PCDF_VALUE\"", nullable = false)
    private String pcdfValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCDF_RADIX\"")
    private _RADIX pcdfRadix = _RADIX.H;

    public static final int MAX_COLUMN = 8;

    public static final String DESCRIPTION = "Packet header structure definition file";

    public enum PcdfType {
        F,
        A,
        T,
        S,
        K,
        P
    }

}
