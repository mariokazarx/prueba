/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Nota;
import java.math.BigDecimal;
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
public class NotaFacade extends AbstractFacade<Nota> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotaFacade() {
        super(Nota.class);
    }
    public Double getNotaFinal(Anlectivo alectivo,Estudiante est,Curso curso,Asignaturaciclo asignatura){
        try {
            System.out.println("MM QUE SERA::::"+alectivo.toString()+"aaa"+est.toString()+"bbb"+curso.toString());
            Query cq = em.createNamedQuery("Nota.findNotaFinal");
            cq.setParameter("cursoId", curso);
            cq.setParameter("estudianteId", est);
            cq.setParameter("anlectivoId", alectivo);
            cq.setParameter("asignatura", asignatura);
            if(cq.getResultList()!=null){
                return (Double)cq.getSingleResult();
            }
            else{
                System.out.println("MM QUE SERA::::222");
                return null;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public BigDecimal getNotaFinalPeriodo(Contenidotematico contenido,Estudiante estudiante){
        try {
            Query cq = em.createNamedQuery("Nota.findByFinal");
            cq.setParameter("estudiante", estudiante);
            cq.setParameter("contenido", contenido);
            if(cq.getResultList()!=null){
                return (BigDecimal)cq.getSingleResult();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
