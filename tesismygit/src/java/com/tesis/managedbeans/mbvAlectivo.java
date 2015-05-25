
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.EstadoAniolectivoFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.EstadoAniolectivo;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

@ManagedBean
@ViewScoped
public class mbvAlectivo implements Serializable  {
    
    private Anlectivo anlectivo;
    private Configuracion configuracionselected;
    private EstadoAniolectivo estadoAlectivoselected;
    private List<EstadoAniolectivo> estadosAlectivos;
    private List<Configuracion> configuraciones;
    private List<Anlectivo> anlectivos;
    private Integer año;  
    private List<Integer> años;
    private String copia;
 
    boolean mensage=false;
    
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private EstadoAniolectivoFacade estadoAlectivoEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    
    public mbvAlectivo() {
    }

    public String getCopia() {
        return copia;
    }

    public void setCopia(String copia) {
        this.copia = copia;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public List<Integer> getAños() {
        return años;
    }

    public void setAños(List<Integer> años) {
        this.años = años;
    }

    public boolean isMensage() {
        return mensage;
    }

    public void setMensage(boolean mensage) {
        this.mensage = mensage;
    }
    
    public Anlectivo getAnlectivo() {
        return anlectivo;
    }

    public void setAnlectivo(Anlectivo anlectivo) {
        this.anlectivo = anlectivo;
    }

    public Configuracion getConfiguracionselected() {
        return configuracionselected;
    }

    public void setConfiguracionselected(Configuracion configuracionselected) {
        this.configuracionselected = configuracionselected;
    }

    public EstadoAniolectivo getEstadoAlectivoselected() {
        return estadoAlectivoselected;
    }

    public void setEstadoAlectivoselected(EstadoAniolectivo estadoAlectivoselected) {
        this.estadoAlectivoselected = estadoAlectivoselected;
    }

    public List<EstadoAniolectivo> getEstadosAlectivos() {
        return estadosAlectivos;
    }

    public void setEstadosAlectivos(List<EstadoAniolectivo> estadosAlectivos) {
        this.estadosAlectivos = estadosAlectivos;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public AnlectivoFacade getAnlectivoEjb() {
        return anlectivoEjb;
    }

    public void setAnlectivoEjb(AnlectivoFacade anlectivoEjb) {
        this.anlectivoEjb = anlectivoEjb;
    }

    public EstadoAniolectivoFacade getEstadoAlectivoEjb() {
        return estadoAlectivoEjb;
    }

    public void setEstadoAlectivoEjb(EstadoAniolectivoFacade estadoAlectivoEjb) {
        this.estadoAlectivoEjb = estadoAlectivoEjb;
    }

    public ConfiguracionFacade getConfiguracionEjb() {
        return configuracionEjb;
    }

    public void setConfiguracionEjb(ConfiguracionFacade configuracionEjb) {
        this.configuracionEjb = configuracionEjb;
    }
    
    @PostConstruct
    public void inicioPagina(){
        this.anlectivo = new Anlectivo();
        this.configuracionselected = new Configuracion();
        this.estadoAlectivoselected = new EstadoAniolectivo();
        this.anlectivos = this.anlectivoEjb.findAll();
        this.configuraciones = this.configuracionEjb.findAll();
        this.estadosAlectivos = this.estadoAlectivoEjb.findAll();
        Calendar fecha = new GregorianCalendar();
        años = new ArrayList<Integer>();
        int a = fecha.get(Calendar.YEAR);
        for(int i = a;i>2000;i--){
            System.out.println("mmm"+i);
            años.add(i);
        }
        //this.criterioeval.setFormacriterioevaluacionId(fcriterioselected);
        //this.dialogEdit=false;
    }
    public void insertar(){
        try{
           inicioPagina();
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Año Registrado", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            inicioPagina();
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarAnlectivo(int anlectivoId){
        try {
           
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newAnlectivo(){
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newAnlectivo",options,null);
    }

}
