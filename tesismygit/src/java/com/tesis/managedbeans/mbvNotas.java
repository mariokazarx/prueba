/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadologronotaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.LogroFacade;
import com.tesis.beans.LogronotaFacade;
import com.tesis.beans.NotaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.ProfesorFacade;
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
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvNotas implements Serializable {

    private String notaxxLogro;
    private Profesor profesor;
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

    public mbvNotas() {
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
        this.logros = new ArrayList<Logro>();
        this.logro = new Logro();
        this.asignaturaSelected = new Asignatura();
        this.contenido = new Contenidotematico();
        this.cursoSelected = new Curso();
        this.profesor = profesorEjb.find(1);
        this.periodo = periodoEjb.find(1);
        this.cursos = cursoEjb.findCursosProfeso(profesor, periodo);
        this.estudiantes = new ArrayList<Estudiante>();
        this.notaxxLogro = new String();
        //cargarModelos();
        //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        //createDynamicColumns();
    }

    public void cargarAsignaturas() {
        asignaturas = new ArrayList<Asignatura>();
        asignaturasCiclo = asignaturaCicloEjb.asignaturasProfesor(profesor, cursoSelected, periodo);
        for (Asignaturaciclo asc : asignaturasCiclo) {
            Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
            asignaturas.add(as);
        }

    }

    public void cargarContenido() {
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(cur.getCicloId(), asignaturaSelected);
        this.contenido = contenidoEjb.getContenidoByAll(profesor, cursoSelected, asg, periodo);
        System.out.println("AAAAA" + cursoSelected + "Periodo" + periodo + "Profesor" + profesor + "asignaturaciclo" + asg + "contenido" + contenido);
        this.logros = logroEjb.getContenidoByAll(contenido);
        estudiantes = estudianteEjB.findByCurso(cursoSelected);
        notaxxLogro = "";
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
        createDynamicColumns();
    }

    public void newLogro() {
        try {
            RequestContext.getCurrentInstance().update("frmCrearLogro:panelCrearLogro");
            RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void insertarLogro() {
        try {
            if (contenido != null) {
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
                }
                RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').hide()");
                updateColumns();
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
        for (Logro aux : logrosaux) {
            System.out.println("QAAAAAAAAAAAAA" + aux.getTitulo().toUpperCase());
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
            //RequestContext.getCurrentInstance().update("frmMateriasCiclos");
            //estudiantes = estudianteEjB.findByCurso(cursoSelected);
            //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmMateriasCiclos:tablanotas");
            //createDynamicColumns();
        } catch (Exception e) {
            System.out.println("EERROORR" + e.toString());
        }


    }

    public BigDecimal getNotaEstudiante(Estudiante es, Integer logroId) {
        System.out.println("INGRESA GET NOTA ******" + es + "LOGRO" + logroId);
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
    }

    public void eliminarLogro(Logro logroId) {
        try {
            logroEjb.remove(logroId);
            for (Estudiante es : estudiantes) {
                actualizarNota(contenido, es);
            }
            RequestContext.getCurrentInstance().execute("PF('dialogoCrearLogro').hide()");
            updateColumns();
            this.logros = logroEjb.getContenidoByAll(contenido);
        } catch (Exception e) {
            System.out.println("ERROR ELIMINAR LOGRO");
        }
    }
}
