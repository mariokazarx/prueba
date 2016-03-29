/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Periodo;
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
public class PeriodoFacade extends AbstractFacade<Periodo> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoFacade() {
        super(Periodo.class);
    }

    public Periodo getPeriodoMinByConfiguracion(Configuracion conf) {
        Query cq = em.createNamedQuery("Periodo.findMinByConfiguracion");
        cq.setParameter("configuracionId", conf);
        return (Periodo) cq.getSingleResult();
    }

    public Periodo getPeriodoMinByAnio(Anlectivo anio) {
        try {
            Query cq = em.createNamedQuery("Periodo.findMinByAnio");
            cq.setParameter("anlectivoId", anio);
            if (cq.getResultList() != null) {
                return (Periodo) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            //e.printStackTrace();
            return null;
        }
    }
    public Periodo getPeriodoMaxByAnio(Anlectivo anio) {
        try {
            Query cq = em.createNamedQuery("Periodo.findMaxByAnio");
            cq.setParameter("anlectivoId", anio);
            if (cq.getResultList() != null) {
                return (Periodo) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            //e.printStackTrace();
            return null;
        }
    }
    public Integer getMaxByAnio(Anlectivo anio) {
        try {
            Query cq = em.createNamedQuery("Periodo.findMax");
            cq.setParameter("anlectivoId", anio);
            if (cq.getResultList() != null) {
                return (Integer) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            e.printStackTrace();
            return null;
        }
    }
    public List<Periodo> getPeriodosByAnioActivo(Anlectivo anlectivoId) {
        try {
            Query cq = em.createNamedQuery("Periodo.findActivoByAnio");
            cq.setParameter("anlectivoId", anlectivoId);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            //e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            //e.printStackTrace();
            return null;
        }
    }
    public List<Periodo> getPeriodosByAnioTerminados(Anlectivo anlectivoId) {
        try {
            Query cq = em.createNamedQuery("Periodo.findPeriodosTerminados");
            cq.setParameter("anlectivo", anlectivoId);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            e.printStackTrace();
            return null;
        }
    }
    public List<Periodo> getPeriodosByConfiguracion(Configuracion conf) {
        Query cq = em.createNamedQuery("Periodo.findByConfiguracion");
        cq.setParameter("configuracionId", conf);
        return cq.getResultList();
    }

    public List<Periodo> getPeriodosByAnio(Anlectivo anlectivoId) {
        try {
            Query cq = em.createNamedQuery("Periodo.findByAnio");
            cq.setParameter("anlectivoId", anlectivoId);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            //e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            //e.printStackTrace();
            return null;
        }
    }
    public boolean enUso(Anlectivo anlectivo) {
        Long result = new Long(0);
        try {
            Query cq = em.createNamedQuery("Periodo.countPeriodoUso");
            cq.setParameter("anlectivo", anlectivo);
            if(cq.getSingleResult()!=null){
                result = (Long) cq.getSingleResult();
                System.out.println("entro 1 "+result+"aaa"+anlectivo);
                if(result>0){
                    return true;
                }else{
                    return false;
                }
            }
            else{
                System.out.println("entro 2");
                return false;
            }
        } catch (PersistenceException e) {
            System.out.println("entro 3");
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            System.out.println("entro 4");
            e.printStackTrace();
            return false;
        }
    }
    public Long getNumeroPeriodosAño(Anlectivo anlectivo) {
        Long result = new Long(0);
        try {
            Query cq = em.createNamedQuery("Periodo.countPeriodoByaño");
            cq.setParameter("anlectivo", anlectivo);
            if(cq.getSingleResult()!=null){
                result = (Long) cq.getSingleResult();
                System.out.println("entro 1 "+result+"aaa"+anlectivo);
                return result;
            }
            else{
                System.out.println("entro 2");
                return result;
            }
        } catch (PersistenceException e) {
            System.out.println("entro 3");
            e.printStackTrace();
            return result;
        }
        catch (Exception e) {
            System.out.println("entro 4");
            e.printStackTrace();
            return result;
        }
    }
    public Long getNumeroPeriodosTerminadosAño(Anlectivo anlectivo) {
        Long result = new Long(0);
        try {
            Query cq = em.createNamedQuery("Periodo.countPeriodoTerminadosByaño");
            cq.setParameter("anlectivo", anlectivo);
            if(cq.getSingleResult()!=null){
                result = (Long) cq.getSingleResult();
                System.out.println("entro 1 "+result+"aaa"+anlectivo);
                return result;
            }
            else{
                System.out.println("entro 2");
                return result;
            }
        } catch (PersistenceException e) {
            System.out.println("entro 3");
            e.printStackTrace();
            return result;
        }
        catch (Exception e) {
            System.out.println("entro 4");
            e.printStackTrace();
            return result;
        }
    }
    public boolean removeById(Periodo periodo) {
        try {
            Query cq = em.createNamedQuery("Periodo.removeById");
            cq.setParameter("periodoId", periodo.getPeriodoId());
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
    public Periodo getPeriodEvaluar(Anlectivo anio) {
        try {
            Query cq = em.createNamedQuery("Periodo.findPeriodoEvaluar");
            cq.setParameter("anlectivo", anio);
            if (cq.getResultList() != null) {
                return (Periodo) cq.getSingleResult();
            } else {
                System.out.println("llega d");
                return null;
            }
        } catch (PersistenceException e) {
            System.out.println("llega b");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("llega c");
            //e.printStackTrace();
            return null;
        }
    }
}
