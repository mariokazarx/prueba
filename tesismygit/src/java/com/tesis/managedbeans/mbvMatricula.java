/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.CicloFacade;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvMatricula implements Serializable{

    private Estudiante estudiante; 
    private boolean banderaExiste;
    private List<Curso> cursos;
    private List<Estudiante> estudiantes;
    private Ciclo cicloEstudiante;
    private Curso cursoSelected;
    @EJB
    private CicloFacade cicloEjb;
    
    public mbvMatricula() {
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Curso getCursoSelected() {
        return cursoSelected;
    }

    public void setCursoSelected(Curso cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    public boolean isBanderaExiste() {
        return banderaExiste;
    }

    public void setBanderaExiste(boolean banderaExiste) {
        this.banderaExiste = banderaExiste;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @PostConstruct()
    public void inicio(){
            this.cicloEstudiante = new Ciclo();
            this.cursoSelected = new Curso();
          //mbvEstudiante estudiante = (mbvEstudiante) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mbvEstudiante");
           Estudiante aux = (Estudiante) FacesContext.getCurrentInstance()
                                    .getExternalContext()
                                    .getFlash()
                                    .get("param1");  
           if(aux!=null){
               this.estudiante=aux;
               System.out.println("QQQQQ1"+this.estudiante+"EEE1"+estudiante.getNombre());
               cargarCursos();
           }else{
               System.out.println("QQQQQ2"+this.estudiante);
               this.estudiante = new Estudiante();
           }
    }
    public void cargarCursos(){
        this.cicloEstudiante = cicloEjb.getCicloByNum(this.estudiante.getUltimoaprobado());
        this.cursos = new ArrayList<Curso>();
        this.cursos = this.cicloEstudiante.getCursoList();
        System.out.println("ciclo"+this.cicloEstudiante+"cursos"+this.cicloEstudiante.getNumero());
    }
    public void Matricular(){
        
    }
}

