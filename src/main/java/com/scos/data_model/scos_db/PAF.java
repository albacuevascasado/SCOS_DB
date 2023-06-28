package com.scos.data_model.scos_db;

import com.scos.data_model.scos_db.common._FMT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "scos_schema", name = "\"PAF\"")
public class PAF {

    public enum PafColumn{
        PAF_NUMBR(0),
        PAF_DESCR(1),
        PAF_RAWFMT(2),
        PAF_NALIAS(3);

        private final int i;

        PafColumn(int i) {
            this.i = i;
        }

        public int getIndex(){
            return this.i;
        }

        public static String getEnumByIndex(int code){
            for(PafColumn e : PafColumn.values()){
                if(e.getIndex()==code) return e.name();
            }
            return null;
        }

    }

    @Id
    @Column(name = "\"PAF_NUMBR\"", nullable = false)
    private String PAF_NUMBR;

    @Column(name = "\"PAF_DESCR\"")
    private String PAF_DESCR;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"PAF_RAWFMT\"")
    private _FMT PAF_RAWFMT = _FMT.U;

    @Column(name = "\"PAF_NALIAS\"")
    private int PAF_NALIAS;

    public static final int MAX_COLUMN = 4;

    public static final String DESCRIPTION = "Parameter alias file";

    public void setProperty(PafColumn name, String value) throws NoSuchFieldException, IllegalAccessException {
        getClass().getDeclaredField(name.toString()).set(this, value);
    }

    public void setProperty(PafColumn name, char value) throws NoSuchFieldException, IllegalAccessException {
        getClass().getDeclaredField(name.toString()).setChar(this, value);
    }

    public void setProperty(PafColumn name, BigInteger value) throws NoSuchFieldException, IllegalAccessException {
        getClass().getDeclaredField(name.toString()).set(this, value);
    }
}
