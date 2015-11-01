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
    
    public informacionBD(LinkedList<Tabla> tablas, LinkedList<Trigger> triggers, LinkedList<String> procedimientos){
        this.tablas = tablas;
        this.triggers = triggers;
        this.procedimientos = procedimientos;
    }
    
    public String comparacion(informacionBD base01, informacionBD base02){
        String resultado = "";
        //deberá compararse cada lista de base01 con cada lista de base02, el resultado debe ser el mismo si se hace
        //base01.comparacion(base01, base02) que base02.comparacion(base02, base01)
        return resultado;
    }
    
    
}
