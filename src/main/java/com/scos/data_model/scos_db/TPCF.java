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
@Table(schema = "scos_schema", name = "\"TPCF\"")
public class TPCF {

    @Id
    @Column(name = "\"TPCF_SPID\"", nullable = false)
    private BigInteger tpcfSpid;

    @Column(name = "\"TPCF_NAME\"")
    private String tpcfName;

    @Column(name = "\"TPCF_SIZE\"")
    private BigInteger tpcfSize;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Telemetry packets characteristic file";

}
