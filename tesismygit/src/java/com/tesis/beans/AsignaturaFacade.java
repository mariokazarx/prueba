/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Asignatura;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
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
public class AsignaturaFacade extends AbstractFacade<Asignatura> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturaFacade() {
        super(Asignatura.class);
    }
    public List<Asignatura> findByCiclo(Ciclo ciclo) {
        Query cq = em.createNamedQuery("Asignatura.findByCiclo");
        cq.setParameter("ciclo",ciclo);
        return cq.getResultList();
        //System.out.println("aa"+ciclo);
        //return null;
    }
    public List<Asignatura> findByConfiguracion(Configuracion conf,Ciclo ciclo) {
        Query cq = em.createNamedQuery("Asignatura.findByConf");
        cq.setParameter("ciclo",ciclo);
        cq.setParameter("configuracion",conf);
        return cq.getResultList();
        //System.out.println("aa"+ciclo);
        //return null;
    }
    public List<Asignatura> findByConfiguracionNew(Configuracion conf) {
        try {
            Query cq = em.createNamedQuery("Asignatura.findByConfiguracion");
            cq.setParameter("configuracion",conf);
            if(cq.getResultList()==null){
                return null;
            }else{
                return cq.getResultList();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeById(Asignatura asignatura) {
        try {
            Query cq = em.createNamedQuery("Asignatura.removeById");
            cq.setParameter("asignaturaId", asignatura.getAsignaturaId());
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
