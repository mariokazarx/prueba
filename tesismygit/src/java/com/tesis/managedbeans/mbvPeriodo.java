/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadoPeriodoFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.LogronotaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.EstadoPeriodo;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Periodo;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvPeriodo implements Serializable {

    private static final long serialVersionUID = 9109780962272475850L;
    private Anlectivo anlectivo;
    private Date minDate = new Date();
    private Date maxDate;
    private Anlectivo anlectivoselected;
    private List<Anlectivo> anlectivos;
    private Periodo periodoselected;
    private Periodo periodo;
    private boolean mostrarFecha;
    private List<Periodo> periodos;
    private List<EstadoPeriodo> estados;
    private EstadoPeriodo estadoSelected;
    private boolean mostrar;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private AsignaturacicloFacade asignaturaCicloEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private EstadoPeriodoFacade estadoPeriodoEjb;
    @EJB
    private LogronotaFacade logroNotaEjb;
    @EJB
    private EstadocontenidotematicoFacade estadoContenidoEjb;
    @EJB
    private EstadoPeriodoFacade estadoEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @Resource
    UserTransaction tx;

    public mbvPeriodo() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public boolean isMostrarFecha() {
        return mostrarFecha;
    }

    public void setMostrarFecha(boolean mostrarFecha) {
        this.mostrarFecha = mostrarFecha;
    }

    public EstadoPeriodo getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(EstadoPeriodo estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<EstadoPeriodo> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoPeriodo> estados) {
        this.estados = estados;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Anlectivo getAnlectivoselected() {
        return anlectivoselected;
    }

    public void setAnlectivoselected(Anlectivo anlectivoselected) {
        this.anlectivoselected = anlectivoselected;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public Periodo getPeriodoselected() {
        return periodoselected;
    }

    public void setPeriodoselected(Periodo periodoselected) {
        this.periodoselected = periodoselected;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public Anlectivo getAnlectivo() {
        return anlectivo;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public void setAnlectivo(Anlectivo anlectivo) {
        this.anlectivo = anlectivo;
    }

    public AnlectivoFacade getAnlectivoEjb() {
        return anlectivoEjb;
    }

    public void setAnlectivoEjb(AnlectivoFacade anlectivoEjb) {
        this.anlectivoEjb = anlectivoEjb;
    }

    @PostConstruct
    public void inicio() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        maxDate = cal.getTime();
        this.anlectivo = new Anlectivo();
        mostrarFecha = false;
        this.mostrar = this.anlectivoEjb.enUso();
        this.anlectivoselected = new Anlectivo();
        this.estados = estadoEjb.findAll();
        this.estadoSelected = new EstadoPeriodo();
        this.periodo = new Periodo();
        if (this.mostrar) {
            this.anlectivos = this.anlectivoEjb.getEnUso();
            //this.periodo.setNumero(numero);
        }
        Integer numero = this.periodoEjb.getMaxByAnio(this.anlectivoEjb.getIniciado());
        System.out.println("NUMERO PERIODO" + numero);
        this.periodo.setNumero(numero + 1);
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
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 13) {
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
    }

    public void cargarAnio() {
        try {
            System.out.println("LLego Cargar");
            this.anlectivoselected = anlectivoEjb.find(this.anlectivoselected.getAnlectivoId());
            this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
            System.out.println("LLego Cargar a:" + this.periodos.toString());
        } catch (Exception e) {
            this.periodos = null;
        }

    }

    public void cargarPeriodo(Integer periodoId) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                if (!anlectivoselected.getTerminado()) {
                    System.out.println("LLego Cargarasasa");
                    this.periodo = this.periodoEjb.find(periodoId);
                    this.estadoSelected = this.estadoEjb.find(this.periodo.getEstadoPeriodoId().getEstadoPeriodoId());
                    /*this.año = this.anlectivo.getAnio();
                     this.configuracionselected = this.configuracionEjb.find(anlectivo.getConfiguracionId().getConfiguracionId());
                     this.estadoAlectivoselected = this.estadoAlectivoEjb.find(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId());
                     if(anlectivo.getTerminado()){
                     this.estadosAlectivos = this.estadoAlectivoEjb.getEstadosAnTerminado();
                     }else{
                     this.estadosAlectivos = this.estadoAlectivoEjb.findAll();
                     }*/
                    //this.areas = this.confuguracionselected.getAreaList();
                    if (this.estadoSelected.getEstadoPeriodoId() == 3) {
                        this.mostrarFecha = true;
                    } else {
                        this.mostrarFecha = false;
                    }
                    RequestContext.getCurrentInstance().update("frmEditarPeriodo:panelEditarPeriodo");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarPeriodo').show()");
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "el año escolar ya termino no se puede editar este periodo"));
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            System.out.println("LLego Cargar" + e.toString());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void eliminarPeriodo(Periodo periodo) {
        //verificar que no alla notas registradas
        // eliminar contenido y periodo
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.eliminar) {
                if (!anlectivoselected.getTerminado()) {
                    Anlectivo aEscolar = anlectivoEjb.getIniciado();
                    System.out.println("Numero de periodos" + periodoEjb.getNumeroPeriodosAño(aEscolar) + "En uso" + contenidoEjb.tieneNotas(periodo));
                    if (periodoEjb.getNumeroPeriodosAño(aEscolar) <= 1) {
                        System.out.println("ultimo no se puede");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "No se puede eliminar todos los periodos");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return;
                    }
                    if (contenidoEjb.tieneNotas(periodo)) {
                        System.out.println("esta en uso");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Periodo en uso");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return;
                    }
                    tx.begin();
                    if (contenidoEjb.tieneContenidoPeriodo(periodo)) {
                        if (!contenidoEjb.removeByPeriodo(periodo)) {
                            System.out.println("NO BORRO 1");
                            tx.rollback();
                            return;
                        }
                    }
                    if (!periodoEjb.removeById(periodo)) {
                        System.out.println("NO BORRO 2");
                        tx.rollback();
                        return;
                    }
                    //si tiene contenido   
                    System.out.println("Exito al eliminar");
                    this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Periodo eliminado");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    tx.commit();
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "el año escolar ya termino no se puede eliminar este periodo"));
                }
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                //verificar que la fecha este entre la actual y un mes mas
                //verificsr que no alla otro peroodo en evalucacion activo
                //3
                estadoSelected = estadoPeriodoEjb.find(estadoSelected.getEstadoPeriodoId());
                Anlectivo aEscolar = anlectivoEjb.find(anlectivoselected.getAnlectivoId());
                if (estadoSelected.getEstadoPeriodoId() == 3) {
                    // falta revisar si se han evaluado todo
                    //falta revisar si hay contenidos en advertencia
                    //periodo a evaluar
                    //comprobamos si no hay uno en evaluacion
                    //verificar que no alla contenidos con advertencias
                    //verificar que las asignaturas del ciclo sean iguales a la asigacion

                    if (!contenidoEjb.tieneAdvertencias(periodo)) {
                        List<Curso> cursosAux = cursoEjb.getCursosByAño(periodo.getAnlectivoId());
                        for (Curso cur : cursosAux) {
                            if (asignaturaCicloEjb.countAsignaturasCiclo(cur.getCicloId()) != contenidoEjb.countAsignaturasCursoPerido(cur, periodo)) {
                                System.out.println("Falta completar la asignacion academica del curso" + cur.getNombre());
                                FacesContext.getCurrentInstance().
                                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Falta completar la asignacion academica del curso" + cur.getNombre()));
                                return;
                            }
                        }
                        if (!periodoEjb.enUso(aEscolar) || this.periodo.getEstadoPeriodoId().getEstadoPeriodoId() == this.estadoSelected.getEstadoPeriodoId()) {
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.MONTH, +1);
                            Date auxMax = cal.getTime();
                            Date auxMin = new Date();
                            if (periodo.getFechacierre().after(auxMin) && periodo.getFechacierre().before(auxMax)) {
                                System.out.println("Funciona Fecha Periodo");
                                periodo.setEstadoPeriodoId(estadoSelected);
                                periodoEjb.edit(periodo);
                                FacesContext.getCurrentInstance().
                                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo editado"));
                                RequestContext.getCurrentInstance().execute("PF('dialogoEditarPeriodo').hide()");
                                //inicio();
                                this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
                                /*Estadocontenidotematico estadoAux = estadoContenidoEjb.find(2);
                                 if(contenidoEjb.updateIniciarPeriodo(periodo,estadoAux)){
                                 periodoEjb.edit(periodo);
                                 }else{
                                 System.out.println("ERROR AL INICIAR EVALUACION NO CAMBIO ESTADO");
                                 }*/
                            }
                        } else {
                            FacesContext.getCurrentInstance().
                                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Actualmente ya se encuentra otro periodo en evaluación"));
                            System.out.println("ESTA EN USO" + estadoSelected.getEstadoPeriodoId() + "  aa" + this.periodo.getEstadoPeriodoId().getEstadoPeriodoId());
                            return;
                        }
                    } else {
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Tiene asignaturas sin asignar profesor"));
                        System.out.println("TIENE ADVERTECIA");
                        return;
                    }


                }
                if (estadoSelected.getEstadoPeriodoId() == 2) {
                    //terminado
                    //falta revisar si hay contenidos en advertencia
                    //verificar que almenos tenga notas
                    //verificar que no alla contenidos con advertencias
                    if (!contenidoEjb.tienePendientes(periodo)) {
                        if (!contenidoEjb.tieneAdvertencias(periodo)) {
                            if (logroNotaEjb.tieneNotas(periodo)) {
                                System.out.println("Tiene NOTAS");
                                periodo.setEstadoPeriodoId(estadoSelected);
                                Estadocontenidotematico estadoAux = estadoContenidoEjb.find(4);
                                if (contenidoEjb.updateIniciarPeriodo(periodo, estadoAux)) {
                                    Date fecha = new Date();
                                    periodo.setFechacierre(fecha);
                                    periodoEjb.edit(periodo);
                                    FacesContext.getCurrentInstance().
                                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo editado"));
                                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarPeriodo').hide()");
                                    //inicio();
                                    this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
                                } else {
                                    FacesContext.getCurrentInstance().
                                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
                                    return;
                                }
                            } else {
                                System.out.println("NO TIENE NOTAS");
                                FacesContext.getCurrentInstance().
                                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "todos los profesores deben realizar la evaluación"));
                                return;
                            }
                        } else {
                            System.out.println("TIENE ADVERTECIA");
                            FacesContext.getCurrentInstance().
                                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Tiene asignaturas sin asignar profesor"));
                            return;
                        }
                    } else {
                        System.out.println("TIENE PENDIENTES");
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "todos los profesores deben realizar la evaluación"));
                    }
                }
                if (estadoSelected.getEstadoPeriodoId() == 1) {
                    periodo.setEstadoPeriodoId(estadoSelected);
                    periodoEjb.edit(periodo);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo editado"));
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarPeriodo').hide()");
                    //inicio();
                    this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
                }

            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }


    }

    public void insertar() throws IllegalStateException, SecurityException, SystemException {

        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.crear) {
                // traer el contenido de un periodo del mismo año y copiarlo
                Anlectivo aEscolar = anlectivoEjb.getIniciado();
                Periodo periodoAux = periodoEjb.getPeriodoMinByAnio(aEscolar);
                List<Contenidotematico> contenidosAux = new ArrayList<Contenidotematico>(contenidoEjb.getByPeriodo(periodoAux));
                System.out.println("periodo " + periodoAux);
                periodo.setEstadoPeriodoId(estadoPeriodoEjb.find(1));
                periodo.setAnlectivoId(aEscolar);
                tx.begin();
                for (Contenidotematico contenido : contenidosAux) {
                    Contenidotematico contenidoNuevo = new Contenidotematico();
                    contenidoNuevo.setAsignaturacicloId(contenido.getAsignaturacicloId());
                    contenidoNuevo.setCursoId(contenido.getCursoId());
                    contenidoNuevo.setEstado(contenido.getEstado());
                    contenidoNuevo.setProfesorId(contenido.getProfesorId());
                    contenidoNuevo.setPeriodoId(periodo);
                    contenidoEjb.create(contenidoNuevo);
                    System.out.println("Contenido " + contenido);
                }
                periodoEjb.create(periodo);
                tx.commit();
                this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
                RequestContext.getCurrentInstance().closeDialog(this);
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            tx.rollback();
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }

    }

    public void newPeriodo() {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if (this.crear) {
            if (!anlectivoselected.getTerminado()) {
                Map<String, Object> options = new HashMap<String, Object>();
                /*options.put("contentHeight", 340);
                 options.put("height", 400);*/
                options.put("width", 300);
                options.put("modal", true);
                options.put("draggable", true);
                options.put("resizable", true);
                RequestContext.getCurrentInstance().openDialog("newPeriodo", options, null);
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "el año escolar ya termino no se puede crear un nuevo periodo"));
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }

    public void closeDialog() {
        //inicio();
        this.periodos = this.periodoEjb.getPeriodosByAnio(anlectivoselected);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Periodo registrado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void cargarEstado() {
        if (estadoSelected.getEstadoPeriodoId() == 3) {
            mostrarFecha = true;
        } else {
            mostrarFecha = false;
        }
    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar periodos"));
        }
    }
}
