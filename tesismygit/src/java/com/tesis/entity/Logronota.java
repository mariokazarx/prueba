/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota", nullable = false)
    private int nota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado", fetch = FetchType.LAZY)
    private List<Logronota> logronotaList;
    @JoinColumn(name = "estado", referencedColumnName = "logronota_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Logronota estado;
    @JoinColumn(name = "logro_id", referencedColumnName = "logro_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Logro logroId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;

    public Logronota() {
    }

    public Logronota(Integer logronotaId) {
        this.logronotaId = logronotaId;
    }

    public Logronota(Integer logronotaId, int nota) {
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @XmlTransient
    public List<Logronota> getLogronotaList() {
        return logronotaList;
    }

    public void setLogronotaList(List<Logronota> logronotaList) {
        this.logronotaList = logronotaList;
    }

    public Logronota getEstado() {
        return estado;
    }

    public void setEstado(Logronota estado) {
        this.estado = estado;
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
