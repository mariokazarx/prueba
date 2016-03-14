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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class mbvBoletinesAnteriores {

    private List<Curso> cursos;
    private Curso cursoSelected;
    //private List<MatriculaReporte> reporte;
    private List<ReporteBoletin> reporte;
    private boolean mostrarPrincipal;
    private boolean mostrarPeriodos;
    private Periodo periodoSelected;
    private List<Periodo> periodos;
    private boolean mostrarCursos;
    private boolean mostrarBoton;
    private Anlectivo anlectivoSelected;
    private List<Anlectivo> anlectivos;
    private Anlectivo aEscolar;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private PeriodoFacade periodoEJb;
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
    
    public mbvBoletinesAnteriores() {
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
    
    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarPeriodos() {
        return mostrarPeriodos;
    }

    public void setMostrarPeriodos(boolean mostrarPeriodos) {
        this.mostrarPeriodos = mostrarPeriodos;
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

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
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
        this.cursos = new ArrayList<Curso>();
        this.reporte = new ArrayList<ReporteBoletin>();
        this.anlectivos = new ArrayList<Anlectivo>();
        this.anlectivoSelected = new Anlectivo();
        this.aEscolar = new Anlectivo();
        this.periodos = new ArrayList<Periodo>();
        this.mostrarBoton = false;
        this.mostrarCursos = false;
        this.mostrarPeriodos = false;
        this.mostrarPrincipal = false;
        this.cursoSelected = new Curso();
        this.periodoSelected = new Periodo();
        this.cursos.clear();
        this.anlectivos = aEscolarEjb.getTerminados();
        if(!anlectivos.isEmpty()){
            this.mostrarPrincipal = true;
        }else{
            this.mostrarPrincipal = false;
        }
        /*Anlectivo auxEscolar = aEscolarEjb.getIniciado();
        if(auxEscolar!=null){
            //hay año iniciado
            this.periodos = periodoEjb.getPeriodosByAnioTerminados(auxEscolar);
            if(this.periodos.isEmpty()){
                //no hay cursos activos
            }else{
                this.mostrarPrincipal = true;
            }
        }else{
            
        }*/
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
            Periodo periodo = periodoEjb.find(1);
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
    public void imprimir() {
        Periodo periodo = periodoEjb.find(periodoSelected.getPeriodoId());
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        List<Contenidotematico> contenidos = new ArrayList<Contenidotematico>();
        List<Estudiante> estudiantes = new ArrayList<Estudiante>();
        contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
        estudiantes = estudianteEjB.findByCurso(cursoSelected);
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            for(Estudiante est:estudiantes){
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
                Image image = Image.getInstance(url);
                image.scaleAbsolute(100,100);        
                PdfPTable table2 = new PdfPTable(2);
                table2.setWidthPercentage(100);
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
                // datos estudiante
                Paragraph parEstudiante = new Paragraph("Boletin Periodo"+periodo.getNumero()+" Año escolar "+cur.getAnlectivoId().getAnio()
                    + " "+cur.getNombre()
                    ,FontFactory.getFont("arial",12,Font.NORMAL));
                document.add(parEstudiante);
                Paragraph parEstudiante2 = new Paragraph("Estudiante: "+ est.getApellido() + " " +est.getNombre()
                    ,FontFactory.getFont("arial",12,Font.NORMAL));
                document.add(parEstudiante2);
                document.add(new Paragraph("\n"));
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{100,8,8,8});
                for(Contenidotematico cont:contenidos){
                    PdfPCell cellProfesor = new PdfPCell();
                    cellProfesor.setColspan(4);
                    Paragraph parProfesor = new Paragraph("Profesor: "+cont.getProfesorId().getApellido()+" "+cont.getProfesorId().getNombre(),FontFactory.getFont("arial",12,Font.NORMAL));
                    //parProfesor.setAlignment(Element.ALIGN_CENTER);
                    cellProfesor.addElement(parProfesor);
                    cellProfesor.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellProfesor);
                    PdfPCell cellAsignatura = new PdfPCell();
                    cellAsignatura.setColspan(4);
                    Paragraph parAsignatura = new Paragraph("Asigantura: "+cont.getAsignaturacicloId().getAsignaturaId().getNombre(),FontFactory.getFont("arial",12,Font.NORMAL));
                    //parAsignatura.setAlignment(Element.ALIGN_CENTER);
                    cellAsignatura.addElement(parAsignatura);
                    cellAsignatura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellAsignatura);
                    PdfPCell cellTituloLogro = new PdfPCell();
                    Paragraph partituloLogro= new Paragraph("Logro",FontFactory.getFont("arial",12));
                    partituloLogro.setAlignment(Element.ALIGN_CENTER);
                    cellTituloLogro.addElement(partituloLogro);
                    //cellTituloLogro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellTituloLogro);
                    PdfPCell cellTituloPorcentaje = new PdfPCell();
                    Paragraph partituloPorcentaje = new Paragraph("%",FontFactory.getFont("arial",12));
                    partituloPorcentaje.setAlignment(Element.ALIGN_CENTER);
                    cellTituloPorcentaje.addElement(partituloPorcentaje);
                    //cellTituloPorcentaje.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellTituloPorcentaje);
                    PdfPCell cellTituloNotaL = new PdfPCell();
                    Paragraph partituloNotaL = new Paragraph("Nota Logro",FontFactory.getFont("arial",12));
                    partituloNotaL.setAlignment(Element.ALIGN_CENTER);
                    cellTituloNotaL.addElement(partituloNotaL);
                    //cellTituloNotaL.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellTituloNotaL);
                    PdfPCell cellTituloNotaF = new PdfPCell();
                    Paragraph partituloNotaF = new Paragraph("Nota Final",FontFactory.getFont("arial",12));
                    partituloNotaF.setAlignment(Element.ALIGN_CENTER);
                    cellTituloNotaF.addElement(partituloNotaF);
                    //cellTituloNotaF.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellTituloNotaF);
                    List<Logro> logrosaux = logroEjb.getContenidoByAll(cont);
                    boolean ban= false;
                    String observacion = "";
                    for (Logro aux : logrosaux) {
                        Logronota logN = logroNotaEjb.getByLogroestudiante(est, aux);
                        PdfPCell cellDescripcion = new PdfPCell();
                        Paragraph parDescripcion = new Paragraph(aux.getTitulo()+"\n"+aux.getDescripcion(),FontFactory.getFont("arial",12));
                        cellDescripcion.addElement(parDescripcion);
                        cellDescripcion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellDescripcion);
                        String porcentaje = Integer.toString(aux.getPorcentaje());
                        PdfPCell cellPorcentaje = new PdfPCell();
                        Paragraph parPorcentaje = new Paragraph(porcentaje,FontFactory.getFont("arial",12));
                        parPorcentaje.setAlignment(Element.ALIGN_CENTER);
                        cellPorcentaje.addElement(parPorcentaje);
                        cellPorcentaje.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellPorcentaje);
                        String notaLogro = logN.getNota().toString();
                        PdfPCell cellNotalog = new PdfPCell();
                        Paragraph parNotalog = new Paragraph(notaLogro,FontFactory.getFont("arial",12));
                        parNotalog.setAlignment(Element.ALIGN_CENTER);
                        cellNotalog.addElement(parNotalog);
                        cellNotalog.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellNotalog);
                        if(ban==false){
                            ban = true;
                            String notaF = getNotaEst(est,cont).toString();
                            PdfPCell cellNota = new PdfPCell();
                            cellNota.setRowspan(logrosaux.size());
                            Paragraph parNotaF = new Paragraph(notaF,FontFactory.getFont("arial",12));
                            parNotaF.setAlignment(Element.ALIGN_CENTER);
                            cellNota.addElement(parNotaF);
                            cellNota.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cellNota);
                        }
                        
                    }
                    
                    Nota notaEst = new Nota();
                    notaEst = estudianteEjB.findNotaEst(cont, est);
                    if (notaEst != null) {
                        observacion = notaEst.getObservaciones()==null?"":notaEst.getObservaciones();
                    }
                    
                    PdfPCell cellObservacion = new PdfPCell();
                    cellObservacion.setColspan(4);
                    Paragraph parObservacion = new Paragraph("OBSERVACIONES: "+observacion,FontFactory.getFont("arial",12,Font.NORMAL));
                    //parObservacion.setAlignment(Element.ALIGN_CENTER);
                    cellObservacion.addElement(parObservacion);
                    cellObservacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellObservacion);
                    PdfPCell cellBlanca = new PdfPCell();
                    cellBlanca.setBorder(Rectangle.NO_BORDER);
                    cellBlanca.setColspan(4);
                    cellBlanca.setPaddingBottom(15);
                    table.addCell(cellBlanca);
                    
                }
                document.add(table);
                document.newPage();
            }
        } catch (Exception e) {
            System.out.println("ENTRO 3 ");
            System.out.println("Error " + e.getMessage());
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
    public void initRender(){
        if(!anlectivos.isEmpty()){
            this.mostrarPrincipal = true;
            /*if(aEscolarEjb.getIniciado().getCursoList().isEmpty()){
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            }else{
                this.mostrarPrincipal = true;
            }*/
        }else{
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }
    public void cargarPeriodo(){
        if(periodoSelected.getPeriodoId()!=null){
            this.cursos = cursoEjb.getCursosByAño(aEscolar);
            cursoSelected = new Curso();
            this.mostrarCursos = true;
            this.mostrarBoton = false;
        }else{
            cursoSelected = new Curso();
            this.mostrarBoton = false;
            this.mostrarCursos = false;
        }
    }
    public void seleccionCurso(){
        if(cursoSelected.getCursoId()!=null){
            this.mostrarBoton = true;
        }else{
            this.mostrarBoton = false;
        }
    }
    public void cargarAño(){
        if(anlectivoSelected.getAnlectivoId()!=null){
            this.aEscolar = aEscolarEjb.find(anlectivoSelected.getAnlectivoId());
            this.mostrarPeriodos = true;
            this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
            this.mostrarCursos = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
            this.periodoSelected = new Periodo();
        }else{
            this.mostrarPeriodos = false;
            this.mostrarCursos = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
        }
    }
}
