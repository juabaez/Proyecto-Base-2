/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.util.Objects;

/**
 *
 * @author juancruz
 */
public class Indice {
    private String nombre;
    private String detalle;

    public Indice(String nombre, String detalle) {
        this.nombre = nombre;
        this.detalle = detalle;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Indice other = (Indice) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.detalle, other.detalle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Indice{" + "nombre=" + nombre + ", detalle=" + detalle + '}';
    }
    
    
}
