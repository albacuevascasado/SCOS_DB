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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

    /** SCOS TABLES */

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

                caf.setCafNumbr(recordSplit[CAF.COLUMNS.CAF_NUMBR.ordinal()]);
                if(recordSplit[CAF.COLUMNS.CAF_DESCR.ordinal()].length() > 0) {
                    caf.setCafDescr(recordSplit[CAF.COLUMNS.CAF_DESCR.ordinal()]);
                }
                caf.setCafEngfmt(_FMT.valueOf(recordSplit[CAF.COLUMNS.CAF_ENGFMT.ordinal()]));
                caf.setCafRawfmt(_FMT.valueOf(recordSplit[CAF.COLUMNS.CAF_RAWFMT.ordinal()]));

                if(recordSplit.length > CAF.COLUMNS.CAF_RADIX.ordinal() && recordSplit[CAF.COLUMNS.CAF_RADIX.ordinal()].length() > 0) {
                    caf.setCafRadix(_RADIX.valueOf(recordSplit[CAF.COLUMNS.CAF_RADIX.ordinal()]));
                }
                if(recordSplit.length > CAF.COLUMNS.CAF_UNIT.ordinal() && recordSplit[CAF.COLUMNS.CAF_UNIT.ordinal()].length() > 0) {
                    caf.setCafUnit(recordSplit[CAF.COLUMNS.CAF_UNIT.ordinal()]);
                }
                if(recordSplit.length > CAF.COLUMNS.CAF_NCURVE.ordinal() && recordSplit[CAF.COLUMNS.CAF_NCURVE.ordinal()].length() > 0) {
                    caf.setCafNcurve(Integer.parseInt(recordSplit[CAF.COLUMNS.CAF_NCURVE.ordinal()]));
                }
                if(recordSplit.length > CAF.COLUMNS.CAF_INTER.ordinal() && recordSplit[CAF.COLUMNS.CAF_INTER.ordinal()].length() > 0) {
                    caf.setCafInter(CAF.CafInter.valueOf(recordSplit[CAF.COLUMNS.CAF_INTER.ordinal()]));
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

                cap.setCaPNumbr(recordSplit[CAP.COLUMNS.CAP_NUMBR.ordinal()]);
                cap.setCapXvals(recordSplit[CAP.COLUMNS.CAP_XVALS.ordinal()]);
                cap.setCapYvals(recordSplit[CAP.COLUMNS.CAP_YVALS.ordinal()]);

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

                cca.setCcaNumbr(recordSplit[CCA.COLUMNS.CCA_NUMBR.ordinal()]);
                if(recordSplit.length > CCA.COLUMNS.CCA_DESCR.ordinal() && recordSplit[CCA.COLUMNS.CCA_DESCR.ordinal()].length() > 0) {
                    cca.setCcaDescr(recordSplit[CCA.COLUMNS.CCA_DESCR.ordinal()]);
                }
                if(recordSplit.length > CCA.COLUMNS.CCA_ENGFMT.ordinal() && recordSplit[CCA.COLUMNS.CCA_ENGFMT.ordinal()].length() > 0) {
                    cca.setCcaEngfmt(_FMT.valueOf(recordSplit[CCA.COLUMNS.CCA_ENGFMT.ordinal()]));
                }
                if(recordSplit.length > CCA.COLUMNS.CCA_RAWFMT.ordinal() && recordSplit[CCA.COLUMNS.CCA_RAWFMT.ordinal()].length() > 0) {
                    cca.setCcaRawfmt(_FMT.valueOf(recordSplit[CCA.COLUMNS.CCA_RAWFMT.ordinal()]));
                }
                if(recordSplit.length > CCA.COLUMNS.CCA_RADIX.ordinal() && recordSplit[CCA.COLUMNS.CCA_RADIX.ordinal()].length() > 0) {
                    cca.setCcaRadix(_RADIX.valueOf(recordSplit[CCA.COLUMNS.CCA_RADIX.ordinal()]));
                }
                if(recordSplit.length > CCA.COLUMNS.CCA_UNIT.ordinal() && recordSplit[CCA.COLUMNS.CCA_UNIT.ordinal()].length() > 0) {
                    cca.setCcaUnit(recordSplit[CCA.COLUMNS.CCA_UNIT.ordinal()]);
                }
                if(recordSplit.length > CCA.COLUMNS.CCA_NCURVE.ordinal() && recordSplit[CCA.COLUMNS.CCA_NCURVE.ordinal()].length() > 0) {
                    cca.setCcaNcurve(Integer.parseInt(recordSplit[CCA.COLUMNS.CCA_NCURVE.ordinal()]));
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

                ccf.setCcfCname(recordSplit[CCF.COLUMNS.CCF_CNAME.ordinal()]);
                ccf.setCcfDescr(recordSplit[CCF.COLUMNS.CCF_DESCR.ordinal()]);
                if(recordSplit[CCF.COLUMNS.CCF_DESCR2.ordinal()].length() > 0) {
                    ccf.setCcfDescr2(recordSplit[CCF.COLUMNS.CCF_DESCR2.ordinal()]);
                }
                if(recordSplit[CCF.COLUMNS.CCF_CTYPE.ordinal()].length() > 0) {
                    ccf.setCcfCtype(CCF.CcfCtype.valueOf(recordSplit[CCF.COLUMNS.CCF_CTYPE.ordinal()]));
                }
                if(recordSplit[CCF.COLUMNS.CCF_CRITICAL.ordinal()].length() > 0) {
                    ccf.setCcfCritical(_YN.valueOf(recordSplit[CCF.COLUMNS.CCF_CRITICAL.ordinal()]));
                }
                ccf.setCcfPktid(recordSplit[CCF.COLUMNS.CCF_PKTID.ordinal()]);
                if (recordSplit.length > CCF.COLUMNS.CCF_TYPE.ordinal() && recordSplit[CCF.COLUMNS.CCF_TYPE.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_TYPE.ordinal()]), 0, 255)) {
                    ccf.setCcfType(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_TYPE.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_STYPE.ordinal() && recordSplit[CCF.COLUMNS.CCF_STYPE.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_STYPE.ordinal()]), 0, 255)) {
                    ccf.setCcfStype(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_STYPE.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_APID.ordinal() && recordSplit[CCF.COLUMNS.CCF_APID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_APID.ordinal()]), 0, 65535)) {
                    ccf.setCcfApid(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_APID.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_NPARS.ordinal() && recordSplit[CCF.COLUMNS.CCF_NPARS.ordinal()].length() > 0) {
                    ccf.setCcfNpars(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_NPARS.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_PLAN.ordinal() && recordSplit[CCF.COLUMNS.CCF_PLAN.ordinal()].length() > 0) {
                    ccf.setCcfPlan(CCF.CcfPlan.valueOf(recordSplit[CCF.COLUMNS.CCF_PLAN.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_EXEC.ordinal() && recordSplit[CCF.COLUMNS.CCF_EXEC.ordinal()].length() > 0) {
                    ccf.setCcfExec(_YN.valueOf(recordSplit[CCF.COLUMNS.CCF_EXEC.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_ILSCOPE.ordinal() && recordSplit[CCF.COLUMNS.CCF_ILSCOPE.ordinal()].length() > 0) {
                    ccf.setCcfIlscope(CCF.CcfScope.valueOf(recordSplit[CCF.COLUMNS.CCF_ILSCOPE.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_ILSTAGE.ordinal() && recordSplit[CCF.COLUMNS.CCF_ILSTAGE.ordinal()].length() > 0) {
                    ccf.setCcfIlstage(CCF.CcfStage.valueOf(recordSplit[CCF.COLUMNS.CCF_ILSTAGE.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_SUBSYS.ordinal() && recordSplit[CCF.COLUMNS.CCF_SUBSYS.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[14]), 1, 255)) {
                    ccf.setCcfSubsys(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_SUBSYS.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_HIPRI.ordinal() && recordSplit[CCF.COLUMNS.CCF_HIPRI.ordinal()].length() > 0) {
                    ccf.setCcfHipri(_YN.valueOf(recordSplit[CCF.COLUMNS.CCF_HIPRI.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_MAPID.ordinal() && recordSplit[CCF.COLUMNS.CCF_MAPID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[16]), 0, 63)) {
                    ccf.setCcfMapid(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_MAPID.ordinal()]));
                }
                if(recordSplit.length > CCF.COLUMNS.CCF_DEFSET.ordinal() && recordSplit[CCF.COLUMNS.CCF_DEFSET.ordinal()].length() > 0) {
                    ccf.setCcfDefset(recordSplit[CCF.COLUMNS.CCF_DEFSET.ordinal()]);
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_RAPID.ordinal()  && recordSplit[CCF.COLUMNS.CCF_RAPID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[18]), 1, 65535)) {
                    ccf.setCcfRapid(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_RAPID.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_ACK.ordinal()  && recordSplit[CCF.COLUMNS.CCF_ACK.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[19]), 0, 15)) {
                    ccf.setCcfAck(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_ACK.ordinal()]));
                }
                if (recordSplit.length > CCF.COLUMNS.CCF_SUBSCHEDID.ordinal() && recordSplit[CCF.COLUMNS.CCF_SUBSCHEDID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[20]), 1, 65535)) {
                    ccf.setCcfSubschedid(Integer.parseInt(recordSplit[CCF.COLUMNS.CCF_SUBSCHEDID.ordinal()]));
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

                ccs.setCcsNumbr(recordSplit[CCS.COLUMNS.CCS_NUMBR.ordinal()]);
                ccs.setCcsXvals(recordSplit[CCS.COLUMNS.CCS_XVALS.ordinal()]);
                ccs.setCcsYvals(recordSplit[CCS.COLUMNS.CCS_YVALS.ordinal()]);

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

                cdf.setCdfCname(recordSplit[CDF.COLUMNS.CDF_CNAME.ordinal()]);
                cdf.setCdfEltype(CDF.CdfType.valueOf(recordSplit[CDF.COLUMNS.CDF_ELTYPE.ordinal()]));
                if(recordSplit[CDF.COLUMNS.CDF_DESCR.ordinal()].length() > 0) {
                    cdf.setCdfDescr(recordSplit[CDF.COLUMNS.CDF_DESCR.ordinal()]);
                }
                cdf.setCdfEllen(Integer.parseInt(recordSplit[CDF.COLUMNS.CDF_ELLEN.ordinal()]));
                cdf.setCdfBit(Integer.parseInt(recordSplit[CDF.COLUMNS.CDF_BIT.ordinal()]));
                if(recordSplit.length > CDF.COLUMNS.CDF_GRPSIZE.ordinal() && recordSplit[CDF.COLUMNS.CDF_GRPSIZE.ordinal()].length() > 0) {
                    cdf.setCdfGrpsize(Integer.parseInt(recordSplit[CDF.COLUMNS.CDF_GRPSIZE.ordinal()]));
                }
                if(recordSplit.length > CDF.COLUMNS.CDF_PNAME.ordinal() && recordSplit[CDF.COLUMNS.CDF_PNAME.ordinal()].length() > 0) {
                    cdf.setCdfPname(recordSplit[CDF.COLUMNS.CDF_PNAME.ordinal()]);
                }
                if(recordSplit.length > CDF.COLUMNS.CDF_INTER.ordinal() && recordSplit[CDF.COLUMNS.CDF_INTER.ordinal()].length() > 0) {
                    cdf.setCdfInter(CDF.CdfInter.valueOf(recordSplit[CDF.COLUMNS.CDF_INTER.ordinal()]));
                }
                if(recordSplit.length > CDF.COLUMNS.CDF_VALUE.ordinal() && recordSplit[CDF.COLUMNS.CDF_VALUE.ordinal()].length() > 0) {
                    cdf.setCdfValue(recordSplit[CDF.COLUMNS.CDF_VALUE.ordinal()]);
                }
                if(recordSplit.length > CDF.COLUMNS.CDF_TMID.ordinal() && recordSplit[CDF.COLUMNS.CDF_TMID.ordinal()].length() > 0) {
                    cdf.setCdfTmid(recordSplit[CDF.COLUMNS.CDF_TMID.ordinal()]);
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

//                cpc.setCpcPname(recordSplit[CPC.COLUMNS.CPC_PNAME.getHierarchy()]);
                cpc.setCpcPname(recordSplit[CPC.COLUMNS.CPC_PNAME.ordinal()]);
//                if(recordSplit[CPC.COLUMNS.CPC_DESCR.getHierarchy()].length() > 0) {
//                    cpc.setCpcDescr(recordSplit[CPC.COLUMNS.CPC_DESCR.getHierarchy()]);
//                }
                if(recordSplit[CPC.COLUMNS.CPC_DESCR.ordinal()].length() > 0) {
                    cpc.setCpcDescr(recordSplit[CPC.COLUMNS.CPC_DESCR.ordinal()]);
                }
//                cpc.setCpcPtc(Double.parseDouble(recordSplit[CPC.COLUMNS.CPC_PTC.getHierarchy()]));
                cpc.setCpcPtc(Double.parseDouble(recordSplit[CPC.COLUMNS.CPC_PTC.ordinal()]));
//                cpc.setCpcPfc(Integer.parseInt(recordSplit[CPC.COLUMNS.CPC_PFC.getHierarchy()]));
                cpc.setCpcPfc(Integer.parseInt(recordSplit[CPC.COLUMNS.CPC_PFC.ordinal()]));
//                if(recordSplit.length > CPC.COLUMNS.CPC_DISPFMT.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_DISPFMT.getHierarchy()].length() > 0) {
//                    cpc.setCpcDispfmt(CPC.CpcDispfmt.valueOf(recordSplit[CPC.COLUMNS.CPC_DISPFMT.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_DISPFMT.ordinal() && recordSplit[CPC.COLUMNS.CPC_DISPFMT.ordinal()].length() > 0) {
                    cpc.setCpcDispfmt(CPC.CpcDispfmt.valueOf(recordSplit[CPC.COLUMNS.CPC_DISPFMT.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_RADIX.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_RADIX.getHierarchy()].length() > 0) {
//                    cpc.setCpcRadix(_RADIX.valueOf(recordSplit[CPC.COLUMNS.CPC_RADIX.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_RADIX.ordinal() && recordSplit[CPC.COLUMNS.CPC_RADIX.ordinal()].length() > 0) {
                    cpc.setCpcRadix(_RADIX.valueOf(recordSplit[CPC.COLUMNS.CPC_RADIX.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_UNIT.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_UNIT.getHierarchy()].length() > 0) {
//                    cpc.setCpcUnit(recordSplit[CPC.COLUMNS.CPC_UNIT.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_UNIT.ordinal() && recordSplit[CPC.COLUMNS.CPC_UNIT.ordinal()].length() > 0) {
                    cpc.setCpcUnit(recordSplit[CPC.COLUMNS.CPC_UNIT.ordinal()]);
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_CATEG.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_CATEG.getHierarchy()].length() > 0) {
//                   cpc.setCpcCateg(CPC.CpcCateg.valueOf(recordSplit[CPC.COLUMNS.CPC_CATEG.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_CATEG.ordinal() && recordSplit[CPC.COLUMNS.CPC_CATEG.ordinal()].length() > 0) {
                    cpc.setCpcCateg(CPC.CpcCateg.valueOf(recordSplit[CPC.COLUMNS.CPC_CATEG.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_PRFREF.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_PRFREF.getHierarchy()].length() > 0) {
//                   cpc.setCpcPrfref(recordSplit[CPC.COLUMNS.CPC_PRFREF.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_PRFREF.ordinal() && recordSplit[CPC.COLUMNS.CPC_PRFREF.ordinal()].length() > 0) {
                    cpc.setCpcPrfref(recordSplit[CPC.COLUMNS.CPC_PRFREF.ordinal()]);
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_CCAREF.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_CCAREF.getHierarchy()].length() > 0) {
//                    cpc.setCpcCcaref(recordSplit[CPC.COLUMNS.CPC_CCAREF.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_CCAREF.ordinal() && recordSplit[CPC.COLUMNS.CPC_CCAREF.ordinal()].length() > 0) {
                    cpc.setCpcCcaref(recordSplit[CPC.COLUMNS.CPC_CCAREF.ordinal()]);
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_PAFREF.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_PAFREF.getHierarchy()].length() > 0) {
//                    cpc.setCpcPafref(recordSplit[CPC.COLUMNS.CPC_PAFREF.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_PAFREF.ordinal() && recordSplit[CPC.COLUMNS.CPC_PAFREF.ordinal()].length() > 0) {
                    cpc.setCpcPafref(recordSplit[CPC.COLUMNS.CPC_PAFREF.ordinal()]);
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_INTER.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_INTER.getHierarchy()].length() > 0) {
//                    cpc.setCpcInter(CPC.CpcInter.valueOf(recordSplit[CPC.COLUMNS.CPC_INTER.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_INTER.ordinal() && recordSplit[CPC.COLUMNS.CPC_INTER.ordinal()].length() > 0) {
                    cpc.setCpcInter(CPC.CpcInter.valueOf(recordSplit[CPC.COLUMNS.CPC_INTER.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_DEFVAL.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_DEFVAL.getHierarchy()].length() > 0) {
//                    cpc.setCpcDefval(recordSplit[CPC.COLUMNS.CPC_DEFVAL.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_DEFVAL.ordinal() && recordSplit[CPC.COLUMNS.CPC_DEFVAL.ordinal()].length() > 0) {
                    cpc.setCpcDefval(recordSplit[CPC.COLUMNS.CPC_DEFVAL.ordinal()]);
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_CORR.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_CORR.getHierarchy()].length() > 0) {
//                    cpc.setCpcCorr(_YN.valueOf(recordSplit[CPC.COLUMNS.CPC_CORR.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_CORR.ordinal() && recordSplit[CPC.COLUMNS.CPC_CORR.ordinal()].length() > 0) {
                    cpc.setCpcCorr(_YN.valueOf(recordSplit[CPC.COLUMNS.CPC_CORR.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_OBTID.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_OBTID.getHierarchy()].length() > 0) {
//                    cpc.setCpcObtid(Integer.parseInt(recordSplit[CPC.COLUMNS.CPC_OBTID.getHierarchy()]));
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_OBTID.ordinal() && recordSplit[CPC.COLUMNS.CPC_OBTID.ordinal()].length() > 0) {
                    cpc.setCpcObtid(Integer.parseInt(recordSplit[CPC.COLUMNS.CPC_OBTID.ordinal()]));
                }
//                if(recordSplit.length > CPC.COLUMNS.CPC_ENDIAN.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_ENDIAN.getHierarchy()].length() > 0) {
//                    cpc.setCpcEndian(CPC.CpcEndian.valueOf(recordSplit[CPC.COLUMNS.CPC_ENDIAN.getHierarchy()]));
//                }
                    if(recordSplit.length > CPC.COLUMNS.CPC_ENDIAN.ordinal() && recordSplit[CPC.COLUMNS.CPC_ENDIAN.ordinal()].length() > 0) {
                        cpc.setCpcEndian(CPC.CpcEndian.valueOf(recordSplit[CPC.COLUMNS.CPC_ENDIAN.ordinal()]));
                    }
//                if(recordSplit.length > CPC.COLUMNS.CPC_DESCR2.getHierarchy() && recordSplit[CPC.COLUMNS.CPC_DESCR2.getHierarchy()].length() > 0) {
//                    cpc.setCpcDescr2(recordSplit[CPC.COLUMNS.CPC_DESCR2.getHierarchy()]);
//                }
                if(recordSplit.length > CPC.COLUMNS.CPC_DESCR2.ordinal() && recordSplit[CPC.COLUMNS.CPC_DESCR2.ordinal()].length() > 0) {
                    cpc.setCpcDescr2(recordSplit[CPC.COLUMNS.CPC_DESCR2.ordinal()]);
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

    public void createCSFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "CSF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<CSF> listCSF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                CSF csf = new CSF();
                String[] recordSplit = record.split("\t");

                csf. setCsfName(recordSplit[CSF.COLUMNS.CSF_NAME.ordinal()]);
                csf.setCsfDesc(recordSplit[CSF.COLUMNS.CSF_DESC.ordinal()]);
                if(recordSplit.length > CSF.COLUMNS.CSF_DESC2.ordinal() && recordSplit[CSF.COLUMNS.CSF_DESC2.ordinal()].length() > 0) {
                    csf.setCsfDesc2(recordSplit[CSF.COLUMNS.CSF_DESC2.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_IFTT.ordinal() && recordSplit[CSF.COLUMNS.CSF_IFTT.ordinal()].length() > 0) {
                    csf.setCsfIftt(CSF.CsfIftt.valueOf(recordSplit[CSF.COLUMNS.CSF_IFTT.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_NFPARS.ordinal() && recordSplit[CSF.COLUMNS.CSF_NFPARS.ordinal()].length() > 0) {
                    csf.setCsfNfpars(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_NFPARS.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_ELEMS.ordinal() && recordSplit[CSF.COLUMNS.CSF_ELEMS.ordinal()].length() > 0) {
                    csf.setCsfElems(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_ELEMS.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_CRITICAL.ordinal() && recordSplit[CSF.COLUMNS.CSF_CRITICAL.ordinal()].length() > 0) {
                    csf.setCsfCritical(_YN.valueOf(recordSplit[CSF.COLUMNS.CSF_CRITICAL.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_PLAN.ordinal() && recordSplit[CSF.COLUMNS.CSF_PLAN.ordinal()].length() > 0) {
                    csf.setCsfPlan(CSF.CsfPlan.valueOf(recordSplit[CSF.COLUMNS.CSF_PLAN.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_EXEC.ordinal() && recordSplit[CSF.COLUMNS.CSF_EXEC.ordinal()].length() > 0) {
                   csf.setCsfExec(_YN.valueOf(recordSplit[CSF.COLUMNS.CSF_EXEC.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_SUBSYS.ordinal() && recordSplit[CSF.COLUMNS.CSF_SUBSYS.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_SUBSYS.ordinal()]),1,255)) {
                   csf.setCsfSubsys(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_SUBSYS.ordinal()]));
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_GENTIME.ordinal() && recordSplit[CSF.COLUMNS.CSF_GENTIME.ordinal()].length() > 0) {
                    csf.setCsfGentime(recordSplit[CSF.COLUMNS.CSF_GENTIME.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_DOCNAME.ordinal() && recordSplit[CSF.COLUMNS.CSF_DOCNAME.ordinal()].length() > 0) {
                    csf.setCsfDocname(recordSplit[CSF.COLUMNS.CSF_DOCNAME.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_ISSUE.ordinal() && recordSplit[CSF.COLUMNS.CSF_ISSUE.ordinal()].length() > 0) {
                    csf.setCsfIssue(recordSplit[CSF.COLUMNS.CSF_ISSUE.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_DATE.ordinal() && recordSplit[CSF.COLUMNS.CSF_DATE.ordinal()].length() > 0) {
                    csf.setCsfDate(recordSplit[CSF.COLUMNS.CSF_DATE.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_DEFSET.ordinal() && recordSplit[CSF.COLUMNS.CSF_DEFSET.ordinal()].length() > 0) {
                    csf.setCsfDefset(recordSplit[CSF.COLUMNS.CSF_DEFSET.ordinal()]);
                }
                if(recordSplit.length > CSF.COLUMNS.CSF_SUBSCHEDID.ordinal() && recordSplit[CSF.COLUMNS.CSF_SUBSCHEDID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_SUBSCHEDID.ordinal()]),1,65535)) {
                    csf.setCsfSubschedid(Integer.parseInt(recordSplit[CSF.COLUMNS.CSF_SUBSCHEDID.ordinal()]));
                }

                listCSF.add(csf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listCSF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listCSF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveCSFRecords(listCSF,scostables,odbData);
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

                cur.setCurPname(recordSplit[CUR.COLUMNS.CUR_PNAME.ordinal()]);
                cur.setCurPos(Integer.parseInt(recordSplit[CUR.COLUMNS.CUR_POS.ordinal()]));
                cur.setCurRlchk(recordSplit[CUR.COLUMNS.CUR_RLCHK.ordinal()]);
                cur.setCurValpar(Integer.parseInt(recordSplit[CUR.COLUMNS.CUR_VALPAR.ordinal()]));
                cur.setCurSelect(recordSplit[CUR.COLUMNS.CUR_SELECT.ordinal()]);

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

                cve.setCveCvsid(Integer.parseInt(recordSplit[CVE.COLUMNS.CVE_CVSID.ordinal()]));
                cve.setCveParnam(recordSplit[CVE.COLUMNS.CVE_PARNAM.ordinal()]);
                if(recordSplit.length > CVE.COLUMNS.CVE_INTER.ordinal() && recordSplit[CVE.COLUMNS.CVE_INTER.ordinal()].length() > 0) {
                    cve.setCveInter(CVE.CveInter.valueOf(recordSplit[CVE.COLUMNS.CVE_INTER.ordinal()]));
                }
                if(recordSplit.length > CVE.COLUMNS.CVE_VAL.ordinal() && recordSplit[CVE.COLUMNS.CVE_VAL.ordinal()].length() > 0) {
                    cve.setCveVal(recordSplit[CVE.COLUMNS.CVE_VAL.ordinal()]);
                }
                if(recordSplit.length > CVE.COLUMNS.CVE_TOL.ordinal() && recordSplit[CVE.COLUMNS.CVE_TOL.ordinal()].length() > 0) {
                    cve.setCveTol(recordSplit[CVE.COLUMNS.CVE_TOL.ordinal()]);
                }
                if(recordSplit.length > CVE.COLUMNS.CVE_CHECK.ordinal() && recordSplit[CVE.COLUMNS.CVE_CHECK.ordinal()].length() > 0) {
                    cve.setCveCheck(CVE.CveCheck.valueOf(recordSplit[CVE.COLUMNS.CVE_CHECK.ordinal()]));
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

                cvp.setCvpTask(recordSplit[CVP.COLUMNS.CVP_TASK.ordinal()]);
                cvp.setCvpType(CVP.CvpType.valueOf(recordSplit[CVP.COLUMNS.CVP_TYPE.ordinal()]));
                cvp.setCvpCvsid(Integer.parseInt(recordSplit[CVP.COLUMNS.CVP_CVSID.ordinal()]));

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

                if(checkIntegerRange(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_ID.ordinal()]), 0, 32767)) {
                    cvs.setCvsId(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_ID.ordinal()]));
                }
                if(checkAlphaNumericRange(recordSplit[CVS.COLUMNS.CVS_TYPE.ordinal()].charAt(0), CVS.arrayCVSType)) {
                    cvs.setCvsType(recordSplit[CVS.COLUMNS.CVS_TYPE.ordinal()].charAt(0));
                }
                cvs.setCvsSource(CVS.CvsSource.valueOf(recordSplit[CVS.COLUMNS.CVS_SOURCE.ordinal()]));
                if(checkLowerBoundary(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_START.ordinal()]), 0)) {
                    cvs.setCvsStart(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_START.ordinal()]));
                }
                if(checkLowerBoundary(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_INTERVAL.ordinal()]), 0)) {
                    cvs.setCvsInterval(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_INTERVAL.ordinal()]));
                }
                if(recordSplit.length > CVS.COLUMNS.CVS_SPID.ordinal() && recordSplit[CVS.COLUMNS.CVS_SPID.ordinal()].length() > 0) {
                    cvs.setCvsSpid(BigInteger.valueOf(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_SPID.ordinal()])));
                }
                if(recordSplit.length > CVS.COLUMNS.CVS_UNCERTAINTY.ordinal() && recordSplit[CVS.COLUMNS.CVS_UNCERTAINTY.ordinal()].length() > 0 && checkLowerBoundary(Integer.parseInt(recordSplit[6]),0)) {
                    cvs.setCvsUncertainty(Integer.parseInt(recordSplit[CVS.COLUMNS.CVS_UNCERTAINTY.ordinal()]));
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

                dpc.setDpcNumbe(recordSplit[DPC.COLUMNS.DPC_NUMBE.ordinal()]);
                if(recordSplit[DPC.COLUMNS.DPC_NAME.ordinal()].length() > 0) {
                    dpc.setDpcName(recordSplit[DPC.COLUMNS.DPC_NAME.ordinal()]);
                }
                dpc.setDpcFldn(Integer.parseInt(recordSplit[DPC.COLUMNS.DPC_FLDN.ordinal()]));
                if(recordSplit.length > DPC.COLUMNS.DPC_COMM.ordinal() && recordSplit[DPC.COLUMNS.DPC_COMM.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[3]),0,9999)) {
                    dpc.setDpcComm(Integer.parseInt(recordSplit[DPC.COLUMNS.DPC_COMM.ordinal()]));
                }
                if(recordSplit.length > DPC.COLUMNS.DPC_MODE.ordinal() && recordSplit[DPC.COLUMNS.DPC_MODE.ordinal()].length() > 0) {
                    dpc.setDpcMode(_YN.valueOf(recordSplit[DPC.COLUMNS.DPC_MODE.ordinal()]));
                }
                if(recordSplit.length > DPC.COLUMNS.DPC_FORM.ordinal() && recordSplit[DPC.COLUMNS.DPC_FORM.ordinal()].length() > 0) {
                   dpc.setDpcForm(DPC.DpcForm.valueOf(recordSplit[DPC.COLUMNS.DPC_FORM.ordinal()]));
                }
                if(recordSplit.length > DPC.COLUMNS.DPC_TEXT.ordinal() && recordSplit[DPC.COLUMNS.DPC_TEXT.ordinal()].length() > 0) {
                    dpc.setDpcText(recordSplit[DPC.COLUMNS.DPC_TEXT.ordinal()]);
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

    public void createDPFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "DPF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<DPF> listDPF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                DPF dpf = new DPF();
                String[] recordSplit = record.split("\t");

                dpf.setDpfNumbe(recordSplit[DPF.COLUMNS.DPF_NUMBE.ordinal()]);
                if(checkAlphaNumericRange(recordSplit[DPF.COLUMNS.DPF_TYPE.ordinal()].charAt(0), DPF.arrayDPFType)) {
                    dpf.setDpfType(recordSplit[DPF.COLUMNS.DPF_TYPE.ordinal()].charAt(0));
                }
                if(recordSplit.length > DPF.COLUMNS.DPF_HEAD.ordinal() && recordSplit[DPF.COLUMNS.DPF_HEAD.ordinal()].length() > 0) {
                    dpf.setDpfHead(recordSplit[DPF.COLUMNS.DPF_HEAD.ordinal()]);
                }

                listDPF.add(dpf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listDPF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listDPF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveDPFRecords(listDPF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createDSTRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GRP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<DST> listDST = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                DST dst = new DST();
                String[] recordSplit = record.split("\t");

                if(checkIntegerRange(Integer.parseInt(recordSplit[DST.COLUMNS.DST_APID.ordinal()]),0,65535)) {
                    dst.setDstApid(Integer.parseInt(recordSplit[DST.COLUMNS.DST_ROUTE.ordinal()]));
                }
                dst.setDstRoute(recordSplit[1]);

                listDST.add(dst);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listDST,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listDST, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveDSTRecords(listDST,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }

    }

    public void createGPCRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GPC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<GPC> listGPC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                GPC gpc = new GPC();
                String[] recordSplit = record.split("\t");

                gpc.setGpcNumbe(recordSplit[GPC.COLUMNS.GPC_NUMBE.ordinal()]);
                gpc.setGpcPos(Integer.parseInt(recordSplit[GPC.COLUMNS.GPC_POS.ordinal()]));
                if(checkAlphaNumericRange(recordSplit[GPC.COLUMNS.GPC_WHERE.ordinal()].charAt(0),GPC.arrayGPCWhere)) {
                    gpc.setGpcWhere(recordSplit[GPC.COLUMNS.GPC_WHERE.ordinal()].charAt(0));
                }
                gpc.setGpcName(recordSplit[GPC.COLUMNS.GPC_NAME.ordinal()]);
                if(recordSplit[GPC.COLUMNS.GPC_RAW.ordinal()].length() > 0) {
                    gpc.setGpcRaw(GPC.GpcRaw.valueOf(recordSplit[GPC.COLUMNS.GPC_RAW.ordinal()]));
                }
                gpc.setGpcMinim(recordSplit[GPC.COLUMNS.GPC_MINIM.ordinal()]);
                gpc.setGpcMaxim(recordSplit[GPC.COLUMNS.GPC_MAXIM.ordinal()]);
                if(checkAlphaNumericRange(recordSplit[GPC.COLUMNS.GPC_PRCLR.ordinal()].charAt(0),GPC.arrayGPCPrclr)) {
                    gpc.setGpcPrclr(recordSplit[GPC.COLUMNS.GPC_PRCLR.ordinal()].charAt(0));
                }
                if(recordSplit.length > GPC.COLUMNS.GPC_SYMB0.ordinal() && recordSplit[GPC.COLUMNS.GPC_SYMB0.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[8].charAt(0),GPC.arrayGPCSymb0)) {
                    gpc.setGpcSymb0(recordSplit[GPC.COLUMNS.GPC_SYMB0.ordinal()].charAt(0));
                }
                if(recordSplit.length > GPC.COLUMNS.GPC_LINE.ordinal() && recordSplit[GPC.COLUMNS.GPC_LINE.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[9].charAt(0),GPC.arrayGPCLine)) {
                    gpc.setGpcLine(recordSplit[GPC.COLUMNS.GPC_LINE.ordinal()].charAt(0));
                }
                if(recordSplit.length > GPC.COLUMNS.GPC_DOMAIN.ordinal() && recordSplit[GPC.COLUMNS.GPC_DOMAIN.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[10]),0,65535)) {
                    gpc.setGpcDomain(Integer.parseInt(recordSplit[GPC.COLUMNS.GPC_DOMAIN.ordinal()]));
                }

                listGPC.add(gpc);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listGPC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listGPC, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveGPCRecords(listGPC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createGPFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "GPF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<GPF> listGPF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                GPF gpf = new GPF();
                String[] recordSplit = record.split("\t");

                gpf.setGpfNumbe(recordSplit[GPF.COLUMNS.GPF_NUMBE.ordinal()]);
                gpf.setGpfType(GPF.GpfType.valueOf(recordSplit[GPF.COLUMNS.GPF_TYPE.ordinal()]));
                if(recordSplit[GPF.COLUMNS.GPF_HEAD.ordinal()].length() > 0) {
                    gpf.setGpfHead(recordSplit[GPF.COLUMNS.GPF_HEAD.ordinal()]);
                }
                if(recordSplit[GPF.COLUMNS.GPF_SCROL.ordinal()].length() > 0) {
                    gpf.setGpfScrol(_YN.valueOf(recordSplit[GPF.COLUMNS.GPF_SCROL.ordinal()]));
                }
                if(recordSplit[GPF.COLUMNS.GPF_HCOPY.ordinal()].length() > 0) {
                    gpf.setGpfHcopy(_YN.valueOf(recordSplit[GPF.COLUMNS.GPF_HCOPY.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_DAYS.ordinal()]),0,99)) {
                    gpf.setGpfDays(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_DAYS.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_HOURS.ordinal()]),0,23)) {
                    gpf.setGpfHours(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_HOURS.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_MINUT.ordinal()]),0,59)) {
                    gpf.setGpfMinut(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_MINUT.ordinal()]));
                }
                if(checkAlphaNumericRange(recordSplit[GPF.COLUMNS.GPF_AXCLR.ordinal()].charAt(0),GPF.arrayGpfAxclr)) {
                    gpf.setGpfAxclr(recordSplit[GPF.COLUMNS.GPF_AXCLR.ordinal()].charAt(0));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_XTICK.ordinal()]),1,99)) {
                    gpf.setGpfXtic(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_XTICK.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_YTICK.ordinal()]),1,99)) {
                    gpf.setGpfYtic(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_YTICK.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_XGRID.ordinal()]),1,99)) {
                    gpf.setGpfXgrid(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_XGRID.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_YGRID.ordinal()]),1,99)) {
                    gpf.setGpfYgrid(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_YGRID.ordinal()]));
                }
                if(recordSplit.length > GPF.COLUMNS.GPF_UPUN.ordinal() && recordSplit[GPF.COLUMNS.GPF_UPUN.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_UPUN.ordinal()]),1,99))  {
                    gpf.setGpfUpun(Integer.parseInt(recordSplit[GPF.COLUMNS.GPF_UPUN.ordinal()]));
                }

                listGPF.add(gpf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listGPF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listGPF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveGPFRecords(listGPF,scostables,odbData);
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

                grp.setGrpName(recordSplit[GRP.COLUMNS.GRP_NAME.ordinal()]);
                grp.setGrpDescr(recordSplit[GRP.COLUMNS.GRP_DESCR.ordinal()]);
                grp.setGrpGtype(GRP.GrpGtype.valueOf(recordSplit[GRP.COLUMNS.GRP_GTYPE.ordinal()]));

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

                grpa.setGrpaGname(recordSplit[GRPA.COLUMNS.GRPA_GNAME.ordinal()]);
                grpa.setGrpaPaname(recordSplit[GRPA.COLUMNS.GRPA_PANAME.ordinal()]);

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

                grpk.setGrpkGname(recordSplit[GRPK.COLUMNS.GRPK_GNAME.ordinal()]);
                grpk.setGrpkPkspid(Long.parseLong(recordSplit[GRPK.COLUMNS.GRPK_PKSPID.ordinal()]));

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

                lgf.setLgfIdent(recordSplit[LGF.COLUMNS.LGF_IDENT.ordinal()]);
                if(recordSplit[LGF.COLUMNS.LGF_DESCR.ordinal()].length() > 0) {
                    lgf.setLgfDescr(recordSplit[LGF.COLUMNS.LGF_DESCR.ordinal()]);
                }
                lgf.setLgfPol1(recordSplit[LGF.COLUMNS.LGF_POL1.ordinal()]);
                if(recordSplit.length > LGF.COLUMNS.LGF_POL2.ordinal() && recordSplit[LGF.COLUMNS.LGF_POL2.ordinal()].length() > 0) {
                    lgf.setLgfPol2(recordSplit[LGF.COLUMNS.LGF_POL2.ordinal()]);
                }
                if(recordSplit.length > LGF.COLUMNS.LGF_POL3.ordinal() && recordSplit[LGF.COLUMNS.LGF_POL3.ordinal()].length() > 0) {
                    lgf.setLgfPol3(recordSplit[LGF.COLUMNS.LGF_POL3.ordinal()]);
                }
                if(recordSplit.length > LGF.COLUMNS.LGF_POL4.ordinal() && recordSplit[LGF.COLUMNS.LGF_POL4.ordinal()].length() > 0) {
                   lgf.setLgfPol4(recordSplit[LGF.COLUMNS.LGF_POL4.ordinal()]);
                }
                if(recordSplit.length > LGF.COLUMNS.LGF_POL5.ordinal() && recordSplit[LGF.COLUMNS.LGF_POL5.ordinal()].length() > 0) {
                    lgf.setLgfPol5(recordSplit[LGF.COLUMNS.LGF_POL5.ordinal()]);
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

                mcf.setMcfIdent(recordSplit[MCF.COLUMNS.MCF_IDENT.ordinal()]);
                if(recordSplit[MCF.COLUMNS.MCF_DESCR.ordinal()].length() > 0) {
                    mcf.setMcfDescr(recordSplit[MCF.COLUMNS.MCF_DESCR.ordinal()]);
                }
                mcf.setMcfPol1(recordSplit[MCF.COLUMNS.MCF_POL1.ordinal()]);
                if(recordSplit.length > MCF.COLUMNS.MCF_POL2.ordinal() && recordSplit[MCF.COLUMNS.MCF_POL2.ordinal()].length() > 0) {
                    mcf.setMcfPol2(recordSplit[MCF.COLUMNS.MCF_POL2.ordinal()]);
                }
                if(recordSplit.length > MCF.COLUMNS.MCF_POL3.ordinal() && recordSplit[MCF.COLUMNS.MCF_POL3.ordinal()].length() > 0) {
                    mcf.setMcfPol3(recordSplit[MCF.COLUMNS.MCF_POL3.ordinal()]);
                }
                if(recordSplit.length > MCF.COLUMNS.MCF_POL4.ordinal() && recordSplit[MCF.COLUMNS.MCF_POL4.ordinal()].length() > 0) {
                    mcf.setMcfPol4(recordSplit[MCF.COLUMNS.MCF_POL4.ordinal()]);
                }
                if(recordSplit.length > MCF.COLUMNS.MCF_POL5.ordinal() && recordSplit[MCF.COLUMNS.MCF_POL5.ordinal()].length() > 0) {
                    mcf.setMcfPol5(recordSplit[MCF.COLUMNS.MCF_POL5.ordinal()]);
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

                ocf.setOcfName(recordSplit[OCF.COLUMNS.OCF_NAME.ordinal()]);
                ocf.setOcfNbchck(Integer.parseInt(recordSplit[OCF.COLUMNS.OCF_NBCHCK.ordinal()]));
                if(checkIntegerRange(Integer.parseInt(recordSplit[OCF.COLUMNS.OCF_NBOOl.ordinal()]),1,16)) {
                   ocf.setOcfNbool(Integer.parseInt(recordSplit[OCF.COLUMNS.OCF_NBOOl.ordinal()]));
                }
                ocf.setOcfInter(OCF.OcfInter.valueOf(recordSplit[OCF.COLUMNS.OCF_INTER.ordinal()]));
                ocf.setOcfCodin(OCF.OcfCodin.valueOf(recordSplit[OCF.COLUMNS.OCF_CODIN.ordinal()]));

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

                ocp.setOcpName(recordSplit[OCP.COLUMNS.OCP_NAME.ordinal()]);
                ocp.setOcpPos(Integer.parseInt(recordSplit[OCP.COLUMNS.OCP_POS.ordinal()]));
                ocp.setOcpType(OCP.OcpType.valueOf(recordSplit[OCP.COLUMNS.OCP_TYPE.ordinal()]));
                if(recordSplit.length > OCP.COLUMNS.OCP_LVALU.ordinal() && recordSplit[OCP.COLUMNS.OCP_LVALU.ordinal()].length() > 0) {
                    ocp.setOcpLvalu(recordSplit[OCP.COLUMNS.OCP_LVALU.ordinal()]);
                }
                if(recordSplit.length > OCP.COLUMNS.OCP_HVALU.ordinal() && recordSplit[OCP.COLUMNS.OCP_HVALU.ordinal()].length() > 0) {
                    ocp.setOcpHvalu(recordSplit[OCP.COLUMNS.OCP_HVALU.ordinal()]);
                }
                if(recordSplit.length > OCP.COLUMNS.OCP_RLCHK.ordinal() && recordSplit[OCP.COLUMNS.OCP_RLCHK.ordinal()].length() > 0) {
                    ocp.setOcpRlchk(recordSplit[OCP.COLUMNS.OCP_RLCHK.ordinal()]);
                }
                if(recordSplit.length > OCP.COLUMNS.OCP_VALPAR.ordinal() && recordSplit[OCP.COLUMNS.OCP_VALPAR.ordinal()].length() > 0) {
                    ocp.setOcpValpar(Integer.parseInt(recordSplit[OCP.COLUMNS.OCP_VALPAR.ordinal()]));
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

    public void createPAFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PAF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PAF> listPAF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PAF paf = new PAF();
                String[] recordSplit = record.split("\t");

                paf.setPAF_NUMBR(recordSplit[PAF.COLUMNS.PAF_NUMBR.ordinal()]);
                if(recordSplit.length > PAF.COLUMNS.PAF_DESCR.ordinal() && recordSplit[PAF.COLUMNS.PAF_DESCR.ordinal()].length() > 0) {
                    paf.setPAF_DESCR(recordSplit[PAF.COLUMNS.PAF_DESCR.ordinal()]);
                }
                if(recordSplit.length > PAF.COLUMNS.PAF_RAWFMT.ordinal() && recordSplit[PAF.COLUMNS.PAF_RAWFMT.ordinal()].length() > 0) {
                    paf.setPAF_RAWFMT(_FMT.valueOf(recordSplit[PAF.COLUMNS.PAF_RAWFMT.ordinal()]));
                }
                if(recordSplit.length > PAF.COLUMNS.PAF_NALIAS.ordinal() && recordSplit[PAF.COLUMNS.PAF_NALIAS.ordinal()].length() > 0) {
                    paf.setPAF_NALIAS(Integer.parseInt(recordSplit[PAF.COLUMNS.PAF_NALIAS.ordinal()]));
                }

                listPAF.add(paf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPAF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPAF, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePAFRecords(listPAF,scostables,odbData);
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

                pas.setPasNumbr(recordSplit[PAS.COLUMNS.PAS_NUMBR.ordinal()]);
                pas.setPasAltxt(recordSplit[PAS.COLUMNS.PAS_ALTXT.ordinal()]);
                pas.setPasAlval(recordSplit[PAS.COLUMNS.PAS_ALVAL.ordinal()]);

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

                pcdf.setPcdfTcname(recordSplit[PCDF.COLUMNS.PCDF_TCNAME.ordinal()]);
                if(recordSplit[PCDF.COLUMNS.PCDF_DESC.ordinal()].length() > 0) {
                    pcdf.setPcdfDesc(recordSplit[PCDF.COLUMNS.PCDF_DESC.ordinal()]);
                }
                pcdf.setPcdfType(PCDF.PcdfType.valueOf(recordSplit[PCDF.COLUMNS.PCDF_TYPE.ordinal()]));
                pcdf.setPcdfLen(Integer.parseInt(recordSplit[PCDF.COLUMNS.PCDF_LEN.ordinal()]));
                pcdf.setPcdfBit(Integer.parseInt(recordSplit[PCDF.COLUMNS.PCDF_BIT.ordinal()]));
                if(recordSplit[PCDF.COLUMNS.PCDF_PNAME.ordinal()].length() > 0) {
                    pcdf.setPcdfPname(recordSplit[PCDF.COLUMNS.PCDF_PNAME.ordinal()]);
                }
                pcdf.setPcdfValue(recordSplit[PCDF.COLUMNS.PCDF_VALUE.ordinal()]);
                if(recordSplit.length > PCDF.COLUMNS.PCDF_RADIX.ordinal() && recordSplit[PCDF.COLUMNS.PCDF_RADIX.ordinal()].length() > 0) {
                    pcdf.setPcdfRadix(_RADIX.valueOf(recordSplit[PCDF.COLUMNS.PCDF_RADIX.ordinal()]));
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

    public void createPCFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PCF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PCF> listPCF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PCF pcf = new PCF();
                String[] recordSplit = record.split("\t");

                pcf.setPcfName(recordSplit[PCF.COLUMNS.PCF_NAME.ordinal()]);
                if(recordSplit[PCF.COLUMNS.PCF_DESCR.ordinal()].length() > 0) {
                    pcf.setPcfDescr(recordSplit[PCF.COLUMNS.PCF_DESCR.ordinal()]);
                }
                if(recordSplit[PCF.COLUMNS.PCF_PID.ordinal()].length() > 0 && checkBigIntegerRange(BigInteger.valueOf(Long.valueOf(recordSplit[PCF.COLUMNS.PCF_PID.ordinal()])),BigInteger.ZERO,BigInteger.valueOf(4294967295L))) {
                    pcf.setPcfPid(BigInteger.valueOf(Long.valueOf(recordSplit[PCF.COLUMNS.PCF_PID.ordinal()])));
                }
                if(recordSplit[PCF.COLUMNS.PCF_UNIT.ordinal()].length() > 0) {
                    pcf.setPcfUnit(recordSplit[PCF.COLUMNS.PCF_UNIT.ordinal()]);
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_PTC.ordinal()]),1,13)) {
                    pcf.setPcfPtc(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_PTC.ordinal()]));
                }
                pcf.setPcfPfc(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_PFC.ordinal()]));
                if(recordSplit[PCF.COLUMNS.PCF_WIDTH.ordinal()].length() > 0) {
                    pcf.setPcfWidth(BigInteger.valueOf(Long.valueOf(recordSplit[PCF.COLUMNS.PCF_WIDTH.ordinal()])));
                }
                if(recordSplit[PCF.COLUMNS.PCF_VALID.ordinal()].length() > 0) {
                    pcf.setPcfValid(recordSplit[PCF.COLUMNS.PCF_VALID.ordinal()]);
                }
                if(recordSplit[PCF.COLUMNS.PCF_RELATED.ordinal()].length() > 0) {
                    pcf.setPcfRelated(recordSplit[PCF.COLUMNS.PCF_RELATED.ordinal()]);
                }
                pcf.setPcfCateg(PCF.PcfCateg.valueOf(recordSplit[PCF.COLUMNS.PCF_CATEG.ordinal()]));
                pcf.setPcfNatur(PCF.PcfNatur.valueOf(recordSplit[PCF.COLUMNS.PCF_NATUR.ordinal()]));
                if(recordSplit.length > PCF.COLUMNS.PCF_CURTX.ordinal() && recordSplit[PCF.COLUMNS.PCF_CURTX.ordinal()].length() > 0) {
                    pcf.setPcfCurtx(recordSplit[PCF.COLUMNS.PCF_CURTX.ordinal()]);
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_INTER.ordinal() && recordSplit[PCF.COLUMNS.PCF_INTER.ordinal()].length() > 0) {
                  pcf.setPcfInter(PCF.PcfInter.valueOf(recordSplit[PCF.COLUMNS.PCF_INTER.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_USCON.ordinal() && recordSplit[PCF.COLUMNS.PCF_USCON.ordinal()].length() > 0) {
                    pcf.setPcfUscon(_YN.valueOf(recordSplit[PCF.COLUMNS.PCF_USCON.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_DECIM.ordinal() && recordSplit[PCF.COLUMNS.PCF_DECIM.ordinal()].length() > 0) {
                    pcf.setPcfDecim(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_DECIM.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_PARVAL.ordinal() && recordSplit[PCF.COLUMNS.PCF_PARVAL.ordinal()].length() > 0) {
                   pcf.setPcfParval(recordSplit[PCF.COLUMNS.PCF_PARVAL.ordinal()]);
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_SUBSYS.ordinal() && recordSplit[PCF.COLUMNS.PCF_SUBSYS.ordinal()].length() > 0) {
                    pcf.setPcfSubsys(recordSplit[PCF.COLUMNS.PCF_SUBSYS.ordinal()]);
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_VALPAR.ordinal() && recordSplit[PCF.COLUMNS.PCF_VALPAR.ordinal()].length() > 0) {
                    pcf.setPcfValpar(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_VALPAR.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_SPTYPE.ordinal() && recordSplit[PCF.COLUMNS.PCF_SPTYPE.ordinal()].length() > 0) {
                    pcf.setPcfSptype(PCF.PcfSptype.valueOf(recordSplit[PCF.COLUMNS.PCF_SPTYPE.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_CORR.ordinal() && recordSplit[PCF.COLUMNS.PCF_CORR.ordinal()].length() > 0) {
                    pcf.setPcfCorr(_YN.valueOf(recordSplit[PCF.COLUMNS.PCF_CORR.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_OBTID.ordinal() && recordSplit[PCF.COLUMNS.PCF_OBTID.ordinal()].length() > 0) {
                    pcf.setPcfObtid(Integer.parseInt(recordSplit[PCF.COLUMNS.PCF_OBTID.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_DARC.ordinal() && recordSplit[PCF.COLUMNS.PCF_DARC.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[PCF.COLUMNS.PCF_DARC.ordinal()].charAt(0),PCF.arrayPCFDarc)) {
                   pcf.setPcfDarc(recordSplit[PCF.COLUMNS.PCF_DARC.ordinal()].charAt(0));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_ENDIAN.ordinal() && recordSplit[PCF.COLUMNS.PCF_ENDIAN.ordinal()].length() > 0) {
                   pcf.setPcfEndian(PCF.PcfEndian.valueOf(recordSplit[PCF.COLUMNS.PCF_ENDIAN.ordinal()]));
                }
                if(recordSplit.length > PCF.COLUMNS.PCF_DESCR2.ordinal() && recordSplit[PCF.COLUMNS.PCF_DESCR2.ordinal()].length() > 0) {
                   pcf.setPcfDescr2(recordSplit[PCF.COLUMNS.PCF_DESCR2.ordinal()]);
                }

                listPCF.add(pcf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPCF, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePCFRecords(listPCF,scostables,odbData);
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

                pcpc.setPcpcPname(recordSplit[PCPC.COLUMNS.PCPC_PNAME.ordinal()]);
                pcpc.setPcpcDesc(recordSplit[PCPC.COLUMNS.PCPC_DESC.ordinal()]);
                if(recordSplit.length > PCPC.COLUMNS.PCPC_CODE.ordinal() && recordSplit[PCPC.COLUMNS.PCPC_CODE.ordinal()].length() > 0) {
                    pcpc.setPcpcCode(PCPC.PcpcCode.valueOf(recordSplit[PCPC.COLUMNS.PCPC_CODE.ordinal()]));
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

    public void createPIDRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PID";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PID> listPID = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                PID pid = new PID();
                String[] recordSplit = record.split("\t");

                if(checkIntegerRange(Integer.parseInt(recordSplit[PID.COLUMNS.PID_TYPE.ordinal()]),0,255)) {
                    pid.setPidType(Integer.parseInt(recordSplit[PID.COLUMNS.PID_TYPE.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PID.COLUMNS.PID_STYPE.ordinal()]),0,255)) {
                    pid.setPidStype(Integer.parseInt(recordSplit[PID.COLUMNS.PID_STYPE.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PID.COLUMNS.PID_APID.ordinal()]),0,65535)) {
                    pid.setPidApid(Integer.parseInt(recordSplit[PID.COLUMNS.PID_APID.ordinal()]));
                }
                if(checkBigIntegerRange(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_PI1_VAL.ordinal()])),BigInteger.ZERO, BigInteger.valueOf(2147483647))) {
                    pid.setPidPi1Val(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_PI1_VAL.ordinal()])));
                }
                if(checkBigIntegerRange(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_PI2_VAL.ordinal()])),BigInteger.ZERO, BigInteger.valueOf(2147483647))) {
                    pid.setPidPi2Val(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_PI2_VAL.ordinal()])));
                }
                if(checkBigIntegerRange(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_SPID.ordinal()])),BigInteger.ONE, BigInteger.valueOf(4294967295L))) {
                    pid.setPidSpid(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_SPID.ordinal()])));
                }
                if(recordSplit[PID.COLUMNS.PID_DESCR.ordinal()].length() > 0) {
                    pid.setPidDescr(recordSplit[PID.COLUMNS.PID_DESCR.ordinal()]);
                }
                if(recordSplit[PID.COLUMNS.PID_UNIT.ordinal()].length() > 0) {
                    pid.setPidUnit(recordSplit[PID.COLUMNS.PID_UNIT.ordinal()]);
                }
                if(checkBigIntegerRange(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_TPSD.ordinal()])),BigInteger.ONE, BigInteger.valueOf(2147483647))) {
                    pid.setPidTpsd(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_TPSD.ordinal()])));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PID.COLUMNS.PID_DFHSIZE.ordinal()]),0,99)) {
                    pid.setPidDfhsize(Integer.parseInt(recordSplit[PID.COLUMNS.PID_DFHSIZE.ordinal()]));
                }
                if(recordSplit.length > PID.COLUMNS.PID_TIME.ordinal() && recordSplit[PID.COLUMNS.PID_TIME.ordinal()].length() > 0) {
                    pid.setPidTime(_YN.valueOf(recordSplit[PID.COLUMNS.PID_TIME.ordinal()]));
                }
                if(recordSplit.length > PID.COLUMNS.PID_INTER.ordinal() && recordSplit[PID.COLUMNS.PID_INTER.ordinal()].length() > 0) {
                    pid.setPidInter(BigInteger.valueOf(Long.valueOf(recordSplit[PID.COLUMNS.PID_INTER.ordinal()])));
                }
                if(recordSplit.length > PID.COLUMNS.PID_VALID.ordinal() && recordSplit[PID.COLUMNS.PID_VALID.ordinal()].length() > 0) {
                    pid.setPidValid(_YN.valueOf(recordSplit[PID.COLUMNS.PID_VALID.ordinal()]));
                }
                if(recordSplit.length > PID.COLUMNS.PID_CHECK.ordinal() && recordSplit[PID.COLUMNS.PID_CHECK.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[PID.COLUMNS.PID_CHECK.ordinal()]),0,1)) {
                    pid.setPidCheck(Integer.parseInt(recordSplit[PID.COLUMNS.PID_CHECK.ordinal()]));
                }
                if(recordSplit.length > PID.COLUMNS.PID_EVENT.ordinal() && recordSplit[PID.COLUMNS.PID_EVENT.ordinal()].length() > 0) {
                    pid.setPidEvent(PID.PidEvent.valueOf(recordSplit[PID.COLUMNS.PID_EVENT.ordinal()]));
                }
                if(recordSplit.length > PID.COLUMNS.PID_EVID.ordinal() && recordSplit[PID.COLUMNS.PID_EVENT.ordinal()].length() > 0) {
                    pid.setPidEvid(recordSplit[PID.COLUMNS.PID_EVENT.ordinal()]);
                }

                listPID.add(pid);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPID,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPID, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePIDRecords(listPID,scostables,odbData);
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

                if(checkIntegerRange(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_TYPE.ordinal()]), 0, 255)) {
                    pic.setPicType(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_TYPE.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_STYPE.ordinal()]), 0, 255)) {
                    pic.setPicStype(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_STYPE.ordinal()]));
                }
                pic.setPicPi1Off(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_PI1_OFF.ordinal()]));
                pic.setPicPi1Wid(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_PI1_WID.ordinal()]));
                pic.setPicPi2Off(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_PI2_OFF.ordinal()]));
                pic.setPicPi2Wid(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_PI2_WID.ordinal()]));
                if(recordSplit.length > PIC.COLUMNS.PIC_APID.ordinal() && recordSplit[PIC.COLUMNS.PIC_APID.ordinal()].length() >0) {
                   pic.setPicApid(Integer.parseInt(recordSplit[PIC.COLUMNS.PIC_APID.ordinal()]));
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

                plf.setPlfName(recordSplit[PLF.COLUMNS.PLF_NAME.ordinal()]);
                if(checkLowerBoundary(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_SPID.ordinal()]),0)) {
                    plf.setPlfSpid(BigInteger.valueOf(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_SPID.ordinal()])));
                }
                if(checkLowerBoundary(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_OFFBY.ordinal()]),0)) {
                    plf.setPlfOffby(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_OFFBY.ordinal()]));
                }
                if(checkIntegerRange(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_OFFBI.ordinal()]),0,7)) {
                    plf.setPlfOffbi(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_OFFBI.ordinal()]));
                }
                if(recordSplit.length > PLF.COLUMNS.PLF_NBOCC.ordinal() && recordSplit[PLF.COLUMNS.PLF_NBOCC.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[4]),1,9999)) {
                    plf.setPlfNbocc(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_NBOCC.ordinal()]));
                }
                if(recordSplit.length > PLF.COLUMNS.PLF_LGOCC.ordinal() && recordSplit[PLF.COLUMNS.PLF_LGOCC.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[5]),1,32767)) {
                    plf.setPlfLgocc(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_LGOCC.ordinal()]));
                }
                if(recordSplit.length > PLF.COLUMNS.PLF_TIME.ordinal() && recordSplit[PLF.COLUMNS.PLF_TIME.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[6]),-4080000, 4080000)) {
                    plf.setPlfTime(BigInteger.valueOf(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_TIME.ordinal()])));
                }
                if(recordSplit.length > PLF.COLUMNS.PLF_TDOCC.ordinal() && recordSplit[PLF.COLUMNS.PLF_TDOCC.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[7]),1,4080000)) {
                    plf.setPlfTdocc(BigInteger.valueOf(Integer.parseInt(recordSplit[PLF.COLUMNS.PLF_TDOCC.ordinal()])));
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

                prf.setPrfNumbr(recordSplit[PRF.COLUMNS.PRF_NUMBR.ordinal()]);
                if(recordSplit.length > PRF.COLUMNS.PRF_DESCR.ordinal() && recordSplit[PRF.COLUMNS.PRF_DESCR.ordinal()].length() > 0) {
                    prf.setPrfDescr(recordSplit[PRF.COLUMNS.PRF_DESCR.ordinal()]);
                }
                if(recordSplit.length > PRF.COLUMNS.PRF_INTER.ordinal() && recordSplit[PRF.COLUMNS.PRF_INTER.ordinal()].length() > 0) {
                    prf.setPrfInter(PRF.PrfInter.valueOf(recordSplit[PRF.COLUMNS.PRF_INTER.ordinal()]));
                }
                if(recordSplit.length > PRF.COLUMNS.PRF_DSPFMT.ordinal() && recordSplit[PRF.COLUMNS.PRF_DSPFMT.ordinal()].length() > 0) {
                    prf.setPrfDspfmt(PRF.PrfDspfmt.valueOf(recordSplit[PRF.COLUMNS.PRF_DSPFMT.ordinal()]));
                }
                if(recordSplit.length > PRF.COLUMNS.PRF_RADIX.ordinal() && recordSplit[PRF.COLUMNS.PRF_RADIX.ordinal()].length() > 0) {
                    prf.setPrfRadix(_RADIX.valueOf(recordSplit[PRF.COLUMNS.PRF_RADIX.ordinal()]));
                }
                if(recordSplit.length > PRF.COLUMNS.PRF_NRANGE.ordinal() && recordSplit[PRF.COLUMNS.PRF_NRANGE.ordinal()].length() > 0) {
                    prf.setPrfNrange(Integer.parseInt(recordSplit[PRF.COLUMNS.PRF_NRANGE.ordinal()]));
                }
                if(recordSplit.length > PRF.COLUMNS.PRF_UNIT.ordinal() && recordSplit[PRF.COLUMNS.PRF_UNIT.ordinal()].length() > 0) {
                    prf.setPrfUnit(recordSplit[PRF.COLUMNS.PRF_UNIT.ordinal()]);
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

                prv.setPrvNumbr(recordSplit[PRV.COLUMNS.PRV_NUMBR.ordinal()]);
                prv.setPrvMinval(recordSplit[PRV.COLUMNS.PRV_MINVAL.ordinal()]);
                if(recordSplit.length > PRV.COLUMNS.PRV_MAXVAL.ordinal() && recordSplit[PRV.COLUMNS.PRV_MAXVAL.ordinal()].length() > 0) {
                    prv.setPrvMaxval(recordSplit[PRV.COLUMNS.PRV_MAXVAL.ordinal()]);
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

    public void createPTVRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "PTV";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<PTV> listPTV = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
               PTV ptv = new PTV();
               String[] recordSplit = record.split("\t");

               ptv.setPtvCname(recordSplit[PTV.COLUMNS.PTV_CNAME.ordinal()]);
               ptv.setPtvParnam(recordSplit[PTV.COLUMNS.PTV_PARNAM.ordinal()]);
               if(recordSplit[PTV.COLUMNS.PTV_INTER.ordinal()].length() > 0) {
                   ptv.setPtvInter(PTV.PtvInter.valueOf(recordSplit[PTV.COLUMNS.PTV_INTER.ordinal()]));
               }
               ptv.setPtvVal(recordSplit[PTV.COLUMNS.PTV_VAL.ordinal()]);

               listPTV.add(ptv);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listPTV,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listPTV, odbFiles);

        if(scosRepository != null) {
            scosRepository.savePTVRecords(listPTV,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createSPCRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "SPC";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<SPC> listSPC = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                SPC spc = new SPC();
                String[] recordSplit = record.split("\t");

                spc.setSpcNumbe(recordSplit[SPC.COLUMNS.SPC_NUMBE.ordinal()]);
                if(checkLowerBoundary(Integer.parseInt(recordSplit[SPC.COLUMNS.SPC_POS.ordinal()]),0)) {
                    spc.setSpcPos(Integer.parseInt(recordSplit[SPC.COLUMNS.SPC_POS.ordinal()]));
                }
                spc.setSpcName(recordSplit[SPC.COLUMNS.SPC_NAME.ordinal()]);
                if(recordSplit[SPC.COLUMNS.SPC_UPDT.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[SPC.COLUMNS.SPC_UPDT.ordinal()].charAt(0), SPC.arraySPCUpdt)) {
                    spc.setSpcUpdt(recordSplit[SPC.COLUMNS.SPC_UPDT.ordinal()].charAt(0));
                }
                if(recordSplit[SPC.COLUMNS.SPC_MODE.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[SPC.COLUMNS.SPC_MODE.ordinal()].charAt(0), SPC.arraySPCMode)) {
                    spc.setSpcMode(recordSplit[SPC.COLUMNS.SPC_MODE.ordinal()].charAt(0));
                }
                if(recordSplit[SPC.COLUMNS.SPC_FORM.ordinal()].length() > 0) {
                    spc.setSpcForm(SPC.SpcForm.valueOf(recordSplit[SPC.COLUMNS.SPC_FORM.ordinal()]));
                }
                if(recordSplit[SPC.COLUMNS.SPC_BACK.ordinal()].length() > 0 && checkAlphaNumericRange(recordSplit[SPC.COLUMNS.SPC_BACK.ordinal()].charAt(0), SPC.arraySPCBack)) {
                    spc.setSpcBack(recordSplit[SPC.COLUMNS.SPC_BACK.ordinal()].charAt(0));
                }
                if(checkAlphaNumericRange(recordSplit[SPC.COLUMNS.SPC_FORE.ordinal()].charAt(0), SPC.arraySPCFore)) {
                    spc.setSpcFore(recordSplit[SPC.COLUMNS.SPC_FORE.ordinal()].charAt(0));
                }

                listSPC.add(spc);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listSPC,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listSPC, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveSPCRecords(listSPC,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createSPFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "SPF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<SPF> listSPF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                  SPF spf = new SPF();
                  String[] recordSplit = record.split("\t");

                  spf.setSpfNumbe(recordSplit[SPF.COLUMNS.SPF_NUMBE.ordinal()]);
                  if(recordSplit[SPF.COLUMNS.SPF_HEAD.ordinal()].length() > 0) {
                      spf.setSpfHead(recordSplit[SPF.COLUMNS.SPF_HEAD.ordinal()]);
                  }
                  if(checkIntegerRange(Integer.parseInt(recordSplit[SPF.COLUMNS.SPF_NPAR.ordinal()]),1,5)) {
                    spf.setSpfNpar(Integer.parseInt(recordSplit[SPF.COLUMNS.SPF_NPAR.ordinal()]));
                  }
                  if(recordSplit.length > SPF.COLUMNS.SPF_UPUN.ordinal() && recordSplit[SPF.COLUMNS.SPF_UPUN.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[3]),1,99)) {
                    spf.setSpfUpun(Integer.parseInt(recordSplit[SPF.COLUMNS.SPF_UPUN.ordinal()]));
                  }

                  listSPF.add(spf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listSPF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listSPF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveSPFRecords(listSPF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createTCPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "TCP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<TCP> listTCP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                TCP tcp = new TCP();
                String[] recordSplit = record.split("\t");

                tcp.setTcpId(recordSplit[TCP.COLUMNS.TCP_ID.ordinal()]);
                if(recordSplit.length > TCP.COLUMNS.TCP_DESC.ordinal() && recordSplit[TCP.COLUMNS.TCP_DESC.ordinal()].length() > 0) {
                    tcp.setTcpDesc(recordSplit[TCP.COLUMNS.TCP_DESC.ordinal()]);
                }

                listTCP.add(tcp);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listTCP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listTCP, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveTCPRecords(listTCP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createTPCFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "TPCF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<TPCF> listTPCF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                TPCF tpcf = new TPCF();
                String[] recordSplit = record.split("\t");

                tpcf.setTpcfSpid(BigInteger.valueOf(Integer.parseInt(recordSplit[TPCF.COLUMNS.TPCF_SPID.ordinal()])));
                if(recordSplit.length > TPCF.COLUMNS.TPCF_NAME.ordinal() && recordSplit[TPCF.COLUMNS.TPCF_NAME.ordinal()].length() > 0) {
                    tpcf.setTpcfName(recordSplit[TPCF.COLUMNS.TPCF_NAME.ordinal()]);
                }
                if(recordSplit.length > TPCF.COLUMNS.TPCF_SIZE.ordinal() && recordSplit[TPCF.COLUMNS.TPCF_SIZE.ordinal()].length() > 0) {
                    tpcf.setTpcfSize(BigInteger.valueOf(Integer.parseInt(recordSplit[TPCF.COLUMNS.TPCF_SIZE.ordinal()])));
                }

                listTPCF.add(tpcf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listTPCF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listTPCF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveTPCFRecords(listTPCF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createTXFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "TXF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<TXF> listTXF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                TXF txf = new TXF();
                String[] recordSplit = record.split("\t");

                txf.setTxfNumbr(recordSplit[TXF.COLUMNS.TXF_NUMBR.ordinal()]);
                if(recordSplit[TXF.COLUMNS.TXF_DESCR.ordinal()].length() > 0) {
                    txf.setTxfDescr(recordSplit[TXF.COLUMNS.TXF_DESCR.ordinal()]);
                }
                txf.setTxfRawfmt(_FMT.valueOf(recordSplit[TXF.COLUMNS.TXF_RAWFMT.ordinal()]));
                if(recordSplit.length > TXF.COLUMNS.TXF_NALIAS.ordinal() && recordSplit[TXF.COLUMNS.TXF_NALIAS.ordinal()].length() > 0) {
                    txf.setTxfNalias(Integer.parseInt(recordSplit[TXF.COLUMNS.TXF_NALIAS.ordinal()]));
                }

                listTXF.add(txf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listTXF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listTXF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveTXFRecords(listTXF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createTXPRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "TXP";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<TXP> listTXP = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                TXP txp = new TXP();
                String[] recordSplit = record.split("\t");

                txp.setTxpNumbr(recordSplit[TXP.COLUMNS.TXP_NUMBR.ordinal()]);
                txp.setTxpFrom(recordSplit[TXP.COLUMNS.TXP_FROM.ordinal()]);
                txp.setTxpTo(recordSplit[TXP.COLUMNS.TXP_TO.ordinal()]);
                txp.setTxpAltxt(recordSplit[TXP.COLUMNS.TXP_ALTXT.ordinal()]);

                listTXP.add(txp);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listTXP,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listTXP, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveTXPRecords(listTXP,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createVDFRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "VDF";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<VDF> listVDF = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                VDF vdf = new VDF();
                String[] recordSplit = record.split("\t");

                vdf.setVdfName(recordSplit[VDF.COLUMNS.VDF_NAME.ordinal()]);
                if(recordSplit.length > VDF.COLUMNS.VDF_COMMENT.ordinal() && recordSplit[VDF.COLUMNS.VDF_COMMENT.ordinal()].length() > 0) {
                    vdf.setVdfComment(recordSplit[VDF.COLUMNS.VDF_COMMENT.ordinal()]);
                }
                if(recordSplit.length > VDF.COLUMNS.VDF_DOMAINID.ordinal() && recordSplit[VDF.COLUMNS.VDF_DOMAINID.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[2]),0,65535)) {
                    vdf.setVdfDomainid(Integer.parseInt(recordSplit[VDF.COLUMNS.VDF_DOMAINID.ordinal()]));
                }
                if(recordSplit.length > VDF.COLUMNS.VDF_RELEASE.ordinal() && recordSplit[VDF.COLUMNS.VDF_RELEASE.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[3]),0,65535)) {
                    vdf.setVdfRelease(Integer.parseInt(recordSplit[VDF.COLUMNS.VDF_RELEASE.ordinal()]));
                }
                if(recordSplit.length > VDF.COLUMNS.VDF_ISSUE.ordinal() && recordSplit[VDF.COLUMNS.VDF_ISSUE.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[4]),0,65535)) {
                    vdf.setVdfIssue(Integer.parseInt(recordSplit[VDF.COLUMNS.VDF_ISSUE.ordinal()]));
                }

                listVDF.add(vdf);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listVDF,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listVDF, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveVDFRecords(listVDF,scostables,odbData);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createVPDRecord(File file, SCOSDB scosdb, ODBFiles odbFiles) throws IOException {
        String tableName = "VPD";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<VPD> listVPD = new ArrayList<>();
        //Start file
        String record = reader.readLine();
        while (record != null) {
            try {
                VPD vpd = new VPD();
                String[] recordSplit = record.split("\t");

                vpd.setVpdTpsd(BigInteger.valueOf(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_TPSD.ordinal()])));
                vpd.setVpdPos(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_POS.ordinal()]));
                vpd.setVpdName(recordSplit[VPD.COLUMNS.VPD_NAME.ordinal()]);
                if(recordSplit[VPD.COLUMNS.VPD_GRPSIZE.ordinal()].length() > 0) {
                    vpd.setVpdGrpsize(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_GRPSIZE.ordinal()]));
                }
                if(recordSplit[VPD.COLUMNS.VPD_FIXREP.ordinal()].length() > 0 && checkLowerBoundary(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_FIXREP.ordinal()]),-1)) {
                    vpd.setVpdFixrep(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_FIXREP.ordinal()]));
                }
                if(recordSplit[VPD.COLUMNS.VPD_CHOICE.ordinal()].length() > 0) {
                    vpd.setVpdChoice(_YN.valueOf(recordSplit[VPD.COLUMNS.VPD_CHOICE.ordinal()]));
                }
                if(recordSplit[VPD.COLUMNS.VPD_PIDREF.ordinal()].length() > 0) {
                    vpd.setVpdPidref(_YN.valueOf(recordSplit[VPD.COLUMNS.VPD_PIDREF.ordinal()]));
                }
                if(recordSplit[VPD.COLUMNS.VPD_DISDESC.ordinal()].length() > 0) {
                    vpd.setVpdDisdesc(recordSplit[VPD.COLUMNS.VPD_DISDESC.ordinal()]);
                }
                if(checkLowerBoundary(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_WIDTH.ordinal()]),0)) {
                    vpd.setVpdWidth(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_WIDTH.ordinal()]));
                }
                if(recordSplit.length > VPD.COLUMNS.VPD_JUSTIFY.ordinal() && recordSplit[VPD.COLUMNS.VPD_JUSTIFY.ordinal()].length() > 0) {
                    vpd.setVpdJustify(VPD.VpdJustify.valueOf(recordSplit[VPD.COLUMNS.VPD_JUSTIFY.ordinal()]));
                }
                if(recordSplit.length > VPD.COLUMNS.VPD_NEWLINE.ordinal() && recordSplit[VPD.COLUMNS.VPD_NEWLINE.ordinal()].length() > 0) {
                    vpd.setVpdNewline(_YN.valueOf(recordSplit[VPD.COLUMNS.VPD_NEWLINE.ordinal()]));
                }
                if(recordSplit.length > VPD.COLUMNS.VPD_DCHAR.ordinal() && recordSplit[VPD.COLUMNS.VPD_DCHAR.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_DCHAR.ordinal()]),0,2)) {
                    vpd.setVpdDchar(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_DCHAR.ordinal()]));
                }
                if(recordSplit.length > VPD.COLUMNS.VPD_FORM.ordinal() && recordSplit[VPD.COLUMNS.VPD_FORM.ordinal()].length() > 0) {
                    vpd.setVpdForm(VPD.VpdForm.valueOf(recordSplit[VPD.COLUMNS.VPD_FORM.ordinal()]));
                }
                if(recordSplit.length > VPD.COLUMNS.VPD_OFFSET.ordinal() && recordSplit[VPD.COLUMNS.VPD_OFFSET.ordinal()].length() > 0 && checkIntegerRange(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_OFFSET.ordinal()]),-32768,32767)) {
                    vpd.setVpdOffset(BigInteger.valueOf(Integer.parseInt(recordSplit[VPD.COLUMNS.VPD_OFFSET.ordinal()])));
                }

                listVPD.add(vpd);

            } catch (Exception e){
                e.printStackTrace();
            }

            record = reader.readLine();
        }

        reader.close();

        //INSERT SCOS TABLES
        SCOSTABLES scostables = createSCOSTABLESRecord(tableName,listVPD,scosdb);
        //INSERT ODB DATA
        ODBData odbData = createODBDATARecord(tableName,listVPD, odbFiles);

        if(scosRepository != null) {
            scosRepository.saveVPDRecords(listVPD,scostables,odbData);
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

    public boolean checkBigIntegerRange(BigInteger n, BigInteger min, BigInteger max) throws Exception {
        int minValue = n.compareTo(min);
        int maxValue = n.compareTo(max);
        if((minValue == 0) || (minValue == 1) || (maxValue == 0) || (maxValue == -1)) {
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

    public boolean checkAlphaNumericRange(char c, char[] array) throws Exception {
       for(char x: array) {
           if(x == c) {
               return true;
           }
       }
       throw  new Exception("Value is out of bounds");
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
