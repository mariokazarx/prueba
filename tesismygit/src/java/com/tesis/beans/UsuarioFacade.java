/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    public Usuario getByCorreo(String correo) {
        try {
            Query cq = em.createNamedQuery("Usuario.findByCorreo");
            cq.setParameter("correo", correo);
            System.out.println("RESULTQDO USUARIO" + cq.getSingleResult());
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Usuario) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
