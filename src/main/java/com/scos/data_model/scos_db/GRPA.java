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
@Table(schema = "scos_schema", name = "\"GRPA\"")
public class GRPA implements Serializable {

    public enum COLUMNS {
        GRPA_GNAME,
        GRPA_PANAME
    }

    @Id
    @Column(name = "\"GRPA_GNAME\"", nullable = false)
    private String grpaGname;

    @Id
    @Column(name = "\"GRPA_PANAME\"", nullable = false)
    private String grpaPaname;

    public static final int MAX_COLUMN = 2;

    public static final String DESCRIPTION = "Parameter group file";

}
