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
@Table(schema = "scos_schema", name = "\"CCS\"")
public class CCS implements Serializable {
    @Id
    @Column(name = "\"CCS_NUMBR\"", nullable = false)
    private String ccsNumbr;

    @Id
    @Column(name = "\"CCS_XVALS\"", nullable = false)
    private String ccsXvals;

    @Id
    @Column(name = "\"CCS_YVALS\"", nullable = false)
    private String ccsYvals;
}
