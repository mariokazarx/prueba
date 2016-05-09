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
@Table(name = "nota", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.countAprobadasContenido", query = "SELECT COUNT(n) FROM Nota n WHERE n.contenidotematicoId = :contenido and n.valor >= :valor"),
    @NamedQuery(name = "Nota.countReprobadasContenido", query = "SELECT COUNT(n) FROM Nota n WHERE n.contenidotematicoId = :contenido and n.valor < :valor"),
    @NamedQuery(name = "Nota.findByFinal", query = "SELECT n.valor FROM Nota n WHERE n.estudianteId = :estudiante AND n.contenidotematicoId = :contenido"),
    @NamedQuery(name = "Nota.findByNotaId", query = "SELECT n FROM Nota n WHERE n.notaId = :notaId"),
    @NamedQuery(name = "Nota.findByValor", query = "SELECT n FROM Nota n WHERE n.valor = :valor"),
    @NamedQuery(name = "Nota.findNotaFinal", query = "SELECT AVG(n.valor) FROM Nota n WHERE n.estudianteId = :estudianteId and n.contenidotematicoId IN (SELECT c FROM Contenidotematico c WHERE c.periodoId IN (SELECT p FROM Periodo p WHERE p.anlectivoId = :anlectivoId ) and c.cursoId = :cursoId AND c.asignaturacicloId = :asignatura)"),
    //select avg(valor) from nota where estudiante_id = 1 and contenidotematico_id in (select contenidotematico_id  from contenidotematico where periodo_id in (select periodo_id from periodo  where anlectivo_id = 9 and curso_id = 1))
    @NamedQuery(name = "Nota.findByFechamodificacion", query = "SELECT n FROM Nota n WHERE n.fechamodificacion = :fechamodificacion"),
    @NamedQuery(name = "Nota.findByObservaciones", query = "SELECT n FROM Nota n WHERE n.observaciones = :observaciones")})
public class Nota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nota_id", nullable = false)
    private Integer notaId;
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
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estudiante estudianteId;
    @JoinColumn(name = "contenidotematico_id", referencedColumnName = "contenidotematico_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Contenidotematico contenidotematicoId;

    public Nota() {
    }

    public Nota(Integer notaId) {
        this.notaId = notaId;
    }

    public Nota(Integer notaId, BigDecimal valor) {
        this.notaId = notaId;
        this.valor = valor;
    }

    public Integer getNotaId() {
        return notaId;
    }

    public void setNotaId(Integer notaId) {
        this.notaId = notaId;
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

    public Estudiante getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiante estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Contenidotematico getContenidotematicoId() {
        return contenidotematicoId;
    }

    public void setContenidotematicoId(Contenidotematico contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notaId != null ? notaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.notaId == null && other.notaId != null) || (this.notaId != null && !this.notaId.equals(other.notaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Nota[ notaId=" + notaId + " ]";
    }
    
}
