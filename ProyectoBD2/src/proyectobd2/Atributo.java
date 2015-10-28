package proyectobd2;

import java.util.Objects;


public class Atributo {
    private String nombre;
    private String tipo;
    private String pk;
    private String fk;
    private String unq;

    public Atributo(String nombre, String tipo,String pk,String fk,String unq) {
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

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getUnq() {
        return unq;
    }

    public void setUnq(String unq) {
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
        return true;
    }
    
    
}
