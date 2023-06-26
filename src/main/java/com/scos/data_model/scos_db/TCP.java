package com.scos.data_model.scos_db;

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
@Table(schema = "scos_schema", name = "\"TCP\"")
public class TCP {

    @Id
    @Column(name = "\"TCP_ID\"", nullable = false)
    private String tcpId;

    @Column(name = "\"TCP_DESC\"")
    private String tcpDesc;

    public static final int MAX_COLUMN = 2;

    public static final String DESCRIPTION = "Telecommand packet header identification";

}
