/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package com.tesis.clases;

import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import com.tesis.managedbeans.mbsLogin;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mario Jurado
 */
public class CheckSession implements PhaseListener {
    private static final long serialVersionUID = -2713924006104518931L;

    private Usuario usr;
    private boolean login;
    private Profesor profesor;
    private final String PATH_LOGIN = "faces/index.xhtml";
    private final String PATH_RECUPERAR = "faces/recuperarContrasenia.xhtml";
    private final String PATH_RESTABLECER = "faces/restablecerContrasenia.xhtml";
    final List<String> urlsProfesor = Arrays.asList("faces/academico/estudiantes.xhtml", "faces/academico/matriculas.xhtml",
            "faces/academico/newestudiante.xhtml", "faces/anlectivo/anlectivos.xhtml", "faces/anlectivo/cursos.xhtml",
            "faces/anlectivo/newAnlectivo.xhtml", "faces/anlectivo/newPeriodo.xhtml", "faces/academico/newcurso.xhtml",
            "faces/anlectivo/periodos.xhtml", "faces/configuracion/areas.xhtml", "faces/configuracion/asignaturas.xhtml",
            "faces/configuracion/ciclo.xhtml", "faces/configuracion/configuracion.xhtml", "faces/configuracion/criterioevaluacion.xhtml",
            "faces/configuracion/escalas.xhtml", "faces/configuracion/materiasciclos.xhtml", "faces/configuracion/newarea.xhtml",
            "faces/configuracion/newasignatura.xhtml", "faces/configuracion/newciclo.xhtml", "faces/configuracion/newconf.xhtml",
            "faces/configuracion/newcriterioeval.xhtml", "faces/configuracion/newescala.xhtml", "faces/profesores/cargaAcademica.xhtml",
            "faces/profesores/newprofesor.xhtml", "faces/profesores/profesores.xhtml", "faces/profesores/rectificarNotas.xhtml",
            "faces/reportes/boletinPeriodo.xhtml", "faces/reportes/boletinesPeriodoAñosAnteriores.xhtml", "faces/reportes/certificadoMatricula.xhtml",
            "faces/reportes/consolidadoPeriodo.xhtml", "faces/reportes/consolidadoPeriodoAñosAnteriores.xhtml", "faces/reportes/consolidadofinal.xhtml",
            "faces/reportes/consolidadofinalAnterior.xhtml", "faces/reportes/constanciaEstudios.xhtml", "faces/reportes/estadisticas.xhtml",
            "faces/reportes/reporteAsignacionAcademica.xhtml", "faces/reportes/reporteAsignacionAñosAnteriores.xhtml", "faces/reportes/reporteMatriculaAñosAnteriores.xhtml",
            "faces/reportes/reporteMatriculas.xhtml", "faces/sistema/newusuario.xhtml", "faces/sistema/perfilUsuario.xhtml", "faces/sistema/usuarios.xhtml");

    @Override
    public void afterPhase(PhaseEvent event) {
        //System.out.println("AfterPhase: " + event.getPhaseId());
        this.usr = new Usuario();
        this.profesor = new Profesor();
        login = false;
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            login = mbslogin.isLogin();
            //System.out.println("usuario"+usr.getNombres()+"Login"+login);

        } catch (Exception e) {
            System.out.println(e.toString());
            //usr = null;
        }
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            profesor = mbslogin.getProfesor();
            login = mbslogin.isLogin();
            //System.out.println(mbslogin.getProfesor().getNombre());
        } catch (Exception e) {
            //System.out.println(e.toString());
            //profesor = null;
        }

        ExternalContext ec = (ExternalContext) event.getFacesContext().getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();
        String url = req.getRequestURL().toString();
        boolean enLogin = url.indexOf(PATH_LOGIN) != -1;
        boolean enRecuperar = url.indexOf(PATH_RECUPERAR) != -1;
        boolean enRestablecer = url.indexOf(PATH_RESTABLECER) != -1;
        //boolean enLogin2 = url.indexOf("/index.html") != -1;

        //System.out.println("LOGIN PROFE " + profesor + "booolean 1 "+enLogin+"boolean 2 "+enRecuperar+"boolean 3"+enRestablecer);
        //si no esta logeado y no se encuentre en login
        if (profesor.getProfesorId() != null) {
            for (String urlAux : urlsProfesor) {
                boolean enOtra = url.indexOf(urlAux) != -1;
                //System.out.println("LOGIN PROFE " + enOtra);
                if (enOtra) {
                    redirect("/plantilla.xhtml");
                    break;
                }
            }

        }
        if (!login && !enLogin) {
            if(enRecuperar || enRestablecer){
                //redirect("/recuperarContraseña.xhtml");
            }else{
                redirect("/index.xhtml");
            }
        } else if (login && (enLogin || enRecuperar)) {
            redirect("/plantilla.xhtml");
        }
        /*if(login){
            
         }else{
         //NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
         //nh.handleNavigation(facesContext, null, "index.xhtml"); 
         }*/
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        //System.out.println("BeforPhase: " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public static void redirect(String url) {
        ServletContext servContx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = (String) servContx.getContextPath();

        if (url.equals("/")) {
            url = path + url;
        } else {
            url = path + "/faces" + url;
        }

        redirectUrl(url);

    }

    public static void redirectUrl(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
