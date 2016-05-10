/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCargaAcademica implements Serializable {

    private static final long serialVersionUID = -5294774578512452156L;
    private Profesor profesor;
    private String mensajeError;
    private boolean contenidoError;
    private List<Profesor> profesores;
    private Curso cursoSelected;
    private List<Curso> cursos;
    private List<Periodo> periodos;
    private List<Asignatura> asSelecteds;
    private List<Asignatura> asignturasdisponibles;
    private List<Asignatura> asignturasSelecionadas;
    private DualListModel<Asignatura> pickList;
    private List<Asignaturaciclo> asignaturasprofes;
    private boolean banderaAsig = false;
    private boolean banderaSearch = false;
    private boolean isPeriodo = false;
    private boolean mostrarSeleccion;
    private boolean mostrarCursos;
    private Contenidotematico contenido;
    private List<Contenidotematico> contenidosProfesor;
    private String mensaje;
    private Periodo periodoSelected;
    private Anlectivo aEscolarP;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AsignaturacicloFacade asignaturaCicloEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private EstadocontenidotematicoFacade estadocontenidoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @Resource
    UserTransaction tx;

    public mbvCargaAcademica() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isEditar() {
        return editar;
    }

    public List<Contenidotematico> getContenidosProfesor() {
        return contenidosProfesor;
    }

    public boolean isMostrarSeleccion() {
        return mostrarSeleccion;
    }

    public void setMostrarSeleccion(boolean mostrarSeleccion) {
        this.mostrarSeleccion = mostrarSeleccion;
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public boolean isIsPeriodo() {
        return isPeriodo;
    }

    public void setIsPeriodo(boolean isPeriodo) {
        this.isPeriodo = isPeriodo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String menajeError) {
        this.mensajeError = menajeError;
    }

    public boolean isContenidoError() {
        return contenidoError;
    }

    public void setContenidoError(boolean contenidoError) {
        this.contenidoError = contenidoError;
    }

    public boolean isBanderaSearch() {
        return banderaSearch;
    }

    public void setBanderaSearch(boolean banderaSearch) {
        this.banderaSearch = banderaSearch;
    }

    public boolean isBanderaAsig() {
        return banderaAsig;
    }

    public void setBanderaAsig(boolean banderaAsig) {
        this.banderaAsig = banderaAsig;
    }

    public DualListModel<Asignatura> getPickList() {
        return pickList;
    }

    public void setPickList(DualListModel<Asignatura> pickList) {
        this.pickList = pickList;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;

    }

    public Curso getCursoSelected() {
        return cursoSelected;
    }

    public void setCursoSelected(Curso cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @PostConstruct
    public void inicioPagina() {
        this.contenidosProfesor = new ArrayList<Contenidotematico>();
        this.contenidoError = false;
        this.banderaAsig = false;
        this.banderaSearch = false;
        this.mostrarCursos = true;
        this.mensaje = "";
        this.cursos = new ArrayList<Curso>();
        this.periodos = new ArrayList<Periodo>();
        this.aEscolarP = new Anlectivo();
        this.periodoSelected = new Periodo();
        Profesor aux = (Profesor) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("param1");
        profesores = this.profesorEjb.findAll();
        //this.cursos = this.cursoEjb.findAll();
        this.cursoSelected = new Curso();
        this.contenido = new Contenidotematico();
        asSelecteds = new ArrayList<Asignatura>();
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
        cargarDatos(aux);

    }

    public List<Profesor> completeProfesor(String query) {

        List<Profesor> allThemes = profesorEjb.findAll();
        List<Profesor> filteredThemes = new ArrayList<Profesor>();
        for (int i = 0; i < allThemes.size(); i++) {
            Profesor skin = allThemes.get(i);
            if (skin.getCedula().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }

        return filteredThemes;
    }

    public void prueba() {
        try {
            if (this.profesor != null) {
                //cursoSelected = cursoEjb.find(1);
                this.cargarDatos(profesor);
            } else {
                this.banderaSearch = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Advertencia", "Profesor no encontrado"));
            }

        } catch (Exception e) {
            this.banderaAsig = true;
        }

    }

    public void cargarPickList() {
        try {
            if (cursoSelected.getCursoId() == null) {
                this.banderaAsig = false;
            } else {
                if (isPeriodo) {
                    this.banderaAsig = true;
                    asignturasdisponibles = new ArrayList<Asignatura>();
                    asignturasSelecionadas = new ArrayList<Asignatura>();
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                    //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                    Periodo per = periodoEjb.getPeriodoMinByAnio(an);
                    asignaturasprofes = asignaturaCicloEjb.asignaturasDisponiblesPeriodo(profesor, cursoSelected, periodoSelected);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasdisponibles.add(as);
                    }
                    asignaturasprofes = asignaturaCicloEjb.asignaturasProfesorPeriodo(profesor, cursoSelected, periodoSelected);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasSelecionadas.add(as);
                    }
                    this.pickList = new DualListModel<Asignatura>(asignturasdisponibles, asignturasSelecionadas);
                } else {
                    this.banderaAsig = true;
                    asignturasdisponibles = new ArrayList<Asignatura>();
                    asignturasSelecionadas = new ArrayList<Asignatura>();
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                    //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                    Periodo per = periodoEjb.getPeriodoMinByAnio(an);
                    asignaturasprofes = asignaturaCicloEjb.asignaturasDisponibles(profesor, cursoSelected);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasdisponibles.add(as);
                    }
                    asignaturasprofes = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected, per);
                    for (Asignaturaciclo asc : asignaturasprofes) {
                        Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                        asignturasSelecionadas.add(as);
                    }
                    this.pickList = new DualListModel<Asignatura>(asignturasdisponibles, asignturasSelecionadas);
                }
            }

        } catch (Exception e) {
            this.banderaAsig = false;
        }
    }

    public void onTransfer(TransferEvent event) {
        try {
            for (Object item : event.getItems()) {
                //builder.append(((Theme) item).getName()).append("<br />");
                if (event.isRemove()) {
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString()));
                    asSelecteds.remove(asg);
                    asignturasSelecionadas.remove(asg);
                    asignturasdisponibles.add(asg);
                }
                if (event.isAdd()) {
                    Asignatura asg = asignaturaEjb.find(Integer.parseInt(item.toString()));
                    asSelecteds.add(asg);
                    asignturasSelecionadas.add(asg);
                    asignturasdisponibles.remove(asg);
                }
            }
        } catch (Exception e) {
        }
        //StringBuilder builder = new StringBuilder();


    }

    public void cargarMaterias() throws IllegalStateException, SecurityException, SystemException {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.editar) {
                tx.begin();
                if (isPeriodo) {
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    for (int i = 0; i < asignturasdisponibles.size(); i++) {
                        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cursoSelected.getCicloId(), asignturasdisponibles.get(i));
                        Contenidotematico conAux = contenidoEjb.getContenidoByAll(profesor, cursoSelected, asg, periodoSelected);
                        // advertencia
                        Estadocontenidotematico estAux = estadocontenidoEjb.find(5);
                        conAux.setEstado(estAux);
                        contenidoEjb.edit(conAux);
                    }
                    for (int i = 0; i < asignturasSelecionadas.size(); i++) {
                        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cursoSelected.getCicloId(), asignturasSelecionadas.get(i));
                        Contenidotematico conAux = contenidoEjb.getContenidoByCambio(cursoSelected, asg, periodoSelected);
                        // advertencia
                        Estadocontenidotematico estAux;
                        if (periodoSelected.getEstadoPeriodoId().getEstadoPeriodoId() == 1) {
                            estAux = estadocontenidoEjb.find(1);
                        } else {
                            estAux = estadocontenidoEjb.find(2);
                        }
                        conAux.setProfesorId(profesor);;
                        conAux.setEstado(estAux);
                        contenidoEjb.edit(conAux);
                    }
                    //return;
                } else {
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    Anlectivo an = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
                    //Configuracion cf = anlectivoEjb.getConfiguracionCurso(cursoSelected);
                    List<Periodo> periodos = periodoEjb.getPeriodosByAnio(an);
                    Estadocontenidotematico est = estadocontenidoEjb.find(1);
                    Curso cur = cursoEjb.find(cursoSelected.getCursoId());
                    for (int i = 0; i < asignturasSelecionadas.size(); i++) {
                        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignturasSelecionadas.get(i));
                        if (!contenidoEjb.tieneAsg(cur, asg)) {
                            for (Periodo aux : periodos) {
                                Contenidotematico con = new Contenidotematico();
                                con.setCursoId(cursoSelected);
                                con.setPeriodoId(aux);
                                con.setProfesorId(profesor);
                                con.setAsignaturacicloId(asg);
                                con.setEstado(est);
                                contenidoEjb.create(con);
                            }
                        }
                    }
                    for (int i = 0; i < asignturasdisponibles.size(); i++) {
                        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cursoSelected.getCicloId(), asignturasdisponibles.get(i));
                        if (contenidoEjb.tieneAsg(cur, asg)) {
                            for (Periodo aux : periodos) {
                                if (!contenidoEjb.removeByAll(cur, asg, aux)) {
                                    tx.rollback();
                                    FacesContext.getCurrentInstance().
                                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "esta asignatura esta en uso debe reajustar manualmente por cada periodo"));
                                    return;
                                }
                            }
                        }
                    }
                }
                tx.commit();
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La operación se realizó satisfactoriamente"));
                cargarPickList();
                this.contenidosProfesor = contenidoEjb.getByProfesorAño(profesor, aEscolarP);
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public void mensajeAdvertencia() {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion academica", "Exitosa"));
        //inicioPagina();
    }

    private void cargarDatos(Profesor aux) {
        if (aux != null) {
            if (!this.editar) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
            this.profesor = aux;
            if (profesor.getEstadoProfesorId().getEstadoProfesorId() == 1) {
                //profesor activo
                Anlectivo anescolar = new Anlectivo();
                Anlectivo auxEscolar = anlectivoEjb.getIniciado();
                if (auxEscolar != null) {
                    aEscolarP = auxEscolar;
                    this.contenidosProfesor = contenidoEjb.getByProfesorAño(profesor, auxEscolar);
                    //hay año iniciado
                    if (cursoEjb.getCursosByAño(auxEscolar).isEmpty()) {//auxEscolar.getCursoList().isEmpty()
                        //no hay cursos activos
                        this.banderaSearch = true;
                        this.mostrarSeleccion = false;
                        this.mostrarCursos = false;
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "aun no hay cursos para realizar la asignacion academica"));
                    } else {
                        this.cursos = cursoEjb.getCursosByAño(auxEscolar);//auxEscolar.getCursoList();
                        this.banderaAsig = false;
                        this.isPeriodo = false;
                        this.mostrarSeleccion = true;
                        this.banderaSearch = true;
                        this.periodoSelected = new Periodo();
                        this.cursoSelected = new Curso();
                    }
                    /*List<Anlectivo> auxAnlectivos;
                     auxAnlectivos = anlectivoEjb.findAll();
                     for(Anlectivo auxan : auxAnlectivos){
                     if(auxan.getEstadoAniolectivoId().getEstadoAniolectivoId()==2){
                     anescolar = anlectivoEjb.find(auxan.getAnlectivoId());
                     break;
                     }
                     }
                     if(anescolar!=null){
                     //si hay año escolar
                     if(anescolar.getCursoList().isEmpty()){
                     // no hay cursos para ese año
                     System.out.println("NO HAY CURSOS");
                     }
                     for(Curso cur:anescolar.getCursoList()){
                     System.out.println(cur);
                     }
                     }*/
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Este profesor se encuentra inactivo en el sistema"));
            }

            //profesor debe estar activo 
            //alño esdcolar iniciado 
            // al menos un curso para ese año
            //prueba();
        } else {
            this.profesor = new Profesor();
            this.mostrarSeleccion = false;
            /*FacesContext.getCurrentInstance().
             addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Advertencia", "Profesor no encontrado"));*/
        }

    }

    public void activarByPeriodo() {
        if (isPeriodo) {
            //esta habilitado ppor periodo
            this.mostrarCursos = false;
            this.banderaAsig = false;
            this.periodos = periodoEjb.getPeriodosByAnio(anlectivoEjb.getIniciado());
        } else {
            this.cursoSelected = new Curso();
            this.mostrarCursos = true;
            this.banderaAsig = false;
            this.periodoSelected = new Periodo();
            this.periodos.clear();
        }
    }

    public void cargarPeriodo() {
        if (periodoSelected.getPeriodoId() != null) {
            periodoSelected = periodoEjb.find(periodoSelected.getPeriodoId());
            if (periodoSelected.getEstadoPeriodoId().getEstadoPeriodoId() != 2) {
                mostrarCursos = true;
            } else {
                mostrarCursos = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Este periodo ya termino"));
            }


        } else {
            mostrarCursos = false;
        }
    }

    public void initRender() {
        Anlectivo aEscolarAux = anlectivoEjb.getIniciado();
        if (aEscolarAux == null) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        } else {
            if (!this.consultar) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluacion"));
            }
        }
    }
}
