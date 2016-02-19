/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.EstadoAniolectivo;
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
public class EstadoAniolectivoFacade extends AbstractFacade<EstadoAniolectivo> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoAniolectivoFacade() {
        super(EstadoAniolectivo.class);
    }
    public List<EstadoAniolectivo> getEstadosAnTerminado() {
        Query cq = em.createNamedQuery("EstadoAniolectivo.findAllTerminado");
        return cq.getResultList();
    }
}
