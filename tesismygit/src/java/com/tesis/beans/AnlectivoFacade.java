/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Curso;
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
                bandera = true;
            } else {
                bandera = false;
            }
        } catch (Exception e) {
            System.out.println("ERROR[]"+e.toString());
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
}
