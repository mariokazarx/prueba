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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "usuario", catalog = "prueba", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"identificacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuarioId", query = "SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId"),
    @NamedQuery(name = "Usuario.findByIdentificacion", query = "SELECT u FROM Usuario u WHERE u.identificacion = :identificacion"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByDireccion", query = "SELECT u FROM Usuario u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuario.findByContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1a = :contrase\u00f1a"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;
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
    @Size(max = 100)
    @Column(name = "correo", length = 100)
    private String correo;
    @Size(max = 199)
    @Column(name = "direccion", length = 199)
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "contrase\u00f1a", length = 2147483647)
    private String contraseña;
    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    private String telefono;
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUsuario tipoUsuarioId;
    @JoinColumn(name = "estado_usuario_id", referencedColumnName = "estado_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoUsuario estadoUsuarioId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<UsuarioRole> usuarioRoleList;

    public Usuario() {
    }

    public Usuario(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario(Integer usuarioId, String identificacion, String nombres, String apellidos) {
        this.usuarioId = usuarioId;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public EstadoUsuario getEstadoUsuarioId() {
        return estadoUsuarioId;
    }

    public void setEstadoUsuarioId(EstadoUsuario estadoUsuarioId) {
        this.estadoUsuarioId = estadoUsuarioId;
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
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Usuario[ usuarioId=" + usuarioId + " ]";
    }
    
}
