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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Asignaturaciclo.asignaturasProfesor", query= "SELECT a FROM Asignaturaciclo a JOIN a.contenidotematicoList c WHERE c.cursoId = :cursoId AND c.profesorId= :profesorId AND c.periodoId = :periodoId"),
    @NamedQuery(name = "Asignaturaciclo.asignaturasProfesorPeriodo", query= "SELECT a FROM Asignaturaciclo a JOIN a.contenidotematicoList c WHERE c.cursoId = :cursoId AND c.profesorId= :profesorId AND c.periodoId = :periodoId AND c.estado.estadocontenidotematicoId != 5"),
    @NamedQuery(name = "Asignaturaciclo.asignaturasDisponibles", query= "SELECT a from Asignaturaciclo a LEFT JOIN Curso c WHERE c.cicloId = a.cicloId AND c.cursoId = :cursoId EXCEPT SELECT a FROM Asignaturaciclo a JOIN a.contenidotematicoList c WHERE c.cursoId = :cursoIdC "),//AND c.profesorId= :profesorId
    @NamedQuery(name = "Asignaturaciclo.asignaturasDisponiblesPeriodo", query= "SELECT a from Asignaturaciclo a LEFT JOIN Curso c WHERE c.cicloId = a.cicloId AND c.cursoId = :cursoId EXCEPT SELECT a FROM Asignaturaciclo a JOIN a.contenidotematicoList c WHERE c.cursoId = :cursoIdC AND c.periodoId = :periodo AND c.estado.estadocontenidotematicoId != 5"),
    @NamedQuery(name = "Asignaturaciclo.findByCicloAsig", query= "SELECT a from Asignaturaciclo a WHERE a.cicloId = :cicloId AND a.asignaturaId = :asignaturaId"),
    @NamedQuery(name = "Asignaturaciclo.countAprobadas", query= "SELECT COUNT(DISTINCT a.asignaturaId) FROM Asignaturaciclo a JOIN a.contenidotematicoList c JOIN a.notafinalList n WHERE c.cursoId = :cursoId AND n.valor >= :valor AND n.estudianteId = :estudianteId"),
    //@NamedQuery(name = "Asignaturaciclo.asignaturasProfesor", query= "SELECT a.* FROM Asignaturaciclo a,Contenidotematico c JOIN a.ciclos aCiclo JOIN c.cilos cCilo WHERE aCilco = cCiclo AND a.curso_id= :cursoId and a.profesor_id= :profesorId"),
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacicloId", fetch = FetchType.LAZY)
    private List<Notafinal> notafinalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacicloId", fetch = FetchType.LAZY)
    private List<Contenidotematico> contenidotematicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturacicloId", fetch = FetchType.LAZY)
    private List<Notafinalrecuperacion> notafinalrecuperacionList;

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

    @XmlTransient
    public List<Notafinal> getNotafinalList() {
        return notafinalList;
    }

    public void setNotafinalList(List<Notafinal> notafinalList) {
        this.notafinalList = notafinalList;
    }

    @XmlTransient
    public List<Contenidotematico> getContenidotematicoList() {
        return contenidotematicoList;
    }

    public void setContenidotematicoList(List<Contenidotematico> contenidotematicoList) {
        this.contenidotematicoList = contenidotematicoList;
    }

    @XmlTransient
    public List<Notafinalrecuperacion> getNotafinalrecuperacionList() {
        return notafinalrecuperacionList;
    }

    public void setNotafinalrecuperacionList(List<Notafinalrecuperacion> notafinalrecuperacionList) {
        this.notafinalrecuperacionList = notafinalrecuperacionList;
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
