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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "notafinal", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notafinal.findAll", query = "SELECT n FROM Notafinal n"),
    @NamedQuery(name = "Notafinal.findByNotafinalId", query = "SELECT n FROM Notafinal n WHERE n.notafinalId = :notafinalId"),
    @NamedQuery(name = "Notafinal.findByRecuperacion", query = "SELECT n FROM Notafinal n WHERE n.recuperacion = :recuperacion"),
    @NamedQuery(name = "Notafinal.findByValor", query = "SELECT n FROM Notafinal n WHERE n.valor = :valor"),
    @NamedQuery(name = "Notafinal.findByActual", query = "SELECT n FROM Notafinal n WHERE n.estudianteId = :estudianteId and n.asignaturacicloId = :asignaturacicloId"),
    @NamedQuery(name = "Notafinal.findByFechamodificacion", query = "SELECT n FROM Notafinal n WHERE n.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Notafinal.findByObservaciones", query = "SELECT n FROM Notafinal n WHERE n.observaciones = :observaciones")})
public class Notafinal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notafinal_id", nullable = false)
    private Integer notafinalId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "recuperacion", nullable = false, length = 2)
    private String recuperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false, precision = 2, scale = 1)
    private BigDecimal valor;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;
    @Size(max = 2147483647)
    @Column(name = "observaciones", length = 2147483647)
    private String observaciones;
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profesor profesorId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;
    @JoinColumn(name = "asignaturaciclo_id", referencedColumnName = "asignaturaciclo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asignaturaciclo asignaturacicloId;

    public Notafinal() {
    }

    public Notafinal(Integer notafinalId) {
        this.notafinalId = notafinalId;
    }

    public Notafinal(Integer notafinalId, String recuperacion, BigDecimal valor) {
        this.notafinalId = notafinalId;
        this.recuperacion = recuperacion;
        this.valor = valor;
    }

    public Integer getNotafinalId() {
        return notafinalId;
    }

    public void setNotafinalId(Integer notafinalId) {
        this.notafinalId = notafinalId;
    }

    public String getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(String recuperacion) {
        this.recuperacion = recuperacion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Profesor getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesor profesorId) {
        this.profesorId = profesorId;
    }

    public Estudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Asignaturaciclo getAsignaturacicloId() {
        return asignaturacicloId;
    }

    public void setAsignaturacicloId(Asignaturaciclo asignaturacicloId) {
        this.asignaturacicloId = asignaturacicloId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notafinalId != null ? notafinalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notafinal)) {
            return false;
        }
        Notafinal other = (Notafinal) object;
        if ((this.notafinalId == null && other.notafinalId != null) || (this.notafinalId != null && !this.notafinalId.equals(other.notafinalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Notafinal[ notafinalId=" + notafinalId + " ]";
    }
    
}
