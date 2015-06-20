/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Configuracion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class ConfiguracionFacade extends AbstractFacade<Configuracion> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionFacade() {
        super(Configuracion.class);
    }
    public boolean getByNombre(String nombre){
        try {
            Query cq = em.createNamedQuery("Configuracion.findByNombre");
            cq.setParameter("nombre",nombre);
            if(cq.getSingleResult()==null){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    
    }
}
