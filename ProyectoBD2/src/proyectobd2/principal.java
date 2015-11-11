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
            servidor2 = ventana.getJtHost2().getText();
            usuario2 = ventana.getJtUsuario2().getText();
            contrasena2 = ventana.getJtContrasena2().getText();
            bd2 = ventana.getJtBD2().getText();
            try {
                ConexionDB con = new ConexionDB();
                conexion1 = con.getConexion(servidor1, bd1, usuario1, contrasena1);
                infoBD1 = new informacionBD(bd1, getTablas(conexion1, bd1), null);
                //mostrarDatosTablas(getTablas(conexion1, bd1));
                con.desconectar(conexion1);
                
                ConexionDB con2 = new ConexionDB();
                conexion2 = con2.getConexion(servidor2, bd2, usuario2, contrasena2);
                infoBD2 = new informacionBD(bd2, getTablas(conexion2, bd2), null);
                //mostrarDatosTablas(getTablas(conexion2, bd2));
                con2.desconectar(conexion1);
                
                System.out.println(infoBD1.comparacion(infoBD1, infoBD2));
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
                ResultSet rsP, rsC, rsF, rsU, rsI;
                String[] titulos = {"enTabla", "enAtributo", "aTabla", "aAtributo"};
                //Object[][] datos = {};
                //DefaultTableModel clavesForaneas = new DefaultTableModel(datos, titulos);
                
                //Se obtienen y guardan las tablas
                while (resultSetTables.next()) {
                    Tabla tablaActual = new Tabla(resultSetTables.getString(3));
                    
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
                    
                    //Se obtienen y guardan las claves foraneas
                    rsF = metaData.getCrossReference(null, null, resultSetTables.getString(3), null, null, null);
                    while (rsF.next()){
                        //System.out.println(rsF.getString("FKTABLE_NAME")+" - "+rsF.getString("FKCOLUMN_NAME")+" - "+rsF.getString("PKCOLUMN_NAME")+ " - "+rsF.getString("PKTABLE_NAME"));
                        for (int i = 0; i < tablas.size(); i++) {
                            for (int j = 0; j < tablas.get(i).getAtributos().size(); j++) {
                                if (tablas.get(i).getAtributos().get(j).getNombre().equals(rsF.getString("FKCOLUMN_NAME"))){
                                    tablas.get(i).getAtributos().get(j).setRefAAtributo(rsF.getString("PKCOLUMN_NAME"));
                                    tablas.get(i).getAtributos().get(j).setRefATabla(rsF.getString("PKTABLE_NAME"));
                                }
                            }
                        }
                        
                        
                    }
                   
                    
                    //Se obtienen y guardan las claves unicas     
                    rsU = metaData.getIndexInfo(null, null, tablaActual.getNombre(), true, false);
                    while (rsU.next()) {
                        String atribNombre = rsU.getString(9);
                        for (int i = 0; i < tablaActual.getAtributos().size(); i++) {
                            Atributo actual = tablaActual.getAtributos().get(i);
                            if (actual.getNombre().equals(atribNombre)){
                                if (!actual.getPk()) {
                                    tablaActual.getAtributos().get(i).setUnq(true);
                                    //Solo para atributos que no son claves primarias
                                }
                            }
                        }
                    }
                    
                    //Se obtienen y guardan los Triggers
                    tablaActual.setTriggers(tablaActual.listaTrigger(tablaActual.getNombre(), conexion));
                    Statement s = conexion.createStatement();
                    rsI = s.executeQuery ("SELECT indexname, indexdef FROM pg_indexes WHERE tablename = '"+tablaActual.getNombre()+"';");
                    while (rsI.next()) {
                        Indice indice = new Indice(rsI.getString("indexname"), rsI.getString("indexdef"));
                        tablaActual.agregarIndice(indice);
                    }
                    
                    //Se agrega la tabla al listado de tablas de la base de datos
                    tablas.add(tablaActual);
                    
                    rsC.close();
                    rsF.close();
                    rsP.close();
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
                System.out.println("\t\t"+atribActual.getNombre()+" tipo: "+atribActual.getTipo()+" PK: "+atribActual.getPk()+" UK: "+atribActual.getUnq()+" FK: "+atribActual.getRefATabla()+" ("+atribActual.getRefAAtributo()+")");
            }
        }
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
