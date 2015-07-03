/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.managedbeans;

import com.tesis.entity.Profesor;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario Jurado
 */
@ManagedBean
@ViewScoped
public class mbvPrueba implements Serializable {

    private Profesor prof;
    private String nombre;
    private boolean login;

    /**
     * Creates a new instance of mbvPrueba
     */
    public mbvPrueba() {
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
        try {
            mbsLogin mbslogin = (mbsLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mbsLogin");
             prof = mbslogin.getProfesor();
             login = mbslogin.isLogin();
            System.out.println(mbslogin.getProfesor().getNombre());
        } catch (Exception e) {
            System.out.println(e.toString());
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