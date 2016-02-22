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
import com.tesis.clases.ReporteBoletin;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
import com.tesis.entity.Periodo;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class mbvReporteConsolidadoPeriodo {

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
    
    public mbvReporteConsolidadoPeriodo() {
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
    public void prueba(){
        List<Object> estudiantesPromedio= new ArrayList<Object>();
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        Periodo periodo = periodoEjb.find(15);
        estudiantesPromedio = estudianteEjB.getFinalPeriodo(cur, periodo);
        for(Object p:estudiantesPromedio){
            Object[] row = (Object[]) p;
            for (int i = 0; i < row.length; i++) {
                if(row[i] instanceof Estudiante){
                    Estudiante est = (Estudiante) row[i];
                    System.out.println("OBJ22 "+est.getNombre());
                }
                System.out.println("OBJ22 "+row[i]);
            }
        }
    }
    
    public void imprimir() {
        System.out.println("ENTRO 1 ");
        List<Object> estudiantesPromedio= new ArrayList<Object>();
        List<Curso> cursos22 = new ArrayList<Curso>();
        Document document = new Document(PageSize.LETTER.rotate());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.out.println("ENTRO 2 ");
            PdfWriter.getInstance(document, baos);
            //METADATA
            document.open();
            for(Curso curAux:cursos){
                Curso cur = cursoEjb.find(curAux.getCursoId());
                Periodo periodo = periodoEjb.find(15);
                estudiantesPromedio = estudianteEjB.getFinalPeriodo(cur, periodo);
            
             
            URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            Image image = Image.getInstance(url);
            image.scaleAbsolute(100,100);        
            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(80);
            table2.setWidths(new int[]{1, 4});
            PdfPCell cell = new PdfPCell(image, true);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setRowspan(2);
            table2.addCell(cell);
            PdfPCell cell2 = new PdfPCell();
            Paragraph par = new Paragraph("CENTRO EDUCATIVO  “ANTONIO RICAURTE” ",FontFactory.getFont("arial",14,Font.BOLD));
            par.setAlignment(Element.ALIGN_CENTER);
            cell2.addElement(par);
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            Paragraph par3 = new Paragraph("Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                    + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 "
                    ,FontFactory.getFont("arial",9,Font.NORMAL));
            par3.setAlignment(Element.ALIGN_CENTER);
            cell3.addElement(par3);
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            cell3.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell3);
            document.add(table2);
            //document.add(image); 
            document.add(new Paragraph("ESTUDIANTES INSCRITOS \n"));     
            DateFormat formatter= new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss:");
            Date currentDate = new Date();
            String date = formatter.format(currentDate);
            document.add(new Paragraph("Fecha Generado: "+date)); 
            document.add(new Paragraph("\n"));
            List<Contenidotematico> contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
            int tam = 2+contenidos.size();
            PdfPTable table = new PdfPTable(2+contenidos.size());
            table.setWidthPercentage(100);      
            float[] ft = new float[tam];
            for(int i=0;i<tam;i++){
                System.out.println(i);
                if(i==0){
                    ft[i]=2f; 
                }else if(i==tam-1){
                    ft[i] = 0.5f;
                }else{
                    ft[i] = 1f;
                }         
            }
            table.setTotalWidth(ft);
            table.addCell("ESTUDIANTE");
            for(Contenidotematico con : contenidos){
                table.addCell(con.getAsignaturacicloId().getAsignaturaId().getNombre());
            }
            table.addCell("Promedio");
            for(Object p:estudiantesPromedio){
                Object[] row = (Object[]) p;
                for (int i = 0; i < row.length; i++) {
                    if(row[i] instanceof Estudiante){
                        Estudiante est = (Estudiante) row[i];
                        table.addCell(est.getApellido()+" "+est.getNombre());
                        for(Contenidotematico cont:contenidos){
                            table.addCell(getNotaEst(est,cont).toString());                        
                        }
                    }
                    if(row[i] instanceof Double){
                        BigDecimal nota = new BigDecimal(row[i].toString());
                        nota = nota.setScale(1, RoundingMode.HALF_EVEN);
                        table.addCell(nota.toString());
                    }        
                }
            }
            document.add(table);
            document.newPage();
            }
            } catch (Exception ex) {
                System.out.println("ENTRO 3 ");
                System.out.println("Error " + ex.getMessage());
            }
            
            document.close();
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                System.out.println("ENTRO 4 ");
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-disposition", "attachment; filename=report.pdf"); 
                hsr.setContentLength(baos.size());
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
            }
       }
}
