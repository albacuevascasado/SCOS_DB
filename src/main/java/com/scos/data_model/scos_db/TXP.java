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
@Table(schema = "scos_schema", name = "\"TXP\"")
public class TXP implements Serializable {

    public enum COLUMNS {
        TXP_NUMBR,
        TXP_FROM,
        TXP_TO,
        TXP_ALTXT
    }

    @Id
    @Column(name = "\"TXP_NUMBR\"", nullable = false)
    private String txpNumbr;

    @Id
    @Column(name = "\"TXP_FROM\"", nullable = false)
    private String txpFrom;

    @Column(name = "\"TXP_TO\"", nullable = false)
    private String txpTo;

    @Column(name = "\"TXP_ALTXT\"", nullable = false)
    private String txpAltxt;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Text string calibration curves parameters";

}
