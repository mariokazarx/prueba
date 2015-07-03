/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
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
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    public List<Estudiante> findByCurso(Curso curso) {
        Query cq = em.createNamedQuery("Estudiante.findByCurso");
        cq.setParameter("cursoId", curso);
        return cq.getResultList();
    }

    public Logronota findNotaEstudiante(Logro logro, Estudiante estudiante) {
        try {
            Query cq = em.createNamedQuery("Estudiante.findNotaLOgro");
            cq.setParameter("logroId", logro);
            cq.setParameter("estudianteId", estudiante.getEstudianteId());
            if (cq.getSingleResult() != null) {
                //System.out.println("***ENCONTRO****");
                return (Logronota) cq.getSingleResult();
            } else {
                //System.out.println("***NO ENCONTRO****");
                return null;
            }
        } catch (Exception e) {
            //System.out.println("***ERROR****"+e.toString());
            return null;
        }
    }

    public Nota findNotaEst(Contenidotematico contenido, Estudiante estudiante) {
        try {
            Query cq = em.createNamedQuery("Estudiante.findNotaEst");
            cq.setParameter("contenidotematicoId", contenido);
            cq.setParameter("estudianteId", estudiante.getEstudianteId());
            if (cq.getSingleResult() != null) {
                //System.out.println("***ENCONTRO****");
                return (Nota) cq.getSingleResult();
            } else {
                //System.out.println("***NO ENCONTRO****");
                return null;
            }
        } catch (Exception e) {
            //System.out.println("***ERROR****"+e.toString());
            return null;
        }
    }
}
