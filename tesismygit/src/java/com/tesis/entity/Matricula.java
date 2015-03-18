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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Matricula.findByMatriculaId", query = "SELECT m FROM Matricula m WHERE m.matriculaId = :matriculaId"),
    @NamedQuery(name = "Matricula.findByEstado", query = "SELECT m FROM Matricula m WHERE m.estado = :estado"),
    @NamedQuery(name = "Matricula.findByAprovacion", query = "SELECT m FROM Matricula m WHERE m.aprovacion = :aprovacion")})
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "matricula_id", nullable = false)
    private Integer matriculaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "estado", nullable = false, length = 200)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "aprovacion", nullable = false, length = 200)
    private String aprovacion;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoId;

    public Matricula() {
    }

    public Matricula(Integer matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Matricula(Integer matriculaId, String estado, String aprovacion) {
        this.matriculaId = matriculaId;
        this.estado = estado;
        this.aprovacion = aprovacion;
    }

    public Integer getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Integer matriculaId) {
        this.matriculaId = matriculaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAprovacion() {
        return aprovacion;
    }

    public void setAprovacion(String aprovacion) {
        this.aprovacion = aprovacion;
    }

    public Estudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
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
