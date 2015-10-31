/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Octicoco
 */
public class principal {
    
    
    private String driver = "org.postgresql.Driver";  
    private Connection conexion1;
    private Connection conexion2;
    private String url1 = ""; 
    private String servidor1 = "";
    private String usuario1 = ""; 
    private String contrasena1 = "";
    private String bd1 = "";
    private String url2 = ""; 
    private String servidor2 = "";
    private String usuario2 = ""; 
    private String contrasena2 = "";
    private String bd2 = "";
    
    principal(principalGUI ventana){
        if (ventana.getJcBasesPorDefecto().isSelected()){
            //Se obtienen los datos por defecto
        } else {
            //Se utilizan los datos asignados.
            servidor1 = ventana.getJtHost1().getText();
            usuario1 = ventana.getJtUsuario1().getText();
            contrasena1 = ventana.getJtContrasena1().getText();
            bd1 = ventana.getJtBD1().getText();
            try {
                ConexionDB con = new ConexionDB();
                conexion1 = con.getConexion(servidor1, bd1, usuario1, contrasena1);
                listarTablas(conexion1, bd1);
                con.desconectar(conexion1);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    
    public void listarTablas(Connection conexion, String bd) throws IOException, SQLException{
        try {
            String[] tipo = {"TABLE"};
            DatabaseMetaData metaData = conexion.getMetaData();
            try (ResultSet resultSetTables = metaData.getTables(bd, "public", null, tipo)) {
                ResultSet rsP, rsC, rsF;
                while (resultSetTables.next()) {
                    System.out.println("-----------------------------------------------------");
                    System.out.println("\tcatalogo: " + resultSetTables.getString(1));
                    System.out.println("\tesquema: " + resultSetTables.getString(2));
                    System.out.println("\tnombre tabla: " + resultSetTables.getString(3));
                    System.out.println("\ttipo: " + resultSetTables.getString(4));
                    System.out.println("\tcomentarios: " + resultSetTables.getString(5));
                    System.out.println("_______________________________________________");
                    rsP = metaData.getPrimaryKeys(null, null, resultSetTables.getString(3));
                    if (rsP.next()) {
                        System.out.println("\t\tprimary key: " + rsP.getObject(4).toString());
                    }
                    rsF = metaData.getExportedKeys(null, null, resultSetTables.getString(3));
                    while (rsP.next()) {
                        System.out.println("\t\tforeign key: " + rsP.getObject(4).toString());
                    }
                    rsC = metaData.getColumns(null, null, resultSetTables.getString(3), null);
                    while (rsC.next()) {
                        System.out.println("\t\tColumna: " + rsC.getString(4) + "\t\t Tipo: " + rsC.getString(6));
                    }
                    rsC.close();
                    rsF.close();
                    rsP.close();

                    System.out.println("-----------------------------------------------------\n");
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }
    }
    
    public LinkedList<Trigger> listaTrigger(String nombreT) throws SQLException{
        Statement s = conexion1.createStatement();
        LinkedList<Trigger> lT = new LinkedList();
        LinkedList<String> nombTrigger = nombreTrigger(nombreT,s);
        LinkedList<String> condTrigger = new LinkedList();
        ResultSet rs;
        String disparador=null;
        for (int i = 0; i < nombTrigger.size(); i++) {
            rs = s.executeQuery ("SELECT event_manipulation,action_timing FROM information_schema.triggers where trigger_name ='"+nombTrigger.get(i)+"'");
            while (rs.next()){
                disparador = rs.getObject("action_timing").toString();
                condTrigger.add(rs.getObject("event_manipulation").toString());
            }
            lT.add(new Trigger(nombreT, condTrigger, disparador));
        }
        s.close();
        return lT;
    }
    
    public static LinkedList<String> nombreTrigger(String nom,Statement s) throws SQLException{
        LinkedList<String> nomb = new LinkedList();
        ResultSet rs = s.executeQuery("SELECT tgname FROM pg_trigger, pg_class WHERE tgrelid=pg_class.oid AND relname='"+nom+"' AND tgname not like '%RI_ConstraintTrigger_%';");
        while (rs.next()){
            nomb.add(rs.getObject("tgname").toString());
        }
        return nomb;
    }
    
}
