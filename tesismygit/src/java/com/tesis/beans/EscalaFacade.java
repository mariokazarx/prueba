/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Escala;
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
public class EscalaFacade extends AbstractFacade<Escala> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EscalaFacade() {
        super(Escala.class);
    }

    public List<Escala> findAllOrder() {
        Query cq = em.createNamedQuery("Escala.findAllOrder");
        return cq.getResultList();
    }

    public boolean getByNombre(String nombre) {
        try {
            Query cq = em.createNamedQuery("Escala.findByNombre");
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

    public boolean removeById(Escala escala) {
        try {
            Query cq = em.createNamedQuery("Escala.removeById");
            cq.setParameter("escalaId", escala.getEscalaId());
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
    /*public User getUserByUsernameOrNull(String username) {
     try{
     Query q = em.createNamedQuery(User.getUserByUsername);
     q.setParameter("username", username);
     return (User) q.getSingleResult();
     } catch(NoResultException e) {
     return null;
     }
     }*/
}
