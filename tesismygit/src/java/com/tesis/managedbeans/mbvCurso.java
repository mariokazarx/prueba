/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCurso implements Serializable {

    private Curso curso;
    private List<Curso> cursos;
    private Anlectivo anlectivoSelected;
    private List<Anlectivo> anlectivos;
    private List<Anlectivo> auxAnlectivos;
    private Ciclo cicloselected;
    private List<Ciclo> ciclosSelected;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AnlectivoFacade anlectivoEJB;
    @EJB
    private CicloFacade cicloEJB;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @EJB
    private MatriculaFacade matriculaEjb;

    public mbvCurso() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
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

    public AnlectivoFacade getAnlectivoEJB() {
        return anlectivoEJB;
    }

    public void setAnlectivoEJB(AnlectivoFacade anlectivoEJB) {
        this.anlectivoEJB = anlectivoEJB;
    }

    public Ciclo getCicloselected() {
        return cicloselected;
    }

    public void setCicloselected(Ciclo cicloselected) {
        this.cicloselected = cicloselected;
    }

    public List<Ciclo> getCiclosSelected() {
        return ciclosSelected;
    }

    public void setCiclosSelected(List<Ciclo> ciclosSelected) {
        this.ciclosSelected = ciclosSelected;
    }

    public CursoFacade getCursoEjb() {
        return cursoEjb;
    }

    public void setCursoEjb(CursoFacade cursoEjb) {
        this.cursoEjb = cursoEjb;
    }

    public CicloFacade getCicloEJB() {
        return cicloEJB;
    }

    public void setCicloEJB(CicloFacade cicloEJB) {
        this.cicloEJB = cicloEJB;
    }

    @PostConstruct
    public void inicioPagina() {
        this.cursos = cursoEjb.findAll();
        this.anlectivoSelected = new Anlectivo();
        this.cicloselected = new Ciclo();
        this.curso = new Curso();
        this.anlectivos = new ArrayList<Anlectivo>();
        this.auxAnlectivos = this.anlectivoEJB.findAll();
        for (Anlectivo aux : auxAnlectivos) {
            if (aux.getEstadoAniolectivoId().getEstadoAniolectivoId() == 2) {
                anlectivos.add(aux);
            }
        }
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
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 14) {
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

    public void cargarCiclo() {
        try {
            this.anlectivoSelected = anlectivoEJB.find(anlectivoSelected.getAnlectivoId());
            //System.out.println("mma"+anlectivoSelected.getConfiguracionId().getNombre());
            this.ciclosSelected = cicloEJB.getByConfiguracion(anlectivoSelected.getConfiguracionId());
            System.out.println(this.ciclosSelected.isEmpty());
            //if(this.ciclosSlected.isEmpty()==true){
            //this.banAsig=false;
            //}
        } catch (Exception e) {
            System.out.println("mmaaaaa" + e.getMessage());
        }
    }

    public void insertar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.crear) {
                this.curso.setAnlectivoId(anlectivoSelected);
                this.curso.setCicloId(cicloselected);
                this.cursoEjb.create(curso);
                RequestContext.getCurrentInstance().closeDialog(this);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso creado satisfactoriamente"));
                inicioPagina();
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error inesperado", "Contáctese con el administrador"));
        }

    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso registrado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void newCurso() {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
            return;
        }
        if (this.crear) {
            if (!anlectivoEJB.existIniciadoNew()) {
                RequestContext.getCurrentInstance().execute("PF('añoNoIniciado').show()");
            } else {
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("contentHeight", 340);
                options.put("height", 400);
                options.put("width", 700);
                options.put("contentWidth", 680);
                options.put("modal", true);
                //options.put("showEffect", "clip");
                options.put("draggable", true);
                options.put("resizable", true);
                RequestContext.getCurrentInstance().openDialog("newcurso", options, null);
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
        }
    }

    public void eliminarCurso(Curso curso) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.eliminar) {
                //this.escala = this.escalaEjb.find(escalaid);
                //System.out.println("ELIMINAR CRITERIO :"+criterioeval);
                if (cursoEjb.removeById(curso) == true) {
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso eliminado"));
                } else {
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "este curso esta en uso"));
                }
                inicioPagina();
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

    public void cargarCurso(int cursoId) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                this.curso = this.cursoEjb.find(cursoId);
                this.anlectivoSelected = this.anlectivoEJB.find(this.curso.getAnlectivoId().getAnlectivoId());
                this.ciclosSelected = cicloEJB.getByConfiguracion(anlectivoSelected.getConfiguracionId());
                this.cicloselected = this.cicloEJB.find(this.curso.getCicloId().getCicloId());
                if (anlectivoSelected.getTerminado()) {
                    RequestContext.getCurrentInstance().execute("PF('enUso').show()");
                } else {
                    RequestContext.getCurrentInstance().update("frmEditarCurso:panelEditarCurso");
                    RequestContext.getCurrentInstance().execute("PF('dialogoEditarCurso').show()");
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", "Contáctese con el administrador"));
        }
    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar cursos"));
        }
    }

    public void actualizar() {
        try {
            if (!login) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesión"));
                return;
            }
            if (this.editar) {
                Integer matriculados = matriculaEjb.countMatriculadosCurso(curso);
                if (matriculados != null) {
                    if (matriculados <= curso.getNumeroestudiantes()) {
                        cursoEjb.edit(curso);
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Curso editado satisfactoriamente"));
                        RequestContext.getCurrentInstance().execute("PF('dialogoEditarCurso').hide()");
                        inicioPagina();
                    } else {
                        FacesContext.getCurrentInstance().
                                addMessage("frmEditarCurso:txtMinEst", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El numero de estudiantes debe ser mayor o igual a "+matriculados));
                    }
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR inesperado", "Curso editado satisfactoriamente"));
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta acción"));
            }
        } catch (Exception e) {
        }
    }
}
