package com.scos.data_model.mps_db;


import com.scos.data_model.mps_db.common.InputFileStatus;
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
@Table(schema = "mps_schema" , name = "\"T_ODB_FILES\"")
@IdClass(ODBFilesKey.class)
public class ODBFiles {

    @Id
    @Column(name = "\"ODB_FILE_ID\"", nullable = false)
    @SequenceGenerator(schema = "mps_schema", name = "\"S_ODB_FILE_ID\"", sequenceName = "\"S_ODB_FILE_ID\"", allocationSize =1)
    @GeneratedValue (strategy = GenerationType.AUTO , generator = "\"S_ODB_FILE_ID\"")
    private BigInteger odbFileId;

    @Id
    @Column(name = "\"ODB_FILE_NAME\"", nullable = false)
    private String odbFileName;

    @Column(name = "\"ODB_RECEIVE_DATE\"", nullable = false)
    private LocalDateTime odbReceiveDate;

    @Column(name = "\"ODB_PROCESS_DATE\"", nullable = false)
    private LocalDateTime odbProcessDate;

    @Column(name = "\"ODB_ARCHIVE_PATH\"", nullable = false)
    private String odbArchivePath;

    @Column(name = "\"ODB_FILE_STATUS\"", nullable = false)
    @Enumerated(EnumType.STRING)
    private InputFileStatus odbFileStatus;

    @Column(name = "\"ODB_FILE_ERROR\"")
    private String odbFileError;


}
