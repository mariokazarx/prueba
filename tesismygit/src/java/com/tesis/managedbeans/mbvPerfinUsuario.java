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
import com.tesis.entity.Recurso;
import com.tesis.entity.Role;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvPerfinUsuario {

    private Usuario usuario;
    private boolean login;
    private boolean cambiarContraseña;
    private String txtRepiteContrasenia;
    private List<Role> rolesRecursos;
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

    public mbvPerfinUsuario() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public List<Role> getRolesRecursos() {
        return rolesRecursos;
    }

    public void setRolesRecursos(List<Role> rolesRecursos) {
        this.rolesRecursos = rolesRecursos;
    }

    public boolean isCambiarContraseña() {
        return cambiarContraseña;
    }

    public void setCambiarContraseña(boolean cambiarContraseña) {
        this.cambiarContraseña = cambiarContraseña;
    }

    public String getTxtRepiteContrasenia() {
        return txtRepiteContrasenia;
    }

    public void setTxtRepiteContrasenia(String txtRepiteContrasenia) {
        this.txtRepiteContrasenia = txtRepiteContrasenia;
    }

    @PostConstruct
    public void inicioPagina() {
        this.cambiarContraseña = false;
        this.txtRepiteContrasenia = "";
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usuario = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
            System.out.println("usuario" + usuario.getNombres() + "Login" + login);
        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
        }
        this.rolesRecursos = new ArrayList<Role>();
        for (UsuarioRole usrRolAux : userroleEjb.getByUser(usuario)) {
            this.rolesRecursos.add(usrRolAux.getRoleId());
        }
    }

    public void activarContraseña() {
        if (cambiarContraseña) {
        } else {
        }
    }

    public void actualizar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            } else {
                if (cambiarContraseña) {
                    if (this.usuario.getContraseña().equals(this.txtRepiteContrasenia)) {
                        usuario.setContraseña(Encrypt.sha512(this.usuario.getContraseña(),this.usuario.getCorreo()));
                    } else {
                        FacesContext.getCurrentInstance().addMessage("frmPerfilUsuario:txtContrasenia", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Las contraseñas no coinciden"));
                        return;
                    }
                }
                usuarioEjb.edit(this.usuario);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados exitosamente", ""));
                inicioPagina();
            }
        } catch (Exception e) {
        }
    }
}
