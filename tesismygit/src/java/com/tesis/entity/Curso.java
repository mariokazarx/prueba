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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "curso", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByCursoId", query = "SELECT c FROM Curso c WHERE c.cursoId = :cursoId"),
    @NamedQuery(name = "Curso.findByNombre", query = "SELECT c FROM Curso c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Curso.findByNumeroestudiantes", query = "SELECT c FROM Curso c WHERE c.numeroestudiantes = :numeroestudiantes")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "curso_id", nullable = false)
    private Integer cursoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroestudiantes", nullable = false)
    private int numeroestudiantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoId", fetch = FetchType.LAZY)
    private List<Asignaturacurso> asignaturacursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoId", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @JoinColumn(name = "ciclo_id", referencedColumnName = "ciclo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciclo cicloId;

    public Curso() {
    }

    public Curso(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public Curso(Integer cursoId, String nombre, int numeroestudiantes) {
        this.cursoId = cursoId;
        this.nombre = nombre;
        this.numeroestudiantes = numeroestudiantes;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroestudiantes() {
        return numeroestudiantes;
    }

    public void setNumeroestudiantes(int numeroestudiantes) {
        this.numeroestudiantes = numeroestudiantes;
    }

    @XmlTransient
    public List<Asignaturacurso> getAsignaturacursoList() {
        return asignaturacursoList;
    }

    public void setAsignaturacursoList(List<Asignaturacurso> asignaturacursoList) {
        this.asignaturacursoList = asignaturacursoList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public Ciclo getCicloId() {
        return cicloId;
    }

    public void setCicloId(Ciclo cicloId) {
        this.cicloId = cicloId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cursoId != null ? cursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.cursoId == null && other.cursoId != null) || (this.cursoId != null && !this.cursoId.equals(other.cursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Curso[ cursoId=" + cursoId + " ]";
    }
    
}
