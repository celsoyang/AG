/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class Conexao {

    private static Connection conention;
    private static Statement statment;
    private static String user;
    private static String password;
    private static String driver;
    private static String str_connection;

    public static void startConnection(String url, String sgbd, String banco, Integer porta, String user, String password) {

        if (sgbd.equalsIgnoreCase("PostgreSql")) {
            connectPostgre(url, banco, porta, user, password);
        } else if (sgbd.equalsIgnoreCase("SqlServer")) {
            connectSqlServer(banco, porta, user, password);
        }
    }
    
    private static void connectPostgre(String url, String banco, Integer porta, String user, String password) {
        try {
            Class.forName(StringsUtils.DRIVER_POSTGRE);
            setConention(DriverManager.getConnection(url + "://localhost:" + porta + "/" + banco, user, password));
            
            JOptionPane.showMessageDialog(null, "Connectado");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void connectSqlServer(String banco, Integer porta, String user, String password) {
    }

    public static Connection getConention() {
        return conention;
    }

    public static void setConention(Connection conention) {
        Conexao.conention = conention;
    }

    public static Statement getStatment() {
        return statment;
    }

    public static void setStatment(Statement statment) {
        Conexao.statment = statment;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Conexao.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Conexao.password = password;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        Conexao.driver = driver;
    }

    public static String getStr_connection() {
        return str_connection;
    }

    public static void setStr_connection(String str_connection) {
        Conexao.str_connection = str_connection;
    }
    
    
    
    

}
