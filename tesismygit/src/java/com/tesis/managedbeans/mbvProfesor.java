/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.ProfesorFacade;
import com.tesis.entity.Profesor;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvProfesor {
    private Profesor profesor;
    private List<Profesor> profesores;
    private UploadedFile foto;
    private String txtRepiteContrasenia;
    @EJB
    private ProfesorFacade profesorEjb;
    
    public mbvProfesor() {
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public String getTxtRepiteContrasenia() {
        return txtRepiteContrasenia;
    }

    public void setTxtRepiteContrasenia(String txtRepiteContrasenia) {
        this.txtRepiteContrasenia = txtRepiteContrasenia;
    }
    

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ProfesorFacade getProfesorEjb() {
        return profesorEjb;
    }

    public void setProfesorEjb(ProfesorFacade profesorEjb) {
        this.profesorEjb = profesorEjb;
    }
    @PostConstruct
    public void inicioPagina(){
       this.profesor = new Profesor();
       this.profesores = this.profesorEjb.findAll();
    }
    public void insertar(){
        
    }
}
