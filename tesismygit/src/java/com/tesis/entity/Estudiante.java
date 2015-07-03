/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "estudiante", catalog = "prueba", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"identificiacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByEstudianteId", query = "SELECT e FROM Estudiante e WHERE e.estudianteId = :estudianteId"),
    @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estudiante.findByIdentificiacion", query = "SELECT e FROM Estudiante e WHERE e.identificiacion = :identificiacion"),
    @NamedQuery(name = "Estudiante.findByTipoIdentifcacion", query = "SELECT e FROM Estudiante e WHERE e.tipoIdentifcacion = :tipoIdentifcacion"),
    @NamedQuery(name = "Estudiante.findByFechaNacimiento", query = "SELECT e FROM Estudiante e WHERE e.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Estudiante.findByDireccion", query = "SELECT e FROM Estudiante e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Estudiante.findByApellido", query = "SELECT e FROM Estudiante e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "Estudiante.findBySexo", query = "SELECT e FROM Estudiante e WHERE e.sexo = :sexo"),
    @NamedQuery(name = "Estudiante.findByZona", query = "SELECT e FROM Estudiante e WHERE e.zona = :zona"),
    @NamedQuery(name = "Estudiante.findBySisben", query = "SELECT e FROM Estudiante e WHERE e.sisben = :sisben"),
    @NamedQuery(name = "Estudiante.findByEstrato", query = "SELECT e FROM Estudiante e WHERE e.estrato = :estrato"),
    @NamedQuery(name = "Estudiante.findByResguardo", query = "SELECT e FROM Estudiante e WHERE e.resguardo = :resguardo"),
    @NamedQuery(name = "Estudiante.findByCabezaFamilia", query = "SELECT e FROM Estudiante e WHERE e.cabezaFamilia = :cabezaFamilia"),
    @NamedQuery(name = "Estudiante.findByAcudiente", query = "SELECT e FROM Estudiante e WHERE e.acudiente = :acudiente"),
    @NamedQuery(name = "Estudiante.findByFoto", query = "SELECT e FROM Estudiante e WHERE e.foto = :foto"),
    @NamedQuery(name = "Estudiante.findByCurso", query = "SELECT e FROM Estudiante e JOIN e.matriculaList m WHERE m.cursoId = :cursoId"),
    @NamedQuery(name = "Estudiante.findNotaLOgro", query = "SELECT logn FROM Estudiante e JOIN e.logronotaList logn WHERE logn.logroId = :logroId AND e.estudianteId = :estudianteId"),
    @NamedQuery(name = "Estudiante.findNotaEst", query = "SELECT nt FROM Estudiante e JOIN e.notaList nt WHERE nt.contenidotematicoId = :contenidotematicoId AND e.estudianteId = :estudianteId"),
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
    @Size(min = 1, max = 5)
    @Column(name = "tipo_identifcacion", nullable = false, length = 5)
    private String tipoIdentifcacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
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
    @Size(min = 1, max = 2)
    @Column(name = "sexo", nullable = false, length = 2)
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "zona", nullable = false, length = 7)
    private String zona;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "sisben", nullable = false, precision = 2, scale = 1)
    private BigDecimal sisben;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estrato", nullable = false)
    private int estrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "resguardo", nullable = false, length = 2)
    private String resguardo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "cabeza_familia", nullable = false, length = 2)
    private String cabezaFamilia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "acudiente", nullable = false, length = 100)
    private String acudiente;
    @Size(max = 2147483647)
    @Column(name = "foto", length = 2147483647)
    private String foto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ultimoaprobado", nullable = false)
    private int ultimoaprobado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Nota> notaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Notafinal> notafinalList;
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoUsuario tipoUsuarioId;
    @JoinColumn(name = "estado_estudiante_id", referencedColumnName = "estado_estudiante_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoEstudiante estadoEstudianteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Logronota> logronotaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Documento> documentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId", fetch = FetchType.LAZY)
    private List<Notafinalrecuperacion> notafinalrecuperacionList;

    public Estudiante() {
    }

    public Estudiante(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Estudiante(Integer estudianteId, String nombre, String identificiacion, String tipoIdentifcacion, Date fechaNacimiento, String telefono, String direccion, String apellido, String sexo, String zona, BigDecimal sisben, int estrato, String resguardo, String cabezaFamilia, String acudiente, int ultimoaprobado) {
        this.estudianteId = estudianteId;
        this.nombre = nombre;
        this.identificiacion = identificiacion;
        this.tipoIdentifcacion = tipoIdentifcacion;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.apellido = apellido;
        this.sexo = sexo;
        this.zona = zona;
        this.sisben = sisben;
        this.estrato = estrato;
        this.resguardo = resguardo;
        this.cabezaFamilia = cabezaFamilia;
        this.acudiente = acudiente;
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

    public String getTipoIdentifcacion() {
        return tipoIdentifcacion;
    }

    public void setTipoIdentifcacion(String tipoIdentifcacion) {
        this.tipoIdentifcacion = tipoIdentifcacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public BigDecimal getSisben() {
        return sisben;
    }

    public void setSisben(BigDecimal sisben) {
        this.sisben = sisben;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public String getResguardo() {
        return resguardo;
    }

    public void setResguardo(String resguardo) {
        this.resguardo = resguardo;
    }

    public String getCabezaFamilia() {
        return cabezaFamilia;
    }

    public void setCabezaFamilia(String cabezaFamilia) {
        this.cabezaFamilia = cabezaFamilia;
    }

    public String getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(String acudiente) {
        this.acudiente = acudiente;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
    public List<Notafinal> getNotafinalList() {
        return notafinalList;
    }

    public void setNotafinalList(List<Notafinal> notafinalList) {
        this.notafinalList = notafinalList;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public EstadoEstudiante getEstadoEstudianteId() {
        return estadoEstudianteId;
    }

    public void setEstadoEstudianteId(EstadoEstudiante estadoEstudianteId) {
        this.estadoEstudianteId = estadoEstudianteId;
    }

    @XmlTransient
    public List<Logronota> getLogronotaList() {
        return logronotaList;
    }

    public void setLogronotaList(List<Logronota> logronotaList) {
        this.logronotaList = logronotaList;
    }

    @XmlTransient
    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @XmlTransient
    public List<Notafinalrecuperacion> getNotafinalrecuperacionList() {
        return notafinalrecuperacionList;
    }

    public void setNotafinalrecuperacionList(List<Notafinalrecuperacion> notafinalrecuperacionList) {
        this.notafinalrecuperacionList = notafinalrecuperacionList;
    }
    //@XmlTransient
    public BigDecimal getnotaLogro(Integer logroId) {
        System.out.println("ENTRA TRAER NOTA");
        BigDecimal nota = new BigDecimal(0);
        for(Logronota logNota : this.logronotaList){
            System.out.println("Traer Nota"+logronotaList+"NOMBRE"+nombre+"LOGRO ID"+logNota.getLogroId().getLogroId()+"PARAMETRO"+logroId);
            if(logroId==logNota.getLogroId().getLogroId()){
                nota = logNota.getNota();
            }
        }
        return nota;
    }
    public void setnotaLogro(Integer logroId,BigDecimal notanew) {
        System.out.println("Poner Nota"+notanew);
        for(Logronota logNota : this.logronotaList){
            if(logroId==logNota.getLogronotaId()){
                logNota.setNota(notanew);
            }
        }
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
