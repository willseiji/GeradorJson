/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatecmc.eletivaweb.avaliacao2_2;
/**
 *
 * @author william
 */

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.lang.Object;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author william
 */
public class Jeyzon {
    private String txt;

    public String toString(Object obj){
        
        try{
            PropertyDescriptor objPropertyDescriptor = null;
            Object variableValue =  null;
            
          /*  objPropertyDescriptor = new PropertyDescriptor("clienteDesde",obj.getClass());
            System.out.println("\ndatas");
            
            Date datavelha = (Date) objPropertyDescriptor.getReadMethod().invoke(obj);
            System.out.println("data velha: "+datavelha);
            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("\nnova data: "+dataFormatada.format(objPropertyDescriptor.getReadMethod().invoke(obj)));
            */
            
            setTxt("{\n");
            Field[] fields = obj.getClass().getDeclaredFields();
          
            for(Field field:fields)
            {
                objPropertyDescriptor = new PropertyDescriptor(field.getName(),obj.getClass());
                System.out.println(objPropertyDescriptor.getClass().getSuperclass());
                Class objAtt = objPropertyDescriptor.getClass().getSuperclass();
                if(objAtt!=null)
                    System.out.println("tem classe no atributo");
                variableValue = objPropertyDescriptor.getReadMethod().invoke(obj);
                System.out.println(variableValue);
                setTxt(getTxt()+"    \""+field.getName()+"\": \"" +variableValue+"\",\n");
            }
                
            /*
            setTxt("{\n");
            Field[] fields = obj.getClass().getDeclaredFields();
            Field[] fieldsSuperClass = obj.getClass().getSuperclass().getDeclaredFields();
            for(Field fieldSuperClass:fieldsSuperClass)
            {
                if(!fieldSuperClass.getType().isArray())
                {
                    objPropertyDescriptor = new PropertyDescriptor(fieldSuperClass.getName(),obj.getClass());
                    variableValue = objPropertyDescriptor.getReadMethod().invoke(obj);
                    System.out.println(variableValue);
                    setTxt(getTxt()+"    \""+fieldSuperClass.getName()+"\": \"" +variableValue+"\",\n");
                    for(Field field:fields)
                    {
                        objPropertyDescriptor = new PropertyDescriptor(field.getName(),obj.getClass());
                        variableValue = objPropertyDescriptor.getReadMethod().invoke(obj);
                        System.out.println(variableValue);
                        setTxt(getTxt()+"    \""+field.getName()+"\": \"" +variableValue+"\",\n");
                    }
                }
                
            }
        */
            setTxt(getTxt().substring(0,getTxt().length()-2)+"\n");
            
            
/*            System.out.println("\nMetodos");
            Method[] methods = obj.getClass().getMethods();
            for(Method method:methods){
               String MethodName = method.getName();
                System.out.println(MethodName);
            }
            System.out.println(obj.getClass().getName());
            */

            setTxt(getTxt()+"}\n");
            return getTxt();
            

        }catch (IntrospectionException| IllegalArgumentException| IllegalAccessException| InvocationTargetException ex){
            Logger.getLogger(Jeyzon.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }    
    }    
   
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
    
}
