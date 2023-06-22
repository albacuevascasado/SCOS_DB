package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema" , name = "\"T_SCOS_DB\"")
public class SCOSDB {

    @Id
    @Column(name = "\"SCOS_RELEASE\"", nullable = false)
    private String scosRelease;

    @Column(name = "\"SCOS_UPDATE\"", nullable = false)
    private LocalDateTime scosUpdate;

}
