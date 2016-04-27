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
    public boolean existeCedula(String identificacion) {
        try {
            Query cq = em.createNamedQuery("Usuario.countCedula");
            cq.setParameter("identificacion",identificacion);
            if(cq.getSingleResult()!=null){
                Long count = (Long) cq.getSingleResult();
                System.out.println("Entra resultado "+count);
                if(count != 0){
                    return true;
                }else{
                    return false;
                }
            }
            else{
                System.out.println("ENTRA 2");
                return false;
            }
        } catch (PersistenceException e) {
            System.out.println("ENTRA 3");
            return false;
        }
        catch (Exception e) {
            System.out.println("ENTRA 4");
            return false;
        }
    }
    public boolean existeCorreo(String correo) {
        try {
            Query cq = em.createNamedQuery("Usuario.countCorreo");
            cq.setParameter("correo",correo);
            if(cq.getSingleResult()!=null){
                Long count = (Long) cq.getSingleResult();
                System.out.println("Entra resultado "+count);
                if(count != 0){
                    return true;
                }else{
                    return false;
                }
            }
            else{
                System.out.println("ENTRA 2");
                return false;
            }
        } catch (PersistenceException e) {
            System.out.println("ENTRA 3");
            return false;
        }
        catch (Exception e) {
            System.out.println("ENTRA 4");
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
                System.out.println("ENTRA 2");
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("ENTRA 3");
            return null;
        }
        catch (Exception e) {
            System.out.println("ENTRA 4");
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
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        

    }
}
