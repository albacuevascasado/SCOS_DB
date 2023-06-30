package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"GRPK\"")
public class GRPK implements Serializable {

    public enum COLUMNS {
        GRPK_GNAME,
        GRPK_PKSPID
    }

    @Id
    @Column(name = "\"GRPK_GNAME\"", nullable = false)
    private String grpkGname;

    @Id
    @Column(name = "\"GRPK_PKSPID\"", nullable = false)
    private long grpkPkspid;

    public static final int MAX_COLUMN = 2;

    public static final String DESCRIPTION = "Packet group file";

}
