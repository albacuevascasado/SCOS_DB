package com.scos.data_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "\"CCF_CTYPE\"")
    private String ccfCtype;

    @Column(name = "\"CCF_CRITICAL\"")
    private char ccfCritical = 'N';

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
    private char ccfPlan = 'N';

    @Column(name = "\"CCF_EXEC\"")
    private char ccfExec = 'Y';

    @Column(name = "\"CCF_ILSCOPE\"")
    private char ccfIlscope = 'N';

    @Column(name = "\"CCF_ILSTAGE\"")
    private char ccfIlstage = 'C';

    @Column(name = "\"CCF_SUBSYS\"")
    private int ccfSubsys;

    @Column(name = "\"CCF_HIPRI\"")
    private char ccfHipri = 'N';

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
}
