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
@Table(name = "estado_matricula", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoMatricula.findAll", query = "SELECT e FROM EstadoMatricula e"),
    @NamedQuery(name = "EstadoMatricula.findByEstadoMatriculaId", query = "SELECT e FROM EstadoMatricula e WHERE e.estadoMatriculaId = :estadoMatriculaId"),
    @NamedQuery(name = "EstadoMatricula.findByNombre", query = "SELECT e FROM EstadoMatricula e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoMatricula.findByDescripcion", query = "SELECT e FROM EstadoMatricula e WHERE e.descripcion = :descripcion")})
public class EstadoMatricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estado_matricula_id", nullable = false)
    private Integer estadoMatriculaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoMatriculaId", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;

    public EstadoMatricula() {
    }

    public EstadoMatricula(Integer estadoMatriculaId) {
        this.estadoMatriculaId = estadoMatriculaId;
    }

    public EstadoMatricula(Integer estadoMatriculaId, String nombre) {
        this.estadoMatriculaId = estadoMatriculaId;
        this.nombre = nombre;
    }

    public Integer getEstadoMatriculaId() {
        return estadoMatriculaId;
    }

    public void setEstadoMatriculaId(Integer estadoMatriculaId) {
        this.estadoMatriculaId = estadoMatriculaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoMatriculaId != null ? estadoMatriculaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoMatricula)) {
            return false;
        }
        EstadoMatricula other = (EstadoMatricula) object;
        if ((this.estadoMatriculaId == null && other.estadoMatriculaId != null) || (this.estadoMatriculaId != null && !this.estadoMatriculaId.equals(other.estadoMatriculaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.EstadoMatricula[ estadoMatriculaId=" + estadoMatriculaId + " ]";
    }
    
}
