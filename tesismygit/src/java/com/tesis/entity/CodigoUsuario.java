/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "codigo_usuario", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoUsuario.findAll", query = "SELECT c FROM CodigoUsuario c"),
    @NamedQuery(name = "CodigoUsuario.findByCodigoVigente", query = "SELECT c FROM CodigoUsuario c WHERE c.codigo = :codigo AND c.estado = 1 AND c.fechavalido > CURRENT_TIMESTAMP"),
    @NamedQuery(name = "CodigoUsuario.findByCodigoUsuarioId", query = "SELECT c FROM CodigoUsuario c WHERE c.codigoUsuarioId = :codigoUsuarioId"),
    @NamedQuery(name = "CodigoUsuario.findByCodigo", query = "SELECT c FROM CodigoUsuario c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CodigoUsuario.findByFechavalido", query = "SELECT c FROM CodigoUsuario c WHERE c.fechavalido = :fechavalido"),
    @NamedQuery(name = "CodigoUsuario.findByEstado", query = "SELECT c FROM CodigoUsuario c WHERE c.estado = :estado")})
public class CodigoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_usuario_id", nullable = false)
    private Integer codigoUsuarioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "codigo", nullable = false, length = 2147483647)
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechavalido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavalido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado", nullable = false)
    private int estado;
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuarioId;

    public CodigoUsuario() {
    }

    public CodigoUsuario(Integer codigoUsuarioId) {
        this.codigoUsuarioId = codigoUsuarioId;
    }

    public CodigoUsuario(Integer codigoUsuarioId, String codigo, Date fechavalido, int estado) {
        this.codigoUsuarioId = codigoUsuarioId;
        this.codigo = codigo;
        this.fechavalido = fechavalido;
        this.estado = estado;
    }

    public Integer getCodigoUsuarioId() {
        return codigoUsuarioId;
    }

    public void setCodigoUsuarioId(Integer codigoUsuarioId) {
        this.codigoUsuarioId = codigoUsuarioId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechavalido() {
        return fechavalido;
    }

    public void setFechavalido(Date fechavalido) {
        this.fechavalido = fechavalido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUsuarioId != null ? codigoUsuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoUsuario)) {
            return false;
        }
        CodigoUsuario other = (CodigoUsuario) object;
        if ((this.codigoUsuarioId == null && other.codigoUsuarioId != null) || (this.codigoUsuarioId != null && !this.codigoUsuarioId.equals(other.codigoUsuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.entity.CodigoUsuario[ codigoUsuarioId=" + codigoUsuarioId + " ]";
    }
    
}
