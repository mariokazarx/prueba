/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvMenu implements Serializable{

    private MenuModel model;
    private Anlectivo aEscolar;
    private Usuario usr;
    private boolean login;
    private Profesor profesor;
    
    @EJB
    private AnlectivoFacade aEscolarEjb;
    
    public mbvMenu() {
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    @PostConstruct
    public void inicioPagina() {
        this.usr = new Usuario();
        this.profesor = new Profesor();
        login=false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
            
        } catch (Exception e) {
            System.out.println(e.toString());
            //usr = null;
        }
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             profesor = mbslogin.getProfesor();
             login = mbslogin.isLogin();
            System.out.println(mbslogin.getProfesor().getNombre());
        } catch (Exception e) {
            System.out.println(e.toString());
            //profesor = null;
        }
        
        if(aEscolarEjb.getIniciado()!=null){
            aEscolar = aEscolarEjb.getIniciado();
        }
        model = new DefaultMenuModel();
        System.out.println("usuario MENU"+usr+"PROFESOR MENU "+profesor);
        if(usr.getUsuarioId()!=null){
            //esta logueaod como usuario
            //First submenu
            System.out.println("usuario MENU"+usr);
            DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");

            DefaultMenuItem item = new DefaultMenuItem("Año escolar Vigente");
            item.setUrl("/faces/reportes/reporteMatriculas.xhtml");
            item.setIcon("ui-icon-home");
            firstSubmenu.addElement(item);

            model.addElement(firstSubmenu);

            //Second submenu
            DefaultSubMenu secondSubmenu = new DefaultSubMenu("Año Escolar Vigente");

            item = new DefaultMenuItem(aEscolar.getAnio());
            item.setIcon("ui-icon-disk");
            item.setCommand("#{menuView.save}");
            //item.setUpdate("messages");
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Delete");
            item.setIcon("ui-icon-close");
            item.setCommand("#{menuView.delete}");
            item.setAjax(false);
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Redirect");
            item.setIcon("ui-icon-search");
            item.setCommand("#{menuView.redirect}");
            secondSubmenu.addElement(item);

            model.addElement(secondSubmenu);
        } 
        if(profesor.getProfesorId()!=null){
            //esta logueado como profesor
            //First submenu
            System.out.println("Profesor MENU"+profesor);
            DefaultSubMenu firstSubmenu = new DefaultSubMenu("Año escolar en curso");

            DefaultMenuItem item = new DefaultMenuItem("Evaluar");
            item.setUrl("/faces/academico/notas.xhtml");
            item.setIcon("ui-icon-home");
            firstSubmenu.addElement(item);

            DefaultMenuItem item2 = new DefaultMenuItem("Rectificar");
            item2.setUrl("/faces/academico/rectificar.xhtml");
            item2.setIcon("ui-icon-home");
            firstSubmenu.addElement(item2);

            model.addElement(firstSubmenu);

            //Second submenu
            DefaultSubMenu secondSubmenu = new DefaultSubMenu("Años escolares anteriores");

            item = new DefaultMenuItem(aEscolar.getAnio());
            item.setIcon("ui-icon-disk");
            item.setCommand("#{menuView.save}");
            //item.setUpdate("messages");
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Delete");
            item.setIcon("ui-icon-close");
            item.setCommand("#{menuView.delete}");
            item.setAjax(false);
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Redirect");
            item.setIcon("ui-icon-search");
            item.setCommand("#{menuView.redirect}");
            secondSubmenu.addElement(item);

            model.addElement(secondSubmenu);
        }
        
    }
}
