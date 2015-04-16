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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "contenidotematico", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenidotematico.findAll", query = "SELECT c FROM Contenidotematico c"),
    @NamedQuery(name = "Contenidotematico.findByContenidotematicoId", query = "SELECT c FROM Contenidotematico c WHERE c.contenidotematicoId = :contenidotematicoId"),
    @NamedQuery(name = "Contenidotematico.findByFechacierre", query = "SELECT c FROM Contenidotematico c WHERE c.fechacierre = :fechacierre"),
    @NamedQuery(name = "Contenidotematico.findByDescripcion", query = "SELECT c FROM Contenidotematico c WHERE c.descripcion = :descripcion")})
public class Contenidotematico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contenidotematico_id", nullable = false)
    private Integer contenidotematicoId;
    @Column(name = "fechacierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacierre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidotematicoId", fetch = FetchType.LAZY)
    private List<Nota> notaList;
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profesor profesorId;
    @JoinColumn(name = "periodo_id", referencedColumnName = "periodo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Periodo periodoId;
    @JoinColumn(name = "estado", referencedColumnName = "estadocontenidotematico_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estadocontenidotematico estado;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoId;
    @JoinColumn(name = "asignaturaciclo_id", referencedColumnName = "asignaturaciclo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asignaturaciclo asignaturacicloId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenidotematicoId", fetch = FetchType.LAZY)
    private List<Logro> logroList;

    public Contenidotematico() {
    }

    public Contenidotematico(Integer contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    public Integer getContenidotematicoId() {
        return contenidotematicoId;
    }

    public void setContenidotematicoId(Integer contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    public Profesor getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesor profesorId) {
        this.profesorId = profesorId;
    }

    public Periodo getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Periodo periodoId) {
        this.periodoId = periodoId;
    }

    public Estadocontenidotematico getEstado() {
        return estado;
    }

    public void setEstado(Estadocontenidotematico estado) {
        this.estado = estado;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Asignaturaciclo getAsignaturacicloId() {
        return asignaturacicloId;
    }

    public void setAsignaturacicloId(Asignaturaciclo asignaturacicloId) {
        this.asignaturacicloId = asignaturacicloId;
    }

    @XmlTransient
    public List<Logro> getLogroList() {
        return logroList;
    }

    public void setLogroList(List<Logro> logroList) {
        this.logroList = logroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contenidotematicoId != null ? contenidotematicoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenidotematico)) {
            return false;
        }
        Contenidotematico other = (Contenidotematico) object;
        if ((this.contenidotematicoId == null && other.contenidotematicoId != null) || (this.contenidotematicoId != null && !this.contenidotematicoId.equals(other.contenidotematicoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Contenidotematico[ contenidotematicoId=" + contenidotematicoId + " ]";
    }
    
}
