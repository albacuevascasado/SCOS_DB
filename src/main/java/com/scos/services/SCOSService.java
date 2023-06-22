package com.scos.services;

import com.scos.data_model.mps_db.ODBData;
import com.scos.data_model.mps_db.ODBFiles;
import com.scos.data_model.mps_db.common.InputFileStatus;
import com.scos.data_model.scos_db.*;
import com.scos.repositories.SCOSRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** BUSINESS LOGIC */
@NoArgsConstructor //REQUIRED TO BE ABLE TO INVOKE METHOD
@Setter
@Getter
public class SCOSService {

    @Autowired
    SCOSRepository scosRepository;

    public SCOSDB createSCOSDBRecord(String scosdbPath) {
        SCOSDB scosdb = new SCOSDB();
        scosdb.setScosRelease(scosdbPath);
        scosdb.setScosUpdate(LocalDateTime.now());

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveSCOSDBRecord(scosdb);
        } else {
            System.out.println("scosRepository has not been injected");
        }

        return scosdb;
    }

    public ODBFiles createODBFILESRecord(String odbPath, String odbFileName) {
        ODBFiles odbFiles = new ODBFiles();
        odbFiles.setOdbFileName(odbFileName);
        odbFiles.setOdbReceiveDate(LocalDateTime.now());
        odbFiles.setOdbProcessDate(LocalDateTime.now());
        odbFiles.setOdbArchivePath(odbPath);
        odbFiles.setOdbFileStatus(InputFileStatus.TO_PROCESS);
        odbFiles.setOdbFileError("ERROR");

        if (scosRepository != null) {
            scosRepository.saveODBFILESRecord(odbFiles);
        } else {
            System.out.println("scosRepository has not been injected");
        }

        return odbFiles;
    }

    public void updateODBFILESStatus(ODBFiles odbFiles) {
        if (scosRepository != null) {
            scosRepository.updateODBFILESStatus(odbFiles);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public SCOSTABLES createSCOSTABLESRecord(String tableName, List<?> dbRecords , SCOSDB scosdb) {
        SCOSTABLES scostables = new SCOSTABLES();
        scostables.setTableName(tableName);
        scostables.setScosDB(scosdb);
        scostables.setCreationDate(LocalDateTime.now());
        scostables.setUpdateDate(LocalDateTime.now());
        scostables.setDimension(BigInteger.valueOf(dbRecords.size()));
        scostables.setDescription(tableName + " IS BEEN INSERTED");

        return scostables;
    }

    public ODBData createODBDATARecord(String tableName, List<?> dbRecords, ODBFiles odbFiles) {
        ODBData odbData = new ODBData();
        odbData.setOdbTableName(tableName);
        odbData.setOdbFiles(odbFiles);
        odbData.setOdbTableSize(BigInteger.valueOf(dbRecords.size()));

        return odbData;
    }

    public void createCVSRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CVS";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CVS> listCVS = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            CVS cvs = new CVS();
            System.out.println("Line createCVSRecord: " + record);
            String[] recordSplit = record.split("\t");

            cvs.setCvsId(Integer.parseInt(recordSplit[0]));
            cvs.setCvsType(recordSplit[1].charAt(0));
            cvs.setCvsSource(recordSplit[2].charAt(0));
            cvs.setCvsStart(Integer.parseInt(recordSplit[3]));
            cvs.setCvsInterval(Integer.parseInt(recordSplit[4]));
            //EMPTY COLUMNS
            if ((recordSplit[5].length() == 0)) {
                cvs.setCvsSpid(0);
            } else {
                cvs.setCvsSpid(Integer.parseInt(recordSplit[5]));
            }
            cvs.setCvsUncertainty(Integer.parseInt(recordSplit[6]));

            //System.out.println("CVS createCVSRecord: " + cvs.getCvsId());
            listCVS.add(cvs);

            reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCVS,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCVS, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCVSRecords(listCVS,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCAFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CAF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CAF> listCAF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            CAF caf = new CAF();
            System.out.println("Line createCAFRecord: " + record);
            String[] recordSplit = record.split("\t");

            caf.setCafNumbr(recordSplit[0]);
            caf.setCafDescr(recordSplit[1]);
            caf.setCafEngfmt(recordSplit[2].charAt(0));
            caf.setCafRawfmt(recordSplit[3].charAt(0));
            caf.setCafRadix(recordSplit[4].charAt(0));
            caf.setCafUnit(recordSplit[5]);
            caf.setCafNcurve(BigInteger.valueOf(Integer.parseInt(recordSplit[6])));
            caf.setCafInter(recordSplit[7].charAt(0));

            //System.out.println("CAF createCAFRecord: " + caf.getCafNumbr());
            listCAF.add(caf);
            reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCAF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCAF, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCAFRecords(listCAF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCAPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CAP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CAP> listCAP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            CAP cap = new CAP();
            //System.out.println("Line createCAPRecord: " + record);
            String[] recordSplit = record.split("\t");

            cap.setCaPNumbr(recordSplit[0]);
            cap.setCapXvals(recordSplit[1]);
            cap.setCapYvals(recordSplit[2]);

            //System.out.println("CAP createCAPRecord: " + cap.getCaPNumbr());
            listCAP.add(cap);

            reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCAP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCAP, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCAPRecords(listCAP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCCARecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CCA";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CCA> listCCA = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            CCA cca = new CCA();
            //System.out.println("Line createCCARecord: " + record);
            String[] recordSplit = record.split("\t");

            cca.setCcaNumbr(recordSplit[0]);
            cca.setCcaDescr(recordSplit[1]);
            cca.setCcaEngfmt(recordSplit[2].charAt(0));
            cca.setCcaRawfmt(recordSplit[3].charAt(0));
            cca.setCcaRadix(recordSplit[4].charAt(0));
            cca.setCcaUnit(recordSplit[5]);
            cca.setCcaNcurve(BigInteger.valueOf(Integer.parseInt(recordSplit[6])));

            //System.out.println("CCA createCCARecord: " + cca.getCcaNumbr());
            listCCA.add(cca);

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCA,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCA, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCCARecords(listCCA,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCCFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CCF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CCF> listCCF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while(record != null) {
            CCF ccf = new CCF();
            //System.out.println("Line createCCFRecord: " + record);
            String[] recordSplit = record.split("\t");

            ccf.setCcfCname(recordSplit[0]);
            ccf.setCcfDescr(recordSplit[1]);
            ccf.setCcfDescr2(recordSplit[2]);
            ccf.setCcfCtype(recordSplit[3]);
            ccf.setCcfCritical(recordSplit[4].charAt(0));
            ccf.setCcfPktid(recordSplit[5]);
            if (recordSplit[6].length() > 0) {
                ccf.setCcfType(Integer.parseInt(recordSplit[6]));
            }
            if (recordSplit[7].length() > 0) {
                ccf.setCcfStype(Integer.parseInt(recordSplit[7]));
            }
            if (recordSplit[8].length() > 0) {
                ccf.setCcfApid(Integer.parseInt(recordSplit[8]));
            }
            if (recordSplit[9].length() > 0) {
                ccf.setCcfNpars(Integer.parseInt(recordSplit[9]));
            }
            ccf.setCcfPlan(recordSplit[10].charAt(0));
            ccf.setCcfExec(recordSplit[11].charAt(0));
            ccf.setCcfIlscope(recordSplit[12].charAt(0));
            ccf.setCcfIlstage(recordSplit[13].charAt(0));
            if (recordSplit[14].length() > 0) {
                ccf.setCcfSubsys(Integer.parseInt(recordSplit[14]));
            }
            ccf.setCcfHipri(recordSplit[15].charAt(0));
            if (recordSplit[16].length() > 0) {
                ccf.setCcfMapid(Integer.parseInt(recordSplit[16]));
            }
            ccf.setCcfDefset(recordSplit[17]);
            if (recordSplit[18].length() > 0) {
                ccf.setCcfRapid(Integer.parseInt(recordSplit[18]));
            }
            if (recordSplit[19].length() > 0) {
                ccf.setCcfAck(Integer.parseInt(recordSplit[19]));
            }
            //TODO file non esiste questa posizione
            if (recordSplit.length > 20) {
                ccf.setCcfSubschedid(Integer.parseInt(recordSplit[20]));
            }

            //System.out.println("CCF createCCFRecord: " + ccf.getCcfCname());
            listCCF.add(ccf);

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCF, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCCFRecords(listCCF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCCSRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CCS";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CCS> listCCS = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while(record != null) {
            CCS ccs = new CCS();
            //System.out.println("Line createCCSRecord: " + record);
            String[] recordSplit = record.split("\t");

            ccs.setCcsNumbr(recordSplit[0]);
            ccs.setCcsXvals(recordSplit[1]);
            ccs.setCcsYvals(recordSplit[2]);

            //System.out.println("CCS createCCSRecord: " + ccs.getCcsNumbr());
            listCCS.add(ccs);

            reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCS,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCS, odbFiles);

        if(scosRepository != null) {
            //System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCCSRecords(listCCS,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

//    //USING SWITCH
//    public void serviceSearchTable(String tableName, String[] record) {
//        switch (tableName) {
//            case "CVS":
//                System.out.println("Table Name: " + tableName);
//                /** if @Autowired works ALL the repositories will be injected without the need of a constructor */
//                SCOSService scosService = new SCOSService();
//                scosService.createCVSRecord(record); //save by record
//                break;
//            default:
//                System.out.println("No se encuentra ninguna tabla con este nombre");
//        }
//
//    }

}
