/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.ProfesorFacade;
import com.tesis.clases.Encrypt;
import com.tesis.entity.Profesor;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@SessionScoped
public class mbvPerfilProfesor implements Serializable{
    private static final long serialVersionUID = 6762211022687548350L;

    private Profesor profesor;
    private boolean login;
    private boolean cambiarContraseña;
    private String txtRepiteContrasenia;
    @EJB
    private ProfesorFacade profesorEjb;
    
    public mbvPerfilProfesor() {
    }

    public String getTxtRepiteContrasenia() {
        return txtRepiteContrasenia;
    }

    public void setTxtRepiteContrasenia(String txtRepiteContrasenia) {
        this.txtRepiteContrasenia = txtRepiteContrasenia;
    }

    public boolean isCambiarContraseña() {
        return cambiarContraseña;
    }

    public void setCambiarContraseña(boolean cambiarContraseña) {
        this.cambiarContraseña = cambiarContraseña;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    @PostConstruct
    public void inicioPagina() {
        cambiarContraseña = false;
        txtRepiteContrasenia = "";
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             profesor = mbslogin.getProfesor();
             login = mbslogin.isLogin();
        } catch (Exception e) {
            profesor = null;
        }
    }
    public void activarContraseña() {
        if (cambiarContraseña) {
        } else {
        }
    }
    public void actualizar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            } else {
                if (cambiarContraseña) {
                    if (this.profesor.getContraseña().equals(this.txtRepiteContrasenia)) {
                        profesor.setContraseña(Encrypt.sha512(this.profesor.getContraseña(),this.profesor.getCorreo()));
                    } else {
                        FacesContext.getCurrentInstance().addMessage("frmPerfilProfesor:txtContrasenia", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                        return;
                    }
                }
                profesorEjb.edit(this.profesor);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados exitosamente", ""));
                inicioPagina();
            }
        } catch (Exception e) {
        }
    }
}
