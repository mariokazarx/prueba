/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.CicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.entity.Ciclo;
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

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCiclo implements Serializable{
    private Ciclo ciclo;
    private List<Ciclo> ciclos;
    private List<Configuracion> configuraciones;
    private Configuracion confuguracionselected;
    
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    
    public mbvCiclo() {
    }
    
    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public List<Ciclo> getCiclos() {
        return ciclos;
    }

    public void setCiclos(List<Ciclo> ciclos) {
        this.ciclos = ciclos;
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

    public CicloFacade getCicloEjb() {
        return cicloEjb;
    }

    public void setCicloEjb(CicloFacade cicloEjb) {
        this.cicloEjb = cicloEjb;
    }

    public ConfiguracionFacade getConfiguracionEjb() {
        return configuracionEjb;
    }

    public void setConfiguracionEjb(ConfiguracionFacade configuracionEjb) {
        this.configuracionEjb = configuracionEjb;
    }
    
    @PostConstruct
    public void inicioPagina(){
        this.ciclo=new Ciclo();
        this.ciclos=this.cicloEjb.findAll();
        this.configuraciones = this.configuracionEjb.findAll();
        this.confuguracionselected = new Configuracion();
    }
    
    
    public void insertar(){
        try{
            ciclo.setConfiguracion(confuguracionselected);
            RequestContext.getCurrentInstance().closeDialog(this);
            cicloEjb.create(ciclo);
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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciclo registrada", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            this.confuguracionselected = this.configuracionEjb.find(confuguracionselected.getConfiguracionId());
            ciclo.setConfiguracion(confuguracionselected);
            cicloEjb.edit(ciclo);
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciclo Editado Exitosamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarCiclo').hide()");
            inicioPagina();
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarCiclo(int cicloId){
        try {
            this.ciclo = this.cicloEjb.find(cicloId);
            this.confuguracionselected = this.configuracionEjb.find(ciclo.getConfiguracion().getConfiguracionId());
            RequestContext.getCurrentInstance().update("frmEditarCiclo:panelEditarCiclo");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarCiclo').show()");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newCiclo(){
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newciclo",options,null);
    }
     public void eliminarCiclo(Ciclo cicloremove) {
        try {
            //this.escala = this.escalaEjb.find(escalaid);
            System.out.println("ELIMINAR CRITERIO :"+cicloremove);
            if(cicloEjb.removeById(cicloremove)==true){
                //inicioPagina();
                //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala","eliminada"));
            }else{
                //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Escala","esta escala esta en uso"));
            }
            inicioPagina();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
