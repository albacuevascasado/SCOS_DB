package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.validation.Constraint;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "scos_schema" , name = "\"CVS\"")
public class CVS {

    @Id
    @Column(name = "\"CVS_ID\"", nullable = false)
    //@Min(value = 0)
    //@Max(value = 32767)
    //@Range
    //@Size
    private int cvsId;

    @Column(name = "\"CVS_TYPE\"", nullable = false)
    private char cvsType;

    @Column(name = "\"CVS_SOURCE\"", nullable = false)
    private char cvsSource;

    @Column(name = "\"CVS_START\"", nullable = false)
    //@Min(value = 0)
    private int cvsStart;

    @Column(name = "\"CVS_INTERVAL\"", nullable = false)
    //@Min(value = 0)
    private int cvsInterval;

    @Column(name = "\"CVS_SPID\"")
    private int cvsSpid;

    @Column(name = "\"CVS_UNCERTAINTY\"")
    private int cvsUncertainty;

}
