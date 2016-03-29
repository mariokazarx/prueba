/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Profesor;
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
public class ProfesorFacade extends AbstractFacade<Profesor> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesorFacade() {
        super(Profesor.class);
    }
    public Profesor getByCorreo(String correo) {
        try {
            Query cq = em.createNamedQuery("Profesor.findByCorreo");
            cq.setParameter("correo", correo);
            System.out.println("RESULTQDO USUARIO" + cq.getSingleResult());
            if (cq.getSingleResult() == null) {
                return null;
            } else {
                return (Profesor) cq.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }
    public boolean existeCedula(String cedula) {
        try {
            Query cq = em.createNamedQuery("Profesor.countCedula");
            cq.setParameter("cedula",cedula);
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
            Query cq = em.createNamedQuery("Profesor.countCorreo");
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
}
