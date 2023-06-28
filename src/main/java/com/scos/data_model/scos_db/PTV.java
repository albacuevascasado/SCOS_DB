package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PTV\"")
public class PTV {

    @Id
    @Column(name = "\"PTV_CNAME\"", nullable = false)
    private String ptvCname;

    @Id
    @Column(name = "\"PTV_PARNAM\"", nullable = false)
    private String ptvParnam;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PTV_INTER\"")
    private PtvInter ptvInter = PtvInter.R;

    @Column(name = "\"PTV_VAL\"", nullable = false)
    private String ptvVal;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "";

    public enum PtvInter {
        R,
        E,
    }

}
