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
@Table(schema = "scos_schema", name = "\"PST\"")
public class PST {

    public enum COLUMNS {
        PST_NAME,
        PST_DESCR
    }

    @Id
    @Column(name = "\"PST_NAME\"", nullable = false)
    private String pstName;

    @Column(name = "\"PST_DESCR\"")
    private String pstDescr;

    public static final int MAX_COLUMN = 2;

    public static final String DESCRIPTION = "Command/sequence parameter set file";

}
