/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author oshada kavintha
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oshada kavintha
 */
public class MySQL {
    private static Connection connection;
    private static final String DATABASE = "hardware_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";
    
    private static Statement createConnection()throws Exception{
        
        if(connection == null){
             Class.forName("com.mysql.jdbc.Driver");
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/"+DATABASE,USERNAME,PASSWORD);
        }
       
        return connection.createStatement();
        
    }
    public static void iud(String query){
        try{
            createConnection().executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static ResultSet search(String query) throws Exception{
        return createConnection().executeQuery(query);
    }
    
    public static java.sql.Connection getConnection(){
        if(connection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/"+DATABASE,USERNAME,PASSWORD);
            } catch (Exception e) {
            }
             
        }
        return connection;
    }
}
