package nl.bastiaansierd.datalogger;

import nl.bastiaansierd.datalogger.data.DataLog;
import nl.bastiaansierd.datalogger.data.FileLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLogger {
    private static SimpleLogger instance = null;
    private Lock environmentLock;
    private String logDirectory;


    private SimpleLogger(){
        logDirectory = System.getProperty("user.dir") + "\\log";
        environmentLock = new ReentrantLock();
    }

    public static SimpleLogger getLog(){
        /* singelton initialisatie*/
        if(instance == null){
            instance = new SimpleLogger();
        }
        return instance;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private void setLogDir(String path){
        logDirectory = path;
    }

    private void writer(String logFile, String whoDunnit, String logText){
        if(!getFileExtension(logFile).equals("txt")){
            System.out.println(logFile + " could not be written, log files should be '.txt' files.");
        } else {
            DataLog fileLog = new FileLog(logDirectory, logFile);

            Date date = new Date();
            String dateString = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(date);
            String message = dateString + " :: " + whoDunnit + " :: " + logText;

            environmentLock.lock();
            try{
                fileLog.saveLog(message);
                System.out.println("Message written to " + logDirectory + "\\" + logFile + ": " + message);
            }
            catch (Exception e){
                System.out.println("Could not write to " + logDirectory + "\\" + logFile + ".");
            }
            finally {
                environmentLock.unlock();
            }
        }
    }

    public static void setLogDirectory(String path){
        SimpleLogger.getLog().setLogDir(path);
    }

    public static void write(String logfile, String whoDunnit, String logText){
        SimpleLogger.getLog().writer(logfile, whoDunnit, logText);
    }

}

