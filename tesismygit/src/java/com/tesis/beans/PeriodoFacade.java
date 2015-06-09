/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Configuracion;
import com.tesis.entity.Periodo;
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
public class PeriodoFacade extends AbstractFacade<Periodo> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoFacade() {
        super(Periodo.class);
    }
    public Periodo getPeriodoMinByConfiguracion(Configuracion conf){
        Query cq = em.createNamedQuery("Periodo.findMinByConfiguracion");
        cq.setParameter("configuracionId",conf);
        return (Periodo) cq.getSingleResult();
    }
     public List<Periodo> getPeriodosByConfiguracion(Configuracion conf){
        Query cq = em.createNamedQuery("Periodo.findByConfiguracion");
        cq.setParameter("configuracionId",conf);
        return cq.getResultList();
    }
}
