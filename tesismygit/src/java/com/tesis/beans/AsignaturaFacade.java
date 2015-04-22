/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignatura;
import com.tesis.entity.Ciclo;
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
public class AsignaturaFacade extends AbstractFacade<Asignatura> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturaFacade() {
        super(Asignatura.class);
    }
    public List<Asignatura> findByCiclo(Ciclo ciclo) {
        Query cq = em.createNamedQuery("Asignatura.findByCiclo");
        cq.setParameter("ciclo",ciclo.getCicloId());
        return cq.getResultList();
        //System.out.println("aa"+ciclo);
        //return null;
    }
}
