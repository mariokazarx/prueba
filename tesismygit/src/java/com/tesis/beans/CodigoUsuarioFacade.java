/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.CodigoUsuario;
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
public class CodigoUsuarioFacade extends AbstractFacade<CodigoUsuario> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodigoUsuarioFacade() {
        super(CodigoUsuario.class);
    }
    public CodigoUsuario getValido(String codigo){
        try {
            Query cq = em.createNamedQuery("CodigoUsuario.findByCodigoVigente");
            cq.setParameter("codigo", codigo);
            if(cq.getSingleResult()!=null){
                return (CodigoUsuario)cq.getSingleResult();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
