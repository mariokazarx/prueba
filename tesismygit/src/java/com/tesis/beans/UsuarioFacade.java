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
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Usuario) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
    public boolean existeCedula(String identificacion) {
        try {
            Query cq = em.createNamedQuery("Usuario.countCedula");
            cq.setParameter("identificacion",identificacion);
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
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
    public boolean existeCorreo(String correo) {
        try {
            Query cq = em.createNamedQuery("Usuario.countCorreo");
            cq.setParameter("correo",correo);
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
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
    public List<Usuario> usersAdmin() {
        try {
            Query cq = em.createNamedQuery("Usuario.findByAdmins");
            if(cq.getResultList() != null){
                return cq.getResultList();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
    public boolean removeById(Integer usuarioId) {
        try {
            Query cq = em.createNamedQuery("Usuario.removeById");
            cq.setParameter("usrid", usuarioId);
            if(cq.executeUpdate()>0){
                return true;
            }
            else{
                return false;
            }
        }
        catch (PersistenceException e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
        

    }
}
