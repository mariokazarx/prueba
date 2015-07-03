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
@Table(name = "recurso", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r"),
    @NamedQuery(name = "Recurso.findByRecursoId", query = "SELECT r FROM Recurso r WHERE r.recursoId = :recursoId"),
    @NamedQuery(name = "Recurso.findByNombre", query = "SELECT r FROM Recurso r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Recurso.findByDescripcion", query = "SELECT r FROM Recurso r WHERE r.descripcion = :descripcion")})
public class Recurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "recurso_id", nullable = false)
    private Integer recursoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recursoId", fetch = FetchType.LAZY)
    private List<Role> roleList;

    public Recurso() {
    }

    public Recurso(Integer recursoId) {
        this.recursoId = recursoId;
    }

    public Recurso(Integer recursoId, String nombre) {
        this.recursoId = recursoId;
        this.nombre = nombre;
    }

    public Integer getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Integer recursoId) {
        this.recursoId = recursoId;
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
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recursoId != null ? recursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.recursoId == null && other.recursoId != null) || (this.recursoId != null && !this.recursoId.equals(other.recursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Recurso[ recursoId=" + recursoId + " ]";
    }
    
}
