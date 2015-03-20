/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Escala;
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
public class mbvConfiguracion {

    /**
     * Creates a new instance of mbvConfiguracion
     */
    
    private Configuracion configuracion;
    private List<Configuracion> configuraciones;
    
    @EJB
    private ConfiguracionFacade configuracionEjb;
    @EJB
    private CriterioevaluacionFacade criterioevalEjb;
    @EJB
    private EscalaFacade escalaEjb;
    
    public mbvConfiguracion() {
    }
    
    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public ConfiguracionFacade getConfiguracionEjb() {
        return configuracionEjb;
    }

    public void setConfiguracionEjb(ConfiguracionFacade configuracionEjb) {
        this.configuracionEjb = configuracionEjb;
    }
    
   
    @PostConstruct
    public void inicioPagina(){
        this.configuracion=new Configuracion();
        this.configuraciones=this.configuracionEjb.findAll();
        //this.fcriterios = this.fcriterioejb.findAll();
        //this.fcriterioselected = new Formacriterioevaluacion();
    }
    
    /*public String getNombreFcriterio(Formacriterioevaluacion fcriterioid){
        return this.fcriterioejb.find(fcriterioid.getFormacriterioevaluacionId()).getNombre();
    }*/
    public String getNombreEscala(Escala escala){
        try {
            return this.escalaEjb.find(escala.getEscalaId()).getNombre();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getNombreCriterio(Criterioevaluacion criterio){
        try {
            return this.criterioevalEjb.find(criterio.getCriterioevaluacionId()).getNombre();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getNombreEscala(){
        try {
            return this.configuracion.getEscalaId().getNombre();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String getNombreCriterio(){
        try {
            return this.configuracion.getCriterioevaluacionId().getNombre();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public void insertar(){
        try{
            //criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            RequestContext.getCurrentInstance().closeDialog(this);
            //criterioevalEjb.create(criterioeval);
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
            //criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            //criterioevalEjb.edit(criterioeval);
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            inicioPagina();
        }catch(Exception e){
            System.out.print("fail"+e.getMessage());
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarCriterioseval(int criterioevalid){
        try {
            //this.criterioeval =  this.criterioevalEjb.find(criterioevalid);
            RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newConfig(){
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
