/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Escala;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public boolean getByNombre(String nombre){
        try {
            Query cq = em.createNamedQuery("Escala.findByNombre");
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
