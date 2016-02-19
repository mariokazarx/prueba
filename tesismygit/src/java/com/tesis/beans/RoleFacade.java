/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Role;
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
public class RoleFacade extends AbstractFacade<Role> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }
    
    public boolean removeById(Integer roleId) {
        try {
            Query cq = em.createNamedQuery("Role.removeByRoleId");
            cq.setParameter("roleId", roleId);
            if(cq.executeUpdate()>0){
                return true;
            }
            else{
                System.out.println("ERROR 1"+roleId);
                return false;
            }
        }
        catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR 2"+roleId);
            return false;
        }
        

    }
}
