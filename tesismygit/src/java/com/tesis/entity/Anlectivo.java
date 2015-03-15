/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "Anlectivo.findByAnlectivoId", query = "SELECT a FROM Anlectivo a WHERE a.anlectivoId = :anlectivoId"),
    @NamedQuery(name = "Anlectivo.findByEstado", query = "SELECT a FROM Anlectivo a WHERE a.estado = :estado"),
    @NamedQuery(name = "Anlectivo.findByAnio", query = "SELECT a FROM Anlectivo a WHERE a.anio = :anio"),
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
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio", nullable = false)
    private int anio;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @JoinColumn(name = "configuracion_id", referencedColumnName = "configuracion_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuracion configuracionId;

    public Anlectivo() {
    }

    public Anlectivo(Integer anlectivoId) {
        this.anlectivoId = anlectivoId;
    }

    public Anlectivo(Integer anlectivoId, String estado, int anio) {
        this.anlectivoId = anlectivoId;
        this.estado = estado;
        this.anio = anio;
    }

    public Integer getAnlectivoId() {
        return anlectivoId;
    }

    public void setAnlectivoId(Integer anlectivoId) {
        this.anlectivoId = anlectivoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
