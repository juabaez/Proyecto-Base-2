/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.util.LinkedList;

/**
 *
 * @author Octicoco
 */
public class informacionBD {
    
    private LinkedList<Tabla> tablas;//listado de tablas existentes
    private LinkedList<Procedure> procedimientos;//LinkedList<String> debera ser LinkedList<Procedimientos> se tiene que cambiar
    private String nombre = "";
    
    public informacionBD(){
    }
    
    public informacionBD(String nombre, LinkedList<Tabla> tablas, LinkedList<Procedure> procedimientos){
        this.nombre = nombre;
        this.tablas = tablas;
        this.procedimientos = procedimientos;
    }
    
    /**
     * Metodo que indica si tienen las mismas tablas, y si no es así, 
     * especificar las tablas comunes, y por cada base de datos que tablas adicionales contienen.
     * @param base01 informacionBD con datos de la primera base de datos a comparar
     * @param base02 informacionBD con datos de la segunda base de datos a comparar
     * @return String con las diferencias solicitadas de las bases de datos
     */
    public String comparacion(informacionBD base01, informacionBD base02){
        String resultado = "\n\t----------------------\n\tCOMPARACIÓN DE TABLAS:\n\t----------------------\n\n";
        resultado = resultado + compararTablas(base01, base02);
        resultado = resultado + "\n\t-------------------------\n\tCOMPARACIÓN DE ATRIBUTOS:\n\t-------------------------\n";
        resultado = resultado + compararAtributos(base01, base02);
        return resultado;
    }

    public String compararTablas(informacionBD base01, informacionBD base02){
        String resultado = "";
        String tablasComunes = "\t";
        String adicionales1 = "";
        String adicionales2 = "";
        if (base01.getTablas().size()>0){
            for (int i = 0; i < base01.getTablas().size(); i++) {
                Tabla tablaBuscada = base01.getTablas().get(i);
                boolean encontrada = false;
                for (int j = 0; j < base02.getTablas().size() && !encontrada; j++) {
                    Tabla actual =  base02.getTablas().get(j);
                    if (tablaBuscada.getNombre().equals(actual.getNombre())){
                        encontrada = true;
                    }
                }
                if (encontrada){
                    tablasComunes = tablasComunes + tablaBuscada.getNombre()+"\n\t";
                } else {
                    adicionales1 = adicionales1 + tablaBuscada.getNombre()+"\n\t";
                }
            }
        } else {
            resultado = resultado + "- No existen tablas en '"+base01.nombre+"'\n";
        }
        if (base02.getTablas().size()>0){
            for (int i = 0; i < base02.getTablas().size(); i++) {
                Tabla tablaBuscada = base02.getTablas().get(i);
                boolean encontrada = false;
                for (int j = 0; j < base01.getTablas().size() && !encontrada; j++) {
                    Tabla actual =  base01.getTablas().get(j);
                    if (tablaBuscada.getNombre().equals(actual.getNombre())){
                        encontrada = true;
                    }
                }
                if (!encontrada){
                    adicionales2 = adicionales2 + tablaBuscada.getNombre()+"\n\t";
                }
            }
        } else {
            resultado = resultado + "- No existen tablas en '"+base01.nombre+"'\n";
        }
        if (adicionales1.equals("") && adicionales2.equals("")){
            resultado = resultado + "+ Las bases de datos tienen las mismas tablas:\n"+tablasComunes;
        } else {
            resultado = resultado + "- Las bases de datos no tienen las mismas tablas.\nTablas comunes:\n"+tablasComunes;
            if (adicionales1.equals("")){
                resultado = resultado + "\n- La base de datos '"+base01.getNombre()+"' no tiene tablas adicionales.";
            } else {
                resultado = resultado + "\nLa base de datos '"+base01.getNombre()+"' tiene las siguientes tablas adicionales:\n"+
                        adicionales1;
            }
            if (adicionales2.equals("")){
                resultado = resultado + "\n- La base de datos '"+base02.getNombre()+"' no tiene tablas adicionales.";
            } else {
                resultado = resultado + "\n- La base de datos '"+base02.getNombre()+"' tiene las siguientes tablas adicionales:\n\t"+
                        adicionales2;
            }
        }
        
        /*resultado = resultado + tablasComunes + "\nAdicionales en "+base01.getNombre()+":\n"+adicionales1+"Adicionales en "+
                base02.getNombre()+":\n"+adicionales2;*/
        //deberá compararse cada lista de base01 con cada lista de base02, el resultado debe ser el mismo si se hace
        //base01.comparacion(base01, base02) que base02.comparacion(base02, base01)
        return resultado;
    }
    
