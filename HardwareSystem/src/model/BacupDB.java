/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import gui.Home;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author oshada kavintha
 */
public class BacupDB {
    
    public static void backupDBToSql() {
        
    try {

//        File jarFile = new File(BacupDB.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
//        String jarDir = jarFile.getParentFile().getPath();

        String dbName = "hardware_system";
        String dbUser = "root";
        String dbPass = "123";

        String folderPath = "C:"+ "\\backup";

        File f1 = new File(folderPath);
        f1.mkdir();

           String savePath = "\"" + "C:" + "\\backup\\" + "hardware_system_backup.sql\"";
         
        String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();

        if (processComplete == 0) {
            System.out.println("Backup Complete");
        } else {
            System.out.println("Backup Failure");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
    
}
