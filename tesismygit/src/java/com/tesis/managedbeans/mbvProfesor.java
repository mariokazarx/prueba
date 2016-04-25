/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.EstadoProfesorFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.TipoUsuarioFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.EstadoProfesor;
import com.tesis.entity.Profesor;
import com.tesis.entity.TipoUsuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.UploadedFile;
import com.tesis.clases.Encrypt;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Periodo;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvProfesor implements Serializable {

    private Profesor profesor;
    private List<Profesor> profesores;
    private UploadedFile foto;
    private String txtRepiteContrasenia;
    private List<EstadoProfesor> estadosProfesor;
    private EstadoProfesor estadoSelected;
    private boolean cambiarContraseña;
    private String cedulaAnterior;
    private String correoAnterior;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private EstadoProfesorFacade estadoEjb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private EstadocontenidotematicoFacade estadocontenidoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @Resource
    UserTransaction tx;

    public mbvProfesor() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public EstadoProfesor getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(EstadoProfesor estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<EstadoProfesor> getEstadosProfesor() {
        return estadosProfesor;
    }

    public void setEstadosProfesor(List<EstadoProfesor> estadosProfesor) {
        this.estadosProfesor = estadosProfesor;
    }

    public boolean isCambiarContraseña() {
        return cambiarContraseña;
    }

    public void setCambiarContraseña(boolean cambiarContraseña) {
        this.cambiarContraseña = cambiarContraseña;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public String getTxtRepiteContrasenia() {
        return txtRepiteContrasenia;
    }

    public void setTxtRepiteContrasenia(String txtRepiteContrasenia) {
        this.txtRepiteContrasenia = txtRepiteContrasenia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ProfesorFacade getProfesorEjb() {
        return profesorEjb;
    }

    public void setProfesorEjb(ProfesorFacade profesorEjb) {
        this.profesorEjb = profesorEjb;
    }

    @PostConstruct
    public void inicioPagina() {
        this.profesor = new Profesor();
        this.estadoSelected = new EstadoProfesor();
        this.cambiarContraseña = false;
        this.estadosProfesor = estadoEjb.findAll();
        this.profesores = this.profesorEjb.findAll();
        this.consultar = false;
        this.editar = false;
        this.eliminar = false;
        this.crear = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
            System.out.println("usuario" + usr.getNombres() + "Login" + login);
        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 10) {
                    if (usrRol.getRoleId().getAgregar()) {
                        this.crear = true;
                    }
                    if (usrRol.getRoleId().getConsultar()) {
                        this.consultar = true;
                    }
                    if (usrRol.getRoleId().getEditar()) {
                        this.editar = true;
                    }
                    if (usrRol.getRoleId().getEliminar()) {
                        this.eliminar = true;
                    }
                }
            }
        }
        if (this.usr.getTipoUsuarioId().getTipoUsuarioId() == 4) {
            this.consultar = true;
            this.editar = true;
            this.eliminar = true;
            this.crear = true;
        }
    }

    public void insertar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.crear) {
                if (this.profesor.getContraseña().equals(this.txtRepiteContrasenia)) {
                    if (profesorEjb.existeCedula(this.profesor.getCedula())) {
                        FacesContext.getCurrentInstance().
                                addMessage("frmProfesor:txtCedula", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cedula en uso"));
                        return;
                    }
                    if (profesorEjb.existeCorreo(this.profesor.getCorreo())) {
                        FacesContext.getCurrentInstance().
                                addMessage("frmProfesor:txtCorreo", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo en uso"));
                        return;
                    }
                    //tx.begin();
                    EstadoProfesor estado = estadoEjb.find(1);
                    TipoUsuario tusu = tusuEjb.find(2);
                    this.profesor.setEstadoProfesorId(estado);
                    this.profesor.setTipoUsuarioId(tusu);
                    this.profesor.setFoto("default.jpg");
                    this.profesor.setContraseña(Encrypt.sha512(this.profesor.getContraseña(), this.profesor.getCorreo()));
                    this.profesorEjb.create(profesor);
                    RequestContext.getCurrentInstance().closeDialog(this);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor creado satisfactoriamente"));
                    //tx.rollback();   
                    inicioPagina();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                    return;
                }
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error inesperado", "Contáctese con el administrador"));
        }

    }

    public void newProfesor() {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.crear) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentHeight", 490);
            options.put("contentWidth", 720);
            options.put("height", 500);
            options.put("width", 730);
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            options.put("responsive", true);
            RequestContext.getCurrentInstance().openDialog("newprofesor", options, null);
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public String cargarMaterias(Profesor profesor) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("param1", profesor);

        return "cargaAcademica?faces-redirect=true";
    }

    public void cargarProfesor(int profesorid) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                this.profesor = profesorEjb.find(profesorid);
                this.estadoSelected = estadoEjb.find(this.profesor.getEstadoProfesorId().getEstadoProfesorId());
                this.txtRepiteContrasenia = this.profesor.getContraseña();
                this.correoAnterior = this.profesor.getCorreo();
                this.cedulaAnterior = this.profesor.getCedula();
                RequestContext.getCurrentInstance().update("frmEditarProfesor:panelEditarProfesor");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarProfesor').show()");
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void activarContraseña() {
        if (cambiarContraseña) {
        } else {
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Profesor registrado satisfactorimente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                tx.begin();
                if (profesorEjb.existeCedula(this.profesor.getCedula()) && !this.profesor.getCedula().equals(this.cedulaAnterior)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarProfesor:txtCedula", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cedula en uso"));
                    tx.rollback();
                    return;
                }
                if (profesorEjb.existeCorreo(this.profesor.getCorreo()) && !this.profesor.getCorreo().equals(this.correoAnterior)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarProfesor:txtCorreo", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo en uso"));
                    tx.rollback();
                    return;
                }
                if (cambiarContraseña) {
                    this.profesor.setContraseña(Encrypt.sha512(this.profesor.getContraseña(), this.profesor.getCorreo()));
                }
                this.estadoSelected = this.estadoEjb.find(estadoSelected.getEstadoProfesorId());
                if (estadoSelected.getEstadoProfesorId() == 2) {
                    Anlectivo anlectivoAux = aEscolarEjb.getIniciado();
                    if (anlectivoAux.getAnlectivoId() != null) {
                        //hay iniciado
                        List<Periodo> peridosAux = periodoEjb.getPeriodosByAnioActivo(anlectivoAux);
                        Estadocontenidotematico estAux = estadocontenidoEjb.find(5);
                        for (Periodo periodoAux : peridosAux) {
                            if (!contenidoEjb.updateRetirarProfesor(periodoAux, estAux, profesor)) {
                                tx.rollback();
                                return;
                            }
                        }
                    }
                    //comprobar si hay un año activo
                    //revisar si tiene asignacion academica
                    //poner en advertencia todos los contenidos de periodos no terminados de ese profesor y de ese año 
                }
                profesor.setEstadoProfesorId(estadoSelected);
                profesorEjb.edit(profesor);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor editado"));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarProfesor').hide()");
                inicioPagina();
                tx.commit();
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar profesores"));
        }
    }
}
