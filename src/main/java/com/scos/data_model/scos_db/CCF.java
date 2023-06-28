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
    private int ccfType;

    @Column(name = "\"CCF_STYPE\"")
    private int ccfStype;

    @Column(name = "\"CCF_APID\"")
    private int ccfApid;

    @Column(name = "\"CCF_NPARS\"")
    private int ccfNpars;

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
    private int ccfSubsys;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CCF_HIPRI\"")
    private _YN ccfHipri = _YN.N;

    @Column(name = "\"CCF_MAPID\"")
    private int ccfMapid;

    @Column(name = "\"CCF_DEFSET\"")
    private String ccfDefset;

    @Column(name = "\"CCF_RAPID\"")
    private int ccfRapid;

    @Column(name = "\"CCF_ACK\"")
    private int ccfAck;

    @Column(name = "\"CCF_SUBSCHEDID\"")
    private int ccfSubschedid;

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
