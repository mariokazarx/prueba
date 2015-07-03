/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.entity.Aprobacion;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.EstadoMatricula;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
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
public class mbvMatricula implements Serializable {

    private Estudiante estudiante;
    private boolean banderaExiste;
    private List<Curso> cursos;
    private List<Estudiante> estudiantes;
    private Ciclo cicloEstudiante;
    private Curso cursoSelected;
    private Matricula matricula;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private EstadoMatriculaFacade estadomatriculaEjB;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEJb;
    
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
    public void inicio() {
        this.cicloEstudiante = new Ciclo();
        this.cursoSelected = new Curso();
        this.matricula = new Matricula();
        //mbvEstudiante estudiante = (mbvEstudiante) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mbvEstudiante");
        Estudiante aux = (Estudiante) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("param1");
        if (aux != null) {
            this.estudiante = aux;
            System.out.println("QQQQQ1" + this.estudiante + "EEE1" + estudiante.getNombre());
            cargarCursos();
        } else {
            System.out.println("QQQQQ2" + this.estudiante);
            this.estudiante = new Estudiante();
        }
        estudiantes= estudianteEJb.findAll();
    }

    public void cargarCursos() {
        this.cicloEstudiante = cicloEjb.getCicloByNum(this.estudiante.getUltimoaprobado());
        this.cursos = new ArrayList<Curso>();
        this.cursos = this.cicloEstudiante.getCursoList();
        System.out.println("ciclo" + this.cicloEstudiante + "cursos" + this.cicloEstudiante.getNumero());
    }
    public List<Estudiante> completeEstudiante(String query) {

        List<Estudiante> allThemes = estudianteEJb.findAll();
        List<Estudiante> filteredThemes = new ArrayList<Estudiante>();
        System.out.println("ccc" + query + allThemes.size());
        for (int i = 0; i < allThemes.size(); i++) {
            Estudiante skin = allThemes.get(i);
            if (skin.getIdentificiacion().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }

        return filteredThemes;
    }
    public void buscar() {
        try {
            if (this.estudiante != null) {
                System.out.println("aaaaww"+this.estudiante.getNombre());
                //cursoSelected = cursoEjb.find(1);
               // this.banderaSearch = true;
            } else {
                //this.banderaSearch = false;
            }

        } catch (Exception e) {
            System.out.println("errrrooorrrr" + e.toString());
            //this.banderaAsig = true;
        }

    }
    public void matricularEstudiante() {
        try {
            System.out.println("curso");
            if (cursoSelected != null) {
                EstadoMatricula esmatricula = new EstadoMatricula();
                esmatricula = estadomatriculaEjB.find(1);
                Aprobacion aprobacion = new Aprobacion();
                aprobacion = aprobacionEjb.find(1);
                this.matricula.setAprobacionId(aprobacion);
                this.matricula.setCursoId(cursoSelected);
                this.matricula.setEstadoMatriculaId(esmatricula);
                this.matricula.setEstudianteId(estudiante);
                matriculaEjb.create(matricula);
            }
        } catch (Exception e) {
            System.out.println("ooooOOOO"+e.toString());
        }


    }
}
