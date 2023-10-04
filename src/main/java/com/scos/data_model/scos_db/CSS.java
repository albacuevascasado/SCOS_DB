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
@Table(schema = "scos_schema", name = "\"CSS\"")
public class CSS implements Serializable {

    public enum COLUMNS {
        CSS_SQNAME,
        CSS_COMM,
        CSS_ENTRY,
        CSS_TYPE,
        CSS_ELEMID,
        CSS_NPARS,
        CSS_MANDISP,
        CSS_RELTYPE,
        CSS_RELTIME,
        CSS_EXTIME,
        CSS_PREVREL,
        CSS_GROUP,
        CSS_BLOCK,
        CSS_ILSCOPE,
        CSS_ILSTAGE,
        CSS_DYNPTV,
        CSS_STAPTV,
        CSS_CEV
    }

    @Id
    @Column(name = "\"CSS_SQNAME\"", nullable = false)
    private String cssSqname;

    @Column(name = "\"CSS_COMM\"")
    private String cssComm;

    @Id
    @Column(name = "\"CSS_ENTRY\"", nullable = false)
    private Integer cssEntry;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_TYPE\"", nullable = false)
    private CssType cssType;

    @Column(name = "\"CSS_ELEMID\"")
    private String cssElemid;

    @Column(name = "\"CSS_NPARS\"")
    private Integer cssNpars;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_MANDISP\"")
    private _YN cssMandisp = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_RELTYPE\"")
    private CssPrev_Rel cssReltype = CssPrev_Rel.R;

    @Column(name = "\"CSS_RELTIME\"")
    private String cssReltime;

    @Column(name = "\"CSS_EXTIME\"")
    private String cssExtime;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_PREVREL\"")
    private CssPrev_Rel cssPrevRel = CssPrev_Rel.R;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_GROUP\"")
    private CssGroup_Block cssGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_BLOCK\"")
    private CssGroup_Block cssBlock;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_ILSCOPE\"")
    private CssIlscope cssIlscope;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_ILSTAGE\"")
    private CssIlstage cssIlstage;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_DYNPTV\"")
    private _YN cssDynptv = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_STAPTV\"")
    private _YN cssStaptv = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSS_CEV\"")
    private _YN cssCev = _YN.N;

    public static final int MAX_COLUMN = 18;

    public static final String DESCRIPTION = "Command sequence set";

    public enum CssType {
        C,
        S,
        T,
        F,
        P
    }

    public enum CssPrev_Rel {
        R,
        A
    }

    public enum CssGroup_Block {
        S,
        M,
        E
    }

    public enum CssIlscope {
        G,
        L,
        S,
        B,
        F,
        T,
        N
    }

    public enum CssIlstage {
        R,
        U,
        O,
        A,
        C
    }

}
