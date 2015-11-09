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
import javax.swing.table.DefaultTableModel;

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
    private informacionBD infoBD1;
    private informacionBD infoBD2;
    
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
                //listarTablas(conexion1, bd1);
                mostrarDatosTablas(getTablas(conexion1, bd1));
                con.desconectar(conexion1);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    

    
    /**
     * Metodo que retorna una Lista de tablas con, de donde se puden obtener sus atributos, claves foraneas, primarias y unicas
     * @param conexion conexion a la base de datos
     * @param bd nombre de la base de datos de donde se obtienen los datos
     * @return una Lista de tablas con, de donde se puden obtener sus atributos, claves foraneas, primarias y unicas
     * @throws IOException
     * @throws SQLException 
     */
    public LinkedList<Tabla> getTablas(Connection conexion, String bd) throws IOException, SQLException{
        LinkedList<Tabla> tablas = new LinkedList<Tabla>();
        try {
            String[] tipo = {"TABLE"};
            DatabaseMetaData metaData = conexion.getMetaData();            
            try (ResultSet resultSetTables = metaData.getTables(bd, "public", null, tipo)) {
                ResultSet rsP, rsC, rsF, rsU;
                String[] titulos = {"enTabla", "enAtributo", "aTabla", "aAtributo"};
                Object[][] datos = {};
                DefaultTableModel clavesForaneas = new DefaultTableModel(datos, titulos);
                //Se obtienen y guardan las tablas
                while (resultSetTables.next()) {
                    Tabla tablaActual = new Tabla(resultSetTables.getString(3));
                    //System.out.println("\tnombre tabla: " + resultSetTables.getString(3));
                    rsC = metaData.getColumns(null, null, resultSetTables.getString(3), null);
                    //Se obtienen los atributos y tipos
                    while (rsC.next()) {
                        Atributo atributo = new Atributo(rsC.getString(4), rsC.getString(6));
                        //System.out.println("\t\tColumna: " + rsC.getString(4) + "\t\t Tipo: " + rsC.getString(6));
                        tablaActual.agregarAtributo(atributo);
                    }
                    //Se obtienen y guardan las claves primarias
                    rsP = metaData.getPrimaryKeys(null, null, resultSetTables.getString(3));
                    while (rsP.next()) {
                        //System.out.println("\t\tprimary key: " + rsP.getObject(4).toString());
                        for (int i = 0; i < tablaActual.getAtributos().size(); i++) {
                            if (tablaActual.getAtributos().get(i).getNombre().equals(rsP.getObject(4).toString())){
                                tablaActual.getAtributos().get(i).setPk(true);
                            }
                        }
                    }
                    //Obtencion de las claves foraneas, se guardan en un DefaultTableModel
                    rsF = metaData.getExportedKeys(null, null, resultSetTables.getString(3));
                    while (rsF.next()) {
                        rsC = metaData.getColumns(null, null, resultSetTables.getString(3), null);
                        String nombreColumna = "";
                        for (int i = 0; i < rsF.getShort("KEY_SEQ") && nombreColumna.equals(""); i++) {
                            rsC.next();
                            if (i+1==rsF.getShort("KEY_SEQ")){
                                nombreColumna = rsC.getString(4);
                            }
                        }
                        //System.out.println(tablaActual.getNombre()+"\tforeign key: " + rsF.getString("FKTABLE_NAME") + " " +rsF.getString("FKCOLUMN_NAME")+ " "+nombreColumna);
                        //                    "enTabla",            "enAtributo", "aTabla",                    "aAtributo"
                        String[] fila = {tablaActual.getNombre(), nombreColumna, rsF.getString("FKTABLE_NAME"), rsF.getString("FKCOLUMN_NAME")};
                        clavesForaneas.addRow(fila);
                    }
                    //Se obtienen las claves unicas     
                    rsU = metaData.getIndexInfo(null, null, tablaActual.getNombre(), true, false);
                    while (rsU.next()) {
                        String atribNombre = rsU.getString(9);
                        rsP = metaData.getPrimaryKeys(null, null, tablaActual.getNombre());
                        boolean encontrado = false;
                        while (rsP.next() && !encontrado) {
                            if (atribNombre.equals(rsP.getObject(4).toString())){
                                encontrado = true;
                                System.out.println("ENCONTRADO Bus "+atribNombre +"--"+rsP.getObject(4).toString());
                            }
                        }
                        System.out.println("Encontrado = "+encontrado+" nombre "+atribNombre);
                        if (encontrado == true){
                            for (int i = 0; i < tablaActual.getAtributos().size(); i++) {
                                if (tablaActual.getAtributos().get(i).getNombre().equals(atribNombre)){
                                    tablaActual.getAtributos().get(i).setUnq(true);
                                }
                            }
                        }
                        /*if (!rsU.getBoolean(4)){                            
                            System.out.println("-Tabla "+resultSetTables.getString(3)+" Unique: "+rsU.getString(9)+ "-" +rsU.getString("INDEX_NAME"));
                        }*/
                        
                    }
                    tablas.add(tablaActual);
                    rsC.close();
                    rsF.close();
                    rsP.close();
                }
                
//                    for (int i = 0; i < clavesForaneas.getRowCount(); i++) {
//                        for (int j = 0; j < clavesForaneas.getColumnCount(); j++) {
//                            System.out.print(clavesForaneas.getValueAt(i, j)+"-");
//                        }
//                        System.out.println("");
//                    }
                
                for (int i = 0; i < clavesForaneas.getRowCount(); i++) {
                    String nombreTabla = (String)clavesForaneas.getValueAt(i, 2);
                    for (int j = 0; j < tablas.size(); j++) {
                        if (tablas.get(j).getNombre().equals(nombreTabla)){
                            String nombreColumna = "";
                            for (int k = 0; k < tablas.get(j).getAtributos().size(); k++) {
                                nombreColumna = tablas.get(j).getAtributos().get(k).getNombre();
                                if (nombreColumna.equals((String)clavesForaneas.getValueAt(i, 3))){
                                    tablas.get(j).getAtributos().get(k).setRefATabla((String)clavesForaneas.getValueAt(i, 0));
                                    tablas.get(j).getAtributos().get(k).setRefAAtributo((String)clavesForaneas.getValueAt(i, 1));
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }
        return tablas;
    }
    
    public void mostrarDatosTablas(LinkedList<Tabla> tablas){
        for (int i = 0; i < tablas.size(); i++) {
            Tabla actual = tablas.get(i);
            System.out.println("Tabla: "+actual.getNombre());
            for (int j = 0; j < actual.getAtributos().size(); j++) {
                Atributo atribActual = actual.getAtributos().get(j);
                System.out.println("\t\t"+atribActual.getNombre()+" tipo: "+atribActual.getTipo()+" PK: "+atribActual.getPk()+" UK: "+atribActual.getPk()+" FK: "+atribActual.getRefATabla()+" ("+atribActual.getRefAAtributo()+")");
            }
        }
    }
    
    /*public void listarTablas(Connection conexion, String bd) throws IOException, SQLException{
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
    }*/
    
    public LinkedList<Trigger> listaTrigger(String nombreT) throws SQLException{
        Statement s = conexion1.createStatement();
        LinkedList<Trigger> lT = new LinkedList();
        LinkedList<String> nombTrigger = nombreTrigger(nombreT,s);
        LinkedList<String> condTrigger = new LinkedList();
        ResultSet rs;
        String disparador=null;
        for (int i = 0; i < nombTrigger.size(); i++) {
            rs = s.executeQuery ("SELECT event_manipulation,action_timing FROM information_schema.triggers where trigger_name ='"+nombTrigger.get(i)+"' order by event_manipulation");
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
        ResultSet rs = s.executeQuery("SELECT tgname FROM pg_trigger, pg_class WHERE tgrelid=pg_class.oid AND relname='"+nom+"' AND tgname not like '%RI_ConstraintTrigger_%' order by tgname;");
        while (rs.next()){
            nomb.add(rs.getObject("tgname").toString());
        }
        return nomb;
    }
    
    public LinkedList<Procedure> getprocedures(Connection conexion, String bd) throws SQLException{
        LinkedList<Procedure> result=new LinkedList<Procedure>();        
        DatabaseMetaData metaData = conexion.getMetaData();
        try (ResultSet resultSetProcedure = metaData.getProcedures(bd, "public", null)){
            ResultSet resultSetParameter;
             while (resultSetProcedure.next()) {
                 String retorno=resultSetProcedure.getString(8);
                 if(retorno.equals("procedureResultUnknown")){
                     retorno="No se puede determinar si el valor de retorno será devuelto";
                 }else if(retorno.equals("procedureNoResult")){
                     retorno= "No devuelve un valor de retorno";
                 }else if(retorno.equals("procedureReturnsResult")){
                     retorno="Devuelve un valor de retorno";
                 }
                 Procedure proc=new Procedure(resultSetProcedure.getNString(3),retorno);
                 /*
                 **
                 ** Falta meter el codigo para los parametros... resultSetParameter
                 **
                 */
             }
             
             
             resultSetProcedure.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }
        
        
        return result;
    }
    
}
