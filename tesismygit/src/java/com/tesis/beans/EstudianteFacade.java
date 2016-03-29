/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
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
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    public List<Estudiante> findByCurso(Curso curso) {
        Query cq = em.createNamedQuery("Estudiante.findByCurso");
        cq.setParameter("cursoId", curso);
        return cq.getResultList();
    }

    public Logronota findNotaEstudiante(Logro logro, Estudiante estudiante) {
        try {
            //em.flush();
            Query cq = em.createNamedQuery("Estudiante.findNotaLOgro");
            cq.setParameter("logroId", logro);
            cq.setParameter("estudianteId", estudiante.getEstudianteId());
            if (cq.getSingleResult() != null) {
                //System.out.println("***ENCONTRO****");
                return (Logronota) cq.getSingleResult();
            } else {
                //System.out.println("***NO ENCONTRO****");
                return null;
            }
        } catch (Exception e) {
            //System.out.println("***ERROR****"+e.toString());
            return null;
        }
    }
    public Estudiante getEstudianteByIdentificacion(String identificacion) {
        try {
            Query cq = em.createNamedQuery("Estudiante.findByIdentificiacion");
            cq.setParameter("identificiacion", identificacion);
            if (cq.getSingleResult() != null) {
                //System.out.println("***ENCONTRO****");
                return (Estudiante) cq.getSingleResult();
            } else {
                //System.out.println("***NO ENCONTRO****");
                return null;
            }
        } catch (Exception e) {
            //System.out.println("***ERROR****"+e.toString());
            return null;
        }
    }

    public Nota findNotaEst(Contenidotematico contenido, Estudiante estudiante) {
        try {
            Query cq = em.createNamedQuery("Estudiante.findNotaEst");
            cq.setParameter("contenidotematicoId", contenido);
            cq.setParameter("estudianteId", estudiante.getEstudianteId());
            if (cq.getSingleResult() != null) {
                //System.out.println("***ENCONTRO****");
                return (Nota) cq.getSingleResult();
            } else {
                //System.out.println("***NO ENCONTRO****");
                return null;
            }
        } catch (Exception e) {
            //System.out.println("***ERROR****"+e.toString());
            return null;
        }
    }
    public List<Object> getFinalPeriodo(Curso curso,Periodo periodo){
        try {
            Query cq = em.createNamedQuery("Estudiante.findPromedioNotaPeriodo");
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
    public List<Estudiante> getOrdenados(){
        try {
            Query cq = em.createNamedQuery("Estudiante.findAllOrdenado");
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
    public boolean existeIdentificacion(String identificacion) {
        try {
            Query cq = em.createNamedQuery("Estudiante.countCedula");
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
    private static void printResult(Object result) {
    if (result == null) {
      System.out.print("NULL");
    } else if (result instanceof Object[]) {
      Object[] row = (Object[]) result;
      System.out.print("[");
      for (int i = 0; i < row.length; i++) {
        printResult(row[i]);
      }
      System.out.print("]");
    } else if (result instanceof Long || result instanceof Double
        || result instanceof String) {
      System.out.print(result.getClass().getName() + ": " + result);
    } else {
      System.out.print(result);
    }
    System.out.println();
  }

}
