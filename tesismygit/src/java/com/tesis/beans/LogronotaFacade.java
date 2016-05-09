/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Periodo;
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
public class LogronotaFacade extends AbstractFacade<Logronota> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogronotaFacade() {
        super(Logronota.class);
    }
    public Logronota getByLogroestudiante(Estudiante est,Logro logro){
        try {
            Query cq = em.createNamedQuery("Logronota.findByLogroEstudiante");
            cq.setParameter("estudiante", est);
            cq.setParameter("logro", logro);
            if(cq.getSingleResult()!=null){
                return (Logronota) cq.getSingleResult();
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
    public boolean tieneNotas(Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Logronota.countNotasPeriodo");
            cq.setParameter("periodo", periodo);
            if(cq.getSingleResult()!=null){
                Long count = (Long) cq.getSingleResult();
                if(count != 0){
                    return true;
                }else{
                    return false;
                }
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
