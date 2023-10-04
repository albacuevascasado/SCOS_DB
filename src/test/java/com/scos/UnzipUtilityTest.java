package com.scos;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class UnzipUtilityTest {

    //target folder EXISTS -> Desktop exists
    @Test
    public void testUnZipFileWDir() throws IOException {
        UnzipUtility unzipUtility = new UnzipUtility();
        assertEquals("C:\\Users\\acasado\\Desktop", unzipUtility.unZip("C:\\Users\\acasado\\Desktop\\mps.zip","C:\\Users\\acasado\\Desktop"));
    }

    //target folder DOES NOT EXIST
    @Test
    public void testUnZipFileNoDir() throws IOException {
        UnzipUtility unzipUtility = new UnzipUtility();
        assertEquals("C:\\Users\\acasado\\Desktop\\Prova", unzipUtility.unZip("C:\\Users\\acasado\\Desktop\\mps.zip","C:\\Users\\acasado\\Desktop\\Prova"));
    }

    @Test
    public void testUnZipFileDelete() throws IOException {
        UnzipUtility unzipUtility = new UnzipUtility();
        File directoryPath = new File ("C:\\Users\\acasado\\Desktop\\Test");
        directoryPath.mkdir();
        assertEquals(true, unzipUtility.deleteUnZipFolder(directoryPath));
//        assertEquals(true, directoryPath.exists());
    }

}
