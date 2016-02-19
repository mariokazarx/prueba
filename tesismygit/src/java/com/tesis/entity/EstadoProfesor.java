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
@Table(name = "estado_profesor", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoProfesor.findAll", query = "SELECT e FROM EstadoProfesor e"),
    @NamedQuery(name = "EstadoProfesor.findByEstadoProfesorId", query = "SELECT e FROM EstadoProfesor e WHERE e.estadoProfesorId = :estadoProfesorId"),
    @NamedQuery(name = "EstadoProfesor.findByNombre", query = "SELECT e FROM EstadoProfesor e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoProfesor.findByDescripcion", query = "SELECT e FROM EstadoProfesor e WHERE e.descripcion = :descripcion")})
public class EstadoProfesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_profesor_id", nullable = false)
    private Integer estadoProfesorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoProfesorId", fetch = FetchType.LAZY)
    private List<Profesor> profesorList;

    public EstadoProfesor() {
    }

    public EstadoProfesor(Integer estadoProfesorId) {
        this.estadoProfesorId = estadoProfesorId;
    }

    public EstadoProfesor(Integer estadoProfesorId, String nombre) {
        this.estadoProfesorId = estadoProfesorId;
        this.nombre = nombre;
    }

    public Integer getEstadoProfesorId() {
        return estadoProfesorId;
    }

    public void setEstadoProfesorId(Integer estadoProfesorId) {
        this.estadoProfesorId = estadoProfesorId;
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
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoProfesorId != null ? estadoProfesorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoProfesor)) {
            return false;
        }
        EstadoProfesor other = (EstadoProfesor) object;
        if ((this.estadoProfesorId == null && other.estadoProfesorId != null) || (this.estadoProfesorId != null && !this.estadoProfesorId.equals(other.estadoProfesorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.EstadoProfesor[ estadoProfesorId=" + estadoProfesorId + " ]";
    }
    
}
