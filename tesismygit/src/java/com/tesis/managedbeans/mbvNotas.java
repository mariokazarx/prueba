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
import com.tesis.beans.EstadologronotaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.LogroFacade;
import com.tesis.beans.LogronotaFacade;
import com.tesis.beans.NotaFacade;
import com.tesis.beans.NotafinalFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.clases.EstudianteNotas;
import com.tesis.clases.ReporteAsignacionAcademica;
import com.tesis.clases.ReporteNotasProfesor;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estadologronota;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.ArrayList;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvNotas implements Serializable {
   
    private static final long serialVersionUID = -3334971107153487494L;

    private List<ReporteNotasProfesor> reporte;
    private String notaxxLogro;
    private Map<String,Integer> datosNotas = new HashMap<String, Integer>();
    private boolean login;
    private List<EstudianteNotas> estudiantesN;
    private Profesor profesor;
    private boolean contenidoLogros;
    private boolean contenidoNotas;
    private boolean editable;
    private Estudiante estActual;
    private Nota notaEstudianteActual;
    private String observaciones;
    private Anlectivo anlectivo;
    private Periodo periodo;
    private Curso cursoSelected;
    private Asignatura asignaturaSelected;
    private Contenidotematico contenido;
    private Logro logro;
    private List<Logro> logros;
    private List<Curso> cursos;
    private List<Asignatura> asignaturas;
    private List<Asignaturaciclo> asignaturasCiclo;
    private List<ColumnModel> columns;
    private List<Estudiante> estudiantes;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private AsignaturacicloFacade asignaturaCicloEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private LogroFacade logroEjb;
    @EJB
    private LogronotaFacade logroNotaEjb;
    @EJB
    private EstadologronotaFacade estadologroEjb;
    @EJB
    private EstudianteFacade estudianteEjB;
    @EJB
    private NotaFacade notaEstEJB;
    @EJB
    private NotafinalFacade notafEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @Resource
    UserTransaction tx;
    private JasperPrint jasperPrint;
    
    public mbvNotas() {
    }

    public List<EstudianteNotas> getEstudiantesN() {
        return estudiantesN;
    }

    public void setEstudiantesN(List<EstudianteNotas> estudiantesN) {
        this.estudiantesN = estudiantesN;
    }

    public Map<String, Integer> getDatosNotas() {
        return datosNotas;
    }

    public void setDatosNotas(Map<String, Integer> datosNotas) {
        this.datosNotas = datosNotas;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isContenidoLogros() {
        return contenidoLogros;
    }

    public void setContenidoLogros(boolean contenidoLogros) {
        this.contenidoLogros = contenidoLogros;
    }

    public boolean isContenidoNotas() {
        return contenidoNotas;
    }

    public void setContenidoNotas(boolean contenidoNotas) {
        this.contenidoNotas = contenidoNotas;
    }

    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void setCursoSelected(Curso cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    public Asignatura getAsignaturaSelected() {
        return asignaturaSelected;
    }

    public void setAsignaturaSelected(Asignatura asignaturaSelected) {
        this.asignaturaSelected = asignaturaSelected;
    }

    public Contenidotematico getContenido() {
        return contenido;
    }

    public void setContenido(Contenidotematico contenido) {
        this.contenido = contenido;
    }

    public Logro getLogro() {
        return logro;
    }

    public void setLogro(Logro logro) {
        this.logro = logro;
    }

    public List<Logro> getLogros() {
        return logros;
    }

    public void setLogros(List<Logro> logros) {
        this.logros = logros;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public String getNotaxxLogro() {
        System.out.println("GET" + notaxxLogro);
        return notaxxLogro;
    }

    public void setNotaxxLogro(String notaxxLogro) {
        try {
            System.out.println("hola" + notaxxLogro);
            this.notaxxLogro = notaxxLogro;
        } catch (Exception e) {
            System.out.println("EROR" + e.toString());
        }

    }

    public void setNotaxxLogro(Estudiante es) {
        try {
            System.out.println("hola" + es.getNombre());
            //this.notaxxLogro = notaxxLogro;
        } catch (Exception e) {
            System.out.println("EROR" + e.toString());
        }

    }

    @PostConstruct
    public void inicioPagina() {
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             profesor = mbslogin.getProfesor();
             login = mbslogin.isLogin();
            System.out.println(mbslogin.getProfesor().getNombre());
        } catch (Exception e) {
            System.out.println(e.toString());
            profesor = null;
        }
        this.estudiantesN = new ArrayList<EstudianteNotas>();
        this.editable=true;
        this.contenidoLogros = false;
        this.contenidoNotas = false;
        this.logros = new ArrayList<Logro>();
        this.logro = new Logro();
        this.asignaturaSelected = new Asignatura();
        this.contenido = new Contenidotematico();
        this.cursoSelected = new Curso();
        if(profesor!=null){
            System.out.println("logueado como profesor");
            anlectivo = anlectivoEjb.getIniciado();
            //año iniciado
            this.periodo = periodoEjb.getPeriodEvaluar(anlectivo);
            this.cursos = cursoEjb.findCursosProfeso(profesor, periodo);
            this.estudiantes = new ArrayList<Estudiante>();
            this.notaxxLogro = new String();
        }
         
        //this.profesor = profesorEjb.find(1); 
        //this.periodo = periodoEjb.find(11);
        
        //cargarModelos();
        //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        //createDynamicColumns();
    }

    public void cargarAsignaturas() {
        asignaturas = new ArrayList<Asignatura>();
        contenidoLogros = false;
        contenidoNotas = false;
        asignaturasCiclo = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected, periodo);
        for (Asignaturaciclo asc : asignaturasCiclo) {
            Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
            asignaturas.add(as);
        }
        asignaturaSelected = new Asignatura();
    }

    public void cargarContenido() {
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignaturaSelected);
        this.contenido = contenidoEjb.getContenidoByAll(profesor, cursoSelected, asg, periodo);
        System.out.println("AAAAA" + cursoSelected + "Periodo" + periodo + "Profesor" + profesor + "asignaturaciclo" + asg + "contenido" + contenido);
        this.logros = logroEjb.getContenidoByAll(contenido);
        estudiantes = estudianteEjB.findByCurso(cursoSelected);
        notaxxLogro = "";
        Integer suma =0;
        for(Logro logAuxSum:logroEjb.getByContenido(contenido)){
            System.out.println("SUMARRRR"+logAuxSum.getPorcentaje());
            suma += logAuxSum.getPorcentaje();
        }
        System.out.println("SUMAAAA"+suma);
        if(suma==100){
            contenidoNotas=true;
        }else{
            contenidoNotas = false;
        }
        contenidoLogros = true;
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
        createDynamicColumns();
        cargarEstudiantes();
    }
    
    private void cargarEstudiantes(){
        List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
        
        estudiantesN.clear();
        System.out.println("CONTENIDOOOOOO "+estudiantesN.size());
        for(Estudiante est:estudiantes){
            EstudianteNotas estNota = new EstudianteNotas();
            estNota.setId(est.getEstudianteId());
            estNota.setNombre(est.getNombre());
            estNota.setApellido(est.getApellido());           
            estNota.setNota(getNotaEst(est));
            Map<Integer,Object> notasLog = new HashMap<Integer,Object>();
            List<Logronota> logrosNotaAux = new ArrayList<Logronota>();
            for (Logro aux : logrosaux) {
                logrosNotaAux.add(logroNotaEjb.getByLogroestudiante(est, aux));
                notasLog.put(aux.getLogroId(),getNotaEstudiante(est,aux.getLogroId()));
                System.out.println("CONTENIDOOOOOO 22222 "+getNotaEstudiante(est,aux.getLogroId()));
            }
            estNota.setLogros(logrosNotaAux);
            estNota.setNotasLogros(notasLog);
            
            estudiantesN.add(estNota);
        }
    }

    public void newLogro() {
        try {
            this.logro = new Logro();
            RequestContext.getCurrentInstance().update("frmCrearLogro:panelCrearLogro");
            RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void insertarLogro() {
        try {
            Integer suma = 0;
            if(this.logro.getPorcentaje()<0 || this.logro.getPorcentaje()>100){
                System.out.println("LOGRO FUERA DE RAMGO");
                return;
            }
            System.out.println("contenido"+contenido+"    "+contenido.getLogroList());//traer por sql
            for(Logro logAuxSum:logroEjb.getByContenido(contenido)){
                System.out.println("SUMARRRR"+logAuxSum.getPorcentaje());
                suma += logAuxSum.getPorcentaje();
            }
            suma += this.logro.getPorcentaje();
            System.out.println("SUMA FINAL"+suma);
            if(suma>100){
                System.out.println("SE PASA DE 100");
                return;
            }
            if(suma==100){
                //cambiar estado contenido a iniciado
                this.contenidoNotas=true;
            }
            if (contenido != null) {
                tx.begin();
                this.logro.setContenidotematicoId(contenido);
                this.logroEjb.create(logro);
                this.logro = new Logro();
                List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
                //System.out.println("ESTUDIANTES***"+estudiantes);
                for (Estudiante es : estudiantes) {
                    //System.out.println("ENTRA ESTUDIANTES***");
                    BigDecimal nota = new BigDecimal(0);
                    Nota notaEst = new Nota();
                    for (Logro aux : logrosaux) {
                        //System.out.println("ENTRA LOGROS***");
                        Logronota logNota = new Logronota();
                        logNota = estudianteEjB.findNotaEstudiante(aux, es);
                        if (logNota != null) {
                            Double porcentaje = new Double(aux.getPorcentaje() / 100.0);
                            BigDecimal auxporcentaje = new BigDecimal(porcentaje);
                            BigDecimal auxnota = logNota.getNota().multiply(auxporcentaje);
                            nota = nota.add(auxnota);
                            //System.out.println("NOTAS***"+auxnota+"OTRA"+nota+"PORCENTJE"+p);
                        }else{
                            Logronota logNotaNew = new Logronota();
                            Estadologronota estLogroNota = new Estadologronota();
                            estLogroNota = estadologroEjb.find(1);
                            BigDecimal notaLogro = new BigDecimal(0);
                            logNotaNew.setLogroId(aux);
                            logNotaNew.setEstado(estLogroNota);
                            logNotaNew.setEstudianteId(es);
                            logNotaNew.setNota(notaLogro);
                            logroNotaEjb.create(logNotaNew);
                        }
                    }
                    notaEst = estudianteEjB.findNotaEst(contenido, es);
                    if (notaEst == null) {
                        Nota notaEstAux = new Nota();
                        notaEstAux.setContenidotematicoId(contenido);
                        notaEstAux.setEstudianteId(es);
                        notaEstAux.setValor(nota);
                        notaEstEJB.create(notaEstAux); 
                    } else {
                        notaEst.setValor(nota);
                        notaEstEJB.edit(notaEst);
                    }
                    actualizarNota(contenido, es);
                }
                tx.commit();
                RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').hide()");
                updateColumns();
                cargarContenido();
                this.logros = logroEjb.getContenidoByAll(contenido);
            }
        } catch (Exception e) {
            System.out.println("FALLO INGRESO LOGRO");
        }

    }

    static public class ColumnModel implements Serializable {

        private String header;
        private Integer property;

        public ColumnModel(String header, Integer property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public Integer getProperty() {
            return property;
        }
    }

    private void createDynamicColumns() {
        columns = new ArrayList<ColumnModel>();
        List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
        System.out.println("CONTENIDOOOOOO "+contenido);
        for (Logro aux : logrosaux) {
            System.out.println("QAAAAAAAAAAAAA" + aux.getTitulo().toUpperCase()+"  ffffFFFFF"+ aux.getLogroId());
            columns.add(new ColumnModel(aux.getTitulo().toUpperCase(), aux.getLogroId()));
        }

    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
        //table.setValueExpression("sortBy", null);
        //update columns
        createDynamicColumns();
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            //editable = false;
            Anlectivo auxEscolar = anlectivoEjb.getIniciado();
            BigDecimal notaLogroValidacion = new BigDecimal(event.getNewValue().toString());
            BigDecimal min = new BigDecimal(auxEscolar.getConfiguracionId().getEscalaId().getMin());
            BigDecimal max = new BigDecimal(auxEscolar.getConfiguracionId().getEscalaId().getMax());
            System.out.println("MIN"+min+" MAX"+max+"Nota"+notaLogroValidacion);
            if(notaLogroValidacion.compareTo(max)>0 || notaLogroValidacion.compareTo(min)<0){
                System.out.println("FUNCIONOOOOO");
                return;
            }
            FaceletContext faceletContext = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            DataTable dataTable = (DataTable) event.getSource();
            Integer logroId = Integer.parseInt(event.getColumn().getHeaderText().trim());
            Logro log = new Logro();
            log = logroEjb.find(logroId);
            Estudiante es = (Estudiante) dataTable.getRowData();
            Logronota logNota = new Logronota();
            logNota = estudianteEjB.findNotaEstudiante(log, es);
            System.out.println("RESPUESTA****" + logNota);
            if (logNota == null) {
                Logronota logNotaNew = new Logronota();
                Estadologronota estLogroNota = new Estadologronota();
                estLogroNota = estadologroEjb.find(1);
                BigDecimal notaLogro = new BigDecimal(event.getNewValue().toString());
                Object oldValue = event.getOldValue();
                Object newValue = event.getNewValue();
                System.out.println("OLD" + oldValue + "NEW" + newValue + "COSO" + logroId);
                logNotaNew.setLogroId(log);
                logNotaNew.setEstado(estLogroNota);
                logNotaNew.setEstudianteId(es);
                logNotaNew.setNota(notaLogro);
                logroNotaEjb.create(logNotaNew);
            } else {
                BigDecimal notaLogro = new BigDecimal(event.getNewValue().toString());
                System.out.println("LOGRO NOTA¨¨¨¨***" + logNota + "NEW" + notaLogro);
                logNota.setNota(notaLogro);
                logroNotaEjb.edit(logNota);
            }
            actualizarNota(contenido, es);
            cargarContenido();
            updateColumns();
            this.editable = true;
            this.notaxxLogro = "";
            //RequestContext.getCurrentInstance().update("testContainer");
            //frmMateriasCiclos
            //estudiantes = estudianteEjB.findByCurso(cursoSelected);
            //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
            //createDynamicColumns();
        } catch (Exception e) {
            System.out.println("EERROORR" + e.toString());
            cargarContenido();
            updateColumns();
        }


    }

    public BigDecimal getNotaEstudiante(Estudiante es, Integer logroId) {
        //System.out.println("INGRESA GET NOTA ******" + es + "LOGRO" + logroId);
        Logro logro = new Logro();
        logro = logroEjb.find(logroId);
        BigDecimal nota = new BigDecimal(0);
        Logronota logNota = new Logronota();
        logNota = estudianteEjB.findNotaEstudiante(logro, es);
        if (logNota != null) {
            nota = logNota.getNota();
        }
        return nota;
    }

    public BigDecimal getNotaEst(Estudiante es) {

        BigDecimal nota = new BigDecimal(0);
        Nota notaEst = new Nota();
        notaEst = estudianteEjB.findNotaEst(contenido, es);
        if (notaEst != null) {
            nota = notaEst.getValor();
        }
        System.out.println("***TABLA**" + nota);
        return nota.setScale(1, RoundingMode.HALF_EVEN);
    }

    private void actualizarNota(Contenidotematico contenido, Estudiante es) {
        BigDecimal nota = new BigDecimal(0);
        Nota notaEst = new Nota();
        Notafinal notaf = new Notafinal();
        List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
        for (Logro aux : logrosaux) {
            //System.out.println("ENTRA LOGROS***");
            Logronota logNota = new Logronota();
            logNota = estudianteEjB.findNotaEstudiante(aux, es);
            if (logNota != null) {
                Double porcentaje = new Double(aux.getPorcentaje() / 100.0);
                BigDecimal auxporcentaje = new BigDecimal(porcentaje);
                BigDecimal auxnota = logNota.getNota().multiply(auxporcentaje);
                nota = nota.add(auxnota);
                //System.out.println("NOTAS***"+auxnota+"OTRA"+nota+"PORCENTJE"+p);
            }
        }
        Date fecha = new Date();
        System.out.println("NoTA ESTTTTT"+nota);
        nota = nota.setScale(1, RoundingMode.HALF_UP);
        System.out.println("NoTA ESTTTTT22222"+nota);
        notaEst = estudianteEjB.findNotaEst(contenido, es);
        if (notaEst == null) {
            Nota notaEstAux = new Nota();
            notaEstAux.setContenidotematicoId(contenido);
            notaEstAux.setEstudianteId(es);
            notaEstAux.setValor(nota);
            notaEstAux.setFechamodificacion(fecha);
            System.out.println("MM QUE SERA NOTA DIFERENTE"+notaEstAux.getValor());
            notaEstEJB.create(notaEstAux);
        } else {
            notaEst.setFechamodificacion(fecha);
            notaEst.setValor(nota);
            System.out.println("MM QUE SERA NOTA DIFERENTE"+notaEst.getValor());
            notaEstEJB.edit(notaEst);
        }
        Anlectivo aEscolarAux = anlectivoEjb.getIniciado();
        Asignaturaciclo asc = new Asignaturaciclo();
        asc = asignaturaCicloEjb.find(contenido.getAsignaturacicloId().getAsignaturacicloId());
        notaf = notafEjb.findNotaFinalActual(asc, es,aEscolarAux);
        Anlectivo anlectivoAux = new Anlectivo();
        cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
        anlectivoAux = anlectivoEjb.find(cursoSelected.getAnlectivoId().getAnlectivoId());
        //System.out.println("MM QUE SERA::::"+notaf.toString()+"aa a"+asc.toString()+"bbb"+es.toString());
        Double notaFinal = notaEstEJB.getNotaFinal(anlectivoAux, es, cursoSelected,contenido.getAsignaturacicloId());        
        BigDecimal notaFinalDef = new BigDecimal(notaFinal.toString());
        System.out.println("NOTA FINAL OK"+notaFinalDef);
        notaFinalDef = notaFinalDef.setScale(1, RoundingMode.HALF_UP);
        System.out.println("NOTA FINAL OK2222"+notaFinalDef);
        if(notaf == null){
            Notafinal notaFinalAux = new Notafinal();
            notaFinalAux.setAsignaturacicloId(asc);
            notaFinalAux.setEstudianteId(es);
            notaFinalAux.setProfesorId(profesor);
            notaFinalAux.setValor(notaFinalDef);
            notaFinalAux.setFechamodificacion(fecha);
            notaFinalAux.setAnlectivoId(anlectivoEjb.getIniciado());
            notaFinalAux.setRecuperacion("NO");
            System.out.println("NOTA FINAL OKKaKKK"+notaFinalAux.getValor());
            notafEjb.create(notaFinalAux);
        }else{
            notaf.setFechamodificacion(fecha);
            notaf.setProfesorId(profesor);
            notaf.setValor(notaFinalDef);
            notafEjb.edit(notaf);
        }
    }

    public void eliminarLogro(Logro logroId) {
        try {
            //aqui cambiar contenido a estado pendiente
            logroEjb.remove(logroId);
            for (Estudiante es : estudiantes) {
                actualizarNota(contenido, es);
            }
            RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').hide()");
            updateColumns();
            this.contenidoNotas = false;
            cargarContenido();
            this.logros = logroEjb.getContenidoByAll(contenido);
        } catch (Exception e) {
            System.out.println("ERROR ELIMINAR LOGRO");
        }
    }
    public void cargarObservacion(Integer estId){
        Estudiante estAux = estudianteEjB.find(estId);
        Nota notaEst = new Nota();
        notaEst = estudianteEjB.findNotaEst(contenido, estAux);
        System.out.println("insertar observacion"+estAux+"NOTA EST"+notaEst);
        this.estActual = estAux;
        this.notaEstudianteActual = notaEst;
        this.observaciones = notaEst.getObservaciones();
        try {
            RequestContext.getCurrentInstance().update("frmObservaciones:panelObservaciones");
            RequestContext.getCurrentInstance().execute("PF('dialogoObservaciones').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void insertarObservacion(Integer estId){
        Estudiante estAux = estudianteEjB.find(estId);
        Nota notaEst = new Nota();
        notaEst = estudianteEjB.findNotaEst(contenido, estAux);
        System.out.println("insertar observacion"+estAux+"NOTA EST"+notaEst);
        this.estActual = estAux;
        this.notaEstudianteActual = notaEst;
        this.observaciones = notaEst.getObservaciones();
        try {
            RequestContext.getCurrentInstance().update("frmCrearObservaciones:panelCrearObservaciones");
            RequestContext.getCurrentInstance().execute("PF('dialogoCrearObservaciones').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void insertarObservacionDB(){
        System.out.println("insertar observacionDB"+estActual+"NOTA ESTDB"+notaEstudianteActual+"OBSERVACION"+this.observaciones);
        this.notaEstudianteActual.setObservaciones(observaciones);
        this.notaEstEJB.edit(notaEstudianteActual);
    }
    public void onRowCancel(RowEditEvent event) {
        //FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("Edito");
    }
    public void onRowEdit(RowEditEvent event) throws IllegalStateException, SecurityException, SystemException {
        EstudianteNotas estNotaAux = (EstudianteNotas) event.getObject();
        Estudiante es = estudianteEjB.find(estNotaAux.getId());
        try {
            
            System.out.println("Edito"+estNotaAux.getNombre());
            
            tx.begin();
            for (Map.Entry<Integer, Object> entry : estNotaAux.getNotasLogros().entrySet()) {
                Integer key = entry.getKey();
                //BigDecimal value = entry.getValue();
                System.out.println("Edito 22"+key+"   "+entry.getValue());
                //editable = false;
                Anlectivo auxEscolar = anlectivoEjb.getIniciado();
                BigDecimal notaLogroValidacion = new BigDecimal(entry.getValue().toString());
                BigDecimal min = new BigDecimal(auxEscolar.getConfiguracionId().getEscalaId().getMin());
                BigDecimal max = new BigDecimal(auxEscolar.getConfiguracionId().getEscalaId().getMax());
                //System.out.println("MIN"+min+" MAX"+max+"Nota"+notaLogroValidacion);
                if(notaLogroValidacion.compareTo(max)>0 || notaLogroValidacion.compareTo(min)<0){
                    System.out.println("FUNCIONOOOOO");
                    tx.rollback();
                    cargarContenido();
                    return;
                }
                Integer logroId = key;
                Logro log = new Logro();
                log = logroEjb.find(logroId);
               
                Logronota logNota = new Logronota();
                logNota = estudianteEjB.findNotaEstudiante(log, es);
                System.out.println("RESPUESTA****" + logNota);
                Date fecha = new Date();
                if (logNota == null) {
                    Logronota logNotaNew = new Logronota();
                    Estadologronota estLogroNota = new Estadologronota();
                    estLogroNota = estadologroEjb.find(1);
                    //BigDecimal notaLogro = new BigDecimal(entry.getValue().toString());
                    logNotaNew.setLogroId(log);
                    logNotaNew.setEstado(estLogroNota);
                    logNotaNew.setEstudianteId(es);
                    logNotaNew.setNota(notaLogroValidacion);
                    logNotaNew.setFechamodificacion(fecha);
                    logroNotaEjb.create(logNotaNew);
                } else {
                    //BigDecimal notaLogro = new BigDecimal(entry.getValue().toString());
                    //System.out.println("LOGRO NOTA¨¨¨¨***" + logNota + "NEW" + notaLogro);
                    logNota.setFechamodificacion(fecha);
                    logNota.setNota(notaLogroValidacion);
                    logroNotaEjb.edit(logNota);

                }
            }          
            actualizarNota(contenido, es);
            cargarContenido();
            tx.commit();
        }
        catch (Exception e) {
            System.out.println("EERROORR" + e.toString());
            e.printStackTrace();
            //actualizarNota(contenido, es);
            //cargarEstudiantes();
            tx.rollback();
            cargarContenido();
            //updateColumns();
        }
    }
    public void prueba(){
        System.out.println("PRUEBA");
        this.editable = false;
    }
    public void pruebaac(){
        System.out.println("PRUEBADC");
        this.editable = true;
    }
    public void generarReporte(){
        try {
            this.reporte = new ArrayList<ReporteNotasProfesor>();
            this.cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
            ReporteNotasProfesor rpt = new ReporteNotasProfesor();
            rpt.setProfesor(profesor);
            rpt.setCurso(cursoSelected);
            rpt.setAsignatura(asignaturaSelected);
            rpt.setPeriodo(periodo);
            rpt.setEstudiantes(estudiantesN);
            reporte.add(rpt);
            init();
            cursoSelected.getCicloId().getNumero();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ooooOOOO"+e.toString());
        }
    }
    public void init() throws JRException{
        System.out.println("entro init");
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/notasProfesor.jasper");
        /*Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("ciclo", "3");
        parametros.put("año", "2016");
        parametros.put("curso", "3-1"); */
        jasperPrint=JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }
    public void pdf() throws JRException, IOException{
        generarReporte();
        HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
        ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete(); 
    } 
    public void pruebafinal(){
        Anlectivo anlectivoAux = anlectivoEjb.getIniciado();
        Estudiante es = estudianteEjB.find(1);
        Double notaFinal = notaEstEJB.getNotaFinal(anlectivoAux, es, cursoSelected,contenido.getAsignaturacicloId());        
        BigDecimal notaFinalDef = new BigDecimal(notaFinal.toString());
        System.out.println("NOTA FINAL OK"+notaFinalDef);
    }
    public void cargarActualizar(Logro logroId){
        try {
            this.logro = this.logroEjb.find(logroId.getLogroId());
            RequestContext.getCurrentInstance().update("frmEditarLogro:panelEditarLogro");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarLogro').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }
    public void actualizarLogro(){
        try {
            Integer suma = 0;
            if(this.logro.getPorcentaje()<0 || this.logro.getPorcentaje()>100){
                System.out.println("LOGRO FUERA DE RAMGO");
                return;
            }
            System.out.println("contenido"+contenido+"    "+contenido.getLogroList());//traer por sql
            for(Logro logAuxSum:logroEjb.getByContenido(contenido)){
                System.out.println("SUMARRRR"+logAuxSum.getLogroId()+"el otro"+logro.getLogroId());
                if(logAuxSum.getLogroId()==this.logro.getLogroId()){
                    suma += this.logro.getPorcentaje();
                }else{
                    suma += logAuxSum.getPorcentaje();
                }
            }
            //suma += this.logro.getPorcentaje();
            System.out.println("SUMA FINAL"+suma);
            if(suma>100){
                System.out.println("SE PASA DE 100");
                return;
            }
            if(suma==100){
                this.contenidoNotas=true;
            }
            this.logroEjb.edit(logro);
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarLogro').hide()");
            updateColumns();
            cargarContenido();
            this.logros = logroEjb.getContenidoByAll(contenido);

        } catch (Exception e) {
            System.out.println("FALLO INGRESO LOGRO");
        }
    }
}
