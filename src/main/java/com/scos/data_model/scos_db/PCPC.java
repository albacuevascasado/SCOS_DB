package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PCPC\"")
public class PCPC {

    public enum COLUMNS {
        PCPC_PNAME,
        PCPC_DESC,
        PCPC_CODE
    }

    @Id
    @Column(name = "\"PCPC_PNAME\"", nullable = false)
    private String pcpcPname;

    @Column(name = "\"PCPC_DESC\"", nullable = false)
    private String pcpcDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PCPC_CODE\"")
    private PcpcCode pcpcCode = PcpcCode.U;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Packet header parameter characteristic files";

    public enum PcpcCode {
        I,
        U
    }

}
