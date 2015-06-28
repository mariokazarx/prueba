/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            System.out.println("RESULTQDO" + cq.getSingleResult());
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Logro) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
