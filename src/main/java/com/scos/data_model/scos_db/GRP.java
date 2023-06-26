package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"GRP\"")
public class GRP {

    @Id
    @Column(name = "\"GRP_NAME\"", nullable = false)
    private String grpName;

    @Column(name = "\"GRP_DESCR\"", nullable = false)
    private String grpDescr;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"GRP_GTYPE\"", nullable = false)
    private GrpGtype grpGtype;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Parameter and packet group characteristic file";

    public enum GrpGtype {
        PA,
        PK
    }

}

