package com.scos.data_model.mps_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ODBFilesKey implements Serializable {
    private BigInteger odbFileId;

    private String odbFileName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ODBFilesKey odbFilesKey = (ODBFilesKey) o;
        return odbFileId.equals(odbFilesKey.odbFileId) && odbFileName.equals(odbFilesKey.odbFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(odbFileId, odbFileName);
    }

}
