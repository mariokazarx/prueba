/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
import com.tesis.beans.NotafinalrecuperacionFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.clases.BackgroundF;
import com.tesis.clases.EstudianteNotas;
import com.tesis.clases.ReporteNotasProfesor;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.EstadoPeriodo;
import com.tesis.entity.Estadologronota;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.omnifaces.util.Faces;
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
public class mbvRecuperacion {

    private List<ReporteNotasProfesor> reporte;
    private String notaxxLogro;
    private Map<String, Integer> datosNotas = new HashMap<String, Integer>();
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
    private List<mbvRecuperacion.ColumnModel> columns;
    private List<Estudiante> estudiantes;
    private List<Notafinal> notas;
    private BigDecimal notaRecuperacion;
    private Notafinal ntRecuperar;
    private Anlectivo anlectivoselected;
    private List<Anlectivo> anlectivos;
    private boolean contenidPrincipal;
    private boolean mostrarRecuperacion;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private NotafinalrecuperacionFacade notaFinalREjb;
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

    public mbvRecuperacion() {
    }

    public boolean isMostrarRecuperacion() {
        return mostrarRecuperacion;
    }

    public void setMostrarRecuperacion(boolean mostrarRecuperacion) {
        this.mostrarRecuperacion = mostrarRecuperacion;
    }

    public boolean isContenidPrincipal() {
        return contenidPrincipal;
    }

    public void setContenidPrincipal(boolean contenidPrincipal) {
        this.contenidPrincipal = contenidPrincipal;
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

    public BigDecimal getNotaRecuperacion() {
        return notaRecuperacion;
    }

    public void setNotaRecuperacion(BigDecimal notaRecuperacion) {
        this.notaRecuperacion = notaRecuperacion;
    }

    public List<Notafinal> getNotas() {
        return notas;
    }

    public void setNotas(List<Notafinal> notas) {
        this.notas = notas;
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

    public List<mbvRecuperacion.ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<mbvRecuperacion.ColumnModel> columns) {
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
        this.cursos = new ArrayList<Curso>();
        this.notaRecuperacion = new BigDecimal(0);
        this.estudiantesN = new ArrayList<EstudianteNotas>();
        this.editable = true;
        this.contenidoLogros = false;
        this.contenidoNotas = false;
        this.logros = new ArrayList<Logro>();
        this.logro = new Logro();
        this.asignaturaSelected = new Asignatura();
        this.contenido = new Contenidotematico();
        this.cursoSelected = new Curso();
        this.anlectivoselected = new Anlectivo();
        this.anlectivos = new ArrayList<Anlectivo>();
        this.contenidPrincipal = false;
        this.mostrarRecuperacion = false;
        if (profesor != null) {
            anlectivos = anlectivoEjb.getAñosEnUso();
            System.out.println("logueado como profesor");
            //anlectivo = anlectivoEjb.getIniciado();
            //año iniciado
            //traer el ultimo periodo dictado 

        }

        //this.profesor = profesorEjb.find(1); 
        //this.periodo = periodoEjb.find(11);

        //cargarModelos();
        //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        //createDynamicColumns();
    }

    public void cargarAsignaturas() {
        asignaturaSelected = new Asignatura();
        if (cursoSelected.getCursoId() != null) {
            asignaturas = new ArrayList<Asignatura>();
            contenidoLogros = false;
            contenidoNotas = false;
            this.asignaturaSelected = new Asignatura();
            this.mostrarRecuperacion = false;
            asignaturasCiclo = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected, periodo);
            if (!asignaturasCiclo.isEmpty()) {
                for (Asignaturaciclo asc : asignaturasCiclo) {
                    Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                    asignaturas.add(as);
                }
            } else {
                this.mostrarRecuperacion = false;
            }


        } else {
            this.asignaturas.clear();
            this.asignaturaSelected = new Asignatura();
            this.mostrarRecuperacion = false;
        }
    }

