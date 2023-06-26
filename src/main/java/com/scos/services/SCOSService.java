package com.scos.services;

import com.scos.data_model.mps_db.ODBData;
import com.scos.data_model.mps_db.ODBFiles;
import com.scos.data_model.mps_db.common.InputFileStatus;
import com.scos.data_model.scos_db.*;
import com.scos.data_model.scos_db.common.*;
import com.scos.repositories.SCOSRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        //dbRecords.getClass().getField("DESCRIPTION");

        return scostables;
    }

    public ODBData createODBDATARecord(String tableName, List<?> dbRecords, ODBFiles odbFiles) {
        ODBData odbData = new ODBData();
        odbData.setOdbTableName(tableName);
        odbData.setOdbFiles(odbFiles);
        odbData.setOdbTableSize(BigInteger.valueOf(dbRecords.size()));

        return odbData;
    }

    public void createCAFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CAF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CAF> listCAF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CAF caf = new CAF();
                String[] recordSplit = record.split("\t");

                caf.setCafNumbr(recordSplit[0]);
                caf.setCafDescr(recordSplit[1]);
                caf.setCafEngfmt(_FMT.valueOf(recordSplit[2]));
                caf.setCafRawfmt(_FMT.valueOf(recordSplit[3]));

                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    caf.setCafRadix(_RADIX.valueOf(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    caf.setCafUnit(recordSplit[5]);
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    caf.setCafNcurve(Integer.parseInt(recordSplit[6]));
                }
                if(recordSplit.length > 7 && recordSplit[7].length() > 0) {
                    caf.setCafInter(CAF.CafInter.valueOf(recordSplit[7]));
                }

                listCAF.add(caf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCAF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCAF, odbFiles);

        if(scosRepository != null) {
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
            try {
                CAP cap = new CAP();
                String[] recordSplit = record.split("\t");

                cap.setCaPNumbr(recordSplit[0]);
                cap.setCapXvals(recordSplit[1]);
                cap.setCapYvals(recordSplit[2]);

                listCAP.add(cap);

            } catch (Exception e){
                e.printStackTrace();
            }
            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCAP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCAP, odbFiles);

        if(scosRepository != null) {
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
            try {
                CCA cca = new CCA();
                String[] recordSplit = record.split("\t");

                cca.setCcaNumbr(recordSplit[0]);
                if(recordSplit.length > 1 && recordSplit[1].length() > 0) {
                    cca.setCcaDescr(recordSplit[1]);
                }
                if(recordSplit.length > 2 && recordSplit[2].length() > 0) {
                    cca.setCcaEngfmt(_FMT.valueOf(recordSplit[2]));
                }
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    cca.setCcaRawfmt(_FMT.valueOf(recordSplit[3]));
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    cca.setCcaRadix(_RADIX.valueOf(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    cca.setCcaUnit(recordSplit[5]);
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    cca.setCcaNcurve(Integer.parseInt(recordSplit[6]));
                }

                listCCA.add(cca);

            } catch (Exception e){
                e.printStackTrace();
            }
            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCA,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCA, odbFiles);

        if(scosRepository != null) {
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

            try {
                CCF ccf = new CCF();
                String[] recordSplit = record.split("\t");

                ccf.setCcfCname(recordSplit[0]);
                ccf.setCcfDescr(recordSplit[1]);
                ccf.setCcfDescr2(recordSplit[2]);
                if(recordSplit[3].length() > 0) {
                    ccf.setCcfCtype(CCF.CcfCtype.valueOf(recordSplit[3]));
                }
                if(recordSplit[4].length() > 0) {
                    ccf.setCcfCritical(_YN.valueOf(recordSplit[4]));
                }
                ccf.setCcfPktid(recordSplit[5]);
                if (recordSplit.length > 6 && recordSplit[6].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[6]), 0, 255)) {
                    ccf.setCcfType(Integer.parseInt(recordSplit[6]));
                }
                if (recordSplit.length > 7 && recordSplit[7].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[7]), 0, 255)) {
                    ccf.setCcfStype(Integer.parseInt(recordSplit[7]));
                }
                if (recordSplit.length > 8 && recordSplit[8].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[8]), 0, 255)) {
                    ccf.setCcfApid(Integer.parseInt(recordSplit[8]));
                }
                if (recordSplit.length > 9 && recordSplit[9].length() > 0) {
                    ccf.setCcfNpars(Integer.parseInt(recordSplit[9]));
                }
                if(recordSplit.length > 10 && recordSplit[10].length() > 0) {
                    ccf.setCcfPlan(CCF.CcfPlan.valueOf(recordSplit[10]));
                }
                if(recordSplit.length > 11 && recordSplit[11].length() > 0) {
                    ccf.setCcfExec(_YN.valueOf(recordSplit[11]));
                }
                if(recordSplit.length > 12 && recordSplit[12].length() > 0) {
                    ccf.setCcfIlscope(CCF.CcfScope.valueOf(recordSplit[12]));
                }
                if(recordSplit.length > 13 && recordSplit[13].length() > 0) {
                    ccf.setCcfIlstage(CCF.CcfStage.valueOf(recordSplit[13]));
                }
                if (recordSplit.length > 14 && recordSplit[14].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[14]), 1, 255)) {
                    ccf.setCcfSubsys(Integer.parseInt(recordSplit[14]));
                }
                if(recordSplit.length > 15 && recordSplit[15].length() > 0) {
                    ccf.setCcfHipri(_YN.valueOf(recordSplit[15]));
                }
                if(recordSplit.length > 16 && recordSplit[16].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[16]), 0, 63)) {
                    ccf.setCcfMapid(Integer.parseInt(recordSplit[16]));
                }
                if(recordSplit.length > 17 && recordSplit[17].length() > 0) {
                    ccf.setCcfDefset(recordSplit[17]);
                }
                if (recordSplit.length > 18  && recordSplit[18].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[18]), 1, 65535)) {
                    ccf.setCcfRapid(Integer.parseInt(recordSplit[18]));
                }
                if (recordSplit.length > 19  && recordSplit[19].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[19]), 0, 15)) {
                    ccf.setCcfAck(Integer.parseInt(recordSplit[19]));
                }
                if (recordSplit.length > 20 && recordSplit[20].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[20]), 1, 65535)) {
                    ccf.setCcfSubschedid(Integer.parseInt(recordSplit[20]));
                }

                listCCF.add(ccf);

            } catch (Exception e){
                e.printStackTrace();
            }
            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCF, odbFiles);

        if(scosRepository != null) {
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
            try {
                CCS ccs = new CCS();
                String[] recordSplit = record.split("\t");

                ccs.setCcsNumbr(recordSplit[0]);
                ccs.setCcsXvals(recordSplit[1]);
                ccs.setCcsYvals(recordSplit[2]);

                listCCS.add(ccs);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCCS,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName, listCCS, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCCSRecords(listCCS,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCDFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CDF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CDF> listCDF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while(record != null) {
            try {
                CDF cdf = new CDF();
                String[] recordSplit = record.split("\t");

                cdf.setCdfCname(recordSplit[0]);
                cdf.setCdfEltype(CDF.CdfType.valueOf(recordSplit[1]));
                cdf.setCdfDescr(recordSplit[2]);
                cdf.setCdfEllen(Integer.parseInt(recordSplit[3]));
                cdf.setCdfBit(Integer.parseInt(recordSplit[4]));
                cdf.setCdfGrpsize(Integer.parseInt(recordSplit[5]));
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    cdf.setCdfPname(recordSplit[6]);
                }
                if(recordSplit.length > 7 && recordSplit[7].length() > 0) {
                    cdf.setCdfInter(CDF.CdfInter.valueOf(recordSplit[7]));
                }
                if(recordSplit.length > 8 && recordSplit[8].length() > 0) {
                    cdf.setCdfValue(recordSplit[8]);
                }
                if(recordSplit.length > 9 && recordSplit[9].length() > 0) {
                    cdf.setCdfTmid(recordSplit[9]);
                }

                listCDF.add(cdf);

            } catch (Exception e) {
                e.printStackTrace();
            }

            record = reader.readLine();
        }
        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCDF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCDF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCDFRecords(listCDF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCPCRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CPC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CPC> listCPC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CPC cpc = new CPC();
                String[] recordSplit = record.split("\t");

                cpc.setCpcPname(recordSplit[0]);
                cpc.setCpcDescr(recordSplit[1]);
                cpc.setCpcPtc(Double.parseDouble(recordSplit[2]));
                cpc.setCpcPfc(Integer.parseInt(recordSplit[3]));
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    cpc.setCpcDispfmt(CPC.CpcDispfmt.valueOf(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    cpc.setCpcRadix(_RADIX.valueOf(recordSplit[5]));
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    cpc.setCpcUnit(recordSplit[6]);
                }
                if(recordSplit.length > 7 && recordSplit[7].length() > 0) {
                   cpc.setCpcCateg(CPC.CpcCateg.valueOf(recordSplit[7]));
                }
                if(recordSplit.length > 8 && recordSplit[8].length() > 0) {
                   cpc.setCpcPrfref(recordSplit[8]);
                }
                if(recordSplit.length > 9 && recordSplit[9].length() > 0) {
                    cpc.setCpcCcaref(recordSplit[9]);
                }
                if(recordSplit.length > 10 && recordSplit[10].length() > 0) {
                    cpc.setCpcPafref(recordSplit[10]);
                }
                if(recordSplit.length > 11 && recordSplit[11].length() > 0) {
                    cpc.setCpcInter(CPC.CpcInter.valueOf(recordSplit[11]));
                }
                if(recordSplit.length > 12 && recordSplit[12].length() > 0) {
                    cpc.setCpcDefval(recordSplit[12]);
                }
                if(recordSplit.length > 13 && recordSplit[13].length() > 0) {
                    cpc.setCpcCorr(_YN.valueOf(recordSplit[13]));
                }
                if(recordSplit.length > 14 && recordSplit[14].length() > 0) {
                    cpc.setCpcObtid(Integer.parseInt(recordSplit[14]));
                }
                if(recordSplit.length > 15 && recordSplit[15].length() > 0) {
                    cpc.setCpcDescr2(recordSplit[15]);
                }
                if(recordSplit.length > 16 && recordSplit[16].length() > 0) {
                    cpc.setCpcEndian(CPC.CpcEndian.valueOf(recordSplit[16]));
                }

                listCPC.add(cpc);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCPC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCPC, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCPCRecords(listCPC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createCURRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CUR";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CUR> listCUR = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CUR cur = new CUR();
                String[] recordSplit = record.split("\t");

                cur.setCurPname(recordSplit[0]);
                cur.setCurPos(Integer.parseInt(recordSplit[1]));
                cur.setCurRlchk(recordSplit[2]);
                cur.setCurValpar(Integer.parseInt(recordSplit[3]));
                cur.setCurSelect(recordSplit[4]);

                listCUR.add(cur);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCUR,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCUR, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCURRecords(listCUR,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCVERecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CVE";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CVE> listCVE = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CVE cve = new CVE();
                String[] recordSplit = record.split("\t");

                cve.setCveCvsid(Integer.parseInt(recordSplit[0]));
                cve.setCveParnam(recordSplit[1]);
                cve.setCveInter(CVE.CveInter.valueOf(recordSplit[2]));
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    cve.setCveVal(recordSplit[3]);
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    cve.setCveTol(recordSplit[4]);
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    cve.setCveCheck(CVE.CveCheck.valueOf(recordSplit[5]));
                }

                listCVE.add((cve));

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCVE,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCVE, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCVERecords(listCVE,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCVPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CVP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CVP> listCVP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CVP cvp = new CVP();
                String[] recordSplit = record.split("\t");

                cvp.setCvpTask(recordSplit[0]);
                cvp.setCvpType(CVP.CvpType.valueOf(recordSplit[1]));
                cvp.setCvpCvsid(Integer.parseInt(recordSplit[2]));

                listCVP.add(cvp);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCVP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCVP, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCVPRecords(listCVP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCVSRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CVS";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CVS> listCVS = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CVS cvs = new CVS();
                String[] recordSplit = record.split("\t");

                if(checkIntegerRange(Integer.parseInt(recordSplit[0]), 0, 32767)) {
                    cvs.setCvsId(Integer.parseInt(recordSplit[0]));
                }
                cvs.setCvsType(recordSplit[1].charAt(0));
                cvs.setCvsSource(CVS.CvsSource.valueOf(recordSplit[2]));
                if(checkLowerBoundary(Integer.parseInt(recordSplit[3]), 0)) {
                    cvs.setCvsStart(Integer.parseInt(recordSplit[3]));
                }
                if(checkLowerBoundary(Integer.parseInt(recordSplit[4]), 0)) {
                    cvs.setCvsInterval(Integer.parseInt(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    cvs.setCvsSpid(Integer.parseInt(recordSplit[5]));
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0 && checkLowerBoundary(Integer.parseInt(recordSplit[6]),0)) {
                    cvs.setCvsUncertainty(Integer.parseInt(recordSplit[6]));
                }

                listCVS.add(cvs);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCVS,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCVS, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCVSRecords(listCVS,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createDPCRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "DPC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<DPC> listDPC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                DPC dpc = new DPC();
                String[] recordSplit = record.split("\t");

                dpc.setDpcNumbe(recordSplit[0]);
                dpc.setDpcName(recordSplit[1]);
                dpc.setDpcFldn(Integer.parseInt(recordSplit[2]));
                if(recordSplit.length > 3 && recordSplit[3].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[3]),0,9999)) {
                    dpc.setDpcFldn(Integer.parseInt(recordSplit[3]));
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    dpc.setDpcMode(_YN.valueOf(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                   dpc.setDpcForm(DPC.DpcForm.valueOf(recordSplit[5]));
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    dpc.setDpcText(recordSplit[6]);
                }

                listDPC.add(dpc);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listDPC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listDPC, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveDPCRecords(listDPC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createGRPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GRP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<GRP> listGRP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                GRP grp = new GRP();
                String[] recordSplit = record.split("\t");

                grp.setGrpName(recordSplit[0]);
                grp.setGrpDescr(recordSplit[1]);
                grp.setGrpGtype(GRP.GrpGtype.valueOf(recordSplit[2]));

                listGRP.add(grp);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listGRP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listGRP, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveGRPRecords(listGRP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createGRPARecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GRPA";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<GRPA> listGRPA = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                GRPA grpa = new GRPA();
                String[] recordSplit = record.split("\t");

                grpa.setGrpaGname(recordSplit[0]);
                grpa.setGrpaPaname(recordSplit[1]);

                listGRPA.add(grpa);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listGRPA,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listGRPA, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveGRPARecords(listGRPA,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createGRPKRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GRPK";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<GRPK> listGRPK = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                GRPK grpk = new GRPK();
                String[] recordSplit = record.split("\t");

                grpk.setGrpkGname(recordSplit[0]);
                grpk.setGrpkPkspid(Long.parseLong(recordSplit[1]));

                listGRPK.add(grpk);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listGRPK,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listGRPK, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveGRPKRecords(listGRPK,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createLGFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "LGF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LGF> listLGF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                LGF lgf = new LGF();
                String[] recordSplit = record.split("\t");

                lgf.setLgfIdent(recordSplit[0]);
                lgf.setLgfDescr(recordSplit[1]);
                lgf.setLgfPol1(recordSplit[2]);
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    lgf.setLgfPol2(recordSplit[3]);
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    lgf.setLgfPol3(recordSplit[4]);
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                   lgf.setLgfPol4(recordSplit[5]);
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    lgf.setLgfPol5(recordSplit[6]);
                }

                listLGF.add(lgf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listLGF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listLGF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveLGFRecords(listLGF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createMCFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "MCF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<MCF> listMCF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                MCF mcf = new MCF();
                String[] recordSplit = record.split("\t");

                mcf.setMcfIdent(recordSplit[0]);
                mcf.setMcfDescr(recordSplit[1]);
                mcf.setMcfPol1(recordSplit[2]);
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    mcf.setMcfPol2(recordSplit[3]);
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    mcf.setMcfPol3(recordSplit[4]);
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    mcf.setMcfPol4(recordSplit[5]);
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    mcf.setMcfPol5(recordSplit[6]);
                }

                listMCF.add(mcf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listMCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listMCF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveMCFRecords(listMCF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createOCFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "OCF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<OCF> listOCF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                OCF ocf = new OCF();
                String[] recordSplit = record.split("\t");

                ocf.setOcfName(recordSplit[0]);
                ocf.setOcfNbchck(Integer.parseInt(recordSplit[1]));
                if(checkIntegerRange(Integer.parseInt(recordSplit[2]),1,16)) {
                   ocf.setOcfNbool(Integer.parseInt(recordSplit[2]));
                }
                ocf.setOcfInter(OCF.OcfInter.valueOf(recordSplit[3]));
                ocf.setOcfCodin(OCF.OcfCodin.valueOf(recordSplit[4]));

                listOCF.add(ocf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listOCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listOCF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveOCFRecords(listOCF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createOCPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "OCP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<OCP> listOCP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                OCP ocp = new OCP();
                String[] recordSplit = record.split("\t");

                ocp.setOcpName(recordSplit[0]);
                ocp.setOcpPos(Integer.parseInt(recordSplit[1]));
                ocp.setOcpType(OCP.OcpType.valueOf(recordSplit[2]));
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    ocp.setOcpLvalu(recordSplit[3]);
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    ocp.setOcpHvalu(recordSplit[4]);
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    ocp.setOcpRlchk(recordSplit[5]);
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    ocp.setOcpValpar(Integer.parseInt(recordSplit[6]));
                }

                listOCP.add(ocp);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listOCP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listOCP, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveOCPRecords(listOCP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createPASRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PAS";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PAS> listPAS = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PAS pas = new PAS();
                String[] recordSplit = record.split("\t");

                pas.setPasNumbr(recordSplit[0]);
                pas.setPasAltxt(recordSplit[1]);
                pas.setPasAlval(recordSplit[2]);

                listPAS.add(pas);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPAS,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPAS, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePASRecords(listPAS,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createPCDFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PCDF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PCDF> listPCDF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PCDF pcdf = new PCDF();
                String[] recordSplit = record.split("\t");

                pcdf.setPcdfTcname(recordSplit[0]);
                pcdf.setPcdfDesc(recordSplit[1]);
                pcdf.setPcdfType(PCDF.PcdfType.valueOf(recordSplit[2]));
                pcdf.setPcdfLen(Integer.parseInt(recordSplit[3]));
                pcdf.setPcdfBit(Integer.parseInt(recordSplit[4]));
                pcdf.setPcdfPname(recordSplit[5]);
                pcdf.setPcdfValue(recordSplit[6]);
                if(recordSplit.length > 7 && recordSplit[7].length() > 0) {
                    pcdf.setPcdfRadix(_RADIX.valueOf(recordSplit[7]));
                }

                listPCDF.add(pcdf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPCDF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPCDF, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePCDFRecords(listPCDF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createPCPCRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PCPC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PCPC> listPCPC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PCPC pcpc = new PCPC();
                String[] recordSplit = record.split("\t");

                pcpc.setPcpcPname(recordSplit[0]);
                pcpc.setPcpcDesc(recordSplit[1]);
                if(recordSplit.length > 2 && recordSplit[2].length() > 0) {
                    pcpc.setPcpcCode(PCPC.PcpcCode.valueOf(recordSplit[2]));
                }

                listPCPC.add(pcpc);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPCPC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPCPC, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePCPCRecords(listPCPC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createPICRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PIC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PIC> listPIC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PIC pic = new PIC();
                String[] recordSplit = record.split("\t");

                if(checkIntegerRange(Integer.parseInt(recordSplit[0]), 0, 255)) {
                    pic.setPicType(Integer.parseInt(recordSplit[0]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[1]), 0, 255)) {
                    pic.setPicStype(Integer.parseInt(recordSplit[1]));
                }
                pic.setPicPi1Off(Integer.parseInt(recordSplit[2]));
                pic.setPicPi1Wid(Integer.parseInt(recordSplit[3]));
                pic.setPicPi2Off(Integer.parseInt(recordSplit[4]));
                pic.setPicPi2Wid(Integer.parseInt(recordSplit[5]));
                if(recordSplit.length > 6 && recordSplit[6].length() >0) {
                   pic.setPicApid(Integer.parseInt(recordSplit[6]));
                }

                listPIC.add(pic);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPIC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPIC, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePICRecords(listPIC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createPLFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PLF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PLF> listPLF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PLF plf = new PLF();
                String[] recordSplit = record.split("\t");

                plf.setPlfName(recordSplit[0]);
                if(checkLowerBoundary(Integer.parseInt(recordSplit[1]),0)) {
                    plf.setPlfSpid(BigInteger.valueOf(Integer.parseInt(recordSplit[1])));
                }
                if(checkLowerBoundary(Integer.parseInt(recordSplit[2]),0)) {
                    plf.setPlfOffby(Integer.parseInt(recordSplit[2]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[3]),0,7)) {
                    plf.setPlfOffbi(Integer.parseInt(recordSplit[3]));
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[4]),1,9999)) {
                    plf.setPlfNbocc(Integer.parseInt(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[5]),1,32767)) {
                    plf.setPlfLgocc(Integer.parseInt(recordSplit[5]));
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[6]),-4080000, 4080000)) {
                    plf.setPlfTime(BigInteger.valueOf(Integer.parseInt(recordSplit[6])));
                }
                if(recordSplit.length > 7 && recordSplit[7].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[7]),1,4080000)) {
                    plf.setPlfTdocc(BigInteger.valueOf(Integer.parseInt(recordSplit[7])));
                }

                listPLF.add(plf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPLF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPLF, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePLFRecords(listPLF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createPRFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PRF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PRF> listPRF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PRF prf = new PRF();
                String[] recordSplit = record.split("\t");

                prf.setPrfNumbr(recordSplit[0]);
                if(recordSplit.length > 1 && recordSplit[1].length() > 0) {
                    prf.setPrfDescr(recordSplit[1]);
                }
                if(recordSplit.length > 2 && recordSplit[2].length() > 0) {
                    prf.setPrfInter(PRF.PrfInter.valueOf(recordSplit[2]));
                }
                if(recordSplit.length > 3 && recordSplit[3].length() > 0) {
                    prf.setPrfDspfmt(PRF.PrfDspfmt.valueOf(recordSplit[3]));
                }
                if(recordSplit.length > 4 && recordSplit[4].length() > 0) {
                    prf.setPrfRadix(_RADIX.valueOf(recordSplit[4]));
                }
                if(recordSplit.length > 5 && recordSplit[5].length() > 0) {
                    prf.setPrfNrange(Integer.parseInt(recordSplit[5]));
                }
                if(recordSplit.length > 6 && recordSplit[6].length() > 0) {
                    prf.setPrfUnit(recordSplit[6]);
                }

                listPRF.add(prf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPRF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPRF, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePRFRecords(listPRF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createPRVRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PRV";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PRV> listPRV = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PRV prv = new PRV();
                String[] recordSplit = record.split("\t");

                prv.setPrvNumbr(recordSplit[0]);
                prv.setPrvMinval(recordSplit[1]);
                if(recordSplit.length > 2 && recordSplit[2].length() > 0) {
                    prv.setPrvMaxval(recordSplit[2]);
                }

                listPRV.add(prv);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPRV,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPRV, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePRVRecords(listPRV,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }


    /** UTILITIES */
    public boolean checkIntegerRange(int n, int min, int max) throws Exception {
            if(( min <= n) && (n <= max)) {
                return true;
            } else {
                throw new Exception("Value " + n + " out of range.");
            }
    }

    public boolean checkLowerBoundary(int n, int min) throws Exception {
        if( n >= min ) {
            return  true;
        } else {
            throw new Exception("Value " + n + " is out of range");
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
