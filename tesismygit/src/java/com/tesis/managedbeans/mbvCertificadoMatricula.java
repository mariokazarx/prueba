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
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.BackgroundF;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class mbvCertificadoMatricula implements Serializable{
    private static final long serialVersionUID = 4445443271082147122L;

    private String identificacion;
    private Estudiante estudiante;
    private boolean mostrarBusqueda;
    private boolean mostrarGenerar;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private EstudianteFacade estdudianteEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;

    public mbvCertificadoMatricula() {
    }

    public boolean isMostrarBusqueda() {
        return mostrarBusqueda;
    }

    public boolean isMostrarGenerar() {
        return mostrarGenerar;
    }

    public boolean isConsultar() {
        return consultar;
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
        this.consultar = false;
        this.editar = false;
        this.eliminar = false;
        this.crear = false;
        this.mostrarBusqueda = false;
        this.mostrarGenerar = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
        if (this.usr != null) {
            for (UsuarioRole usrRol : usrRoleEjb.getByUser(usr)) {
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 17) {
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
        this.identificacion = "";
        this.estudiante = new Estudiante();
    }

    public void imprimir() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            Estudiante est = estudiante;
            Matricula matEst = matriculaEjb.getActivaByEstudiante(est);
            Document document = new Document(PageSize.LETTER);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                document.open();
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
                /*PdfContentByte canvas = writer.getDirectContentUnder();
                 Image imageBackground = Image.getInstance(url);
                 imageBackground.scaleAbsolute(400, 400);
                 imageBackground.setAbsolutePosition(115, 225);
                 canvas.saveState();
                 PdfGState state = new PdfGState();
                 state.setFillOpacity(0.2f);
                 canvas.setGState(state);
                 canvas.addImage(imageBackground);
                 canvas.restoreState();*/
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
                Paragraph par4 = new Paragraph(15, "LA RECTORA DEL CENTRO EDUCATIVO “ANTONIO RICAURTE”, INSTITUCION DE CARÁCTER PRIVADO, CON ACREDITACION DE ESTUDIOS "
                        + "SEGÚN RESOLUCIÓN No. 1861 JUNIO 30 DE 2005 DE LA SECRETARÍA DE EDUCACIÓN Y CULTURA DEPARTAMENTAL PARA EDUCACIÓN MEDIA ACADEMICA, SEGÚN DECRETO 3011/97, "
                        + "DEL M. E. N. REGISTRO DANE 352612001093 Y SNP ICFES 114454", FontFactory.getFont("arial", 11, Font.NORMAL));
                par4.setAlignment(Element.ALIGN_JUSTIFIED);
                par4.setIndentationLeft(50);
                par4.setIndentationRight(50);
                par4.setSpacingAfter(30);
                document.add(par4);
                Paragraph p5 = new Paragraph("CERTIFICA:", FontFactory.getFont("arial", 14, Font.BOLDITALIC));
                p5.setAlignment(Element.ALIGN_CENTER);
                p5.setSpacingAfter(20);
                document.add(p5);
                Paragraph p6 = new Paragraph("Que, " + est.getApellido() + " " + est.getNombre() + ", identificado con " + est.getTipoIdentifcacion()
                        + " No. " + est.getIdentificiacion()
                        + " actualmente se encuentra matriculado(a) al Ciclo " + matEst.getCursoId().getCicloId().getNumero()
                        + "durante el periodo académico,  " + matEst.getCursoId().getAnlectivoId().getAnio()
                        + " ", FontFactory.getFont("arial", 11, Font.NORMAL));
                p6.setAlignment(Element.ALIGN_JUSTIFIED);
                p6.setIndentationLeft(50);
                p6.setIndentationRight(50);
                p6.setSpacingAfter(20);
                document.add(p6);


            } catch (Exception e) {
            }
            document.close();
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-disposition", "attachment; filename=report.pdf");
                hsr.setContentLength(baos.size());
                try {
                    ServletOutputStream out = hsr.getOutputStream();
                    baos.writeTo(out);
                    out.flush();
                } catch (IOException ex) {
                }
                context.responseComplete();
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void buscar() {
        estudiante = estdudianteEjb.getEstudianteByIdentificacion(identificacion);
        if(estudiante!=null){
            mostrarBusqueda = true;
            Matricula matEst = matriculaEjb.getActivaByEstudiante(estudiante);
            if(matEst!=null){
                mostrarGenerar=true;
            }else{
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El estudiante no se encuentra matricula academicamente"));
                mostrarGenerar=false;
            }
            
        }else{
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adevertencia", "No se encontro ningun estudiante con esa identificacion"));
            mostrarBusqueda = false;
            mostrarGenerar = false;
        }

    }

    public void initRender() {

        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar certificados"));
        }

    }
}
