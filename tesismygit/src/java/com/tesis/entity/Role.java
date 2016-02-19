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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "role", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRoleId", query = "SELECT r FROM Role r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "Role.removeByRoleId", query = "DELETE FROM Role r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "Role.findByConsultar", query = "SELECT r FROM Role r WHERE r.consultar = :consultar"),
    @NamedQuery(name = "Role.findByEditar", query = "SELECT r FROM Role r WHERE r.editar = :editar"),
    @NamedQuery(name = "Role.findByAgregar", query = "SELECT r FROM Role r WHERE r.agregar = :agregar"),
    @NamedQuery(name = "Role.findByEliminar", query = "SELECT r FROM Role r WHERE r.eliminar = :eliminar")})
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "consultar", nullable = false)
    private boolean consultar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "editar", nullable = false)
    private boolean editar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "agregar", nullable = false)
    private boolean agregar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eliminar", nullable = false)
    private boolean eliminar;
    @JoinColumn(name = "recurso_id", referencedColumnName = "recurso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Recurso recursoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId", fetch = FetchType.LAZY)
    private List<UsuarioRole> usuarioRoleList;

    public Role() {
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Role(Integer roleId, boolean consultar, boolean editar, boolean agregar, boolean eliminar) {
        this.roleId = roleId;
        this.consultar = consultar;
        this.editar = editar;
        this.agregar = agregar;
        this.eliminar = eliminar;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean getConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean getEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean getAgregar() {
        return agregar;
    }

    public void setAgregar(boolean agregar) {
        this.agregar = agregar;
    }

    public boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Recurso getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Recurso recursoId) {
        this.recursoId = recursoId;
    }

    @XmlTransient
    public List<UsuarioRole> getUsuarioRoleList() {
        return usuarioRoleList;
    }

    public void setUsuarioRoleList(List<UsuarioRole> usuarioRoleList) {
        this.usuarioRoleList = usuarioRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Role[ roleId=" + roleId + " ]";
    }
    
}
