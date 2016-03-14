/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "matricula", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m"),
    @NamedQuery(name = "Matricula.findEstudianteTerminadas", query = "SELECT m FROM Matricula m where m.estudianteId = :estudiante AND m.estadoMatriculaId.estadoMatriculaId = 4 ORDER BY m.cursoId.anlectivoId.anio"),
    @NamedQuery(name = "Matricula.findMatAño", query = "SELECT m FROM Matricula m WHERE m.cursoId.anlectivoId = :anlectivoId AND (m.estadoMatriculaId.estadoMatriculaId != 2 or m.estadoMatriculaId.estadoMatriculaId != 3)"),
    @NamedQuery(name = "Matricula.findMatriculaActiva", query = "SELECT m FROM Matricula m WHERE m.estudianteId = :estudiante AND m.estadoMatriculaId.estadoMatriculaId = 1"),
    @NamedQuery(name = "Matricula.findMatriculaByCurso", query = "SELECT m FROM Matricula m WHERE m.cursoId = :curso AND m.estadoMatriculaId.estadoMatriculaId = 1 ORDER BY m.estudianteId.apellido"),
    @NamedQuery(name = "Matricula.findMatriculaTerminadaByCurso", query = "SELECT m FROM Matricula m WHERE m.cursoId = :curso AND m.estadoMatriculaId.estadoMatriculaId = 4 ORDER BY m.estudianteId.apellido"),
    @NamedQuery(name = "Matricula.findMatriculasAnio", query = "SELECT m FROM Matricula m WHERE m.cursoId IN (SELECT c FROM Curso c where c.anlectivoId = :anlectivoId) and m.estadoMatriculaId.estadoMatriculaId != 2 or m.estadoMatriculaId.estadoMatriculaId != 3"),
    @NamedQuery(name = "Matricula.findByMatriculaId", query = "SELECT m FROM Matricula m WHERE m.matriculaId = :matriculaId")})
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "matricula_id", nullable = false)
    private Integer matriculaId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;
    @JoinColumn(name = "estado_matricula_id", referencedColumnName = "estado_matricula_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoMatricula estadoMatriculaId;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoId;
    @JoinColumn(name = "aprobacion_id", referencedColumnName = "aprobacion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aprobacion aprobacionId;

    public Matricula() {
    }

    public Matricula(Integer matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Integer getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Integer matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Estudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    public EstadoMatricula getEstadoMatriculaId() {
        return estadoMatriculaId;
    }

    public void setEstadoMatriculaId(EstadoMatricula estadoMatriculaId) {
        this.estadoMatriculaId = estadoMatriculaId;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Aprobacion getAprobacionId() {
        return aprobacionId;
    }

    public void setAprobacionId(Aprobacion aprobacionId) {
        this.aprobacionId = aprobacionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculaId != null ? matriculaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.matriculaId == null && other.matriculaId != null) || (this.matriculaId != null && !this.matriculaId.equals(other.matriculaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Matricula[ matriculaId=" + matriculaId + " ]";
    }
    
}
