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
    private static final long serialVersionUID = -8248477580423713654L;

    private Escala escala;
    private Usuario usr;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
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

    public boolean isCrear() {
        return crear;
    }

    public boolean isConsultar() {
        return consultar;
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
        this.consultar=false;
        this.editar=false;
        this.eliminar=false;
        this.crear=false;
        this.escalas = this.escalaEjb.findAllOrder();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if(this.usr!=null){
            for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==6){
                    if(usrRol.getRoleId().getAgregar()){
                        this.crear=true;
                    }
                    if(usrRol.getRoleId().getConsultar()){
                        this.consultar=true;
                    }
                    if(usrRol.getRoleId().getEditar()){
                        this.editar=true;
                    }
                    if(usrRol.getRoleId().getEliminar()){
                        this.eliminar=true;
                    }
                }
            }
        }
        if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
            this.consultar=true;
            this.editar=true;
            this.eliminar=true;
            this.crear=true;
        }
        //this.escalasFiltradas = this.escalaEjb.findAllOrder();
    }
    //validator="#{mbvEscala.validateNombreUnique}"

    public void validateMax(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        HtmlInputText maxText = (HtmlInputText) arg1.findComponent("txtMax");
        if (12 <= escala.getMin()) {
            throw new ValidatorException(new FacesMessage("Debe ser mayor que nota mínima"));
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
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.crear){
                if (escala.getMax() <= escala.getMin()) {
                    this.mensage = false;
                    FacesContext.getCurrentInstance().
                            addMessage("frmescala:txtMax", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "debe ser mayor a nota mínima"));
                    //this.mensage=false;
                    return;
                }
                if (escala.getNotaminimaaprob() <= escala.getMin() || escala.getNotaminimaaprob() >= escala.getMax()) {
                    this.mensage = false;
                    FacesContext.getCurrentInstance().
                            addMessage("frmescala:txtMinaprob", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota mínima aprobación debe ser superior a nota mínima e inferior a máxima"));
                    //this.mensage=false;
                    return;
                }
                escalaEjb.create(escala);
                RequestContext.getCurrentInstance().closeDialog(this);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Escala creada Satisfactoriamente"));
                inicioPagina();
            }else{
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Escala registrada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            if(!login){
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                if (escala.getMax() <= escala.getMin()) {
                    this.mensage = true;
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota máxima debe ser mayor a nota mínima"));
                    //this.mensage=false;
                    return;
                }
                if (escala.getNotaminimaaprob() <= escala.getMin() || escala.getNotaminimaaprob() >= escala.getMax()) {
                    this.mensage = true;
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nota mínima aprobación debe ser superior a nota mínima e inferior a máxima"));
                    //this.mensage=false;
                    return;
                }
                escalaEjb.edit(escala);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Escala editada satisfactoriamente"));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
                inicioPagina();
            }
            else{
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void cargarEscala(int escalaid) {
        try {
            if(!login){
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                this.escala = this.escalaEjb.find(escalaid);
                if(!anlectivoEjb.escalaEnUso(escala)){
                    this.nomoriginal = this.escala.getNombre();
                    RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
                }else{
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()"); 
                }
            }else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void newEscala() {
        if(!login){
            this.mensage = true;
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if(this.crear){
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentHeight", 340);
            options.put("height", 400);
            options.put("width", 700);
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newescala", options, null);
        }else{
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }
    public void eliminarEscala(Escala escalaid) {
        try {
            if(!login){
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.eliminar){
                //this.escala = this.escalaEjb.find(escalaid);
                if(escalaEjb.removeById(escalaid)==true){
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito","Escala eliminada"));
                }else{
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia","esta escala está en uso"));
                }
                inicioPagina();
            }
            else{
                this.mensage = true;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }
    public void initRender(){
        if(!this.consultar){
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar escalas"));
        }
    }
}
