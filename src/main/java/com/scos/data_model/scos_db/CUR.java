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
@Table(schema = "scos_schema", name = "\"CUR\"")
public class CUR implements Serializable {

    public enum COLUMNS {
        CUR_PNAME,
        CUR_POS,
        CUR_RLCHK,
        CUR_VALPAR,
        CUR_SELECT
    }

    @Id
    @Column(name = "\"CUR_PNAME\"", nullable = false)
    private String curPname;

    @Id
    @Column(name = "\"CUR_POS\"", nullable = false)
    private Integer curPos;

    @Column(name = "\"CUR_RLCHK\"", nullable = false)
    private String curRlchk;

    @Column(name = "\"CUR_VALPAR\"", nullable = false)
    private Integer curValpar;

    @Column(name = "\"CUR_SELECT\"", nullable = false)
    private String curSelect;

    public static final int MAX_COLUMN = 5;

    public static final String DESCRIPTION = "Calibration definition conditional selection";

}
