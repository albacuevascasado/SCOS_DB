package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "mps_schema", name = "\"T_SYS_SCHEDULING_PROVA\"")
public class SysSchedulingProva {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"S_SYS_SCHEDULING_ID_PROVA\"")
    @SequenceGenerator(schema = "mps_schema", name = "\"S_SYS_SCHEDULING_ID_PROVA\"", sequenceName = "\"S_SYS_SCHEDULING_ID_PROVA\"", allocationSize = 1)
    @Id
    @Column(name = "\"SCHEDULING_ID\"", nullable = false)
    private BigInteger schedulingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"SCHEDULING_TYPE\"", nullable = false)
//    private SchedulingType schedulingType = SchedulingType.AUTO; //by default
    private SchedulingType schedulingType;

    public enum SchedulingType {
        AUTO,
        MANUAL
    }

}
