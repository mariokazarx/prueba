/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCurso implements Serializable{

    private Curso curso;
    private List<Curso> cursos;
    private Anlectivo anlectivoSelected;
    private List<Anlectivo> anlectivos;
    private Ciclo cicloselected;
    private List<Ciclo> ciclosSelected;
    
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AnlectivoFacade anlectivoEJB;
    @EJB
    private CicloFacade cicloEJB;
    
    public mbvCurso() {
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Anlectivo getAnlectivoSelected() {
        return anlectivoSelected;
    }

    public void setAnlectivoSelected(Anlectivo anlectivoSelected) {
        this.anlectivoSelected = anlectivoSelected;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public AnlectivoFacade getAnlectivoEJB() {
        return anlectivoEJB;
    }

    public void setAnlectivoEJB(AnlectivoFacade anlectivoEJB) {
        this.anlectivoEJB = anlectivoEJB;
    }

    

    public Ciclo getCicloselected() {
        return cicloselected;
    }

    public void setCicloselected(Ciclo cicloselected) {
        this.cicloselected = cicloselected;
    }

    public List<Ciclo> getCiclosSelected() {
        return ciclosSelected;
    }

    public void setCiclosSelected(List<Ciclo> ciclosSelected) {
        this.ciclosSelected = ciclosSelected;
    }

    public CursoFacade getCursoEjb() {
        return cursoEjb;
    }

    public void setCursoEjb(CursoFacade cursoEjb) {
        this.cursoEjb = cursoEjb;
    }

    public CicloFacade getCicloEJB() {
        return cicloEJB;
    }

    public void setCicloEJB(CicloFacade cicloEJB) {
        this.cicloEJB = cicloEJB;
    }
    
    @PostConstruct
    public void inicioPagina(){
        this.anlectivoSelected = new Anlectivo();
        this.cicloselected = new Ciclo();
        this.curso = new Curso();
        this.anlectivos = this.anlectivoEJB.findAll();      
    }
    public void cargarCiclo(){
        try {
            this.anlectivoSelected = anlectivoEJB.find(anlectivoSelected.getAnlectivoId());
            //System.out.println("mma"+anlectivoSelected.getConfiguracionId().getNombre());
            this.ciclosSelected = cicloEJB.getByConfiguracion(anlectivoSelected.getConfiguracionId());
            System.out.println(this.ciclosSelected.isEmpty());
            //if(this.ciclosSlected.isEmpty()==true){
                //this.banAsig=false;
            //}
        } catch (Exception e) {
           System.out.println("mmaaaaa"+e.getMessage());
        }    
    }
     public void insertar(){
        try {
            this.curso.setAnlectivoId(anlectivoSelected);
            this.curso.setCicloId(cicloselected);
            this.cursoEjb.create(curso);
            FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
            inicioPagina();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        
    }
}
