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
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.clases.BackgroundF;
import com.tesis.clases.MatriculaReporte;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import com.tesis.entity.Profesor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
import org.omnifaces.util.Faces;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvListaEstudiantes {

    private Profesor profesor;
    private boolean mostrarContenido;
    private Curso cursoSelected;
    private Asignatura asignaturaSelected;
    private Anlectivo aEscolar;
    private List<Curso> cursos;
    private List<Asignatura> asignaturas;
    private boolean login;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private MatriculaFacade matriculaEjb;

    public mbvListaEstudiantes() {
    }

    public boolean isMostrarContenido() {
        return mostrarContenido;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public Curso getCursoSelected() {
        return cursoSelected;
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

    @PostConstruct
    public void inicioPagina() {
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            profesor = mbslogin.getProfesor();
            login = mbslogin.isLogin();
        } catch (Exception e) {
            System.out.println(e.toString());
            profesor = null;
            this.login = false;
        }
        this.mostrarContenido = false;
        this.asignaturaSelected = new Asignatura();
        this.aEscolar = anlectivoEjb.getIniciado();
        this.cursoSelected = new Curso();
        this.cursos = new ArrayList<Curso>();
        this.asignaturas = new ArrayList<Asignatura>();
        if (login) {
            if (aEscolar != null) {
                this.mostrarContenido = true;
                this.cursos = this.cursoEjb.getCursosByAño(aEscolar);
            }
        }
    }

    public void cargarAsignaturas() {
        asignaturas = new ArrayList<Asignatura>();
        if (cursoSelected.getCursoId() != null) {
            List<Contenidotematico> conts = contenidoEjb.getByProfesorCurso(profesor, cursoSelected);
            for (Contenidotematico contenido : conts) {
                Asignatura asg = asignaturaEjb.find(contenido.getAsignaturacicloId().getAsignaturaId().getAsignaturaId());
                asignaturas.add(asg);
            }
            asignaturas = new ArrayList<Asignatura>(new LinkedHashSet<Asignatura>(asignaturas));
        } else {
            asignaturaSelected = new Asignatura();
        }
    }

    public void initRender() {

        if (aEscolar == null) {
            this.mostrarContenido = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "aun no inicia el año escolar"));
        }
    }

    public void imprimir() {
        if (login) {
            if (this.cursoSelected.getCursoId() != null && this.asignaturaSelected.getAsignaturaId() != null) {
                Document document = new Document(PageSize.LETTER);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    asignaturaSelected = asignaturaEjb.find(asignaturaSelected.getAsignaturaId());
                    PdfWriter writer = PdfWriter.getInstance(document, baos);
                    BackgroundF event = new BackgroundF();
                    writer.setPageEvent(event);
                    document.open();
                    URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
                    Image image = Image.getInstance(url);
                    image.scaleAbsolute(100, 100);
                    PdfPTable table2 = new PdfPTable(2);
                    table2.setWidthPercentage(90);
                    table2.setWidths(new int[]{1, 4});
                    PdfPCell cell = new PdfPCell(image, true);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setRowspan(2);
                    table2.addCell(cell);
                    PdfPCell cell2 = new PdfPCell();
                    Paragraph par = new Paragraph("CENTRO EDUCATIVO  “ANTONIO RICAURTE” ", FontFactory.getFont("arial", 14, Font.BOLDITALIC));
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

                    

                    Paragraph par4 = new Paragraph(15, "Estudiantes Matriculados al "+cursoSelected.getNombre(), FontFactory.getFont("arial", 11, Font.NORMAL));
                    par4.setAlignment(Element.ALIGN_JUSTIFIED);
                    par4.setIndentationLeft(50);
                    par4.setIndentationRight(50);
                    //par4.setSpacingAfter(30);
                    par4.setSpacingBefore(20);
                    document.add(par4);
                    
                    Paragraph par5 = new Paragraph(10, "Asignatura "+asignaturaSelected.getNombre(), FontFactory.getFont("arial", 11, Font.NORMAL));
                    par5.setAlignment(Element.ALIGN_JUSTIFIED);
                    par5.setIndentationLeft(50);
                    par5.setIndentationRight(50);
                    par5.setSpacingAfter(30);
                    //par5.setSpacingBefore(20);
                    document.add(par5);
                    
                    PdfPTable table = new PdfPTable(1);
                    table.setWidthPercentage(100);
                    //table.setWidths(new int[]{70, 8, 20});
                    PdfPCell cellTituloAsignatura = new PdfPCell();
                    Paragraph partituloAsignatura = new Paragraph(10, "ESTUDIANTES", FontFactory.getFont("arial", 11));
                    partituloAsignatura.setAlignment(Element.ALIGN_CENTER);
                    cellTituloAsignatura.addElement(partituloAsignatura);
                    table.addCell(cellTituloAsignatura);
                    List<Estudiante> est = new ArrayList<Estudiante>();
                    for (Matricula matriculasCurso : matriculaEjb.matriculasCurso(cursoSelected)) {
                        table.addCell(matriculasCurso.getEstudianteId().getApellido() + " " + matriculasCurso.getEstudianteId().getApellido());
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
                    try {
                        System.out.println("ENTRO 4 ");
                        HttpServletResponse hsr = (HttpServletResponse) response;
                        //hsr.setContentType("application/pdf");
                        //hsr.setHeader("Content-disposition", "attachment; filename=report.pdf");
                        //hsr.setContentLength(baos.size());
                        hsr.reset();
                        //envia archivo

                        Faces.sendFile(baos.toByteArray(), "ListadoEstudiantes.pdf", false);
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
    }
}