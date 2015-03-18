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
@Table(name = "logro", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logro.findAll", query = "SELECT l FROM Logro l"),
    @NamedQuery(name = "Logro.findByLogroId", query = "SELECT l FROM Logro l WHERE l.logroId = :logroId"),
    @NamedQuery(name = "Logro.findByDescripcion", query = "SELECT l FROM Logro l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "Logro.findByPorcentaje", query = "SELECT l FROM Logro l WHERE l.porcentaje = :porcentaje"),
    @NamedQuery(name = "Logro.findByTitulo", query = "SELECT l FROM Logro l WHERE l.titulo = :titulo")})
public class Logro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logro_id", nullable = false)
    private Integer logroId;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje", nullable = false)
    private int porcentaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "logroId", fetch = FetchType.LAZY)
    private List<Logronota> logronotaList;
    @JoinColumn(name = "contenidotematico_id", referencedColumnName = "contenidotematico_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Contenidotematico contenidotematicoId;

    public Logro() {
    }

    public Logro(Integer logroId) {
        this.logroId = logroId;
    }

    public Logro(Integer logroId, int porcentaje, String titulo) {
        this.logroId = logroId;
        this.porcentaje = porcentaje;
        this.titulo = titulo;
    }

    public Integer getLogroId() {
        return logroId;
    }

    public void setLogroId(Integer logroId) {
        this.logroId = logroId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public List<Logronota> getLogronotaList() {
        return logronotaList;
    }

    public void setLogronotaList(List<Logronota> logronotaList) {
        this.logronotaList = logronotaList;
    }

    public Contenidotematico getContenidotematicoId() {
        return contenidotematicoId;
    }

    public void setContenidotematicoId(Contenidotematico contenidotematicoId) {
        this.contenidotematicoId = contenidotematicoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logroId != null ? logroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logro)) {
            return false;
        }
        Logro other = (Logro) object;
        if ((this.logroId == null && other.logroId != null) || (this.logroId != null && !this.logroId.equals(other.logroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Logro[ logroId=" + logroId + " ]";
    }
    
}
