/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class LogroFacade extends AbstractFacade<Logro> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogroFacade() {
        super(Logro.class);
    }

    public List<Logro> getContenidoByAll(Contenidotematico contenido) {
        Query cq = em.createNamedQuery("Logro.findByContenido");
        cq.setParameter("contenidotematicoId", contenido);
        return cq.getResultList();
    }

    public Logro getByEstudiante(Estudiante estudiante, Logro logro) {
        try {
            Query cq = em.createNamedQuery("Logro.findByEstdudiante");
            cq.setParameter("estudianteId", estudiante);
            cq.setParameter("logroId", logro.getLogroId());
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Logro) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
    public List<Logro> getByContenido(Contenidotematico contenido){
        try {
            Query cq = em.createNamedQuery("Logro.findByContenido");
            cq.setParameter("contenidotematicoId", contenido);
            if(cq.getResultList()!=null){
                return cq.getResultList();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
        
    }
}
