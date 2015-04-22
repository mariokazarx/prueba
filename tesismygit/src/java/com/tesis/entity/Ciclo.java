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
@Table(name = "ciclo", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciclo.findAll", query = "SELECT c FROM Ciclo c"),
    @NamedQuery(name = "Ciclo.findByCicloId", query = "SELECT c FROM Ciclo c WHERE c.cicloId = :cicloId"),
    @NamedQuery(name = "Ciclo.findByNumero", query = "SELECT c FROM Ciclo c WHERE c.numero = :numero"),
    @NamedQuery(name = "Ciclo.findByConfiguracion", query = "SELECT c FROM Ciclo c WHERE c.configuracion = :configuracion"),
    @NamedQuery(name = "Ciclo.findByDescripcion", query = "SELECT c FROM Ciclo c WHERE c.descripcion = :descripcion")})
public class Ciclo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciclo_id", nullable = false)
    private Integer cicloId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero", nullable = false)
    private int numero;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @JoinColumn(name = "configuracion", referencedColumnName = "configuracion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracion configuracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cicloId", fetch = FetchType.LAZY)
    private List<Curso> cursoList;

    public Ciclo() {
    }

    public Ciclo(Integer cicloId) {
        this.cicloId = cicloId;
    }

    public Ciclo(Integer cicloId, int numero) {
        this.cicloId = cicloId;
        this.numero = numero;
    }

    public Integer getCicloId() {
        return cicloId;
    }

    public void setCicloId(Integer cicloId) {
        this.cicloId = cicloId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    @XmlTransient
    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cicloId != null ? cicloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciclo)) {
            return false;
        }
        Ciclo other = (Ciclo) object;
        if ((this.cicloId == null && other.cicloId != null) || (this.cicloId != null && !this.cicloId.equals(other.cicloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Ciclo[ cicloId=" + cicloId + " ]";
    }
    
}
