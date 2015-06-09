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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "periodo", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findByPeriodoId", query = "SELECT p FROM Periodo p WHERE p.periodoId = :periodoId"),
    @NamedQuery(name = "Periodo.findMinByConfiguracion", query = "SELECT p FROM Periodo p WHERE p.numero =(SELECT MIN(p1.numero) from Periodo p1 WHERE p1.configuracionId = :configuracionId) and p.configuracionId = :configuracionId"),
    @NamedQuery(name = "Periodo.findByConfiguracion", query = "SELECT p FROM Periodo p WHERE p.configuracionId = :configuracionId"),
    @NamedQuery(name = "Periodo.findByNumero", query = "SELECT p FROM Periodo p WHERE p.numero = :numero")})
public class Periodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "periodo_id", nullable = false)
    private Integer periodoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero", nullable = false)
    private int numero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodoId", fetch = FetchType.LAZY)
    private List<Contenidotematico> contenidotematicoList;
    @JoinColumn(name = "configuracion_id", referencedColumnName = "configuracion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracion configuracionId;

    public Periodo() {
    }

    public Periodo(Integer periodoId) {
        this.periodoId = periodoId;
    }

    public Periodo(Integer periodoId, int numero) {
        this.periodoId = periodoId;
        this.numero = numero;
    }

    public Integer getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Integer periodoId) {
        this.periodoId = periodoId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @XmlTransient
    public List<Contenidotematico> getContenidotematicoList() {
        return contenidotematicoList;
    }

    public void setContenidotematicoList(List<Contenidotematico> contenidotematicoList) {
        this.contenidotematicoList = contenidotematicoList;
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
        hash += (periodoId != null ? periodoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.periodoId == null && other.periodoId != null) || (this.periodoId != null && !this.periodoId.equals(other.periodoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Periodo[ periodoId=" + periodoId + " ]";
    }
    
}
