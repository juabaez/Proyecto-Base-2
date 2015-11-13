/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author mery
 */
public class Procedimiento {
    
    private String nombre;
    private String retorno;
    private LinkedList<String> parametros;

    public Procedimiento(String nom,String ret){
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
        final Procedimiento other = (Procedimiento) obj;
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
        String res= "(";
        try{
            res += parametros.getFirst();
            for (int i = 1; i < parametros.size(); i++) {
                res += ", "+parametros.get(i);
            }
            res +=")";
        }catch(NoSuchElementException e){
            res+="NO POSEE PARAMETROS)";
        }
        return "\nProcedure{\n\t" + "nombre: " + nombre + "\n\tretorno: " + retorno + "\n\tparametros: "+res+"\n}";
    }
    
    public String procedimientoIguales(Procedimiento proc1, Procedimiento proc2, String nombreBD1, String nombreBD2){
        String resultado = "";
        if (!proc1.getRetorno().equals(proc2.getRetorno())){
            resultado = resultado + "Mientras que el procedimiento '"+proc1.getNombre()+"' de la base de datos '"+nombreBD1+"' "+proc1.getRetorno()
                    +" en la base de datos '"+nombreBD2+"' "+proc2.getRetorno();
        }
        String parametros1 = "";
        for (int i = 0; i < proc1.getParametros().size(); i++) {
            if (!parametros1.equals("")){
                parametros1 = parametros1 + " - ";
            }
            parametros1 = parametros1 + proc1.getParametros().get(i);
        }
        String parametros2 = "";
        for (int i = 0; i < proc2.getParametros().size(); i++) {
            if (!parametros2.equals("")){
                parametros2 = parametros2 + " - ";
            }
            parametros2 = parametros2 + proc2.getParametros().get(i);
        }
        if(!parametros1.equals(parametros2)){
            resultado = resultado + "\nEl procedimiento '"+proc1.getNombre()+"' en la base de datos '"+nombreBD1+"' tiene los parametros: "+parametros1+
                    " mientras que en la base de datos '"+nombreBD2+"' tiene los parametros: "+parametros2;
        }
        return resultado;
    }
    
}
