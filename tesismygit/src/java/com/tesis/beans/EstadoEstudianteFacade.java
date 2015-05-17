/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.EstadoEstudiante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mario Jurado
 */
@Stateless
public class EstadoEstudianteFacade extends AbstractFacade<EstadoEstudiante> {
    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoEstudianteFacade() {
        super(EstadoEstudiante.class);
    }
    
}
