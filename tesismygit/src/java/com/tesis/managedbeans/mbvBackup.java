/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.entity.Documento;
import com.tesis.entity.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvBackup implements Serializable {

    private static final long serialVersionUID = -8504807238993505325L;
    private StreamedContent file;
    private boolean consultar;
    private boolean login;
    private Usuario usr;

    /**
     * Creates a new instance of mbvBackup
     */
    public mbvBackup() {
    }

    public boolean isConsultar() {
        return consultar;
    }

    public StreamedContent getFile() {
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            ProcessBuilder pb;
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump.exe",
                    "-i",
                    "-h", "localhost",
                    "-p", "5432",
                    "-U", "postgres",
                    "-F", "c", "-b", "-v",
                    "-f", "C:\\backup\\copia.backup",
                    "prueba");
            pb.environment().put("PGPASSWORD", "123");
            pb.redirectErrorStream(true);
            p = pb.start();
            InputStream is;
            is = new FileInputStream("C:\\backup\\copia.backup");
            file = new DefaultStreamedContent(is, "application/octet-stream", "copia.backup");
            return file;
        } catch (IOException ex) {
            System.out.println("MMM " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(mbvBackup.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @PostConstruct
    public void inicioPagina() {
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usr = mbslogin.getUsuario();
            this.login = mbslogin.isLogin();
        } catch (Exception e) {
            this.login = false;
        }
    }

    public void restore() {
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            ProcessBuilder pb;
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_restore.exe",
                    "-h", "localhost",
                    "-p", "5432",
                    "-U", "postgres",
                    "-d", "prueba",
                    "-c",
                    "-v", "C:\\backup\\restore.backup");
            pb.environment().put("PGPASSWORD", "123");
            pb.redirectErrorStream(true);
            p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
                // System.out.println(ll);
            }
        } catch (IOException ex) {
            Logger.getLogger(mbvBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void restaurar() {
        try {
            ProcessBuilder pbuilder;
            //Realiza la construccion del comando
            pbuilder = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_restore.exe", "-i", "-h", "localhost", "-p", "5432", "-U", "postgres", "-d", "prueba", "-v", "E:\\vacia.backup");
            //Se ingresa el valor del password a la variable de entorno de postgres
            pbuilder.environment().put("PGPASSWORD", "123");
            pbuilder.redirectErrorStream(true);
            //Ejecuta el proceso
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backup() {
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            ProcessBuilder pb;
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump.exe",
                    "-i",
                    "-h", "localhost",
                    "-p", "5432",
                    "-U", "postgres",
                    "-F", "c", "-b", "-v",
                    "-f", "C:\\backup\\copia.backup",
                    "prueba");
            pb.environment().put("PGPASSWORD", "123");
            pb.redirectErrorStream(true);
            p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
                System.out.println(ll);
            }

        } catch (IOException ex) {
            Logger.getLogger(mbvBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bck() {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\9.3\\bin\\pg_dump.exe",
                    "-U", "postgres",
                    "-h", "localhost",
                    "prueba");

            Map<String, String> env = builder.environment();
            env.put("PGPASSWORD", "123");

            Process p = builder.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
                System.out.println(ll);
            }
        } catch (IOException ex) {
            Logger.getLogger(mbvBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            if (event.getFile().getSize() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar una copia \".backup\""));
                return;
            }

            if (!event.getFile().getFileName().endsWith(".backup")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".backup\""));
                return;
            }
            outputStream = new FileOutputStream(new File("C:\\backup\\restore.backup"));
            inputStream = event.getFile().getInputstream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Copia restaurada"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error inesperado", "Contáctese con el administrador"));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }
            restore();
        }
    }

    public void initRender() {
        if (usr.getUsuarioId() != null) {
            if (this.usr.getTipoUsuarioId().getTipoUsuarioId() == 4) {
                this.consultar = true;
            } else {
                this.consultar = false;
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "este usuario no está autorizado"));
            }
        }
    }
}
