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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "configuracion", catalog = "prueba", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c"),
    @NamedQuery(name = "Configuracion.findByConfiguracionId", query = "SELECT c FROM Configuracion c WHERE c.configuracionId = :configuracionId"),
    @NamedQuery(name = "Configuracion.findByNombre", query = "SELECT c FROM Configuracion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Configuracion.enUso", query = "SELECT COUNT(c) FROM Configuracion c JOIN c.anlectivoList an WHERE c.configuracionId = :configuracionId and an.estadoAniolectivoId.estadoAniolectivoId != 1"),
    @NamedQuery(name = "Configuracion.removeById", query = "DELETE FROM Configuracion c WHERE c.configuracionId = :configuracionId"),
    @NamedQuery(name = "Configuracion.findByDescripcion", query = "SELECT c FROM Configuracion c WHERE c.descripcion = :descripcion")})
public class Configuracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "configuracion_id", nullable = false)
    private Integer configuracionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @JoinColumn(name = "escala_id", referencedColumnName = "escala_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Escala escalaId;
    @JoinColumn(name = "criterioevaluacion_id", referencedColumnName = "criterioevaluacion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Criterioevaluacion criterioevaluacionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracionId", fetch = FetchType.LAZY)
    private List<Area> areaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracionId", fetch = FetchType.LAZY)
    private List<Asignatura> asignaturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracion", fetch = FetchType.LAZY)
    private List<Ciclo> cicloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracionId", fetch = FetchType.LAZY)
    private List<Anlectivo> anlectivoList;

    public Configuracion() {
    }

    public Configuracion(Integer configuracionId) {
        this.configuracionId = configuracionId;
    }

    public Configuracion(Integer configuracionId, String nombre) {
        this.configuracionId = configuracionId;
        this.nombre = nombre;
    }

    public Integer getConfiguracionId() {
        return configuracionId;
    }

    public void setConfiguracionId(Integer configuracionId) {
        this.configuracionId = configuracionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Escala getEscalaId() {
        return escalaId;
    }

    public void setEscalaId(Escala escalaId) {
        this.escalaId = escalaId;
    }

    public Criterioevaluacion getCriterioevaluacionId() {
        return criterioevaluacionId;
    }

    public void setCriterioevaluacionId(Criterioevaluacion criterioevaluacionId) {
        this.criterioevaluacionId = criterioevaluacionId;
    }

    @XmlTransient
    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    @XmlTransient
    public List<Asignatura> getAsignaturaList() {
        return asignaturaList;
    }

    public void setAsignaturaList(List<Asignatura> asignaturaList) {
        this.asignaturaList = asignaturaList;
    }

    @XmlTransient
    public List<Ciclo> getCicloList() {
        return cicloList;
    }

    public void setCicloList(List<Ciclo> cicloList) {
        this.cicloList = cicloList;
    }

    @XmlTransient
    public List<Anlectivo> getAnlectivoList() {
        return anlectivoList;
    }

    public void setAnlectivoList(List<Anlectivo> anlectivoList) {
        this.anlectivoList = anlectivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configuracionId != null ? configuracionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.configuracionId == null && other.configuracionId != null) || (this.configuracionId != null && !this.configuracionId.equals(other.configuracionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Configuracion[ configuracionId=" + configuracionId + " ]";
    }
    
}
