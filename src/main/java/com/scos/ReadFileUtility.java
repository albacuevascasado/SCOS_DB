package com.scos;

import com.scos.repositories.SCOSRepository;
import com.scos.services.SCOSService;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReadFileUtility {

    ApplicationContext applicationContext;

    public ReadFileUtility(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("Application Context: " + applicationContext );
    }

    /** String filePrueba if we inject direcltly a folder without the need of unzip */
    /** String filePath if we use the watcher */
    public void listFilesFromFolder(String filePath) throws IOException {

        UnzipUtility unzipFile = new UnzipUtility();
        //target folder
        String folderDest = unzipFile.unZip(filePath , "C:\\Users\\acasado\\Desktop\\PRUEBA");

        File directoryPath = new File (folderDest);
        //INPUT listFilesFromFolder(String filePrueba)
        //File directoryPath = new File (filePrueba);

        //List all files/folders
        File fileList[] = directoryPath.listFiles();
        for(File fileString: fileList) {
            System.out.println("Files List: " + fileString);
        }

        for(File file:fileList) {
            if (file.isDirectory()) {
                //files INSIDE directory
                File filesInDirectory[] = file.listFiles();
                for (File fileDir : filesInDirectory) {
                    //get file's name == table in DB
                    String[] fileDirName = fileDir.getName().split(".dat"); //IMPORTANTE per il nome della tabella
                    //System.out.println("File Name: " + fileDirName[0].toUpperCase());
                    System.out.println("Files in folder: " + fileDir);

                    readFilesFromFolder(fileDir, fileDirName[0]);
                }
            } else {
                /** files OUTSIDE directory */
                //get file's name == table in DB
                String[] fileName = file.getName().split(".dat");
                System.out.println("Files OUTSIDE directory");
                //System.out.println("File Name: " + fileName[0].toUpperCase());
                System.out.println("File: " + file);

                readFilesFromFolder(file, fileName[0]);
            }
        }
    }

    public void readFilesFromFolder(File file, String fileName) throws IOException {

        SCOSRepository scosRepository = this.applicationContext.getBean(SCOSRepository.class);
        scosRepository.truncateTableSCOS(fileName.toUpperCase());

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> listLines = new ArrayList<>();
            //Start file
            String record = reader.readLine();
            while(record != null) {
                //save by file
                listLines.add(record);
                //System.out.println("Record: " + record);
                    //save line by line
                    //String[] recordSplit = record.trim().split("\t");
                    //callRepository(fileName.toUpperCase(), recordSplit); //chiama il servicio con il nome della tabella
                record = reader.readLine();
            }

            reader.close();

            //save records in one transaction
            callRepository(fileName.toUpperCase(), listLines);

        } catch (IOException | ClassNotFoundException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException error) {
            //TODO in this case
            System.out.println(fileName.toUpperCase() + " does not exist as @Entity in DB.");
        }
    }

    public  void callRepository(String tableName, List<String> listLinesFile) throws ClassNotFoundException, NoSuchMethodException,
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

        Method method = scosService.getClass().getDeclaredMethod(methodName, List.class);
        System.out.println("Method: " + method);
        //parameters = object[]
        Object[] args = new Object[] {listLinesFile};
        method.invoke(scosService, args);

    }
}
