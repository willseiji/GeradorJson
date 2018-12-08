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

public class Jeyzon2 {

    public String toString(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        PropertyDescriptor objPropertyDescriptor = null;
        Object variableValue =  null;
        
        List<List<Object>> listaAtributos = new ArrayList<>();
        List<List<Object>> listaAtributosArray = new ArrayList<>();
        Class c = obj.getClass();
        String txt = "{\n";
        String keyValor="";
        
        listaAtributos = getListAtributos(c,obj);
        
        for(int i=0;i<listaAtributos.size();i++){
            System.out.println(listaAtributos.get(i).get(0));
            //System.out.println(listaAtributos.get(i).get(1));
            Object objKey = listaAtributos.get(i).get(0).toString();
            Object objValor = listaAtributos.get(i).get(1);
            
            String st = objValor.getClass().toString();
            String tipo = st.substring(st.lastIndexOf('.')+1);
            
            
            if(!objValor.getClass().isArray()){
                if(tipo.equals("String")||tipo.equals("Date")){
                    keyValor = objKey+"\": \"" +objValor;
                    txt = txt.substring(0,txt.length()-1)+"\n";
                    txt = txt+"    \""+keyValor+"\",\n";
                }else{
                    Field[] fields = objValor.getClass().getDeclaredFields();
                    txt = txt+"    \""+objKey+"\": {\n";
                    for (Field field:fields){
                        field.setAccessible(true);
                        keyValor = field.getName()+"\": "+"\""+ field.get(listaAtributos.get(i).get(1));
                        txt=txt+"       \""+keyValor+"\",\n";        
                    }
                    txt = txt.substring(0,txt.length()-2)+"\n";
                    txt =txt+"    },\n";
                }
            }else{
                txt=txt+"    \""+objKey+"\": [";
                System.out.println("Array");
                System.out.println(objValor);
                System.out.println(objValor.getClass());
                
                System.out.println("tamanho");
                System.out.println(Array.getLength(objValor));
                for (int j = 0; j < Array.getLength(objValor); j++) {
                    txt=txt+"{\n";
                    Object objArray = Array.get(objValor, j);
                    Field[] fields = objArray.getClass().getDeclaredFields();
                    for(Field field:fields){
                        field.setAccessible(true);
                        Object objArrayKey = field.getName();
                        Object objArrayValor = field.get(Array.get(objValor, j));
                        if(field.getType()==String.class||field.getType()==Date.class){
                            System.out.println("    \""+objArrayKey+"\": \"" +objArrayValor+"\",");
                            keyValor = objArrayKey+"\": \"" +objArrayValor;
                            txt = txt+"    \""+keyValor+"\",\n";
                        }else{
                            Field[] subfields = objArrayValor.getClass().getDeclaredFields();
                            System.out.println("    \""+objArrayKey+"\": {");
                            txt = txt+"    \""+objArrayKey+"\": {\n";
                            for (Field subfield:subfields){
                                subfield.setAccessible(true);
                                System.out.println("       \""+subfield.getName()+"\": "+"\""+ subfield.get(objArrayValor)+"\",\n");
                                keyValor = subfield.getName()+"\": "+"\""+ subfield.get(objArrayValor);
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
    
    public static List<List<Object>> getListAtributos(Class classe,Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        PropertyDescriptor objPropertyDescriptor = null;
        Object variableValue =  null;
        List<Object> atributos;
        List<List<Object>> listaAtributos = new ArrayList<>();
        
        if(classe.getSuperclass()!=null){
            listaAtributos.addAll(getListAtributos(classe.getSuperclass(),obj));
        }
        
        Field[] fields = classe.getDeclaredFields();
        for(Field field:fields){
            atributos = new ArrayList<>();
            field.setAccessible(true);
            atributos.add(field.getName());
            atributos.add(field.get(obj));
            listaAtributos.add(atributos);
        } 
        return listaAtributos;        
    }
}
