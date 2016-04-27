/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.webservices;

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
import com.tesis.beans.CursoFacade;
import com.tesis.beans.EscalaFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.NotafinalFacade;
import com.tesis.beans.NotafinalrecuperacionFacade;
import com.tesis.clases.BackgroundF;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mario Jurado
 */
@WebService(serviceName = "WsNotas")
public class WsNotas {

    @EJB
    private EstudianteFacade estudianteEjb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private EscalaFacade escalaEjb;
    @EJB
    private CursoFacade cursoEjb;
    @EJB
    private CicloFacade cicloEjb;
    @EJB
    private AsignaturaFacade asignaturaEjb;
    @EJB
    private AsignaturacicloFacade asiganturaCicloEjb;
    @EJB
    private NotafinalFacade notaFinalEjb;
    @EJB
    private NotafinalrecuperacionFacade notaFinalRecEjb;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "getConstancia")
    public byte[] convertDocToByteArray(@WebParam(name = "estIden") String idest) {
        Estudiante est = estudianteEjb.getEstudianteByIdentificacion(idest);
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.out.println("MATIRCULAS " + est);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            //BackgroundF event = new BackgroundF();
            //writer.setPageEvent(event);
            document.open();
            System.out.println("MATIRCULAS 222" + est);
            //URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/escudo.png");
            //Image image = Image.getInstance(url);
            //image.scaleAbsolute(100, 100);
            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(90);
            table2.setWidths(new int[]{1, 4});
            PdfPCell cell = new PdfPCell();
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
            System.out.println("MATIRCULAS 111" + est);
            List<Matricula> matriculas = matriculaEjb.getMatriculasEstudiante(est);
            System.out.println("MATIRCULAS " + matriculas);
            if (!matriculas.isEmpty()) {
                for (Matricula maticula : matriculas) {
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

                    Anlectivo anlectivo = aEscolarEjb.find(maticula.getCursoId().getAnlectivoId().getAnlectivoId());
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
            System.out.println("ENTRO 3 ");
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        document.close();
        return (baos.toByteArray());



    }

    @WebMethod(operationName = "getEstudiante")
    public ObjRespuesta getEstudiante(@WebParam(name = "identificacion") String id) {
        ObjRespuesta obj = new ObjRespuesta();
        Estudiante estu = estudianteEjb.getEstudianteByIdentificacion(id);
        if (estu != null) {
            obj.setEstado("OK");
            obj.setEst(estu);
            List<Anlectivo> aEscolaresAux = new ArrayList<Anlectivo>();
            System.out.println("MATRICULAS" + matriculaEjb.getMatriculasEstudiante(estu));
            for (Matricula mat : matriculaEjb.getMatriculasEstudiante(estu)) {
                Anlectivo anAux = aEscolarEjb.find(mat.getCursoId().getAnlectivoId().getAnlectivoId());
                aEscolaresAux.add(anAux);
            }
            obj.setAescolares(aEscolaresAux);
        } else {
            obj.setEstado("FAIL");
        }
        return obj;
    }

    @WebMethod(operationName = "getNotas")
    public List<ObjRespuestaNota> getNotas(@WebParam(name = "idEst") String idEst, @WebParam(name = "idAn") int idAn) {
        System.out.println("NMNMNMaaaa"+idAn);
        List<ObjRespuestaNota> notas = new ArrayList<ObjRespuestaNota>();
        Anlectivo anlectivo = aEscolarEjb.find(idAn);
        if (anlectivo != null) {
            Estudiante est = estudianteEjb.getEstudianteByIdentificacion(idEst);
            if (est != null) {
                Matricula maticula = matriculaEjb.getAñoByEstudiante(est, anlectivo);
                if (maticula != null) {
                    Escala escala = escalaEjb.find(anlectivo.getConfiguracionId().getEscalaId().getEscalaId());
                    List<Asignatura> asignaturas = new ArrayList<Asignatura>();
                    Curso curso = cursoEjb.find(maticula.getCursoId().getCursoId());
                    Ciclo ciclo = cicloEjb.find(curso.getCicloId().getCicloId());
                    asignaturas = asignaturaEjb.findByCiclo(ciclo);
                    for (Asignatura asignatura : asignaturas) {
                        ObjRespuestaNota obj = new ObjRespuestaNota();
                        obj.setAsignatur(asignatura.getNombre());
                        Asignaturaciclo asg = asiganturaCicloEjb.asignaturasCiclo(ciclo, asignatura);
                        Notafinal notafinalEst = notaFinalEjb.findNotaFinalActual(asg, est, anlectivo);
                        BigDecimal max = new BigDecimal(escala.getNotaminimaaprob());
                        System.out.println("NMNMNM" + notafinalEst + "ESTUDIANTE " + est + "asignatura " + asg + "año escolar " + anlectivo + "MATRICULA " + maticula);
                        if (notafinalEst.getRecuperacion().compareTo("SI") == 0) {

                            //tiene recupeacion
                            Notafinalrecuperacion notaRecuperacion = notaFinalRecEjb.getNotaFinalRecuperar(notafinalEst);
                            //queda sacar la nota y comprar para vere si pasa o no
                            obj.setNota(notaRecuperacion.getValor().toString());

                        } else {
                            //no tiene recuperacion
                            obj.setNota(notafinalEst.getValor().toString());

                        }
                        notas.add(obj);
                    }
                }
            }
        }
        return notas;
    }
}
