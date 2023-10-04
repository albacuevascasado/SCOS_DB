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
@Table(name = "\"T_IPOR_FILES\"")
@IdClass(IPORKey.class)
public class IPORFiles {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"S_IPOR_FILE_ID\"")
    @SequenceGenerator(name = "\"S_IPOR_FILE_ID\"", sequenceName = "\"S_IPOR_FILE_ID\"", allocationSize = 1)
    @Id
    @Column(name = "\"ZIP_FILE_ID\"", nullable = false)
    private BigInteger zipFileId;

    @Id
    @Column(name = "\"ZIP_FILE_NAME\"", nullable = false)
    private String zipFileName;

    @Column(name = "\"ZIP_RECEIVE_DATE\"", nullable = false)
    private LocalDateTime zipReceiveDate;

    @Column(name = "\"ZIP_PROCESS_DATE\"", nullable = false)
    private LocalDateTime zipProcessDate;

    @Column(name = "\"ZIP_ARCHIVE_PATH\"", nullable = false)
    private String zipArchivePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"ZIP_FILE_STATUS\"", nullable = false)
    private InputFileStatus zipFileStatus;

    @Column(name = "\"ZIP_FILE_ERROR\"")
    private String zipFileError;
}
