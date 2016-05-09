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
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
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
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.BackgroundF;
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
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.fonts.FontFamily;
import org.omnifaces.util.Faces;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvBoletinesAnteriores implements Serializable{
    private static final long serialVersionUID = 8612214971891104719L;

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
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
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
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    private JasperPrint jasperPrint;

    public mbvBoletinesAnteriores() {
    }

    public boolean isConsultar() {
        return consultar;
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
        this.consultar = false;
        this.editar = false;
        this.eliminar = false;
        this.crear = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 18) {
                    if (usrRol.getRoleId().getAgregar()) {
                        this.crear = true;
                    }
                    if (usrRol.getRoleId().getConsultar()) {
                        this.consultar = true;
                    }
                    if (usrRol.getRoleId().getEditar()) {
                        this.editar = true;
                    }
                    if (usrRol.getRoleId().getEliminar()) {
                        this.eliminar = true;
                    }
                }
            }
        }
        if (this.usr.getTipoUsuarioId().getTipoUsuarioId() == 4) {
            this.consultar = true;
            this.editar = true;
            this.eliminar = true;
            this.crear = true;
        }
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
        if (!anlectivos.isEmpty()) {
            this.mostrarPrincipal = true;
        } else {
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

    public void init() throws JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reporte);
        String reportpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/boletinPeriodo.jasper");//boletinPeriodo.jasper
        /*Map<String, Object> parametros = new HashMap<String, Object>();
         parametros.put("ciclo", "3");
         parametros.put("año", "2016");
         parametros.put("curso", "3-1"); */
        jasperPrint = JasperFillManager.fillReport(reportpath, new HashMap<String, Object>(), beanCollectionDataSource);
    }

    public void pdf() throws JRException, IOException {
        generar();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
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
            for (Estudiante est : estudiantes) {
                ReporteBoletin rpt = new ReporteBoletin();
                rpt.setCurso(cur);
                rpt.setEstudiante(est);
                rpt.setPeriodo(periodo);
                List<ContenidoBoletin> conrpt = new ArrayList<ContenidoBoletin>();
                for (Contenidotematico cont : contenidos) {
                    ContenidoBoletin conBoletin = new ContenidoBoletin();
                    conBoletin.setContenido(cont);
                    conBoletin.setNota(BigDecimal.ZERO);
                    conBoletin.setNota(getNotaEst(est, cont));
                    conBoletin.setObservaciones("falata observacion");
                    List<Logro> logrosaux = logroEjb.getContenidoByAll(cont);
                    List<Logronota> logrosNotaAux = new ArrayList<Logronota>();
                    for (Logro aux : logrosaux) {
                        logrosNotaAux.add(logroNotaEjb.getByLogroestudiante(est, aux));
                    }
                    conrpt.add(conBoletin);
                    conBoletin.setLogros(logrosNotaAux);
                }
                rpt.setContenidos(conrpt);
                reporte.add(rpt);
            }
            init();
        } catch (Exception e) {
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
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            Periodo periodo = periodoEjb.find(periodoSelected.getPeriodoId());
            Curso cur = cursoEjb.find(cursoSelected.getCursoId());
            List<Contenidotematico> contenidos = new ArrayList<Contenidotematico>();
            List<Estudiante> estudiantes = new ArrayList<Estudiante>();
            contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
            estudiantes = estudianteEjB.findByCurso(cursoSelected);
            Document document = new Document(PageSize.LETTER);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                document.open();
                for (Estudiante est : estudiantes) {
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
                    Paragraph parEstudiante = new Paragraph("Boletin Periodo" + periodo.getNumero() + " Año escolar " + cur.getAnlectivoId().getAnio()
                            + " " + cur.getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
                    document.add(parEstudiante);
                    Paragraph parEstudiante2 = new Paragraph("Estudiante: " + est.getApellido() + " " + est.getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
                    document.add(parEstudiante2);
                    document.add(new Paragraph("\n"));
                    PdfPTable table = new PdfPTable(4);
                    table.setWidthPercentage(100);
                    table.setWidths(new int[]{100, 8, 8, 8});
                    for (Contenidotematico cont : contenidos) {
                        PdfPCell cellProfesor = new PdfPCell();
                        cellProfesor.setColspan(4);
                        Paragraph parProfesor = new Paragraph(10, "Profesor: " + cont.getProfesorId().getApellido() + " " + cont.getProfesorId().getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
                        //parProfesor.setAlignment(Element.ALIGN_CENTER);
                        cellProfesor.addElement(parProfesor);
                        cellProfesor.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellProfesor);
                        PdfPCell cellAsignatura = new PdfPCell();
                        cellAsignatura.setColspan(4);
                        Paragraph parAsignatura = new Paragraph(10, "Asigantura: " + cont.getAsignaturacicloId().getAsignaturaId().getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
                        //parAsignatura.setAlignment(Element.ALIGN_CENTER);
                        cellAsignatura.addElement(parAsignatura);
                        cellAsignatura.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellAsignatura);
                        PdfPCell cellTituloLogro = new PdfPCell();
                        Paragraph partituloLogro = new Paragraph(10, "Logro", FontFactory.getFont("arial", 12));
                        partituloLogro.setAlignment(Element.ALIGN_CENTER);
                        cellTituloLogro.addElement(partituloLogro);
                        //cellTituloLogro.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellTituloLogro);
                        PdfPCell cellTituloPorcentaje = new PdfPCell();
                        Paragraph partituloPorcentaje = new Paragraph(10, "%", FontFactory.getFont("arial", 12));
                        partituloPorcentaje.setAlignment(Element.ALIGN_CENTER);
                        cellTituloPorcentaje.addElement(partituloPorcentaje);
                        //cellTituloPorcentaje.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellTituloPorcentaje);
                        PdfPCell cellTituloNotaL = new PdfPCell();
                        Paragraph partituloNotaL = new Paragraph(10, "N.L", FontFactory.getFont("arial", 12));
                        partituloNotaL.setAlignment(Element.ALIGN_CENTER);
                        cellTituloNotaL.addElement(partituloNotaL);
                        //cellTituloNotaL.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellTituloNotaL);
                        PdfPCell cellTituloNotaF = new PdfPCell();
                        Paragraph partituloNotaF = new Paragraph(10, "N.F", FontFactory.getFont("arial", 12));
                        partituloNotaF.setAlignment(Element.ALIGN_CENTER);
                        cellTituloNotaF.addElement(partituloNotaF);
                        //cellTituloNotaF.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellTituloNotaF);
                        List<Logro> logrosaux = logroEjb.getContenidoByAll(cont);
                        boolean ban = false;
                        String observacion = "";
                        for (Logro aux : logrosaux) {
                            Logronota logN = logroNotaEjb.getByLogroestudiante(est, aux);
                            PdfPCell cellDescripcion = new PdfPCell();
                            Paragraph parDescripcion = new Paragraph(10, aux.getTitulo() + "\n" + aux.getDescripcion(), FontFactory.getFont("arial", 10));
                            cellDescripcion.addElement(parDescripcion);
                            cellDescripcion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cellDescripcion);
                            String porcentaje = Integer.toString(aux.getPorcentaje());
                            PdfPCell cellPorcentaje = new PdfPCell();
                            Paragraph parPorcentaje = new Paragraph(10, porcentaje, FontFactory.getFont("arial", 10));
                            parPorcentaje.setAlignment(Element.ALIGN_CENTER);
                            cellPorcentaje.addElement(parPorcentaje);
                            cellPorcentaje.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cellPorcentaje);
                            String notaLogro = logN.getNota().toString();
                            PdfPCell cellNotalog = new PdfPCell();
                            Paragraph parNotalog = new Paragraph(10, notaLogro, FontFactory.getFont("arial", 10));
                            parNotalog.setAlignment(Element.ALIGN_CENTER);
                            cellNotalog.addElement(parNotalog);
                            cellNotalog.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cellNotalog);
                            if (ban == false) {
                                ban = true;
                                String notaF = getNotaEst(est, cont).toString();
                                PdfPCell cellNota = new PdfPCell();
                                cellNota.setRowspan(logrosaux.size());
                                Paragraph parNotaF = new Paragraph(10, notaF, FontFactory.getFont("arial", 10));
                                parNotaF.setAlignment(Element.ALIGN_CENTER);
                                cellNota.addElement(parNotaF);
                                cellNota.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(cellNota);
                            }

                        }

                        Nota notaEst = new Nota();
                        notaEst = estudianteEjB.findNotaEst(cont, est);
                        if (notaEst != null) {
                            observacion = notaEst.getObservaciones() == null ? "" : notaEst.getObservaciones();
                        }

                        PdfPCell cellObservacion = new PdfPCell();
                        cellObservacion.setColspan(4);
                        Paragraph parObservacion = new Paragraph(10, "OBSERVACIONES: " + observacion, FontFactory.getFont("arial", 10, Font.NORMAL));
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
                    //---firmas
                    document.add(new Paragraph("\n \n"));
                    PdfPTable tableFirmas = new PdfPTable(2);
                    tableFirmas.setWidthPercentage(80);
                    tableFirmas.setWidths(new int[]{2, 2});

                    PdfPCell cellNombre = new PdfPCell();
                    Paragraph parNombre = new Paragraph(10, "Lic. YOLANDA SANCHEZ C.", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parNombre.setAlignment(Element.ALIGN_CENTER);
                    cellNombre.addElement(parNombre);
                    cellNombre.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellNombre.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellNombre);

                    PdfPCell cellNombre2 = new PdfPCell();
                    Paragraph parNombre2 = new Paragraph(10, "Mag. HERNANDO BARAHONA D.", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parNombre2.setAlignment(Element.ALIGN_CENTER);
                    cellNombre2.addElement(parNombre2);
                    cellNombre2.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellNombre2.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellNombre2);

                    PdfPCell cellCargo = new PdfPCell();
                    Paragraph parCargo = new Paragraph(10, "Rectora", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parCargo.setAlignment(Element.ALIGN_CENTER);
                    cellCargo.addElement(parCargo);
                    cellCargo.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellCargo.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellCargo);

                    PdfPCell cellCargo2 = new PdfPCell();
                    Paragraph parCargo2 = new Paragraph(10, "Secretario Académico", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parCargo2.setAlignment(Element.ALIGN_CENTER);
                    cellCargo2.addElement(parCargo2);
                    cellCargo2.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellCargo2.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellCargo2);

                    PdfPCell cellDocumento = new PdfPCell();
                    Paragraph parDocumento = new Paragraph(10, "C.C. No. 30.715.393 de Pasto", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parDocumento.setAlignment(Element.ALIGN_CENTER);
                    cellDocumento.addElement(parDocumento);
                    cellDocumento.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellDocumento.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellDocumento);

                    PdfPCell cellDocumento2 = new PdfPCell();
                    Paragraph parDocumento2 = new Paragraph(10, "C.C. No. 6.744.418 de Tunja", FontFactory.getFont("arial", 12, Font.NORMAL));
                    parDocumento2.setAlignment(Element.ALIGN_CENTER);
                    cellDocumento2.addElement(parDocumento2);
                    cellDocumento2.setVerticalAlignment(Element.ALIGN_CENTER);
                    cellDocumento2.setBorder(Rectangle.NO_BORDER);
                    tableFirmas.addCell(cellDocumento2);

                    document.add(tableFirmas);
                    document.newPage();

                }

            } catch (Exception e) {
            }
            document.close();
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                try {
                    HttpServletResponse hsr = (HttpServletResponse) response;
                    hsr.reset();
                    Faces.sendFile(baos.toByteArray(), "Boletines.pdf", false);
                    try {
                        ServletOutputStream out = hsr.getOutputStream();
                        baos.writeTo(out);
                        out.flush();
                    } catch (IOException ex) {
                    }
                    context.responseComplete();
                } catch (IOException ex) {
                    Logger.getLogger(mbvConstanciaEstudios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void initRender() {
        if (!anlectivos.isEmpty()) {
            if (!this.consultar) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
            }
            this.mostrarPrincipal = true;
            /*if(aEscolarEjb.getIniciado().getCursoList().isEmpty()){
             this.mostrarPrincipal = false;
             FacesContext.getCurrentInstance().
             addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
             }else{
             this.mostrarPrincipal = true;
             }*/
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }

    public void cargarPeriodo() {
        if (periodoSelected.getPeriodoId() != null) {
            this.cursos = cursoEjb.getCursosByAño(aEscolar);
            cursoSelected = new Curso();
            this.mostrarCursos = true;
            this.mostrarBoton = false;
        } else {
            cursoSelected = new Curso();
            this.mostrarBoton = false;
            this.mostrarCursos = false;
        }
    }

    public void seleccionCurso() {
        if (cursoSelected.getCursoId() != null) {
            this.mostrarBoton = true;
        } else {
            this.mostrarBoton = false;
        }
    }

    public void cargarAño() {
        if (anlectivoSelected.getAnlectivoId() != null) {
            this.aEscolar = aEscolarEjb.find(anlectivoSelected.getAnlectivoId());
            this.mostrarPeriodos = true;
            this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
            this.mostrarCursos = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
            this.periodoSelected = new Periodo();
        } else {
            this.mostrarPeriodos = false;
            this.mostrarCursos = false;
            this.mostrarBoton = false;
            this.cursoSelected = new Curso();
        }
    }
}
