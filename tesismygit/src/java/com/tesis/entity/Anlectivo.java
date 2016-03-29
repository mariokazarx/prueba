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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "anlectivo", catalog = "prueba", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"anio"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anlectivo.findAll", query = "SELECT a FROM Anlectivo a"),
    @NamedQuery(name = "Anlectivo.añoEnUso", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.anio = :anio"),
    @NamedQuery(name = "Anlectivo.findAnlectivoUtil", query = "SELECT a FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2 OR a.estadoAniolectivoId.estadoAniolectivoId = 5"),
    @NamedQuery(name = "Anlectivo.findAnlectivosfinalizados", query = "SELECT a FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 3 OR a.estadoAniolectivoId.estadoAniolectivoId = 5"),
    @NamedQuery(name = "Anlectivo.escalaEnUso", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.configuracionId.escalaId = :escala AND (a.estadoAniolectivoId.estadoAniolectivoId = 2 OR a.estadoAniolectivoId.estadoAniolectivoId = 3 OR a.estadoAniolectivoId.estadoAniolectivoId = 5)"),
    @NamedQuery(name = "Anlectivo.criterioEnUso", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.configuracionId.criterioevaluacionId = :criterio AND (a.estadoAniolectivoId.estadoAniolectivoId = 2 OR a.estadoAniolectivoId.estadoAniolectivoId = 3 OR a.estadoAniolectivoId.estadoAniolectivoId = 5)"),
    @NamedQuery(name = "Anlectivo.configuracionEnUso", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.configuracionId = :configuracion AND (a.estadoAniolectivoId.estadoAniolectivoId = 2 OR a.estadoAniolectivoId.estadoAniolectivoId = 3 OR a.estadoAniolectivoId.estadoAniolectivoId = 5)"),
    @NamedQuery(name = "Anlectivo.findByAnlectivoId", query = "SELECT a FROM Anlectivo a WHERE a.anlectivoId = :anlectivoId"),
    @NamedQuery(name = "Anlectivo.findByEstadocopiado", query = "SELECT a FROM Anlectivo a WHERE a.estadocopiado = :estadocopiado"),
    @NamedQuery(name = "Anlectivo.findByNoPeriodos", query = "SELECT a FROM Anlectivo a WHERE a.noPeriodos = :noPeriodos"),
    @NamedQuery(name = "Anlectivo.findByAnio", query = "SELECT a FROM Anlectivo a WHERE a.anio = :anio"),
    @NamedQuery(name = "Anlectivo.findByTerminado", query = "SELECT a FROM Anlectivo a WHERE a.terminado = :terminado"),
    @NamedQuery(name = "Anlectivo.findByUso", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2 or a.estadoAniolectivoId.estadoAniolectivoId = 5"),
    @NamedQuery(name = "Anlectivo.findUso", query = "SELECT a FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2 or a.estadoAniolectivoId.estadoAniolectivoId = 5"),
    @NamedQuery(name = "Anlectivo.findActivo", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 5"),
    @NamedQuery(name = "Anlectivo.findIniciado", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2"),
    @NamedQuery(name = "Anlectivo.findIniciadoObj", query = "SELECT a FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2"),
    @NamedQuery(name = "Anlectivo.findIniciadoNew", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.estadoAniolectivoId.estadoAniolectivoId = 2 and a.terminado = false"),
    @NamedQuery(name = "Anlectivo.findConfoguracionCurso", query = "SELECT a.configuracionId FROM Anlectivo a JOIN a.cursoList c WHERE c.cursoId = :cursoId"),
    @NamedQuery(name = "Anlectivo.findConfiguracion", query = "SELECT COUNT(a) FROM Anlectivo a WHERE a.configuracionId = :configuracionId"),
    @NamedQuery(name = "Anlectivo.findConfiguracionUso", query = "SELECT a FROM Anlectivo a WHERE a.configuracionId = :configuracionId and a.terminado = true"),
    @NamedQuery(name = "Anlectivo.removeById", query = "DELETE FROM Anlectivo a WHERE a.anlectivoId = :anlectivoId"),
    @NamedQuery(name = "Anlectivo.findByDescripcion", query = "SELECT a FROM Anlectivo a WHERE a.descripcion = :descripcion")})
public class Anlectivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "anlectivo_id", nullable = false)
    private Integer anlectivoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "estadocopiado", nullable = false, length = 2)
    private String estadocopiado;
    @Basic(optional = false)
    @NotNull
    @Min(value=1,message="Debe ser mayor que 0")
    @Column(name = "no_periodos", nullable = false)
    private int noPeriodos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio", nullable = false)
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "terminado", nullable = false)
    private boolean terminado;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anlectivoId", fetch = FetchType.LAZY)
    private List<Periodo> periodoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anlectivoId", fetch = FetchType.LAZY)
    private List<Curso> cursoList;
    @JoinColumn(name = "estado_aniolectivo_id", referencedColumnName = "estado_aniolectivo_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoAniolectivo estadoAniolectivoId;
    @JoinColumn(name = "configuracion_id", referencedColumnName = "configuracion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracion configuracionId;

    public Anlectivo() {
    }

    public Anlectivo(Integer anlectivoId) {
        this.anlectivoId = anlectivoId;
    }

    public Anlectivo(Integer anlectivoId, String estadocopiado, int noPeriodos, int anio, boolean terminado) {
        this.anlectivoId = anlectivoId;
        this.estadocopiado = estadocopiado;
        this.noPeriodos = noPeriodos;
        this.anio = anio;
        this.terminado = terminado;
    }

    public Integer getAnlectivoId() {
        return anlectivoId;
    }

    public void setAnlectivoId(Integer anlectivoId) {
        this.anlectivoId = anlectivoId;
    }

    public String getEstadocopiado() {
        return estadocopiado;
    }

    public void setEstadocopiado(String estadocopiado) {
        this.estadocopiado = estadocopiado;
    }

    public int getNoPeriodos() {
        return noPeriodos;
    }

    public void setNoPeriodos(int noPeriodos) {
        this.noPeriodos = noPeriodos;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Periodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<Periodo> periodoList) {
        this.periodoList = periodoList;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    public EstadoAniolectivo getEstadoAniolectivoId() {
        return estadoAniolectivoId;
    }

    public void setEstadoAniolectivoId(EstadoAniolectivo estadoAniolectivoId) {
        this.estadoAniolectivoId = estadoAniolectivoId;
    }

    public Configuracion getConfiguracionId() {
        return configuracionId;
    }

    public void setConfiguracionId(Configuracion configuracionId) {
        this.configuracionId = configuracionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (anlectivoId != null ? anlectivoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anlectivo)) {
            return false;
        }
        Anlectivo other = (Anlectivo) object;
        if ((this.anlectivoId == null && other.anlectivoId != null) || (this.anlectivoId != null && !this.anlectivoId.equals(other.anlectivoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Anlectivo[ anlectivoId=" + anlectivoId + " ]";
    }
    
}
