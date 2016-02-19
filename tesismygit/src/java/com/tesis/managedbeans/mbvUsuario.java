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
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvUsuario implements Serializable {

    private Usuario usuario;
    private Recurso recursoSelected;
    private Role roleActual;
    private List<Role> rolesRecursos;
    private List<Recurso> recursos;
    private List<Usuario> usuarios;
    private String txtRepiteContrasenia;
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
    @Resource
    UserTransaction tx;

    public mbvUsuario() {
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
         System.out.println("ENTRO INICIO");
        this.usuario = new Usuario();
        this.recursoSelected = new Recurso();
        this.usuarios = this.usuarioEjb.findAll();
        this.recursos = this.recursoEjb.findAll();
        this.roleActual = new Role();
        this.rolesRecursos = new ArrayList<Role>();
    }

    public void actualizar(){
        try {
            tx.begin();
            if (this.usuario.getContraseña().equals(this.txtRepiteContrasenia)) {
                usuario.setContraseña(Encrypt.sha512(this.usuario.getContraseña()));
                //usuario.getUsuarioRoleList().clear();
                //usuarioEjb.edit(this.usuario);
                List<UsuarioRole> auxUsrRoles = new ArrayList<UsuarioRole>(userroleEjb.getByUser(usuario));
                //auxUsrRoles = usuario.getUsuarioRoleList();
                if(!userroleEjb.getByUser(usuario).isEmpty()){
                    System.out.println("ERRORR GRAVE 13333"+usuario.getUsuarioRoleList()+"EL OTRO"+auxUsrRoles);
                    if(!userroleEjb.removeByUsuario(usuario)){
                        //tx.rollback();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden 1"));
                        return;
                    } 
                    usuario.getUsuarioRoleList().clear();
                    System.out.println("ERRORR GRAVE 13333"+userroleEjb.getByUser(usuario)+"EL O>TRO"+auxUsrRoles);
                    for(UsuarioRole usrRolAux:auxUsrRoles){
                        if(!roleEjb.removeById(usrRolAux.getRoleId().getRoleId())){
                            tx.rollback();
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden 2"));
                            return;
                        }
                    }
                }
                tx.commit();
                //borrar roles por rol id de recursos
                // borrar usuario roles
                tx.begin();
                System.out.println("MIERDAAAA"+userroleEjb.getByUser(usuario));
                if (rolesRecursos.size() > 0) {
                    for (Role aux : rolesRecursos) {
                        System.out.println("role lllllll"+aux.getRecursoId());
                        roleEjb.create(aux);
                        UsuarioRole usrRol = new UsuarioRole();
                        usrRol.setRoleId(aux);
                        usrRol.setUsuarioId(usuario);
                        System.out.println("role 22222"+usrRol.toString());
                        userroleEjb.create(usrRol);
                        System.out.println("MIERDAAAA 222212121211111"+userroleEjb.getByUser(usuario));
                    }
                    System.out.println("MIERDAAAA 22222"+userroleEjb.getByUser(usuario));
                    usuarioEjb.edit(this.usuario);
                    tx.commit();
                    //closeDialog();
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                    //tx.rollback();    
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').hide()");
                    inicioPagina();
                } else {
                    tx.rollback();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                    return;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                tx.rollback();
                return;
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        }

    }
    public void prueba(){
        System.out.println("ENTRO PRUEB A");
        List<UsuarioRole> auxUsrRoles = new ArrayList<UsuarioRole>(userroleEjb.getByUser(usuario));
        if(!userroleEjb.removeByUsuario(usuario)){
            System.out.println("no pudo borrar");
        }else{
            System.out.println("pudo borrar");
            for(UsuarioRole usrRolAux:auxUsrRoles){
                if(!roleEjb.removeById(usrRolAux.getRoleId().getRoleId())){
                    System.out.println("no pudo borrar KKKKK");
                }
                else{
                    System.out.println("pudo borrar LLLLL");
                }
            }
        }
    }
    public void insertar() {
        try {
            tx.begin();
            if (this.usuario.getContraseña().equals(this.txtRepiteContrasenia)) {
                EstadoUsuario estado = estUsuEjb.find(1);
                TipoUsuario tusu = tusuEjb.find(3);
                usuario.setEstadoUsuarioId(estado);
                usuario.setTipoUsuarioId(tusu);
                usuario.setContraseña(Encrypt.sha512(this.usuario.getContraseña()));
                System.out.println("USUARIO"+usuario.getIdentificacion());
                usuarioEjb.create(this.usuario);
                System.out.println("USUARIO"+usuario);
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
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                    //tx.rollback();    
                    inicioPagina();
                } else {
                    tx.rollback();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                    return;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                tx.rollback();
                return;
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        }

    }

    public void newUsuario() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentHeight", 600);
        options.put("contentWidth", 800);
        options.put("height", 600);
        options.put("width", 800);
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newusuario", options, null);
    }

    public void checkPermiso() {
        if (recursoSelected != null) {
            if (roleActual.getAgregar() == false && roleActual.getConsultar() == false && roleActual.getEditar() == false) {
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
                System.out.println("ALGUN  CHECK" + roleActual.getRecursoId() + " ARREGLO " + rolesRecursos);
                //return;
            }
        }
    }

    public void cargarPermiso() {
        if (rolesRecursos.size() > 0) {
            boolean encontro = false;
            System.out.println("ROLES" + rolesRecursos.size());
            for (Role aux : rolesRecursos) {
                System.out.println("RECURSO SELECTED" + recursoSelected + " AUX " + aux.getRecursoId());
                if (aux.getRecursoId().equals(recursoSelected)) {
                    System.out.println("ENTRO 1");
                    roleActual = aux;
                    encontro = true;
                    return;
                }
            }
            if (!encontro) {
                System.out.println("ENTRO 2");
                roleActual = new Role();
            }
        } else {
             System.out.println("ENTRO 3");
            roleActual = new Role();
        }
    }
    public void cargarUsuario(int usuarioId) {
        try {
            this.usuario = this.usuarioEjb.find(usuarioId);
            this.rolesRecursos.clear();
            //if(!usuario.getUsuarioRoleList().isEmpty()){
            //    System.out.println("FFGFGHG"+this.usuario.getUsuarioRoleList());
            //}
            //usuarioEjb.notifyAll();
            for(UsuarioRole usrRolAux:userroleEjb.getByUser(usuario)){
                this.rolesRecursos.add(usrRolAux.getRoleId());
            }
            this.cargarPermiso();
            //this.nomoriginal = this.escala.getNombre();
            RequestContext.getCurrentInstance().update("frmEditarUsuario:panelEditarUsuario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala Registrada", "exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
