/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.ReporteAsignacionAcademica;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Periodo;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteAsignacionAnteriores implements Serializable{
    private static final long serialVersionUID = 2626739799949039422L;

    private List<Curso> cursos;
    private List<Curso> cursosReporte;
    private Curso cursoSelected;
    private List<ReporteAsignacionAcademica> reporte;
    private boolean mostrarPeriodos;
    private boolean mostrarCursos;
    private String seleccion;
    private List<Periodo> periodos;
    private Periodo periodoSelected;
    private Anlectivo aEscolar;
    private boolean mostrarPrincipal;
    private boolean mostrarOpciones;
    private boolean mostrarBoton;
    private Anlectivo anlectivoSelected;
    private List<Anlectivo> anlectivos;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
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
    private PeriodoFacade periodoEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    private JasperPrint jasperPrint;

    public mbvReporteAsignacionAnteriores() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public Anlectivo getAnlectivoSelected() {
        return anlectivoSelected;
    }

    public void setAnlectivoSelected(Anlectivo anlectivoSelected) {
        this.anlectivoSelected = anlectivoSelected;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarOpciones() {
        return mostrarOpciones;
    }

    public void setMostrarOpciones(boolean mostrarOpciones) {
        this.mostrarOpciones = mostrarOpciones;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
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

    public boolean isMostrarPeriodos() {
        return mostrarPeriodos;
    }

    public void setMostrarPeriodos(boolean mostrarPeriodos) {
        this.mostrarPeriodos = mostrarPeriodos;
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public PeriodoFacade getPeriodoEJb() {
        return periodoEJb;
    }

    public void setPeriodoEJb(PeriodoFacade periodoEJb) {
        this.periodoEJb = periodoEJb;
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
        } catch (Exception e) {
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
        //verificar permisos
        //verificar año iniciado
        //verificar cursos
        this.seleccion = "";
        this.cursos = new ArrayList<Curso>();
        this.cursosReporte = new ArrayList<Curso>();
        this.anlectivos = new ArrayList<Anlectivo>();
        this.anlectivoSelected = new Anlectivo();
        this.mostrarCursos = false;
        this.mostrarPeriodos = false;
        this.mostrarBoton = false;
        this.mostrarOpciones = false;
        this.periodos = new ArrayList<Periodo>();
        this.reporte = new ArrayList<ReporteAsignacionAcademica>();
        this.cursoSelected = new Curso();
        this.periodoSelected = new Periodo();
        this.cursos.clear();
        this.anlectivos = aEscolarEjb.getTerminados();
        if (!anlectivos.isEmpty()) {
            this.mostrarPrincipal = true;
        } else {
            this.mostrarPrincipal = false;
        }
        //this.aEscolar = aEscolarEjb.getIniciado();
        /*if(aEscolar!=null){
         //hay año iniciado
         if(aEscolar.getPeriodoList().isEmpty()){
         this.mostrarPrincipal = false;
         //no hay periodos
         }else{
         this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
         this.mostrarPeriodos = true;
         this.mostrarPrincipal = true;
         //this.cursos = aEscolar.getCursoList();
         }
         }else{
         this.mostrarPrincipal = false;
         }*/
    }

    public void generar() {
        try {
            if (cursoSelected != null && this.seleccion.compareTo("curso") == 0) {
                cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                this.cursosReporte.add(cursoSelected);
            }
            reporte.clear();
            for (Curso curReporte : cursosReporte) {
                Periodo periodo = periodoSelected;
                List<Contenidotematico> con = new ArrayList<Contenidotematico>();
                ReporteAsignacionAcademica mtrReporte = new ReporteAsignacionAcademica();
                mtrReporte.setAño(aEscolar.getAnio());
                mtrReporte.setCurso(curReporte.getNombre());
                mtrReporte.setNumero(periodo.getNumero());
                for (Contenidotematico conenido : contenidoEjb.getByPeriodoCurso(periodo, curReporte)) {
                    con.add(conenido);
                }
                mtrReporte.setContenidos(con);
                reporte.add(mtrReporte);
            }
            init();

        } catch (Exception e) {
        }
    }

    public void init() throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/AsignacionAcademica.jasper");
        /*Map<String, Object> parametros = new HashMap<String, Object>();
         parametros.put("ciclo", "3");
         parametros.put("año", "2016");
         parametros.put("curso", "3-1"); */
        jasperPrint = JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }

    public void pdf() throws JRException, IOException {
        if (!login) {
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
        }else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void cargarPeriodo() {
        if (this.periodoSelected.getPeriodoId() != null) {
            this.periodoSelected = periodoEJb.find(periodoSelected.getPeriodoId());
            //this.cursos.clear();
            this.mostrarOpciones = true;
            this.mostrarCursos = false;
            this.cursoSelected = new Curso();
            if (this.seleccion.compareTo("todos") == 0) {
                this.mostrarBoton = true;
            } else {
                this.mostrarBoton = false;
            }
            if (this.seleccion.compareTo("curso") == 0) {
                this.cursoSelected = new Curso();
                this.cursosReporte.clear();
                this.cursos = cursoEjb.getCursosByAño(aEscolar);
                this.mostrarCursos = true;
                this.mostrarBoton = false;
            }
        } else {
            this.mostrarBoton = false;
            this.mostrarOpciones = false;
            this.mostrarCursos = false;
            this.cursoSelected = new Curso();
            this.cursos.clear();
        }
    }

    public void cargarCurso() {
        if (this.seleccion.compareTo("todos") == 0) {
            Anlectivo auxEscolar = aEscolarEjb.getIniciado();
            this.cursosReporte.clear();
            this.cursosReporte = cursoEjb.getCursosByAño(aEscolar);
            this.cursoSelected = new Curso();
            this.mostrarBoton = true;
            this.mostrarCursos = false;
        }
        if (this.seleccion.compareTo("curso") == 0) {
            this.cursoSelected = new Curso();
            this.cursosReporte.clear();
            this.cursos = cursoEjb.getCursosByAño(aEscolar);
            this.mostrarCursos = true;
            this.mostrarBoton = false;
        }
    }

    public void seleccionCurso() {
        if (this.cursoSelected.getCursoId() != null) {
            this.mostrarBoton = true;
        } else {
            this.mostrarBoton = false;
        }
    }

    public void initRender() {
        if (!anlectivos.isEmpty()) {
            if (!this.consultar) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
            }
            this.mostrarPrincipal = true;
            /*if(aEscolarEjb.getIniciado().getCursoList().isEmpty()){
             this.mostrarPrincipal = false;
             FacesContext.getCurrentInstance().
             addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
             }else{
             this.mostrarPrincipal = true;
             }*/
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }

    public void cargarAño() {
        if (anlectivoSelected.getAnlectivoId() != null) {
            this.aEscolar = aEscolarEjb.find(anlectivoSelected.getAnlectivoId());
            this.mostrarPeriodos = true;
            this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
            this.mostrarCursos = false;
            this.mostrarOpciones = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
            this.periodoSelected = new Periodo();
        } else {
            this.mostrarPeriodos = false;
            this.mostrarCursos = false;
            this.mostrarOpciones = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
        }
    }
}
