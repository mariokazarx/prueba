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
import com.tesis.entity.Recurso;
import com.tesis.entity.Role;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvPerfinUsuario {
    private Usuario usr;
    private boolean login;
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
    @PostConstruct
    public void inicioPagina() {
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
        }
    }
}
