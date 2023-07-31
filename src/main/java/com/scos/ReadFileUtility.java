package com.scos;

import com.scos.data_model.mps_db.ODBFiles;
import com.scos.data_model.scos_db.SCOSDB;
import com.scos.repositories.SCOSRepository;
//import com.scos.services.MPSService;
import com.scos.services.SCOSService;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadFileUtility {

    ApplicationContext applicationContext;

    public ReadFileUtility(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("Application Context from Read File: " + applicationContext);
    }

    /** String filePrueba if we inject direcltly a folder without the need of unzip */
    /** String filePath if we use the watcher */
    public void startProcessZipFolder(String filePath, String fileName) throws IOException {
        System.out.println("File Path: " + filePath);

        //TODO QUERY BEFORE UNZIP SCOS_DB | ODB_FILES
        SCOSService scosService = this.applicationContext.getBean(SCOSService.class);
        SCOSDB scosdb = scosService.createSCOSDBRecord(filePath);
        ODBFiles odbFiles = scosService.createODBFILESRecord(filePath, fileName);

//        MPSService mpsService = this.applicationContext.getBean(MPSService.class);
//        mpsService.createODBFilesRecord(filePath);

        UnzipUtility unzipFile = new UnzipUtility();
        //target folder
        String folderDest = unzipFile.unZip(filePath , "C:\\Users\\acasado\\Desktop\\PRUEBA");

        File directoryPath = new File (folderDest);
        //INPUT listFilesFromFolder(String filePrueba)
        //File directoryPath = new File (filePrueba);

        //TODO RECURSIVELY METHOD TO LIST FILES FROM A FOLDER
        listFilesFromZip(directoryPath, scosdb, odbFiles);

        //TODO delete unzip folder
        if (unzipFile.deleteUnZipFolder(directoryPath)) {
            System.out.println("Target Folder Empty: " + directoryPath.getPath());
            //TODO update ODBFiles Status
            scosService.updateODBFILESStatus(odbFiles);
        } else {
            System.out.println("Target Folder Has Not Been Deleted: " + directoryPath.getPath() );
        }

    }

    public void listFilesFromZip(File unzipDirectory, SCOSDB scosdb, ODBFiles odbFiles) {
        File fileList[] = unzipDirectory.listFiles();
        for (File file : fileList) {

            if(file.isDirectory()) {
                listFilesFromZip(file, scosdb, odbFiles);
            } else {
                //TODO CHECK FILE EXTENSION
                System.out.println("File Name in List: " + file);

                //get file's name == table in DB
                //TODO CHECK TYPE OF FILE .DAT
                String[] fileExtension = file.getName().split("\\.");
                //System.out.println("File Extension: " + fileExtension[1]);
                String[] fileName = file.getName().split(".dat"); //IMPORTANTE per il nome della tabella

                try {
                    SCOSRepository scosRepository = this.applicationContext.getBean(SCOSRepository.class);
                    boolean tableExistsInDB = scosRepository.getSCOSEntities(fileName[0].toUpperCase());
                    System.out.println("Table exists in DB? " + tableExistsInDB);

                    if (tableExistsInDB) {
                        callRepository(fileName[0].toUpperCase(), file, scosdb, odbFiles);
                    }
                } catch (ClassNotFoundException | IllegalAccessException |
                        InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                catch (NoSuchMethodException error) {
                    //TODO in this case
                    System.out.println(fileName[0].toUpperCase() + " does not exist as @Entity in DB.");
                }
            }
        }
    }

    public  void callRepository(String tableName, File file, SCOSDB scosdb, ODBFiles odbFiles) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, InstantiationException {

        /** call repository for this table SWITCH */
//        SCOSService scosService = this.applicationContext.getBean(SCOSService.class);
//        scosService.serviceSearchTable(tableName, record);


        /** JAVA REFLECT */
        //? = any / T = specific type
        //Class<?> serviceClass = Class.forName("com.scos.services.SCOSService");
        //System.out.println("Service Class: " + serviceClass);
        //Object of Service Class
        /** Has to be initialized in Spring framework to inject dependencies */
        SCOSService scosService = this.applicationContext.getBean(SCOSService.class);

        //Object scosService = serviceClass.newInstance(); //== SCOSService scosService = new SCOSService(); EMPTY CONSTRUCTOR
        String methodName = "create"+tableName+"Record";

        Method method = scosService.getClass().getDeclaredMethod(methodName, File.class, SCOSDB.class, ODBFiles.class);
        System.out.println("Method: " + method);
        //parameters = object[]
        Object[] args = new Object[] {file, scosdb, odbFiles};
        method.invoke(scosService, args);

    }

}
