/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Periodo;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvResumenAsignacion implements Serializable {

    private static final long serialVersionUID = 1084423837094791069L;
    private List<Curso> cursos;
    private List<Asignaturaciclo> asgCiclos;
    private Curso cursoSelected;
    private boolean mostrarPeriodos;
    private boolean mostrarCursos;
    private List<Periodo> periodos;
    private Periodo periodoSelected;
    private Anlectivo aEscolar;
    private boolean mostrarPrincipal;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private CursoFacade cursoEjb;//
    @EJB
    private AsignaturacicloFacade asgCicloEjB;
    @EJB
    private PeriodoFacade periodoEJb;
    /*@EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEjB;
    @EJB
    private LogroFacade logroEjb;
    @EJB
    private LogronotaFacade logroNotaEjb;*/
    @EJB
    private ContenidotematicoFacade contenidoEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
  
    /**
     * Creates a new instance of mbvReporteMatriculas
     */
    public mbvResumenAsignacion() {
    }

    public List<Asignaturaciclo> getAsgCiclos() {
        return asgCiclos;
    }

    public boolean isConsultar() {
        return consultar;
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

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
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
        this.asgCiclos = new ArrayList<Asignaturaciclo>();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 4) {
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
        this.cursos = new ArrayList<Curso>();
        this.mostrarCursos = false;
        this.mostrarPeriodos = false;
        this.periodos = new ArrayList<Periodo>();
        this.cursoSelected = new Curso();
        this.periodoSelected = new Periodo();
        this.aEscolar = aEscolarEjb.getIniciado();
        if (aEscolar != null) {
            //hay año iniciado
            this.periodos = periodoEjb.getPeriodosByAnio(aEscolar);
            if (this.periodos.isEmpty()) {
                this.mostrarPrincipal = false;
                //no hay periodos
            } else {
                //this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
                this.mostrarPeriodos = true;
                this.mostrarPrincipal = true;
                //this.cursos = aEscolar.getCursoList();
            }
        } else {
            this.mostrarPrincipal = false;
        }
    }

    public void cargarPeriodo() {
        if (this.periodoSelected.getPeriodoId() != null) {
            this.periodoSelected = periodoEJb.find(periodoSelected.getPeriodoId());
            this.mostrarCursos = true;
            this.cursoSelected = new Curso();
            this.cursos = cursoEjb.getCursosByAño(aEscolar);//aEscolar.getCursoList();
            this.mostrarCursos = true;

        } else {
            this.mostrarCursos = false;
            this.cursoSelected = new Curso();
        }
    }

    public void seleccionCurso() {
        if (this.cursoSelected.getCursoId() != null) {
            cursoSelected = cursoEjb.find(this.cursoSelected.getCursoId());
            asgCiclos = asgCicloEjB.asignaturasCiclo(cursoSelected.getCicloId());
        } else {
            
        }
    }

    public String estado(Asignaturaciclo asg){
        if(contenidoEJb.tieneAsgPeriodo(cursoSelected, asg, periodoSelected)){
            return "Asignado";
        }else{
            return "Sin asignar";
        }
    }
    public void initRender() {
        this.aEscolar = aEscolarEjb.getIniciado();
        if (aEscolar != null) {
            if (cursoEjb.getCursosByAño(aEscolar).isEmpty()) {//aEscolar.getCursoList().isEmpty()
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            }
            if (periodoEJb.getPeriodosByAnio(aEscolar).isEmpty()) {//aEscolar.getPeriodoList().isEmpty()
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado periodos"));
            }
            if (!periodoEJb.getPeriodosByAnio(aEscolar).isEmpty() && !cursoEjb.getCursosByAño(aEscolar).isEmpty()) {//aEscolar.getPeriodoList().isEmpty() && !aEscolar.getCursoList().isEmpty()
                if (!this.consultar) {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
                }
                this.mostrarPeriodos = true;
                this.mostrarPrincipal = true;
                //this.cursos = aEscolar.getCursoList();
            }
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }
}
