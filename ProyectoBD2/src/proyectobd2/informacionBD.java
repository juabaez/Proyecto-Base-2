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
    private LinkedList<Procedimiento> procedimientos;//LinkedList<String> debera ser LinkedList<Procedimientos> se tiene que cambiar
    private String nombre = "";
    
    public informacionBD(){
    }
    
    public informacionBD(String nombre, LinkedList<Tabla> tablas, LinkedList<Procedimiento> procedimientos){
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
        resultado = resultado + "\n\t-------------------------\n\tCOMPARACIÓN DE TRIGGERS:\n\t-------------------------\n";
        resultado = resultado + compararTriggers(base01, base02);
        resultado = resultado + "\n\t------------------------------------------\n\tCOMPARACIÓN DE PROCEDIMIENTOS ALMACENADOS:\n\t------------------------------------------\n";
        resultado = resultado + compararProcedimientos(base01, base02);
        resultado = resultado + "\n\t-------------------------\n\tCOMPARACIÓN INDICES:\n\t-------------------------\n";
        resultado = resultado + compararIndices(base01, base02);
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
                                        res = "\nTabla "+tabla+":\n" +res;
                                        mostrado = true;
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
                    for (int k = 0; k < tablaBD1.getTriggers().size(); k++) {
                        Trigger trig1 = tablaBD1.getTriggers().get(k);
                        boolean encontrado =false;
                        for (int m = 0; m < tablaBD2.getTriggers().size() && !encontrado; m++) {
                            Trigger trig2 = tablaBD2.getTriggers().get(m);
                            if (trig1.getNombre().equals(trig2.getNombre())) {
                                encontrado = true;
                                String res = trig1.comparacionTrigger(trig2, base01.getNombre(), base02.getNombre());
                                if (!res.equals("")){
                                    if (!mostrado){
                                        res = "\nTabla "+tabla+":\n" +res;
                                        mostrado = true;
                                    }                                    
                                    resultado = resultado + res;
                                }
                                
                            }
                        }
                        if (encontrado){
                            comunes = comunes + trig1.getNombre()+"\n\t";
                        } else {
                            adicionales1 = adicionales1 + trig1.getNombre()+"\n\t";
                        }
                    }
                    for (int k = 0; k < tablaBD2.getTriggers().size(); k++) {
                        Trigger trig2 = tablaBD2.getTriggers().get(k);
                        boolean encontrado =false;
                        for (int l = 0; l < tablaBD1.getTriggers().size() && !encontrado; l++) {
                            Trigger trig1 = tablaBD1.getTriggers().get(l);
                            if (trig1.getNombre().equals(trig2.getNombre())) {
                                encontrado = true;
                                //resultado = resultado + atrib2.comparacion(atrib1, base02.getNombre(), base01.getNombre());
                            }
                        }
                        if (!encontrado){
                            adicionales2 = adicionales2 + trig2.getNombre()+"\n\t";
                        }
                    }
                    
                    if (adicionales1.equals("") && adicionales2.equals("")){
                        
                        if (comunes.trim().equals("")){
                            resultado = resultado + "\n+ En las tablas '"+tablaBD1.getNombre()+"' no existen triggers\n";
                        } else {
                            resultado = resultado + "\n+ Las tablas '"+tablaBD1.getNombre()+"' tienen los mismos triggers:\n"+comunes;
                        }
                    } else {
                        if (comunes.equals("")){
                            comunes = "No hay triggers en común";
                        }
                        resultado = resultado + "\n- Las tablas '"+tablaBD1.getNombre()+"' no tienen los mismos triggers:.\nTriggers comunes:\n"+comunes;
                        if (adicionales1.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base01.getNombre()+" no tiene triggers adicionales.";
                        } else {
                            resultado = resultado + "\nLa tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base01.getNombre()+" tiene los siguientes triggers adicionales:\n"+
                                    adicionales1;
                        }
                        if (adicionales2.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base02.getNombre()+" no tiene triggers adicionales.";
                        } else {
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos "+base02.getNombre()+" tiene los siguientes triggers adicionales:\n"+
                                    adicionales2;
                        }
                    }
                }
                
            }
        }
        return resultado;
    }
    
    /*public boolean CompararProcedimientos(Procedimiento otro){
        boolean igual=false;
        if(this.equals(otro)){
            return true;
        }
        return igual;
    }*/
    
    
    
    public String compararProcedimientos(informacionBD base01, informacionBD base02){
        String resultado = "";
        String comunes = "\t";
        String adicionales1 = "";
        String adicionales2 = "";
        for (int i = 0; i < base01.getProcedimientos().size(); i++) {
            Procedimiento actual1= base01.getProcedimientos().get(i);
            boolean encontrado = false;
            for (int j = 0; j < base02.getProcedimientos().size() && !encontrado; j++) {
                Procedimiento actual2 = base02.getProcedimientos().get(j);
                if (actual1.getNombre().equals(actual2.getNombre())){
                    resultado = resultado + actual1.procedimientoIguales(actual1,actual2,base01.getNombre(),base02.getNombre());
                    encontrado = true;
                }
            }
            if (encontrado){
                comunes = comunes + actual1.getNombre() +"\n\t";;
            } else {
                adicionales1 = adicionales1 + actual1.getNombre()+"\n\t";
            }
        }
        for (int i = 0; i < base02.getProcedimientos().size(); i++) {
            Procedimiento actual2= base02.getProcedimientos().get(i);
            boolean encontrado = false;
            for (int j = 0; j < base01.getProcedimientos().size() && !encontrado; j++) {
                Procedimiento actual1 = base01.getProcedimientos().get(j);
                if (actual2.getNombre().equals(actual1.getNombre())){
                    resultado = resultado + actual2.procedimientoIguales(actual2,actual1,base02.getNombre(),base01.getNombre());
                    encontrado = true;
                }
            }
            if (!encontrado){
                adicionales2 = adicionales2 + actual2.getNombre()+"\n\t";
            }
        }
        
        if (adicionales1.equals("") && adicionales2.equals("")){
            if (comunes.trim().equals("")){
                resultado = resultado + "\n+ En las base de datos no existen procedimientos almacenados\n";
            } else {
                resultado = resultado + "\n+ Las bases de datos tienen los procedimientos almacenados:\n"+comunes;
            }
        } else {
            if (comunes.equals("")){
                comunes = "No hay procedimientos almacenados en común";
            }
            resultado = resultado + "\n- Las bases de datos no tienen los mismos procedimientos almacenados:.\nProcedimientos comunes:\n"+comunes;
            if (adicionales1.equals("")){
                resultado = resultado + "\n- La  base de datos "+base01.getNombre()+" no tiene procedimientos almacenados adicionales.";
            } else {
                resultado = resultado + "\nLa  base de datos "+base01.getNombre()+" tiene los siguientes procedimientos almacenados adicionales:\n"+
                        adicionales1;
            }
            if (adicionales2.equals("")){
                resultado = resultado + "\n- La  base de datos "+base02.getNombre()+" no tiene procedimientos almacenados adicionales.";
            } else {
                resultado = resultado + "\n- La  base de datos "+base02.getNombre()+" tiene los siguientes procedimientos almacenados adicionales:\n"+
                        adicionales2;
            }
        }
        
        return resultado;
    }
    
    public String compararIndices(informacionBD base01, informacionBD base02){
        String resultado = "";
        for (int i = 0; i < base01.getTablas().size(); i++) {
            Tabla tablaBD1 = base01.getTablas().get(i);
            for (int j = 0; j < base02.getTablas().size(); j++) {
                Tabla tablaBD2 = base02.getTablas().get(j);
                String comunes = "";
                String adicionales1 = "";
                String adicionales2 = "";
                if (tablaBD1.getNombre().equals(tablaBD2.getNombre())){
                    String tabla = tablaBD1.getNombre();
                    boolean mostrado = false;
                    for (int k = 0; k < tablaBD1.getIndices().size(); k++) {
                        Indice indice1 = tablaBD1.getIndices().get(k);
                        boolean encontrado =false;
                        for (int m = 0; m < tablaBD2.getIndices().size() && !encontrado; m++) {
                            Indice indice2 = tablaBD2.getIndices().get(m);
                            if (indice1.getNombre().equals(indice2.getNombre())) {
                                encontrado = true;
                                String res = indice1.comparacionIndice(indice2, tabla, base01.getNombre(), base02.getNombre());
                                if (!res.equals("")){
                                    if (!mostrado){
                                        res = "\nTabla "+tabla+":\n" +res;
                                        mostrado = true;
                                    }                                    
                                    resultado = resultado + res;
                                }
                                
                            }
                        }
                        if (encontrado){
                            comunes = comunes + indice1.getNombre()+"\n\t";
                        } else {
                            adicionales1 = adicionales1 + indice1.getNombre()+"\n\t";
                        }
                    }
                    for (int k = 0; k < tablaBD2.getIndices().size(); k++) {
                        Indice indice2 = tablaBD2.getIndices().get(k);
                        boolean encontrado =false;
                        for (int l = 0; l < tablaBD1.getIndices().size() && !encontrado; l++) {
                            Indice trig1 = tablaBD1.getIndices().get(l);
                            if (trig1.getNombre().equals(indice2.getNombre())) {
                                encontrado = true;
                                //resultado = resultado + atrib2.comparacion(atrib1, base02.getNombre(), base01.getNombre());
                            }
                        }
                        if (!encontrado){
                            adicionales2 = adicionales2 + indice2.getNombre()+"\n\t";
                        }
                    }
                    
                    if (adicionales1.equals("") && adicionales2.equals("")){
                        
                        if (comunes.trim().equals("")){
                            resultado = resultado + "\n+ En las tablas '"+tablaBD1.getNombre()+"' no existen indices\n";
                        } else {
                            resultado = resultado + "\n+ Las tablas '"+tablaBD1.getNombre()+"' tienen los mismos indices:\n"+comunes;
                        }
                    } else {
                        if (comunes.equals("")){
                            comunes = "No hay indices en común";
                        }
                        resultado = resultado + "\n- Las tablas '"+tablaBD1.getNombre()+"' no tienen los mismos indices:.\nIndices comunes:\n"+comunes;
                        if (adicionales1.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos '"+base01.getNombre()+"' no tiene indices adicionales.";
                        } else {
                            resultado = resultado + "\nLa tabla '"+tablaBD1.getNombre()+"' de la  base de datos '"+base01.getNombre()+"' tiene los siguientes indices adicionales:\n"+
                                    adicionales1;
                        }
                        if (adicionales2.equals("")){
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos '"+base02.getNombre()+"' no tiene indices adicionales.";
                        } else {
                            resultado = resultado + "\n- La tabla '"+tablaBD1.getNombre()+"' de la  base de datos '"+base02.getNombre()+"' tiene los siguientes indices adicionales:\n"+
                                    adicionales2;
                        }
                    }
                }
                
            }
        }
        return resultado;
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
    public LinkedList<Procedimiento> getProcedimientos() {
        return procedimientos;
    }

    /**
     * @param procedimientos the procedimientos to set
     */
    public void setProcedimientos(LinkedList<Procedimiento> procedimientos) {
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
