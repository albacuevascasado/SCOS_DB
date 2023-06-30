package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"DPF\"")
public class DPF {

    public enum COLUMNS {
        DPF_NUMBE,
        DPF_TYPE,
        DPF_HEAD
    }

    @Id
    @Column(name = "\"DPF_NUMBE\"", nullable = false)
    private String dpfNumbe;

    @Column(name = "\"DPF_TYPE\"", nullable = false)
    private char dpfType;

    @Column(name = "\"DPF_HEAD\"")
    private String dpfHead;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Alphanumeric display";

    public static final char[] arrayDPFType = new char[] {'1','3'};

}
