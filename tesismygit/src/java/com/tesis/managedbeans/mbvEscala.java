/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.EscalaFacade;
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
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvEscala implements Serializable {

    private Escala escala;
    private List<Escala> escalas;
    boolean mensage = false;
    private String nomoriginal;
    @EJB
    private EscalaFacade escalaEjb;

    public mbvEscala() {
    }

    public boolean isMensage() {
        return mensage;
    }

    public void setMensage(boolean mensage) {
        this.mensage = mensage;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public EscalaFacade getEscalaEjb() {
        return escalaEjb;
    }

    public void setEscalaEjb(EscalaFacade escalaEjb) {
        this.escalaEjb = escalaEjb;
    }

    public List<Escala> getEscalas() {
        return escalas;
    }

    public void setEscalas(List<Escala> escalas) {
        this.escalas = escalas;
    }

    @PostConstruct
    public void inicioPagina() {
        this.escala = new Escala();
        nomoriginal = "";
        this.escalas = this.escalaEjb.findAllOrder();
    }
    //validator="#{mbvEscala.validateNombreUnique}"

    public void validateMax(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        HtmlInputText maxText = (HtmlInputText) arg1.findComponent("txtMax");
        System.out.print("min" + maxText.getLocalValue() + "max" + arg2);
        if (12 <= escala.getMin()) {
            throw new ValidatorException(new FacesMessage("Debe ser mayor que nota minima"));
        }
    }

    public void validateNombreUnique(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        //this.escalaEjb.getByNombre(arg2.toString());
        this.mensage = false;
        if (this.escalaEjb.getByNombre(arg2.toString()) == false) {
            throw new ValidatorException(new FacesMessage("ya existe una escala con este nombre"));
        }
    }

    public void validateNombreUniqueEditar(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        //this.escalaEjb.getByNombre(arg2.toString());
        this.mensage = false;
        if (!this.nomoriginal.equals(arg2.toString())) {
            if (this.escalaEjb.getByNombre(arg2.toString()) == false) {
                throw new ValidatorException(new FacesMessage("ya existe una escala con este nombre"));
            }
        }
    }

    public void insertar() {
        try {
            if (escala.getMax() <= escala.getMin()) {
                this.mensage = true;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota maxima debe ser mayor a nota minima"));
                //this.mensage=false;
                return;
            }
            if (escala.getNotaminimaaprob() <= escala.getMin() || escala.getNotaminimaaprob() >= escala.getMax()) {
                this.mensage = true;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota minima aprobacion debe ser superior a la nota minima e inferior a la maxima"));
                //this.mensage=false;
                return;
            }
            escalaEjb.create(escala);
            RequestContext.getCurrentInstance().closeDialog(this);
            System.out.print("error");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            inicioPagina();
        } catch (Exception e) {
            System.out.print("error");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala Registrada", "exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            if (escala.getMax() <= escala.getMin()) {
                this.mensage = true;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota maxima debe ser mayor a nota minima"));
                //this.mensage=false;
                return;
            }
            if (escala.getNotaminimaaprob() <= escala.getMin() || escala.getNotaminimaaprob() >= escala.getMax()) {
                this.mensage = true;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota minima aprobacion debe ser superior a la nota minima e inferior a la maxima"));
                //this.mensage=false;
                return;
            }
            escalaEjb.edit(escala);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala editada Satisfactoriamente", ""));
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
            inicioPagina();
        } catch (Exception e) {
            System.out.print("fail" + e.getMessage());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void cargarEscala(int escalaid) {
        try {
            this.escala = this.escalaEjb.find(escalaid);
            this.nomoriginal = this.escala.getNombre();
            RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void newEscala() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width", 700);
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newescala", options, null);
    }
}
