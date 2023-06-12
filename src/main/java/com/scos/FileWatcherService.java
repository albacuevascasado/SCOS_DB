package com.scos;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
public class FileWatcherService implements Runnable {

    //pool of threads to run task asynchronously
    protected ExecutorService executor;

    /** can't be loaded as a <constructor-arg></constructor-arg> because the constructor needs it */
    //private String pathToDir = "C:\\Users\\acasado\\Desktop\\TABLE_PRUEBA";
    /** <property></property> */
    private String pathToDir;

    public FileWatcherService() {
    /** <constructor-arg></constructor-arg> NOT REQUIRED @Getter @Setter */
    }

    private void watchSCOSDir() throws IOException, InterruptedException {
        this.executor = Executors.newSingleThreadExecutor();

        System.out.println("Path To Directory: " + pathToDir);
        //watchservice instance
        WatchService watchService = FileSystems.getDefault().newWatchService();
        //register a watch service in this path to be monitored
        Path path = Paths.get(pathToDir);
        //Register the path
//    WatchKey watchKey = Paths.get("pathsToDir").register((java.nio.file.WatchService) watchService, StandardWatchEventKinds.ENTRY_CREATE);
        //WatchKey represents the registration of a directory with watch service
        path.register(watchService, ENTRY_CREATE);
        System.out.println("Iniciando observacion para " + path.getFileName());
        //StandardWatchEventKinds -> kinds of events to watch for on the registered directory

        WatchKey key;
        while ((key = watchService.take()) != null ) {
            List<WatchEvent<?>> eventList = key.pollEvents();
            for (WatchEvent<?> event: eventList) {
                //event type in this case is unique (ENTRY_CREATE)
                WatchEvent.Kind<?> eventType = event.kind();
                System.out.println("Event kind: " + eventType.name()
                        + ". File Name: " + event.context());

                //lost or discarded events
                if(eventType == OVERFLOW) {
                    continue;
                } else if (eventType == ENTRY_CREATE) {
                    //TODO using file name
                    System.out.println("New file received: " + event.context() + ".");

                    executor.execute(()->{
                        try {
                            jobToBeExecuted(pathToDir+"/"+event.context());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                //reset watchkey to capture more events
                boolean valid = key.reset();
                if(!valid) {
                    break;
                }
            }

        }

    }

    private void jobToBeExecuted(String filePath) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        ReadFileUtility readFile = new ReadFileUtility(applicationContext);
        readFile.listFilesFromFolder(filePath);
    }

    @Override
    public void run() {
        try {
            watchSCOSDir();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}


