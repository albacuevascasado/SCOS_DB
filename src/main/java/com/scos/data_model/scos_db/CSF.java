package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._YN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CSF\"")
public class CSF {

    public enum COLUMNS {
        CSF_NAME (0),
        CSF_DESC (1),
        CSF_DESC2 (2),
        CSF_IFTT (3),
        CSF_NFPARS (4),
        CSF_ELEMS (5),
        CSF_CRITICAL (6),
        CSF_PLAN (7),
        CSF_EXEC (8),
        CSF_SUBSYS (9),
        CSF_GENTIME (10),
        CSF_DOCNAME (11),
        CSF_ISSUE (12),
        CSF_DATE (13),
        CSF_DEFSET (14),
        CSF_SUBSCHEDID (15);

        private int hierarchy;

        private COLUMNS (final int hierarchy) {
            this.hierarchy = hierarchy;
        }

        public int getHierarchy() {
            return getHierarchy();
        }
    }

    @Id
    @Column(name = "\"CSF_NAME\"", nullable = false)
    private String csfName;

    @Column(name = "\"CSF_DESC\"", nullable = false)
    private String csfDesc;

    @Column(name = "\"CSF_DESC2\"")
    private String csfDesc2;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSF_IFTT\"")
    private CsfIftt csfIftt = CsfIftt.N;

    @Column(name = "\"CSF_NFPARS\"")
    private int csfNfpars;

    @Column(name = "\"CSF_ELEMS\"")
    private int csfElems;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSF_CRITICAL\"")
    private _YN csfCritical = _YN.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSF_PLAN\"")
    private CsfPlan csfPlan = CsfPlan.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CSF_EXEC\"")
    private _YN csfExec = _YN.Y;

    @Column(name = "\"CSF_SUBSYS\"")
    private int csfSubsys;

    @Column(name = "\"CSF_GENTIME\"")
    private String csfGentime;

    @Column(name = "\"CSF_DOCNAME\"")
    private String csfDocname;

    @Column(name = "\"CSF_ISSUE\"")
    private String csfIssue;

    @Column(name = "\"CSF_DATE\"")
    private String csfDate;

    @Column(name = "\"CSF_DEFSET\"")
    private String csfDefset;

    @Column(name = "\"CSF_SUBSCHEDID\"")
    private int csfSubschedid;

    public static final int MAX_COLUMN = 16;

    public static final String DESCRIPTION = "";

    public enum CsfIftt {
        Y,
        B,
        N
    }

    public enum CsfPlan {
        A,
        F,
        S,
        N
    }

}