    public void cargarContenido() {
        if (asignaturaSelected.getAsignaturaId() != null) {
            Curso cur = cursoEjb.find(cursoSelected.getCursoId());
            Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignaturaSelected);
            createDynamicColumns();
            notas = notafEjb.findByRecuperacion(asg, profesor, anlectivo);
            if (!notas.isEmpty()) {
                for (Notafinal notaAux : notas) {
                    System.out.println("ESTUDIANTE " + notaAux.getEstudianteId().getNombre() + "NOTA " + notaAux.getValor());
                }
                this.mostrarRecuperacion = true;
            } else {
                this.mostrarRecuperacion = false;
            }
        } else {
            this.mostrarRecuperacion = false;
        }
    }

    private void cargarEstudiantes() {
        List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);

        estudiantesN.clear();
        System.out.println("CONTENIDOOOOOO " + estudiantesN.size());
        for (Estudiante est : estudiantes) {
            EstudianteNotas estNota = new EstudianteNotas();
            estNota.setId(est.getEstudianteId());
            estNota.setNombre(est.getNombre());
            estNota.setApellido(est.getApellido());
            estNota.setNota(getNotaEst(est));
            Map<Integer, Object> notasLog = new HashMap<Integer, Object>();
            List<Logronota> logrosNotaAux = new ArrayList<Logronota>();
            for (Logro aux : logrosaux) {
                logrosNotaAux.add(logroNotaEjb.getByLogroestudiante(est, aux));
                notasLog.put(aux.getLogroId(), getNotaEstudiante(est, aux.getLogroId()));
                System.out.println("CONTENIDOOOOOO 22222 " + getNotaEstudiante(est, aux.getLogroId()));
            }
            estNota.setLogros(logrosNotaAux);
            estNota.setNotasLogros(notasLog);

            estudiantesN.add(estNota);
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
        columns = new ArrayList<mbvRecuperacion.ColumnModel>();
        List<Periodo> periodosaux = periodoEjb.getPeriodosByAnio(anlectivo);
        for (Periodo aux : periodosaux) {
            columns.add(new mbvRecuperacion.ColumnModel("Periodo " + aux.getNumero(), aux.getPeriodoId()));
        }

    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
        //table.setValueExpression("sortBy", null);
        //update columns
        createDynamicColumns();
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

    public void prueba() {
        System.out.println("PRUEBA");
        this.editable = false;
    }

    public void pruebaac() {
        System.out.println("PRUEBADC");
        this.editable = true;
    }

    public void generarReporte() {
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
            System.out.println("ooooOOOO" + e.toString());
        }
    }

    public void init() throws JRException {
        System.out.println("entro init");
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/notasProfesor.jasper");
        /*Map<String, Object> parametros = new HashMap<String, Object>();
         parametros.put("ciclo", "3");
         parametros.put("año", "2016");
         parametros.put("curso", "3-1"); */
        jasperPrint = JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }

    public void pdf() throws JRException, IOException {
        generarReporte();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void pruebafinal() {
        Anlectivo anlectivoAux = anlectivoEjb.find(anlectivoselected.getAnlectivoId());
        Estudiante es = estudianteEjB.find(1);
        Double notaFinal = notaEstEJB.getNotaFinal(anlectivoAux, es, cursoSelected, contenido.getAsignaturacicloId());
        BigDecimal notaFinalDef = new BigDecimal(notaFinal);
        System.out.println("NOTA FINAL OK" + notaFinalDef);
    }

    public BigDecimal getNotaPeriodo(Estudiante estudiante, Integer periodoId) {
        BigDecimal nota = new BigDecimal(0);
        Periodo periodoParam = periodoEjb.find(periodoId);
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignaturaSelected);
        this.contenido = contenidoEjb.getContenidoByCambio(cur, asg, periodoParam);
        nota = notaEstEJB.getNotaFinalPeriodo(contenido, estudiante);
        return nota;
    }

    public BigDecimal getNotaRecuperacion(Integer notaFinalId) {
        Notafinal notaFinalAux = notafEjb.find(notaFinalId);
        BigDecimal nota = new BigDecimal(0);
        BigDecimal aux = notaFinalREjb.getNotaFinalRecuperacion(notaFinalAux);
        if (aux != null) {
            nota = aux;
        }
        return nota;
    }

    public void cargarRecuperacion(Integer notaFinalId) {
        try {
            Anlectivo auxEscolar = anlectivoEjb.find(anlectivoselected.getAnlectivoId());
            Notafinal notaFinalAux = notafEjb.find(notaFinalId);
            BigDecimal max = new BigDecimal(auxEscolar.getConfiguracionId().getEscalaId().getNotaminimaaprob());
            if (notaFinalAux.getValor().compareTo(max) >= 0) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "El estudiante no necesita recuperar"));
            } else {
                this.ntRecuperar = notaFinalAux;
                BigDecimal aux = notaFinalREjb.getNotaFinalRecuperacion(notaFinalAux);
                if (aux != null) {
                    this.notaRecuperacion = aux;
                }
                RequestContext.getCurrentInstance().update("frmRecuperarNota:panelRecuperarNota");
                RequestContext.getCurrentInstance().execute("PF('dialogoRecuperarNota').show()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void recuperarNota() throws IllegalStateException, IllegalStateException, SecurityException, SystemException {
        System.out.println("NOTA RECUPERACION " + notaRecuperacion);
        try {
            System.out.println("ENTRO 1");
            tx.begin();
            ntRecuperar.setRecuperacion("SI");
            notafEjb.edit(ntRecuperar);
            Notafinalrecuperacion notaFinalRecuAux = notaFinalREjb.getNotaFinalRecuperar(ntRecuperar);
            if (notaFinalRecuAux == null) {
                System.out.println("ENTRO 2" + ntRecuperar.getAsignaturacicloId());
                Asignaturaciclo asg = asignaturaCicloEjb.find(ntRecuperar.getAsignaturacicloId().getAsignaturacicloId());
                Estudiante est = estudianteEjB.find(ntRecuperar.getEstudianteId().getEstudianteId());
                System.out.println("ENTRO 5" + est + "vv " + asg);
                notaFinalRecuAux = new Notafinalrecuperacion();
                notaFinalRecuAux.setAsignaturacicloId(asg);
                notaFinalRecuAux.setEstudianteId(est);
                notaFinalRecuAux.setNotafinalId(ntRecuperar);
                notaFinalRecuAux.setProfesorId(profesor);
                notaFinalRecuAux.setValor(notaRecuperacion);
                notaFinalREjb.create(notaFinalRecuAux);
            } else {
                System.out.println("ENTRO 3");
                notaFinalRecuAux.setProfesorId(profesor);
                notaFinalRecuAux.setValor(notaRecuperacion);
                notaFinalREjb.edit(notaFinalRecuAux);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ENTRO 4" + e.getMessage());
            tx.rollback();
        }

    }

    public void cargarAnio() {
        try {
            System.out.println("ENTRO 1");
            if (anlectivoselected.getAnlectivoId() != null) {
                anlectivo = anlectivoEjb.find(anlectivoselected.getAnlectivoId());
                if (periodoEjb.getNumeroPeriodosAño(anlectivoselected) == periodoEjb.getNumeroPeriodosTerminadosAño(anlectivoselected)) {
                    System.out.println("ENTRO 2");
                    this.cursos.clear();
                    this.periodo = periodoEjb.getPeriodoMaxByAnio(anlectivo);
                    System.out.println("Periodo maximo " + periodo + "numero " + periodo.getNumero());
                    this.cursos = cursoEjb.findCursosProfeso(profesor, periodo);
                    this.estudiantes = new ArrayList<Estudiante>();
                    this.notaxxLogro = new String();
                    if (!this.cursos.isEmpty()) {
                        this.contenidPrincipal = true;
                    } else {
                        this.contenidPrincipal = false;
                    }
                } else {
                    this.contenidPrincipal = false;
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Se debe terminar todos los periodos del año escolar"));
                }
            } else {
                //this.asignaturas.clear();
                this.mostrarRecuperacion = false;
                this.asignaturaSelected = new Asignatura();
                this.contenidPrincipal = false;
                this.mostrarRecuperacion = false;
                this.cursoSelected = new Curso();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ENTRO 4");
        }

    }

    public void imprimir() {
        cursoSelected = cursoEjb.find(this.contenido.getCursoId().getCursoId());
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            BackgroundF event = new BackgroundF();
            writer.setPageEvent(event);
            document.open();
            URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            Image image = Image.getInstance(url);
            image.scaleAbsolute(100, 100);
            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(100);
            table2.setWidths(new int[]{1, 4});
            PdfPCell cell = new PdfPCell(image, true);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setRowspan(2);
            table2.addCell(cell);
            PdfPCell cell2 = new PdfPCell();
            Paragraph par = new Paragraph("CENTRO EDUCATIVO  “ANTONIO RICAURTE” ", FontFactory.getFont("arial", 14, Font.BOLD));
            par.setAlignment(Element.ALIGN_CENTER);
            cell2.addElement(par);
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            Paragraph par3 = new Paragraph(10, "Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                    + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 ", FontFactory.getFont("arial", 9, Font.NORMAL));
            par3.setAlignment(Element.ALIGN_CENTER);
            cell3.addElement(par3);
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            cell3.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell3);
            document.add(table2);
            // datos estudiante
            Paragraph parDescrip = new Paragraph("Reporte notas año escolar " + cursoSelected.getAnlectivoId().getAnio()
                    + " " + cursoSelected.getNombre() + " ciclo " + cursoSelected.getCicloId().getNumero(), FontFactory.getFont("arial", 12, Font.NORMAL));
            document.add(parDescrip);
            Paragraph parProf = new Paragraph("Profesor: " + profesor.getApellido() + " " + profesor.getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
            document.add(parProf);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(3 + columns.size());
            table.setWidthPercentage(100);
            int tam = 3 + columns.size();
            float[] ft = new float[tam];
            for (int i = 0; i < tam; i++) {
                if (i == 0) {
                    ft[i] = 80f;
                } else {
                    ft[i] = 15f;
                }
            }
            table.setWidths(ft);
            PdfPCell cellTituloEstudiante = new PdfPCell();
            Paragraph partituloEstudiante = new Paragraph("ESTUDIANTE ", FontFactory.getFont("arial", 12));
            partituloEstudiante.setAlignment(Element.ALIGN_CENTER);
            cellTituloEstudiante.addElement(partituloEstudiante);
            table.addCell(cellTituloEstudiante);
            for (mbvRecuperacion.ColumnModel col : columns) {
                table.addCell(col.getHeader());
            }
            PdfPCell cellTituloNotaF = new PdfPCell();
            Paragraph partituloNotaF = new Paragraph("Nota Final", FontFactory.getFont("arial", 12));
            partituloNotaF.setAlignment(Element.ALIGN_CENTER);
            cellTituloNotaF.addElement(partituloNotaF);
            table.addCell(cellTituloNotaF);
            PdfPCell cellTituloNotaR = new PdfPCell();
            Paragraph partituloNotaR = new Paragraph("Recuperacion ", FontFactory.getFont("arial", 12));
            partituloNotaR.setAlignment(Element.ALIGN_CENTER);
            cellTituloNotaR.addElement(partituloNotaR);
            table.addCell(cellTituloNotaR);
            List<Periodo> periodosReporte = periodoEjb.getPeriodosByAnio(anlectivoselected);
            System.out.println("AÑO ESCOLAR " + anlectivoselected + " PERIODOS " + periodosReporte);
            for (Notafinal ntf : notas) {
                table.addCell(ntf.getEstudianteId().getApellido() + " " + ntf.getEstudianteId().getNombre());

                for (Periodo aux : periodosReporte) {
                    table.addCell(getNotaPeriodo(ntf.getEstudianteId(), aux.getPeriodoId()).toString());
                }
                table.addCell(ntf.getValor().toString());

                if (getNotaRecuperacion(ntf.getNotafinalId()).toString().equals("0")) {
                    table.addCell("-");
                } else {
                    table.addCell(getNotaRecuperacion(ntf.getNotafinalId()).toString());
                }
            }
            document.add(table);
            Paragraph parNombre = new Paragraph(10, profesor.getNombre() + " " + profesor.getApellido(), FontFactory.getFont("arial", 12, Font.NORMAL));
            parNombre.setAlignment(Element.ALIGN_CENTER);
            parNombre.setSpacingBefore(60);
            document.add(parNombre);
            parNombre = new Paragraph(10, "C.C " + profesor.getCedula(), FontFactory.getFont("arial", 12, Font.NORMAL));
            parNombre.setAlignment(Element.ALIGN_CENTER);
            document.add(parNombre);

        } catch (Exception e) {
            System.out.println("ENTRO 3 ");
            System.out.println("Error " + e.getMessage());
        }
        document.close();
        FacesContext context = FacesContext.getCurrentInstance();
        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                System.out.println("ENTRO 4 ");
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.reset();
                Faces.sendFile(baos.toByteArray(), "reporteRecuperaciones.pdf", false);
                try {
                    System.out.println("ENTRO 5 ");
                    ServletOutputStream out = hsr.getOutputStream();
                    baos.writeTo(out);
                    out.flush();
                } catch (IOException ex) {
                    System.out.println("ENTRO 6 ");
                    System.out.println("Error:  " + ex.getMessage());
                }
                System.out.println("ENTRO 7 ");
                context.responseComplete();
            } catch (IOException ex) {
                Logger.getLogger(mbvConstanciaEstudios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
