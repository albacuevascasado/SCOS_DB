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
@Table(schema = "scos_schema", name = "\"PAS\"")
public class PAS implements Serializable {

    public enum COLUMNS {
        PAS_NUMBR,
        PAS_ALTXT,
        PAS_ALVAL
    }

    @Id
    @Column(name = "\"PAS_NUMBR\"", nullable = false)
    private String pasNumbr;

    @Id
    @Column(name = "\"PAS_ALTXT\"", nullable = false)
    private String pasAltxt;

    @Id
    @Column(name = "\"PAS_ALVAL\"", nullable = false)
    private String pasAlval;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Parameter alias set";

}
