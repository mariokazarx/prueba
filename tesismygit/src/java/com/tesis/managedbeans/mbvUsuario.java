/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.EstadoUsuarioFacade;
import com.tesis.beans.RecursoFacade;
import com.tesis.beans.RoleFacade;
import com.tesis.beans.TipoUsuarioFacade;
import com.tesis.beans.UsuarioFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.Encrypt;
import com.tesis.entity.EstadoUsuario;
import com.tesis.entity.Recurso;
import com.tesis.entity.Role;
import com.tesis.entity.TipoUsuario;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvUsuario implements Serializable {
    private static final long serialVersionUID = -4278771562644577627L;

    private Usuario usuario;
    private Recurso recursoSelected;
    private Role roleActual;
    private List<Role> rolesRecursos;
    private List<Recurso> recursos;
    private List<Usuario> usuarios;
    private String txtRepiteContrasenia;
    private String correoAnetrior;
    private String cedulaAnterior;
    private EstadoUsuario estadoSelected;
    private List<EstadoUsuario> estadosUsuarios;
    private boolean cambiarContraseña;
    private boolean mostrarEditar;
    private boolean mostrarCrear;
    private boolean mostrarConsultar;
    private boolean mostrarEliminar;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private boolean usrRoot;
    private Usuario usr;
    @EJB
    private UsuarioFacade usuarioEjb;
    @EJB
    private RecursoFacade recursoEjb;
    @EJB
    private RoleFacade roleEjb;
    @EJB
    private UsuarioRoleFacade userroleEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    @EJB
    private EstadoUsuarioFacade estUsuEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @Resource
    UserTransaction tx;

    public mbvUsuario() {
    }

    public boolean isUsrRoot() {
        return usrRoot;
    }

    public void setUsrRoot(boolean usrRoot) {
        this.usrRoot = usrRoot;
    }

    public List<EstadoUsuario> getEstadosUsuarios() {
        return estadosUsuarios;
    }

    public void setEstadosUsuarios(List<EstadoUsuario> estadosUsuarios) {
        this.estadosUsuarios = estadosUsuarios;
    }

    public EstadoUsuario getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(EstadoUsuario estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public boolean isMostrarEditar() {
        return mostrarEditar;
    }

    public boolean isMostrarCrear() {
        return mostrarCrear;
    }

    public boolean isMostrarConsultar() {
        return mostrarConsultar;
    }

    public boolean isMostrarEliminar() {
        return mostrarEliminar;
    }

    public boolean isCambiarContraseña() {
        return cambiarContraseña;
    }

    public void setCambiarContraseña(boolean cambiarContraseña) {
        this.cambiarContraseña = cambiarContraseña;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Recurso getRecursoSelected() {
        return recursoSelected;
    }

    public void setRecursoSelected(Recurso recursoSelected) {
        this.recursoSelected = recursoSelected;
    }

    public Role getRoleActual() {
        return roleActual;
    }

    public void setRoleActual(Role roleActual) {
        this.roleActual = roleActual;
    }

    public List<Role> getRolesRecursos() {
        return rolesRecursos;
    }

    public void setRolesRecursos(List<Role> rolesRecursos) {
        this.rolesRecursos = rolesRecursos;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getTxtRepiteContrasenia() {
        return txtRepiteContrasenia;
    }

    public void setTxtRepiteContrasenia(String txtRepiteContrasenia) {
        this.txtRepiteContrasenia = txtRepiteContrasenia;
    }

    @PostConstruct
    public void inicioPagina() {
        this.consultar = false;
        this.editar = false;
        this.eliminar = false;
        this.crear = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 12) {
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
            this.usuarios = this.usuarioEjb.findAll();
        } else {
            this.usuarios = this.usuarioEjb.usersAdmin();
        }
        this.mostrarConsultar = false;
        this.mostrarEditar = false;
        this.mostrarEliminar = false;
        this.mostrarCrear = false;
        this.usrRoot = false;
        this.usuario = new Usuario();
        this.recursoSelected = new Recurso();
        this.recursos = this.recursoEjb.findAll();
        this.roleActual = new Role();
        this.rolesRecursos = new ArrayList<Role>();
        this.estadosUsuarios = this.estUsuEjb.findAll();
        this.estadoSelected = new EstadoUsuario();
    }

    public void actualizar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                tx.begin();
                if (usuarioEjb.existeCedula(this.usuario.getIdentificacion()) && !this.usuario.getIdentificacion().equals(this.cedulaAnterior)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarUsuario:txtCedula", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cedula en uso"));
                    tx.rollback();
                    return;
                }
                if (usuarioEjb.existeCorreo(this.usuario.getCorreo()) && !this.usuario.getCorreo().equals(this.correoAnetrior)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarUsuario:txtCorreo", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo en uso"));
                    tx.rollback();
                    return;
                }
                if (cambiarContraseña) {
                    if (this.usuario.getContraseña().equals(this.txtRepiteContrasenia)) {
                        usuario.setContraseña(Encrypt.sha512(this.usuario.getContraseña(), this.usuario.getCorreo()));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                        tx.rollback();
                        return;
                    }
                }

                //usuario.getUsuarioRoleList().clear();
                //usuarioEjb.edit(this.usuario);
                List<UsuarioRole> auxUsrRoles = new ArrayList<UsuarioRole>(userroleEjb.getByUser(usuario));
                //auxUsrRoles = usuario.getUsuarioRoleList();
                if (!userroleEjb.getByUser(usuario).isEmpty()) {
                    if (!userroleEjb.removeByUsuario(usuario)) {
                        //tx.rollback();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                        return;
                    }
                    usuario.getUsuarioRoleList().clear();
                    for (UsuarioRole usrRolAux : auxUsrRoles) {
                        if (!roleEjb.removeById(usrRolAux.getRoleId().getRoleId())) {
                            tx.rollback();
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                            return;
                        }
                    }
                }
                tx.commit();
                //borrar roles por rol id de recursos
                // borrar usuario roles
                tx.begin();
                if (rolesRecursos.size() > 0 || this.usuario.getTipoUsuarioId().getTipoUsuarioId() == 4) {
                    for (Role aux : rolesRecursos) {
                        roleEjb.create(aux);
                        UsuarioRole usrRol = new UsuarioRole();
                        usrRol.setRoleId(aux);
                        usrRol.setUsuarioId(usuario);
                        userroleEjb.create(usrRol);
                    }
                    estadoSelected = estUsuEjb.find(estadoSelected.getEstadoUsuarioId());
                    this.usuario.setEstadoUsuarioId(estadoSelected);
                    usuarioEjb.edit(this.usuario);
                    tx.commit();
                    //closeDialog();
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario editado"));
                    //tx.rollback();    
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').hide()");
                    inicioPagina();
                } else {
                    tx.rollback();
                    FacesContext.getCurrentInstance().addMessage("frmEditarUsuario:selectRecurso", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Debe almenos seleccionar un recurso"));
                    return;
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error inesperado", "Contáctese con el administrador"));
        }

    }

    public void prueba() {
        List<UsuarioRole> auxUsrRoles = new ArrayList<UsuarioRole>(userroleEjb.getByUser(usuario));
        if (!userroleEjb.removeByUsuario(usuario)) {
        } else {
            for (UsuarioRole usrRolAux : auxUsrRoles) {
                if (!roleEjb.removeById(usrRolAux.getRoleId().getRoleId())) {
                } else {
                }
            }
        }
    }

    public void insertar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.crear) {
                tx.begin();
                if (this.usuario.getContraseña().equals(this.txtRepiteContrasenia)) {
                    if (usuarioEjb.existeCedula(this.usuario.getIdentificacion())) {
                        FacesContext.getCurrentInstance().
                                addMessage("frmUsuario:txtCedula", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cedula en uso"));
                        return;
                    }
                    if (usuarioEjb.existeCorreo(this.usuario.getCorreo())) {
                        FacesContext.getCurrentInstance().
                                addMessage("frmUsuario:txtCorreo", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo en uso"));
                        return;
                    }
                    EstadoUsuario estado = estUsuEjb.find(1);
                    TipoUsuario tusu = tusuEjb.find(3);
                    usuario.setEstadoUsuarioId(estado);
                    usuario.setTipoUsuarioId(tusu);
                    this.usuario.setFoto("default.jpg");
                    usuario.setContraseña(Encrypt.sha512(this.usuario.getContraseña(), this.usuario.getCorreo()));
                    usuarioEjb.create(this.usuario);
                    if (rolesRecursos.size() > 0) {
                        for (Role aux : rolesRecursos) {
                            roleEjb.create(aux);
                            UsuarioRole usrRol = new UsuarioRole();
                            usrRol.setRoleId(aux);
                            usrRol.setUsuarioId(usuario);
                            userroleEjb.create(usrRol);
                        }
                        tx.commit();
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario creado satisfactoriamente"));
                        //tx.rollback();   
                        RequestContext.getCurrentInstance().closeDialog(this);
                        inicioPagina();
                    } else {
                        tx.rollback();
                        FacesContext.getCurrentInstance().addMessage("frmUsuario:selectRecurso", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Debe asignar almenos un recurso"));
                        return;
                    }

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                    tx.rollback();
                    return;
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error inesperado", "Contáctese con el administrador"));
        }

    }

    public void newUsuario() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.crear) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentHeight", 600);
            options.put("contentWidth", 800);
            options.put("height", 600);
            options.put("width", 800);
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newusuario", options, null);
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void checkPermiso() {
        if (recursoSelected != null) {
            if ((roleActual.getAgregar() || roleActual.getEditar() || roleActual.getEditar()) && roleActual.getConsultar() == false) {
                roleActual.setConsultar(true);
            }
            if (roleActual.getAgregar() == false && roleActual.getConsultar() == false && roleActual.getEditar() == false && roleActual.getEliminar() == false) {
                if (rolesRecursos.size() > 0) {
                    for (Role aux : rolesRecursos) {
                        if (aux.getRecursoId().equals(recursoSelected)) {
                            rolesRecursos.remove(aux);
                            return;
                        }
                    }
                }
            } else {
                if (rolesRecursos.size() > 0) {
                    boolean noesta = true;
                    for (Role aux : rolesRecursos) {
                        if (aux.getRecursoId().equals(recursoSelected)) {
                            noesta = false;
                            return;
                        }
                    }
                    if (noesta) {
                        Recurso recaux = new Recurso();
                        recaux = recursoEjb.find(recursoSelected.getRecursoId());
                        roleActual.setRecursoId(recaux);
                        rolesRecursos.add(roleActual);

                    }
                } else {
                    Recurso recaux = new Recurso();
                    recaux = recursoEjb.find(recursoSelected.getRecursoId());
                    roleActual.setRecursoId(recaux);
                    rolesRecursos.add(roleActual);
                }
                //recursoSelected = new Recurso();
                //roleActual = new Role();
                //return;
            }
        }
    }

    public void cargarPermiso() {
        if (recursoSelected.getRecursoId() != null) {
            if (recursoSelected.getRecursoId() > 0 || recursoSelected.getRecursoId() < 19) {
                if (recursoSelected.getRecursoId() == 16 || recursoSelected.getRecursoId() == 8 || recursoSelected.getRecursoId() == 9 || recursoSelected.getRecursoId() == 4) {
                    this.mostrarConsultar = true;
                    this.mostrarEditar = true;
                    this.mostrarEliminar = false;
                    this.mostrarCrear = false;
                } else if (recursoSelected.getRecursoId() == 17 || recursoSelected.getRecursoId() == 18) {
                    this.mostrarConsultar = true;
                    this.mostrarEditar = false;
                    this.mostrarEliminar = false;
                    this.mostrarCrear = false;
                } else {
                    this.mostrarConsultar = true;
                    this.mostrarEditar = true;
                    this.mostrarEliminar = true;
                    this.mostrarCrear = true;
                }
                if (rolesRecursos.size() > 0) {
                    boolean encontro = false;
                    for (Role aux : rolesRecursos) {
                        if (aux.getRecursoId().equals(recursoSelected)) {
                            roleActual = aux;
                            encontro = true;
                            return;
                        }
                    }
                    if (!encontro) {
                        roleActual = new Role();
                    }
                } else {
                    roleActual = new Role();
                }
            } else {
                this.mostrarConsultar = false;
                this.mostrarEditar = false;
                this.mostrarEliminar = false;
                this.mostrarCrear = false;
            }
        }
    }

    public void cargarUsuario(int usuarioId) {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.editar) {
                this.usuario = this.usuarioEjb.find(usuarioId);
                this.rolesRecursos.clear();
                this.estadoSelected = estUsuEjb.find(this.usuario.getEstadoUsuarioId().getEstadoUsuarioId());
                if (this.usuario.getTipoUsuarioId().getTipoUsuarioId() == 4) {
                    this.usrRoot = true;
                } else {
                    this.usrRoot = false;
                }
                //if(!usuario.getUsuarioRoleList().isEmpty()){
                //    System.out.println("FFGFGHG"+this.usuario.getUsuarioRoleList());
                //}
                //usuarioEjb.notifyAll();
                for (UsuarioRole usrRolAux : userroleEjb.getByUser(usuario)) {
                    this.rolesRecursos.add(usrRolAux.getRoleId());
                }
                this.cargarPermiso();
                //this.nomoriginal = this.escala.getNombre();
                this.cedulaAnterior = this.usuario.getIdentificacion();
                this.correoAnetrior = this.usuario.getCorreo();
                RequestContext.getCurrentInstance().update("frmEditarUsuario:panelEditarUsuario");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').show()");
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario creado satisfactoriamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void eliminarUsuario(Usuario usuario) {
        try {
            List<UsuarioRole> auxUsrRoles = new ArrayList<UsuarioRole>(userroleEjb.getByUser(usuario));
            tx.begin();
            if (!userroleEjb.getByUser(usuario).isEmpty()) {
                if (!userroleEjb.removeByUsuario(usuario)) {
                    //tx.rollback();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                    return;
                }
                usuario.getUsuarioRoleList().clear();
                for (UsuarioRole usrRolAux : auxUsrRoles) {
                    if (!roleEjb.removeById(usrRolAux.getRoleId().getRoleId())) {
                        tx.rollback();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
                        return;
                    }
                }
            }
            if (usuarioEjb.removeById(usuario.getUsuarioId())) {
                tx.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado"));
                inicioPagina();
            } else {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Las contraseñas no coinciden"));
            }


        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void activarContraseña() {
        if (cambiarContraseña) {
        } else {
        }
    }

    public String traducir(boolean estado) {
        /*if (recurso.getRecursoId() == 16 || recurso.getRecursoId() == 8 || recurso.getRecursoId() == 9 || recurso.getRecursoId() == 4) {
            this.mostrarConsultar = true;
            this.mostrarEditar = true;
            this.mostrarEliminar = false;
            this.mostrarCrear = false;
        } else if (recurso.getRecursoId() == 17 || recurso.getRecursoId() == 18) {
            this.mostrarConsultar = true;
            this.mostrarEditar = false;
            this.mostrarEliminar = false;
            this.mostrarCrear = false;
        } else {
            this.mostrarConsultar = true;
            this.mostrarEditar = true;
            this.mostrarEliminar = true;
            this.mostrarCrear = true;
        }*/
        if(estado==true){
            return "SI";
        }else{
            return "NO";
        }

    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar usuarios"));
        }
    }
}
