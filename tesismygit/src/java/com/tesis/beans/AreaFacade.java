/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Area;
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
public class AreaFacade extends AbstractFacade<Area> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AreaFacade() {
        super(Area.class);
    }

    public boolean removeById(Area area) {
        try {
            Query cq = em.createNamedQuery("Area.removeById");
            cq.setParameter("areaId", area.getAreaId());
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
    public List<Area> getByConfiguracion(Configuracion configuracion){
        try {
            Query cq = em.createNamedQuery("Area.findByConfiguraion");
            cq.setParameter("configuracion",configuracion);
            if(cq.getResultList()!=null){
                return cq.getResultList();
            }
            else{
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            System.out.println("llega c");
            e.printStackTrace();
            return null;
        }
        
    }
    
}
