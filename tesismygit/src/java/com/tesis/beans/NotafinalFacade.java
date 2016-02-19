/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Notafinal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class NotafinalFacade extends AbstractFacade<Notafinal> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotafinalFacade() {
        super(Notafinal.class);
    }
    public Notafinal findNotaFinalActual(Asignaturaciclo asc,Estudiante est){
        try {
            Query cq = em.createNamedQuery("Notafinal.findByActual");
            cq.setParameter("asignaturacicloId", asc);
            cq.setParameter("estudianteId", est);
            if(cq.getResultList()!=null){
                return (Notafinal)cq.getSingleResult();
            }
            else{
                System.out.println("1");
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("2");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("3"+e.toString());
            return null;
        }
        
    }
}
