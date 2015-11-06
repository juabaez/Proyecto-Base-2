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
 * @author mery
 */
public class Procedure {
    
    private String nombre;
    private String retorno;
    private LinkedList<String> parametros;

    public Procedure(String nom,String ret){
        this.nombre=nom;
        this.retorno=ret;
        this.parametros=new LinkedList<String>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public LinkedList<String> getParametros() {
        return parametros;
    }

    public void setParametros(LinkedList<String> parametros) {
        this.parametros = parametros;
    }
    
    
     @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Procedure other = (Procedure) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.retorno, other.retorno)) {
            return false;
        }
        if (!Objects.equals(this.parametros, other.parametros)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        String res= "";
        res += parametros.getFirst();
        for (int i = 1; i < parametros.size(); i++) {
            res += ", "+parametros.get(i);
        }
        return "Procedure{\n\t" + "nombre: " + nombre + "\n\tretorno: " + retorno + "\n\tparametros: "+res+"\n}";
    }
    
}
