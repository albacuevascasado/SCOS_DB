package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CPS\"")
public class CPS implements Serializable {

    public enum COLUMNS {
        CPS_NAME,
        CPS_PAR,
        CPS_BIT
    }

    @Id
    @Column(name = "\"CPS_NAME\"", nullable = false)
    private String cpsName;

    @Column(name = "\"CPS_PAR\"", nullable = false)
    private String cpsPar;

    @Id
    @Column(name = "\"CPS_BIT\"", nullable = false)
    private Integer cpsBit;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Command/sequence parameter set file";

}
