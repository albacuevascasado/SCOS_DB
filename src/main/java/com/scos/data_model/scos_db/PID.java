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

    @Id
    @Column(name = "\"PID_TYPE\"", nullable = false)
    private int pidType;

    @Id
    @Column(name = "\"PID_STYPE\"", nullable = false)
    private int pidStype;

    @Id
    @Column(name = "\"PID_APID\"", nullable = false)
    private int pidApid;

    @Id
    @Column(name = "\"PID_PI1_VAL\"")
    private BigInteger pidPi1Val = BigInteger.valueOf(0);

    @Id
    @Column(name = "\"PID_PI2_VAL\"")
    private BigInteger pidPi2Val = BigInteger.valueOf(0);

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
    private int pidDfhsize;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PID_TIME\"")
    private _YN pidTime = _YN.N;

    @Column(name = "\"PID_INTER\"")
    private BigInteger pidInter;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PID_VALID\"")
    private _YN pidValid = _YN.Y;

    @Column(name = "\"PID_CHECK\"")
    private int pidCheck = Integer.valueOf(0);

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
