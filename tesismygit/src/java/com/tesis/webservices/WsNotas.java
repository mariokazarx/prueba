/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.webservices;

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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

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
