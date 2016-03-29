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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "asignatura", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a"),
    @NamedQuery(name = "Asignatura.findByAsignaturaId", query = "SELECT a FROM Asignatura a WHERE a.asignaturaId = :asignaturaId"),
    @NamedQuery(name = "Asignatura.findByNombre", query = "SELECT a FROM Asignatura a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Asignatura.findByCiclo", query = "SELECT a FROM Asignatura a JOIN a.asignaturacicloList ac WHERE ac.cicloId = :ciclo"),
    @NamedQuery(name = "Asignatura.findByConfiguracion", query = "SELECT a FROM Asignatura a WHERE a.configuracionId = :configuracion"),
    @NamedQuery(name = "Asignatura.findByConf", query = "SELECT a FROM Asignatura a WHERE a.configuracionId = :configuracion EXCEPT SELECT a FROM Asignatura a JOIN a.asignaturacicloList ac WHERE ac.cicloId = :ciclo"),
    @NamedQuery(name = "Asignatura.removeById", query = "DELETE FROM Asignatura a WHERE a.asignaturaId = :asignaturaId"),
    @NamedQuery(name = "Asignatura.findByDescripcion", query = "SELECT a FROM Asignatura a WHERE a.descripcion = :descripcion")})
public class Asignatura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "asignatura_id", nullable = false)
    private Integer asignaturaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturaId", fetch = FetchType.LAZY)
    private List<Asignaturaciclo> asignaturacicloList;
    @JoinColumn(name = "configuracion_id", referencedColumnName = "configuracion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracion configuracionId;
    @JoinColumn(name = "area_id", referencedColumnName = "area_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Area areaId;

    public Asignatura() {
    }

    public Asignatura(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Asignatura(Integer asignaturaId, String nombre) {
        this.asignaturaId = asignaturaId;
        this.nombre = nombre;
    }

    public Integer getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
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

    @XmlTransient
    public List<Asignaturaciclo> getAsignaturacicloList() {
        return asignaturacicloList;
    }

    public void setAsignaturacicloList(List<Asignaturaciclo> asignaturacicloList) {
        this.asignaturacicloList = asignaturacicloList;
    }

    public Configuracion getConfiguracionId() {
        return configuracionId;
    }

    public void setConfiguracionId(Configuracion configuracionId) {
        this.configuracionId = configuracionId;
    }

    public Area getAreaId() {
        return areaId;
    }

    public void setAreaId(Area areaId) {
        this.areaId = areaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignaturaId != null ? asignaturaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.asignaturaId == null && other.asignaturaId != null) || (this.asignaturaId != null && !this.asignaturaId.equals(other.asignaturaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Asignatura[ asignaturaId=" + asignaturaId + " ]";
    }
    
}
