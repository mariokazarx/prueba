/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.UsuarioRole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class UsuarioRoleFacade extends AbstractFacade<UsuarioRole> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioRoleFacade() {
        super(UsuarioRole.class);
    }
    
}
