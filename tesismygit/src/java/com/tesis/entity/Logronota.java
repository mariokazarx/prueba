/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "logronota", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logronota.findAll", query = "SELECT l FROM Logronota l"),
    @NamedQuery(name = "Logronota.findByLogronotaId", query = "SELECT l FROM Logronota l WHERE l.logronotaId = :logronotaId"),
    @NamedQuery(name = "Logronota.findByFechamodificacion", query = "SELECT l FROM Logronota l WHERE l.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Logronota.findByNota", query = "SELECT l FROM Logronota l WHERE l.nota = :nota")})
public class Logronota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logronota_id", nullable = false)
    private Integer logronotaId;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota", nullable = false, precision = 2, scale = 1)
    private BigDecimal nota;
    @JoinColumn(name = "logro_id", referencedColumnName = "logro_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Logro logroId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;
    @JoinColumn(name = "estado", referencedColumnName = "estadologronota_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estadologronota estado;

    public Logronota() {
    }

    public Logronota(Integer logronotaId) {
        this.logronotaId = logronotaId;
    }

    public Logronota(Integer logronotaId, BigDecimal nota) {
        this.logronotaId = logronotaId;
        this.nota = nota;
    }

    public Integer getLogronotaId() {
        return logronotaId;
    }

    public void setLogronotaId(Integer logronotaId) {
        this.logronotaId = logronotaId;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public Logro getLogroId() {
        return logroId;
    }

    public void setLogroId(Logro logroId) {
        this.logroId = logroId;
    }

    public Estudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Estadologronota getEstado() {
        return estado;
    }

    public void setEstado(Estadologronota estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logronotaId != null ? logronotaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logronota)) {
            return false;
        }
        Logronota other = (Logronota) object;
        if ((this.logronotaId == null && other.logronotaId != null) || (this.logronotaId != null && !this.logronotaId.equals(other.logronotaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Logronota[ logronotaId=" + logronotaId + " ]";
    }
    
}
