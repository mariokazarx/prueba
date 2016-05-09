/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
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
import com.tesis.clases.BackgroundF;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Aprobacion;
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
import com.tesis.entity.EstadoEstudiante;
import com.tesis.entity.EstadoMatricula;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Logro;
import com.tesis.entity.Matricula;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
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
public class mbvConstanciaEstudios implements Serializable{
    private static final long serialVersionUID = 1L;

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
    private UsuarioRoleFacade usrRoleEjb;
    @EJB 
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private PeriodoFacade periodoEjb;
    @EJB
    private AprobacionFacade aprobacionEjb;
    @EJB
    private EstadocontenidotematicoFacade estadoContenidoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
    @EJB
    private EstadoAniolectivoFacade estadoAlectivoEjb;
    @EJB
    private ConfiguracionFacade configuracionEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private AsignaturacicloFacade asiganturaCicloEjb;
    @EJB
    private EstudianteFacade estdudianteEjb;
    @EJB
    private EscalaFacade escalaEjb;
    @EJB
    private CriterioevaluacionFacade criterioEjb;
    @EJB
    private FormacriterioevaluacionFacade formaEvaluacionEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private NotafinalFacade notaFinalEjb;
    @EJB
    private NotafinalrecuperacionFacade notaFinalRecEjb;
    @EJB
    private EstadoEstudianteFacade estadoEstuainteEjb;
    @EJB
    private EstadoMatriculaFacade estadoMatriculaEjb;
    @EJB
    private CicloFacade cicloEjb;

