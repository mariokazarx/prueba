/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.webservices;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Estudiante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Jurado
 */
public class ObjRespuesta implements Serializable{
    private static final long serialVersionUID = -8824680347672854234L;
    
    private String estado;
    private Estudiante est;
    private List<Anlectivo> aescolares;

    public ObjRespuesta() {
        est = new Estudiante();
        aescolares = new ArrayList<Anlectivo>();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Estudiante getEst() {
        return est;
    }

    public void setEst(Estudiante est) {
        this.est = est;
    }

    public List<Anlectivo> getAescolares() {
        return aescolares;
    }

    public void setAescolares(List<Anlectivo> aescolares) {
        this.aescolares = aescolares;
    }
    
    
}
