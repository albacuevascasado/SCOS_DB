package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "scos_schema" , name = "\"CVS\"")
public class CVS {

    @Id
    @Column(name = "\"CVS_ID\"", nullable = false)
    private int cvsId;

    @Column(name = "\"CVS_TYPE\"", nullable = false)
    private char cvsType;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"CVS_SOURCE\"", nullable = false)
    private CvsSource cvsSource;

    @Column(name = "\"CVS_START\"", nullable = false)
    private int cvsStart;

    @Column(name = "\"CVS_INTERVAL\"", nullable = false)
    private int cvsInterval;

    @Column(name = "\"CVS_SPID\"")
    private int cvsSpid;

    @Column(name = "\"CVS_UNCERTAINTY\"")
    private int cvsUncertainty;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Command verification stage file";

    public enum CvsSource {
        R,
        V
    }

}