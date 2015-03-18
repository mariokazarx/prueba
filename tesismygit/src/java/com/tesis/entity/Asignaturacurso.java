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
@Table(name = "asignaturacurso", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturacurso.findAll", query = "SELECT a FROM Asignaturacurso a"),
    @NamedQuery(name = "Asignaturacurso.findByAsignaturacursoId", query = "SELECT a FROM Asignaturacurso a WHERE a.asignaturacursoId = :asignaturacursoId")})
public class Asignaturacurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "asignaturacurso_id", nullable = false)
    private Integer asignaturacursoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacursoId", fetch = FetchType.LAZY)
    private List<Notafinal> notafinalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacursoId", fetch = FetchType.LAZY)
    private List<Contenidotematico> contenidotematicoList;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoId;
    @JoinColumn(name = "asignatura_id", referencedColumnName = "asignatura_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asignatura asignaturaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacursoId", fetch = FetchType.LAZY)
    private List<Notafinalrecuperacion> notafinalrecuperacionList;

    public Asignaturacurso() {
    }

    public Asignaturacurso(Integer asignaturacursoId) {
        this.asignaturacursoId = asignaturacursoId;
    }

    public Integer getAsignaturacursoId() {
        return asignaturacursoId;
    }

    public void setAsignaturacursoId(Integer asignaturacursoId) {
        this.asignaturacursoId = asignaturacursoId;
    }

    @XmlTransient
    public List<Notafinal> getNotafinalList() {
        return notafinalList;
    }

    public void setNotafinalList(List<Notafinal> notafinalList) {
        this.notafinalList = notafinalList;
    }

    @XmlTransient
    public List<Contenidotematico> getContenidotematicoList() {
        return contenidotematicoList;
    }

    public void setContenidotematicoList(List<Contenidotematico> contenidotematicoList) {
        this.contenidotematicoList = contenidotematicoList;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Asignatura getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Asignatura asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    @XmlTransient
    public List<Notafinalrecuperacion> getNotafinalrecuperacionList() {
        return notafinalrecuperacionList;
    }

    public void setNotafinalrecuperacionList(List<Notafinalrecuperacion> notafinalrecuperacionList) {
        this.notafinalrecuperacionList = notafinalrecuperacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignaturacursoId != null ? asignaturacursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturacurso)) {
            return false;
        }
        Asignaturacurso other = (Asignaturacurso) object;
        if ((this.asignaturacursoId == null && other.asignaturacursoId != null) || (this.asignaturacursoId != null && !this.asignaturacursoId.equals(other.asignaturacursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Asignaturacurso[ asignaturacursoId=" + asignaturacursoId + " ]";
    }
    
}
