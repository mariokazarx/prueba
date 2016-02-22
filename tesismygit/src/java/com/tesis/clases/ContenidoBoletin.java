/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Logronota;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
public class ContenidoBoletin {
    private Contenidotematico contenido;
    private List<Logronota> logros;
    private BigDecimal nota;
    private String observaciones;

    public ContenidoBoletin() {
        this.logros = new ArrayList<Logronota>();
    }

    public Contenidotematico getContenido() {
        return contenido;
    }

    public void setContenido(Contenidotematico contenido) {
        this.contenido = contenido;
    }

    public List<Logronota> getLogros() {
        return logros;
    }

    public void setLogros(List<Logronota> logros) {
        this.logros = logros;
    }

    

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public JRDataSource getlogrosDS()    
    {       
        return new JRBeanCollectionDataSource(logros);   
    }
    
}
