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
        this.atributos.add(nuevo);
    }
        
    
    
}
