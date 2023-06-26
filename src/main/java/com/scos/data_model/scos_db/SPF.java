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

    @Id
    @Column(name = "\"SPF_NUMBE\"", nullable = false)
    private String spfNumbe;

    @Column(name = "\"SPF_HEAD\"")
    private String spfHead;

    @Column(name = "\"SPF_NPAR\"", nullable = false)
    private int spfNpar;

    @Column(name = "\"SPF_UPUN\"")
    private int spfUpun;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Scrolling display";

}
