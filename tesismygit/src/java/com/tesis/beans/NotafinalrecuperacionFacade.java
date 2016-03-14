/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import java.math.BigDecimal;
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
public class NotafinalrecuperacionFacade extends AbstractFacade<Notafinalrecuperacion> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotafinalrecuperacionFacade() {
        super(Notafinalrecuperacion.class);
    }
    public BigDecimal getNotaFinalRecuperacion(Notafinal notafinal){
        try {
            Query cq = em.createNamedQuery("Notafinalrecuperacion.findByNotafinal");
            cq.setParameter("notafinal", notafinal);
            if(cq.getResultList()!=null){
                return (BigDecimal)cq.getSingleResult();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
    public Notafinalrecuperacion getNotaFinalRecuperar(Notafinal notafinal){
        try {
            Query cq = em.createNamedQuery("Notafinalrecuperacion.findByNotafinalRecuperar");
            cq.setParameter("notafinal", notafinal);
            if(cq.getResultList()!=null){
                return (Notafinalrecuperacion)cq.getSingleResult();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
}
