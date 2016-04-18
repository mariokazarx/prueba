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
@Table(name = "codigo_profesor", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoProfesor.findAll", query = "SELECT c FROM CodigoProfesor c"),
    @NamedQuery(name = "CodigoProfesor.findByCodigoVigente", query = "SELECT c FROM CodigoProfesor c WHERE c.codigo = :codigo AND c.estado = 1 AND c.fechavalido > CURRENT_TIMESTAMP"),
    @NamedQuery(name = "CodigoProfesor.findByCodigoProfesorId", query = "SELECT c FROM CodigoProfesor c WHERE c.codigoProfesorId = :codigoProfesorId"),
    @NamedQuery(name = "CodigoProfesor.findByCodigo", query = "SELECT c FROM CodigoProfesor c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CodigoProfesor.findByFechavalido", query = "SELECT c FROM CodigoProfesor c WHERE c.fechavalido = :fechavalido"),
    @NamedQuery(name = "CodigoProfesor.findByEstado", query = "SELECT c FROM CodigoProfesor c WHERE c.estado = :estado")})
public class CodigoProfesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_profesor_id", nullable = false)
    private Integer codigoProfesorId;
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
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profesor profesorId;

    public CodigoProfesor() {
    }

    public CodigoProfesor(Integer codigoProfesorId) {
        this.codigoProfesorId = codigoProfesorId;
    }

    public CodigoProfesor(Integer codigoProfesorId, String codigo, Date fechavalido, int estado) {
        this.codigoProfesorId = codigoProfesorId;
        this.codigo = codigo;
        this.fechavalido = fechavalido;
        this.estado = estado;
    }

    public Integer getCodigoProfesorId() {
        return codigoProfesorId;
    }

    public void setCodigoProfesorId(Integer codigoProfesorId) {
        this.codigoProfesorId = codigoProfesorId;
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

    public Profesor getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesor profesorId) {
        this.profesorId = profesorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProfesorId != null ? codigoProfesorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoProfesor)) {
            return false;
        }
        CodigoProfesor other = (CodigoProfesor) object;
        if ((this.codigoProfesorId == null && other.codigoProfesorId != null) || (this.codigoProfesorId != null && !this.codigoProfesorId.equals(other.codigoProfesorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.entity.CodigoProfesor[ codigoProfesorId=" + codigoProfesorId + " ]";
    }
    
}