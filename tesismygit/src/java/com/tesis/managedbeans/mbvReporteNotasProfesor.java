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
import com.tesis.clases.BackgroundF;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.omnifaces.util.Faces;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteNotasProfesor implements Serializable {

    private boolean login;
    private Anlectivo aEscolar;
    private Profesor profesor;
    private List<Periodo> periodos;
    private List<Curso> cursos;
    private List<Asignatura> asignaturas;
    private boolean mostrarPrincipal;
    private boolean mostrarPeriodos;
    private boolean mostrarCursos;
    private boolean mostrarAsignatura;
    private Periodo periodoSelected;
    private Curso curso;
    private Asignatura asignaturaSelected;
    private Contenidotematico contenido;
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

    public mbvReporteNotasProfesor() {
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

    @PostConstruct
    public void inicioPagina() {
        this.profesor = new Profesor();
        aEscolar = anlectivoEjb.getIniciado();
        this.login = false;
        this.mostrarPrincipal = false;
        this.mostrarAsignatura = false;
        this.mostrarCursos = false;
        this.asignaturas = new ArrayList<Asignatura>();
        this.cursos = new ArrayList<Curso>();
        this.periodos = new ArrayList<Periodo>();
        this.periodoSelected = new Periodo();
        this.curso = new Curso();
        this.asignaturaSelected = new Asignatura();
        this.contenido = new Contenidotematico();
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            profesor = mbslogin.getProfesor();
            login = mbslogin.isLogin();

        } catch (Exception e) {
            System.out.println(e.toString());
            this.login = false;
            //profesor = null;
        }
        //comprobarInicio();
    }

    public void comprobarInicio() {
        if (this.aEscolar != null) {
            if (periodoEjb.getNumeroPeriodosTerminadosAño(aEscolar) > 0) {
                this.mostrarPrincipal = true;
                this.periodos = periodoEjb.getPeriodosByAnioTerminados(aEscolar);
                this.mostrarPeriodos = true;
            } else {
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha Terminado ningun año escolar"));
            }
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }

    public void cargarPeriodo() {
        if (this.periodoSelected.getPeriodoId() != null) {
            this.periodoSelected = periodoEjb.find(this.periodoSelected.getPeriodoId());
            this.cursos = cursoEjb.findCursosProfeso(profesor, periodoSelected);
            if (!this.cursos.isEmpty()) {
                this.mostrarCursos = true;
            } else {
                this.mostrarCursos = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Usted no digto ninguna asignatura en este periodo"));
            }
        } else {
            this.mostrarCursos = false;
        }
    }

    public void cargarCurso() {
        if (this.curso.getCursoId() != null) {
            this.curso = cursoEjb.find(this.curso.getCursoId());
            List<Asignaturaciclo> asignaturasCiclo = asignaturaCicloEjb.asignaturasProfesor(profesor, curso, periodoSelected);
            for (Asignaturaciclo asc : asignaturasCiclo) {
                Asignatura as = asignaturaEjb.find(asc.getAsignaturaId().getAsignaturaId());
                asignaturas.add(as);
            }
            this.mostrarAsignatura = true;
        } else {
            this.mostrarAsignatura = false;
        }
    }

    public void cargarAsignatura() {
        if (this.asignaturaSelected.getAsignaturaId() != null) {
            this.asignaturaSelected = asignaturaEjb.find(this.asignaturaSelected.getAsignaturaId());
            System.out.println("CICLO" + curso.getCicloId());
            Asignaturaciclo asg = asignaturaCicloEjb.asignaturasCiclo(curso.getCicloId(), asignaturaSelected);
            contenido = contenidoEjb.getContenidoByAll(profesor, curso, asg, periodoSelected);
        } else {
        }
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

    public void imprimir() {
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
            Paragraph par3 = new Paragraph(10,"Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                    + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 ", FontFactory.getFont("arial", 9, Font.NORMAL));
            par3.setAlignment(Element.ALIGN_CENTER);
            cell3.addElement(par3);
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            cell3.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell3);
            document.add(table2);
            // datos estudiante
            Paragraph parDescrip = new Paragraph(10, "Reporte notas año escolar " + curso.getAnlectivoId().getAnio()
                    + " " + curso.getNombre() + " ciclo " + curso.getCicloId().getNumero() + " periodo " + contenido.getPeriodoId().getNumero(), FontFactory.getFont("arial", 12, Font.NORMAL));
            document.add(parDescrip);
            Paragraph parProf = new Paragraph(10, "Profesor: " + profesor.getApellido() + " " + profesor.getNombre(), FontFactory.getFont("arial", 12, Font.NORMAL));
            document.add(parProf);
            document.add(new Paragraph("\n"));

            PdfPTable tablaLogros = new PdfPTable(3);
            tablaLogros.setWidths(new int[]{30, 80, 10});

            PdfPCell cellTituloLogro = new PdfPCell();
            Paragraph partituloLogro = new Paragraph("Logro", FontFactory.getFont("arial", 12));
            partituloLogro.setAlignment(Element.ALIGN_CENTER);
            cellTituloLogro.addElement(partituloLogro);
            tablaLogros.addCell(cellTituloLogro);

            PdfPCell cellTituloDescrip = new PdfPCell();
            Paragraph partituloDescrip = new Paragraph("Descripcion", FontFactory.getFont("arial", 12));
            partituloDescrip.setAlignment(Element.ALIGN_CENTER);
            cellTituloDescrip.addElement(partituloDescrip);
            tablaLogros.addCell(cellTituloDescrip);

            PdfPCell cellTituloPorcentaje = new PdfPCell();
            Paragraph partituloPorcentaje = new Paragraph("%", FontFactory.getFont("arial", 12));
            partituloPorcentaje.setAlignment(Element.ALIGN_CENTER);
            cellTituloPorcentaje.addElement(partituloPorcentaje);
            tablaLogros.addCell(cellTituloPorcentaje);
            List<Logro> logros = logroEjb.getContenidoByAll(contenido);
            for (Logro log : logros) {
                tablaLogros.addCell(log.getTitulo());
                tablaLogros.addCell(log.getDescripcion());
                tablaLogros.addCell(log.getPorcentaje() + "");
            }
            document.add(tablaLogros);
            document.add(new Paragraph("\n"));
            List<Logro> logrosaux = logroEjb.getContenidoByAll(contenido);

            PdfPTable table = new PdfPTable(2 + logrosaux.size());
            table.setWidthPercentage(100);
            int tam = 2 + logrosaux.size();
            float[] ft = new float[tam];
            for (int i = 0; i < tam; i++) {
                System.out.println(i);
                if (i == 0) {
                    ft[i] = 80f;
                } else if (i == tam - 1) {
                    ft[i] = 9f;
                } else {
                    ft[i] = 30f;
                }
            }
            table.setWidths(ft);
            PdfPCell cellTituloEstudiante = new PdfPCell();
            Paragraph partituloEstudiante = new Paragraph("ESTUDIANTE ", FontFactory.getFont("arial", 12));
            partituloLogro.setAlignment(Element.ALIGN_CENTER);
            cellTituloEstudiante.addElement(partituloEstudiante);
            table.addCell(cellTituloEstudiante);
            for (Logro aux : logrosaux) {
                table.addCell(aux.getTitulo().toUpperCase());

            }
            PdfPCell cellTituloNotaF = new PdfPCell();
            Paragraph partituloNotaF = new Paragraph("Nota ", FontFactory.getFont("arial", 12));
            partituloNotaF.setAlignment(Element.ALIGN_CENTER);
            cellTituloNotaF.addElement(partituloNotaF);
            table.addCell(cellTituloNotaF);
            List<Estudiante> estudiantes = estudianteEjB.findByCurso(curso);
            for (Estudiante est : estudiantes) {
                table.addCell(est.getApellido() + " " + est.getNombre());
                for (Logro aux : logros) {
                    table.addCell(getNotaEstudiante(est, aux.getLogroId()) + "");
                }
                table.addCell(getNotaEst(est) + "");
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
                Faces.sendFile(baos.toByteArray(), "reporteNotas.pdf", false);
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
