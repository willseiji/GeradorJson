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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Jeyzon4 {

    public String toString(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return "{ "+getJson(obj)+"}";
    }    
    
    public String getJson(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String txt = "";
        List<Field> listaField = getListField(obj.getClass(),obj);
        for(int i=0;i<listaField.size();i++){
            Field objField = listaField.get(i);
            Object objKey = objField.getName();
            Object objValor = objField.get(obj);
            if(objField.getType()==String.class||objField.getType()==Date.class||objField.getType()==Number.class){
                txt = txt+"    \""+getKeyValorSimples(objField,obj)+"\",";    
            }else if(objField.getType().isArray()){
                txt = txt+"    \""+objKey+"\": \n["+getKeyValorArray(objValor)+"],";
            }else
                txt = txt+"    \""+objKey+"\": \n{"+getKeyValorObjeto(objValor)+"},";
        }
        txt = txt.substring(0,txt.length()-1);
        return txt;
    }
    
    public String getKeyValorSimples(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException{
        String valor;
        if(field.getType()==Date.class&& null!=field.getAnnotation(Formate.class))
            valor = new SimpleDateFormat(field.getAnnotation(Formate.class).padrao()).format(field.get(obj));
        else
            valor = field.get(obj).toString();
        return field.getName()+"\": "+"\""+ valor;
    }
    
    public String getKeyValorObjeto(Object obj) throws IllegalArgumentException, IllegalAccessException{
        String txt="";
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            txt=txt+"       \""+getKeyValorSimples(field,obj)+"\",";        
        }
        return txt;
    }
    
    public String getKeyValorArray(Object obj) throws IllegalArgumentException, IllegalAccessException{
        
        String txt="";
        for (int j = 0; j < Array.getLength(obj); j++) {
            txt=txt+"{\n";
            Object objArray = Array.get(obj, j);
            Field[] fields = objArray.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Object objArrayKey = field.getName();
                Object objArrayValor = field.get(Array.get(obj, j));
                if(field.getType()==String.class||field.getType()==Date.class){
                    txt = txt+"    \""+getKeyValorSimples(field,objArray)+"\",";
                }else{
                    txt = txt+"    \""+objArrayKey+"\":";
                    String keyValorObjeto =getKeyValorObjeto(objArrayValor);     
                    txt = txt+"{"+keyValorObjeto.substring(0,keyValorObjeto.length()-1)+"}";
                }
            }    
            txt=txt+"},";
        }
        txt = txt.substring(0,txt.length()-1);
        return txt;
    }
    
    public static List<Field> getListField(Class classe,Object obj) throws IllegalArgumentException, IllegalAccessException{
        List<Field> listaField = new ArrayList<>();
        while(null!=classe){
            Field[] fields = classe.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class ))
                    continue;
                listaField.add(field);
            }
            classe = classe.getSuperclass();
        }
        return listaField;
    }
}
