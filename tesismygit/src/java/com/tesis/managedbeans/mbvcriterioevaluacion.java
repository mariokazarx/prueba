/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.FormacriterioevaluacionFacade;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Formacriterioevaluacion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvcriterioevaluacion implements Serializable {

    /**
     * Creates a new instance of mbvcriterioevaluacion
     */
    private Criterioevaluacion criterioeval;
    private List<Criterioevaluacion> criterioseval;
    private List<Formacriterioevaluacion> fcriterios;
    private Formacriterioevaluacion fcriterioselected;
    
    @EJB
    private CriterioevaluacionFacade criterioevalEjb;
    @EJB
    private FormacriterioevaluacionFacade fcriterioejb;
    
    public mbvcriterioevaluacion() {
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
    public void inicioPagina(){
        this.criterioeval=new Criterioevaluacion();
        this.criterioseval=this.criterioevalEjb.findAll();
        this.fcriterios = this.fcriterioejb.findAll();
        this.fcriterioselected = new Formacriterioevaluacion();
        //this.dialogEdit=false;
    }
    
    
    public void insertar(){
        try{
            criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            RequestContext.getCurrentInstance().closeDialog(this);
            criterioevalEjb.create(criterioeval);
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
            inicioPagina();
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala Registrada", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            System.out.print("fail 2"+fcriterioselected+criterioeval.getFormacriterioevaluacionId());
            criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            System.out.print("fail 3"+fcriterioselected+criterioeval.getFormacriterioevaluacionId());
            criterioevalEjb.edit(criterioeval);
            System.out.print("fail 4"+fcriterioselected+criterioeval.getFormacriterioevaluacionId());
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
            inicioPagina();
        }catch(Exception e){
            System.out.print("fail"+fcriterioselected);
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarCriterioseval(int criterioevalid){
        try {
            this.criterioeval =  this.criterioevalEjb.find(criterioevalid);
            //this.fcriterioselected = this.fcriterioejb.find(criterioeval.getFormacriterioevaluacionId().getFormacriterioevaluacionId());
            RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newCriterioEval(){
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newcriterioeval",options,null);
    }
}
