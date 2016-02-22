/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.LogroFacade;
import com.tesis.beans.LogronotaFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.clases.ContenidoBoletin;
import com.tesis.clases.EstudianteNotas;
import com.tesis.clases.MatriculaReporte;
import com.tesis.clases.ReporteBoletin;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Matricula;
import com.tesis.entity.Nota;
import com.tesis.entity.Periodo;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteBoletinesPeriodo {
    
    private List<Curso> cursos;
    private Curso cursoSelected;
    //private List<MatriculaReporte> reporte;
    private List<ReporteBoletin> reporte;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private CursoFacade cursoEjb;//
    @EJB
    private EstadoMatriculaFacade estadomatriculaEjB;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEjB;
    @EJB
    private LogroFacade logroEjb;
    @EJB
    private LogronotaFacade logroNotaEjb;
    @EJB
    private ContenidotematicoFacade contenidoEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    private JasperPrint jasperPrint;
    /**
     * Creates a new instance of mbvReporteMatriculas
     */
    
    public mbvReporteBoletinesPeriodo() {
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
    
    @PostConstruct()
    public void inicio() {
        //verificar permisos
        //verificar año iniciado
        //verificar cursos
        this.cursos = new ArrayList<Curso>();
        this.reporte = new ArrayList<ReporteBoletin>();
        this.cursoSelected = new Curso();
        this.cursos.clear();
        Anlectivo auxEscolar = aEscolarEjb.getIniciado();
        if(auxEscolar!=null){
            //hay año iniciado
            if(auxEscolar.getCursoList().isEmpty()){
                //no hay cursos activos
            }else{
                this.cursos = auxEscolar.getCursoList();
            }
        }
    }
    public void init() throws JRException{
        System.out.println("entro init"+ reporte.get(0).getContenidos().size());
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/boletinPeriodo.jasper");//boletinPeriodo.jasper
        /*Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("ciclo", "3");
        parametros.put("año", "2016");
        parametros.put("curso", "3-1"); */
        jasperPrint=JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }
    public void pdf() throws JRException, IOException{
        generar();
        HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
        ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        FacesContext.getCurrentInstance().responseComplete(); 
    }
    public void generar() {
        try {
            reporte.clear();
            Curso cur = cursoEjb.find(cursoSelected.getCursoId());
            List<Contenidotematico> contenidos = new ArrayList<Contenidotematico>();
            List<Estudiante> estudiantes = new ArrayList<Estudiante>();
            Periodo periodo = periodoEjb.find(15);
            contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
            estudiantes = estudianteEjB.findByCurso(cursoSelected);
            for(Estudiante est:estudiantes){
                ReporteBoletin rpt = new ReporteBoletin();
                rpt.setCurso(cur);
                rpt.setEstudiante(est);
                rpt.setPeriodo(periodo);
                List<ContenidoBoletin> conrpt = new ArrayList<ContenidoBoletin>();
                for(Contenidotematico cont:contenidos){
                    ContenidoBoletin conBoletin = new ContenidoBoletin();
                    conBoletin.setContenido(cont);
                    conBoletin.setNota(BigDecimal.ZERO);
                    conBoletin.setNota(getNotaEst(est,cont));
                    conBoletin.setObservaciones("falata observacion");
                    List<Logro> logrosaux = logroEjb.getContenidoByAll(cont);
                    List<Logronota> logrosNotaAux = new ArrayList<Logronota>();
                    for (Logro aux : logrosaux) {
                        logrosNotaAux.add(logroNotaEjb.getByLogroestudiante(est, aux));    
                    }
                    conrpt.add(conBoletin);
                    conBoletin.setLogros(logrosNotaAux);
                }
                System.out.println("CONTENIDO"+conrpt.size());
                rpt.setContenidos(conrpt);
                reporte.add(rpt);
            }
            init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR Datos"+e.toString());
        }
        
    }
    
    private BigDecimal getNotaEst(Estudiante es, Contenidotematico contenido) {

        BigDecimal nota = new BigDecimal(0);
        Nota notaEst = new Nota();
        notaEst = estudianteEjB.findNotaEst(contenido, es);
        if (notaEst != null) {
            nota = notaEst.getValor();
        }
        return nota.setScale(1, RoundingMode.HALF_EVEN);
    }
}
