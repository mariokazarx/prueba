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
public class mbvConfiguracion implements Serializable {

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
    private String nomoriginal;
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
    public void inicioPagina() {
        this.nomoriginal = "";
        this.configuracion = new Configuracion();
        this.configuraciones = this.configuracionEjb.findAll();
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
    public void insertar() {
        try {
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
            RequestContext.getCurrentInstance().closeDialog(this);
            //criterioeval.setFormacriterioevaluacionId(fcriterioselected);
            //RequestContext.getCurrentInstance().closeDialog(this);
            //criterioevalEjb.create(criterioeval);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
            inicioPagina();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void validateNombreUnique(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        // this.mensage=false;
        if (this.configuracionEjb.getByNombre(arg2.toString()) == false) {
            throw new ValidatorException(new FacesMessage("ya existe este nombre"));
        }
    }

    public void validateNombreUniqueEditar(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        // this.mensage=false;
        if (!this.nomoriginal.equals(arg2.toString())) {
            if (this.configuracionEjb.getByNombre(arg2.toString()) == false) {
                throw new ValidatorException(new FacesMessage("ya existe este nombre"));
            }
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala Registrada", "exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            this.criterioevaluacionselected = this.criterioevalEjb.find(criterioevaluacionselected.getCriterioevaluacionId());
            this.escalaselected = this.escalaEjb.find(escalaselected.getEscalaId());
            this.configuracion.setCriterioevaluacionId(criterioevaluacionselected);
            this.configuracion.setEscalaId(escalaselected);
            this.configuracionEjb.edit(this.configuracion);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
            inicioPagina();
        } catch (Exception e) {
            System.out.print("fail" + e.getMessage());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void cargarConfiguracion(int configuracionId) {
        try {
            this.configuracion = this.configuracionEjb.find(configuracionId);
            this.criterioevaluacionselected = this.criterioevalEjb.find(configuracion.getCriterioevaluacionId().getCriterioevaluacionId());
            this.escalaselected = this.escalaEjb.find(configuracion.getEscalaId().getEscalaId());
            this.nomoriginal = this.configuracion.getNombre();
            if (configuracionEjb.enUso(configuracion)) {
                RequestContext.getCurrentInstance().execute("PF('dlg2').show()");
            } else {
                RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void newConfig() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentHeight", 430);
        options.put("contentWidth", 560);
        //options.put("height", 400);
        //options.put("width",700);
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newconf", options, null);
    }

    public void eliminarConfiguracion(Configuracion configuracion) {
        try {
            //this.escala = this.escalaEjb.find(escalaid);
            System.out.println("ELIMINAR CRITERIO :" + configuracion);
            if (configuracionEjb.removeById(configuracion) == true) {
                //inicioPagina();
                //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala", "eliminada"));
            } else {
                //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Escala", "esta escala esta en uso"));
            }
            inicioPagina();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
