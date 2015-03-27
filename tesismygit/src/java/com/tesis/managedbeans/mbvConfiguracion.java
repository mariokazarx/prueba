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
public class mbvConfiguracion implements Serializable{

    /**
     * Creates a new instance of mbvConfiguracion
     */
    
    private Configuracion configuracion;
    private List<Configuracion> configuraciones;
    private Escala escala;
    private Criterioevaluacion criterioevaluacion;
    private List<Criterioevaluacion> criterios;
    private List<Escala> escalas;
    private Escala escalaselected;
    private Criterioevaluacion criterioevaluacionselected;

    @EJB
    private ConfiguracionFacade configuracionEjb;
    @EJB
    private CriterioevaluacionFacade criterioevalEjb;
    @EJB
    private EscalaFacade escalaEjb;
    
    public mbvConfiguracion() {
    }
    
    public Escala getEscalaselected() {
        return escalaselected;
    }

    public void setEscalaselected(Escala escalaselected) {
        this.escalaselected = escalaselected;
    }

    public Criterioevaluacion getCriterioevaluacionselected() {
        return criterioevaluacionselected;
    }

    public void setCriterioevaluacionselected(Criterioevaluacion criterioevaluacionselected) {
        this.criterioevaluacionselected = criterioevaluacionselected;
    }
    
    public List<Criterioevaluacion> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<Criterioevaluacion> criterios) {
        this.criterios = criterios;
    }

    public List<Escala> getEscalas() {
        return escalas;
    }

    public void setEscalas(List<Escala> escalas) {
        this.escalas = escalas;
    }
    
    public Escala getEscala() {
        this.escala = this.configuracion.getEscalaId();
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }
    
    public Criterioevaluacion getCriterioevaluacion() {
        this.criterioevaluacion = this.configuracion.getCriterioevaluacionId();
        return criterioevaluacion;
    }

    public void setCriterioevaluacion(Criterioevaluacion criterioevaluacion) {
        this.criterioevaluacion = criterioevaluacion;
    }
    
    public CriterioevaluacionFacade getCriterioevalEjb() {
        return criterioevalEjb;
    }

    public void setCriterioevalEjb(CriterioevaluacionFacade criterioevalEjb) {
        this.criterioevalEjb = criterioevalEjb;
    }

    public EscalaFacade getEscalaEjb() {
        return escalaEjb;
    }

    public void setEscalaEjb(EscalaFacade escalaEjb) {
        this.escalaEjb = escalaEjb;
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
        this.escala = new Escala();
        this.criterioevaluacion = new Criterioevaluacion();
        this.escalas = escalaEjb.findAll();
        this.criterios = criterioevalEjb.findAll();
        this.criterioevaluacionselected = new Criterioevaluacion();
        this.escalaselected = new Escala();
        //this.fcriterios = this.fcriterioejb.findAll();
        //this.fcriterioselected = new Formacriterioevaluacion();
    }
    
    /*public String getNombreFcriterio(Formacriterioevaluacion fcriterioid){
        return this.fcriterioejb.find(fcriterioid.getFormacriterioevaluacionId()).getNombre();
    }*/
   
    public void insertar(){
        try{
            //System.out.print(criterioevaluacionselected.getCriterioevaluacionId());
//            if(criterioevaluacionselected.getCriterioevaluacionId()==null){
//                FacesContext.getCurrentInstance().
//                        addMessage("sltCriterio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado","null criterio"));
//            }
//            if(escalaselected.getEscalaId()==null){
//                FacesContext.getCurrentInstance().
//                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado","null escala"));
//            }
            configuracion.setCriterioevaluacionId(criterioevaluacionselected);
            configuracion.setEscalaId(escalaselected);
            configuracionEjb.create(configuracion);
            //criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            //RequestContext.getCurrentInstance().closeDialog(this);
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
        RequestContext.getCurrentInstance().openDialog("newconf",options,null);
    }
}
