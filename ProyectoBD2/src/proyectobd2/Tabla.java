/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        LinkedList<Atributo> atributos = this.getAtributos();
        atributos.add(nuevo);
        this.setAtributos(atributos);
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
        
    public LinkedList<Trigger> listaTrigger(String nombreT, Connection con) throws SQLException{
        Statement s = con.createStatement();
        LinkedList<Trigger> lT = new LinkedList();
        LinkedList<String> nombTrigger = nombreTrigger(nombreT,s);
        LinkedList<String> condTrigger = new LinkedList();
        ResultSet rs;
        String disparador=null;
        for (int i = 0; i < nombTrigger.size(); i++) {
            rs = s.executeQuery ("SELECT event_manipulation,action_timing FROM information_schema.triggers where trigger_name ='"+nombTrigger.get(i)+"' order by event_manipulation");
            while (rs.next()){
                disparador = rs.getObject("action_timing").toString();
                condTrigger.add(rs.getObject("event_manipulation").toString());
            }
            lT.add(new Trigger(nombreT, condTrigger, disparador));
        }
        s.close();
        return lT;
    }
    
    public static LinkedList<String> nombreTrigger(String nom,Statement s) throws SQLException{
        LinkedList<String> nomb = new LinkedList();
        ResultSet rs = s.executeQuery("SELECT tgname FROM pg_trigger, pg_class WHERE tgrelid=pg_class.oid AND relname='"+nom+"' AND tgname not like '%RI_ConstraintTrigger_%' order by tgname;");
        while (rs.next()){
            nomb.add(rs.getObject("tgname").toString());
        }
        return nomb;
    }
    
}
