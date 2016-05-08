/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binding;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlOutputText;

/**
 *
 * @author usuario
 */
@Named(value = "enlace")
@RequestScoped
public class Enlace {
    
    private HtmlOutputText salida;

    /**
     * Creates a new instance of Enlace
     */
    public Enlace() {
    }

    public HtmlOutputText getSalida() {
        return salida;
    }

    public void setSalida(HtmlOutputText salida) {
        this.salida = salida;
    }
    
    public String ejecutar(){
        salida.setStyle("color: red;");
        return"";
    }
}
