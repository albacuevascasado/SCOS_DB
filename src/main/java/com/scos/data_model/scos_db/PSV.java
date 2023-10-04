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
@Table(schema = "scos_schema", name = "\"PSV\"")
public class PSV {

    public enum COLUMNS {
        PSV_NAME,
        PSV_PVSID,
        PSV_DESCR
    }

    @Column(name = "\"PSV_NAME\"", nullable = false)
    private String psvName;

    @Id
    @Column(name = "\"PSV_PVSID\"", nullable = false)
    private String psvPvsid;

    @Column(name = "\"PSV_DESCR\"")
    private String psvDescr;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Command/sequence parameter value set file";


}
