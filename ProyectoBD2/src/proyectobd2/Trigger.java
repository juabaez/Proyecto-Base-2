/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.util.LinkedList;

/**
 *
 * @author juancruz
 */
public class Trigger {
    private String nombre; //nombre del trigger
    private LinkedList<String> condicion; //la lista de condiciones (insert,update,delete)
    private String disparador; // after o before

    public Trigger(String nombre, LinkedList<String> condicion, String disparador) {
        this.nombre = nombre;
        this.condicion = condicion;
        this.disparador = disparador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<String> getCondicion() {
        return condicion;
    }

    public void setCondicion(LinkedList<String> condicion) {
        this.condicion = condicion;
    }

    public String getDisparador() {
        return disparador;
    }

    public void setDisparador(String disparador) {
        this.disparador = disparador;
    }

    @Override
    public String toString() {
        return "Trigger{" + "nombre=" + nombre + ", condicion=" + condicion + ", disparador=" + disparador + '}';
    }
    
}
