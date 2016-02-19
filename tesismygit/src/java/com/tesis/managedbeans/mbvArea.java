/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AreaFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Area;
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
public class mbvArea implements Serializable{

    private Area area;
    private List<Area> areas;
    private Usuario usr;
    private boolean login;
    private Configuracion confselected;
    private List<Configuracion> configs;
    @EJB
    private AreaFacade areaEJB;
    @EJB
    private ConfiguracionFacade confEJB;
    @EJB
    private AnlectivoFacade añoEJB;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;

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
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        login=false;
        this.area=new Area();
        this.areas=this.areaEJB.findAll();
        this.configs = this.confEJB.findAll();
        this.confselected = new Configuracion();
    }
    public void insertar(){
        try{
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==2){
                        if(usrRol.getRoleId().getAgregar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                Configuracion auxcfg = confEJB.find(confselected.getConfiguracionId());
                for(Area auxarea:auxcfg.getAreaList()){
                    System.out.print(auxarea.getNombre()+"la otra"+ area.getNombre());
                    if(area.getNombre().trim().equals(auxarea.getNombre().trim())){
                        System.out.println("IGUALLLL");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Nombre en uso para esta configuracion"); 
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return;
                    }
                }
                area.setConfiguracionId(confselected);
                RequestContext.getCurrentInstance().closeDialog(this);
                areaEJB.create(area);
                FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Area creada Satisfactoriamente", ""));
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
    public void actualizar(){
        try{
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==2){
                        if(usrRol.getRoleId().getEditar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                this.confselected = this.confEJB.find(confselected.getConfiguracionId());
                area.setConfiguracionId(confselected);
                areaEJB.edit(area);
                FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
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
    public void cargarArea(int areaId){
        try {
            
            this.area = this.areaEJB.find(areaId);
            Configuracion auxcfg = confEJB.find(this.area.getConfiguracionId().getConfiguracionId());
            if(añoEJB.existeConfiguracionEnUso(auxcfg)){
                System.out.println("En Uso");
                return;
            }
            this.confselected = this.confEJB.find(area.getConfiguracionId().getConfiguracionId());
            RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Area Registrada", "exitosamente"); 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public void newArea(){
        System.out.println("Hata AQUI!!");
        Map<String,Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
        options.put("height", 400);
        options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newarea",options,null);
    }
     public void eliminarArea(Area area) {
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==2){
                        if(usrRol.getRoleId().getEliminar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                //this.escala = this.escalaEjb.find(escalaid);
                //System.out.println("ELIMINAR CRITERIO :"+criterioeval);
                if(areaEJB.removeById(area)==true){
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
}
