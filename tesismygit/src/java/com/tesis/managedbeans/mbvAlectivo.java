package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.EstadoAniolectivoFacade;
import com.tesis.beans.EstadoEstudianteFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.FormacriterioevaluacionFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.NotafinalFacade;
import com.tesis.beans.NotafinalrecuperacionFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Aprobacion;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Configuracion;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
import com.tesis.entity.EstadoAniolectivo;
import com.tesis.entity.EstadoEstudiante;
import com.tesis.entity.EstadoMatricula;
import com.tesis.entity.Estadocontenidotematico;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Formacriterioevaluacion;
import com.tesis.entity.Matricula;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javax.faces.model.SelectItem;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class mbvAlectivo implements Serializable {

    private Anlectivo anlectivo;
    private Usuario usr;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
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
    private boolean mostrarEditar = false;
    private boolean mostrarLabel = false;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstadocontenidotematicoFacade estadoContenidoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
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
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private NotafinalFacade notaFinalEjb;
    @EJB
    private NotafinalrecuperacionFacade notaFinalRecEjb;
    @EJB
    private EstadoEstudianteFacade estadoEstuainteEjb;
    @EJB
    private EstadoMatriculaFacade estadoMatriculaEjb;
    @EJB
    private CicloFacade cicloEjb;
    @Resource
    UserTransaction tx;
    public mbvAlectivo() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public String getCopia() {
        return copia;
    }

    public boolean isMostrarEditar() {
        return mostrarEditar;
    }

    public void setMostrarEditar(boolean mostrarEditar) {
        this.mostrarEditar = mostrarEditar;
    }

    public boolean isMostrarLabel() {
        return mostrarLabel;
    }

    public void setMostrarLabel(boolean mostrarLabel) {
        this.mostrarLabel = mostrarLabel;
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
        this.consultar=false;
        this.editar=false;
        this.eliminar=false;
        this.crear=false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             usr = mbslogin.getUsuario();
             this.login = mbslogin.isLogin();
            System.out.println("usuario"+usr.getNombres()+"Login"+login);
        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
        }
        if(this.usr!=null){
            for(UsuarioRole usrRol:usrRoleEjb.getByUser(usr)){
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==1){
                    if(usrRol.getRoleId().getAgregar()){
                        this.crear=true;
                    }
                    if(usrRol.getRoleId().getConsultar()){
                        this.consultar=true;
                    }
                    if(usrRol.getRoleId().getEditar()){
                        this.editar=true;
                    }
                    if(usrRol.getRoleId().getEliminar()){
                        this.eliminar=true;
                    }
                }
            }
        }
        if(this.usr.getTipoUsuarioId().getTipoUsuarioId()==4){
            this.consultar=true;
            this.editar=true;
            this.eliminar=true;
            this.crear=true;
        }
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
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.crear){
                if(anlectivoEjb.existeAño(año)){
                    FacesContext.getCurrentInstance().
                        addMessage("frmAlectivo:selectaño", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "año en uso"));
                    return;
                }
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
                RequestContext.getCurrentInstance().closeDialog(this);
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
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                tx.begin();
                if(anlectivoEjb.existeAño(año) && año != anlectivo.getAnio()){
                    FacesContext.getCurrentInstance().
                        addMessage("frmEditarAlectivo:selectaño", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "año en uso"));
                    tx.rollback();
                    return;
                }
                //revisar editar 
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
                this.anlectivo.setAnio(año);
                if(estadoAlectivoselected.getEstadoAniolectivoId()==3){
                    if(periodoEjb.getNumeroPeriodosAño(anlectivo)!=periodoEjb.getNumeroPeriodosTerminadosAño(anlectivo)){
                        FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No ha terminado de evaluar todos los periodos"));
                        return;
                    }
                    // verificar que todos los periodos esten terminados 
                    // poner todos lo contenidos de ese año en terminado
                    // traer las asignaturas del ciclo mdel estudiante
                    // comprobar si el año ta estaba terminado
                    // comprobar si perdio mas de la permitidas 
                    // sacar el numero del ciclo dependiendo de si aprobo o no restar o aumentar
                    //cmbiar estados matricua
                    
                    
                    //4
                    Estadocontenidotematico estadoContenido = estadoContenidoEjb.find(4);
                    contenidoEjb.updateTerminarAño(anlectivo, estadoContenido);
                    this.anlectivo.setTerminado(true);
                    Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                    Criterioevaluacion criterio = criterioEjb.find(anlectivo.getConfiguracionId().getCriterioevaluacionId().getCriterioevaluacionId());
                    //Formacriterioevaluacion formaEvaluacion = formaEvaluacionEjb.find(criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId());
                    System.out.println("MATRICULA ESTUDIANTE DATOS!!!! MIN APROB"+escala.getNotaminimaaprob()+"FORMA"+criterio.getFormacriterioevaluacionId().getFormacriterioevaluacionId()+"MINIMO FORMA"+criterio.getMinaprob());
                    //Anlectivo anAux = anlectivoEjb.find(anlectivo.getAnlectivoId());
                    //hacer transacion 1 asignatura 2 areas 
                    List <Matricula> matriculasAnio;
                    matriculasAnio = matriculaEjb.getMatriculasAnio(anlectivo);
                    if(!matriculasAnio.isEmpty()){
                        for(Matricula maticula : matriculasAnio){
                            Estudiante est = estdudianteEjb.find(maticula.getEstudianteId().getEstudianteId());
                            List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                            Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                            Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                            asignaturas = asignaturaEjb.findByCiclo(ciclo);
                            int perdidas = 0;
                            if(asignaturas.isEmpty()){
                                FacesContext.getCurrentInstance().
                                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "hay un ciclo sin asignaturas"));
                                tx.rollback();
                                return;
                            }
                            for(Asignatura asignatura : asignaturas){
                                Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                                Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                                BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
                                System.out.println("NMNMNM"+notafinalEst+"ESTUDIANTE "+est+"asignatura "+asg+"año escolar "+anlectivo+"MATRICULA "+maticula);
                                if(notafinalEst.getRecuperacion().compareTo("SI")==0){
                                    //tiene recupeacion
                                    Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
                                    //queda sacar la nota y comprar para vere si pasa o no

                                    if(notaRecuperacion.getValor().compareTo(max)>=0){
                                        //paso
                                    }else{
                                        //no paso
                                        perdidas ++;
                                    }

                                }else{
                                    //no tiene recuperacion
                                    if(notafinalEst.getValor().compareTo(max)>=0){
                                        //paso
                                    }else{
                                        //no paso
                                        perdidas ++;
                                    }
                                }                           
                            }
                            if(perdidas>criterio.getMinaprob()){
                                //perdio año
                                est.setUltimoaprobado(ciclo.getNumero());
                                Aprobacion aprobacion = aprobacionEjb.find(3);
                                maticula.setAprobacionId(aprobacion);
                            }else{
                                //gano el año
                                est.setUltimoaprobado(ciclo.getNumero()+1);
                                Aprobacion aprobacion = aprobacionEjb.find(2);
                                maticula.setAprobacionId(aprobacion);
                                if(ciclo.getNumero()+1>5){//este es importante
                                    EstadoEstudiante estadoEst = estadoEstuainteEjb.find(2);
                                    est.setEstadoEstudianteId(estadoEst);
                                }
                            }
                            EstadoMatricula estadoMat = estadoMatriculaEjb.find(4);
                            maticula.setEstadoMatriculaId(estadoMat);
                            estdudianteEjb.edit(est);
                            matriculaEjb.edit(maticula);
                            System.out.println("estudiante "+est+"matricula "+maticula+"perdidos "+perdidas);
                        }
                    }else{
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro matrculas activas para este año"));
                        tx.rollback();
                        return;
                    }
                }
                this.anlectivoEjb.edit(anlectivo);
                tx.commit();
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
            e.printStackTrace();
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void cargarAnlectivo(int anlectivoId) {
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                this.anlectivo = this.anlectivoEjb.find(anlectivoId);
                this.año = this.anlectivo.getAnio();
                this.configuracionselected = this.configuracionEjb.find(anlectivo.getConfiguracionId().getConfiguracionId());
                this.estadoAlectivoselected = this.estadoAlectivoEjb.find(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId());
                this.estadoAlectivoselectedAux = this.estadoAlectivoEjb.find(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId());
                if(anlectivo.getTerminado()){
                    this.estadosAlectivos = this.estadoAlectivoEjb.getEstadosAnTerminado();
                    this.mostrarEditar = false;
                    this.mostrarLabel = true;
                }else{
                    this.estadosAlectivos = this.estadoAlectivoEjb.findAll();
                }
                if(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId()==1){
                    //inactivo
                    this.mostrarEditar = true;
                    this.mostrarLabel = false;
                    this.estadosAlectivos.clear();
                    EstadoAniolectivo estAux = estadoAlectivoEjb.find(1);
                    EstadoAniolectivo estAux2 = estadoAlectivoEjb.find(2);
                    EstadoAniolectivo estAux3 = estadoAlectivoEjb.find(4);
                    this.estadosAlectivos.add(estAux);
                    this.estadosAlectivos.add(estAux2);
                    this.estadosAlectivos.add(estAux3);
                }
                if(this.anlectivo.getEstadoAniolectivoId().getEstadoAniolectivoId()==2){
                    //iniciado
                    this.mostrarEditar = false;
                    this.mostrarLabel = true;
                    this.estadosAlectivos.clear();
                    EstadoAniolectivo estAux = estadoAlectivoEjb.find(3);
                    EstadoAniolectivo estAux2 = estadoAlectivoEjb.find(4);
                    EstadoAniolectivo estAux3 = estadoAlectivoEjb.find(2);
                    this.estadosAlectivos.add(estAux);
                    this.estadosAlectivos.add(estAux2);
                    this.estadosAlectivos.add(estAux3);
                }
                //this.areas = this.confuguracionselected.getAreaList();
                RequestContext.getCurrentInstance().update("frmEditarAlectivo:panelEditarAlectivo");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarAlectivo').show()");
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void newAnlectivo() {
        if(!login){
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if(this.crear){
            Map<String, Object> options = new HashMap<String, Object>();
            /*options.put("contentHeight", 340);
             options.put("height", 400);
             options.put("width",700);*/
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newAnlectivo", options, null);
        }
        else{
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
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
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.eliminar){
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
    public void initRender(){
        if(!this.consultar){
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluacion"));
        }
    }
}
