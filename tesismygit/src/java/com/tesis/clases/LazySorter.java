package com.tesis.clases;

import com.tesis.entity.Asignatura;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorter implements Comparator<Asignatura>,Serializable {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @Override
    public int compare(Asignatura asg1, Asignatura asg2) {
        try {
            //System.out.println("aaa"+this.sortField+"ooo"+sortOrder);
            Field val1 = Asignatura.class.getDeclaredField(this.sortField);
            val1.setAccessible(true);
            Object value1 = val1.get(asg1);
            Object value2 = val1.get(asg2);
            //Object value1 = Asignatura.class.getField(this.sortField).get(asg1);
            //Object value2 = Asignatura.class.getField(this.sortField).get(asg2);
           // System.out.println("asg1"+val1);
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            //System.err.println("aaa"+e.toString());
            throw new RuntimeException();
        }
    }

}