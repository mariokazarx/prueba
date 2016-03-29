/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
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
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    
    public mbvCiclo() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
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
        this.consultar=false;
        this.editar=false;
        this.eliminar=false;
        this.crear=false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
        }
        if(this.usr!=null){
            for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==5){
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
    }
    
    
    public void insertar(){
        try{
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.crear){
                if(anlectivoEjb.configuracionEnUso(confuguracionselected)){
                    FacesContext.getCurrentInstance().
                            addMessage("frmCrearCiclo:stlConfiguracion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Configuracion esta en uso"));
                    return;
                }
                this.confuguracionselected = configuracionEjb.find(confuguracionselected.getConfiguracionId());
                for(Ciclo auxciclo:cicloEjb.getByConfiguracion(confuguracionselected)){
                    if(ciclo.getNumero() == auxciclo.getNumero()){//&& area.getAreaId()!= auxarea.getAreaId()
                        System.out.println("IGUALLLL");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Numero de ciclo en uso"); 
                        FacesContext.getCurrentInstance().addMessage("frmCrearCiclo:txtNumero", message);
                        return;
                    }
                }
                ciclo.setConfiguracion(confuguracionselected);
                RequestContext.getCurrentInstance().closeDialog(this);
                cicloEjb.create(ciclo);
                FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignatura creada Satisfactoriamente", ""));
                inicioPagina();
            }
            else{
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
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
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                this.confuguracionselected = this.configuracionEjb.find(confuguracionselected.getConfiguracionId());
                if(anlectivoEjb.configuracionEnUso(confuguracionselected)){
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarCiclo:stlConfiguracion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Configuracion esta en uso"));
                    return;
                }
                this.confuguracionselected = configuracionEjb.find(confuguracionselected.getConfiguracionId());
                for(Ciclo auxciclo:cicloEjb.getByConfiguracion(confuguracionselected)){
                    if(ciclo.getNumero() == auxciclo.getNumero() && ciclo.getCicloId() != auxciclo.getCicloId()){//&& area.getAreaId()!= auxarea.getAreaId()
                        System.out.println("IGUALLLL");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Numero de ciclo en uso"); 
                        FacesContext.getCurrentInstance().addMessage("frmEditarCiclo:txtNumero", message);
                        return;
                    }
                }
                ciclo.setConfiguracion(confuguracionselected);
                cicloEjb.edit(ciclo);
                FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciclo Editado Exitosamente", ""));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarCiclo').hide()");
                inicioPagina();
            }
            else{
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void cargarCiclo(int cicloId){
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                this.ciclo = this.cicloEjb.find(cicloId);
                Configuracion auxcfg = configuracionEjb.find(this.ciclo.getConfiguracion().getConfiguracionId());
                if(!anlectivoEjb.configuracionEnUso(auxcfg)){
                    this.confuguracionselected = this.configuracionEjb.find(ciclo.getConfiguracion().getConfiguracionId());
                    RequestContext.getCurrentInstance().update("frmEditarCiclo:panelEditarCiclo");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarCiclo').show()");
                }else{
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()");
                }
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void newCiclo(){
        if(!login){
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if(this.crear){
            Map<String,Object> options = new HashMap<String, Object>();
            /*options.put("contentHeight", 340);
            options.put("height", 400);
            options.put("width",700);*/
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newciclo",options,null);
        }
        else{
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }
     public void eliminarCiclo(Ciclo cicloremove) {
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.eliminar){
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
            }
            else{
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
     public void initRender(){
        if(!this.consultar){
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluacion"));
        }
    }
}
