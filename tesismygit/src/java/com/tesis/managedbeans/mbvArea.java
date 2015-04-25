/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AreaFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.entity.Area;
import com.tesis.entity.Configuracion;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvArea {

    private Area area;
    private List<Area> areas;
    private Configuracion confselected;
    private List<Configuracion> configs;
    @EJB
    private AreaFacade areaEJB;
    @EJB
    private ConfiguracionFacade confEJB;

    /**
     * Creates a new instance of mbvArea
     */
    
    public mbvArea() {
    }
    
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public Configuracion getConfselected() {
        return confselected;
    }

    public void setConfselected(Configuracion confselected) {
        this.confselected = confselected;
    }

    public List<Configuracion> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Configuracion> configs) {
        this.configs = configs;
    }

    public AreaFacade getAreaEJB() {
        return areaEJB;
    }

    public void setAreaEJB(AreaFacade areaEJB) {
        this.areaEJB = areaEJB;
    }

    public ConfiguracionFacade getConfEJB() {
        return confEJB;
    }

    public void setConfEJB(ConfiguracionFacade confEJB) {
        this.confEJB = confEJB;
    }
    @PostConstruct
    public void inicioPagina(){
        this.area=new Area();
        this.areas=this.areaEJB.findAll();
        this.configs = this.confEJB.findAll();
        this.confselected = new Configuracion();
    }
    public void insertar(){
        try{
            area.setConfiguracionId(confselected);
            //RequestContext.getCurrentInstance().closeDialog(this);
            areaEJB.create(area);
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
            inicioPagina();
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
