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
import com.tesis.clases.ContenidoBoletin;
import com.tesis.clases.ReporteAsignacionAcademica;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvReporteConsolidadoPeriodo {

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
            System.out.println("usuario" + usr.getNombres() + "Login" + login);
        } catch (Exception e) {
            System.out.println(e.toString());
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
                    System.out.println("OBJ22 " + est.getNombre());
                }
                System.out.println("OBJ22 " + row[i]);
            }
        }
    }

    public void imprimir() {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.consultar) {
            System.out.println("ENTRO 1 ");
            List<Object> estudiantesPromedio = new ArrayList<Object>();
            List<Curso> cursos22 = new ArrayList<Curso>();
            Document document = new Document(PageSize.LEGAL.rotate());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                System.out.println("ENTRO 2 ");
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
                    if (!auxEscolar.getCursoList().isEmpty()) {
                        this.cursosReporte = auxEscolar.getCursoList();
                    }
                }
                System.out.println("ENTRO 222 " + this.cursosReporte.size());
                for (Curso curPrueba : cursosReporte) {

                    System.out.println("ENTRO for ");
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
                    Paragraph par3 = new Paragraph(10,"Licencia de funcionamiento 366 de Abril 4 de 2001, Resolución 1861  Junio 30 de 2005 de  la Secretaría "
                            + "de Educación y Cultura Departamental  CODIGO SNP ICFES 114454 Código DANE 352612001093 ", FontFactory.getFont("arial", 9, Font.NORMAL));
                    par3.setAlignment(Element.ALIGN_CENTER);
                    cell3.addElement(par3);
                    cell3.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    table2.addCell(cell3);
                    document.add(table2);
                    //document.add(image); 
                    document.add(new Paragraph("ESTUDIANTES INSCRITOS \n"));
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yy '-' hh:mm:ss:");
                    Date currentDate = new Date();
                    String date = formatter.format(currentDate);
                    document.add(new Paragraph("Fecha Generado: " + date));
                    document.add(new Paragraph("\n"));
                    List<Contenidotematico> contenidos = contenidoEJb.getByPeriodoCurso(periodo, cur);
                    int tam = 2 + contenidos.size();
                    PdfPTable table = new PdfPTable(2 + contenidos.size());
                    table.setWidthPercentage(100);
                    float[] ft = new float[tam];
                    for (int i = 0; i < tam; i++) {
                        System.out.println(i);
                        if (i == 0) {
                            ft[i] = 2f;
                        } else if (i == tam - 1) {
                            ft[i] = 0.5f;
                        } else {
                            ft[i] = 1f;
                        }
                    }
                    table.setTotalWidth(ft);
                    table.addCell("ESTUDIANTE");
                    for (Contenidotematico con : contenidos) {
                        table.addCell(con.getAsignaturacicloId().getAsignaturaId().getNombre());
                    }
                    table.addCell("Promedio");
                    for (Object p : estudiantesPromedio) {
                        Object[] row = (Object[]) p;
                        for (int i = 0; i < row.length; i++) {
                            if (row[i] instanceof Estudiante) {
                                Estudiante est = (Estudiante) row[i];
                                table.addCell(est.getApellido() + " " + est.getNombre());
                                for (Contenidotematico cont : contenidos) {
                                    table.addCell(getNotaEst(est, cont).toString());
                                }
                            }
                            if (row[i] instanceof Double) {
                                BigDecimal nota = new BigDecimal(row[i].toString());
                                nota = nota.setScale(1, RoundingMode.HALF_EVEN);
                                table.addCell(nota.toString());
                            }
                        }
                    }
                    document.add(table);
                    document.newPage();
                }
            } catch (Exception ex) {
                System.out.println("ENTRO 3 ");
                System.out.println("Error " + ex.getMessage());
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
        } else {
            System.out.print("error permiso denegado");
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
                this.cursos = aEscolar.getCursoList();
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
            this.cursosReporte = auxEscolar.getCursoList();
            this.cursoSelected = new Curso();
            this.mostrarBoton = true;
            this.mostrarCursos = false;
        }
        if (this.seleccion.compareTo("curso") == 0) {
            this.cursoSelected = new Curso();
            this.cursosReporte.clear();
            this.cursos = aEscolar.getCursoList();
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
            if (aEscolar.getCursoList().isEmpty()) {
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado cursos"));
            }
            if (aEscolar.getPeriodoList().isEmpty()) {
                this.mostrarPrincipal = false;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Aun no ha han registrado periodos"));
            }
            if (!aEscolar.getPeriodoList().isEmpty() && !aEscolar.getCursoList().isEmpty()) {
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
