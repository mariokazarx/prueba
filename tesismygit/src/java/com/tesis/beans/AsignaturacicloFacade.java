/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
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
public class AsignaturacicloFacade extends AbstractFacade<Asignaturaciclo> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturacicloFacade() {
        super(Asignaturaciclo.class);
    }
    public int removeByAsignatura(Asignatura asig) {
        Query cq = em.createNamedQuery("Asignaturaciclo.removeByAsignatura");
        cq.setParameter("asignatura",asig);
        return cq.executeUpdate();
    }
    public List<Asignaturaciclo> asignaturasProfesor(Profesor profesor,Curso curso,Periodo periodo){
        Query cq = em.createNamedQuery("Asignaturaciclo.asignaturasProfesor");
        cq.setParameter("cursoId",curso);
        cq.setParameter("periodoId",periodo);
        cq.setParameter("profesorId",profesor);
        return cq.getResultList();
    }
    public List<Asignaturaciclo> asignaturasProfesorPeriodo(Profesor profesor,Curso curso,Periodo periodo){
        Query cq = em.createNamedQuery("Asignaturaciclo.asignaturasProfesorPeriodo");
        cq.setParameter("cursoId",curso);
        cq.setParameter("periodoId",periodo);
        cq.setParameter("profesorId",profesor);
        return cq.getResultList();
    }
    public List<Asignaturaciclo> asignaturasDisponibles(Profesor profesor,Curso curso){
        Query cq = em.createNamedQuery("Asignaturaciclo.asignaturasDisponibles");
        cq.setParameter("cursoId",curso.getCursoId());
        cq.setParameter("cursoIdC",curso);
        //cq.setParameter("profesorId",profesor);
        return cq.getResultList();
    }
    public List<Asignaturaciclo> asignaturasDisponiblesPeriodo(Profesor profesor,Curso curso,Periodo periodo){
        Query cq = em.createNamedQuery("Asignaturaciclo.asignaturasDisponiblesPeriodo");
        cq.setParameter("cursoId",curso.getCursoId());
        cq.setParameter("cursoIdC",curso);
        cq.setParameter("periodo",periodo);
        return cq.getResultList();
    } 
    public Asignaturaciclo asignaturasCiclo(Ciclo ciclo,Asignatura asg){
        Query cq = em.createNamedQuery("Asignaturaciclo.findByCicloAsig");
        cq.setParameter("cicloId",ciclo);
        cq.setParameter("asignaturaId",asg);
        return (Asignaturaciclo) cq.getSingleResult();
    }
     public Long asignaturasAprobadasAnio(Integer valor,Estudiante est,Curso curso){
        try {
            Query cq = em.createNamedQuery("Asignaturaciclo.countAprobadas");
            cq.setParameter("cursoId", curso);
            cq.setParameter("estudianteId", est);
            cq.setParameter("valor", valor);
            if(cq.getResultList()!=null){
                return (Long)cq.getSingleResult();
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
     public List<Asignaturaciclo> asignaturasCiclo(Ciclo ciclo){
        try {
            Query cq = em.createNamedQuery("Asignaturaciclo.findByCiclo");
            cq.setParameter("ciclo", ciclo);
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
     public Long countAsignaturasCiclo(Ciclo ciclo){
        try {
            Query cq = em.createNamedQuery("Asignaturaciclo.countByCiclo");
            cq.setParameter("ciclo", ciclo);
            if(cq.getResultList()!=null){
                return (Long)cq.getSingleResult();
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
