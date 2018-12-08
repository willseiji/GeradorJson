
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Jeyzon5 {
    private Set<Object> mapaClasses;
    
    public String toString(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if (null == obj)
            return "{}";
        return "{ "+getJson(obj)+"\n}";
    }    
    
    public String getJson(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        mapaClasses = new HashSet<>();
        mapaClasses.add(obj);
        String txt = "";
        
        Class classe = obj.getClass();
        while(null!=classe){
            Field[] fields = classe.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Object objKey = field.getName();
                Object objValor = field.get(obj);
                if(null==objValor)
                    txt = txt+"\n    \""+objKey+"\":"+null;
                else{
                    if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class))
                        continue;
                    if(field.getType().isPrimitive()|| String.class.equals(field.getType())
                        ||Date.class.equals(field.getType())||Number.class.equals(field.getType())){
                        txt = txt+"\n"+getKeyValorSimples(field,obj);    
                    }else if(field.getType().isArray()){
                        txt = txt+"\n    \""+objKey+"\": \n    ["+getKeyValorArray(objValor)+"\n     ]";

                    }else{
                        txt = txt+"\n    \""+objKey+"\": \n    {"+getKeyValorObjeto(objValor)+"\n    }";                    
                    }
                }
                txt=txt+",";
            }
            classe = classe.getSuperclass();
        }
        txt = txt.substring(0,txt.length()-1);
        return txt;
    }
    
    public String getKeyValorSimples(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException{
        String valor;
        if(field.getType()==Date.class&& null!=field.getAnnotation(Formate.class)){
            valor = "\""+ new SimpleDateFormat(field.getAnnotation(Formate.class).padrao()).format(field.get(obj))+"\"";
        }else if(field.getType().isPrimitive()&&!("char".equals(field.getType().toString()))&&!("char".equals(field.getType().toString()))){
            valor = field.get(obj).toString();
        }else if (null==field.get(obj)){
            valor = null;
        }else
            valor = "\""+ field.get(obj).toString()+"\"";
        return "    \""+field.getName()+"\": "+valor;
    }
    
    public String getKeyValorObjeto(Object obj) throws IllegalArgumentException, IllegalAccessException{
        String txt="";
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field:fields){
            if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class))
                continue;
            field.setAccessible(true);
            if(!mapaClasses.contains(field.get(obj))){
                mapaClasses.add(field.get(obj));
                if (null==field.get(obj))
                    txt = txt+"\n    \""+field.getName()+"\":"+null;
                else{
                    if(field.getType().isPrimitive()||field.getType()==String.class||field.getType()==Date.class){
                        txt=txt+"\n    "+getKeyValorSimples(field,obj);        
                    }else if(field.getType().isArray()){
                        txt = txt+"\n    \""+field.getName()+"\": \n    ["+getKeyValorArray(field.get(obj))+"\n     ]";
                    }else{
                        txt = txt+"\n        \""+field.getName()+"\":";
                        String keyValorObjeto =getKeyValorObjeto(field.get(obj));     
                        txt = txt+"\n        {"+keyValorObjeto.substring(0,keyValorObjeto.length())+"}";
                    }
                }
                txt=txt+",";
            }else
                txt = txt+"\n        \""+field.getName()+"\": { },";
        }
        txt = txt.substring(0,txt.length()-1);
        return txt;
    }
    
    public String getKeyValorArray(Object obj) throws IllegalArgumentException, IllegalAccessException{
        String txt="";
        for (int j = 0; j < Array.getLength(obj); j++) {
            txt=txt+"\n     {";
            Object objArray = Array.get(obj, j);
            Field[] fields = objArray.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Object objArrayKey = field.getName();
                Object objArrayValor = field.get(Array.get(obj, j));
                if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class))
                    continue;
                if (null==field.get(objArray))
                        txt = txt+"\n    \""+field.getName()+"\":"+null;
                else{
                    if(field.getType().isPrimitive()||field.getType()==String.class||field.getType()==Date.class){
                        txt = txt+"\n    "+getKeyValorSimples(field,objArray);
                    }else if(field.getType().isArray()){
                        txt = txt+"\n    \""+field.getName()+"\": \n    ["+getKeyValorArray(field.get(objArray))+"\n     ]";
                    }else{
                        txt = txt+"\n        \""+objArrayKey+"\":";
                        String keyValorObjeto =getKeyValorObjeto(objArrayValor);     
                        txt = txt+"\n        {"+keyValorObjeto.substring(0,keyValorObjeto.length())+"}";         
                    }
                }
                txt=txt+",";
            }   
            txt = txt.substring(0,txt.length()-1);
            txt=txt+"\n    },";
        }
        txt = txt.substring(0,txt.length()-1);
        return txt;
    }
}
