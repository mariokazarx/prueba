/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.beans;

import com.tesis.entity.Anlectivo;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
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
public class MatriculaFacade extends AbstractFacade<Matricula> {

    @PersistenceContext(unitName = "tesismygitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }

    public Matricula getActivaByEstudiante(Estudiante est) {
        try {
            Query cq = em.createNamedQuery("Matricula.findMatriculaActiva");
            cq.setParameter("estudiante", est);
            if (cq.getSingleResult() != null) {
                return (Matricula) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("ERROR 2"+e.toString());
            return null;
        }

    }

    public Matricula getSuspendidaByEstudiante(Estudiante est, Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Matricula.findMatriculaSuspendida");
            cq.setParameter("estudiante", est);
            cq.setParameter("anlectivo", anlectivo);
            if (cq.getSingleResult() != null) {
                return (Matricula) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Matricula getAñoByEstudiante(Estudiante est, Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Matricula.findEstudianteAño");
            cq.setParameter("estudiante", est);
            cq.setParameter("anlectivo", anlectivo);
            if (cq.getSingleResult() != null) {
                return (Matricula) cq.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<Matricula> matriculasCurso(Curso curso) {
        try {
            Query cq = em.createNamedQuery("Matricula.findMatriculaByCurso");
            cq.setParameter("curso", curso);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List<Matricula> getMatriculasEstudiante(Estudiante est) {
        try {
            Query cq = em.createNamedQuery("Matricula.findEstudianteTerminadas");
            cq.setParameter("estudiante", est);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List<Matricula> matriculasTerminadasCurso(Curso curso) {
        try {
            Query cq = em.createNamedQuery("Matricula.findMatriculaTerminadaByCurso");
            cq.setParameter("curso", curso);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public List<Matricula> getMatriculasAnio(Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Matricula.findMatAño");
            cq.setParameter("anlectivoId", anlectivo);
            if (cq.getResultList() != null) {
                return cq.getResultList();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getAprobadosMatriucula(Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Matricula.countAprobo");
            cq.setParameter("anlectivoId", anlectivo);
            if (cq.getSingleResult() != null) {
                Long result = (Long) cq.getSingleResult();
                return result.intValue();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getReprobadosMatriucula(Anlectivo anlectivo) {
        try {
            Query cq = em.createNamedQuery("Matricula.countReprobo");
            cq.setParameter("anlectivoId", anlectivo);
            if (cq.getSingleResult() != null) {
                Long result = (Long) cq.getSingleResult();
                return result.intValue();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer countMatriculadosCurso(Curso curso) {
        try {
            Query cq = em.createNamedQuery("Matricula.countByCurso");
            cq.setParameter("curso", curso);
            if (cq.getSingleResult() != null) {
                Long result = (Long) cq.getSingleResult();
                return result.intValue();
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean tieneMatricula(Estudiante estudiante) {
        try {
            Query cq = em.createNamedQuery("Matricula.countByEstudiante");
            cq.setParameter("estudiante", estudiante);
            if (cq.getSingleResult() != null) {
                Long result = (Long) cq.getSingleResult();
                if (result != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
