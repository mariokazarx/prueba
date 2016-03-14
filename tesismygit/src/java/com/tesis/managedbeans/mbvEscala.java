/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Escala;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
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
    private Usuario usr;
    private boolean login;
    private List<Escala> escalas;
    private List<Escala> escalasFiltradas;
    boolean mensage = false;
    private String nomoriginal;
    @EJB
    private EscalaFacade escalaEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    
    public mbvEscala() {
    }

    public List<Escala> getEscalasFiltradas() {
        return escalasFiltradas;
    }

    public void setEscalasFiltradas(List<Escala> escalasFiltradas) {
        this.escalasFiltradas = escalasFiltradas;
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
        login=false;
        this.escalas = this.escalaEjb.findAllOrder();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //this.escalasFiltradas = this.escalaEjb.findAllOrder();
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
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==6){
                        if(usrRol.getRoleId().getAgregar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
                permiso=true;
            }
            if(permiso){
                System.out.println("Puede insertar");
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
                            addMessage("frmescala:txtMinaprob", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota minima aprobacion debe ser superior a la nota minima e inferior a la maxima"));
                    //this.mensage=false;
                    return;
                }
                escalaEjb.create(escala);
                RequestContext.getCurrentInstance().closeDialog(this);
                System.out.print("error");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
                inicioPagina();
            }else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
            
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
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==6){
                        if(usrRol.getRoleId().getEditar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
                permiso=true;
            }
            if(permiso){
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
            }
            else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
            
        } catch (Exception e) {
            System.out.print("fail" + e.getMessage());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void cargarEscala(int escalaid) {
        try {
            this.escala = this.escalaEjb.find(escalaid);
            System.out.println("En uso "+anlectivoEjb.escalaEnUso(escala));
            if(!anlectivoEjb.escalaEnUso(escala)){
                this.nomoriginal = this.escala.getNombre();
                RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
            }else{
                RequestContext.getCurrentInstance().execute("PF('enUso').show()"); 
            }
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
    public void eliminarEscala(Escala escalaid) {
        try {
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==6){
                        if(usrRol.getRoleId().getEliminar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
                permiso=true;
            }
            if(permiso){
                //this.escala = this.escalaEjb.find(escalaid);
                System.out.println("ELIMINAR ESCALA :"+escalaid);
                if(escalaEjb.removeById(escalaid)==true){
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
            }
            else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
