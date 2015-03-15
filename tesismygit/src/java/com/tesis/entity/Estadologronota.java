/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "estadologronota", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadologronota.findAll", query = "SELECT e FROM Estadologronota e"),
    @NamedQuery(name = "Estadologronota.findByEstadologronotaId", query = "SELECT e FROM Estadologronota e WHERE e.estadologronotaId = :estadologronotaId"),
    @NamedQuery(name = "Estadologronota.findByNombre", query = "SELECT e FROM Estadologronota e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estadologronota.findByDescripcion", query = "SELECT e FROM Estadologronota e WHERE e.descripcion = :descripcion")})
public class Estadologronota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estadologronota_id", nullable = false)
    private Integer estadologronotaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    public Estadologronota() {
    }

    public Estadologronota(Integer estadologronotaId) {
        this.estadologronotaId = estadologronotaId;
    }

    public Estadologronota(Integer estadologronotaId, String nombre) {
        this.estadologronotaId = estadologronotaId;
        this.nombre = nombre;
    }

    public Integer getEstadologronotaId() {
        return estadologronotaId;
    }

    public void setEstadologronotaId(Integer estadologronotaId) {
        this.estadologronotaId = estadologronotaId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadologronotaId != null ? estadologronotaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadologronota)) {
            return false;
        }
        Estadologronota other = (Estadologronota) object;
        if ((this.estadologronotaId == null && other.estadologronotaId != null) || (this.estadologronotaId != null && !this.estadologronotaId.equals(other.estadologronotaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Estadologronota[ estadologronotaId=" + estadologronotaId + " ]";
    }
    
}
