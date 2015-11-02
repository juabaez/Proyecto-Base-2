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
public class Tabla {
    
    private LinkedList<Atributo> atributos;
    private String nombre;
    
    public Tabla(String nombre){
        this.atributos = new LinkedList<Atributo>();
        this.nombre = nombre;
    }
    
    public void agregarAtributo(Atributo nuevo){
        this.getAtributos().add(nuevo);
    }

    /**
     * @return the atributos
     */
    public LinkedList<Atributo> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(LinkedList<Atributo> atributos) {
        this.atributos = atributos;
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
