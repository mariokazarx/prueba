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
import com.tesis.beans.AprobacionFacade;
import com.tesis.beans.AsignaturaFacade;
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.CicloFacade;
import com.tesis.beans.ConfiguracionFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CriterioevaluacionFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.EstadoAniolectivoFacade;
import com.tesis.beans.EstadoEstudianteFacade;
import com.tesis.beans.EstadoMatriculaFacade;
import com.tesis.beans.EstadocontenidotematicoFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.FormacriterioevaluacionFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.NotafinalFacade;
import com.tesis.beans.NotafinalrecuperacionFacade;
import com.tesis.beans.PeriodoFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCertificadoMatricula {

    private String identificacion;
    private Estudiante estudiante;
    
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private EstudianteFacade estdudianteEjb;
    
    public mbvCertificadoMatricula() {
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    @PostConstruct
    public void inicioPagina() {
        this.identificacion = "";
        this.estudiante = new Estudiante();
    }
    
    public void imprimir(){
        Estudiante est = estdudianteEjb.find(10);
        Matricula matEst = matriculaEjb.getActivaByEstudiante(est);
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image imageBackground = Image.getInstance(url);
            imageBackground.scaleAbsolute(400,400);
            imageBackground.setAbsolutePosition(115,225);
            canvas.saveState();
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.2f);
            canvas.setGState(state);
            canvas.addImage(imageBackground);
            canvas.restoreState();
            Image image = Image.getInstance(url);
            image.scaleAbsolute(100,100);        
            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(90);
            table2.setWidths(new int[]{1, 4});
            PdfPCell cell = new PdfPCell(image, true);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setRowspan(2);
            table2.addCell(cell);
            PdfPCell cell2 = new PdfPCell();
            Paragraph par = new Paragraph("CENTRO EDUCATIVO  “ANTONIO RICAURTE” ",FontFactory.getFont("arial",14,Font.BOLDITALIC));
            par.setAlignment(Element.ALIGN_CENTER);
            cell2.addElement(par);
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            Paragraph par3 = new Paragraph(10,"Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                    + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 "
                    ,FontFactory.getFont("arial",9,Font.NORMAL));
            par3.setAlignment(Element.ALIGN_CENTER);
            cell3.addElement(par3);
            cell3.setVerticalAlignment(Element.ALIGN_CENTER);
            cell3.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell3);
            document.add(table2);
            Paragraph par4 = new Paragraph(15,"LA RECTORA DEL CENTRO EDUCATIVO “ANTONIO RICAURTE”, INSTITUCION DE CARÁCTER PRIVADO, CON ACREDITACION DE ESTUDIOS "
                    + "SEGÚN RESOLUCIÓN No. 1861 JUNIO 30 DE 2005 DE LA SECRETARÍA DE EDUCACIÓN Y CULTURA DEPARTAMENTAL PARA EDUCACIÓN MEDIA ACADEMICA, SEGÚN DECRETO 3011/97, "
                    + "DEL M. E. N. REGISTRO DANE 352612001093 Y SNP ICFES 114454"
                    ,FontFactory.getFont("arial",11,Font.NORMAL));
            par4.setAlignment(Element.ALIGN_JUSTIFIED);
            par4.setIndentationLeft(50);
            par4.setIndentationRight(50);
            par4.setSpacingAfter(30);
            document.add(par4);
            Paragraph p5 = new Paragraph("CERTIFICA:",FontFactory.getFont("arial",14,Font.BOLDITALIC));
            p5.setAlignment(Element.ALIGN_CENTER);
            p5.setSpacingAfter(20);
            document.add(p5);
            Paragraph p6 = new Paragraph("Que, "+est.getApellido()+" "+est.getNombre()+", identificado con "+est.getTipoIdentifcacion()
                    + " No. "+est.getIdentificiacion()
                    + " actualmente se encuentra matriculado(a) al Ciclo "+matEst.getCursoId().getCicloId().getNumero()
                    + "durante el periodo académico,  "+matEst.getCursoId().getAnlectivoId().getAnio()
                    + " ",FontFactory.getFont("arial",11,Font.NORMAL));
            p6.setAlignment(Element.ALIGN_JUSTIFIED);
            p6.setIndentationLeft(50);
            p6.setIndentationRight(50);
            p6.setSpacingAfter(20);
            document.add(p6);
            
            
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
    public void buscar(){
        estudiante = estdudianteEjb.getEstudianteByIdentificacion(identificacion);
        
    }
}