    public mbvConstanciaEstudios() {
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
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
            Document document = new Document(PageSize.LETTER);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                document.open();
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");

                /* = PdfWriter.getInstance(document, baos);;
                 PdfContentByte canvas = writer.getDirectContentUnder();
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
                //par4.setSpacingBefore(20);
                document.add(par4);
                // datos estudiante
                Paragraph p5 = new Paragraph("CERTIFICA:", FontFactory.getFont("arial", 14, Font.BOLDITALIC));
                p5.setAlignment(Element.ALIGN_CENTER);
                p5.setSpacingAfter(20);
                document.add(p5);
                Paragraph p6 = new Paragraph("Que, " + est.getApellido() + " " + est.getNombre() + ", identificado con " + est.getTipoIdentifcacion()
                        + " No. " + est.getIdentificiacion()
                        + " cursó en este establecimiento educativo  "
                        + "los ciclos relacionados a continuacion, Jornada Fines de Semana,  "
                        + "de acuerdo al Decreto 3011 / 97. ", FontFactory.getFont("arial", 11, Font.NORMAL));
                p6.setAlignment(Element.ALIGN_JUSTIFIED);
                p6.setIndentationLeft(50);
                p6.setIndentationRight(50);
                p6.setSpacingAfter(20);
                document.add(p6);

                List<Matricula> matriculas = matriculaEjb.getMatriculasEstudiante(est);
                if (!matriculas.isEmpty()) {
                    for (Matricula maticula : matriculas) {
                        /*document.newPage();
                    
                         PdfContentByte canvasP = writer.getDirectContentUnder();
                         Image imageBackgroundP = Image.getInstance(url);
                         imageBackgroundP.scaleAbsolute(400,400);
                         imageBackgroundP.setAbsolutePosition(125,225);
                         canvasP.saveState();
                         PdfGState stateP = new PdfGState();
                         stateP.setFillOpacity(0.2f);
                         canvasP.setGState(stateP);
                         canvasP.addImage(imageBackgroundP);
                         canvasP.restoreState();
                         */

                        Paragraph p7 = new Paragraph("Curso y " + maticula.getAprobacionId().getNombre() + " el CICLO " + maticula.getCursoId().getCicloId().getNumero() + ", " + maticula.getCursoId().getCicloId().getDescripcion() + ", en el Periodo academico " + maticula.getCursoId().getAnlectivoId().getAnio(), FontFactory.getFont("arial", 11, Font.NORMAL));
                        p7.setAlignment(Element.ALIGN_JUSTIFIED);
                        p7.setIndentationLeft(50);
                        p7.setIndentationRight(50);
                        p7.setSpacingAfter(5);
                        document.add(p7);

                        PdfPTable table = new PdfPTable(3);
                        table.setWidthPercentage(80);
                        table.setWidths(new int[]{70, 8, 20});


                        PdfPCell cellTituloAsignatura = new PdfPCell();
                        Paragraph partituloAsignatura = new Paragraph(10, "ASIGNATURA", FontFactory.getFont("arial", 11));
                        partituloAsignatura.setAlignment(Element.ALIGN_CENTER);
                        cellTituloAsignatura.addElement(partituloAsignatura);
                        table.addCell(cellTituloAsignatura);

                        PdfPCell cellTituloNota = new PdfPCell();
                        Paragraph partituloNota = new Paragraph(10, "NOTA", FontFactory.getFont("arial", 11));
                        partituloNota.setAlignment(Element.ALIGN_CENTER);
                        cellTituloNota.addElement(partituloNota);
                        table.addCell(cellTituloNota);

                        PdfPCell cellTituloEstado = new PdfPCell();
                        Paragraph partituloEstado = new Paragraph(10, "ESTADO", FontFactory.getFont("arial", 11));
                        partituloEstado.setAlignment(Element.ALIGN_CENTER);
                        cellTituloEstado.addElement(partituloEstado);
                        table.addCell(cellTituloEstado);

                        Anlectivo anlectivo = anlectivoEjb.find(maticula.getCursoId().getAnlectivoId().getAnlectivoId());
                        Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                        List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                        Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                        Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                        asignaturas = asignaturaEjb.findByCiclo(ciclo);
                        for (Asignatura asignatura : asignaturas) {
                            Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                            Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                            BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
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
                        }
                        document.add(table);
                    }
                }


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




            //traer matriculas terminadas estudiante pordenas por años
            //decir cuales aprbo en que año y cuales reporbo
            //gtraer de cada matricula las notas 
            // poner cuales recupero
        /*Estudiante est = estdudianteEjb.find(10);
             List<Matricula> matriculas = matriculaEjb.getMatriculasEstudiante(est);
             if(!matriculas.isEmpty()){
             for(Matricula maticula : matriculas){
             Anlectivo anlectivo = anlectivoEjb.find(maticula.getCursoId().getAnlectivoId().getAnlectivoId());
             Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
             List<Asignatura> asignaturas = new ArrayList<Asignatura>();
             Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
             Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
             asignaturas = asignaturaEjb.findByCiclo(ciclo);
             for(Asignatura asignatura : asignaturas){
             Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
             Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
             BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
             System.out.println("NMNMNM"+notafinalEst+"ESTUDIANTE "+est+"asignatura "+asg+"año escolar "+anlectivo+"MATRICULA "+maticula);
             if(notafinalEst.getRecuperacion().compareTo("SI")==0){
             //tiene recupeacion
             Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
             //queda sacar la nota y comprar para vere si pasa o no

             if(notaRecuperacion.getValor().compareTo(max)>=0){
             //paso
             }else{
             //no paso
             }

             }else{
             //no tiene recuperacion
             if(notafinalEst.getValor().compareTo(max)>=0){
             //paso
             }else{
             //no paso
             }
             }                           
             }
             }
             }
             */
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void imprimirnue() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            Estudiante est = estudiante;
            Document document = new Document(PageSize.LETTER);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                document.open();
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");

                /* = PdfWriter.getInstance(document, baos);;
                 PdfContentByte canvas = writer.getDirectContentUnder();
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
                //par4.setSpacingBefore(20);
                document.add(par4);
                // datos estudiante
                Paragraph p5 = new Paragraph("CERTIFICA:", FontFactory.getFont("arial", 14, Font.BOLDITALIC));
                p5.setAlignment(Element.ALIGN_CENTER);
                p5.setSpacingAfter(20);
                document.add(p5);
                Paragraph p6 = new Paragraph("Que, " + est.getApellido() + " " + est.getNombre() + ", identificado con " + est.getTipoIdentifcacion()
                        + " No. " + est.getIdentificiacion()
                        + " cursó en este establecimiento educativo  "
                        + "los ciclos relacionados a continuacion, Jornada Fines de Semana,  "
                        + "de acuerdo al Decreto 3011 / 97. ", FontFactory.getFont("arial", 11, Font.NORMAL));
                p6.setAlignment(Element.ALIGN_JUSTIFIED);
                p6.setIndentationLeft(50);
                p6.setIndentationRight(50);
                p6.setSpacingAfter(20);
                document.add(p6);

                List<Matricula> matriculas = matriculaEjb.getMatriculasEstudiante(est);
                if (!matriculas.isEmpty()) {
                    for (Matricula maticula : matriculas) {
                        /*document.newPage();
                    
                         PdfContentByte canvasP = writer.getDirectContentUnder();
                         Image imageBackgroundP = Image.getInstance(url);
                         imageBackgroundP.scaleAbsolute(400,400);
                         imageBackgroundP.setAbsolutePosition(125,225);
                         canvasP.saveState();
                         PdfGState stateP = new PdfGState();
                         stateP.setFillOpacity(0.2f);
                         canvasP.setGState(stateP);
                         canvasP.addImage(imageBackgroundP);
                         canvasP.restoreState();
                         */

                        Paragraph p7 = new Paragraph("Curso y " + maticula.getAprobacionId().getNombre() + " el CICLO " + maticula.getCursoId().getCicloId().getNumero() + ", " + maticula.getCursoId().getCicloId().getDescripcion() + ", en el Periodo academico " + maticula.getCursoId().getAnlectivoId().getAnio(), FontFactory.getFont("arial", 11, Font.NORMAL));
                        p7.setAlignment(Element.ALIGN_JUSTIFIED);
                        p7.setIndentationLeft(50);
                        p7.setIndentationRight(50);
                        p7.setSpacingAfter(5);
                        document.add(p7);

                        PdfPTable table = new PdfPTable(3);
                        table.setWidthPercentage(80);
                        table.setWidths(new int[]{70, 8, 20});


                        PdfPCell cellTituloAsignatura = new PdfPCell();
                        Paragraph partituloAsignatura = new Paragraph(10, "ASIGNATURA", FontFactory.getFont("arial", 11));
                        partituloAsignatura.setAlignment(Element.ALIGN_CENTER);
                        cellTituloAsignatura.addElement(partituloAsignatura);
                        table.addCell(cellTituloAsignatura);

                        PdfPCell cellTituloNota = new PdfPCell();
                        Paragraph partituloNota = new Paragraph(10, "NOTA", FontFactory.getFont("arial", 11));
                        partituloNota.setAlignment(Element.ALIGN_CENTER);
                        cellTituloNota.addElement(partituloNota);
                        table.addCell(cellTituloNota);

                        PdfPCell cellTituloEstado = new PdfPCell();
                        Paragraph partituloEstado = new Paragraph(10, "ESTADO", FontFactory.getFont("arial", 11));
                        partituloEstado.setAlignment(Element.ALIGN_CENTER);
                        cellTituloEstado.addElement(partituloEstado);
                        table.addCell(cellTituloEstado);

                        Anlectivo anlectivo = anlectivoEjb.find(maticula.getCursoId().getAnlectivoId().getAnlectivoId());
                        Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                        List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                        Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                        Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                        asignaturas = asignaturaEjb.findByCiclo(ciclo);
                        for (Asignatura asignatura : asignaturas) {
                            Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                            Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                            BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
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
                    }
                }


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
        estudiante = estdudianteEjb.getEstudianteByIdentificacion(identificacion);
        if (estudiante != null) {
            this.mostrarBusqueda = true;
            List<Matricula> matriculas = matriculaEjb.getMatriculasEstudiante(estudiante);
            if (!matriculas.isEmpty()) {
                this.mostrarGenerar = true;
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adevertencia", "El estudiante aun no ha teminado ningun año en la institucion"));
                this.mostrarGenerar = false;
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adevertencia", "No se encontro ningun estudiante con esa identificacion"));
            this.mostrarBusqueda = false;
            this.mostrarGenerar = false;
        }
    }

    public void initRender() {

        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar certificados"));
        }

    }
}
