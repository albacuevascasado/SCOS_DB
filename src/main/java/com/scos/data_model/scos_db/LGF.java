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
@Table(schema = "scos_schema", name = "\"LGF\"")
public class LGF {

    @Id
    @Column(name = "\"LGF_IDENT\"", nullable = false)
    private String lgfIdent;

    @Column(name = "\"LGF_DESCR\"")
    private String lgfDescr;

    @Column(name = "\"LGF_POL1\"", nullable = false)
    private String lgfPol1;

    @Column(name = "\"LGF_POL2\"")
    private String lgfPol2;

    @Column(name = "\"LGF_POL3\"")
    private String lgfPol3;

    @Column(name = "\"LGF_POL4\"")
    private String lgfPol4;

    @Column(name = "\"LGF_POL5\"")
    private String lgfPol5;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Logarithmic calibration curve definition";

}
