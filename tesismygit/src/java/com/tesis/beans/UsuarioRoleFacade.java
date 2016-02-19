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
    public boolean removeByUsuario(Usuario usuario) {
        try {
            Query cq = em.createNamedQuery("UsuarioRole.removeByUsuarioId");
            cq.setParameter("usuario", usuario);
            if(cq.executeUpdate()>0){
                System.out.println("EXITOSO BORRAR");
                return true;
            }
            else{
                System.out.println("ERRO GRAVE 1");
                return false;
            }
        } catch (Exception e) {
            System.out.println("ERRO GRAVE 2");
            return false;
        }

    }
    public List<UsuarioRole> getByUser(Usuario usr){
        try {
            Query cq = em.createNamedQuery("UsuarioRole.findByUsuarioId");
            cq.setParameter("usuario", usr);
            if(cq.getResultList()!=null){
                return cq.getResultList();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            System.out.println("llega c");
            e.printStackTrace();
            return null;
        }
        
    }
}
