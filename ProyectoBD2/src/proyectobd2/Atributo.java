package proyectobd2;

import java.util.Objects;


public class Atributo {
    private String nombre;
    private String tipo = "";
    private boolean pk = false;
    private String refAAtributo = "";
    private String refATabla = "";
    private boolean unq = false;

    public Atributo(String nombre, String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
        this.unq = false;
    }
    
    public Atributo(String nombre, String tipo, boolean pk, String refAAtributo, String refATabla, boolean unq) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pk = pk;
        this.refAAtributo = refAAtributo;
        this.refATabla = refATabla;
        this.unq = unq;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public boolean getUnq() {
        return unq;
    }

    public void setUnq(boolean unq) {
        this.unq = unq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Atributo other = (Atributo) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.refATabla, other.refATabla)) {
            return false;
        }
        if (!Objects.equals(this.refAAtributo, other.refAAtributo)){
            return false;
        }
        if (!Objects.equals(this.unq, other.unq)) {
            return false;
        }
        return true;
    }
    
    /**
     * Como condición de uso el nombre de los atributos deben ser iguales, sino se concidera que son distintas columnas de la tabla
     * @param otro atributo a comparar
     * @return listado de diferencias entre atributos
     */
    public String comparacion(Atributo otro, String nombreBD1, String nombreBD2){
        String resultado = "";
        if (!this.tipo.equals(otro.tipo)){
            resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' es de tipo '"+this.tipo+
                    "' el de la base '"+nombreBD2+"' es de tipo '"+otro.tipo+"'\n";
        }
        if (!this.pk == otro.pk){
            if (pk){
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' es de tipo CLAVE PRIMARIA"+
                    " el de la base '"+nombreBD2+"' NO lo es\n";
            } else {
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD2+"' es de tipo CLAVE PRIMARIA"+
                    " el de la base '"+nombreBD1+"' NO lo es\n";
            } 
        }
        if (!this.refAAtributo.equals(otro.getRefAAtributo())){
            //Que el de la base 1 sea clave foranea pero el de la 2 no
            if (!this.refAAtributo.equals("") && otro.getRefAAtributo().equals("")){
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' es CLAVE FORANEA"+
                    "que referencia a '"+this.getRefATabla()+"("+this.getRefAAtributo()+")' el de la base '"+nombreBD2+"' NO es CLAVE FORANEA\n";
            }
            //Que el de la base 1 NO sea clave foraea y el de la 2 si
            if (this.refAAtributo.equals("") && !otro.getRefAAtributo().equals("")){
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' NO es CLAVE FORANEA"+
                    " el de la base '"+nombreBD2+"' es CLAVE FORANEA que referencia a '"+ otro.getRefATabla()+"("+otro.getRefAAtributo()+")'\n";
            }
            //Que ambos sean claves foraneas pero no iguales    
            if (!this.refAAtributo.equals("") && !otro.getRefAAtributo().equals("")){
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' es CLAVE FORANEA "+
                    "que referencia a '"+this.getRefATabla()+"("+this.getRefAAtributo()+")' el de la base '"+nombreBD2+"' es CLAVE FORANEA que referencia a '"+ 
                     otro.getRefATabla()+"("+otro.getRefAAtributo()+")'\n";
            }
        }
        if (!this.unq == otro.unq){
            if (unq){
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD1+"' es de tipo CLAVE UNICA"+
                    " el de la base '"+nombreBD2+"' NO lo es\n";
            } else {
                resultado = resultado + "\nMientras que el atributo '"+this.nombre+"' en la base de datos '"+nombreBD2+"' es de tipo CLAVE UNICA"+
                    " el de la base '"+nombreBD1+"' NO lo es\n";
            } 
        }
        /*if (resultado.equals("")){
            resultado = "Atributos Iguales";
        }*/
        return resultado;
    }

    /**
     * @return the refAAtributo
     */
    public String getRefAAtributo() {
        return refAAtributo;
    }

    /**
     * @param refAAtributo the refAAtributo to set
     */
    public void setRefAAtributo(String refAAtributo) {
        this.refAAtributo = refAAtributo;
    }

    /**
     * @return the refATabla
     */
    public String getRefATabla() {
        return refATabla;
    }

    /**
     * @param refATabla the refATabla to set
     */
    public void setRefATabla(String refATabla) {
        this.refATabla = refATabla;
    }

    @Override
    public String toString() {
        return "Atributo{" + "nombre=" + nombre + ", tipo=" + tipo + ", pk=" + pk + ", refAAtributo=" + refAAtributo + ", refATabla=" + refATabla + ", unq=" + unq + '}';
    }
    
    
    
    
}
