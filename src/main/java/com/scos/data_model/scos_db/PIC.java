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
@Table(schema = "scos_schema", name = "\"PIC\"")
public class PIC implements Serializable {

    public enum COLUMNS {
        PIC_TYPE,
        PIC_STYPE,
        PIC_PI1_OFF,
        PIC_PI1_WID,
        PIC_PI2_OFF,
        PIC_PI2_WID,
        PIC_APID
    }

    @Id
    @Column(name = "\"PIC_TYPE\"", nullable = false)
    private Integer picType;

    @Id
    @Column(name = "\"PIC_STYPE\"", nullable = false)
    private Integer picStype;

    @Column(name = "\"PIC_PI1_OFF\"", nullable = false)
    private Integer picPi1Off;

    @Column(name = "\"PIC_PI1_WID\"", nullable = false)
    private Integer picPi1Wid;

    @Column(name = "\"PIC_PI2_OFF\"", nullable = false)
    private Integer picPi2Off;

    @Column(name = "\"PIC_PI2_WID\"", nullable = false)
    private Integer picPi2Wid;

    @Id
    @Column(name = "\"PIC_APID\"")
    private Integer picApid = 99999;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Packet identification criteria file";

}
