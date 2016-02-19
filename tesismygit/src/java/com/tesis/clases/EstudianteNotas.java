/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
public class EstudianteNotas  implements Serializable{
    private Integer id;
    private String nombre;
    private String apellido;
    private Map<Integer,Object> notasLogros;
    private BigDecimal nota;
    private List<Logronota> logros;

    public EstudianteNotas() {
        notasLogros = new HashMap<Integer, Object>();
        logros = new ArrayList<Logronota>();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Map<Integer, Object> getNotasLogros() {
        return notasLogros;
    }

    public void setNotasLogros(Map<Integer, Object> notasLogros) {
        this.notasLogros = notasLogros;
    }
    
    public JRDataSource getlogrosDS()    
    {       
        return new JRBeanCollectionDataSource(logros);   
    }
    
}
