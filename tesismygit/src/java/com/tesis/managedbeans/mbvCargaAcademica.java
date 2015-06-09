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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
public class mbvCargaAcademica implements Serializable{
    
    private Profesor profesor;
    private List<Profesor> profesores;
    private Curso cursoSelected;
    private List<Curso> cursos;
    private List<Asignatura> asSelecteds;
    private List<Asignatura> asignturasdisponibles;
    private List<Asignatura> asignturasSelecionadas;
    private DualListModel<Asignatura> pickList;
    private List<Asignaturaciclo> asignaturasprofes;
    private boolean banderaAsig = false;
    private boolean banderaSearch = false;
    private Contenidotematico contenido;
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
    public void inicioPagina(){
        this.profesor = new Profesor();
        profesores = this.profesorEjb.findAll();
        this.cursos = this.cursoEjb.findAll();
        this.cursoSelected = new Curso();
        this.contenido = new Contenidotematico();
        asSelecteds = new ArrayList<Asignatura>();
    }
    public List<Profesor> completeProfesor(String query) {
        
        List<Profesor> allThemes = profesorEjb.findAll();
        List<Profesor> filteredThemes = new ArrayList<Profesor>();
        System.out.println("ccc"+query+allThemes.size()); 
        for (int i = 0; i < allThemes.size(); i++) {
            Profesor skin = allThemes.get(i);
            if(skin.getCedula().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }
         
        return filteredThemes;
    }
    public void prueba(){
        try {
            if(this.profesor!=null){
                System.out.println("aaaaww");
                //cursoSelected = cursoEjb.find(1);
                this.banderaSearch=true;
            }else{
                this.banderaSearch = false;
            }
            
        } catch (Exception e) {
            System.out.println("errrrooorrrr"+e.toString());
            this.banderaAsig = true;
        }
        
    }
    public void cargarPickList(){
        try {
            System.out.println("aaa"+cursoSelected);
            if(cursoSelected.getCursoId()==null){
                this.banderaAsig = false;
            }else{
                this.banderaAsig = true;
                asignturasdisponibles = new ArrayList<Asignatura>();
                asignturasSelecionadas = new ArrayList<Asignatura>();
                Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                System.out.println("FUNCIONO:::::"+cf);
                Periodo per = periodoEjb.getPeriodoMinByConfiguracion(cf);
                System.out.println("FUNCIONO********"+per);
                asignaturasprofes = asignaturaCicloEjb.asignaturasDisponibles(profesor, cursoSelected);
                for(Asignaturaciclo asc:asignaturasprofes){
                    Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                    asignturasdisponibles.add(as);
                }
                asignaturasprofes = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected,per);
                for(Asignaturaciclo asc:asignaturasprofes){
                    Asignatura as =  asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                    asignturasSelecionadas.add(as);
                }
                this.pickList = new DualListModel<Asignatura>(asignturasdisponibles,asignturasSelecionadas);
            }
            
        } catch (Exception e) {
            System.out.println("errrrooorrrr"+e.toString());
            this.banderaAsig = false;
        }
    }
    public void onTransfer(TransferEvent event) {
        try {
            System.out.println("mmm"+event.isAdd()+event.isRemove()+event.getItems());
          
            for(Object item : event.getItems()) {
                //builder.append(((Theme) item).getName()).append("<br />");
                System.out.println(item.toString());
                if(event.isRemove()){
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString())); 
                    asSelecteds.remove(asg);
                    System.out.println("FUNCIONO********"+asSelecteds);
                }
                if(event.isAdd()){
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString())); 
                    asSelecteds.add(asg);
                    System.out.println("FUNCIONO********"+asSelecteds);
                }
            }
        } catch (Exception e) {
            System.out.println(",,2"+e.getMessage());
        }
        //StringBuilder builder = new StringBuilder();
         
        
    } 
    public void cargarMaterias() throws IllegalStateException, SecurityException, SystemException{
        try {
            tx.begin();
            System.out.println("DELETE****"+contenidoEjb.removeByProfesorCurso(profesor, cursoSelected));
            Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
            List<Periodo> periodos = periodoEjb.getPeriodosByConfiguracion(cf);
            Estadocontenidotematico est = estadocontenidoEjb.find(1);
            System.out.println("FUNCIONO********"+asSelecteds);
            Curso cur = cursoEjb.find(cursoSelected.getCursoId());
            for(int i = 0; i<asSelecteds.size();i++){
                Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(),asSelecteds.get(i));
                for(Periodo aux : periodos){
                    Contenidotematico con = new Contenidotematico();
                    con.setCursoId(cursoSelected);
                    con.setPeriodoId(aux);
                    con.setProfesorId(profesor);
                    con.setAsignaturacicloId(asg);
                    con.setEstado(est);
                    contenidoEjb.create(con);
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("ERROR"+e.toString());
        }
    }
}
