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
@Table(name = "asignaturaciclo", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturaciclo.findAll", query = "SELECT a FROM Asignaturaciclo a"),
    @NamedQuery(name = "Asignaturaciclo.removeByAsignatura", query = "DELETE FROM Asignaturaciclo a WHERE a.asignaturaId= :asignatura"),
    @NamedQuery(name = "Asignaturaciclo.findByAsignaturacicloId", query = "SELECT a FROM Asignaturaciclo a WHERE a.asignaturacicloId = :asignaturacicloId")})
public class Asignaturaciclo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "asignaturaciclo_id", nullable = false)
    private Integer asignaturacicloId;
    @JoinColumn(name = "ciclo_id", referencedColumnName = "ciclo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciclo cicloId;
    @JoinColumn(name = "asignatura_id", referencedColumnName = "asignatura_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asignatura asignaturaId;

    public Asignaturaciclo() {
    }

    public Asignaturaciclo(Integer asignaturacicloId) {
        this.asignaturacicloId = asignaturacicloId;
    }

    public Integer getAsignaturacicloId() {
        return asignaturacicloId;
    }

    public void setAsignaturacicloId(Integer asignaturacicloId) {
        this.asignaturacicloId = asignaturacicloId;
    }

    public Ciclo getCicloId() {
        return cicloId;
    }

    public void setCicloId(Ciclo cicloId) {
        this.cicloId = cicloId;
    }

    public Asignatura getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Asignatura asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignaturacicloId != null ? asignaturacicloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturaciclo)) {
            return false;
        }
        Asignaturaciclo other = (Asignaturaciclo) object;
        if ((this.asignaturacicloId == null && other.asignaturacicloId != null) || (this.asignaturacicloId != null && !this.asignaturacicloId.equals(other.asignaturacicloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Asignaturaciclo[ asignaturacicloId=" + asignaturacicloId + " ]";
    }
    
}
