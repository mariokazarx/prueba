/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class AsignaturacicloFacade extends AbstractFacade<Asignaturaciclo> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturacicloFacade() {
        super(Asignaturaciclo.class);
    }
    public int removeByAsignatura(Asignatura asig) {
        //System.out.println("aaa"+asig);
        Query cq = em.createNamedQuery("Asignaturaciclo.removeByAsignatura");
        cq.setParameter("asignatura",asig);
        return cq.executeUpdate();
    
        
        //System.out.println("aa"+ciclo);
        //return 1;
}
}
