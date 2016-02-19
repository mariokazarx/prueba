/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesis.clases;

import com.tesis.entity.Asignatura;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Mario Jurado
 */
public class LazyAsignaturaDataModel extends LazyDataModel<Asignatura> {
     
    private List<Asignatura> datasource;
     
    public LazyAsignaturaDataModel(List<Asignatura> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Asignatura getRowData(String rowKey) {
        for(Asignatura asig : datasource) {
            if(asig.getAsignaturaId().equals(rowKey))
                return asig;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Asignatura asg) {
        return asg.getAsignaturaId();
    }
 
    @Override
    public List<Asignatura> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Asignatura> data = new ArrayList<Asignatura>();
 
        //filter
        for(Asignatura asg : datasource) {
            boolean match = true;
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field val1 = Asignatura.class.getDeclaredField(filterProperty);
                        val1.setAccessible(true);
                        String fieldValue = String.valueOf(val1.get(asg));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString()) || fieldValue.toLowerCase().contains(filterValue.toString().toLowerCase())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(asg);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}