package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._YN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"DPC\"")
public class DPC implements Serializable {

    @Id
    @Column(name = "\"DPC_NUMBE\"", nullable = false)
    private String dpcNumbe;

    @Column(name = "\"DPC_NAME\"")
    private String dpcName;

    @Id
    @Column(name = "\"DPC_FLDN\"", nullable = false)
    private int dpcFldn;

    @Column(name = "\"DPC_COMM\"")
    private int dpcComm;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"DPC_MODE\"")
    private _YN dpcMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"DPC_FORM\"")
    private DpcForm dpcForm;

    @Column(name = "\"DPC_TEXT\"")
    private String dpcText;

    public static final int MAX_COLUMN = 7;

    public static final String DESCRIPTION = "Alphanumeric display definition";

    public enum DpcForm {
        B,
        O,
        D,
        H,
        N
    }

}
