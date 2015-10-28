/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Octicoco
 */
public class ProyectoBD2 implements ActionListener{

    private principalGUI ventana = new principalGUI();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ProyectoBD2();
    }
    
    public ProyectoBD2(){
        try{
            javax.swing.UIManager.setLookAndFeel(
            javax.swing.UIManager.getSystemLookAndFeelClassName());
            ventana.setVisible(true);
            ventana.setActionListeners(this);
        }catch ( Exception e) { 
            e.printStackTrace();
        }
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventana.getJbProcesar()){
            principal prin = new principal(ventana);
        }
    }
    
}
