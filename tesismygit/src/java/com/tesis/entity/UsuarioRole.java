/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "usuario_role", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioRole.findAll", query = "SELECT u FROM UsuarioRole u"),
    @NamedQuery(name = "UsuarioRole.removeByUsuarioId", query = "DELETE FROM UsuarioRole u WHERE u.usuarioId = :usuario"),
    @NamedQuery(name = "UsuarioRole.findByUsuarioId", query = "SELECT u FROM UsuarioRole u WHERE u.usuarioId = :usuario"),
    @NamedQuery(name = "UsuarioRole.findByUsuarioRoleId", query = "SELECT u FROM UsuarioRole u WHERE u.usuarioRoleId = :usuarioRoleId")})
public class UsuarioRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario_role_id", nullable = false)
    private Integer usuarioRoleId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuarioId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Role roleId;

    public UsuarioRole() {
    }

    public UsuarioRole(Integer usuarioRoleId) {
        this.usuarioRoleId = usuarioRoleId;
    }

    public Integer getUsuarioRoleId() {
        return usuarioRoleId;
    }

    public void setUsuarioRoleId(Integer usuarioRoleId) {
        this.usuarioRoleId = usuarioRoleId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioRoleId != null ? usuarioRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRole)) {
            return false;
        }
        UsuarioRole other = (UsuarioRole) object;
        if ((this.usuarioRoleId == null && other.usuarioRoleId != null) || (this.usuarioRoleId != null && !this.usuarioRoleId.equals(other.usuarioRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.UsuarioRole[ usuarioRoleId=" + usuarioRoleId + " ]";
    }
    
}
