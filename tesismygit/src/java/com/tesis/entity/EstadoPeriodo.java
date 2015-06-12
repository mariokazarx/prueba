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
@Table(name = "estado_periodo", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoPeriodo.findAll", query = "SELECT e FROM EstadoPeriodo e"),
    @NamedQuery(name = "EstadoPeriodo.findByEstadoPeriodoId", query = "SELECT e FROM EstadoPeriodo e WHERE e.estadoPeriodoId = :estadoPeriodoId"),
    @NamedQuery(name = "EstadoPeriodo.findByNombre", query = "SELECT e FROM EstadoPeriodo e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoPeriodo.findByDescripcion", query = "SELECT e FROM EstadoPeriodo e WHERE e.descripcion = :descripcion")})
public class EstadoPeriodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estado_periodo_id", nullable = false)
    private Integer estadoPeriodoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoPeriodoId", fetch = FetchType.LAZY)
    private List<Periodo> periodoList;

    public EstadoPeriodo() {
    }

    public EstadoPeriodo(Integer estadoPeriodoId) {
        this.estadoPeriodoId = estadoPeriodoId;
    }

    public EstadoPeriodo(Integer estadoPeriodoId, String nombre) {
        this.estadoPeriodoId = estadoPeriodoId;
        this.nombre = nombre;
    }

    public Integer getEstadoPeriodoId() {
        return estadoPeriodoId;
    }

    public void setEstadoPeriodoId(Integer estadoPeriodoId) {
        this.estadoPeriodoId = estadoPeriodoId;
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
    public List<Periodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<Periodo> periodoList) {
        this.periodoList = periodoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoPeriodoId != null ? estadoPeriodoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoPeriodo)) {
            return false;
        }
        EstadoPeriodo other = (EstadoPeriodo) object;
        if ((this.estadoPeriodoId == null && other.estadoPeriodoId != null) || (this.estadoPeriodoId != null && !this.estadoPeriodoId.equals(other.estadoPeriodoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.EstadoPeriodo[ estadoPeriodoId=" + estadoPeriodoId + " ]";
    }
    
}
