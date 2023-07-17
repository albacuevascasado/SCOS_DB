package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._YN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PID\"")
public class PID implements Serializable {

    public enum COLUMNS {
        PID_TYPE,
        PID_STYPE,
        PID_APID,
        PID_PI1_VAL,
        PID_PI2_VAL,
        PID_SPID,
        PID_DESCR,
        PID_UNIT,
        PID_TPSD,
        PID_DFHSIZE,
        PID_TIME,
        PID_INTER,
        PID_VALID,
        PID_CHECK,
        PID_EVENT,
        PID_EVID

    }

    @Id
    @Column(name = "\"PID_TYPE\"", nullable = false)
    private Integer pidType;

    @Id
    @Column(name = "\"PID_STYPE\"", nullable = false)
    private Integer pidStype;

    @Id
    @Column(name = "\"PID_APID\"", nullable = false)
    private Integer pidApid;

    @Id
    @Column(name = "\"PID_PI1_VAL\"")
    private BigInteger pidPi1Val = BigInteger.ZERO;

    @Id
    @Column(name = "\"PID_PI2_VAL\"")
    private BigInteger pidPi2Val = BigInteger.ZERO;

    @Id
    @Column(name = "\"PID_SPID\"", nullable = false)
    private BigInteger pidSpid;

    @Column(name = "\"PID_DESCR\"")
    private String pidDescr;

    @Column(name = "\"PID_UNIT\"")
    private String pidUnit;

    @Column(name = "\"PID_TPSD\"")
    private BigInteger pidTpsd = BigInteger.valueOf(-1);

    @Column(name = "\"PID_DFHSIZE\"", nullable = false)
    private Integer pidDfhsize;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PID_TIME\"")
    private _YN pidTime = _YN.N;

    @Column(name = "\"PID_INTER\"")
    private BigInteger pidInter;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PID_VALID\"")
    private _YN pidValid = _YN.Y;

    @Column(name = "\"PID_CHECK\"")
    private Integer pidCheck = Integer.valueOf(0);

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PID_EVENT\"")
    private PidEvent pidEvent = PidEvent.N;

    @Column(name = "\"PID_EVID\"")
    private String pidEvid;

    public static final int MAX_COLUMN = 16;

    public static final String DESCRIPTION = "Packet identification";

    public enum PidEvent {
        N,
        I,
        W,
        A
    }

}
