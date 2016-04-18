/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Periodo;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
public class ReporteBoletin {
    private Estudiante estudiante;
    private Periodo periodo;
    private Curso curso;
    private List<ContenidoBoletin> contenidos;

    public ReporteBoletin() {
        this.contenidos = new ArrayList<ContenidoBoletin>();
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<ContenidoBoletin> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<ContenidoBoletin> contenidos) {
        this.contenidos = contenidos;
    }
    public JRDataSource getcontenidosDS()    
    {       
        return new JRBeanCollectionDataSource(contenidos);   
    }
}
