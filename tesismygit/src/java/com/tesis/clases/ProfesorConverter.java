/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.beans.ProfesorFacade;
import com.tesis.entity.Profesor;
import com.tesis.managedbeans.mbvCargaAcademica;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.spi.Context;
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
@FacesConverter("profesorConverter")
public class ProfesorConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                //Profesor profesor = (mbvCargaAcademica) fc.getExternalContext().getApplicationMap().get("mbvCargaAcademica");
                mbvCargaAcademica profesor = (mbvCargaAcademica) fc.getViewRoot().getViewMap().get("mbvCargaAcademica");
                //Profesor profesor = this.profesorEjb.find(value);
                List<Profesor> aux = profesor.getProfesores();
                for(Profesor prof: aux){
                    if(prof.getCedula().equals(value)){
                        return prof;
                    }
                }
                return null;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Profesor) object).getCedula());
        }
        else {
            return null;
        }
    }   

}  
