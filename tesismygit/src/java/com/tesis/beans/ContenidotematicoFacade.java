/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estadocontenidotematico;
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
public class ContenidotematicoFacade extends AbstractFacade<Contenidotematico> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContenidotematicoFacade() {
        super(Contenidotematico.class);
    }
    public int removeByProfesorCurso(Profesor profesor,Curso curso) {
        Query cq = em.createNamedQuery("Contenidotematico.DeleteByProfesorCurso");
        cq.setParameter("profesorId",profesor);
        cq.setParameter("cursoId",curso);
        return cq.executeUpdate();
    }
    public Contenidotematico getContenidoByAll(Profesor profesor,Curso curso,Asignaturaciclo asinaturaciclo,Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findByAll");
            cq.setParameter("profesorId",profesor);
            cq.setParameter("cursoId",curso);
            cq.setParameter("asignaturacicloId",asinaturaciclo);
            cq.setParameter("periodoId",periodo);
            if(cq.getSingleResult()!=null){
                return (Contenidotematico) cq.getSingleResult();
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
    public Contenidotematico getContenidoByCambio(Curso curso,Asignaturaciclo asinaturaciclo,Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findByCambio");
            cq.setParameter("cursoId",curso);
            cq.setParameter("asignaturacicloId",asinaturaciclo);
            cq.setParameter("periodoId",periodo);
            if(cq.getSingleResult()!=null){
                return (Contenidotematico) cq.getSingleResult();
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
    public boolean tieneNotas(Periodo periodo) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.countByPeriodo");
            cq.setParameter("periodo", periodo);
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
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean tieneAdvertencias(Periodo periodo) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.countAdvertenciaPeriodo");
            cq.setParameter("periodo", periodo);
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
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeByPeriodo(Periodo periodo) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.removeByPeriodo");
            cq.setParameter("periodo", periodo);
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
    public boolean updateIniciarPeriodo(Periodo periodo,Estadocontenidotematico estado) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.updateIniciarPeriodo");
            cq.setParameter("periodo", periodo);
            cq.setParameter("estado", estado);
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
    public boolean updateTerminarAño(Anlectivo anlectivo,Estadocontenidotematico estado) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.updateTerminarAño");
            cq.setParameter("anlectivo", anlectivo);
            cq.setParameter("estado", estado);
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
    public boolean updateRetirarProfesor(Periodo periodo,Estadocontenidotematico estado,Profesor profesor) {
        try {
            Query cq = em.createNamedQuery("Contenidotematico.updateRetirarProfesor");
            cq.setParameter("periodo", periodo);
            cq.setParameter("estado", estado);
            cq.setParameter("profesor", profesor);
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
    public List<Contenidotematico> getByPeriodo(Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findByPeriodo");
            cq.setParameter("periodo", periodo);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public List<Contenidotematico> getByPeriodoCurso(Periodo periodo,Curso curso){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findByCursoPeriodo");
            cq.setParameter("periodo", periodo);
            cq.setParameter("curso", curso);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public List<Contenidotematico> getRectificar(Periodo periodo,Profesor profesor){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findDiponiblePeriodoProfesor");
            cq.setParameter("periodo", periodo);
            cq.setParameter("profesor", profesor);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public List<Contenidotematico> getRectificar(Profesor profesor){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findRectificarProfesor");
            cq.setParameter("profesor", profesor);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public List<Contenidotematico> getRectificarAño(Profesor profesor,Anlectivo anlectivo){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findRectificarProfesorAño");
            cq.setParameter("profesor", profesor);
            cq.setParameter("anlectivo", anlectivo);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public List<Contenidotematico> getRectificados(Periodo periodo,Profesor profesor){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findRectificarPeriodoProfesor");
            cq.setParameter("periodo", periodo);
            cq.setParameter("profesor", profesor);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
    public Long countAsignaturasCursoPerido(Curso curso,Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.countByCursoPeriodo");
            cq.setParameter("curso", curso);
            cq.setParameter("periodo", periodo);
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
    /*public List<Contenidotematico> getByPeriodoCurso(Periodo periodo,Curso curso){
        try {
            Query cq = em.createNamedQuery("Contenidotematico.findByPeriodoCurso");
            cq.setParameter("periodo", periodo);
            cq.setParameter("curso", curso);
            if(cq.getResultList()!=null){
                return cq.getResultList();
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
        
    }*/
}
