/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.FormacriterioevaluacionFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Formacriterioevaluacion;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvcriterioevaluacion implements Serializable {
    private static final long serialVersionUID = 84672469225979328L;

    /**
     * Creates a new instance of mbvcriterioevaluacion
     */
    private Criterioevaluacion criterioeval;
    private List<Criterioevaluacion> criterioseval;
    private List<Formacriterioevaluacion> fcriterios;
    private Formacriterioevaluacion fcriterioselected;
    private String nomoriginal;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CriterioevaluacionFacade criterioevalEjb;
    @EJB
    private FormacriterioevaluacionFacade fcriterioejb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    
    public mbvcriterioevaluacion() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public List<Formacriterioevaluacion> getFcriterios() {
        return fcriterios;
    }

    public void setFcriterios(List<Formacriterioevaluacion> fcriterio) {
        this.fcriterios = fcriterio;
    }

    public Formacriterioevaluacion getFcriterioselected() {
        return fcriterioselected;
    }

    public void setFcriterioselected(Formacriterioevaluacion fcriterioselected) {
        this.fcriterioselected = fcriterioselected;
    }

    public FormacriterioevaluacionFacade getFcriterioejb() {
        return fcriterioejb;
    }

    public void setFcriterioejb(FormacriterioevaluacionFacade fcriterioejb) {
        this.fcriterioejb = fcriterioejb;
    }

    public Criterioevaluacion getCriterioeval() {
        return criterioeval;
    }

    public void setCriterioeval(Criterioevaluacion criterioeval) {
        this.criterioeval = criterioeval;
    }

    public List<Criterioevaluacion> getCriterioseval() {
        return criterioseval;
    }

    public void setCriterioseval(List<Criterioevaluacion> criterioseval) {
        this.criterioseval = criterioseval;
    }

    public CriterioevaluacionFacade getCriterioevalEjb() {
        return criterioevalEjb;
    }

    public void setCriterioevalEjb(CriterioevaluacionFacade criterioevalEjb) {
        this.criterioevalEjb = criterioevalEjb;
    }

    @PostConstruct
    public void inicioPagina() {
        this.nomoriginal = "";
        this.criterioeval = new Criterioevaluacion();
        this.criterioseval = this.criterioevalEjb.findAll();
        this.fcriterios = this.fcriterioejb.findAll();
        this.fcriterioselected = new Formacriterioevaluacion();
        this.consultar=false;
        this.editar=false;
        this.eliminar=false;
        this.crear=false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if(this.usr!=null){
            for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==11){
                    if(usrRol.getRoleId().getAgregar()){
                        this.crear=true;
                    }
                    if(usrRol.getRoleId().getConsultar()){
                        this.consultar=true;
                    }
                    if(usrRol.getRoleId().getEditar()){
                        this.editar=true;
                    }
                    if(usrRol.getRoleId().getEliminar()){
                        this.eliminar=true;
                    }
                }
            }
        }
        if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
            this.consultar=true;
            this.editar=true;
            this.eliminar=true;
            this.crear=true;
        }
        //this.criterioeval.setFormacriterioevaluacionId(fcriterioselected);
        //this.dialogEdit=false;
    }

    public void validateNombreUnique(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        // this.mensage=false;
        if (this.criterioevalEjb.getByNombre(arg2.toString()) == false) {
            throw new ValidatorException(new FacesMessage("ya existe este nombre"));
        }
    }

    public void validateNombreUniqueEditar(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        // this.mensage=false;
        if (!this.nomoriginal.equals(arg2.toString())) {
            if (this.criterioevalEjb.getByNombre(arg2.toString()) == false) {
                throw new ValidatorException(new FacesMessage("ya existe este nombre"));
            }
        }
    }

    public void insertar() {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.crear){
                criterioeval.setFormacriterioevaluacionId(fcriterioselected);
                RequestContext.getCurrentInstance().closeDialog(this);
                criterioevalEjb.create(criterioeval);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Criterio de evaluación editado satisfactoriamente"));
                inicioPagina();
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void closeDialog() {
        RequestContext.getCurrentInstance().execute("PF('tablaCriterios').clearFilters()");
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Criterio de evaluación registrado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                this.fcriterioselected = this.fcriterioejb.find(fcriterioselected.getFormacriterioevaluacionId());
                criterioeval.setFormacriterioevaluacionId(fcriterioselected);
                criterioevalEjb.edit(criterioeval);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Criterio de evaluación creado"));
                RequestContext.getCurrentInstance().execute("PF('tablaCriterios').clearFilters()");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
                inicioPagina();
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void cargarCriterioseval(int criterioevalid) {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                this.criterioeval = this.criterioevalEjb.find(criterioevalid);
                if(!anlectivoEjb.criterioEnUso(criterioeval)){
                    this.fcriterioselected = this.fcriterioejb.find(criterioeval.getFormacriterioevaluacionId().getFormacriterioevaluacionId());
                    this.nomoriginal = criterioeval.getNombre();
                    RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
                }else{
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()"); 
                }
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado","Contáctese con el administrador"));
        }
    }

    public void newCriterioEval() {
        if(!login){
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if(this.crear){
            Map<String, Object> options = new HashMap<String, Object>();
            /*options.put("contentHeight", 340);
             options.put("height", 400);
             options.put("width",700);*/
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newcriterioeval", options, null);
        }
        else{
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }
    
    public void eliminarCriterio(Criterioevaluacion criterioeval) {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.eliminar){
                //this.escala = this.escalaEjb.find(escalaid);
                if(criterioevalEjb.removeById(criterioeval)==true){
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito","Criterio de evaluación eliminado"));
                }else{
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia","este criterio esta en uso"));
                }
                RequestContext.getCurrentInstance().execute("PF('tablaCriterios').clearFilters()");
                inicioPagina();
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administradorContáctese con el administrador"));
        }
    }
    public void initRender(){
        if(!this.consultar){
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluación"));
        }
    }
}