    public String compararAtributos(informacionBD base01, informacionBD base02){
        String resultado = "";
        for (int i = 0; i < base01.getTablas().size(); i++) {
            Tabla tablaBD1 = base01.getTablas().get(i);
            for (int j = 0; j < base02.getTablas().size(); j++) {
                Tabla tablaBD2 = base02.getTablas().get(j);
                String comunes = "\t";
                String adicionales1 = "";
                String adicionales2 = "";
                if (tablaBD1.getNombre().equals(tablaBD2.getNombre())){
                    String tabla = tablaBD1.getNombre();
                    boolean mostrado = false;
                    for (int k = 0; k < tablaBD1.getAtributos().size(); k++) {
                        Atributo atrib1 = tablaBD1.getAtributos().get(k);
                        boolean encontrado =false;
                        for (int l = 0; l < tablaBD2.getAtributos().size() && !encontrado; l++) {
                            Atributo atrib2 = tablaBD2.getAtributos().get(l);
                            if (atrib1.getNombre().equals(atrib2.getNombre())) {
                                encontrado = true;
                                String res = atrib1.comparacion(atrib2, base01.getNombre(), base02.getNombre());
                                if (!res.equals("")){
                                    if (!mostrado){
                                        res = tablaBD1.getNombre()+":\n" +res;
                                    }                                    
                                    resultado = resultado + res;
                                }
                                
                            }
                        }
                        if (encontrado){
                            comunes = comunes + atrib1.getNombre()+"\n\t";
                        } else {
                            adicionales1 = adicionales1 + atrib1.getNombre()+"\n\t";
                        }
                    }
                    for (int k = 0; k < tablaBD2.getAtributos().size(); k++) {
                        Atributo atrib2 = tablaBD2.getAtributos().get(k);
                        boolean encontrado =false;
                        for (int l = 0; l < tablaBD1.getAtributos().size() && !encontrado; l++) {
                            Atributo atrib1 = tablaBD1.getAtributos().get(l);
                            if (atrib1.getNombre().equals(atrib2.getNombre())) {
                                encontrado = true;
                                //resultado = resultado + atrib2.comparacion(atrib1, base02.getNombre(), base01.getNombre());
                            }
                        }
                        if (!encontrado){
                            adicionales2 = adicionales2 + atrib2.getNombre()+"\n\t";
                        }
                    }
                    
                    if (adicionales1.equals("") && adicionales2.equals("")){
                        resultado = resultado + "\n+ Las tablas '"+tablaBD1.getNombre()+"' tienen los mismos atributos:\n"+comunes;
                    } else {
                        resultado = resultado + "\n- Las tablas '"+tablaBD1.getNombre()+"' no tienen los mismos atributos:.\nAtributos comunes:\n"+comunes;
                        if (adicionales1.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base01.getNombre()+" no tiene atributos adicionales.";
                        } else {
                            resultado = resultado + "\nLa tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base01.getNombre()+" tiene los siguientes atributos adicionales:\n"+
                                    adicionales1;
                        }
                        if (adicionales2.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base02.getNombre()+" no tiene atributos adicionales.";
                        } else {
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base02.getNombre()+" tiene los siguientes atributos adicionales:\n"+
                                    adicionales2;
                        }
                    }
                }
                
            }
        }
        
        return resultado;
    }
    
    public String compararTriggers(informacionBD base01, informacionBD base02){
        
        for (int i = 0; i < base01.getTablas().size(); i++) {
            Tabla tablaBD1 = base01.getTablas().get(i);
            boolean encontrada = false;
            for (int j = 0; j < base02.getTablas().size() && !encontrada; j++) {
                Tabla tablaBD2 =  base02.getTablas().get(j);
                if (tablaBD1.getNombre().equals(tablaBD2.getNombre())){
                    encontrada = true;
                }
            }
            if (encontrada){
                //tablasComunes = tablasComunes + tablaBD1.getNombre()+"\n\t";
            } else {
                //adicionales1 = adicionales1 + tablaBD1.getNombre()+"\n\t";
            }
        }
    }
    
    /**
     * @return the tablas
     */
    public LinkedList<Tabla> getTablas() {
        return tablas;
    }

    /**
     * @param tablas the tablas to set
     */
    public void setTablas(LinkedList<Tabla> tablas) {
        this.tablas = tablas;
    }

    /**
     * @return the procedimientos
     */
    public LinkedList<Procedure> getProcedimientos() {
        return procedimientos;
    }

    /**
     * @param procedimientos the procedimientos to set
     */
    public void setProcedimientos(LinkedList<Procedure> procedimientos) {
        this.procedimientos = procedimientos;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
