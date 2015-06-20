/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.EstadoProfesorFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.TipoUsuarioFacade;
import com.tesis.entity.EstadoProfesor;
import com.tesis.entity.Profesor;
import com.tesis.entity.TipoUsuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.UploadedFile;
import com.tesis.clases.Encrypt;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvProfesor implements Serializable {
    private Profesor profesor;
    private List<Profesor> profesores;
    private UploadedFile foto;
    private String txtRepiteContrasenia;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private EstadoProfesorFacade estadoEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    /*@Resource 
    UserTransaction tx;*/
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
        try {
            if(this.profesor.getContraseña().equals(this.txtRepiteContrasenia)){
                //tx.begin();
                EstadoProfesor estado = estadoEjb.find(1);
                TipoUsuario tusu = tusuEjb.find(2);
                this.profesor.setEstadoProfesorId(estado);
                this.profesor.setTipoUsuarioId(tusu);
                this.profesor.setFoto("default.jpg");
                this.profesor.setContraseña(Encrypt.sha512(this.profesor.getContraseña()));
                this.profesorEjb.create(profesor);
                FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                //tx.rollback();    
                inicioPagina();
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                return;
            }
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        
    }
    public void newProfesor(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentHeight", 440);
        options.put("height", 460);
        options.put("width",700);
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newprofesor",options,null);
    }
    public String cargarMaterias(Profesor profesor){
         FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .put("param1", profesor);
					
        return "cargaAcademica?faces-redirect=true";
     }
    public void cargarProfesor(int profesorid){
        try {
            this.profesor = profesorEjb.find(profesorid);
            RequestContext.getCurrentInstance().update("frmEditarProfesor:panelEditarProfesor");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarProfesor').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
