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
@Table(name = "escala", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escala.findAll", query = "SELECT e FROM Escala e"),
    @NamedQuery(name = "Escala.findByEscalaId", query = "SELECT e FROM Escala e WHERE e.escalaId = :escalaId"),
    @NamedQuery(name = "Escala.findByNombre", query = "SELECT e FROM Escala e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Escala.findByDescripcion", query = "SELECT e FROM Escala e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Escala.findByMin", query = "SELECT e FROM Escala e WHERE e.min = :min"),
    @NamedQuery(name = "Escala.findByMax", query = "SELECT e FROM Escala e WHERE e.max = :max"),
    @NamedQuery(name = "Escala.findByNotaminimaaprob", query = "SELECT e FROM Escala e WHERE e.notaminimaaprob = :notaminimaaprob")})
public class Escala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "escala_id", nullable = false)
    private Integer escalaId;
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
    @Column(name = "min", nullable = false)
    private int min;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max", nullable = false)
    private int max;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notaminimaaprob", nullable = false)
    private int notaminimaaprob;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escalaId", fetch = FetchType.LAZY)
    private List<Configuracion> configuracionList;

    public Escala() {
    }

    public Escala(Integer escalaId) {
        this.escalaId = escalaId;
    }

    public Escala(Integer escalaId, String nombre, int min, int max, int notaminimaaprob) {
        this.escalaId = escalaId;
        this.nombre = nombre;
        this.min = min;
        this.max = max;
        this.notaminimaaprob = notaminimaaprob;
    }

    public Integer getEscalaId() {
        return escalaId;
    }

    public void setEscalaId(Integer escalaId) {
        this.escalaId = escalaId;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNotaminimaaprob() {
        return notaminimaaprob;
    }

    public void setNotaminimaaprob(int notaminimaaprob) {
        this.notaminimaaprob = notaminimaaprob;
    }

    @XmlTransient
    public List<Configuracion> getConfiguracionList() {
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (escalaId != null ? escalaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escala)) {
            return false;
        }
        Escala other = (Escala) object;
        if ((this.escalaId == null && other.escalaId != null) || (this.escalaId != null && !this.escalaId.equals(other.escalaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Escala[ escalaId=" + escalaId + " ]";
    }
    
}
