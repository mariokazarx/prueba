/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.EstadoEstudianteFacade;
import com.tesis.beans.EstudianteFacade;
import com.tesis.beans.TipoUsuarioFacade;
import com.tesis.entity.EstadoEstudiante;
import com.tesis.entity.Estudiante;
import com.tesis.entity.TipoUsuario;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvEstudiante implements Serializable{

    private Estudiante estudiante;
    private List<Estudiante> estudiantes;
    private UploadedFile foto;
    private String ruta;
    
    @EJB
    private EstudianteFacade estudianteEjb;
    @EJB
    private EstadoEstudianteFacade estadoEjb;
    @EJB
    private TipoUsuarioFacade tusuEjb;
    
    public mbvEstudiante() {
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

    
    @PostConstruct
    public void inicioPagina(){
       this.estudiante = new Estudiante();
       this.estudiantes = this.estudianteEjb.findAll();
       ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
       this.ruta=(String)servletContext.getRealPath("/");

    }
    public void insertar(){
        try{
            EstadoEstudiante estado = estadoEjb.find(1);
            TipoUsuario tusu = tusuEjb.find(1);
            System.out.println("ooOO"+estado.getNombre());
            this.estudiante.setEstadoEstudianteId(estado);
            this.estudiante.setTipoUsuarioId(tusu);
            System.out.println("ESTUDIANTE: "+estudiante.getEstudianteId());
            InputStream inputStream=null;
            OutputStream outputStream=null;
            ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaEstudiantes=(String)servletContext.getRealPath("/fotosestudiantes");
            try
            {
                if(this.foto.getSize()<=0)
                {
                    this.estudiante.setFoto("default.png");
                }else{
                    if(!this.foto.getFileName().endsWith(".png"))
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                        return;
                    }
                    if(this.foto.getSize()>20971520)
                    {
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
                    nombreArchivo+= año+"-"+mes+"-"+dia+"-"+hora+"-"+minuto+"-"+segundo+"-"+milseg;
                    Random nrd = new Random();
                    nombreArchivo+= nrd.nextInt()+".png";
                    outputStream=new FileOutputStream(new File(carpetaEstudiantes+nombreArchivo));
                    inputStream=this.foto.getInputstream();

                    int read=0;
                    byte[] bytes=new byte[1024];

                    while((read=inputStream.read(bytes))!=-1)
                    {
                        outputStream.write(bytes, 0, read);
                    }
                    this.estudiante.setFoto(nombreArchivo);
                }
                
                System.out.println("ESTUDIANTE: "+this.estudiante);
                this.estudianteEjb.create(estudiante);
                FacesContext.getCurrentInstance().
                       addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criterio Evaluacion creado Satisfactoriamente", ""));
                inicioPagina();
            }
            catch(Exception ex)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
            }
            
        }catch(Exception e){
             FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
        
    }
    public void handleFileUpload(FileUploadEvent event) throws IOException {    
        System.out.println("mm"+event.getFile().getFileName());
        InputStream inputStream=null;
        OutputStream outputStream=null;
        
        try
        {
            if(event.getFile().getSize()<=0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }
            
            if(!event.getFile().getFileName().endsWith(".pdf"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                return;
            }
            
            if(event.getFile().getSize()>2097152)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }
            
            ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaAvatar=(String)servletContext.getRealPath("/avatar");
            
            outputStream=new FileOutputStream(new File("C:\\temporal\\"+event.getFile().getFileName()));
            inputStream=event.getFile().getInputstream();
            
            int read=0;
            byte[] bytes=new byte[1024];
            
            while((read=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes, 0, read);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Avatar actualizado correctamente"));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            
            if(outputStream!=null)
            {
                outputStream.close();
            }
        }
    }
    public void subirFoto() throws IOException{
        InputStream inputStream=null;
        OutputStream outputStream=null;
        
        try
        {
            if(this.foto.getSize()<=0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }
            
            if(!this.foto.getFileName().endsWith(".png"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                return;
            }
            
            if(this.foto.getSize()>2097152)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }
            
            ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaAvatar=(String)servletContext.getRealPath("/avatar");
            
            outputStream=new FileOutputStream(new File(carpetaAvatar+"/prueba.png"));
            inputStream=this.foto.getInputstream();
            
            int read=0;
            byte[] bytes=new byte[1024];
            
            while((read=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes, 0, read);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Avatar actualizado correctamente"));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            
            if(outputStream!=null)
            {
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
        try{
 
        if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
        }else{
                System.out.println("Delete operation is failed.");
        }

    	}catch(Exception e){
 
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

         File file = new File("C:\\temporal",ruta);
         BufferedInputStream input = null ;
         BufferedOutputStream output = null ;

         try {
             // Open file.
             input = new BufferedInputStream( new FileInputStream(file), DEFAULT_BUFFER_SIZE );

             // Init servlet response.
             response.reset();
             response.setHeader( "Content-Type" , "application/pdf" );
             response.setHeader( "Content-Length" , String. valueOf (file.length()));
             response.setHeader( "Content-Disposition" , "inline; filename=\"" +ruta + "\"" );
             output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE );

             // Write file contents to response.
             byte [] buffer = new byte [ DEFAULT_BUFFER_SIZE ];
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
         if (resource != null ) {
             try {
                 resource.close();
             } catch (IOException e) {
                 // Do your thing with the exception.  Print it, log it or mail it.  It may be useful to
                 // know that this will generally only be thrown when the client aborted the download.
                 e.printStackTrace();
             }
         }
     }
}
