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
import com.tesis.clases.ReporteBoletin;
import com.tesis.entity.Anlectivo;
import com.tesis.entity.Contenidotematico;
import com.tesis.entity.Curso;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Nota;
import com.tesis.entity.Periodo;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;
import org.omnifaces.util.Faces;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteConsolidadoPeriodo implements Serializable{
    private static final long serialVersionUID = 7298959117010728707L;

    private List<Curso> cursos;
    private Curso cursoSelected;
    private List<Curso> cursosReporte;
    private boolean mostrarPeriodos;
    private boolean mostrarCursos;
    private String seleccion;
    private List<Periodo> periodos;
    private Periodo periodoSelected;
    private Anlectivo aEscolar;
    private boolean mostrarPrincipal;
    private boolean mostrarOpciones;
    private boolean mostrarBoton;
    //private List<MatriculaReporte> reporte;
    private List<ReporteBoletin> reporte;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    @EJB
    private CicloFacade cicloEjb;
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
    private PeriodoFacade periodoEJb;
    @EJB
    private ContenidotematicoFacade contenidoEJb;
    @EJB
    private AnlectivoFacade aEscolarEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;
    private JasperPrint jasperPrint;

    /**
     * Creates a new instance of mbvReporteMatriculas
     */
    public mbvReporteConsolidadoPeriodo() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    public boolean isMostrarPeriodos() {
        return mostrarPeriodos;
    }

    public void setMostrarPeriodos(boolean mostrarPeriodos) {
        this.mostrarPeriodos = mostrarPeriodos;
    }

    public boolean isMostrarCursos() {
        return mostrarCursos;
    }

    public void setMostrarCursos(boolean mostrarCursos) {
        this.mostrarCursos = mostrarCursos;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public Periodo getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(Periodo periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarOpciones() {
        return mostrarOpciones;
    }

    public void setMostrarOpciones(boolean mostrarOpciones) {
        this.mostrarOpciones = mostrarOpciones;
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
        //verificar permisos
        //verificar año iniciado
        //verificar cursos
        this.seleccion = "";
        this.cursos = new ArrayList<Curso>();
        this.cursosReporte = new ArrayList<Curso>();
        this.mostrarCursos = false;
        this.mostrarPeriodos = false;
        this.mostrarBoton = false;
        this.mostrarOpciones = false;
        this.periodos = new ArrayList<Periodo>();
        this.cursoSelected = new Curso();
        this.periodoSelected = new Periodo();
        this.cursos.clear();
        this.aEscolar = aEscolarEjb.getIniciado();
        if (aEscolar != null) {
            //hay año iniciado
            this.periodos = periodoEjb.getPeriodosByAnioTerminados(aEscolar);
            if (this.periodos.isEmpty()) {
                this.mostrarPrincipal = false;
                //no hay periodos
            } else {
                //this.periodos = periodoEJb.getPeriodosByAnio(aEscolar);
                this.mostrarPeriodos = true;
                this.mostrarPrincipal = true;
                //this.cursos = aEscolar.getCursoList();
            }
        } else {
            this.mostrarPrincipal = false;
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

    public void prueba() {
        List<Object> estudiantesPromedio = new ArrayList<Object>();
        Curso cur = cursoEjb.find(cursoSelected.getCursoId());
        Periodo periodo = periodoEjb.find(15);
        estudiantesPromedio = estudianteEjB.getFinalPeriodo(cur, periodo);
        for (Object p : estudiantesPromedio) {
            Object[] row = (Object[]) p;
            for (int i = 0; i < row.length; i++) {
                if (row[i] instanceof Estudiante) {
                    Estudiante est = (Estudiante) row[i];
                }
            }
        }
    }

    public void imprimir() {
        if (!login) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            List<Object> estudiantesPromedio = new ArrayList<Object>();
            List<Curso> cursos22 = new ArrayList<Curso>();
            Document document = new Document(PageSize.LEGAL.rotate());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                BackgroundF event = new BackgroundF();
                writer.setPageEvent(event);
                //METADATA
                document.open();
                if (cursoSelected != null && this.seleccion.compareTo("curso") == 0) {
                    cursoSelected = cursoEjb.find(cursoSelected.getCursoId());
                    this.cursosReporte.add(cursoSelected);
                }
                if (this.seleccion.compareTo("todos") == 0) {
                    this.cursosReporte.clear();
                    Anlectivo auxEscolar = aEscolarEjb.getIniciado();
                    if (!cursoEjb.getCursosByAño(aEscolar).isEmpty()) {//auxEscolar.getCursoList().isEmpty()
                        this.cursosReporte = cursoEjb.getCursosByAño(aEscolar);//auxEscolar.getCursoList();
                    }
                }
                for (Curso curPrueba : cursosReporte) {
                    Curso cur = cursoEjb.find(curPrueba.getCursoId());
                    Periodo periodo = periodoEjb.find(periodoSelected.getPeriodoId());
                    estudiantesPromedio = estudianteEjB.getFinalPeriodo(cur, periodo);


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
                    Paragraph par3 = new Paragraph(10, "Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                            + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 ", FontFactory.getFont("arial", 9, Font.NORMAL));
                    par3.setAlignment(Element.ALIGN_CENTER);
                    cell3.addElement(par3);
                    cell3.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    table2.addCell(cell3);
                    document.add(table2);
                    //document.add(image); 
                    document.add(new Paragraph(10,"Reporte consolidado periodo "+periodoSelected.getNumero()+" curso "+curPrueba.getNombre()+ " año escolar "+aEscolar.getAnio()));
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss:");
                    Date currentDate = new Date();
                    String date = formatter.format(currentDate);
                    document.add(new Paragraph("Fecha de generación: " + date));
                    document.add(new Paragraph("\n"));
                    List<Contenidotematico> contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
                    int tam = 2 + contenidos.size();
                    PdfPTable table = new PdfPTable(2 + contenidos.size());
                    table.setWidthPercentage(100);
                    float[] ft = new float[tam];
                    for (int i = 0; i < tam; i++) {
                        if (i == 0) {
                            ft[i] = 2f;
                        } else if (i == tam - 1) {
                            ft[i] = 0.5f;
                        } else {
                            ft[i] = 1f;
                        }
                    }
                    table.setTotalWidth(ft);
                    PdfPCell cellT1 = new PdfPCell();
                    Paragraph parT1 = new Paragraph(10, "ESTUDIANTE", FontFactory.getFont("arial", 8, Font.BOLD));
                    parT1.setAlignment(Element.ALIGN_CENTER);
                    cellT1.addElement(parT1);
                    cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellT1);
                    for (Contenidotematico con : contenidos) {
                        PdfPCell cellT2 = new PdfPCell();
                        Paragraph parT2 = new Paragraph(10, con.getAsignaturacicloId().getAsignaturaId().getNombre(), FontFactory.getFont("arial", 8, Font.BOLD));
                        parT2.setAlignment(Element.ALIGN_CENTER);
                        cellT2.addElement(parT2);
                        //cellT2.setRotation(90);
                        //cellT2.setFixedHeight(100);
                        cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cellT2);
                    }
                    PdfPCell cellT3 = new PdfPCell();
                    Paragraph parT3 = new Paragraph(10, "Promedio", FontFactory.getFont("arial", 8, Font.BOLD));
                    parT3.setAlignment(Element.ALIGN_CENTER);
                    cellT3.addElement(parT3);
                    cellT1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cellT3);
                    for (Object p : estudiantesPromedio) {
                        Object[] row = (Object[]) p;
                        for (int i = 0; i < row.length; i++) {
                            if (row[i] instanceof Estudiante) {
                                Estudiante est = (Estudiante) row[i];
                                PdfPCell cellC1 = new PdfPCell();
                                Paragraph parC1 = new Paragraph(10, est.getApellido() + " " + est.getNombre(), FontFactory.getFont("arial", 9, Font.NORMAL));
                                //parC1.setAlignment(Element.ALIGN_CENTER);
                                cellC1.addElement(parC1);
                                table.addCell(cellC1);
                                for (Contenidotematico cont : contenidos) {
                                    PdfPCell cellC2 = new PdfPCell();
                                    Paragraph parC2 = new Paragraph(10, getNotaEst(est, cont).toString(), FontFactory.getFont("arial", 10, Font.NORMAL));
                                    parC2.setAlignment(Element.ALIGN_CENTER);
                                    cellC2.addElement(parC2);
                                    table.addCell(cellC2);
                                }
                            }
                            if (row[i] instanceof Double) {
                                BigDecimal nota = new BigDecimal(row[i].toString());
                                nota = nota.setScale(1, RoundingMode.HALF_EVEN);
                                PdfPCell cellC3 = new PdfPCell();
                                Paragraph parC3 = new Paragraph(10, nota.toString(), FontFactory.getFont("arial", 10, Font.NORMAL));
                                parC3.setAlignment(Element.ALIGN_CENTER);
                                cellC3.addElement(parC3);
                                table.addCell(cellC3);
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
                    document.newPage();
                }
            } catch (Exception ex) {
            }

            document.close();
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                try {
                    HttpServletResponse hsr = (HttpServletResponse) response;
                    hsr.reset();
                    Faces.sendFile(baos.toByteArray(), "ConsolidadoPeriodo.pdf", false);
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

    public void cargarPeriodo() {
        if (this.periodoSelected.getPeriodoId() != null) {
            this.periodoSelected = periodoEJb.find(periodoSelected.getPeriodoId());
            //this.cursos.clear();
            this.mostrarOpciones = true;
            this.mostrarCursos = false;
            this.cursoSelected = new Curso();
            if (this.seleccion.compareTo("todos") == 0) {
                this.mostrarBoton = true;
            } else {
                this.mostrarBoton = false;
            }
            if (this.seleccion.compareTo("curso") == 0) {
                this.cursoSelected = new Curso();
                this.cursosReporte.clear();
                this.cursos = cursoEjb.getCursosByAño(aEscolar);//aEscolar.getCursoList();
                this.mostrarCursos = true;
                this.mostrarBoton = false;
            }
        } else {
            this.mostrarBoton = false;
            this.mostrarOpciones = false;
            this.mostrarCursos = false;
            this.cursoSelected = new Curso();
            this.cursos.clear();
        }
    }

    public void cargarCurso() {
        if (this.seleccion.compareTo("todos") == 0) {
            Anlectivo auxEscolar = aEscolarEjb.getIniciado();
            this.cursosReporte.clear();
            this.cursosReporte = cursoEjb.getCursosByAño(aEscolar);//auxEscolar.getCursoList();
            this.cursoSelected = new Curso();
            this.mostrarBoton = true;
            this.mostrarCursos = false;
        }
        if (this.seleccion.compareTo("curso") == 0) {
            this.cursoSelected = new Curso();
            this.cursosReporte.clear();
            this.cursos = cursoEjb.getCursosByAño(aEscolar);//aEscolar.getCursoList();
            this.mostrarCursos = true;
            this.mostrarBoton = false;
        }
    }

    public void seleccionCurso() {
        if (this.cursoSelected.getCursoId() != null) {
            this.mostrarBoton = true;
        } else {
            this.mostrarBoton = false;
        }
    }

    public void initRender() {
        this.aEscolar = aEscolarEjb.getIniciado();
        if (aEscolar != null) {
            if (cursoEjb.getCursosByAño(aEscolar).isEmpty()) {//aEscolar.getCursoList().isEmpty()
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            }
            if (periodoEJb.getPeriodosByAnio(aEscolar).isEmpty()) {//aEscolar.getPeriodoList().isEmpty()
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado periodos"));
            }
            if (!periodoEJb.getPeriodosByAnio(aEscolar).isEmpty() && !cursoEjb.getCursosByAño(aEscolar).isEmpty()) {//aEscolar.getPeriodoList().isEmpty() && !aEscolar.getCursoList().isEmpty()
                if (!this.consultar) {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para generar reportes"));
                }
                this.mostrarPeriodos = true;
                this.mostrarPrincipal = true;
                //this.cursos = aEscolar.getCursoList();
            }
        } else {
            this.mostrarPrincipal = false;
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha iniciado el año escolar"));
        }
    }
}
