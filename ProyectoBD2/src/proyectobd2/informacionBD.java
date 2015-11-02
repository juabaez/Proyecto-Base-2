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
    private LinkedList<Trigger> triggers;//listado de triggers existentes
    private LinkedList<String> procedimientos;//LinkedList<String> debera ser LinkedList<Procedimientos> se tiene que cambiar
    private String nombre = "";
    
    public informacionBD(){
    }
    
    public informacionBD(String nombre, LinkedList<Tabla> tablas, LinkedList<Trigger> triggers, LinkedList<String> procedimientos){
        this.nombre = nombre;
        this.tablas = tablas;
        this.triggers = triggers;
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
        String resultado = "";
        String tablasComunes = "";
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
                    tablasComunes = tablasComunes + tablaBuscada.getNombre()+"\n";
                } else {
                    adicionales1 = adicionales1 + tablaBuscada.getNombre()+"\n";
                }
            }
        } else {
            resultado = resultado + "- No existen tablas en "+base01.nombre+"\n";
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
                    adicionales2 = adicionales2 + tablaBuscada.getNombre()+"\n";
                }
            }
        } else {
            resultado = resultado + "- No existen tablas en "+base01.nombre+"\n";
        }
        if (adicionales1.equals("") && adicionales2.equals("")){
            resultado = resultado + "+ Las bases de datos tienen las mismas tablas:\n"+tablasComunes;
        } else {
            resultado = resultado + "- Las bases de datos no tienen las mismas tablas.\nTablas comunes:\n:"+tablasComunes;
            if (adicionales1.equals("")){
                resultado = resultado + "  La base de datos "+base01.getNombre()+" no tiene tablas adicionales.";
            } else {
                resultado = resultado + "  La base de datos "+base01.getNombre()+" tiene las siguientes tablas adicionales:\n"+
                        adicionales1;
            }
            if (adicionales1.equals("")){
                resultado = resultado + "  La base de datos "+base01.getNombre()+" no tiene tablas adicionales.";
            } else {
                resultado = resultado + "  La base de datos "+base02.getNombre()+" tiene las siguientes tablas adicionales:\n"+
                        adicionales2;
            }
        }
        
        /*resultado = resultado + tablasComunes + "\nAdicionales en "+base01.getNombre()+":\n"+adicionales1+"Adicionales en "+
                base02.getNombre()+":\n"+adicionales2;*/
        //deberá compararse cada lista de base01 con cada lista de base02, el resultado debe ser el mismo si se hace
        //base01.comparacion(base01, base02) que base02.comparacion(base02, base01)
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
     * @return the triggers
     */
    public LinkedList<Trigger> getTriggers() {
        return triggers;
    }

    /**
     * @param triggers the triggers to set
     */
    public void setTriggers(LinkedList<Trigger> triggers) {
        this.triggers = triggers;
    }

    /**
     * @return the procedimientos
     */
    public LinkedList<String> getProcedimientos() {
        return procedimientos;
    }

    /**
     * @param procedimientos the procedimientos to set
     */
    public void setProcedimientos(LinkedList<String> procedimientos) {
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
