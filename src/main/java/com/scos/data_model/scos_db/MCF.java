package com.scos.data_model.scos_db;

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
@Table(schema = "scos_schema", name = "\"MCF\"")
public class MCF {

    public enum COLUMNS {
        MCF_IDENT,
        MCF_DESCR,
        MCF_POL1,
        MCF_POL2,
        MCF_POL3,
        MCF_POL4,
        MCF_POL5
    }

    @Id
    @Column(name = "\"MCF_IDENT\"", nullable = false)
    private String mcfIdent;

    @Column(name = "\"MCF_DESCR\"")
    private String mcfDescr;

    @Column(name = "\"MCF_POL1\"", nullable = false)
    private String mcfPol1;

    @Column(name = "\"MCF_POL2\"")
    private String mcfPol2 = "0";

    @Column(name = "\"MCF_POL3\"")
    private String mcfPol3 = "0";

    @Column(name = "\"MCF_POL4\"")
    private String mcfPol4 = "0";

    @Column(name = "\"MCF_POL5\"")
    private String mcfPol5 = "0";

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Polynomial calibration curve file";
}
