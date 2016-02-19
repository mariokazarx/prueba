package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.EstadoAniolectivoFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.FormacriterioevaluacionFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Escala;
import com.tesis.entity.EstadoAniolectivo;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Formacriterioevaluacion;
import com.tesis.entity.Matricula;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class mbvAlectivo implements Serializable {

    private Anlectivo anlectivo;
    private Usuario usr;
    private boolean login;
    private Configuracion configuracionselected;
    private EstadoAniolectivo estadoAlectivoselected;
    private EstadoAniolectivo estadoAlectivoselectedAux;
    private List<EstadoAniolectivo> estadosAlectivos;
    private List<Configuracion> configuraciones;
    private List<Anlectivo> anlectivos;
    private Integer año;
    private List<SelectItem> años;
    private String copia;
    private boolean estCopia;
    boolean mensage = false;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private EstadoAniolectivoFacade estadoAlectivoEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private AsignaturacicloFacade asiganturaCicloEjb;
    @EJB
    private EstudianteFacade estdudianteEjb;
    @EJB
    private EscalaFacade escalaEjb;
    @EJB
    private CriterioevaluacionFacade criterioEjb;
    @EJB
    private FormacriterioevaluacionFacade formaEvaluacionEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    
    public mbvAlectivo() {
    }

    public String getCopia() {
        return copia;
    }

    public void setCopia(String copia) {
        this.copia = copia;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public List<SelectItem> getAños() {
        return años;
    }

    public boolean isMensage() {
        return mensage;
    }

    public void setMensage(boolean mensage) {
        this.mensage = mensage;
    }

    public Anlectivo getAnlectivo() {
        return anlectivo;
    }

    public void setAnlectivo(Anlectivo anlectivo) {
        this.anlectivo = anlectivo;
    }

    public Configuracion getConfiguracionselected() {
        return configuracionselected;
    }

    public void setConfiguracionselected(Configuracion configuracionselected) {
        this.configuracionselected = configuracionselected;
    }

    public EstadoAniolectivo getEstadoAlectivoselected() {
        return estadoAlectivoselected;
    }

    public void setEstadoAlectivoselected(EstadoAniolectivo estadoAlectivoselected) {
        this.estadoAlectivoselected = estadoAlectivoselected;
    }

    public List<EstadoAniolectivo> getEstadosAlectivos() {
        return estadosAlectivos;
    }

    public void setEstadosAlectivos(List<EstadoAniolectivo> estadosAlectivos) {
        this.estadosAlectivos = estadosAlectivos;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public AnlectivoFacade getAnlectivoEjb() {
        return anlectivoEjb;
    }

    public void setAnlectivoEjb(AnlectivoFacade anlectivoEjb) {
        this.anlectivoEjb = anlectivoEjb;
    }

    public EstadoAniolectivoFacade getEstadoAlectivoEjb() {
        return estadoAlectivoEjb;
    }

    public void setEstadoAlectivoEjb(EstadoAniolectivoFacade estadoAlectivoEjb) {
        this.estadoAlectivoEjb = estadoAlectivoEjb;
    }

    public ConfiguracionFacade getConfiguracionEjb() {
        return configuracionEjb;
    }

    public void setConfiguracionEjb(ConfiguracionFacade configuracionEjb) {
        this.configuracionEjb = configuracionEjb;
    }

    public boolean isEstCopia() {
        return estCopia;
    }

    @PostConstruct
    public void inicioPagina() {
        //this.año = 0;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        login=false;
        this.estCopia = false;
        this.anlectivo = new Anlectivo();
        this.configuracionselected = new Configuracion();
        this.estadoAlectivoselected = new EstadoAniolectivo();
        this.anlectivos = this.anlectivoEjb.findAll();
        this.configuraciones = this.configuracionEjb.findAll();
        //this.estadosAlectivos = this.estadoAlectivoEjb.findAll();
        Calendar fecha = new GregorianCalendar();
        años = new ArrayList<SelectItem>();
        int a = fecha.get(Calendar.YEAR);
        for (int i = a; i > 2000; i--) {
            //System.out.println("mmm"+i);
            años.add(new SelectItem(i, "" + i));
        }
        //this.criterioeval.setFormacriterioevaluacionId(fcriterioselected);
        //this.dialogEdit=false;
    }

    public void insertar() {
        System.out.println("INSERTAR" + año + "---" + configuracionselected + ":::" + anlectivo.getDescripcion());
        try {
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==1){
                        if(usrRol.getRoleId().getAgregar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                EstadoAniolectivo est = new EstadoAniolectivo();
                est = estadoAlectivoEjb.find(1);
                this.anlectivo.setEstadoAniolectivoId(est);
                this.anlectivo.setAnio(año);
                this.anlectivo.setConfiguracionId(configuracionselected);
                if (this.copia == null) {
                    this.copia = "NO";
                }
                this.anlectivo.setEstadocopiado(copia);
                anlectivoEjb.create(anlectivo);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                inicioPagina();
            }
            else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            System.out.println("INSERTAR ERROR" + e.toString());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void closeDialog() {
        inicioPagina();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Año Registrado", "exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizar() {
        try {
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==1){
                        if(usrRol.getRoleId().getEditar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                System.out.println("AUX"+this.estadoAlectivoselectedAux.getEstadoAniolectivoId()+"cambio"+this.estadoAlectivoselected.getEstadoAniolectivoId());
                if(this.estadoAlectivoselected.getEstadoAniolectivoId() != this.estadoAlectivoselectedAux.getEstadoAniolectivoId()){
                    if(anlectivoEjb.existActivo() && estadoAlectivoselected.getEstadoAniolectivoId()==5){
                        FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya hay activo", ""));
                        return;
                    }
                    if(anlectivoEjb.existIniciado() && estadoAlectivoselected.getEstadoAniolectivoId()==2){
                        FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya hay iniciado", ""));
                        return;
                    }
                }
                this.configuracionselected = this.configuracionEjb.find(configuracionselected.getConfiguracionId());
                this.estadoAlectivoselected = this.estadoAlectivoEjb.find(estadoAlectivoselected.getEstadoAniolectivoId());
                this.anlectivo.setConfiguracionId(configuracionselected);
                this.anlectivo.setEstadoAniolectivoId(estadoAlectivoselected);
                if(estadoAlectivoselected.getEstadoAniolectivoId()==3){
                    this.anlectivo.setTerminado(true);
                    Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                    Criterioevaluacion criterio = criterioEjb.find(anlectivo.getConfiguracionId().getCriterioevaluacionId().getCriterioevaluacionId());
                    //Formacriterioevaluacion formaEvaluacion = formaEvaluacionEjb.find(criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId());
                    System.out.println("MATRICULA ESTUDIANTE DATOS!!!! MIN APROB"+escala.getNotaminimaaprob()+"FORMA"+criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId()+"MINIMO FORMA"+criterio.getMinaprob());
                    //Anlectivo anAux = anlectivoEjb.find(anlectivo.getAnlectivoId());
                    //hacer transacion 1 asignatura 2 areas 
                    List <Matricula> matriculasAnio;
                    matriculasAnio = matriculaEjb.getMatriculasAnio(anlectivo);
                    for(Matricula maticula : matriculasAnio){
                        if(criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId()==1){
                            Long aprobadas = this.asiganturaCicloEjb.asignaturasAprobadasAnio(new Integer(escala.getNotaminimaaprob()),maticula.getEstudianteId(),maticula.getCursoId());
                            if(aprobadas<criterio.getMinaprob()){
                                System.out.println("PERDIO");
                            }else{
                                System.out.println("GANO");
                            }
                        }else if(criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId()==2){

                        }
                        //Estudiante est = estdudianteEjb.find(maticula.getEstudianteId().getEstudianteId());
                        //Long aprobadas = this.asiganturaCicloEjb.asignaturasAprobadasAnio(new Integer(3),maticula.getEstudianteId(),maticula.getCursoId());
                        //System.out.println("MATRICULA ESTUDIANTE !!!!"+maticula.getEstudianteId()+"NUMERO APROABAS"+aprobadas);

                    }
                    return;
                }
                this.anlectivoEjb.edit(anlectivo);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala creada Satisfactoriamente", ""));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarAlectivo').hide()");
                inicioPagina();
            }
            else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void cargarAnlectivo(int anlectivoId) {
        try {
            this.anlectivo = this.anlectivoEjb.find(anlectivoId);
            this.año = this.anlectivo.getAnio();
            this.configuracionselected = this.configuracionEjb.find(anlectivo.getConfiguracionId().getConfiguracionId());
            this.estadoAlectivoselected = this.estadoAlectivoEjb.find(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId());
            this.estadoAlectivoselectedAux = this.estadoAlectivoEjb.find(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId());
            if(anlectivo.getTerminado()){
                this.estadosAlectivos = this.estadoAlectivoEjb.getEstadosAnTerminado();
            }else{
                this.estadosAlectivos = this.estadoAlectivoEjb.findAll();
            }
            //this.areas = this.confuguracionselected.getAreaList();
            RequestContext.getCurrentInstance().update("frmEditarAlectivo:panelEditarAlectivo");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarAlectivo').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void newAnlectivo() {
        Map<String, Object> options = new HashMap<String, Object>();
        /*options.put("contentHeight", 340);
         options.put("height", 400);
         options.put("width",700);*/
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        RequestContext.getCurrentInstance().openDialog("newAnlectivo", options, null);
    }

    public void cargarCopia() {
        if (anlectivoEjb.existeConfiguracion(configuracionselected)) {
            System.out.println("mbvENCONTRO" + this.año);
            estCopia = true;
        } else {
            System.out.println("NO mbvENCONTRO");
            estCopia = false;
        }
    }
    public void eliminarAnlectivo(Anlectivo anlectivo) {
        try {
            if(!login){
                this.mensage = true;
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            boolean permiso=false;
            //6 recurso escalas
            if(this.usr!=null){
                for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                    if(usrRol.getRoleId().getRecursoId().getRecursoId()==1){
                        if(usrRol.getRoleId().getEliminar()){
                            permiso=true;
                        }
                    }
                }
            }
            if(permiso){
                //this.escala = this.escalaEjb.find(escalaid);
                //System.out.println("ELIMINAR CRITERIO :"+criterioeval);
                if(anlectivoEjb.removeById(anlectivo)==true){
                    //inicioPagina();
                    //RequestContext.getCurrentInstance().update("frmEditarEscala"); 
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Escala","eliminada"));
                }else{
                    //RequestContext.getCurrentInstance().update("frmEditarEscala:mensajeGeneral");
                    FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Escala","esta escala esta en uso"));
                }
                inicioPagina();
            }
            else{
                this.mensage = true;
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
}
