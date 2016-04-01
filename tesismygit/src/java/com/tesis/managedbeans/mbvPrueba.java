/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.ProfesorFacade;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvPrueba implements Serializable {

    private Profesor prof;
    private Usuario usuario;
    private String nombre;
    private boolean login;
    private boolean loginProfesor;
    private boolean loginUsuario;
    @EJB
    private ProfesorFacade profesorEjb;

    /**
     * Creates a new instance of mbvPrueba
     */
    public mbvPrueba() {
    }

    public boolean isLoginProfesor() {
        return loginProfesor;
    }

    public void setLoginProfesor(boolean loginProfesor) {
        this.loginProfesor = loginProfesor;
    }

    public boolean isLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(boolean loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profesor getProf() {
        return prof;
    }

    public void setProf(Profesor prof) {
        this.prof = prof;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    @PostConstruct
    public void inicio() {
        this.prof = new Profesor();
        loginProfesor = false;
        loginUsuario = false;
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            prof = mbslogin.getProfesor();
            login = mbslogin.isLogin();
            loginProfesor = true;
            loginUsuario = false;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
            usuario = mbslogin.getUsuario();
            login = mbslogin.isLogin();
            loginProfesor = false;
            loginUsuario = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void cambiarFotoProfesor() {
        try {
            if (!login) {
                System.out.println("Usuario NO logeado");
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Debe iniciar sesion"));
                return;
            }

            RequestContext.getCurrentInstance().execute("PF('dialogoSubirArchivos').show()");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error inesperado", e.getMessage()));
        }
    }

    public void subirFotoProfesor(FileUploadEvent event) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String carpetaEstudiantes = (String) servletContext.getRealPath("/fotosprofesores/");
        File file = new File(carpetaEstudiantes + prof.getFoto());
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
                if (!prof.getFoto().equals("default.jpg")) {
                    if (file.delete()) {
                        System.out.println("ENTRA 7");
                        String nombreArchivo = "/";
                        nombreArchivo += this.prof.getCedula();
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
                        this.prof.setFoto(nombreArchivo);
                        System.out.println("ENTRA 8");
                        profesorEjb.edit(prof);
                        FacesContext.getCurrentInstance().
                                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto actualizada exitosamente", ""));
                        RequestContext.getCurrentInstance().execute("PF('dialogoSubirArchivos').hide()");
                        //inicioPagina();
                    } else {
                        System.out.println("Delete operation is failed.");
                    }
                } else {
                    System.out.println("ENTRA 7");
                    String nombreArchivo = "/";
                    nombreArchivo += this.prof.getCedula();
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
                    this.prof.setFoto(nombreArchivo);
                    System.out.println("ENTRA 8");
                    profesorEjb.edit(prof);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto actualizada exitosamente", ""));
                    RequestContext.getCurrentInstance().execute("PF('dialogoSubirArchivos').hide()");
                    //inicioPagina();
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
}
/* 
package com.tesis.beans;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
    if(constraintViolations.size() > 0){
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        while(iterator.hasNext()){
            ConstraintViolation<T> cv = iterator.next();
            System.err.println(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage());

            //JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
        }
    }else{
        getEntityManager().persist(entity);
    }
    }
*/