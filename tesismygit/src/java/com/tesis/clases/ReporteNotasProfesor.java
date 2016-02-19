/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Asignatura;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
public class ReporteNotasProfesor implements Serializable{
    private Profesor profesor;
    private Curso curso;
    private Periodo periodo;
    private Asignatura asignatura;
    private List<EstudianteNotas> estudiantes;

    public ReporteNotasProfesor() {
        this.estudiantes= new ArrayList<EstudianteNotas>();
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
    
    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public List<EstudianteNotas> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<EstudianteNotas> estudiantes) {
        this.estudiantes = estudiantes;
    }
    public JRDataSource getEstudiantesDS()    
    {       
        return new JRBeanCollectionDataSource(estudiantes);   
    }
    
}
