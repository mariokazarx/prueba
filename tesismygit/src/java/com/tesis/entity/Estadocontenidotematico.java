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
@Table(name = "estadocontenidotematico", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadocontenidotematico.findAll", query = "SELECT e FROM Estadocontenidotematico e"),
    @NamedQuery(name = "Estadocontenidotematico.findByEstadocontenidotematicoId", query = "SELECT e FROM Estadocontenidotematico e WHERE e.estadocontenidotematicoId = :estadocontenidotematicoId"),
    @NamedQuery(name = "Estadocontenidotematico.findByNombre", query = "SELECT e FROM Estadocontenidotematico e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estadocontenidotematico.findByDescripcion", query = "SELECT e FROM Estadocontenidotematico e WHERE e.descripcion = :descripcion")})
public class Estadocontenidotematico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estadocontenidotematico_id", nullable = false)
    private Integer estadocontenidotematicoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado", fetch = FetchType.LAZY)
    private List<Contenidotematico> contenidotematicoList;

    public Estadocontenidotematico() {
    }

    public Estadocontenidotematico(Integer estadocontenidotematicoId) {
        this.estadocontenidotematicoId = estadocontenidotematicoId;
    }

    public Estadocontenidotematico(Integer estadocontenidotematicoId, String nombre) {
        this.estadocontenidotematicoId = estadocontenidotematicoId;
        this.nombre = nombre;
    }

    public Integer getEstadocontenidotematicoId() {
        return estadocontenidotematicoId;
    }

    public void setEstadocontenidotematicoId(Integer estadocontenidotematicoId) {
        this.estadocontenidotematicoId = estadocontenidotematicoId;
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
    public List<Contenidotematico> getContenidotematicoList() {
        return contenidotematicoList;
    }

    public void setContenidotematicoList(List<Contenidotematico> contenidotematicoList) {
        this.contenidotematicoList = contenidotematicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadocontenidotematicoId != null ? estadocontenidotematicoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadocontenidotematico)) {
            return false;
        }
        Estadocontenidotematico other = (Estadocontenidotematico) object;
        if ((this.estadocontenidotematicoId == null && other.estadocontenidotematicoId != null) || (this.estadocontenidotematicoId != null && !this.estadocontenidotematicoId.equals(other.estadocontenidotematicoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Estadocontenidotematico[ estadocontenidotematicoId=" + estadocontenidotematicoId + " ]";
    }
    
}
