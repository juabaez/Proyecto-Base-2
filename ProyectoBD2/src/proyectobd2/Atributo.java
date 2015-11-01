package proyectobd2;

import java.util.Objects;


public class Atributo {
    private String nombre;
    private String tipo;
    private boolean pk;
    private boolean fk;
    private boolean unq;

    public Atributo(String nombre, String tipo, boolean pk, boolean fk, boolean unq) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pk = pk;
        this.fk = fk;
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

    public boolean getFk() {
        return fk;
    }

    public void setFk(boolean fk) {
        this.fk = fk;
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
        if (!Objects.equals(this.fk, other.fk)) {
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
            resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD1+" es de tipo "+this.tipo+
                    " el de la base "+nombreBD2+" es de tipo "+otro.tipo+"\n";
        }
        if (!this.pk == otro.pk){
            if (pk){
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD1+" es de tipo CLAVE PRIMARIA"+
                    " el de la base "+nombreBD2+" NO lo es\n";
            } else {
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD2+" es de tipo CLAVE PRIMARIA"+
                    " el de la base "+nombreBD1+" NO lo es\n";
            } 
        }
        //VER SI SE NECESITA SABER A QUIEN REFERENCIA
        if (!this.fk == otro.fk){
            if (fk){
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD1+" es de tipo CLAVE FORANEA"+
                    " el de la base "+nombreBD2+" NO lo es\n";
            } else {
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD2+" es de tipo CLAVE FORANEA"+
                    " el de la base "+nombreBD1+" NO lo es\n";
            } 
        }
        if (!this.unq == otro.unq){
            if (unq){
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD1+" es de tipo CLAVE UNICA"+
                    " el de la base "+nombreBD2+" NO lo es\n";
            } else {
                resultado = resultado + "Mientras que el atributo "+this.nombre+" en la base de datos "+nombreBD2+" es de tipo CLAVE UNICA"+
                    " el de la base "+nombreBD1+" NO lo es\n";
            } 
        }
        if (resultado.equals("")){
            resultado = "Atributos Iguales";
        }
        return resultado;
    }
    
    
}
