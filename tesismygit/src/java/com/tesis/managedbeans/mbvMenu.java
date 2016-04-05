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
    private static final long serialVersionUID = 9151197840461324706L;

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
        aEscolar = new Anlectivo();
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
            DefaultSubMenu firstSubmenu = new DefaultSubMenu("Año escolar Vigente");

            DefaultMenuItem item = new DefaultMenuItem("Reporte de Matriculas");
            item.setUrl("/faces/reportes/reporteMatriculas.xhtml");
            item.setIcon("ui-icon-document");
            firstSubmenu.addElement(item);

            DefaultMenuItem item2 = new DefaultMenuItem("Reporte Asignacion academica");
            item2.setUrl("/faces/reportes/reporteAsignacionAcademica.xhtml");
            item2.setIcon("ui-icon-document");
            firstSubmenu.addElement(item2);
            
            DefaultMenuItem item3 = new DefaultMenuItem("Consolidado por Periodo");
            item3.setUrl("/faces/reportes/consolidadoPeriodo.xhtml");
            item3.setIcon("ui-icon-document");
            firstSubmenu.addElement(item3);
            
            DefaultMenuItem item4 = new DefaultMenuItem("Consolidado Cierre de año");
            item4.setUrl("/faces/reportes/consolidadofinal.xhtml");
            item4.setIcon("ui-icon-document");
            firstSubmenu.addElement(item4);
            
            DefaultMenuItem item5 = new DefaultMenuItem("Boletines");
            item5.setUrl("/faces/reportes/boletinPeriodo.xhtml");
            item5.setIcon("ui-icon-document");
            firstSubmenu.addElement(item5);
            
            model.addElement(firstSubmenu);

            //Second submenu
            DefaultSubMenu secondSubmenu = new DefaultSubMenu("Años Escolares anteriores");

           /*if(aEscolar.getAnlectivoId()!=null){
                item = new DefaultMenuItem(aEscolar.getAnio());
                item.setIcon("ui-icon-disk");
                item.setCommand("#{menuView.save}");
                //item.setUpdate("messages");
                secondSubmenu.addElement(item);
            }
            item = new DefaultMenuItem("Delete");
            item.setIcon("ui-icon-close");
            item.setCommand("#{menuView.delete}");
            item.setAjax(false);
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Redirect");
            item.setIcon("ui-icon-search");
            item.setCommand("#{menuView.redirect}");
            secondSubmenu.addElement(item);

            model.addElement(secondSubmenu);*/
            DefaultMenuItem item6 = new DefaultMenuItem("Reporte de Matriculas");
            item6.setUrl("/faces/reportes/reporteMatriculaAñosAnteriores.xhtml");
            item6.setIcon("ui-icon-document");
            secondSubmenu.addElement(item6);

            DefaultMenuItem item7 = new DefaultMenuItem("Reporte Asignacion academica");
            item7.setUrl("/faces/reportes/reporteAsignacionAñosAnteriores.xhtml");
            item7.setIcon("ui-icon-document");
            secondSubmenu.addElement(item7);
           
            DefaultMenuItem item8 = new DefaultMenuItem("Consolidado por Periodo");
            item8.setUrl("/faces/reportes/consolidadoPeriodoAñosAnteriores.xhtml");
            item8.setIcon("ui-icon-document");
            secondSubmenu.addElement(item8);
            
            DefaultMenuItem item9 = new DefaultMenuItem("Consolidado Cierre de año");
            item9.setUrl("/faces/reportes/consolidadofinalAnterior.xhtml");
            item9.setIcon("ui-icon-document");
            secondSubmenu.addElement(item9);
            
            DefaultMenuItem item10 = new DefaultMenuItem("Boletines");
            item10.setUrl("/faces/reportes/boletinesPeriodoAñosAnteriores.xhtml");
            item10.setIcon("ui-icon-document");
            secondSubmenu.addElement(item10);
            model.addElement(secondSubmenu);
            
            DefaultSubMenu certificadosMenu = new DefaultSubMenu("Certificados");
            
            DefaultMenuItem item11 = new DefaultMenuItem("Constancia de estudios");
            item11.setUrl("/faces/reportes/constanciaEstudios.xhtml");
            item11.setIcon("ui-icon-document");
            certificadosMenu.addElement(item11);
            
            DefaultMenuItem item12 = new DefaultMenuItem("Certificado matricula");
            item12.setUrl("/faces/reportes/certificadoMatricula.xhtml");
            item12.setIcon("ui-icon-document");
            certificadosMenu.addElement(item12);
            model.addElement(certificadosMenu);
            
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

            DefaultMenuItem item3 = new DefaultMenuItem("Recuperaciones");
            item3.setUrl("/faces/academico/recuperacion.xhtml");
            item3.setIcon("ui-icon-home");
            firstSubmenu.addElement(item3);
            
            item3 = new DefaultMenuItem("Reportes");
            item3.setUrl("/faces/profesores/reportesNotas.xhtml");
            item3.setIcon("ui-icon-home");
            firstSubmenu.addElement(item3);
            
            item3 = new DefaultMenuItem("Listado de estudiantes");
            item3.setUrl("/faces/profesores/listaEstudiantes.xhtml");
            item3.setIcon("ui-icon-home");
            firstSubmenu.addElement(item3);
            
            model.addElement(firstSubmenu);

            //Second submenu
            DefaultSubMenu secondSubmenu = new DefaultSubMenu("Años escolares anteriores");

            item = new DefaultMenuItem(aEscolar.getAnio());
            item.setIcon("ui-icon-disk");
            item.setCommand("#{menuView.save}");
            //item.setUpdate("messages");
            secondSubmenu.addElement(item);

            item3 = new DefaultMenuItem("Reportes");
            item3.setUrl("/faces/profesores/reportes.xhtml");
            item3.setIcon("ui-icon-home");
            secondSubmenu.addElement(item3);

            item = new DefaultMenuItem("Redirect");
            item.setIcon("ui-icon-search");
            item.setCommand("#{menuView.redirect}");
            secondSubmenu.addElement(item);

            model.addElement(secondSubmenu);
            
            secondSubmenu = new DefaultSubMenu("Perfil");
            item3 = new DefaultMenuItem("Editar perfil");
            item3.setUrl("/faces/profesores/perfilProfesor.xhtml");
            item3.setIcon("ui-icon-home");
            secondSubmenu.addElement(item3);

            model.addElement(secondSubmenu);
        }
        
    }
}
