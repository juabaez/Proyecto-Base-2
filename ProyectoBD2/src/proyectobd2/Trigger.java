/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.util.LinkedList;
import java.util.Objects;

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trigger other = (Trigger) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (this.condicion!= null && other.condicion!=null) {
            return this.equalsCondicion(other);
        }
        if (!Objects.equals(this.disparador, other.disparador)) {
            return false;
        }
        return true;
    }
    
    public boolean equalsCondicion(Trigger other){
        LinkedList<String> current = this.condicion;
        LinkedList<String> otherC = other.condicion;
        if (current.size()==3 && otherC.size()==3) {
            return true;
        }
        if (current.size()!=otherC.size()) {
            return false;
        }
        if (current.size()<3 && otherC.size()<3) {
            int i = 0;
            int j = 0;
            boolean iguales = false;
            while (i<current.size() && j<otherC.size() && !iguales){
                if (!current.get(i).equals(otherC.get(j))) {
                    iguales = true;
                }
                i++;
                j++;
            }
            if (iguales) {
                return false;
            }else{
                return true;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String cond= "";
        cond = cond + condicion.getFirst();
        for (int i = 1; i < condicion.size(); i++) {
            cond = cond +", "+condicion.get(i);
        }
        return "Trigger{" + "nombre: " + nombre + ", condicion: " + cond + ", disparador: " + disparador + '}';
    }
    
    /**
     * Como condición de uso el nombre de los trigger deben ser iguales, sino se concidera que son distintas columnas de la tabla
     * @param otro trigger a comparar
     * @param nombreBD1 nombre de la base de datos 1
     *  @param nombreBD2 nombre de la base de datos 2
     * @return listado de diferencias entre triggers
     */
    public String comparacionTrigger(Trigger otro, String nombreBD1, String nombreBD2){
        String resultado = "";
        int i=0;
        int j=0;
        while (i<this.condicion.size() && j<otro.getCondicion().size()){
            if (!this.condicion.get(i).equals(otro.getCondicion().get(j))) {
                resultado = resultado + "El trigger "+this.nombre+" en la base de datos "+nombreBD1+" tiene como condicion de disparo "+this.disparador+
                    "mientras que en la base de datos "+nombreBD2+" tiene como condicion de disparo "+otro.getDisparador();
            }
            i++;
            j++;
        }
        while (i<this.condicion.size()) {
            resultado = resultado + "El trigger "+this.nombre+" en la base de datos "+nombreBD1+" tiene como condicion de disparo "+this.disparador+
                "mientras que en la base de datos "+nombreBD2+" no lo tiene como condicion de disparo ";
        }
        while (j<otro.getCondicion().size()) {
                resultado = resultado + "El trigger "+this.nombre+" en la base de datos "+nombreBD1+" tiene como condicion de disparo "+this.disparador+
                    "mientras que en la base de datos "+nombreBD2+" no lo tiene como condicion de disparo";
        }
        if (!this.disparador.equals(otro.getDisparador())) {
            resultado = resultado + "El trigger "+this.nombre+" en la base de datos "+nombreBD1+" tiene como disparador "+this.disparador+
                    "mientras que en la base de datos "+nombreBD2+" tiene como disparador "+otro.getDisparador();
        }
        return resultado;
    }
    
}
