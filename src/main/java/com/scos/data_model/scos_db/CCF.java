package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CCF\"")
public class CCF {

    public enum COLUMNS {
        CCF_CNAME,
        CCF_DESCR,
        CCF_DESCR2,
        CCF_CTYPE,
        CCF_CRITICAL,
        CCF_PKTID,
        CCF_TYPE,
        CCF_STYPE,
        CCF_APID,
        CCF_NPARS,
        CCF_PLAN,
        CCF_EXEC,
        CCF_ILSCOPE,
        CCF_ILSTAGE,
        CCF_SUBSYS,
        CCF_HIPRI,
        CCF_MAPID,
        CCF_DEFSET,
        CCF_RAPID,
        CCF_ACK,
        CCF_SUBSCHEDID

    }

    @Id
    @Column(name = "\"CCF_CNAME\"", nullable = false)
    private String ccfCname;

    @Column(name = "\"CCF_DESCR\"", nullable = false)
    private String ccfDescr;

    @Column(name = "\"CCF_DESCR2\"")
    private String ccfDescr2;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_CTYPE\"")
    private CcfCtype ccfCtype;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_CRITICAL\"")
    private _YN ccfCritical = _YN.N;

    @Column(name = "\"CCF_PKTID\"", nullable = false)
    private String ccfPktid;

    @Column(name = "\"CCF_TYPE\"")
    private Integer ccfType;

    @Column(name = "\"CCF_STYPE\"")
    private Integer ccfStype;

    @Column(name = "\"CCF_APID\"")
    private Integer ccfApid;

    @Column(name = "\"CCF_NPARS\"")
    private Integer ccfNpars;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_PLAN\"")
    private CcfPlan ccfPlan = CcfPlan.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_EXEC\"")
    private _YN ccfExec = _YN.Y;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_ILSCOPE\"")
    private CcfScope ccfIlscope;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_ILSTAGE\"")
    private CcfStage ccfIlstage = CcfStage.C;

    @Column(name = "\"CCF_SUBSYS\"")
    private Integer ccfSubsys;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_HIPRI\"")
    private _YN ccfHipri = _YN.N;

    @Column(name = "\"CCF_MAPID\"")
    private Integer ccfMapid;

    @Column(name = "\"CCF_DEFSET\"")
    private String ccfDefset;

    @Column(name = "\"CCF_RAPID\"")
    private Integer ccfRapid;

    @Column(name = "\"CCF_ACK\"")
    private Integer ccfAck;

    @Column(name = "\"CCF_SUBSCHEDID\"")
    private Integer ccfSubschedid;

    public static final int MAX_COLUMN = 21;

    public static final String DESCRIPTION = "Command characteristic file";

    public enum CcfCtype {
        R,
        F,
        S,
        T,
        N
    }

    public enum CcfPlan {
        A,
        F,
        S,
        N
    }

    public enum CcfScope {
        G,
        L,
        S,
        B,
        F,
        T,
        N
    }

    public enum CcfStage {
        R,
        U,
        O,
        A,
        C
    }
}
