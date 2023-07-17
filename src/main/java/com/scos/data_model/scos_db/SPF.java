package com.scos.data_model.scos_db;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"SPF\"")
public class SPF {

    public enum COLUMNS {
        SPF_NUMBE,
        SPF_HEAD,
        SPF_NPAR,
        SPF_UPUN
    }

    @Id
    @Column(name = "\"SPF_NUMBE\"", nullable = false)
    private String spfNumbe;

    @Column(name = "\"SPF_HEAD\"")
    private String spfHead;

    @Column(name = "\"SPF_NPAR\"", nullable = false)
    private Integer spfNpar;

    @Column(name = "\"SPF_UPUN\"")
    private Integer spfUpun;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Scrolling display";

}
