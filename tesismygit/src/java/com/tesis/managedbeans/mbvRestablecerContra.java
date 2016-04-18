/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.CodigoProfesorFacade;
import com.tesis.beans.CodigoUsuarioFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.UsuarioFacade;
import com.tesis.clases.Encrypt;
import com.tesis.entity.CodigoProfesor;
import com.tesis.entity.CodigoUsuario;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvRestablecerContra implements Serializable{
    private static final long serialVersionUID = -1457878217524934224L;
    private String txtContrasenia;
    private String txtCodigo;
    private String txtRepita;
    private Usuario usuario;
    private Profesor profesor;
    @EJB
    private UsuarioFacade usuarioEjb;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private CodigoUsuarioFacade codigoUsuarioEjb;
    @EJB
    private CodigoProfesorFacade codigoProfesorEjb;
    
    public mbvRestablecerContra() {
    }

    public String getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(String txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public String getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(String txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public String getTxtRepita() {
        return txtRepita;
    }

    public void setTxtRepita(String txtRepita) {
        this.txtRepita = txtRepita;
    }
    @PostConstruct
    public void inicio() {
        this.usuario = new Usuario();
        this.profesor = new Profesor();
    }
    public void restablecerContrasenia(){
        try {
            CodigoUsuario codUsu = new CodigoUsuario();
            codUsu = codigoUsuarioEjb.getValido(Encrypt.code(txtCodigo));
            if(codUsu!=null){
                usuario = usuarioEjb.find(codUsu.getUsuarioId().getUsuarioId());
                usuario.setContraseña(Encrypt.sha512(this.txtContrasenia,this.usuario.getCorreo()));
                usuarioEjb.edit(usuario);
                codUsu.setEstado(2);
                codigoUsuarioEjb.edit(codUsu);
            }else{
                CodigoProfesor codProfe = new CodigoProfesor();
                codProfe = codigoProfesorEjb.getValido(Encrypt.code(txtCodigo));
                if(codProfe!=null){
                  profesor = profesorEjb.find(codProfe.getProfesorId().getProfesorId());
                  profesor.setContraseña(Encrypt.sha512(txtContrasenia,this.profesor.getCorreo()));
                  profesorEjb.edit(profesor);
                  codProfe.setEstado(2);
                  codigoProfesorEjb.edit(codProfe);
                }else{
                    //no encontro codigo
                }
            }
        } catch (Exception e) {
        }
    }
}
