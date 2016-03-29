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
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Aprobacion;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.EstadoMatricula;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
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
public class mbvMatricula implements Serializable {

    private Estudiante estudiante;
    private boolean banderaExiste;
    private List<Curso> cursos;
    private List<Estudiante> estMatriculados;
    private List<Estudiante> estudiantes;
    private Ciclo cicloEstudiante;
    private Curso cursoSelected;
    private Matricula matricula;
    private boolean contenidoMatricular;
    private boolean contenidoCancelar;
    private boolean contenidoCambiar;
    private boolean contenido;
    private boolean mostrarPrincipal;
    private boolean mostrarMatriculados;
    private String mensaje;
    private String mensajeCancelar;
    private String txtmensaje;
    private boolean contenidoSuspender;
    private boolean contenidoActivar;
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
    private EstadoMatriculaFacade estadomatriculaEjB;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private CursoFacade CursoEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    
    public mbvMatricula() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isEditar() {
        return editar;
    }

    public boolean isContenidoActivar() {
        return contenidoActivar;
    }

    public void setContenidoActivar(boolean contenidoActivar) {
        this.contenidoActivar = contenidoActivar;
    }

    public boolean isContenidoSuspender() {
        return contenidoSuspender;
    }

    public void setContenidoSuspender(boolean contenidoSuspender) {
        this.contenidoSuspender = contenidoSuspender;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarMatriculados() {
        return mostrarMatriculados;
    }

    public void setMostrarMatriculados(boolean mostrarMatriculados) {
        this.mostrarMatriculados = mostrarMatriculados;
    }

    public boolean isContenidoCambiar() {
        return contenidoCambiar;
    }

    public void setContenidoCambiar(boolean contenidoCambiar) {
        this.contenidoCambiar = contenidoCambiar;
    }

    public List<Estudiante> getEstMatriculados() {
        return estMatriculados;
    }

    public void setEstMatriculados(List<Estudiante> estMatriculados) {
        this.estMatriculados = estMatriculados;
    }
    
    public boolean isContenidoMatricular() {
        return contenidoMatricular;
    }

    public void setContenidoMatricular(boolean contenidoMatricular) {
        this.contenidoMatricular = contenidoMatricular;
    }

    public boolean isContenidoCancelar() {
        return contenidoCancelar;
    }

    public void setContenidoCancelar(boolean contenidoCancelar) {
        this.contenidoCancelar = contenidoCancelar;
    }

    
    
    public String getMensajeCancelar() {
        return mensajeCancelar;
    }

    public void setMensajeCancelar(String mensajeCancelar) {
        this.mensajeCancelar = mensajeCancelar;
    }

    
    
    public boolean isContenido() {
        return contenido;
    }

    public void setContenido(boolean contenido) {
        this.contenido = contenido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {  
        this.mensaje = mensaje;
    }

   

    public String getTxtmensaje() {
        return txtmensaje;
    }

    public void setTxtmensaje(String txtmensaje) {
        this.txtmensaje = txtmensaje;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
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

    public boolean isBanderaExiste() {
        return banderaExiste;
    }

    public void setBanderaExiste(boolean banderaExiste) {
        this.banderaExiste = banderaExiste;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @PostConstruct()
    public void inicio() {
        this.cicloEstudiante = new Ciclo();
        this.cursoSelected = new Curso();
        this.matricula = new Matricula();
        this.cursos = new ArrayList<Curso>();
        this.estMatriculados = new ArrayList<Estudiante>(); 
        this.contenidoSuspender = false;
        contenidoActivar = false;
        //mbvEstudiante estudiante = (mbvEstudiante) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mbvEstudiante");
        Estudiante aux = (Estudiante) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("param1");
        
        estudiantes= estudianteEJb.findAll();
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
                if(usrRol.getRoleId().getRecursoId().getRecursoId()==9){
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
        cargarDatos(aux);
    }

    public void cargarCursos() {
        try {
            this.contenido=true;
            this.mensaje = "";
            this.cicloEstudiante = cicloEjb.getCicloByNum(this.estudiante.getUltimoaprobado());
            this.cursos = new ArrayList<Curso>();
            this.cursos = this.cicloEstudiante.getCursoList();
            System.out.println("ciclo" + this.cicloEstudiante + "cursos" + this.cicloEstudiante.getNumero());
        } catch (Exception e) {
            this.contenido=false;
            this.mensaje = "";
        }
        
    }
    public List<Estudiante> completeEstudiante(String query) {

        List<Estudiante> allThemes = estudianteEJb.findAll();
        List<Estudiante> filteredThemes = new ArrayList<Estudiante>();
        System.out.println("ccc" + query + allThemes.size());
        for (int i = 0; i < allThemes.size(); i++) {
            Estudiante skin = allThemes.get(i);
            if (skin.getIdentificiacion().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }

        return filteredThemes;
    }
    private void cargarDatos(Estudiante aux){
        if (aux != null) {
            if(!this.editar){
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
            this.mostrarPrincipal = true;
            this.estudiante = estudianteEJb.find(aux.getEstudianteId());
            System.out.println("QQQQQ1" + this.estudiante + "EEE1" + estudiante.getNombre()+"estado"+estudiante.getEstadoEstudianteId());
            if(estudiante.getEstadoEstudianteId().getEstadoEstudianteId()==1){
                Anlectivo auxEscolar = aEscolarEjb.getIniciado();
                if(auxEscolar!=null){
                    //existe año iniciado
                    boolean bandera = false;
                    for(Ciclo ciclo:auxEscolar.getConfiguracionId().getCicloList()){
                        System.out.println("CICLO"+ciclo.getNumero());
                        if(ciclo.getNumero()==estudiante.getUltimoaprobado()){
                            bandera=true;
                            break;
                        }
                    }
                    if(!bandera){
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Actualmente no se ofrece el ciclo para este estudiante")); 
                        System.out.println("No se ofrece el ciclo");
                    }
                    else{
                        boolean banderaCursos=false;
                        System.out.println("se ofrece el ciclo");
                        cursos.clear();
                        for(Curso curAux:auxEscolar.getCursoList()){
                            System.out.println("Curso"+curAux.getNombre()+"Ciclo"+curAux.getCicloId().getNumero());
                            if(curAux.getCicloId().getNumero()==estudiante.getUltimoaprobado()){
                                banderaCursos = true;
                                cursos.add(curAux);
                                //break;
                            }
                        }
                        if(cursos.isEmpty()){
                            FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay cursos registrados para el ciclo del estudiante")); 
                            System.out.println("No se ofrece curso");
                        }else{
                            System.out.println("se ofrece curso");
                            System.out.println("si año iniciado");
                            Matricula auxMatricula = matriculaEjb.getActivaByEstudiante(estudiante);
                            if(auxMatricula!=null){
                                //hay matricula activa 
                                System.out.println("Curso"+auxMatricula.getCursoId().getAnlectivoId()+"año escolar"+auxEscolar.getAnlectivoId());
                                if(auxMatricula.getCursoId().getAnlectivoId().getAnlectivoId()==auxEscolar.getAnlectivoId()){
                                    //son matriculas del mismo año
                                    System.out.println("mismo año");
                                    Long cursosCount = CursoEjb.countCursosCiclo(auxEscolar, auxMatricula.getCursoId().getCicloId());
                                    if(cursosCount>1){
                                        contenidoCancelar=true;
                                        contenidoMatricular=false;
                                        contenidoCambiar = true;
                                        contenidoSuspender = true;
                                        contenidoActivar = false;
                                        matricula = auxMatricula;
                                        mensajeCancelar="Detalle matricula";
                                        cursos.remove(auxMatricula.getCursoId());
                                        estMatriculados.clear();
                                        for(Matricula matriculasCurso : matriculaEjb.matriculasCurso(auxMatricula.getCursoId())){
                                            estMatriculados.add(matriculasCurso.getEstudianteId());
                                        }
                                    }else{
                                        contenidoCancelar=true;
                                        contenidoMatricular=false;
                                        contenidoCambiar = false;
                                        contenidoSuspender = true;
                                        contenidoActivar = false;
                                    }
                                    
                                }
                                else{
                                    System.out.println("diferente año");
                                    // no son en el mismo año 
                                    contenidoCancelar=true;
                                    contenidoMatricular=false;
                                    contenidoSuspender = false;
                                    contenidoActivar = false;
                                    matricula = auxMatricula;
                                    mensajeCancelar="El estudiante tiene una matricula activa en otro año debe cancelarla para realizar matricula al año actual";
                                }
                            }else{
                                //no esta matriculado
                                Matricula suspendida = matriculaEjb.getSuspendidaByEstudiante(estudiante,auxEscolar);
                                matricula = suspendida;
                                if(suspendida!=null){
                                    contenidoActivar = true;
                                    contenidoCambiar = false;
                                    contenidoCancelar = true;
                                    contenidoSuspender = false;
                                    contenidoMatricular = false;
                                    
                                }else{
                                    System.out.println("No matriculado");
                                    contenidoMatricular=true;
                                    contenidoCambiar = false;
                                    contenidoCancelar=false;
                                    contenidoSuspender = false;
                                    contenidoActivar = false;
                                }
                                
                            }
                        }
                    }
                    
                    
                }
                //el estudiante esta activo
                //comprobar si el ciclo se ofrece ese año
                //comprobar si existe curso disponible para el año que va el estudiante
                //comprobar si hay un año activo o iniciado no terminado
                //comprobar que el estudiante no tenga ninguna matrocula activa
                // si tiene una matricula activa comprobar si es dle mismo año activo
                
            }else if(estudiante.getEstadoEstudianteId().getEstadoEstudianteId()==2){
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "El estudiante ya termindo los estudios"));
            }
            else if(estudiante.getEstadoEstudianteId().getEstadoEstudianteId()==2){
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El estudiante se encuentra inactivo en el sistema"));
            }
            //cargarCursos();
        } else {
            this.mostrarPrincipal = false;
            this.contenido=false;
            contenidoActivar = false;
            this.mensaje = "";
            System.out.println("QQQQQ2" + this.estudiante);
            System.out.println("OTRO"+this.estudiante);
            //this.estudiante = new Estudiante();
        }
    }
    public void buscar() {
        try {
            if (this.estudiante != null) {
                System.out.println("aaaaww"+this.estudiante.getNombre());
                //cursoSelected = cursoEjb.find(1);
               // this.banderaSearch = true;
                this.cargarDatos(this.estudiante);
                this.contenido=true;
                this.mensaje = "";
            } else {
                //this.banderaSearch = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estudiante no encontrado", "no se encontro ningun estudiante"));
                
                this.contenido = false;
                this.mensaje = "";
            }

        } catch (Exception e) {
            System.out.println("errrrooorrrr" + e.toString());
            //this.banderaAsig = true;
            this.contenido = false;
            this.mensaje = "";
        }

    }
    public void matricularEstudiante() {
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                System.out.println("curso");
                if (cursoSelected != null) {
                    cursoSelected = CursoEjb.find(cursoSelected.getCursoId());
                    EstadoMatricula esmatricula = new EstadoMatricula();
                    esmatricula = estadomatriculaEjB.find(1);
                    Aprobacion aprobacion = new Aprobacion();
                    aprobacion = aprobacionEjb.find(1);
                    this.matricula.setAprobacionId(aprobacion);
                    this.matricula.setCursoId(cursoSelected);
                    this.matricula.setEstadoMatriculaId(esmatricula);
                    this.matricula.setEstudianteId(estudiante);
                    matriculaEjb.create(matricula);
                    this.contenidoMatricular = false;
                    this.contenidoCambiar = true;
                    this.contenidoCancelar = true;
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Matricula exitosa", "Estudiante matriulado al Ciclo "+cursoSelected.getCicloId().getNumero()+ " Curso "+cursoSelected.getNombre()));

                    //inicio();
                }
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            System.out.println("ooooOOOO"+e.toString());
        }


    }
    public void cancelarMatricula(){
        if(!login){
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if(this.editar){
            EstadoMatricula esmatricula = new EstadoMatricula();
            esmatricula = estadomatriculaEjB.find(2);
            this.matricula.setEstadoMatriculaId(esmatricula);
            matriculaEjb.edit(matricula);
            contenidoMatricular=true;
            contenidoCancelar=false;
        }
        else{
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }
    public void cambiarMatricularEstudiante(){
        if(!login){
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if(this.editar){
            if(cursoSelected!=null){
                this.matricula.setCursoId(cursoSelected);
                matriculaEjb.edit(matricula);
            }
        }
        else{
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }
    public void initRender(){
        Anlectivo aEscolarAux = aEscolarEjb.getIniciado();
        if(aEscolarAux==null){
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }else{
            if(!this.consultar){
                FacesContext.getCurrentInstance().
                           addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluacion"));
            }
        }
    }
    public String getMatricula(Estudiante estAux){
        System.out.println("AUX EST"+estAux);
        Matricula auxMatricula = matriculaEjb.getActivaByEstudiante(estAux);
        if(auxMatricula!=null){
            return auxMatricula.getCursoId().getNombre();
        }else{
            return "PENDIENTE";
        }
    }
    public void suspenderMatricula(){
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                EstadoMatricula esmatricula = new EstadoMatricula();
                esmatricula = estadomatriculaEjB.find(3);
                this.matricula.setEstadoMatriculaId(esmatricula);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Matricula suspendida exitosamente", ""));
                matriculaEjb.edit(matricula);
                contenidoMatricular=false;
                contenidoCancelar=false;
                contenidoCambiar = false;
                contenidoActivar = true;
                contenidoSuspender = false;
                this.matriculaEjb.edit(matricula);
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "error inesperado"));
            
        }
        
    }
    public void activarMatricula(){
        try {
            if(!login){
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if(this.editar){
                EstadoMatricula esmatricula = new EstadoMatricula();
                esmatricula = estadomatriculaEjB.find(1);
                this.matricula.setEstadoMatriculaId(esmatricula);
                matriculaEjb.edit(matricula);
                contenidoMatricular=false;
                contenidoCancelar=true;
                contenidoCambiar = false;
                contenidoActivar = false;
                contenidoSuspender = true;
                this.matriculaEjb.edit(matricula);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Matricula activada exitosamente", ""));
            }
            else{
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "error inesperado"));
            
        }
        
    }
}
