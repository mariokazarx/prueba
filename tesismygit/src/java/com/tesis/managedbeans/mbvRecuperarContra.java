/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.beans.CodigoProfesorFacade;
import com.tesis.beans.CodigoUsuarioFacade;
import com.tesis.beans.ProfesorFacade;
import com.tesis.beans.UsuarioFacade;
import com.tesis.clases.Encrypt;
import com.tesis.entity.CodigoProfesor;
import com.tesis.entity.CodigoUsuario;
import com.tesis.entity.Profesor;
import com.tesis.entity.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvRecuperarContra implements Serializable {

    private static final long serialVersionUID = 743387389365626021L;
    private Session session;
    private String txtCorreo;
    private String tipoUsuario;
    private Usuario usuario;
    private Profesor profesor;
    private final Properties properties = new Properties();
    @EJB
    private UsuarioFacade usuarioEjb;
    @EJB
    private ProfesorFacade profesorEjb;
    @EJB
    private CodigoUsuarioFacade codigoUsuarioEjb;
    @EJB
    private CodigoProfesorFacade codigoProfesorEjb;

    public mbvRecuperarContra() {
    }

    public String getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(String txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @PostConstruct
    public void inicio() {
        this.usuario = new Usuario();
        this.profesor = new Profesor();
    }

    private void init() {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.mail.sender", "mariokazarx@gmail.com");
        properties.put("mail.smtp.user", "mariokazarx");
        //properties.put("mail.smtp.password", "mfjkazar");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail() {
        try {
            boolean enviar = false;
            String code = getCadenaAlfanumAleatoria(12);
            if (this.tipoUsuario.equals("administrativo")) {
                Usuario auxUsu = new Usuario();
                auxUsu = usuarioEjb.getByCorreo(txtCorreo);
                if (auxUsu != null) {
                    String codeEncrypt = Encrypt.code(code);
                    Date fecha = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
                    CodigoUsuario codigoUsu = new CodigoUsuario();
                    codigoUsu.setCodigo(codeEncrypt);
                    codigoUsu.setEstado(1);
                    codigoUsu.setFechavalido(fecha);
                    codigoUsu.setUsuarioId(auxUsu);
                    codigoUsuarioEjb.create(codigoUsu);
                    enviar = true;
                } else {
                    //usuario no existe
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Datos incorrectos"));
                    return;
                }
            } else if (this.tipoUsuario.equals("profesor")) {
                Profesor auxProf = new Profesor();
                auxProf = profesorEjb.getByCorreo(txtCorreo);
                if (auxProf != null) {
                    String codeEncrypt = Encrypt.code(code);
                    Date fecha = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
                    CodigoProfesor codigoProf = new CodigoProfesor();
                    codigoProf.setCodigo(codeEncrypt);
                    codigoProf.setEstado(1);
                    codigoProf.setFechavalido(fecha);
                    codigoProf.setProfesorId(auxProf);
                    codigoProfesorEjb.create(codigoProf);
                    enviar = true;
                } else {
                    //usuario no existe
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Datos incorrectos"));
                    return;
                }
            }
            if (enviar) {
                init();
                FacesContext context = FacesContext.getCurrentInstance();
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(txtCorreo));
                message.setSubject("Recuperación Contraseña sistema SAMY");
                message.setContent(message, "text/html");
                message.setText(this.textoCorreo(code), "utf-8", "html");
                Transport t = session.getTransport("smtp");
                t.connect((String) properties.get("mail.smtp.user"), "mfjkazar");
                t.sendMessage(message, message.getAllRecipients());
                t.close();
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Por favor revise su correo electrónico"));
                context.getExternalContext().redirect("restablecerContrasenia.xhtml");
            }
        } catch (MessagingException me) {
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        } catch (Exception ex) {
            return;
        }
    }

    private String getCadenaAlfanumAleatoria(int longitud) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }

    private String textoCorreo(String codigo) {
        String texto = "";
        texto += "<div style='width: 600px;margin: 0 auto; font-family: Arial, Helvetica, sans-serif'>"
                + "<center> <table style='width: 600px;' cellpadding='0' cellspacing='0'> <tr>"
                + "<td style='border-right: 0px;border-bottom: 0px;'>"
                + "<div style='background-color: gainsboro; text-align: center; height: 70px; line-height: 4;'>"
                + "<span>CENTRO EDUCATIVO ANTONIO RICAURTE</span></div></td></tr><tr><td><div>"
                + "Para restablecer tu contraseña ingresa el siguiente codigo: " + codigo + "<a href='http://localhost:8080/tesismygit/faces/restablecerContrasenia.xhtml'> Aqui</a>\n"
                + "El codigo de seguridad es unico y valido por 15 minutos "
                + "</div></td></tr><tr>	<td style='border-right: 0px;border-bottom: 0px;'>"
                + "<div style='background-color: gainsboro; text-align: center; height: 70px; line-height: 4;'>"
                + "<span>¡Educamos para la paz y el Progreso!</span>"
                + "<span>Sede Escuela Bloque 3 - Ricaurte Nariño Celular: 3218300831 Emll cericaurte@hotmail.com</span>"
                + "</div></td></tr></table></center></div>";
        return texto;
    }
}
