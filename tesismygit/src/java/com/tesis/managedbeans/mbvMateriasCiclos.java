/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvMateriasCiclos implements Serializable{
    private static final long serialVersionUID = 1583259057567607778L;
    private Configuracion configuracionSelected;
    private List<Configuracion> configuraciones;
    private Ciclo cicloselected;
    private List<Ciclo> ciclosSlected;
    private List<Asignatura> asignturasdisponibles;
    private List<Asignatura> asignturasSelecionadas;
    private DualListModel<Asignatura> coso;
    private Asignaturaciclo asigCiclo;
    private Asignatura asg;
    private boolean banAsig;
    private Ciclo cicloTabla;
    private boolean mostrarContenido;
    private boolean mostrarEditar;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private ConfiguracionFacade configuracionEJB;
    @EJB
    private CicloFacade cicloEJB;
    @EJB
    private AsignaturaFacade asignaturaEJB;
    @EJB
    private AsignaturacicloFacade asigCicloaEJB;
    @EJB
    private AnlectivoFacade anlectivoEJB;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    
    public mbvMateriasCiclos() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isEditar() {
        return editar;
    }

    public boolean isMostrarContenido() {
        return mostrarContenido;
    }

    public void setMostrarContenido(boolean mostrarContenido) {
        this.mostrarContenido = mostrarContenido;
    }

    public boolean isMostrarEditar() {
        return mostrarEditar;
    }

    public void setMostrarEditar(boolean mostrarEditar) {
        this.mostrarEditar = mostrarEditar;
    }

    public Ciclo getCicloTabla() {
        return cicloTabla;
    }

    public void setCicloTabla(Ciclo cicloTabla) {
        this.cicloTabla = cicloTabla;
    }

    public List<Asignatura> getAsignturasSelecionadas() {
        return asignturasSelecionadas;
    }

    public void setAsignturasSelecionadas(List<Asignatura> asignturasSelecionadas) {
        this.asignturasSelecionadas = asignturasSelecionadas;
    }

    public Asignaturaciclo getAsigCiclo() {
        return asigCiclo;
    }

    public void setAsigCiclo(Asignaturaciclo asigCiclo) {
        this.asigCiclo = asigCiclo;
    }

    public Asignatura getAsg() {
        return asg;
    }

    public void setAsg(Asignatura asg) {
        this.asg = asg;
    }
    
    public boolean isBanAsig() {
        return banAsig;
    }

    public void setBanAsig(boolean banAsig) {
        this.banAsig = banAsig;
    }
    
    
    public List<Asignatura> getAsignturasdisponibles() {
        return asignturasdisponibles;
    }

    public void setAsignturasdisponibles(List<Asignatura> asignturasdisponibles) {
        this.asignturasdisponibles = asignturasdisponibles;
    }

    public DualListModel<Asignatura> getCoso() {
        return coso;
    }

    public void setCoso(DualListModel<Asignatura> coso) {
        this.coso = coso;
    }
  
    public Configuracion getConfiguracionSelected() {
        return configuracionSelected;
    }

    public void setConfiguracionSelected(Configuracion configuracionSelected) {
        this.configuracionSelected = configuracionSelected;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public Ciclo getCicloselected() {
        return cicloselected;
    }

    public void setCicloselected(Ciclo cicloselected) {
        this.cicloselected = cicloselected;
    }

    public List<Ciclo> getCiclosSlected() {
        return ciclosSlected;
    }

    public void setCiclosSlected(List<Ciclo> ciclosSlected) {
        this.ciclosSlected = ciclosSlected;
    }

    @PostConstruct
    public void inicio(){
        this.configuracionSelected = new Configuracion();
        this.cicloselected = new Ciclo();
        this.asigCiclo = new Asignaturaciclo();
        this.configuraciones = this.configuracionEJB.findAll();
        /*this.asignturasdisponibles = this.asignaturaEJB.findAll();
        this.coso = new DualListModel<Asignatura>(asignturasdisponibles,asignturasdisponibles);*/
        this.banAsig = false;
        this.mostrarContenido = false;
        this.mostrarEditar = false;
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
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==8){
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
    public void cargarCiclo(){
        try {
            if(configuracionSelected.getConfiguracionId()!=null){
                this.mostrarContenido=false;
                this.mostrarEditar=false;
                //System.out.println(configuracionSelected.getConfiguracionId());
                this.ciclosSlected = cicloEJB.getByConfiguracion(configuracionSelected);
                if(this.ciclosSlected.isEmpty()==true){
                    this.banAsig=false;
                }
                Configuracion auxcfg = configuracionEJB.find(configuracionSelected.getConfiguracionId());
                if(anlectivoEJB.configuracionEnUso(auxcfg)){
                    //en uso
                }else{
                    //no en uso
                }
            }else{
                this.cicloselected = new Ciclo();
                this.mostrarContenido=false;
                this.mostrarEditar=false;
                this.ciclosSlected.clear();
            }
        } catch (Exception e) {
            this.banAsig=false;
            this.mostrarContenido=false;
            this.mostrarEditar=false;
        }    
    }
    public void cargarAsignaturas(){
        try {
            if(cicloselected.getCicloId()!=null){
                this.configuracionSelected = this.configuracionEJB.find(configuracionSelected.getConfiguracionId());
                if(anlectivoEJB.configuracionEnUso(configuracionSelected)){
                    this.mostrarContenido = true;
                    this.mostrarEditar = false;
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Configuración esta en uso"));
                }else{
                    this.mostrarEditar = true;
                    this.mostrarContenido = true;
                    if(!this.editar){
                        FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
                    }
                }
                cicloTabla = cicloEJB.find(cicloselected.getCicloId());
                this.banAsig = true;
                this.configuraciones = this.configuracionEJB.findAll();
                this.asignturasdisponibles = this.asignaturaEJB.findByConfiguracion(configuracionSelected, cicloselected);
                this.asignturasSelecionadas = this.asignaturaEJB.findByCiclo(cicloselected);
                this.coso = new DualListModel<Asignatura>(asignturasdisponibles,asignturasSelecionadas);
            }else{
                this.mostrarContenido=false;
                this.mostrarEditar=false;
            }
        } catch (Exception e) {
            this.banAsig=false;
            this.mostrarContenido=false;
            this.mostrarEditar=false;
        }
        
    }
    public void onTransfer(TransferEvent event) {
        try {
            if(!login){
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if(this.editar){
                //isremove de izq a derecha isadd de derecha a izq
                for(Object item : event.getItems()) {
                    //builder.append(((Theme) item).getName()).append("<br />");
                    if(event.isRemove()){
                        asg = asignaturaEJB.find(Integer.parseInt(item.toString())); 
                        asigCicloaEJB.removeByAsignatura(asg);
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Asignatura removida"));
                    }
                    if(event.isAdd()){
                        asg = asignaturaEJB.find(Integer.parseInt(item.toString())); 
                        asigCiclo.setAsignaturaId(asg);
                        asigCiclo.setCicloId(cicloselected);
                        asigCicloaEJB.create(asigCiclo);
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Asignatura agregada"));
                    }
                }
                this.asignturasSelecionadas = this.asignaturaEJB.findByCiclo(cicloselected);
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
        }
        //StringBuilder builder = new StringBuilder();
         
        
    } 
    public void initRender(){
        if(!this.consultar){
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar esta sección"));
        }
    }
}
