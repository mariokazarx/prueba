/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Curso;
import java.util.List;
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
public class CicloFacade extends AbstractFacade<Ciclo> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CicloFacade() {
        super(Ciclo.class);
    }
    public List<Ciclo> getByConfiguracion(Configuracion configuracion){
        try {
            Query cq = em.createNamedQuery("Ciclo.findByConfiguracion");
            cq.setParameter("configuracion",configuracion);
            if(cq.getResultList()==null){
                return null;
            }else{
                return cq.getResultList();
            }
        } catch (Exception e) {
            return null;
        }
    }
    public Ciclo getCicloByNum(int numero){
        Query cq = em.createNamedQuery("Ciclo.findByNumero");
        cq.setParameter("numero",numero);
        return (Ciclo) cq.getSingleResult();
    }

    public boolean removeById(Ciclo ciclo) {
        try {
            Query cq = em.createNamedQuery("Ciclo.removeById");
            cq.setParameter("cicloId", ciclo.getCicloId());
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
