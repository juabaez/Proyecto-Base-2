/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectobd2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan baez
 */
public class ConexionDB {
      
    public static Connection getConexion(String servidor, String bd, String usuario, String contrasena) throws FileNotFoundException, IOException
    {
        Connection conexion=null;
        if (conexion==null){
            try{
                conexion = DriverManager.getConnection("jdbc:postgresql://"+servidor+"/"+bd, usuario,contrasena);
                System.out.println("Conexion ok");
            }catch(SQLException ex){
                conexion=null;
                ex.printStackTrace();
            }
        }
        return conexion;
    }
    
    public static void desconectar(Connection con) throws SQLException{
        con.close();
    }
}
