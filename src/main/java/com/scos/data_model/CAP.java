package com.scos.data_model;

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
@Table(schema = "scos_schema", name = "\"CAP\"")
public class CAP {
    @Id
    @Column(name = "\" CAP_NUMBR\"", nullable = false)
    private String caPNumbr;

    @Id
    @Column(name = "\" CAP_XVALS\"", nullable = false)
    private String capXvals;

    @Id
    @Column(name = "\" CAP_YVALS\"", nullable = false)
    private String capYvals;
}
