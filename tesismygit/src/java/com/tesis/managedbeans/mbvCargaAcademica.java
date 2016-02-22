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
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCargaAcademica implements Serializable {

    private Profesor profesor;
    private String mensajeError;
    private boolean contenidoError;
    private List<Profesor> profesores;
    private Curso cursoSelected;
    private List<Curso> cursos;
    private List<Periodo> periodos;
    private List<Asignatura> asSelecteds;
    private List<Asignatura> asignturasdisponibles;
    private List<Asignatura> asignturasSelecionadas;
    private DualListModel<Asignatura> pickList;
    private List<Asignaturaciclo> asignaturasprofes;
    private boolean banderaAsig = false;
    private boolean banderaSearch = false;
    private boolean isPeriodo = false;
    private boolean mostrarCursos;
    private Contenidotematico contenido;
    private String mensaje;
    private Periodo periodoSelected;
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
    private EstadocontenidotematicoFacade estadocontenidoEjb;
    @Resource
    UserTransaction tx;

    public mbvCargaAcademica() {
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public boolean isIsPeriodo() {
        return isPeriodo;
    }

    public void setIsPeriodo(boolean isPeriodo) {
        this.isPeriodo = isPeriodo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String menajeError) {
        this.mensajeError = menajeError;
    }

    public boolean isContenidoError() {
        return contenidoError;
    }

    public void setContenidoError(boolean contenidoError) {
        this.contenidoError = contenidoError;
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

    public DualListModel<Asignatura> getPickList() {
        return pickList;
    }

    public void setPickList(DualListModel<Asignatura> pickList) {
        this.pickList = pickList;
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

    public Curso getCursoSelected() {
        return cursoSelected;
    }

    public void setCursoSelected(Curso cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
  
    @PostConstruct
    public void inicioPagina() {
        this.contenidoError = false;
        this.banderaAsig = false;
        this.banderaSearch = false;
        this.mostrarCursos = true;
        this.mensaje = "";
        this.cursos = new ArrayList<Curso>();
        this.periodos = new ArrayList<Periodo>();
        this.periodoSelected = new Periodo();
        Profesor aux = (Profesor) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("param1");
        cargarDatos(aux);
        profesores = this.profesorEjb.findAll();
        //this.cursos = this.cursoEjb.findAll();
        this.cursoSelected = new Curso();
        this.contenido = new Contenidotematico();
        asSelecteds = new ArrayList<Asignatura>();
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
                this.banderaSearch = false;
            }

        } catch (Exception e) {
            System.out.println("errrrooorrrr" + e.toString());
            this.banderaAsig = true;
        }

    }

    public void cargarPickList() {
        try {
            System.out.println("aaa" + cursoSelected);
            if (cursoSelected.getCursoId() == null) {
                this.banderaAsig = false;
            } else {
                if(isPeriodo){
                    this.banderaAsig = true;
                    asignturasdisponibles = new ArrayList<Asignatura>();
                    asignturasSelecionadas = new ArrayList<Asignatura>();
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    System.out.println("FUNCIONO:::::" + cursoSelected.getAnlectivoId());
                    Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                    //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                    System.out.println("FUNCIONO:::::" + an);
                    Periodo per = periodoEjb.getPeriodoMinByAnio(an);
                    System.out.println("FUNCIONO********oo" + per);
                    asignaturasprofes = asignaturaCicloEjb.asignaturasDisponiblesPeriodo(profesor, cursoSelected,periodoSelected);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasdisponibles.add(as);
                    }
                    asignaturasprofes = asignaturaCicloEjb.asignaturasProfesorPeriodo(profesor, cursoSelected, periodoSelected);
                    System.out.println("JJKJKJ"+profesor+"   "+cursoSelected+"    "+per);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        System.out.println("asignatura entro "+asc);
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasSelecionadas.add(as);
                    }
                    this.pickList = new DualListModel<Asignatura>(asignturasdisponibles, asignturasSelecionadas);
                }else{
                    this.banderaAsig = true;
                    asignturasdisponibles = new ArrayList<Asignatura>();
                    asignturasSelecionadas = new ArrayList<Asignatura>();
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    System.out.println("FUNCIONO:::::" + cursoSelected.getAnlectivoId());
                    Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                    //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                    System.out.println("FUNCIONO:::::" + an);
                    Periodo per = periodoEjb.getPeriodoMinByAnio(an);
                    System.out.println("FUNCIONO********oo" + per);
                    asignaturasprofes = asignaturaCicloEjb.asignaturasDisponibles(profesor, cursoSelected);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasdisponibles.add(as);
                    }
                    asignaturasprofes = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected, per);
                    System.out.println("JJKJKJ"+profesor+"   "+cursoSelected+"    "+per);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        System.out.println("asignatura entro "+asc);
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasSelecionadas.add(as);
                    }
                    this.pickList = new DualListModel<Asignatura>(asignturasdisponibles, asignturasSelecionadas);
                }
            }

        } catch (Exception e) {
            System.out.println("errrrooorrrr" + e.toString());
            this.banderaAsig = false;
        }
    }

    public void onTransfer(TransferEvent event) {
        try {
            System.out.println("mmm" + event.isAdd() + event.isRemove() + event.getItems());

            for (Object item : event.getItems()) {
                //builder.append(((Theme) item).getName()).append("<br />");
                System.out.println(item.toString());
                if (event.isRemove()) {
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString()));
                    asSelecteds.remove(asg);
                    asignturasSelecionadas.remove(asg);
                    asignturasdisponibles.add(asg);
                    System.out.println("FUNCIONO********" + asignturasdisponibles+"ESTE MEJOR"+asignturasSelecionadas+"NHNH "+asg);
                }
                if (event.isAdd()) {
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString()));
                    asSelecteds.add(asg);
                    asignturasSelecionadas.add(asg);
                    asignturasdisponibles.remove(asg);
                    System.out.println("FUNCIONO********" + asignturasdisponibles+"ESTE MEJOR"+asignturasSelecionadas+"MJMJM "+asg);
                }
            }
        } catch (Exception e) {
            System.out.println(",,2" + e.getMessage());
        }
        //StringBuilder builder = new StringBuilder();


    }

    public void cargarMaterias() throws IllegalStateException, SecurityException, SystemException {
        try {
            tx.begin();
            if(isPeriodo){
                System.out.println("ES por periodo"+asignturasdisponibles.size()+" seleccionadas "+asignturasSelecionadas.size());
                cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                for (int i = 0; i < asignturasdisponibles.size(); i++) {
                    Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cursoSelected.getCicloId(), asignturasdisponibles.get(i));
                    Contenidotematico conAux = contenidoEjb.getContenidoByAll(profesor, cursoSelected, asg, periodoSelected);
                    System.out.println("asignatura ciclo "+conAux);
                    // advertencia
                    Estadocontenidotematico estAux = estadocontenidoEjb.find(5);
                    conAux.setEstado(estAux);
                    contenidoEjb.edit(conAux);
                }
                for (int i = 0; i < asignturasSelecionadas.size(); i++) {
                    Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cursoSelected.getCicloId(), asignturasSelecionadas.get(i));
                    System.out.println("asignatura ciclo AUX "+asg);
                    Contenidotematico conAux = contenidoEjb.getContenidoByCambio(cursoSelected, asg, periodoSelected);
                    System.out.println("asignatura ciclo "+conAux);
                    // advertencia
                    Estadocontenidotematico estAux;
                    if(periodoSelected.getEstadoPeriodoId().getEstadoPeriodoId()==1){
                        estAux = estadocontenidoEjb.find(1);
                    }
                    else{
                        estAux = estadocontenidoEjb.find(2);
                    }
                    conAux.setProfesorId(profesor);;
                    conAux.setEstado(estAux);
                    contenidoEjb.edit(conAux);
                }
                //return;
            }
            else{
                System.out.println("DELETE****" + contenidoEjb.removeByProfesorCurso(profesor, cursoSelected));
                cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                List<Periodo> periodos = periodoEjb.getPeriodosByAnio(an);
                Estadocontenidotematico est = estadocontenidoEjb.find(1);
                System.out.println("FUNCIONO********" + asSelecteds+"ESTE MEJOR"+asignturasSelecionadas);
                Curso cur = cursoEjb.find(cursoSelected.getCursoId());
                for (int i = 0; i < asignturasSelecionadas.size(); i++) {
                    Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignturasSelecionadas.get(i));
                    for (Periodo aux : periodos) {
                        Contenidotematico con = new Contenidotematico();
                        con.setCursoId(cursoSelected);
                        con.setPeriodoId(aux);
                        con.setProfesorId(profesor);
                        con.setAsignaturacicloId(asg);
                        con.setEstado(est);
                        contenidoEjb.create(con);
                    }
                }
            }
            tx.commit();
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion academica", "Exitosa"));
            cargarPickList();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("ERROR" + e.toString());
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
                Anlectivo anescolar = new Anlectivo();
                Anlectivo auxEscolar = anlectivoEjb.getIniciado();
                if(auxEscolar!=null){
                    //hay año iniciado
                    if(auxEscolar.getCursoList().isEmpty()){
                        //no hay cursos activos
                    }else{
                        this.cursos = auxEscolar.getCursoList();
                    }
                    List<Anlectivo> auxAnlectivos;
                    auxAnlectivos = anlectivoEjb.findAll();
                    for(Anlectivo auxan : auxAnlectivos){
                        if(auxan.getEstadoAniolectivoId().getEstadoAniolectivoId()==2){
                            anescolar = anlectivoEjb.find(auxan.getAnlectivoId());
                            break;
                        }
                    }
                    if(anescolar!=null){
                        //si hay año escolar
                        if(anescolar.getCursoList().isEmpty()){
                            // no hay cursos para ese año
                            System.out.println("NO HAY CURSOS");
                        }
                        for(Curso cur:anescolar.getCursoList()){
                            System.out.println(cur);
                        }
                    }
                }
            }
            this.banderaSearch = true;
            this.banderaAsig = false;
            this.isPeriodo = false;
            this.periodoSelected = new Periodo();
            this.cursoSelected = new Curso();
            //profesor debe estar activo 
            //alño esdcolar iniciado 
            // al menos un curso para ese año
            //prueba();
        }
        else{
            this.profesor = new Profesor();
        }
        
    }
    public void activarByPeriodo(){
        if(isPeriodo){
            //esta habilitado ppor periodo
            System.out.println("ACTIVO PERIODO");
            this.mostrarCursos = false;
            this.periodos = periodoEjb.getPeriodosByAnio(anlectivoEjb.getIniciado());
            System.out.println("PERIODOS "+this.periodos);
        }else{
            this.mostrarCursos=true;
            System.out.println("DESACTIVO PERIODO");
            this.periodoSelected = new Periodo();
            this.periodos.clear();
        }
    }
    public void cargarPeriodo(){
        if(periodoSelected.getPeriodoId()!=null){
            periodoSelected = periodoEjb.find(periodoSelected.getPeriodoId());
            if(periodoSelected.getEstadoPeriodoId().getEstadoPeriodoId()!=2){
                mostrarCursos = true;
            }else{
                mostrarCursos = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Este periodo ya termino"));
            }
            
            
        }else{
            mostrarCursos = false;
        }
    }
}
