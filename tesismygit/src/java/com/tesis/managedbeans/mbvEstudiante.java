/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.paramValueType;
import com.tesis.beans.DocumentoFacade;
import com.tesis.beans.EstadoEstudianteFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.MatriculaFacade;
import com.tesis.beans.TipoUsuarioFacade;
import com.tesis.beans.UsuarioRoleFacade;
import com.tesis.entity.Documento;
import com.tesis.entity.EstadoEstudiante;
import com.tesis.entity.Estudiante;
import com.tesis.entity.Matricula;
import com.tesis.entity.TipoUsuario;
import com.tesis.entity.Usuario;
import com.tesis.entity.UsuarioRole;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvEstudiante implements Serializable {

    private Estudiante estudiante;
    private List<Estudiante> estudiantes;
    private List<Documento> documentos;
    private Documento documento;
    private UploadedFile foto;
    private List<EstadoEstudiante> estadosEstudiante;
    private EstadoEstudiante estadoSelected;
    private String ruta;
    private boolean login;
    private boolean consultar;
    private boolean editar;
    private boolean crear;
    private boolean eliminar;
    private Usuario usr;
    String prueba;
    private String identificacionAnterior;
    @EJB
    private EstudianteFacade estudianteEjb;
    @EJB
    private EstadoEstudianteFacade estadoEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    @EJB
    private DocumentoFacade docuemntoEjb;
    @EJB
    private MatriculaFacade matriculaEjb;
    @EJB
    private UsuarioRoleFacade usrRoleEjb;

    public mbvEstudiante() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public boolean isCrear() {
        return crear;
    }

    public List<EstadoEstudiante> getEstadosEstudiante() {
        return estadosEstudiante;
    }

    public void setEstadosEstudiante(List<EstadoEstudiante> estadosEstudiante) {
        this.estadosEstudiante = estadosEstudiante;
    }

    public EstadoEstudiante getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(EstadoEstudiante estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public EstudianteFacade getEstudianteEjb() {
        return estudianteEjb;
    }

    public void setEstudianteEjb(EstudianteFacade estudianteEjb) {
        this.estudianteEjb = estudianteEjb;
    }

    @PreDestroy
    public void a() {
        System.out.println("BBBBBBBBBBBBBBBB");
    }

    @PostConstruct
    public void inicioPagina() {
        System.out.println("AAAAAAAAAAAAAAA" + this.prueba);
        this.estudiante = new Estudiante();
        this.estadoSelected = new EstadoEstudiante();
        this.estadosEstudiante = new ArrayList<EstadoEstudiante>();
        this.estadosEstudiante = estadoEjb.findAll();
        this.documento = new Documento();
        this.estudiantes = this.estudianteEjb.getOrdenados();
        this.foto = new DefaultUploadedFile();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        this.ruta = (String) servletContext.getRealPath("/");
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
                if (usrRol.getRoleId().getRecursoId().getRecursoId() == 7) {
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
    }

    public void insertar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.crear) {
                if (estudianteEjb.existeIdentificacion(this.estudiante.getIdentificiacion())) {
                    //RequestContext.getCurrentInstance().closeDialog(null);
                    FacesContext.getCurrentInstance().
                            addMessage("frmProfesor:txtIdentificacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Identificacion en uso"));
                    return;
                }
                EstadoEstudiante estado = estadoEjb.find(1);
                TipoUsuario tusu = tusuEjb.find(1);
                System.out.println("ooOO" + estado.getNombre());
                this.estudiante.setEstadoEstudianteId(estado);
                this.estudiante.setTipoUsuarioId(tusu);
                System.out.println("ESTUDIANTE: " + estudiante.getEstudianteId());
                InputStream inputStream = null;
                OutputStream outputStream = null;
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String carpetaEstudiantes = (String) servletContext.getRealPath("/fotosestudiantes");
                try {
                    if (this.foto.getSize() <= 0) {
                        this.estudiante.setFoto("default.png");
                    } else {
                        if (!this.foto.getFileName().endsWith(".png") || !this.foto.getFileName().endsWith(".jpg")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\" o \".jpg\" "));
                            return;
                        }
                        if (this.foto.getSize() > 20971520) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 20mb"));
                            return;
                        }
                        String nombreArchivo = "/";
                        nombreArchivo += this.estudiante.getIdentificiacion();
                        Calendar fecha = new GregorianCalendar();
                        int año = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH);
                        int dia = fecha.get(Calendar.DAY_OF_MONTH);
                        int hora = fecha.get(Calendar.HOUR_OF_DAY);
                        int minuto = fecha.get(Calendar.MINUTE);
                        int segundo = fecha.get(Calendar.SECOND);
                        int milseg = fecha.get(Calendar.MILLISECOND);
                        nombreArchivo += año + "-" + mes + "-" + dia + "-" + hora + "-" + minuto + "-" + segundo + "-" + milseg;
                        Random nrd = new Random();
                        nombreArchivo += nrd.nextInt() + ".png";
                        outputStream = new FileOutputStream(new File(carpetaEstudiantes + nombreArchivo));
                        inputStream = this.foto.getInputstream();

                        int read = 0;
                        byte[] bytes = new byte[1024];

                        while ((read = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                        this.estudiante.setFoto(nombreArchivo);
                    }

                    System.out.println("ESTUDIANTE: " + this.estudiante);
                    this.estudianteEjb.create(estudiante);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                    /*FacesContext.getCurrentInstance()
                     .getExternalContext()
                     .getFlash()
                     .put("param1", this.estudiante);
                     */
                    RequestContext.getCurrentInstance().closeDialog(this.estudiante);
                    inicioPagina();
                    //ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
                    //context.redirect("/tesismygit/faces/academico/matriculas.xhtml");

                    //return "matriculas.xhtml?faces-redirect=true";
                    //return "/academico/matriculas.xhtml?faces-redirect=true&includeViewParams=true";
                } catch (Exception ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
                }
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
        //return ;

    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        System.out.println("mm" + event.getFile().getFileName());
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            if (event.getFile().getSize() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }

            if (!event.getFile().getFileName().endsWith(".pdf")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                return;
            }

            if (event.getFile().getSize() > 2097152) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }

            String nombreArchivo = this.estudiante.getIdentificiacion();
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            int minuto = fecha.get(Calendar.MINUTE);
            int segundo = fecha.get(Calendar.SECOND);
            int milseg = fecha.get(Calendar.MILLISECOND);
            nombreArchivo += "_" + año + "_" + mes + "_" + dia + "_" + hora + "_" + minuto + "_" + segundo + "_" + milseg;
            Random nrd = new Random();
            nombreArchivo += nrd.nextInt() + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf("."));
            System.out.println(" NMBRE :" + nombreArchivo);
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaAvatar = (String) servletContext.getRealPath("/avatar");

            outputStream = new FileOutputStream(new File("C:\\temporal\\" + nombreArchivo));
            inputStream = event.getFile().getInputstream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            Documento documentoNuevo = new Documento();
            documentoNuevo.setEstudianteId(this.estudiante);
            documentoNuevo.setRutaarchivo(nombreArchivo);
            documentoNuevo.setNombre(event.getFile().getFileName());
            docuemntoEjb.create(documentoNuevo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Avatar actualizado correctamente"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void subirFoto(FileUploadEvent event) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String carpetaEstudiantes = (String) servletContext.getRealPath("/fotosestudiantes");
        File file = new File(carpetaEstudiantes + estudiante.getFoto());
        //file.delete();

        try {
            System.out.println("ENTRA 2" + event.getFile().getFileName());
            if (event.getFile().getSize() <= 0) {
            } else {
                System.out.println("ENTRA 4" + event.getFile().getFileName().endsWith(".jpg"));
                boolean ban = false;
                if (event.getFile().getFileName().endsWith(".jpg") == true) {
                    ban = true;
                }
                if (event.getFile().getFileName().endsWith(".png") == true) {
                    ban = true;
                }
                if (ban == false) {
                    System.out.println("ENTRA 5" + event.getFile().getFileName());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\" o \".jpg\" "));
                    //inicioPagina();  
                    return;
                }
                if (event.getFile().getSize() > 20971520) {
                    System.out.println("ENTRA 6");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 20mb"));
                    return;
                }
                if (file.delete()) {
                    System.out.println("ENTRA 7");
                    String nombreArchivo = "/";
                    nombreArchivo += this.estudiante.getIdentificiacion();
                    Calendar fecha = new GregorianCalendar();
                    int año = fecha.get(Calendar.YEAR);
                    int mes = fecha.get(Calendar.MONTH);
                    int dia = fecha.get(Calendar.DAY_OF_MONTH);
                    int hora = fecha.get(Calendar.HOUR_OF_DAY);
                    int minuto = fecha.get(Calendar.MINUTE);
                    int segundo = fecha.get(Calendar.SECOND);
                    int milseg = fecha.get(Calendar.MILLISECOND);
                    nombreArchivo += año + "-" + mes + "-" + dia + "-" + hora + "-" + minuto + "-" + segundo + "-" + milseg;
                    Random nrd = new Random();
                    nombreArchivo += nrd.nextInt() + ".png";
                    outputStream = new FileOutputStream(new File(carpetaEstudiantes + nombreArchivo));
                    inputStream = event.getFile().getInputstream();

                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    this.estudiante.setFoto(nombreArchivo);
                    System.out.println("ENTRA 8");
                    estudianteEjb.edit(estudiante);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto actualizada exitosamente", ""));
                    //RequestContext.getCurrentInstance().execute("PF('dialogoEditarEstudiante').hide()");
                    //inicioPagina();
                } else {
                    System.out.println("Delete operation is failed.");
                }
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        } finally {
            if (inputStream != null) {
                System.out.println("ENTRA 10");
                inputStream.close();
            }

            if (outputStream != null) {
                System.out.println("ENTRA 10");
                outputStream.close();
            }
        }

    }
    private DefaultStreamedContent download;

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public DefaultStreamedContent getDownload() throws Exception {
        System.out.println("GET = " + download.getName());
        return download;
    }

    public void FileDownloadView() throws FileNotFoundException {
        File file = new File("C:\\temporal\\1.pdf");
        //file.delete();
        try {

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        /*InputStream input = new FileInputStream(file);
         ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
         setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
         System.out.println("PREP = " + download.getName());*/
    }
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Actions ------------------------------------------------------------------------------------
    public void downloadPDF(String ruta) throws IOException {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file = new File("C:\\temporal", ruta);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");//image/jpeg
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + ruta + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception.  Print it, log it or mail it.  It may be useful to
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }

    public String pp() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("param1", "hola");

        return "matriculas.xhtml?faces-redirect=true";
    }

    public String cargarMatricula(Estudiante es) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("param1", es);

        return "matriculas.xhtml?faces-redirect=true";
    }

    public void newEstudiante() {
        if (!login) {
            System.out.println("Usuario NO logeado");
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
            return;
        }
        if (this.crear) {
            Map<String, Object> options = new HashMap<String, Object>();
            /*options.put("contentHeight", 650);
             options.put("height", 660);*/
            options.put("contentWidth", 890);
            options.put("width", 900);
            options.put("modal", true);
            options.put("maximizable", true);
            options.put("draggable", true);
            options.put("resizable", true);
            RequestContext.getCurrentInstance().openDialog("newestudiante", options, null);
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
        }
    }

    public void cargarEstudiante(int estudianteid) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.editar) {
                this.estudiante = estudianteEjb.find(estudianteid);
                this.identificacionAnterior = this.estudiante.getIdentificiacion();
                this.estadoSelected = estadoEjb.find(estudiante.getEstadoEstudianteId().getEstadoEstudianteId());
                RequestContext.getCurrentInstance().update("frmEditarEstudiante:panelEditarEstudiante");
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEstudiante').show()");
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void subirArchivos(int estudianteid) {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.editar) {
                this.estudiante = estudianteEjb.find(estudianteid);
                //RequestContext.getCurrentInstance().update("frmArchivos:panelEditarEstudiante");
                RequestContext.getCurrentInstance().execute("PF('dialogoSubirArchivos').show()");
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void examinarArchivos(int estudianteid) {
        try {
            this.estudiante = estudianteEjb.find(estudianteid);
            this.documentos = this.docuemntoEjb.getByEstudiante(estudiante);
            RequestContext.getCurrentInstance().update("frmArchivosView:panelArchivosEstudiante");
            RequestContext.getCurrentInstance().execute("PF('dialogoExaminarArchivos').show()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public String getMatricula(Estudiante estAux) {
        Matricula auxMatricula = matriculaEjb.getActivaByEstudiante(estAux);
        if (auxMatricula != null) {
            return "MATRICULADO";
        } else {
            if (estAux.getUltimoaprobado() > 5) {
                return "GRADUADO";
            } else {
                return "PENDIENTE";
            }
        }
    }

    public void editar() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }
            if (this.editar) {
                if (estudianteEjb.existeIdentificacion(this.estudiante.getIdentificiacion()) && !this.estudiante.getIdentificiacion().equals(identificacionAnterior)) {
                    FacesContext.getCurrentInstance().
                            addMessage("frmEditarEstudiante:txtIdentificacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Identificacion en uso"));
                    return;
                }
                System.out.println("ENTRA 8");
                this.estadoSelected = estadoEjb.find(estadoSelected.getEstadoEstudianteId());
                estudiante.setEstadoEstudianteId(estadoSelected);
                estudianteEjb.edit(estudiante);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estudiante editado exitosamente", ""));
                RequestContext.getCurrentInstance().execute("PF('dialogoEditarEstudiante').hide()");
                inicioPagina();
            } else {
                System.out.print("error permiso denegado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para esta accion"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void redirect() {
        RequestContext.getCurrentInstance().closeDialog("hola");
        System.out.println("Entra 1");
        /* System.out.println("Entra 1");
         final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
         FacesContext ctx = FacesContext.getCurrentInstance();        
         ExternalContext extContext = ctx.getExternalContext();
         String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/tesismygit/faces/academico/matriculas.xhtml"));
         System.out.println("Entra 2");
         */

    }

    public void closeDialog(SelectEvent event) {
        Estudiante estRe = (Estudiante) event.getObject();
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("param1", estRe);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect("/tesismygit/faces/academico/matriculas.xhtml");
            //return "matriculas.xhtml?faces-redirect=true";
        } catch (IOException ex) {
            Logger.getLogger(mbvEstudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initRender() {
        if (!this.consultar) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "usted no tiene permisos para manejar criterios de evaluacion"));
        }
    }
}
