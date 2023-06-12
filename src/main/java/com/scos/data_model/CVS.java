package com.scos.data_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"CVS\"")
public class CVS {

    @Id
    @Column(name = "\"CVS_ID\"", nullable = false)
    @Min(value = 0)
    @Max(value = 32767)
    private Integer cvsId;

    @Column(name = "\"CVS_TYPE\"", nullable = false)
    private Character cvsType;

    @Column(name = "\"CVS_SOURCE\"", nullable = false)
    private Character cvsSource;

    @Column(name = "\"CVS_START\"", nullable = false)
    @Min(value = 0)
    private Integer cvsStart;

    @Column(name = "\"CVS_INTERVAL\"", nullable = false)
    @Min(value = 0)
    private Integer cvsInterval;

    // CVS_SPID
    @Column(name = "\"CVS_SPID\"")
    private Integer cvsSpid;

    @Column(name = "\"CVS_UNCERTAINTY\"")
    private Integer cvsUncertainty;

}
