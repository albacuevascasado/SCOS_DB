package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"OCP\"")
public class OCP implements Serializable {

    public enum COLUMNS {
        OCP_NAME,
        OCP_POS,
        OCP_TYPE,
        OCP_LVALU,
        OCP_HVALU,
        OCP_RLCHK,
        OCP_VALPAR
    }

    @Id
    @Column(name = "\"OCP_NAME\"", nullable = false)
    private String ocpName;

    @Id
    @Column(name = "\"OCP_POS\"", nullable = false)
    private Integer ocpPos;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"OCP_TYPE\"", nullable = false)
    private OcpType ocpType;

    @Column(name = "\"OCP_LVALU\"")
    private String ocpLvalu;

    @Column(name = "\"OCP_HVALU\"")
    private String ocpHvalu;

    @Column(name = "\"OCP_RLCHK\"")
    private String ocpRlchk;

    @Column(name = "\"OCP_VALPAR\"")
    private Integer ocpValpar = 1;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Out of limits definition file";

    public enum OcpType {
        S,
        H,
        D,
        C,
        E
    }

}
