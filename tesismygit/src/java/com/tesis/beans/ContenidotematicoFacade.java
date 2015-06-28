/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Periodo;
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
    public Contenidotematico getContenidoByAll(Profesor profesor,Curso curso,Asignaturaciclo asinaturaciclo,Periodo periodo){
        Query cq = em.createNamedQuery("Contenidotematico.findByAll");
        cq.setParameter("profesorId",profesor);
        cq.setParameter("cursoId",curso);
        cq.setParameter("asignaturacicloId",asinaturaciclo);
        cq.setParameter("periodoId",periodo);
        return (Contenidotematico) cq.getSingleResult();
    }
}
