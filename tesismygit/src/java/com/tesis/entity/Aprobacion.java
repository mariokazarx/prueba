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
@Table(name = "aprobacion", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aprobacion.findAll", query = "SELECT a FROM Aprobacion a"),
    @NamedQuery(name = "Aprobacion.findByAprobacionId", query = "SELECT a FROM Aprobacion a WHERE a.aprobacionId = :aprobacionId"),
    @NamedQuery(name = "Aprobacion.findByNombre", query = "SELECT a FROM Aprobacion a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Aprobacion.findByDescripcion", query = "SELECT a FROM Aprobacion a WHERE a.descripcion = :descripcion")})
public class Aprobacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aprobacion_id", nullable = false)
    private Integer aprobacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aprobacionId", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;

    public Aprobacion() {
    }

    public Aprobacion(Integer aprobacionId) {
        this.aprobacionId = aprobacionId;
    }

    public Aprobacion(Integer aprobacionId, String nombre) {
        this.aprobacionId = aprobacionId;
        this.nombre = nombre;
    }

    public Integer getAprobacionId() {
        return aprobacionId;
    }

    public void setAprobacionId(Integer aprobacionId) {
        this.aprobacionId = aprobacionId;
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
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aprobacionId != null ? aprobacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aprobacion)) {
            return false;
        }
        Aprobacion other = (Aprobacion) object;
        if ((this.aprobacionId == null && other.aprobacionId != null) || (this.aprobacionId != null && !this.aprobacionId.equals(other.aprobacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Aprobacion[ aprobacionId=" + aprobacionId + " ]";
    }
    
}
