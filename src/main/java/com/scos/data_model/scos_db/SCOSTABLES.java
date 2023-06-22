package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema" , name = "\"T_SCOS_TABLES\"")
public class SCOSTABLES {

    @Id
    @Column(name = "\"TABLE_NAME\"" , nullable = false)
    private String tableName;

    @ManyToOne (optional = false)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "\"T_SCOS_TABLES_DB_fkey\""),
            name="\"SCOS_RELEASE\"", referencedColumnName = "\"SCOS_RELEASE\"", nullable = false
    )
    private SCOSDB scosDB;

    @Column(name = "\"CREATION_DATE\"" , nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "\"UPDATE_DATE\"" , nullable = false)
    private LocalDateTime updateDate;

    //number of rows
    @Column(name = "\"DIMENSION\"" , nullable = false)
    private BigInteger dimension;

    @Column(name = "\"DESCRIPTION\"" , nullable = false)
    private String description;

}
