/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Profesor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class ContenidotematicoFacade extends AbstractFacade<Contenidotematico> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContenidotematicoFacade() {
        super(Contenidotematico.class);
    }
    public int removeByProfesorCurso(Profesor profesor,Curso curso) {
        Query cq = em.createNamedQuery("Contenidotematico.DeleteByProfesorCurso");
        cq.setParameter("profesorId",profesor);
        cq.setParameter("cursoId",curso);
        return cq.executeUpdate();
    }
}
