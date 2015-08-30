/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Criterioevaluacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class CriterioevaluacionFacade extends AbstractFacade<Criterioevaluacion> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CriterioevaluacionFacade() {
        super(Criterioevaluacion.class);
    }

    public boolean getByNombre(String nombre) {
        try {
            Query cq = em.createNamedQuery("Criterioevaluacion.findByNombre");
            cq.setParameter("nombre", nombre);
            if (cq.getSingleResult() == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }

    }

    public boolean removeById(Criterioevaluacion criterioeval) {
        try {
            Query cq = em.createNamedQuery("Criterioevaluacion.removeById");
            cq.setParameter("criterioevaluacionId", criterioeval.getCriterioevaluacionId());
            if(cq.executeUpdate()>0){
                return true;
            }
            else{
                return false;
            }
        } catch (PersistenceException e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
}
