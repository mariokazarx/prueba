/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Escala;
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
public class EscalaFacade extends AbstractFacade<Escala> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EscalaFacade() {
        super(Escala.class);
    }
    public List<Escala> findAllOrder() {
        Query cq = em.createNamedQuery("Escala.findAllOrder");
        return cq.getResultList();
    }
}
