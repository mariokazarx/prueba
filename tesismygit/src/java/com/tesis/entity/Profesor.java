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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mario Jurado
 */
@Entity
@Table(name = "profesor", catalog = "prueba", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cedula"}),
    @UniqueConstraint(columnNames = {"correo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p"),
    @NamedQuery(name = "Profesor.findByProfesorId", query = "SELECT p FROM Profesor p WHERE p.profesorId = :profesorId"),
    @NamedQuery(name = "Profesor.findByCedula", query = "SELECT p FROM Profesor p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "Profesor.findByTelefono", query = "SELECT p FROM Profesor p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Profesor.findByCorreo", query = "SELECT p FROM Profesor p WHERE p.correo = :correo"),
    @NamedQuery(name = "Profesor.findByContrase\u00f1a", query = "SELECT p FROM Profesor p WHERE p.contrase\u00f1a = :contrase\u00f1a"),
    @NamedQuery(name = "Profesor.findBySexo", query = "SELECT p FROM Profesor p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Profesor.findByTituloProfesional", query = "SELECT p FROM Profesor p WHERE p.tituloProfesional = :tituloProfesional"),
    @NamedQuery(name = "Profesor.findByFoto", query = "SELECT p FROM Profesor p WHERE p.foto = :foto"),
    @NamedQuery(name = "Profesor.findByNombre", query = "SELECT p FROM Profesor p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Profesor.findByApellido", query = "SELECT p FROM Profesor p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "Profesor.findByDireccion", query = "SELECT p FROM Profesor p WHERE p.direccion = :direccion")})
public class Profesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "profesor_id", nullable = false)
    private Integer profesorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "cedula", nullable = false, length = 15)
    private String cedula;
    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "contrase\u00f1a", nullable = false, length = 2147483647)
    private String contraseña;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "sexo", nullable = false, length = 2)
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "titulo_profesional", nullable = false, length = 2147483647)
    private String tituloProfesional;
    @Size(max = 2147483647)
    @Column(name = "foto", length = 2147483647)
    private String foto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apellido", nullable = false, length = 200)
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUsuario tipoUsuarioId;
    @JoinColumn(name = "estado_profesor_id", referencedColumnName = "estado_profesor_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoProfesor estadoProfesorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorId", fetch = FetchType.LAZY)
    private List<Notafinal> notafinalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorId", fetch = FetchType.LAZY)
    private List<Contenidotematico> contenidotematicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorId", fetch = FetchType.LAZY)
    private List<Notafinalrecuperacion> notafinalrecuperacionList;

    public Profesor() {
    }

    public Profesor(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public Profesor(Integer profesorId, String cedula, String correo, String contraseña, String sexo, String tituloProfesional, String nombre, String apellido, String direccion) {
        this.profesorId = profesorId;
        this.cedula = cedula;
        this.correo = correo;
        this.contraseña = contraseña;
        this.sexo = sexo;
        this.tituloProfesional = tituloProfesional;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTituloProfesional() {
        return tituloProfesional;
    }

    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public EstadoProfesor getEstadoProfesorId() {
        return estadoProfesorId;
    }

    public void setEstadoProfesorId(EstadoProfesor estadoProfesorId) {
        this.estadoProfesorId = estadoProfesorId;
    }

    @XmlTransient
    public List<Notafinal> getNotafinalList() {
        return notafinalList;
    }

    public void setNotafinalList(List<Notafinal> notafinalList) {
        this.notafinalList = notafinalList;
    }

    @XmlTransient
    public List<Contenidotematico> getContenidotematicoList() {
        return contenidotematicoList;
    }

    public void setContenidotematicoList(List<Contenidotematico> contenidotematicoList) {
        this.contenidotematicoList = contenidotematicoList;
    }

    @XmlTransient
    public List<Notafinalrecuperacion> getNotafinalrecuperacionList() {
        return notafinalrecuperacionList;
    }

    public void setNotafinalrecuperacionList(List<Notafinalrecuperacion> notafinalrecuperacionList) {
        this.notafinalrecuperacionList = notafinalrecuperacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profesorId != null ? profesorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.profesorId == null && other.profesorId != null) || (this.profesorId != null && !this.profesorId.equals(other.profesorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tesis.entity.Profesor[ profesorId=" + profesorId + " ]";
    }
    
}
