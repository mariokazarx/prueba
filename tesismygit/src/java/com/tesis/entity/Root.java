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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "root", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Root.findAll", query = "SELECT r FROM Root r"),
    @NamedQuery(name = "Root.findByRootId", query = "SELECT r FROM Root r WHERE r.rootId = :rootId"),
    @NamedQuery(name = "Root.findByIdentificacion", query = "SELECT r FROM Root r WHERE r.identificacion = :identificacion"),
    @NamedQuery(name = "Root.findByNombres", query = "SELECT r FROM Root r WHERE r.nombres = :nombres"),
    @NamedQuery(name = "Root.findByApellidos", query = "SELECT r FROM Root r WHERE r.apellidos = :apellidos"),
    @NamedQuery(name = "Root.findByTelefono", query = "SELECT r FROM Root r WHERE r.telefono = :telefono")})
public class Root implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "root_id", nullable = false)
    private Integer rootId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "identificacion", nullable = false, length = 12)
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombres", nullable = false, length = 200)
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apellidos", nullable = false, length = 200)
    private String apellidos;
    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    private String telefono;
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUsuario tipoUsuarioId;

    public Root() {
    }

    public Root(Integer rootId) {
        this.rootId = rootId;
    }

    public Root(Integer rootId, String identificacion, String nombres, String apellidos) {
        this.rootId = rootId;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rootId != null ? rootId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Root)) {
            return false;
        }
        Root other = (Root) object;
        if ((this.rootId == null && other.rootId != null) || (this.rootId != null && !this.rootId.equals(other.rootId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Root[ rootId=" + rootId + " ]";
    }
    
}
