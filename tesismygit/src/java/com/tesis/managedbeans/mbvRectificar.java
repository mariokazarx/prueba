/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvRectificar implements Serializable {

    private Profesor profesor;
    private List<Profesor> profesores;
    private boolean banderaAsig = false;
    private boolean banderaSearch = false;
    private List<Contenidotematico> contenidosRectificar;
    private List<Contenidotematico> contenidosRectificados;
    private List<Periodo> periodosSelecteds;
    private Anlectivo aEscolar;
    private Periodo periodoSelected;
    private boolean contenidoPrincipal;
    private boolean banderaRectificar;
    private Contenidotematico contenidoSelected;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AsignaturacicloFacade asignaturaCicloEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private EstadocontenidotematicoFacade estadoContenidoEjb;
    @EJB
    private EstadocontenidotematicoFacade estadocontenidoEjb;
    @Resource
    UserTransaction tx;

    public mbvRectificar() {
    }

    public List<Contenidotematico> getContenidosRectificar() {
        return contenidosRectificar;
    }

    public void setContenidosRectificar(List<Contenidotematico> contenidosRectificar) {
        this.contenidosRectificar = contenidosRectificar;
    }

    public List<Contenidotematico> getContenidosRectificados() {
        return contenidosRectificados;
    }

    public void setContenidosRectificados(List<Contenidotematico> contenidosRectificados) {
        this.contenidosRectificados = contenidosRectificados;
    }

    public boolean isContenidoPrincipal() {
        return contenidoPrincipal;
    }

    public void setContenidoPrincipal(boolean contenidoPrincipal) {
        this.contenidoPrincipal = contenidoPrincipal;
    }

    public List<Periodo> getPeriodosSelecteds() {
        return periodosSelecteds;
    }

    public void setPeriodosSelecteds(List<Periodo> periodosSelecteds) {
        this.periodosSelecteds = periodosSelecteds;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public boolean isBanderaRectificar() {
        return banderaRectificar;
    }

    public void setBanderaRectificar(boolean banderaRectificar) {
        this.banderaRectificar = banderaRectificar;
    }

    public Contenidotematico getContenidoSelected() {
        return contenidoSelected;
    }

    public void setContenidoSelected(Contenidotematico contenidoSelected) {
        this.contenidoSelected = contenidoSelected;
    }
    
    
    
    
    
    public boolean isBanderaSearch() {
        return banderaSearch;
    }

    public void setBanderaSearch(boolean banderaSearch) {
        this.banderaSearch = banderaSearch;
    }

    public boolean isBanderaAsig() {
        return banderaAsig;
    }

    public void setBanderaAsig(boolean banderaAsig) {
        this.banderaAsig = banderaAsig;
    }

    
    public Profesor getProfesor() {
        return profesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;

    }

    
    @PostConstruct
    public void inicioPagina() {
        this.aEscolar = anlectivoEjb.getIniciado();
        this.contenidosRectificados = new ArrayList<Contenidotematico>();
        this.contenidosRectificar = new ArrayList<Contenidotematico>();
        this.periodosSelecteds = periodoEjb.getPeriodosByAnio(aEscolar);
        this.contenidoPrincipal = false;
        this.banderaRectificar = false;
        this.periodoSelected = new Periodo();
        this.contenidoSelected = new Contenidotematico();
        
        this.banderaAsig = false;
        this.banderaSearch = false;
        Profesor aux = (Profesor) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("param1");
        cargarDatos(aux);
        profesores = this.profesorEjb.findAll();
    }

    public void cargarContenido(){
        System.out.println("PEROIODP SELECTED"+periodoSelected);
        if(periodoSelected.getPeriodoId()!=null){
            //si el periodo esta en evaluacion no se puede
            periodoSelected = periodoEjb.find(periodoSelected.getPeriodoId());
            if(periodoSelected.getEstadoPeriodoId().getEstadoPeriodoId()==2){
                contenidosRectificar = contenidoEjb.getRectificar(periodoSelected, profesor);
                contenidosRectificados = contenidoEjb.getRectificados(periodoSelected, profesor);
            }else{
                System.out.println("ENTRO MENSAJE");
                this.contenidoSelected = new Contenidotematico();
                contenidosRectificar.clear();
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No puede rectificar notas de un periodo que no ha terminado"));
            }
            
        }else{
            //seleccione
            this.contenidoSelected = new Contenidotematico();
            contenidosRectificar.clear();
        }
    }
    
    public void cargarBoton(){
        System.out.println("PEROIODP SELECTED"+contenidoSelected);
        if(contenidoSelected.getContenidotematicoId()!=null){
            this.banderaRectificar = true;
        }
        else{
            this.banderaRectificar = false;
        }
    }
    
    public void rectificarMateria(){
        if(contenidoSelected.getContenidotematicoId()!=null){
            contenidoSelected = contenidoEjb.find(contenidoSelected.getContenidotematicoId());
            Estadocontenidotematico estadoAux = estadoContenidoEjb.find(3);
            contenidoSelected.setEstado(estadoAux);
            contenidoEjb.edit(contenidoSelected);
        }
    }
    public void terminarRectificar(Contenidotematico contenicoAux){
        
    }
    
    
    public List<Profesor> completeProfesor(String query) {

        List<Profesor> allThemes = profesorEjb.findAll();
        List<Profesor> filteredThemes = new ArrayList<Profesor>();
        System.out.println("ccc" + query + allThemes.size());
        for (int i = 0; i < allThemes.size(); i++) {
            Profesor skin = allThemes.get(i);
            if (skin.getCedula().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }

        return filteredThemes;
    }

    public void prueba() {
        try {
            if (this.profesor != null) {
                System.out.println("aaaaww"+this.profesor.getNombre());
                //cursoSelected = cursoEjb.find(1);
                this.cargarDatos(profesor);
            } else {
                System.out.println("errrrooorrrr 123" );
                this.contenidoPrincipal = false;
            }

        } catch (Exception e) {
            System.out.println("errrrooorrrr" + e.toString());
            this.contenidoPrincipal = false;
        }

    }

    public void mensajeAdvertencia(){
        System.out.println("mensjaeee");
        
        FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion academica", "Exitosa"));
        //inicioPagina();
    }
    private void cargarDatos(Profesor aux){
        if (aux != null) {
            this.profesor = aux;
            System.out.println("QQQQQ1" + this.profesor + "EEE1" + profesor.getNombre());
            if(profesor.getEstadoProfesorId().getEstadoProfesorId()==1){
                //profesor activo
                System.out.println("entro 1");
                if(aEscolar!=null){ 
                    //hay año iniciado
                    System.out.println("entro 2");
                    if(!periodosSelecteds.isEmpty()){
                        System.out.println("entro 3");
                        this.contenidoPrincipal = true;
                    }
                    else{
                        System.out.println("entro 4");
                        //no hay periodos
                    }
                }else{
                    System.out.println("entro 5");
                    this.contenidoPrincipal = false;
                }
            }
            else{
                System.out.println("entro 6");
                this.contenidoPrincipal = false;
                //profesor debe estar activo 
                //alño esdcolar iniciado 
                // al menos un curso para ese año
                //prueba();
            }
        }
        else{
            System.out.println("entro 7");
            this.profesor = new Profesor();
        }
    }
    
}