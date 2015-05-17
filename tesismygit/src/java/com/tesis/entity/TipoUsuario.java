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
@Table(name = "tipo_usuario", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findByTipoUsuarioId", query = "SELECT t FROM TipoUsuario t WHERE t.tipoUsuarioId = :tipoUsuarioId"),
    @NamedQuery(name = "TipoUsuario.findByNombre", query = "SELECT t FROM TipoUsuario t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoUsuario.findByDescripcion", query = "SELECT t FROM TipoUsuario t WHERE t.descripcion = :descripcion")})
public class TipoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipo_usuario_id", nullable = false)
    private Integer tipoUsuarioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuarioId", fetch = FetchType.LAZY)
    private List<Profesor> profesorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuarioId", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuarioId", fetch = FetchType.LAZY)
    private List<Estudiante> estudianteList;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public TipoUsuario(Integer tipoUsuarioId, String nombre) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.nombre = nombre;
    }

    public Integer getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Integer tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
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

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        hash += (tipoUsuarioId != null ? tipoUsuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.tipoUsuarioId == null && other.tipoUsuarioId != null) || (this.tipoUsuarioId != null && !this.tipoUsuarioId.equals(other.tipoUsuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.TipoUsuario[ tipoUsuarioId=" + tipoUsuarioId + " ]";
    }
    
}
