/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Curso;
import com.tesis.entity.Periodo;
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
public class CursoFacade extends AbstractFacade<Curso> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }

    public List<Curso> findCursosProfeso(Profesor profesor, Periodo periodo) {
        System.out.println("MMMM" + profesor.getNombre());
        Query cq = em.createNamedQuery("Curso.findByCursoProfesor");
        cq.setParameter("profesorId", profesor);
        cq.setParameter("periodoId", periodo);
        return cq.getResultList();
    }
    public List<Curso> getCursosByAño(Anlectivo anlectivo){
        try {
            Query cq = em.createNamedQuery("Curso.findByAño");
            cq.setParameter("anlectivo",anlectivo);
            if(cq.getResultList()==null){
                return null;
            }else{
                return cq.getResultList();
            }
        } catch (Exception e) {
            return null;
        }
    }
    public boolean removeById(Curso curso) {
        try {
            Query cq = em.createNamedQuery("Curso.removeById");
            cq.setParameter("cursoId", curso.getCursoId());
            if(cq.executeUpdate()>0){
                return true;
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
}
