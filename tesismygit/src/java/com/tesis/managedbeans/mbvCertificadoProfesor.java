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
import com.tesis.beans.AsignaturacicloFacade;
import com.tesis.beans.ContenidotematicoFacade;
import com.tesis.beans.CursoFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.clases.BackgroundF;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
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
import org.omnifaces.util.Faces;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvCertificadoProfesor implements Serializable{
    private static final long serialVersionUID = 7135998092565308829L;

    private String identificacion;
    private Profesor profesor;
    private boolean mostrarBusqueda;
    private boolean mostrarGenerar;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private AsignaturacicloFacade asiganturaCicloEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private CursoFacade cursoEjb;

    public mbvCertificadoProfesor() {
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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
        this.profesor = new Profesor();
    }

    public void imprimirnue() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            Profesor prof = profesor;
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
                //par4.setSpacingBefore(20);
                document.add(par4);
                // datos estudiante
                Paragraph p5 = new Paragraph("CERTIFICA:", FontFactory.getFont("arial", 14, Font.BOLDITALIC));
                p5.setAlignment(Element.ALIGN_CENTER);
                p5.setSpacingAfter(20);
                document.add(p5);
                Paragraph p6 = new Paragraph("Que, " + prof.getApellido() + " " + prof.getNombre() + ", identificado con C.C"
                        + " No. " + prof.getCedula()
                        + " está vinculado en esta institución como docente,"
                        + "con una asignación académica para el año "+anlectivoEjb.getIniciado().getAnio()
                        + " como se indica a continuación. ", FontFactory.getFont("arial", 11, Font.NORMAL));
                p6.setAlignment(Element.ALIGN_JUSTIFIED);
                p6.setIndentationLeft(50);
                p6.setIndentationRight(50);
                p6.setSpacingAfter(20);
                document.add(p6);

                Anlectivo auxEscolar = anlectivoEjb.getIniciado();
                List<Asignaturaciclo> asignaturasCiclo = contenidoEjb.getByProfesorAsg(profesor, auxEscolar);

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(80);
                table.setWidths(new int[]{10, 90});


                PdfPCell cellTituloAsignatura = new PdfPCell();
                Paragraph partituloAsignatura = new Paragraph(10, "#", FontFactory.getFont("arial", 11));
                partituloAsignatura.setAlignment(Element.ALIGN_CENTER);
                cellTituloAsignatura.addElement(partituloAsignatura);
                table.addCell(cellTituloAsignatura);

                PdfPCell cellTituloNota = new PdfPCell();
                Paragraph partituloNota = new Paragraph(10, "ASIGNATURA", FontFactory.getFont("arial", 11));
                partituloNota.setAlignment(Element.ALIGN_CENTER);
                cellTituloNota.addElement(partituloNota);
                table.addCell(cellTituloNota);
                int i = 1;
                for(Asignaturaciclo asg:asignaturasCiclo){
                    table.addCell(""+i);
                    Asignatura asignatura = asignaturaEjb.find(asg.getAsignaturaId().getAsignaturaId());
                    table.addCell(asignatura.getNombre());
                    i++;
                }
                
                /*PdfPCell cellTituloEstado = new PdfPCell();
                 Paragraph partituloEstado = new Paragraph(10, "ESTADO", FontFactory.getFont("arial", 11));
                 partituloEstado.setAlignment(Element.ALIGN_CENTER);
                 cellTituloEstado.addElement(partituloEstado);
                 table.addCell(cellTituloEstado);*/

                /* Anlectivo anlectivo = anlectivoEjb.find(maticula.getCursoId().getAnlectivoId().getAnlectivoId());
                 Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                 List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                 Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                 Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                 asignaturas = asignaturaEjb.findByCiclo(ciclo);
                 for (Asignatura asignatura : asignaturas) {
                 Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                 Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                 BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
                 System.out.println("NMNMNM" + notafinalEst + "ESTUDIANTE " + est + "asignatura " + asg + "año escolar " + anlectivo + "MATRICULA " + maticula);
                 table.addCell(asignatura.getNombre());
                 if (notafinalEst.getRecuperacion().compareTo("SI") == 0) {

                 //tiene recupeacion
                 Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
                 //queda sacar la nota y comprar para vere si pasa o no
                 table.addCell(notaRecuperacion.getValor().toString());
                 if (notaRecuperacion.getValor().compareTo(max) >= 0) {
                 table.addCell("APROBO");
                 } else {
                 table.addCell("REPROBO");
                 }

                 } else {
                 //no tiene recuperacion
                 table.addCell(notafinalEst.getValor().toString());
                 if (notafinalEst.getValor().compareTo(max) >= 0) {
                 table.addCell("APROBO");
                 } else {
                 table.addCell("REPROBO");
                 }
                 }
                 }*/
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




            } catch (Exception e) {
            }
            document.close();
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                try {
                    HttpServletResponse hsr = (HttpServletResponse) response;
                    //hsr.setContentType("application/pdf");
                    //hsr.setHeader("Content-disposition", "attachment; filename=report.pdf");
                    //hsr.setContentLength(baos.size());
                    hsr.reset();
                    //envia archivo

                    Faces.sendFile(baos.toByteArray(), "cosntancia.pdf", false);
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

    public void buscar() {
        profesor = profesorEjb.getByCedula(identificacion);
        if (profesor != null) {
            this.mostrarBusqueda = true;
            Anlectivo auxEscolar = anlectivoEjb.getIniciado();
            List<Asignaturaciclo> asignaturasCiclo = contenidoEjb.getByProfesorAsg(profesor, auxEscolar);
            if (!asignaturasCiclo.isEmpty()) {
                this.mostrarGenerar = true;
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adevertencia", "El profesor no tiene asignación academica para el año en curso"));
                this.mostrarGenerar = false;
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adevertencia", "No se encontro ningun profesor con esa identificacion"));
            this.mostrarBusqueda = false;
            this.mostrarGenerar = false;
        }
    }

    public void initRender() {
        Anlectivo auxEscolar = anlectivoEjb.getIniciado();
        if (auxEscolar != null) {
            if (cursoEjb.getCursosByAño(auxEscolar).isEmpty()) {//auxEscolar.getCursoList().isEmpty()
                this.mostrarGenerar = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            } else {
                if (!this.consultar) {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
                }
                this.mostrarGenerar = true;
            }
        } else {
            this.mostrarGenerar = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }

    }
}
