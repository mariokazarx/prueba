/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Contenidotematico;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
public class ReporteAsignacionAcademica {

    private Integer numero;
    private String curso;
    private Integer año;
    private List<Contenidotematico> contenidos; 
    
    public ReporteAsignacionAcademica() {
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public List<Contenidotematico> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenidotematico> contenidos) {
        this.contenidos = contenidos;
    }
    public JRDataSource getContenidoDS()    
    {       
        return new JRBeanCollectionDataSource(contenidos);   
    }   
    
}
