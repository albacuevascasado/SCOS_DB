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
public class IPORKey implements Serializable {
    private BigInteger zipFileId;

    private String zipFileName;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        IPORKey porFilesKey = (IPORKey) o;
        return Objects.equals( zipFileId, porFilesKey.zipFileId ) &&
                Objects.equals( zipFileName, porFilesKey.zipFileName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( zipFileId, zipFileName );
    }
}
