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
import com.tesis.entity.Asignatura;
import com.tesis.entity.Asignaturaciclo;
import com.tesis.entity.Ciclo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Criterioevaluacion;
import com.tesis.entity.Curso;
import com.tesis.entity.Escala;
import com.tesis.entity.EstadoEstudiante;
import com.tesis.entity.EstadoMatricula;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.Nota;
import com.tesis.entity.Notafinal;
import com.tesis.entity.Notafinalrecuperacion;
import com.tesis.entity.Periodo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
public class mbvConsolidadoFinal implements Serializable{
    private static final long serialVersionUID = 1L;

    private boolean mostrarBonton;
    private Anlectivo anlectivo;
    @EJB
    private AnlectivoFacade anlectivoEjb;
    @EJB
    private ContenidotematicoFacade contenidoEjb;
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
    private PeriodoFacade periodoEjb;
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

    public mbvConsolidadoFinal() {
    }

    public boolean isMostrarBonton() {
        return mostrarBonton;
    }

    @PostConstruct()
    public void inicio() {
        this.mostrarBonton = false;
        this.anlectivo = anlectivoEjb.getIniciado();
    }

    public void imprimir() {
        List<Matricula> matriculasAnio;
        List<Curso> cursos;
        Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
        Criterioevaluacion criterio = criterioEjb.find(anlectivo.getConfiguracionId().getCriterioevaluacionId().getCriterioevaluacionId());
        Document document = new Document(PageSize.LEGAL.rotate());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!cursoEjb.getCursosByAño(anlectivo).isEmpty()) {//anlectivo.getCursoList().isEmpty()
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                document.open();
                cursos = cursoEjb.getCursosByAño(anlectivo);//anlectivo.getCursoList();
                for (Curso cur : cursos) {
                    URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
                    Image image = Image.getInstance(url);
                    image.scaleAbsolute(50, 50);
                    PdfPTable table2 = new PdfPTable(2);
                    table2.setWidthPercentage(80);
                    table2.setWidths(new int[]{1, 6});
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
                    Paragraph par3 = new Paragraph("Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                            + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 ", FontFactory.getFont("arial", 9, Font.NORMAL));
                    par3.setAlignment(Element.ALIGN_CENTER);
                    cell3.addElement(par3);
                    cell3.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    table2.addCell(cell3);
                    document.add(table2);
                    document.add(new Paragraph(10,"Reporte consolidado cierre de año "+anlectivo.getAnio()));
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss:");
                    Date currentDate = new Date();
                    String date = formatter.format(currentDate);
                    document.add(new Paragraph("Fecha de generación: " + date));
                    document.add(new Paragraph("\n"));
                    Ciclo cicloaux = cicloEjb.find(cur.getCicloId().getCicloId());
                    List<Asignatura> asignaturasAux = new ArrayList<Asignatura>();
                    asignaturasAux = asignaturaEjb.findByCiclo(cicloaux);
                    int numPeriodos = Integer.parseInt(periodoEjb.getNumeroPeriodosAño(anlectivo).toString());
                    int totalP = asignaturasAux.size() * numPeriodos;
                    PdfPTable tablaContenido = new PdfPTable(2 + asignaturasAux.size() + totalP);
                    int tam = 2 + asignaturasAux.size() + totalP;
                    tablaContenido.setWidthPercentage(100);
                    float[] ft = new float[tam];
                    for (int i = 0; i < tam; i++) {
                        if (i == 0) {
                            ft[i] = 50f;
                        } else if (i == tam - 1) {
                            ft[i] = 15f;
                        } else {
                            ft[i] = 7f;
                        }
                    }
                    tablaContenido.setWidths(ft);

                    PdfPCell cellT1 = new PdfPCell();
                    cellT1.setRowspan(2);
                    Paragraph parT1 = new Paragraph(10, "Estudiante", FontFactory.getFont("arial", 9, Font.BOLD));
                    parT1.setAlignment(Element.ALIGN_CENTER);
                    cellT1.addElement(parT1);
                    //cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablaContenido.addCell(cellT1);

                    for (Asignatura asg : asignaturasAux) {
                        PdfPCell cellT2 = new PdfPCell();
                        cellT2.setColspan(numPeriodos + 1);
                        Paragraph parT2 = new Paragraph(10, asg.getNombre(), FontFactory.getFont("arial", 9, Font.BOLD));
                        parT2.setAlignment(Element.ALIGN_CENTER);
                        cellT2.addElement(parT2);
                        //cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaContenido.addCell(cellT2);
                    }
                    PdfPCell cellT3 = new PdfPCell();
                    Paragraph parT3 = new Paragraph(10, "Estado", FontFactory.getFont("arial", 9, Font.BOLD));
                    cellT3.setRowspan(2);
                    parT3.setAlignment(Element.ALIGN_CENTER);
                    cellT3.addElement(parT3);
                    //cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablaContenido.addCell(cellT3);

                    for (Asignatura asg : asignaturasAux) {
                        for (Periodo per : periodoEjb.getPeriodosByAnio(anlectivo)) {
                            PdfPCell cellT2 = new PdfPCell();
                            Paragraph parT2 = new Paragraph(10, "P" + per.getNumero(), FontFactory.getFont("arial", 9, Font.BOLD));
                            parT2.setAlignment(Element.ALIGN_CENTER);
                            cellT2.addElement(parT2);
                            //cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tablaContenido.addCell(cellT2);
                        }
                        PdfPCell cellT2 = new PdfPCell();
                        Paragraph parT2 = new Paragraph(10, "NF", FontFactory.getFont("arial", 9, Font.BOLD));
                        parT2.setAlignment(Element.ALIGN_CENTER);
                        cellT2.addElement(parT2);
                        //cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaContenido.addCell(cellT2);
                    }



                    matriculasAnio = matriculaEjb.matriculasCurso(cur);
                    for (Matricula maticula : matriculasAnio) {
                        Estudiante est = estdudianteEjb.find(maticula.getEstudianteId().getEstudianteId());
                        List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                        Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                        Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                        asignaturas = asignaturaEjb.findByCiclo(ciclo);
                        int perdidas = 0;
                        PdfPCell cellC1 = new PdfPCell();
                        Paragraph parC1 = new Paragraph(10, est.getApellido() + " " + est.getNombre(), FontFactory.getFont("arial", 10, Font.NORMAL));
                        parC1.setAlignment(Element.ALIGN_CENTER);
                        cellC1.addElement(parC1);
                        tablaContenido.addCell(cellC1);

                        for (Asignatura asignatura : asignaturas) {
                            for (Periodo per : periodoEjb.getPeriodosByAnio(anlectivo)) {
                                Contenidotematico cont = contenidoEjb.getByReporte(curso, asignatura, per);
                                PdfPCell cellC2 = new PdfPCell();
                                Paragraph parC2 = new Paragraph(10, this.getNotaEst(est, cont).toString(), FontFactory.getFont("arial", 10, Font.NORMAL));
                                parC2.setAlignment(Element.ALIGN_CENTER);
                                cellC2.addElement(parC2);
                                tablaContenido.addCell(cellC2);
                            }
                            Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                            Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                            BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
                            if (notafinalEst.getRecuperacion().compareTo("SI") == 0) {

                                //tiene recupeacion
                                Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
                                //queda sacar la nota y comprar para vere si pasa o no
                                PdfPCell cellC2 = new PdfPCell();
                                Paragraph parC2 = new Paragraph(10, "" + notaRecuperacion.getValor(), FontFactory.getFont("arial", 10, Font.NORMAL));
                                parC2.setAlignment(Element.ALIGN_CENTER);
                                cellC2.addElement(parC2);
                                tablaContenido.addCell(cellC2);
                                if (notaRecuperacion.getValor().compareTo(max) >= 0) {
                                    //paso
                                } else {
                                    //no paso
                                    perdidas++;
                                }

                            } else {
                                PdfPCell cellC2 = new PdfPCell();
                                Paragraph parC2 = new Paragraph(10, "" + notafinalEst.getValor(), FontFactory.getFont("arial", 10, Font.NORMAL));
                                parC2.setAlignment(Element.ALIGN_CENTER);
                                cellC2.addElement(parC2);
                                tablaContenido.addCell(cellC2);
                                //no tiene recuperacion
                                if (notafinalEst.getValor().compareTo(max) >= 0) {
                                    //paso
                                } else {
                                    //no paso
                                    perdidas++;
                                }
                            }
                        }
                        if (perdidas > criterio.getMinaprob()) {
                            //perdio año
                            PdfPCell cellC3 = new PdfPCell();
                            Paragraph parC3 = new Paragraph(10, "REPROBO", FontFactory.getFont("arial", 10, Font.NORMAL));
                            parC3.setAlignment(Element.ALIGN_CENTER);
                            cellC3.addElement(parC3);
                            tablaContenido.addCell(cellC3);
                        } else {
                            //gano el año
                            PdfPCell cellC4 = new PdfPCell();
                            Paragraph parC4 = new Paragraph(10, "APROBO", FontFactory.getFont("arial", 10, Font.NORMAL));
                            parC4.setAlignment(Element.ALIGN_CENTER);
                            cellC4.addElement(parC4);
                            tablaContenido.addCell(cellC4);
                        }
                    }
                    document.add(tablaContenido);
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
                    Faces.sendFile(baos.toByteArray(), "consolidadoFinal.pdf", false);
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
        }
    }

    private BigDecimal getNotaEst(Estudiante es, Contenidotematico contenido) {

        BigDecimal nota = new BigDecimal(0);
        Nota notaEst = new Nota();
        notaEst = estdudianteEjb.findNotaEst(contenido, es);
        if (notaEst != null) {
            nota = notaEst.getValor();
        }
        return nota.setScale(1, RoundingMode.HALF_EVEN);
    }

    public void initRender() {
        if (anlectivo != null) {
            if (periodoEjb.getNumeroPeriodosAño(anlectivo) == periodoEjb.getNumeroPeriodosTerminadosAño(anlectivo)) {
                this.mostrarBonton = true;
            } else {
                this.mostrarBonton = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Se debe terminar todos los periodos del año escolar"));
            }
        } else {
            this.mostrarBonton = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }
}
