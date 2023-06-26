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
@Table(schema = "scos_schema", name = "\"PRV\"")
public class PRV implements Serializable {

    @Id
    @Column(name = "\"PRV_NUMBR\"", nullable = false)
    private String prvNumbr;

    @Id
    @Column(name = "\"PRV_MINVAL\"", nullable = false)
    private String prvMinval;

    @Column(name = "\"PRV_MAXVAL\"")
    private String prvMaxval;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Parameter range value file";

}
