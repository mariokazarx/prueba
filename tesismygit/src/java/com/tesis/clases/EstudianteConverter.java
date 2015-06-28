/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Estudiante;
import com.tesis.managedbeans.mbvMatricula;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Mario Jurado
 */
@FacesConverter("estudianteConverter")
public class EstudianteConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("aaaa"+value);
        if(value != null && value.trim().length() > 0) {
            try {
                mbvMatricula estudiante = (mbvMatricula) fc.getViewRoot().getViewMap().get("mbvMatricula");
                System.out.println("aaaassssLL"+estudiante.getEstudiantes());
                List<Estudiante> aux = estudiante.getEstudiantes();
                for(Estudiante est: aux){
                    System.out.println("aaaa"+est.getIdentificiacion()+"valeu"+value);
                    if(est.getIdentificiacion().equals(value)){
                        return est;
                    }
                }
                return null;
            } catch(NumberFormatException e) {
                System.out.println("ddddd"+e.toString());
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
         System.out.println("bbb"+object);
       if(object != null) {
            return String.valueOf(((Estudiante) object).getIdentificiacion());
        }
        else {
            System.out.println("NNN");
            return null;
        }
    }  
    
}
