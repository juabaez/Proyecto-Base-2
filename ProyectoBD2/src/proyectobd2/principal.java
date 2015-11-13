/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    private informacionBD infoBD1;
    private informacionBD infoBD2;
    
    principal(principalGUI ventana){
        if (ventana.getJcBasesPorDefecto().isSelected()){
            //Se obtienen los datos por defecto
            try{
                File miDir = new File (".");
                File archivo = new File (miDir.getCanonicalPath()+"\\config.txt");
                FileReader fr = new FileReader (archivo);
                BufferedReader br = new BufferedReader(fr);
                String linea ="";
                while((linea=br.readLine())!=null){
                    if (linea.contains("Servidor1:")){
                        servidor1 = linea.replace("Servidor1", "");
                    } else if(linea.contains("BaseDeDatos1:")){
                        usuario1 = linea.replace("BaseDeDatos1", "");
                    } else if(linea.contains("usuario1:")){
                        contrasena1 = linea.replace("usuario1", "");
                    } else if(linea.contains("contrasena1:")){
                        bd1 = linea.replace("contrasena1", "");
                    } else if(linea.contains("Servidor2:")){
                        servidor2 = linea.replace("Servidor2", "");
                    } else if(linea.contains("BaseDeDatos2:")){
                        usuario2 = linea.replace("BaseDeDatos2", "");
                    } else if(linea.contains("usuario2:")){
                        contrasena2 = linea.replace("usuario2", "");
                    } else if(linea.contains("contrasena2:")){
                        bd2 = linea.replace("contrasena2", "");
                    }
                }
                if (datosConexionCorrectos()){
                    cargarYComparar();
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("No se ha podido leer el archivo.\nCree un archivo en la carpeta donde se encuentra este .jar, llamado config.txt con los siguientes datos: ");
                System.out.println("Puede modificar los datos de este archivo para utilizar el sistema.\n" +
                                    "Los datos no pueden tener espacios entre si.\n" +
                                    "Servidor1:localhost\n" +
                                    "BaseDeDatos1:comparar1\n" +
                                    "usuario1:postgres\n" +
                                    "contrasena1:root\n" +
                                    "Servidor2:localhost\n" +
                                    "BaseDeDatos2:comparar2\n" +
                                    "usuario2:postgres\n" +
                                    "contrasena2:root");
            }
            
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
            if (datosConexionCorrectos()){
                cargarYComparar();
            }
        }
        
    }
    
    private boolean datosConexionCorrectos(){
        if (servidor1.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato Servidor1 no es correcto.");
            return false;
        } else if (usuario1.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato usuario1 no es correcto.");
            return false;
        } else if (contrasena1.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato contrasena1 no es correcto.");
            return false;
        } else if (bd1.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato BaseDeDatos1 no es correcto.");
            return false;
        } else if (servidor2.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato Servidor2 no es correcto.");
            return false;
        } else if (usuario2.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato usuario2 no es correcto.");
            return false;
        } else if (contrasena2.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato contrasena2 no es correcto.");
            return false;
        } else if (bd2.trim().equals("")){
            System.out.println("ERROR DE CONEXION: El dato BaseDeDatos2 no es correcto.");
            return false;
        } 
        return true;
    }
    
    private void cargarYComparar(){
        try {
            ConexionDB con = new ConexionDB();
            conexion1 = con.getConexion(servidor1, bd1, usuario1, contrasena1);
            infoBD1 = new informacionBD(bd1, getTablas(conexion1, bd1), null);
            //mostrarDatosTablas(getTablas(conexion1, bd1));
            infoBD1.setProcedimientos(this.getprocedures(conexion1, bd1));
            con.desconectar(conexion1);

            ConexionDB con2 = new ConexionDB();
            conexion2 = con2.getConexion(servidor2, bd2, usuario2, contrasena2);
            infoBD2 = new informacionBD(bd2, getTablas(conexion2, bd2), null);
            //mostrarDatosTablas(getTablas(conexion2, bd2));
            infoBD2.setProcedimientos(this.getprocedures(conexion2, bd2));
            con2.desconectar(conexion1);

            //infoBD1.mostrarProcedimientos(infoBD1.getProcedimientos(), infoBD2.getProcedimientos());
            System.out.println(infoBD1.comparacion(infoBD1, infoBD2));
        } catch(Exception e){
            e.printStackTrace();
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
                    
                    //Se obtienen y guardan los Indices
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
    
    public LinkedList<Procedimiento> getprocedures(Connection conexion, String bd) throws SQLException{
        LinkedList<Procedimiento> result=new LinkedList<Procedimiento>();        
        DatabaseMetaData metaData = conexion.getMetaData();
        try (ResultSet resultSetProcedure = metaData.getProcedures(bd, "public", null)){
            ResultSet resultSetParameter;
             while (resultSetProcedure.next()) {
                 String retorno=resultSetProcedure.getString(8);
                 if(retorno.equals("1")){
                     retorno="no se puede determinar si retorna un valor";
                 }else if(retorno.equals("2")){
                     retorno= "no devuelve un valor de retorno";
                 }else if(retorno.equals("3")){
                     retorno="devuelve un valor de retorno";
                 }
                 Procedimiento proc=new Procedimiento(resultSetProcedure.getString(3),retorno);
                 resultSetParameter=metaData.getProcedureColumns(bd, "public", proc.getNombre(), null);
                 LinkedList<String> listParametros=new LinkedList<String>();
                 while(resultSetParameter.next()){
                     String parametros=resultSetParameter.getString(4)+" | "+
                                       evalucionTipoParametro(resultSetParameter.getString(5))+" | "+
                                       resultSetParameter.getString(7);
                     listParametros.add(parametros);
                 }
                 proc.setParametros(listParametros);
                 result.add(proc);
                 resultSetParameter.close();
             }
             resultSetProcedure.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }
        return result;
    }
    
    private String evalucionTipoParametro(String tipo){
        switch (tipo) {
            case "procedureColumnUnknown":
                tipo="No se sabe";
                break;
            case "procedureColumnIn":
                tipo="parametro IN";
                break;
            case "procedureColumnInOut":
                tipo="parametro INOUT";
                break;
            case "procedureColumnOut":
                tipo="parametro OUT";
                break;
            case "procedureColumnReturn":
                tipo="valor de retorno del procedimiento";
                break;
            case "procedureColumnResult":
                tipo="columna de resultados en ResultSet";
                break;
        }
        return tipo;
    }
    
    
    
    
}
