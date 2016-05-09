/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
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
public class AnlectivoFacade extends AbstractFacade<Anlectivo> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnlectivoFacade() {
        super(Anlectivo.class);
    }

    public Configuracion getConfiguracionCurso(Curso curso) {
        Query cq = em.createNamedQuery("Anlectivo.findConfoguracionCurso");
        cq.setParameter("cursoId", curso.getCursoId());
        return (Configuracion) cq.getSingleResult();
    }

    public boolean existeConfiguracion(Configuracion configuracion) {
        boolean bandera;
        try {
            Query cq = em.createNamedQuery("Anlectivo.findConfiguracion");
            cq.setParameter("configuracionId", configuracion);
            if (cq.getSingleResult() != null) {
                Long count = (Long) cq.getSingleResult();
                if(count != 0){
                    return true;
                }else{
                    return false;
                }
            } else {
                bandera = false;
            }
        } catch (Exception e) {
            bandera = false;
        }
        return bandera;
    }
    public boolean escalaEnUso(Escala escala) {
        try {
            Query cq = em.createNamedQuery("Anlectivo.escalaEnUso");
            cq.setParameter("escala", escala);
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
    public boolean existeAño(int anio) {
        try {
            Query cq = em.createNamedQuery("Anlectivo.añoEnUso");
            cq.setParameter("anio",anio);
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
    public boolean criterioEnUso(Criterioevaluacion criterio) {
        try {
            Query cq = em.createNamedQuery("Anlectivo.criterioEnUso");
            cq.setParameter("criterio", criterio);
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
    public boolean configuracionEnUso(Configuracion configuracion) {
        try {
            Query cq = em.createNamedQuery("Anlectivo.configuracionEnUso");
            cq.setParameter("configuracion", configuracion);
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
    public boolean existeConfiguracionEnUso(Configuracion configuracion) {
        boolean bandera;
        try {
            Query cq = em.createNamedQuery("Anlectivo.findConfiguracionUso");
            cq.setParameter("configuracionId", configuracion);
            if (cq.getSingleResult() != null) {
                bandera = true;
            } else {
                bandera = false;
            }
        } catch (Exception e) {
            bandera = false;
        }
        return bandera;
    }

    public boolean removeById(Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Anlectivo.removeById");
            cq.setParameter("anlectivoId", anlectivo.getAnlectivoId());
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
    public boolean enUso(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findByUso");
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
    public boolean existActivo(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findActivo");
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
    public boolean existIniciado(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findIniciado");
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
    public boolean existIniciadoNew(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findIniciado");
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
    
    public Anlectivo getIniciado(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findIniciadoObj");
            if(cq.getSingleResult()!=null){
                return (Anlectivo) cq.getSingleResult();
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
    public List<Anlectivo> getEnUso(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findUso");
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
    public List<Anlectivo> getTerminados(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findAnlectivosfinalizados");
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
    public List<Anlectivo> getAñosEnUso(){
        try {
            Query cq = em.createNamedQuery("Anlectivo.findAnlectivoUtil");
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
