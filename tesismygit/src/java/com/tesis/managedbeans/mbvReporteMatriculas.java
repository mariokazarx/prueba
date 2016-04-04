/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.MatriculaReporte;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteMatriculas implements Serializable {

    private List<Curso> cursos;
    private List<Curso> cursosReporte;
    private Curso cursoSelected;
    private List<MatriculaReporte> reporte;
    private String todos = "";
    private boolean mostrarPrincipal;
    private boolean mostrarBoton;
    private boolean mostrarCursos;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private EstadoMatriculaFacade estadomatriculaEjB;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    private JasperPrint jasperPrint;

    /**
     * Creates a new instance of mbvReporteMatriculas
     */
    public mbvReporteMatriculas() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Curso getCursoSelected() {
        return cursoSelected;
    }

    public void setCursoSelected(Curso cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    @PostConstruct()
    public void inicio() {
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
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 18) {
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
        this.mostrarCursos = false;
        this.mostrarCursos = false;
        this.cursos = new ArrayList<Curso>();
        this.cursosReporte = new ArrayList<Curso>();
        this.reporte = new ArrayList<MatriculaReporte>();
        this.cursoSelected = new Curso();
        this.cursos.clear();
        Anlectivo auxEscolar = aEscolarEjb.getIniciado();
        if (auxEscolar != null) {

            //hay año iniciado
            if (auxEscolar.getCursoList().isEmpty()) {
                //no hay cursos activos
                this.mostrarPrincipal = false;
            } else {
                this.mostrarPrincipal = true;
                this.cursosReporte = cursoEjb.getCursosByAño(auxEscolar);
                this.cursos = cursoEjb.getCursosByAño(auxEscolar);
            }
        } else {
            this.mostrarPrincipal = false;
        }
    }

    public void generar() {
        try {
            System.out.println("curso" + cursoSelected);
            if (cursoSelected != null && this.todos.compareTo("curso") == 0) {
                cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                this.cursosReporte.add(cursoSelected);
            }
            reporte.clear();
            for (Curso cur : this.cursosReporte) {
                List<Estudiante> est = new ArrayList<Estudiante>();
                MatriculaReporte mtrReporte = new MatriculaReporte();
                mtrReporte.setAño(aEscolarEjb.getIniciado().getAnio());
                mtrReporte.setCurso(cur.getNombre());
                mtrReporte.setNumero(cur.getCicloId().getNumero());
                for (Matricula matriculasCurso : matriculaEjb.matriculasCurso(cur)) {
                    System.out.println("entro for");
                    est.add(matriculasCurso.getEstudianteId());
                }
                mtrReporte.setEstudiantes(est);
                reporte.add(mtrReporte);
            }
            init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ooooOOOO" + e.toString());
        }
    }

    public void init() throws JRException {
        System.out.println("entro init");
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Cursos.jasper");
        /*Map<String, Object> parametros = new HashMap<String, Object>();
         parametros.put("ciclo", "3");
         parametros.put("año", "2016");
         parametros.put("curso", "3-1"); */
        jasperPrint = JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }

    public void pdf() throws JRException, IOException {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            generar();
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            httpServletResponse.reset();
            Faces.sendFile(JasperExportManager.exportReportToPdf(jasperPrint), "reporteMatricula.pdf", false);
            FacesContext.getCurrentInstance().responseComplete();
            
        } else {
            System.out.print("error permiso denegado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }

    }

    public void cargarCurso() {
        System.out.println("todos" + this.todos);
        if (this.todos.compareTo("todos") == 0) {
            this.mostrarBoton = true;
            this.mostrarCursos = false;
            Anlectivo auxEscolar = aEscolarEjb.getIniciado();
            this.cursosReporte = cursoEjb.getCursosByAño(auxEscolar);
            this.cursos = cursoEjb.getCursosByAño(auxEscolar);
            this.cursoSelected = new Curso();
        }
        if (this.todos.compareTo("curso") == 0) {
            this.mostrarCursos = true;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
            this.cursosReporte.clear();
        }
    }

    public void initRender() {
        Anlectivo auxEscolar = aEscolarEjb.getIniciado();
        if (auxEscolar != null) {
            if (auxEscolar.getCursoList().isEmpty()) {
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            } else {
                if (!this.consultar) {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
                }
                this.mostrarPrincipal = true;
            }
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }

    public void seleccionCurso() {
        if (cursoSelected.getCursoId() != null) {
            this.mostrarBoton = true;
        } else {
            this.mostrarBoton = false;
        }
    }
}
