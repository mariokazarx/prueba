/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.EstadoProfesorFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.PeriodoFacade;
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
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Periodo;
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
    private List<EstadoProfesor> estadosProfesor;
    private EstadoProfesor estadoSelected;
    private boolean cambiarContraseña;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private EstadoProfesorFacade estadoEjb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private EstadocontenidotematicoFacade estadocontenidoEjb;   
    @Resource 
    UserTransaction tx;
    public mbvProfesor() {
    }

    public EstadoProfesor getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(EstadoProfesor estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<EstadoProfesor> getEstadosProfesor() {
        return estadosProfesor;
    }

    public void setEstadosProfesor(List<EstadoProfesor> estadosProfesor) {
        this.estadosProfesor = estadosProfesor;
    }

    public boolean isCambiarContraseña() {
        return cambiarContraseña;
    }

    public void setCambiarContraseña(boolean cambiarContraseña) {
        this.cambiarContraseña = cambiarContraseña;
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
       this.estadoSelected = new EstadoProfesor();
       this.cambiarContraseña = false;
       this.estadosProfesor = estadoEjb.findAll();
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
        options.put("responsive", true);
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
            this.estadoSelected = estadoEjb.find(this.profesor.getEstadoProfesorId().getEstadoProfesorId());
            this.txtRepiteContrasenia = this.profesor.getContraseña();
            RequestContext.getCurrentInstance().update("frmEditarProfesor:panelEditarProfesor");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarProfesor').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void activarContraseña(){
        if(cambiarContraseña){
            
        }else{
            
        }
    }
    public void actualizar(){
        try{
            tx.begin();
            if(cambiarContraseña){
                this.profesor.setContraseña(Encrypt.sha512(this.profesor.getContraseña()));
            }
            this.estadoSelected = this.estadoEjb.find(estadoSelected.getEstadoProfesorId());
            if(estadoSelected.getEstadoProfesorId()==2){
                Anlectivo anlectivoAux = aEscolarEjb.getIniciado();
                if(anlectivoAux.getAnlectivoId()!=null){
                    //hay iniciado
                    List<Periodo> peridosAux = periodoEjb.getPeriodosByAnioActivo(anlectivoAux);
                    Estadocontenidotematico estAux = estadocontenidoEjb.find(5);
                    for(Periodo periodoAux: peridosAux){
                        if(!contenidoEjb.updateRetirarProfesor(periodoAux, estAux, profesor)){
                            tx.rollback();
                            return;
                        }
                    }
                }
                //comprobar si hay un año activo
                //revisar si tiene asignacion academica
                //poner en advertencia todos los contenidos de periodos no terminados de ese profesor y de ese año 
            }
            profesor.setEstadoProfesorId(estadoSelected);
            profesorEjb.edit(profesor);
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarProfesor').hide()");
            inicioPagina();
            tx.commit();
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
