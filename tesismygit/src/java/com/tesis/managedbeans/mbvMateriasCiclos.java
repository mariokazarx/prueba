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
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
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
    
    public mbvMateriasCiclos() {
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

    public AsignaturacicloFacade getAsigCicloaEJB() {
        return asigCicloaEJB;
    }

    public void setAsigCicloaEJB(AsignaturacicloFacade asigCicloaEJB) {
        this.asigCicloaEJB = asigCicloaEJB;
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
    
    
    public AsignaturaFacade getAsignaturaEJB() {
        return asignaturaEJB;
    }

    public void setAsignaturaEJB(AsignaturaFacade asignaturaEJB) {
        this.asignaturaEJB = asignaturaEJB;
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

    public ConfiguracionFacade getConfiguracionEJB() {
        return configuracionEJB;
    }

    public void setConfiguracionEJB(ConfiguracionFacade configuracionEJB) {
        this.configuracionEJB = configuracionEJB;
    }

    public CicloFacade getCicloEJB() {
        return cicloEJB;
    }

    public void setCicloEJB(CicloFacade cicloEJB) {
        this.cicloEJB = cicloEJB;
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
    }
    public void cargarCiclo(){
        try {
            //System.out.println(configuracionSelected.getConfiguracionId());
            this.ciclosSlected = cicloEJB.getByConfiguracion(configuracionSelected);
            System.out.println(this.ciclosSlected.isEmpty());
            if(this.ciclosSlected.isEmpty()==true){
                this.banAsig=false;
            }
            Configuracion auxcfg = configuracionEJB.find(configuracionSelected.getConfiguracionId());
            if(anlectivoEJB.configuracionEnUso(auxcfg)){
                //en uso
            }else{
                //no en uso
            }
        } catch (Exception e) {
            this.banAsig=false;
        }    
    }
    public void cargarAsignaturas(){
        try {
            System.out.println(cicloselected.getCicloId());
            cicloTabla = cicloEJB.find(cicloselected.getCicloId());
            this.banAsig = true;
            this.configuraciones = this.configuracionEJB.findAll();
            this.asignturasdisponibles = this.asignaturaEJB.findByConfiguracion(configuracionSelected, cicloselected);
            this.asignturasSelecionadas = this.asignaturaEJB.findByCiclo(cicloselected);
            this.coso = new DualListModel<Asignatura>(asignturasdisponibles,asignturasSelecionadas);
        } catch (Exception e) {
            System.err.println("mm"+e.getMessage());
            this.banAsig=false;
        }
        
    }
    public void onTransfer(TransferEvent event) {
        try {
            System.out.println("mmm"+event.isAdd()+event.isRemove()+event.getItems());
            //isremove de izq a derecha isadd de derecha a izq
            for(Object item : event.getItems()) {
                //builder.append(((Theme) item).getName()).append("<br />");
                System.out.println(item.toString());
                if(event.isRemove()){
                    asg = asignaturaEJB.find(Integer.parseInt(item.toString())); 
                    System.out.println("mierda"+asg.getNombre());
                    asigCicloaEJB.removeByAsignatura(asg);
                }
                if(event.isAdd()){
                    asg = asignaturaEJB.find(Integer.parseInt(item.toString())); 
                    System.out.println("mierda"+asg.getNombre());
                    asigCiclo.setAsignaturaId(asg);
                    asigCiclo.setCicloId(cicloselected);
                    asigCicloaEJB.create(asigCiclo);
                }
            }
        } catch (Exception e) {
            System.out.println(",,2"+e.getMessage());
        }
        //StringBuilder builder = new StringBuilder();
         
        
    } 
}
