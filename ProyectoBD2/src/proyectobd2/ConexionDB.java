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
      
    public static Connection GetConnection() throws FileNotFoundException, IOException
    {
        Connection conexion=null;
        if (conexion==null){
            try{
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/practica1","root","root");
            }catch(SQLException ex){
                conexion=null;
            }
        }
        return conexion;
    }
    
    public static void Disconnect(Connection con) throws SQLException{
        con.close();
    }
}
