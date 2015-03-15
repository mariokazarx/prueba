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
@Table(name = "estudiante", catalog = "prueba", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByEstudianteId", query = "SELECT e FROM Estudiante e WHERE e.estudianteId = :estudianteId"),
    @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estudiante.findByIdentificiacion", query = "SELECT e FROM Estudiante e WHERE e.identificiacion = :identificiacion"),
    @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Estudiante.findByDireccion", query = "SELECT e FROM Estudiante e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Estudiante.findByApellido", query = "SELECT e FROM Estudiante e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "Estudiante.findByUltimoaprobado", query = "SELECT e FROM Estudiante e WHERE e.ultimoaprobado = :ultimoaprobado")})
public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estudiante_id", nullable = false)
    private Integer estudianteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "identificiacion", nullable = false, length = 12)
    private String identificiacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apellido", nullable = false, length = 200)
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimoaprobado", nullable = false)
    private int ultimoaprobado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Nota> notaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Documento> documentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Notafinal> notafinalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUsuario tipoUsuarioId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Notafinalrecuperacion> notafinalrecuperacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Logronota> logronotaList;

    public Estudiante() {
    }

    public Estudiante(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Estudiante(Integer estudianteId, String nombre, String identificiacion, String telefono, String direccion, String apellido, int ultimoaprobado) {
        this.estudianteId = estudianteId;
        this.nombre = nombre;
        this.identificiacion = identificiacion;
        this.telefono = telefono;
        this.direccion = direccion;
        this.apellido = apellido;
        this.ultimoaprobado = ultimoaprobado;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificiacion() {
        return identificiacion;
    }

    public void setIdentificiacion(String identificiacion) {
        this.identificiacion = identificiacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getUltimoaprobado() {
        return ultimoaprobado;
    }

    public void setUltimoaprobado(int ultimoaprobado) {
        this.ultimoaprobado = ultimoaprobado;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @XmlTransient
    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    @XmlTransient
    public List<Notafinal> getNotafinalList() {
        return notafinalList;
    }

    public void setNotafinalList(List<Notafinal> notafinalList) {
        this.notafinalList = notafinalList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    @XmlTransient
    public List<Notafinalrecuperacion> getNotafinalrecuperacionList() {
        return notafinalrecuperacionList;
    }

    public void setNotafinalrecuperacionList(List<Notafinalrecuperacion> notafinalrecuperacionList) {
        this.notafinalrecuperacionList = notafinalrecuperacionList;
    }

    @XmlTransient
    public List<Logronota> getLogronotaList() {
        return logronotaList;
    }

    public void setLogronotaList(List<Logronota> logronotaList) {
        this.logronotaList = logronotaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudianteId != null ? estudianteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.estudianteId == null && other.estudianteId != null) || (this.estudianteId != null && !this.estudianteId.equals(other.estudianteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Estudiante[ estudianteId=" + estudianteId + " ]";
    }
    
}
