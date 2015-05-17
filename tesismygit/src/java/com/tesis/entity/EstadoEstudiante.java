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
@Table(name = "estado_estudiante", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoEstudiante.findAll", query = "SELECT e FROM EstadoEstudiante e"),
    @NamedQuery(name = "EstadoEstudiante.findByEstadoEstudianteId", query = "SELECT e FROM EstadoEstudiante e WHERE e.estadoEstudianteId = :estadoEstudianteId"),
    @NamedQuery(name = "EstadoEstudiante.findByNombre", query = "SELECT e FROM EstadoEstudiante e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoEstudiante.findByDescripcion", query = "SELECT e FROM EstadoEstudiante e WHERE e.descripcion = :descripcion")})
public class EstadoEstudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estado_estudiante_id", nullable = false)
    private Integer estadoEstudianteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoEstudianteId", fetch = FetchType.LAZY)
    private List<Estudiante> estudianteList;

    public EstadoEstudiante() {
    }

    public EstadoEstudiante(Integer estadoEstudianteId) {
        this.estadoEstudianteId = estadoEstudianteId;
    }

    public EstadoEstudiante(Integer estadoEstudianteId, String nombre) {
        this.estadoEstudianteId = estadoEstudianteId;
        this.nombre = nombre;
    }

    public Integer getEstadoEstudianteId() {
        return estadoEstudianteId;
    }

    public void setEstadoEstudianteId(Integer estadoEstudianteId) {
        this.estadoEstudianteId = estadoEstudianteId;
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
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoEstudianteId != null ? estadoEstudianteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEstudiante)) {
            return false;
        }
        EstadoEstudiante other = (EstadoEstudiante) object;
        if ((this.estadoEstudianteId == null && other.estadoEstudianteId != null) || (this.estadoEstudianteId != null && !this.estadoEstudianteId.equals(other.estadoEstudianteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.EstadoEstudiante[ estadoEstudianteId=" + estadoEstudianteId + " ]";
    }
    
}
