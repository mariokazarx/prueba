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
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.clases.MatriculaReporte;
import com.tesis.clases.ReporteAsignacionAcademica;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Periodo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
public class mbvReporteAsignacion {

    /**
     * Creates a new instance of mbvReporteAsignacion
     */
    private List<Curso> cursos;
    private Curso cursoSelected;
    private List<ReporteAsignacionAcademica> reporte;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private EstadoMatriculaFacade estadomatriculaEjB;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstudianteFacade estudianteEJb;
    @EJB
    private PeriodoFacade periodoEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    private JasperPrint jasperPrint;
    
    public mbvReporteAsignacion() {
    }
    @PostConstruct()
    public void inicio() {
        //verificar permisos
        //verificar año iniciado
        //verificar cursos
        this.cursos = new ArrayList<Curso>();
        this.reporte = new ArrayList<ReporteAsignacionAcademica>();
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
    public void generar(){
        try {
            Curso curReporte = cursoEjb.find(9);
            Periodo periodo = periodoEJb.find(17);
            List<Contenidotematico> con = new ArrayList<Contenidotematico>();
            ReporteAsignacionAcademica mtrReporte = new ReporteAsignacionAcademica();
            mtrReporte.setAño(aEscolarEjb.getIniciado().getAnio());
            mtrReporte.setCurso(curReporte.getNombre());
            mtrReporte.setNumero(periodo.getNumero());
            System.out.println("MMMMMM"+mtrReporte.getNumero()+"   "+mtrReporte.getAño()+"    "+cursoSelected+"   "+matriculaEjb.matriculasCurso(cursoSelected));
            for(Contenidotematico conenido : contenidoEjb.getByPeriodoCurso(periodo,curReporte)){
                System.out.println("entro for"+conenido.getAsignaturacicloId().getAsignaturaId().getNombre());
                con.add(conenido);
            }
            mtrReporte.setContenidos(con);
            reporte.add(mtrReporte);
            init();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ooooOOOO"+e.toString());
        }
    }
    public void init() throws JRException{
        System.out.println("entro init");
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/AsignacionAcademica.jasper");
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
}
