/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "contenidotematico", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenidotematico.findAll", query = "SELECT c FROM Contenidotematico c"),
    @NamedQuery(name = "Contenidotematico.findByReporte", query = "SELECT c FROM Contenidotematico c WHERE c.asignaturacicloId.asignaturaId = :asignatura AND c.cursoId = :curso AND c.periodoId= :periodo"),
    @NamedQuery(name = "Contenidotematico.findByProfesorAño", query = "SELECT c FROM Contenidotematico c WHERE c.cursoId.anlectivoId = :anlectivo and c.profesorId = :profesor"),
    @NamedQuery(name = "Contenidotematico.countByCursoPeriodo", query = "SELECT COUNT(c) FROM Contenidotematico c WHERE c.cursoId = :curso AND c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.findByCursoPeriodo", query = "SELECT c FROM Contenidotematico c WHERE c.cursoId = :curso AND c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.countAdvertenciaPeriodo", query = "SELECT COUNT(c) FROM Contenidotematico c WHERE c.estado.estadocontenidotematicoId = 5 AND c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.countPendientesPeriodo", query = "SELECT COUNT(c) FROM Contenidotematico c WHERE c.estado.estadocontenidotematicoId = 1 AND c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.updateIniciarPeriodo", query = "UPDATE Contenidotematico c SET c.estado = :estado WHERE c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.updateTerminarAño", query = "UPDATE Contenidotematico c SET c.estado = :estado WHERE c.cursoId.anlectivoId = :anlectivo"),
    @NamedQuery(name = "Contenidotematico.updateRetirarProfesor", query = "UPDATE Contenidotematico c SET c.estado = :estado WHERE c.periodoId = :periodo and c.profesorId = :profesor"),
    @NamedQuery(name = "Contenidotematico.findDiponiblePeriodoProfesor", query = "SELECT c FROM Contenidotematico c WHERE c.periodoId = :periodo AND c.profesorId = :profesor AND c.estado.estadocontenidotematicoId != 3 ORDER BY c.cursoId"),
    @NamedQuery(name = "Contenidotematico.findRectificarPeriodoProfesor", query = "SELECT c FROM Contenidotematico c WHERE c.periodoId = :periodo AND c.profesorId = :profesor AND c.estado.estadocontenidotematicoId = 3"),
    @NamedQuery(name = "Contenidotematico.findRectificarTodosProfesor", query = "SELECT c FROM Contenidotematico c WHERE c.profesorId = :profesor AND c.estado.estadocontenidotematicoId = 3"),
    @NamedQuery(name = "Contenidotematico.findRectificarProfesor", query = "SELECT c FROM Contenidotematico c WHERE c.profesorId = :profesor AND c.estado.estadocontenidotematicoId = 3"),
    @NamedQuery(name = "Contenidotematico.findRectificarProfesorAño", query = "SELECT c FROM Contenidotematico c WHERE c.profesorId = :profesor AND c.estado.estadocontenidotematicoId = 3 AND c.cursoId.anlectivoId = :anlectivo"),
    @NamedQuery(name = "Contenidotematico.findByPeriodo", query = "SELECT c FROM Contenidotematico c WHERE c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.findByPeriodoCurso", query = "SELECT c FROM Contenidotematico c WHERE c.periodoId = :periodo AND c.cursoId = :curso"),
    @NamedQuery(name = "Contenidotematico.removeByPeriodo", query = "DELETE FROM Contenidotematico c where c.periodoId = :periodo"), 
    @NamedQuery(name = "Contenidotematico.countByPeriodo", query = "SELECT COUNT(c) FROM Contenidotematico c JOIN c.notaList n WHERE c.periodoId = :periodo"),
    @NamedQuery(name = "Contenidotematico.findByContenidotematicoId", query = "SELECT c FROM Contenidotematico c WHERE c.contenidotematicoId = :contenidotematicoId"),
    @NamedQuery(name = "Contenidotematico.findCursoId", query = "SELECT DISTINCT c.cursoId FROM Contenidotematico c WHERE c.contenidotematicoId = :contenidotematicoId"),
    @NamedQuery(name = "Contenidotematico.findByAll", query = "SELECT c FROM Contenidotematico c WHERE c.cursoId = :cursoId and c.periodoId = :periodoId and c.profesorId = :profesorId and c.asignaturacicloId = :asignaturacicloId"),
    @NamedQuery(name = "Contenidotematico.findByCambio", query = "SELECT c FROM Contenidotematico c WHERE c.cursoId = :cursoId and c.periodoId = :periodoId  and c.asignaturacicloId = :asignaturacicloId"),
    @NamedQuery(name = "Contenidotematico.DeleteByProfesorCurso", query = "DELETE FROM Contenidotematico c WHERE c.profesorId = :profesorId AND c.cursoId = :cursoId")})
public class Contenidotematico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contenidotematico_id", nullable = false)
    private Integer contenidotematicoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidotematicoId", fetch = FetchType.LAZY)
    private List<Nota> notaList;
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profesor profesorId;
    @JoinColumn(name = "periodo_id", referencedColumnName = "periodo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Periodo periodoId;
    @JoinColumn(name = "estado", referencedColumnName = "estadocontenidotematico_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estadocontenidotematico estado;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoId;
    @JoinColumn(name = "asignaturaciclo_id", referencedColumnName = "asignaturaciclo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asignaturaciclo asignaturacicloId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidotematicoId", fetch = FetchType.LAZY)
    private List<Logro> logroList;

    public Contenidotematico() {
    }

    public Contenidotematico(Integer contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    public Integer getContenidotematicoId() {
        return contenidotematicoId;
    }

    public void setContenidotematicoId(Integer contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    public Profesor getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesor profesorId) {
        this.profesorId = profesorId;
    }

    public Periodo getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Periodo periodoId) {
        this.periodoId = periodoId;
    }

    public Estadocontenidotematico getEstado() {
        return estado;
    }

    public void setEstado(Estadocontenidotematico estado) {
        this.estado = estado;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Asignaturaciclo getAsignaturacicloId() {
        return asignaturacicloId;
    }

    public void setAsignaturacicloId(Asignaturaciclo asignaturacicloId) {
        this.asignaturacicloId = asignaturacicloId;
    }

    @XmlTransient
    public List<Logro> getLogroList() {
        return logroList;
    }

    public void setLogroList(List<Logro> logroList) {
        this.logroList = logroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contenidotematicoId != null ? contenidotematicoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenidotematico)) {
            return false;
        }
        Contenidotematico other = (Contenidotematico) object;
        if ((this.contenidotematicoId == null && other.contenidotematicoId != null) || (this.contenidotematicoId != null && !this.contenidotematicoId.equals(other.contenidotematicoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Contenidotematico[ contenidotematicoId=" + contenidotematicoId + " ]";
    }
    
}
