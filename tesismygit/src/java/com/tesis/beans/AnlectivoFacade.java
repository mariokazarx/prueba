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
    public Configuracion getConfiguracionCurso(Curso curso){
        Query cq = em.createNamedQuery("Anlectivo.findConfoguracionCurso");
        cq.setParameter("cursoId",curso.getCursoId());
        return (Configuracion) cq.getSingleResult();
}
}
