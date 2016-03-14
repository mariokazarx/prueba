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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tesis.beans.AnlectivoFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.LogroFacade;
import com.tesis.beans.LogronotaFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.clases.EstudianteNotas;
import com.tesis.clases.ReporteNotasProfesor;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Logronota;
import com.tesis.entity.Nota;
import com.tesis.entity.Periodo;
import com.tesis.entity.Profesor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class mbvReporteProfesor implements Serializable{

    private boolean login;
    private Profesor profesor;
    private List<Anlectivo> anlectivos;
    private List<Periodo> periodos;
    private List<Curso> cursos;
    private List<Asignatura> asignaturas;
    private boolean mostrarPrincipal;
    private boolean mostrarAños;
    private boolean mostrarPeriodos;
    private boolean mostrarCursos;
    private boolean mostrarAsignatura;
    private Anlectivo anlectivoSelected;
    private Periodo periodoSelected;
    private Curso curso;
    private Asignatura asignaturaSelected;
    private Contenidotematico contenido;
    private JasperPrint jasperPrint;
    private List<ReporteNotasProfesor> reporte;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AsignaturacicloFacade asignaturaCicloEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private EstudianteFacade estudianteEjB;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private LogroFacade logroEjb;
    @EJB
    private LogronotaFacade logroNotaEjb;
    
    public mbvReporteProfesor() {
    }

    public boolean isMostrarAsignatura() {
        return mostrarAsignatura;
    }

    public void setMostrarAsignatura(boolean mostrarAsignatura) {
        this.mostrarAsignatura = mostrarAsignatura;
    }

    public Asignatura getAsignaturaSelected() {
        return asignaturaSelected;
    }

    public void setAsignaturaSelected(Asignatura asignaturaSelected) {
        this.asignaturaSelected = asignaturaSelected;
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public boolean isMostrarPeriodos() {
        return mostrarPeriodos;
    }

    public void setMostrarPeriodos(boolean mostrarPeriodos) {
        this.mostrarPeriodos = mostrarPeriodos;
    }

    public List<Anlectivo> getAnlectivos() {
        return anlectivos;
    }

    public void setAnlectivos(List<Anlectivo> anlectivos) {
        this.anlectivos = anlectivos;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> curso) {
        this.cursos = curso;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarAños() {
        return mostrarAños;
    }

    public void setMostrarAños(boolean mostrarAños) {
        this.mostrarAños = mostrarAños;
    }

    public Anlectivo getAnlectivoSelected() {
        return anlectivoSelected;
    }

    public void setAnlectivoSelected(Anlectivo anlectivoSelected) {
        this.anlectivoSelected = anlectivoSelected;
    }
    
    @PostConstruct
    public void inicioPagina() {
        this.profesor = new Profesor();
        //aEscolar = new Anlectivo();
        this.login=false;
        this.mostrarAños = false;
        this.mostrarPrincipal = false;
        this.mostrarAsignatura = false;
        this.mostrarCursos = false;
        this.anlectivos = new ArrayList<Anlectivo>();
        this.asignaturas = new ArrayList<Asignatura>();
        this.cursos = new ArrayList<Curso>();
        this.periodos = new ArrayList<Periodo>();
        this.anlectivoSelected = new Anlectivo();
        this.periodoSelected = new Periodo();
        this.curso = new Curso();
        this.asignaturaSelected = new Asignatura();
        this.contenido = new Contenidotematico();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             profesor = mbslogin.getProfesor();
             login = mbslogin.isLogin();
            this.login = true;
        } catch (Exception e) {
            System.out.println(e.toString());
            //profesor = null;
        }
        //comprobarInicio();
    }
    public void comprobarInicio(){
        this.anlectivos = this.anlectivoEjb.getTerminados();
        if(!this.anlectivos.isEmpty()){
            this.mostrarPrincipal = true;
            this.mostrarAños = true;
        }else{
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha terminado ningun año escolar"));
        }
    }
    public void cargarAño(){
        if(this.anlectivoSelected.getAnlectivoId()!=null){
            this.anlectivoSelected = anlectivoEjb.find(this.anlectivoSelected.getAnlectivoId());
            this.mostrarPeriodos = true;
            this.periodos = periodoEjb.getPeriodosByAnio(anlectivoSelected);
        }else{
            this.mostrarPeriodos = false;
        }
    }
    public void cargarPeriodo(){
        if(this.periodoSelected.getPeriodoId()!=null){
            this.periodoSelected = periodoEjb.find(this.periodoSelected.getPeriodoId());
            this.cursos = cursoEjb.findCursosProfeso(profesor, periodoSelected);
            if(!this.cursos.isEmpty()){
                this.mostrarCursos = true;
            }else{
                this.mostrarCursos = false;
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Usted no digto ninguna asignatura en este periodo"));
            }
        }else{
            this.mostrarCursos = false;
        }
    }
    public void cargarCurso(){
        if(this.curso.getCursoId()!=null){
            this.curso = cursoEjb.find(this.curso.getCursoId());
            List<Asignaturaciclo> asignaturasCiclo = asignaturaCicloEjb.asignaturasProfesor(profesor, curso, periodoSelected);
            for (Asignaturaciclo asc : asignaturasCiclo) {
                Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                asignaturas.add(as);
            }
            this.mostrarAsignatura = true;
        }else{
            this.mostrarAsignatura = false;
        }
    }
    public void cargarAsignatura(){
        if(this.asignaturaSelected.getAsignaturaId()!= null){
            this.asignaturaSelected = asignaturaEjb.find(this.asignaturaSelected.getAsignaturaId());
            System.out.println("CICLO"+curso.getCicloId());
            Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(curso.getCicloId(), asignaturaSelected);
            contenido = contenidoEjb.getContenidoByAll(profesor, curso,asg, periodoSelected);
        }else{
            
        }
    }
    public void generarReporte(){
        try {
            this.reporte = new ArrayList<ReporteNotasProfesor>();
            ReporteNotasProfesor rpt = new ReporteNotasProfesor();
            rpt.setProfesor(profesor);
            rpt.setCurso(curso);
            rpt.setAsignatura(asignaturaSelected);
            rpt.setPeriodo(periodoSelected);
            List<EstudianteNotas> estudiantesN = new ArrayList<EstudianteNotas>();
            List<Estudiante> estudiantes = estudianteEjB.findByCurso(curso);
            for(Estudiante est:estudiantes){
                EstudianteNotas estNota = new EstudianteNotas();
                estNota.setId(est.getEstudianteId());
                estNota.setNombre(est.getNombre());
                estNota.setApellido(est.getApellido());           
                estNota.setNota(getNotaEst(est));
                Map<Integer,Object> notasLog = new HashMap<Integer,Object>();
                List<Logronota> logrosNotaAux = new ArrayList<Logronota>();
                List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
                for (Logro aux : logrosaux) {
                    logrosNotaAux.add(logroNotaEjb.getByLogroestudiante(est, aux));
                    notasLog.put(aux.getLogroId(),getNotaEstudiante(est,aux.getLogroId()));
                }
                estNota.setLogros(logrosNotaAux);
                estNota.setNotasLogros(notasLog);

                estudiantesN.add(estNota);
            }
            rpt.setEstudiantes(estudiantesN);
            reporte.add(rpt);
            init();
            curso.getCicloId().getNumero();
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
    private BigDecimal getNotaEst(Estudiante es) {

        BigDecimal nota = new BigDecimal(0);
        Nota notaEst = new Nota();
        
        notaEst = estudianteEjB.findNotaEst(contenido, es);
        if (notaEst != null) {
            nota = notaEst.getValor();
        }
        System.out.println("***TABLA**" + nota);
        return nota.setScale(1, RoundingMode.HALF_EVEN);
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
    public void imprimir(){
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            
            // = PdfWriter.getInstance(document, baos);;
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image imageBackground = Image.getInstance(url);
            imageBackground.scaleAbsolute(400,400);
            imageBackground.setAbsolutePosition(125,225);
            canvas.saveState();
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.2f);
            canvas.setGState(state);
            canvas.addImage(imageBackground);
            canvas.restoreState();
            
            
            
            
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
            Paragraph parDescrip = new Paragraph("Reporte notas año escolar "+curso.getAnlectivoId().getAnio()
                + " "+curso.getNombre()+" ciclo "+curso.getCicloId().getNumero()+" periodo "+contenido.getPeriodoId().getNumero()
                ,FontFactory.getFont("arial",12,Font.NORMAL));
            document.add(parDescrip);
            Paragraph parProf = new Paragraph("Profesor: "+ profesor.getApellido() + " " +profesor.getNombre()
                ,FontFactory.getFont("arial",12,Font.NORMAL));
            document.add(parProf);
            document.add(new Paragraph("\n"));
            
            PdfPTable tablaLogros = new PdfPTable(3);
            tablaLogros.setWidths(new int[]{30,80,10});
           
            PdfPCell cellTituloLogro = new PdfPCell();
            Paragraph partituloLogro= new Paragraph("Logro",FontFactory.getFont("arial",12));
            partituloLogro.setAlignment(Element.ALIGN_CENTER);
            cellTituloLogro.addElement(partituloLogro);
            tablaLogros.addCell(cellTituloLogro);
            
            PdfPCell cellTituloDescrip = new PdfPCell();
            Paragraph partituloDescrip = new Paragraph("Descripcion",FontFactory.getFont("arial",12));
            partituloDescrip.setAlignment(Element.ALIGN_CENTER);
            cellTituloDescrip.addElement(partituloDescrip);
            tablaLogros.addCell(cellTituloDescrip);
            
            PdfPCell cellTituloPorcentaje = new PdfPCell();
            Paragraph partituloPorcentaje = new Paragraph("%",FontFactory.getFont("arial",12));
            partituloPorcentaje.setAlignment(Element.ALIGN_CENTER);
            cellTituloPorcentaje.addElement(partituloPorcentaje);
            tablaLogros.addCell(cellTituloPorcentaje);
            List<Logro> logros = logroEjb.getContenidoByAll(contenido);
            for(Logro log : logros){
                tablaLogros.addCell(log.getTitulo());
                tablaLogros.addCell(log.getDescripcion());
                tablaLogros.addCell(log.getPorcentaje()+"");
            }
            document.add(tablaLogros);
            document.add(new Paragraph("\n"));
            List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);
        
            PdfPTable table = new PdfPTable(2+logrosaux.size());
            table.setWidthPercentage(100);
            int tam = 2+logrosaux.size();
            float[] ft = new float[tam];
            for(int i=0;i<tam;i++){
                System.out.println(i);
                if(i==0){
                    ft[i]=80f; 
                }else if(i==tam-1){
                    ft[i] = 9f;
                }else{
                    ft[i] = 30f;
                }         
            }
            table.setWidths(ft);
            PdfPCell cellTituloEstudiante = new PdfPCell();
            Paragraph partituloEstudiante= new Paragraph("ESTUDIANTE ",FontFactory.getFont("arial",12));
            partituloLogro.setAlignment(Element.ALIGN_CENTER);
            cellTituloEstudiante.addElement(partituloEstudiante);
            table.addCell(cellTituloEstudiante);
            for (Logro aux : logrosaux) {
               table.addCell(aux.getTitulo().toUpperCase());
               
            }
            PdfPCell cellTituloNotaF = new PdfPCell();
            Paragraph partituloNotaF= new Paragraph("Nota ",FontFactory.getFont("arial",12));
            partituloNotaF.setAlignment(Element.ALIGN_CENTER);
            cellTituloNotaF.addElement(partituloNotaF);
            table.addCell(cellTituloNotaF);
            List<Estudiante> estudiantes = estudianteEjB.findByCurso(curso);
            for(Estudiante est : estudiantes){
                table.addCell(est.getApellido()+" "+est.getNombre());
                for (Logro aux : logros) {
                    table.addCell(getNotaEstudiante(est,aux.getLogroId())+"");
                }
                table.addCell(getNotaEst(est)+"");
            }
            document.add(table);
            
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
}
