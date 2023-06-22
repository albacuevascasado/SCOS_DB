package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "mps_schema" , name = "\"T_ODB_DATA\"")
public class ODBData {

    @Id
    @Column(name = "\"ODB_TABLE_NAME\"", nullable = false)
    private String odbTableName;

    @ManyToOne(optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "\"T_ODB_DATA_FILES_fkey\""),
            value = {
                    @JoinColumn(name = "\"ODB_FILE_ID\"", referencedColumnName = "\"ODB_FILE_ID\"", nullable = false),
                    @JoinColumn(name = "\"ODB_FILE_NAME\"", referencedColumnName = "\"ODB_FILE_NAME\"", nullable = false)
            })
    private ODBFiles odbFiles;

    @Column(name = "\"ODB_TABLE_SIZE\"")
    private BigInteger odbTableSize;

}
