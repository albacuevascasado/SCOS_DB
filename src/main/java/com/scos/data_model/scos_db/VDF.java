package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"VDF\"")
public class VDF implements Serializable {

    @Id
    @Column(name = "\"VDF_NAME\"", nullable = false)
    private String vdfName;

    @Column(name = "\"VDF_COMMENT\"")
    private String vdfComment;

    @Column(name = "\"VDF_DOMAINID\"")
    private int vdfDomainid;

    @Id
    @Column(name = "\"VDF_RELEASE\"")
    private int vdfRelease = 0;

    @Id
    @Column(name = "\"VDF_ISSUE\"")
    private int vdfIssue = 0;

    public static final int MAX_COLUMN = 5;

    public static final String DESCRIPTION = "Database version definition file";

}
