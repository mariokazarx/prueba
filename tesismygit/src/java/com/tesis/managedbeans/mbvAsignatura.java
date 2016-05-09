/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AreaFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Area;
import com.tesis.entity.Asignatura;
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
import org.primefaces.context.RequestContext;;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvAsignatura implements Serializable{
    private static final long serialVersionUID = -3593970165471501041L;

    /**
     * Creates a new instance of mbvAsignatura
     */
    private Usuario usr;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Asignatura asignatura;
    private List<Asignatura> asignaturas;
    private List<Area> areas;
    private Area areaselected;
    private List<Configuracion> configuraciones;
    private Configuracion confuguracionselected;
    
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private AreaFacade areaEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;

    public mbvAsignatura() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }
    
    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
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
    
    @PostConstruct
    public void inicioPagina(){
        this.consultar=false;
        this.editar=false;
        this.eliminar=false;
        this.crear=false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if(this.usr!=null){
            for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==3){
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
        this.asignatura=new Asignatura();
        this.asignaturas=  this.asignaturaEjb.findAll();
        //this.areas = this.areaEjb.findAll();
        this.areaselected = new Area();
        this.configuraciones = this.configuracionEjb.findAll();
        this.confuguracionselected = new Configuracion();
    }
    
    
    public void insertar(){
        try{
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.crear){
                if(anlectivoEjb.configuracionEnUso(confuguracionselected)){
                    FacesContext.getCurrentInstance().
                            addMessage("frmCrearrAsignatura:stlConfiguracion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta configuración esta en uso"));
                    return;
                }
                this.confuguracionselected = configuracionEjb.find(confuguracionselected.getConfiguracionId());
                for(Asignatura auxasig:asignaturaEjb.findByConfiguracionNew(confuguracionselected)){
                    if(asignatura.getNombre().trim().equals(auxasig.getNombre().trim())){//&& area.getAreaId()!= auxarea.getAreaId()
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Nombre en uso para esta configuración"); 
                        FacesContext.getCurrentInstance().addMessage("frmCrearrAsignatura:txtNombre", message);
                        return;
                    }
                }
                asignatura.setAreaId(areaselected);
                asignatura.setConfiguracionId(confuguracionselected);
                RequestContext.getCurrentInstance().closeDialog(this);
                asignaturaEjb.create(asignatura);
                FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignatura creada satisfactoriamente"));
                inicioPagina();
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignatura registrada"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar(){
        try{
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                this.areaselected = this.areaEjb.find(areaselected.getAreaId());
                this.confuguracionselected = this.configuracionEjb.find(confuguracionselected.getConfiguracionId());
                if(anlectivoEjb.configuracionEnUso(confuguracionselected)){
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarAsignatura:stlConfiguracion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta configuración esta en uso"));
                    return;
                }
                this.confuguracionselected = configuracionEjb.find(confuguracionselected.getConfiguracionId());
                for(Asignatura auxasig:asignaturaEjb.findByConfiguracionNew(confuguracionselected)){
                    if(asignatura.getNombre().trim().equals(auxasig.getNombre().trim()) && asignatura.getAsignaturaId()!= auxasig.getAsignaturaId()){
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Nombre en uso para esta configuración"); 
                        FacesContext.getCurrentInstance().addMessage("frmEditarAsignatura:txtNombre", message);
                        return;
                    }
                }
                asignatura.setAreaId(areaselected);
                asignatura.setConfiguracionId(confuguracionselected);
                asignaturaEjb.edit(asignatura);
                FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignatura editada"));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarAsignatura').hide()");
                inicioPagina();
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        }catch(Exception e){
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }
    public void cargarAreas(){
        this.confuguracionselected = this.configuracionEjb.find(this.confuguracionselected.getConfiguracionId());
        this.areas = this.areaEjb.getByConfiguracion(confuguracionselected);
    }
    public void cargarAsignatura(int asignaturaid){
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                this.asignatura =  this.asignaturaEjb.find(asignaturaid);
                Configuracion auxcfg = configuracionEjb.find(this.asignatura.getConfiguracionId().getConfiguracionId());
                if(!anlectivoEjb.configuracionEnUso(auxcfg)){
                    this.areaselected = this.areaEjb.find(asignatura.getAreaId().getAreaId());
                    this.confuguracionselected = this.configuracionEjb.find(asignatura.getConfiguracionId().getConfiguracionId());
                    this.areas = areaEjb.getByConfiguracion(this.confuguracionselected);//this.confuguracionselected.getAreaList()
                    RequestContext.getCurrentInstance().update("frmEditarAsignatura:panelEditarAsignatura");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarAsignatura').show()");
                }
                else{
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()");
                }
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }
    public void newAsignatura(){
        if(!login){
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if(this.crear){
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
        else{
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }
     public void eliminarAsignatura(Asignatura asignatura) {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.eliminar){
                //this.escala = this.escalaEjb.find(escalaid);
               //System.out.println("ELIMINAR CRITERIO :"+criterioeval);
                if(asignaturaEjb.removeById(asignatura)==true){
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito","Asignatura eliminada"));
                }else{
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia","esta asignatura esta en uso"));
                }
                inicioPagina();
            }
            else{
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
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar asignaturas"));
        }
    }
}
