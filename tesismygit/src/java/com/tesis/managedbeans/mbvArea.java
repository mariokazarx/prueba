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
public class mbvArea implements Serializable {
    private static final long serialVersionUID = 2420808426741271422L;

    private Area area;
    private List<Area> areas;
    private Usuario usr;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
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
    @EJB
    private AnlectivoFacade anlectivoEjb;

    /**
     * Creates a new instance of mbvArea
     */
    public mbvArea() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
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
    public void inicioPagina() {
        this.consultar = false;
        this.editar = false;
        this.eliminar = false;
        this.crear = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 2) {
                    if (usrRol.getRoleId().getAgregar()) {
                        this.crear = true;
                    }
                    if (usrRol.getRoleId().getConsultar()) {
                        this.consultar = true;
                    }
                    if (usrRol.getRoleId().getEditar()) {
                        this.editar = true;
                    }
                    if (usrRol.getRoleId().getEliminar()) {
                        this.eliminar = true;
                    }
                }
            }
        }
        if (this.usr.getTipoUsuarioId().getTipoUsuarioId() == 4) {
            this.consultar = true;
            this.editar = true;
            this.eliminar = true;
            this.crear = true;
        }
        this.area = new Area();
        this.areas = this.areaEJB.findAll();
        this.configs = this.confEJB.findAll();
        this.confselected = new Configuracion();
    }

    public void insertar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.crear) {
                Configuracion auxcfg = confEJB.find(confselected.getConfiguracionId());
                if (añoEJB.configuracionEnUso(auxcfg)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmcriterioeval:stlCriterio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta área esta en uso"));
                    return;
                }
                for (Area auxarea : areaEJB.getByConfiguracion(auxcfg)) {//auxcfg.getAreaList()
                    if (area.getNombre().trim().equals(auxarea.getNombre().trim())) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Nombre en uso para esta configuración");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return;
                    }
                }
                area.setConfiguracionId(confselected);
                RequestContext.getCurrentInstance().closeDialog(this);
                areaEJB.create(area);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Área creada satisfactoriamente"));
                inicioPagina();
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void actualizar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                this.confselected = this.confEJB.find(confselected.getConfiguracionId());
                if (añoEJB.configuracionEnUso(confselected)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarEscala:stlConfiguracion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta área esta en uso"));
                    return;
                }
                for (Area auxarea : areaEJB.getByConfiguracion(confselected)) {//confselected.getAreaList()
                    if (area.getNombre().trim().equals(auxarea.getNombre().trim()) && area.getAreaId() != auxarea.getAreaId()) {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia ", "Nombre en uso para esta configuración");
                        FacesContext.getCurrentInstance().addMessage("frmEditarEscala:txtNombre", message);
                        return;
                    }
                }
                area.setConfiguracionId(confselected);
                areaEJB.edit(area);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Área editada satisfactoriamente"));
                RequestContext.getCurrentInstance().execute("PF('tablaAreas').clearFilters()");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').hide()");
                inicioPagina();
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void cargarArea(int areaId) {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                this.area = this.areaEJB.find(areaId);
                Configuracion auxcfg = confEJB.find(this.area.getConfiguracionId().getConfiguracionId());
                if (añoEJB.configuracionEnUso(auxcfg)) {
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()");
                } else {
                    this.confselected = this.confEJB.find(area.getConfiguracionId().getConfiguracionId());
                    RequestContext.getCurrentInstance().update("frmEditarEscala:panelEditarEscala");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarEscala').show()");
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void closeDialog() {
        RequestContext.getCurrentInstance().execute("PF('tablaAreas').clearFilters()");
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Área registrada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newArea() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if (this.crear) {
            Map<String, Object> options = new HashMap<String, Object>();
            /*options.put("contentHeight", 340);
             options.put("height", 400);
             options.put("width",700);*/
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newarea", options, null);
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }

    public void eliminarArea(Area area) {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.eliminar) {
                //this.escala = this.escalaEjb.find(escalaid);
                //System.out.println("ELIMINAR CRITERIO :"+criterioeval);
                if (areaEJB.removeById(area) == true) {
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Área eliminada"));
                } else {
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "esta área esta en uso"));
                }
                RequestContext.getCurrentInstance().execute("PF('tablaAreas').clearFilters()");
                inicioPagina();
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar Áreas"));
        }
    }
}
