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
@Table(schema = "scos_schema", name = "\"DST\"")
public class DST {

    public enum COLUMNS {
        DST_APID,
        DST_ROUTE
    }

    @Id
    @Column(name = "\"DST_APID\"", nullable = false)
    private Integer dstApid;

    @Column(name = "\"DST_ROUTE\"", nullable = false)
    private String dstRoute;

    public static final int MAX_COLUMN = 2;

    public static final String DESCRIPTION = "";

}
