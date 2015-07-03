/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AreaFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.clases.LazyAsignaturaDataModel;
import com.tesis.entity.Area;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Configuracion;
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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvAsignatura implements Serializable{

    /**
     * Creates a new instance of mbvAsignatura
     */
    private Asignatura asignatura;
    private LazyDataModel<Asignatura> asignaturas;
    private List<Area> areas;
    private Area areaselected;
    private List<Configuracion> configuraciones;
    private Configuracion confuguracionselected;
    
    @EJB
    private AsignaturaFacade asignaturaEjb;

    public mbvAsignatura() {
    }
    
    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public LazyDataModel<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(LazyDataModel<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public Area getAreaselected() {
        return areaselected;
    }

    public void setAreaselected(Area areaselected) {
        this.areaselected = areaselected;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public Configuracion getConfuguracionselected() {
        return confuguracionselected;
    }

    public void setConfuguracionselected(Configuracion confuguracionselected) {
        this.confuguracionselected = confuguracionselected;
    }

    public AsignaturaFacade getAsignaturaEjb() {
        return asignaturaEjb;
    }

    public void setAsignaturaEjb(AsignaturaFacade asignaturaEjb) {
        this.asignaturaEjb = asignaturaEjb;
    }

    public AreaFacade getAreaEjb() {
        return areaEjb;
    }

    public void setAreaEjb(AreaFacade areaEjb) {
        this.areaEjb = areaEjb;
    }

    public ConfiguracionFacade getConfiguracionEjb() {
        return configuracionEjb;
    }

    public void setConfiguracionEjb(ConfiguracionFacade configuracionEjb) {
        this.configuracionEjb = configuracionEjb;
    }
    @EJB
    private AreaFacade areaEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    
    
    @PostConstruct
    public void inicioPagina(){
        this.asignatura=new Asignatura();
        this.asignaturas= new LazyAsignaturaDataModel(this.asignaturaEjb.findAll());
        //this.areas = this.areaEjb.findAll();
        this.areaselected = new Area();
        this.configuraciones = this.configuracionEjb.findAll();
        this.confuguracionselected = new Configuracion();
    }
    
    
    public void insertar(){
        try{
            asignatura.setAreaId(areaselected);
            asignatura.setConfiguracionId(confuguracionselected);
            RequestContext.getCurrentInstance().closeDialog(this);
            asignaturaEjb.create(asignatura);
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignatura creada Satisfactoriamente", ""));
            inicioPagina();
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignatura registrada", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            this.areaselected = this.areaEjb.find(areaselected.getAreaId());
            this.confuguracionselected = this.configuracionEjb.find(confuguracionselected.getConfiguracionId());
            asignatura.setAreaId(areaselected);
            asignatura.setConfiguracionId(confuguracionselected);
            asignaturaEjb.edit(asignatura);
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignatura Editada Exitosamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarAsignatura').hide()");
            inicioPagina();
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarAreas(){
        this.confuguracionselected = this.configuracionEjb.find(this.confuguracionselected.getConfiguracionId());
        this.areas = this.confuguracionselected.getAreaList();
    }
    public void cargarAsignatura(int asignaturaid){
        try {
            this.asignatura =  this.asignaturaEjb.find(asignaturaid);
            this.areaselected = this.areaEjb.find(asignatura.getAreaId().getAreaId());
            this.confuguracionselected = this.configuracionEjb.find(asignatura.getConfiguracionId().getConfiguracionId());
            this.areas = this.confuguracionselected.getAreaList();
            RequestContext.getCurrentInstance().update("frmEditarAsignatura:panelEditarAsignatura");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarAsignatura').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newAsignatura(){
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        //options.put("showEffect", "clip");
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newasignatura",options,null);
    }
    
}
