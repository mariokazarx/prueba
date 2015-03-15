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
import javax.persistence.Id;
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
@Table(name = "formacriterioevaluacion", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formacriterioevaluacion.findAll", query = "SELECT f FROM Formacriterioevaluacion f"),
    @NamedQuery(name = "Formacriterioevaluacion.findByFormacriterioevaluacionId", query = "SELECT f FROM Formacriterioevaluacion f WHERE f.formacriterioevaluacionId = :formacriterioevaluacionId"),
    @NamedQuery(name = "Formacriterioevaluacion.findByNombre", query = "SELECT f FROM Formacriterioevaluacion f WHERE f.nombre = :nombre")})
public class Formacriterioevaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "formacriterioevaluacion_id", nullable = false)
    private Integer formacriterioevaluacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formacriterioevaluacionId", fetch = FetchType.LAZY)
    private List<Criterioevaluacion> criterioevaluacionList;

    public Formacriterioevaluacion() {
    }

    public Formacriterioevaluacion(Integer formacriterioevaluacionId) {
        this.formacriterioevaluacionId = formacriterioevaluacionId;
    }

    public Formacriterioevaluacion(Integer formacriterioevaluacionId, String nombre) {
        this.formacriterioevaluacionId = formacriterioevaluacionId;
        this.nombre = nombre;
    }

    public Integer getFormacriterioevaluacionId() {
        return formacriterioevaluacionId;
    }

    public void setFormacriterioevaluacionId(Integer formacriterioevaluacionId) {
        this.formacriterioevaluacionId = formacriterioevaluacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Criterioevaluacion> getCriterioevaluacionList() {
        return criterioevaluacionList;
    }

    public void setCriterioevaluacionList(List<Criterioevaluacion> criterioevaluacionList) {
        this.criterioevaluacionList = criterioevaluacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formacriterioevaluacionId != null ? formacriterioevaluacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formacriterioevaluacion)) {
            return false;
        }
        Formacriterioevaluacion other = (Formacriterioevaluacion) object;
        if ((this.formacriterioevaluacionId == null && other.formacriterioevaluacionId != null) || (this.formacriterioevaluacionId != null && !this.formacriterioevaluacionId.equals(other.formacriterioevaluacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Formacriterioevaluacion[ formacriterioevaluacionId=" + formacriterioevaluacionId + " ]";
    }
    
}
