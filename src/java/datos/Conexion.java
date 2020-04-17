/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Larrocca
 */
public class Conexion {

    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/control_clientes?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection obtenerConexion() {
        try {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

            con = DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error al abrir la conexion: " + ex.getClass().getName() + ex.getMessage());
        }
        return con;
    }

    public static void close(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
