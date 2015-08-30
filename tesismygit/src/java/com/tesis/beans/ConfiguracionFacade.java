/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Configuracion;
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

    public boolean getByNombre(String nombre) {
        try {
            Query cq = em.createNamedQuery("Configuracion.findByNombre");
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

    public boolean removeById(Configuracion configuracion) {
        try {
            Query cq = em.createNamedQuery("Configuracion.removeById");
            cq.setParameter("configuracionId", configuracion.getConfiguracionId());
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
    public boolean enUso(Configuracion configuracion) {
        try {
            Query cq = em.createNamedQuery("Configuracion.enUso");
            cq.setParameter("configuracionId", configuracion.getConfiguracionId());
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
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
