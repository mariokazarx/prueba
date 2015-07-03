/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

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
public class ProfesorFacade extends AbstractFacade<Profesor> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesorFacade() {
        super(Profesor.class);
    }
    public Profesor getByCorreo(String correo) {
        try {
            Query cq = em.createNamedQuery("Profesor.findByCorreo");
            cq.setParameter("correo", correo);
            System.out.println("RESULTQDO USUARIO" + cq.getSingleResult());
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Profesor) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
