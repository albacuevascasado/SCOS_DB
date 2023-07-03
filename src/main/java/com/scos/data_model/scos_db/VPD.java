package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._YN;
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
@Table(schema = "scos_schema", name = "\"VPD\"")
public class VPD implements Serializable {

    public enum COLUMNS {
        VPD_TPSD,
        VPD_POS,
        VPD_NAME,
        VPD_GRPSIZE,
        VPD_FIXREP,
        VPD_CHOICE,
        VPD_PIDREF,
        VPD_DISDESC,
        VPD_WIDTH,
        VPD_JUSTIFY,
        VPD_NEWLINE,
        VPD_DCHAR,
        VPD_FORM,
        VPD_OFFSET

    }

    @Id
    @Column(name = "\"VPD_TPSD\"", nullable = false)
    private BigInteger vpdTpsd;

    @Id
    @Column(name = "\"VPD_POS\"", nullable = false)
    private int vpdPos;

    @Column(name = "\"VPD_NAME\"", nullable = false)
    private String vpdName;

    @Column(name = "\"VPD_GRPSIZE\"")
    private int vpdGrpsize = 0;

    @Column(name = "\"VPD_FIXREP\"")
    private int vpdFixrep = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"VPD_CHOICE\"")
    private _YN vpdChoice = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"VPD_PIDREF\"")
    private _YN vpdPidref = _YN.N;

    @Column(name = "\"VPD_DISDESC\"")
    private String vpdDisdesc;

    @Column(name = "\"VPD_WIDTH\"", nullable = false)
    private int vpdWidth;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"VPD_JUSTIFY\"")
    private VpdJustify vpdJustify = VpdJustify.L;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"VPD_NEWLINE\"")
    private _YN vpdNewline = _YN.N;

    @Column(name = "\"VPD_DCHAR\"")
    private int vpdDchar = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"VPD_FORM\"")
    private VpdForm vpdForm = VpdForm.N;

    @Column(name = "\"VPD_OFFSET\"")
    private BigInteger vpdOffset = BigInteger.ZERO;

    public static final int MAX_COLUMN = 14;

    public static final String DESCRIPTION = "Variable packet definition file";

    public enum VpdJustify {
        L,
        R,
        C
    }

    public enum VpdForm {
        B,
        O,
        D,
        H,
        N
    }
}
