/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.EscalaFacade;
import com.tesis.entity.Escala;
import java.io.Serializable;
import java.util.ArrayList;
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
public class mbvEscala implements Serializable {

    private Escala escala;
    private List<Escala> escalas;

    @EJB
    private EscalaFacade escalaEjb;
    public mbvEscala() {
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
    public void inicioPagina(){
        this.escala=new Escala();
        this.escalas=this.escalaEjb.findAllOrder();
    }
    
    public void insertar(){
        try{
            escalaEjb.create(escala);
            RequestContext.getCurrentInstance().closeDialog(this);
            System.out.print("error");
          //  FacesContext.getCurrentInstance().
           //             addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            //nicioPagina();
        }catch(Exception e){
            System.out.print("error");
            // FacesContext.getCurrentInstance().
              //          addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala Registrada", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            System.out.print("ok"+escala.getDescripcion());
            escalaEjb.edit(escala);
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
            inicioPagina();
        }catch(Exception e){
            System.out.print("fail"+e.getMessage());
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarEscala(int escalaid){
        try {
            this.escala =  this.escalaEjb.find(escalaid);
            RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newEscala(){
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newescala",options,null);
    }
     
    
}
