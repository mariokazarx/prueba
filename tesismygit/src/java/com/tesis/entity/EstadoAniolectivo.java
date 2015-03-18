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
@Table(name = "estado_aniolectivo", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoAniolectivo.findAll", query = "SELECT e FROM EstadoAniolectivo e"),
    @NamedQuery(name = "EstadoAniolectivo.findByEstadoAniolectivoId", query = "SELECT e FROM EstadoAniolectivo e WHERE e.estadoAniolectivoId = :estadoAniolectivoId"),
    @NamedQuery(name = "EstadoAniolectivo.findByEstado", query = "SELECT e FROM EstadoAniolectivo e WHERE e.estado = :estado")})
public class EstadoAniolectivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estado_aniolectivo_id", nullable = false)
    private Integer estadoAniolectivoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "estado", nullable = false, length = 200)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoAniolectivoId", fetch = FetchType.LAZY)
    private List<Anlectivo> anlectivoList;

    public EstadoAniolectivo() {
    }

    public EstadoAniolectivo(Integer estadoAniolectivoId) {
        this.estadoAniolectivoId = estadoAniolectivoId;
    }

    public EstadoAniolectivo(Integer estadoAniolectivoId, String estado) {
        this.estadoAniolectivoId = estadoAniolectivoId;
        this.estado = estado;
    }

    public Integer getEstadoAniolectivoId() {
        return estadoAniolectivoId;
    }

    public void setEstadoAniolectivoId(Integer estadoAniolectivoId) {
        this.estadoAniolectivoId = estadoAniolectivoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Anlectivo> getAnlectivoList() {
        return anlectivoList;
    }

    public void setAnlectivoList(List<Anlectivo> anlectivoList) {
        this.anlectivoList = anlectivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoAniolectivoId != null ? estadoAniolectivoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoAniolectivo)) {
            return false;
        }
        EstadoAniolectivo other = (EstadoAniolectivo) object;
        if ((this.estadoAniolectivoId == null && other.estadoAniolectivoId != null) || (this.estadoAniolectivoId != null && !this.estadoAniolectivoId.equals(other.estadoAniolectivoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.EstadoAniolectivo[ estadoAniolectivoId=" + estadoAniolectivoId + " ]";
    }
    
}
