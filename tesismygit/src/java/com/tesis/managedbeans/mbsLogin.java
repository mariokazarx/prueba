/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.UsuarioFacade;
import com.tesis.clases.Encrypt;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@SessionScoped
public class mbsLogin implements Serializable {

    private String txtCorreo;
    private String clave;
    private String tipoUsuario;
    private boolean login;
    private boolean loginProfesor;
    private boolean loginUsuario;
    private Usuario usuario;
    private Profesor profesor;
    @EJB
    private UsuarioFacade usuarioEjb;
    @EJB
    private ProfesorFacade profesorEjb;

    public mbsLogin() {
    }

    public String getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(String txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public boolean isLoginProfesor() {
        return loginProfesor;
    }

    public void setLoginProfesor(boolean loginProfesor) {
        this.loginProfesor = loginProfesor;
    }

    public boolean isLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(boolean loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    @PostConstruct
    public void inicio() {
        this.loginProfesor = false;
        this.loginUsuario = false;
        this.login = false;
        this.usuario = new Usuario();
        this.profesor = new Profesor();
    }

    public void confirmar() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.tipoUsuario.equals("administrativo")) {
            Usuario auxUsu = new Usuario();
            auxUsu = usuarioEjb.getByCorreo(txtCorreo);
            if (auxUsu != null) {
                if (Encrypt.sha512(clave).equals(auxUsu.getContraseña())) {
                    //bien
                    System.out.println("bien");
                    this.login = true;
                    this.loginUsuario = true;
                    this.usuario = auxUsu;
                    context.getExternalContext().redirect("plantilla.xhtml");
                } else {
                    //clave mal
                    System.out.println("clave mal");
                    return;
                }
            } else {
                //usuario no existe
                System.out.println("usuario no existe");
                return;
            }
        } else {
            if (this.tipoUsuario.equals("profesor")) {
                Profesor auxProf = new Profesor();
                auxProf = profesorEjb.getByCorreo(txtCorreo);
                if (auxProf != null) {
                    if (Encrypt.sha512(clave).equals(auxProf.getContraseña())) {
                        System.out.println("bien");
                        this.login = true;
                        this.loginProfesor = true;
                        this.profesor = auxProf;
                        context.getExternalContext().redirect("plantilla.xhtml");
                        //bien
                    } else {
                        //clave mal
                        System.out.println("clave mal");
                        return;
                    }
                } else {
                    //usuario no existe
                    System.out.println("usuario no existe");
                    return;
                }
            } else {
                //no existe el tipo de usurario
                return;
            }
        }
    }

    public void cerrarSession() throws IOException {
        ServletContext servContx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String url = (String) servContx.getContextPath();
        this.login = false;
        this.profesor = new Profesor();
        this.usuario = new Usuario();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().getSession(login);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/faces/index.xhtml");
    }
    public boolean login(){
        return false;
    }
}
