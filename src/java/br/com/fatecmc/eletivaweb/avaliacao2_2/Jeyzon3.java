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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jeyzon3 {

    public String toString(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        PropertyDescriptor objPropertyDescriptor = null;
        Object variableValue =  null;
        
        List<Field> listaAtributos = new ArrayList<>();
        Class c = obj.getClass();
        String txt = "{\n";
        String keyValor="";
        
        listaAtributos = getListAtributos(c,obj);
        
        for(int i=0;i<listaAtributos.size();i++){
            Field objField = listaAtributos.get(i);
            Object objKey = objField.getName();
            Object objValor = objField.get(obj);
            
            String st = objValor.getClass().toString();
            String tipo = st.substring(st.lastIndexOf('.')+1);
            //Field objField = (Field)listaAtributos.get(i).get(2);
            
            if(!objValor.getClass().isArray()){
                if(objField.getType()==String.class||objField.getType()==Date.class){
                    keyValor = getStringKeyValor(objField,obj);
                    txt = txt.substring(0,txt.length()-1)+"\n";
                    txt = txt+"    \""+keyValor+"\",\n";
                }else{
                    Field[] fields = objValor.getClass().getDeclaredFields();
                    txt = txt+"    \""+objKey+"\": {\n";
                    for (Field field:fields){
                        field.setAccessible(true);
                        keyValor = getStringKeyValor(field,objValor);
                        txt=txt+"       \""+keyValor+"\",\n";        
                    }
                    txt = txt.substring(0,txt.length()-2)+"\n";
                    txt =txt+"    },\n";
                }
            }else{
                System.out.println("Array");
                System.out.println("tamanho: "+Array.getLength(objValor));
                txt=txt+"    \""+objKey+"\": [";
                for (int j = 0; j < Array.getLength(objValor); j++) {
                    txt=txt+"{\n";
                    Object objArray = Array.get(objValor, j);
                    Field[] fields = objArray.getClass().getDeclaredFields();
                    for(Field field:fields){
                        field.setAccessible(true);
                        System.out.println("tipo: "+field.getType());
                        Object objArrayKey = field.getName();
                        Object objArrayValor = field.get(Array.get(objValor, j));
                        if(field.getType()==String.class||field.getType()==Date.class){
                            keyValor = getStringKeyValor(field,objArray);
                            txt = txt+"    \""+keyValor+"\",\n";
                        }else{
                            Field[] subfields = objArrayValor.getClass().getDeclaredFields();
                            txt = txt+"    \""+objArrayKey+"\": {\n";
                            for (Field subfield:subfields){
                                subfield.setAccessible(true);
                                keyValor = getStringKeyValor(subfield,objArrayValor);
                                txt=txt+"       \""+keyValor+"\",\n";        
                            }
                            txt = txt.substring(0,txt.length()-2)+"\n";
                            txt =txt+"    }\n";
                            txt = txt.substring(0,txt.length()-1)+"\n";
                            txt =txt+"    },\n";
                        }
                    }
                }
                txt = txt.substring(0,txt.length()-2)+"\n";
                txt =txt+"    ],\n";
            }

        }
        txt = txt.substring(0,txt.length()-2)+"\n";
        txt = txt+"}\n";
        return txt;
    }    
    
    public String getStringKeyValor(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException{
        return field.getName()+"\": "+"\""+ field.get(obj).toString();
    }
    
    
    public static List<Field> getListAtributos(Class classe,Object obj) throws IllegalArgumentException, IllegalAccessException{
        
        List<Field> listaAtributos = new ArrayList<>();
        while(null!=classe){
            Field[] fields = classe.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                listaAtributos.add(field);
            }
            classe = classe.getSuperclass();
        }
        System.out.println("tamanho: "+listaAtributos.size());
        return listaAtributos;
    }
    
    
}
