/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.NotaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Escala;
import com.tesis.entity.Periodo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvEstadisticas implements Serializable{
    private static final long serialVersionUID = -3923990097750323603L;

    private HorizontalBarChartModel barModel;
    private PieChartModel pieModel1;
    private Anlectivo anlectivoSelected;
    private List<Anlectivo> anlectivos;
    private boolean mostrarContenido;
    private boolean mostrarEstAños;
    private boolean mostrarEstPeriodo;
    private Periodo periodoSelected;
    private List<Periodo> periodos;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private EscalaFacade escalaEjb;
    @EJB
    private NotaFacade notaEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;

    public mbvEstadisticas() {
    }

    public HorizontalBarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(HorizontalBarChartModel barModel) {
        this.barModel = barModel;
    }

    public boolean isMostrarEstPeriodo() {
        return mostrarEstPeriodo;
    }

    public void setMostrarEstPeriodo(boolean mostrarEstPeriodo) {
        this.mostrarEstPeriodo = mostrarEstPeriodo;
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

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
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

    public boolean isMostrarContenido() {
        return mostrarContenido;
    }

    public void setMostrarContenido(boolean mostrarContenido) {
        this.mostrarContenido = mostrarContenido;
    }

    public boolean isMostrarEstAños() {
        return mostrarEstAños;
    }

    public void setMostrarEstAños(boolean mostrarEstAños) {
        this.mostrarEstAños = mostrarEstAños;
    }

    @PostConstruct()
    public void inicio() {
        this.anlectivos = anlectivoEjb.getTerminados();
        this.anlectivoSelected = new Anlectivo();
        this.periodoSelected = new Periodo();
        this.periodos = new ArrayList<Periodo>();
        this.mostrarEstPeriodo = false;
    }

    public void cargarGrafico() {
        if (anlectivoSelected.getAnlectivoId() != null) {
            this.periodos = periodoEjb.getPeriodosByAnio(anlectivoSelected);
            anlectivoSelected = anlectivoEjb.find(anlectivoSelected.getAnlectivoId());
            this.mostrarEstAños = true;
            pieModel1 = new PieChartModel();

            pieModel1.set("Aprobados", matriculaEjb.getAprobadosMatriucula(anlectivoSelected));
            pieModel1.set("Reprobados", matriculaEjb.getReprobadosMatriucula(anlectivoSelected));

            pieModel1.setTitle("Año " + anlectivoSelected.getAnio());
            pieModel1.setShowDataLabels(true);
            pieModel1.setLegendPosition("w");
        } else {
            this.mostrarEstAños = false;
            this.mostrarEstPeriodo = false;
        }

    }

    public void cargarGraficoPeriodo() {
        if (periodoSelected.getPeriodoId() != null) {
            barModel = new HorizontalBarChartModel();
            Escala escala = escalaEjb.find(anlectivoSelected.getConfiguracionId().getEscalaId().getEscalaId());
            BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
            ChartSeries aprobados = new ChartSeries();
            aprobados.setLabel("Apobados");
            ChartSeries reprobados = new ChartSeries();
            reprobados.setLabel("Reprobados");


            List<Contenidotematico> contenidos = contenidoEjb.getByPeriodo(periodoSelected);
            for (Contenidotematico cont : contenidos) {
                Asignatura asg = asignaturaEjb.find(cont.getAsignaturacicloId().getAsignaturaId().getAsignaturaId());
                aprobados.set(asg.getNombre(), notaEjb.getAprobadosMateria(cont, max));
                reprobados.set(asg.getNombre(), notaEjb.getReprobadosMateria(cont, max));
            }
            barModel.addSeries(aprobados);
            barModel.addSeries(reprobados);
            barModel.setTitle("Estadistica por periodo");
            barModel.setLegendPosition("ne");

            Axis xAxis = barModel.getAxis(AxisType.Y);
            xAxis.setLabel("Asignaturas");

            Axis yAxis = barModel.getAxis(AxisType.X);
            yAxis.setLabel("Estudiantes");
            barModel.setShowDatatip(true);
            barModel.setStacked(true);
            yAxis.setMin(0);
            yAxis.setMax(30);
            mostrarEstPeriodo = true;
        } else {
            mostrarEstPeriodo = false;
        }
        //barModel = initBarModel();

        /*barModel.setTitle("Estadistica por periodo");
         barModel.setLegendPosition("ne");
         
         Axis xAxis = barModel.getAxis(AxisType.X);
         xAxis.setLabel("Asignaturas");
         
         Axis yAxis = barModel.getAxis(AxisType.Y);
         yAxis.setLabel("Estudiantes");
         yAxis.setMin(0);
         yAxis.setMax(200);*/
    }

    /*private void datos() {
        List<Matricula> matriculasAnio;
        matriculasAnio = matriculaEjb.getMatriculasAnio(anlectivoSelected);
        if (!matriculasAnio.isEmpty()) {
            for (Matricula maticula : matriculasAnio) {
                Estudiante est = estdudianteEjb.find(maticula.getEstudianteId().getEstudianteId());
                List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                asignaturas = asignaturaEjb.findByCiclo(ciclo);
                int perdidas = 0;
                for (Asignatura asignatura : asignaturas) {
                    Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                    Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                    BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
                    System.out.println("NMNMNM" + notafinalEst + "ESTUDIANTE " + est + "asignatura " + asg + "año escolar " + anlectivo + "MATRICULA " + maticula);
                    if (notafinalEst.getRecuperacion().compareTo("SI") == 0) {
                        //tiene recupeacion
                        Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
                        //queda sacar la nota y comprar para vere si pasa o no

                        if (notaRecuperacion.getValor().compareTo(max) >= 0) {
                            //paso
                        } else {
                            //no paso
                            perdidas++;
                        }

                    } else {
                        //no tiene recuperacion
                        if (notafinalEst.getValor().compareTo(max) >= 0) {
                            //paso
                        } else {
                            //no paso
                            perdidas++;
                        }
                    }
                }
                if (perdidas > criterio.getMinaprob()) {
                    //perdio año
                    est.setUltimoaprobado(ciclo.getNumero());
                    Aprobacion aprobacion = aprobacionEjb.find(3);
                    maticula.setAprobacionId(aprobacion);
                } else {
                    //gano el año
                    est.setUltimoaprobado(ciclo.getNumero() + 1);
                    Aprobacion aprobacion = aprobacionEjb.find(2);
                    maticula.setAprobacionId(aprobacion);
                    if (ciclo.getNumero() + 1 > 5) {//este es importante
                        EstadoEstudiante estadoEst = estadoEstuainteEjb.find(2);
                        est.setEstadoEstudianteId(estadoEst);
                    }
                }
                EstadoMatricula estadoMat = estadoMatriculaEjb.find(4);
                maticula.setEstadoMatriculaId(estadoMat);
                estdudianteEjb.edit(est);
                matriculaEjb.edit(maticula);
                System.out.println("estudiante " + est + "matricula " + maticula + "perdidos " + perdidas);
            }
        }
    }*/

    public void initRender() {
        if (!this.anlectivos.isEmpty()) {
            //this.mostarAños = true;
        } else {
            //this.mostrarBonton = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha terminado un año escolar"));
        }
    }
}
