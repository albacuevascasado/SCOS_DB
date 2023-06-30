package com.scos.data_model.scos_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"CAP\"")
public class CAP implements Serializable {

//    public enum COLUMNS {
//        CAP_NUMBR (1),
//        CAP_XVALS (2),
//        CAP_YVALS (3);
//
//        //internal state
//        private int hierarchy;
//        //constructor
//        private COLUMNS(final int hierarchy) {
//            this.hierarchy = hierarchy;
//        }
//        //method to get value
//        public int getHierarchy() {
//            return hierarchy;
//        }
//    }

    public enum COLUMNS {
        CAP_NUMBR,
        CAP_XVALS,
        CAP_YVALS
    }

    @Id
    @Column(name = "\"CAP_NUMBR\"", nullable = false)
    private String caPNumbr;

    @Id
    @Column(name = "\"CAP_XVALS\"", nullable = false)
    private String capXvals;


    @Column(name = "\"CAP_YVALS\"", nullable = false)
    private String capYvals;

    public static final int MAX_COLUMN = 3;

    public static final String DESCRIPTION = "Numerical calibration curve parameters";
}
