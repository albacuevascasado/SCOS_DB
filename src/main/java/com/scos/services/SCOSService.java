package com.scos.services;

import com.scos.data_model.*;
import com.scos.repositories.SCOSRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/** BUSINESS LOGIC */
@NoArgsConstructor //REQUIRED TO BE ABLE TO INVOKE METHOD
@Setter
@Getter
public class SCOSService {

    @Autowired
    SCOSRepository scosRepository;

    public void createCVSRecord(List<String> listLinesFile) {
        List<CVS> listCVS = new ArrayList<>();
        for(String line: listLinesFile) {
            //when there are errors in the format -> EMPTY LINE
            if(line.length() > 0) {
                //create ALWAYS new object to avoid changing the info INSIDE the box
                CVS cvs = new CVS();
                System.out.println("Line createCVSRecord: " + line);
                String[] recordSplit = line.trim().split("\t");

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

                System.out.println("CVS createCVSRecord: " + cvs.getCvsId());
                listCVS.add(cvs);
            }
        }

        if(scosRepository != null) {
            System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCVSRecords(listCVS);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCAFRecord(List<String> listLinesFile) {
        List<CAF> listCAF = new ArrayList<>();
        for(String line: listLinesFile) {
            if(line.length() > 0) {
                //create ALWAYS new object to avoid changing the info INSIDE the box
                CAF caf = new CAF();
                System.out.println("Line createCAFRecord: " + line);
                String[] recordSplit = line.trim().split("\t");

                caf.setCafNumbr(recordSplit[0]);
                caf.setCafDescr(recordSplit[1]);
                caf.setCafEngfmt(recordSplit[2].charAt(0));
                caf.setCafRawfmt(recordSplit[3].charAt(0));
                caf.setCafRadix(recordSplit[4].charAt(0));
                caf.setCafUnit(recordSplit[5]);
                caf.setCafNcurve(BigInteger.valueOf(Integer.parseInt(recordSplit[6])));
                caf.setCafInter(recordSplit[7].charAt(0));

                System.out.println("CAF createCAFRecord: " + caf.getCafNumbr());
                listCAF.add(caf);
            }
        }

        if(scosRepository != null) {
            System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCAFRecords(listCAF);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCAPRecord(List<String> listLinesFile) {
        List<CAP> listCAP = new ArrayList<>();
        for(String line: listLinesFile) {
            if(line.length() > 0) {
                CAP cap = new CAP();
                System.out.println("Line createCAPRecord: " + line);
                String[] recordSplit = line.trim().split("\t");

                cap.setCaPNumbr(recordSplit[0]);
                cap.setCapXvals(recordSplit[1]);
                cap.setCapYvals(recordSplit[2]);

                System.out.println("CAP createCAPRecord: " + cap.getCaPNumbr());
                listCAP.add(cap);
            }
        }

        if(scosRepository != null) {
            System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCAPRecords(listCAP);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

    public void createCCARecord(List<String> listLinesFile) {
        List<CCA> listCCA = new ArrayList<>();
        for(String line: listLinesFile) {
            if(line.length() > 0) {
                CCA cca = new CCA();
                System.out.println("Line createCCARecord: " + line);
                String[] recordSplit = line.trim().split("\t");

                cca.setCcaNumbr(recordSplit[0]);
                cca.setCcaDescr(recordSplit[1]);
                cca.setCcaEngfmt(recordSplit[2].charAt(0));
                cca.setCcaRawfmt(recordSplit[3].charAt(0));
                cca.setCcaRadix(recordSplit[4].charAt(0));
                cca.setCcaUnit(recordSplit[5]);
                cca.setCcaNcurve(BigInteger.valueOf(Integer.parseInt(recordSplit[6])));

                System.out.println("CCA createCCARecord: " + cca.getCcaNumbr());
                listCCA.add(cca);
            }
        }

        if(scosRepository != null) {
            System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveCCARecords(listCCA);
        } else {
            System.out.println("scosRepository has not been injected");
        }
    }

//    public void createCCFRecord(List<String> listLinesFile) {
//        List<CCF> listCCF = new ArrayList<>();
//        for(String line: listLinesFile) {
//            if(line.length() > 0) {
//                CCF ccf = new CCF();
//                System.out.println("Line createCCFRecord: " + line);
//                String[] recordSplit = line.trim().split("\t");
//
//                ccf.setCcfCname(recordSplit[0]);
//                ccf.setCcfDescr(recordSplit[1]);
//                ccf.setCcfDescr2(recordSplit[2]);
//                ccf.setCcfCtype(recordSplit[3]);
//                ccf.setCcfCritical(recordSplit[4].charAt(0));
//                ccf.setCcfPktid(recordSplit[5]);
//                ccf.setCcfType(Integer.parseInt(recordSplit[6]));
//               // ccf.set
//
//                System.out.println("CCA createCCARecord: " + cca.getCcaNumbr());
//                listCCF.add(ccf);
//            }
//        }
//
//        if(scosRepository != null) {
//            System.out.println("scosRepository: " + scosRepository);
//            scosRepository.saveCCFRecords(listCCF);
//        } else {
//            System.out.println("scosRepository has not been injected");
//        }
//    }

    public void createTESTRecord(List<String> listLinesFile) {
        List<TEST> listTest = new ArrayList<>();
        for(String line: listLinesFile) {
            TEST test = new TEST();
            System.out.println("Line createTESTRecord: " + line);
            String[] recordSplit = line.trim().split("\t");

            test.setId(Integer.parseInt(recordSplit[0]));
            test.setFirstName(recordSplit[1]);
            test.setLastName(recordSplit[2]);

            listTest.add(test);
        }

        if(scosRepository != null) {
            System.out.println("scosRepository: " + scosRepository);
            scosRepository.saveTESTRecords(listTest);
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
