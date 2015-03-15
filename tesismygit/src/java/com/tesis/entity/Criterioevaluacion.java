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
@Table(name = "criterioevaluacion", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Criterioevaluacion.findAll", query = "SELECT c FROM Criterioevaluacion c"),
    @NamedQuery(name = "Criterioevaluacion.findByCriterioevaluacionId", query = "SELECT c FROM Criterioevaluacion c WHERE c.criterioevaluacionId = :criterioevaluacionId"),
    @NamedQuery(name = "Criterioevaluacion.findByNombre", query = "SELECT c FROM Criterioevaluacion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Criterioevaluacion.findByDescripcion", query = "SELECT c FROM Criterioevaluacion c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Criterioevaluacion.findByMinaprob", query = "SELECT c FROM Criterioevaluacion c WHERE c.minaprob = :minaprob")})
public class Criterioevaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "criterioevaluacion_id", nullable = false)
    private Integer criterioevaluacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "minaprob", nullable = false)
    private int minaprob;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criterioevaluacionId", fetch = FetchType.LAZY)
    private List<Configuracion> configuracionList;
    @JoinColumn(name = "formacriterioevaluacion_id", referencedColumnName = "formacriterioevaluacion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Formacriterioevaluacion formacriterioevaluacionId;

    public Criterioevaluacion() {
    }

    public Criterioevaluacion(Integer criterioevaluacionId) {
        this.criterioevaluacionId = criterioevaluacionId;
    }

    public Criterioevaluacion(Integer criterioevaluacionId, String nombre, int minaprob) {
        this.criterioevaluacionId = criterioevaluacionId;
        this.nombre = nombre;
        this.minaprob = minaprob;
    }

    public Integer getCriterioevaluacionId() {
        return criterioevaluacionId;
    }

    public void setCriterioevaluacionId(Integer criterioevaluacionId) {
        this.criterioevaluacionId = criterioevaluacionId;
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

    public int getMinaprob() {
        return minaprob;
    }

    public void setMinaprob(int minaprob) {
        this.minaprob = minaprob;
    }

    @XmlTransient
    public List<Configuracion> getConfiguracionList() {
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    public Formacriterioevaluacion getFormacriterioevaluacionId() {
        return formacriterioevaluacionId;
    }

    public void setFormacriterioevaluacionId(Formacriterioevaluacion formacriterioevaluacionId) {
        this.formacriterioevaluacionId = formacriterioevaluacionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (criterioevaluacionId != null ? criterioevaluacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criterioevaluacion)) {
            return false;
        }
        Criterioevaluacion other = (Criterioevaluacion) object;
        if ((this.criterioevaluacionId == null && other.criterioevaluacionId != null) || (this.criterioevaluacionId != null && !this.criterioevaluacionId.equals(other.criterioevaluacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Criterioevaluacion[ criterioevaluacionId=" + criterioevaluacionId + " ]";
    }
    
}
