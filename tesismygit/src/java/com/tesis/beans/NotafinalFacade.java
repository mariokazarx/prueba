/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Profesor;
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
    public Notafinal findNotaFinalActual(Asignaturaciclo asc,Estudiante est,Anlectivo aEscolar){
        try {
            Query cq = em.createNamedQuery("Notafinal.findByActual");
            cq.setParameter("asignaturacicloId", asc);
            cq.setParameter("estudianteId", est);
            cq.setParameter("anlectivo", aEscolar);
            if(cq.getResultList()!=null){
                return (Notafinal)cq.getSingleResult();
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
    public List<Notafinal> findByRecuperacion(Asignaturaciclo asc,Profesor profesor,Anlectivo aEscolar){
        try {
            Query cq = em.createNamedQuery("Notafinal.findDetailRecuperacion");
            cq.setParameter("asignatura", asc);
            cq.setParameter("profesor", profesor);
            cq.setParameter("anlectivo", aEscolar);
            if(cq.getResultList()!=null){
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
}
