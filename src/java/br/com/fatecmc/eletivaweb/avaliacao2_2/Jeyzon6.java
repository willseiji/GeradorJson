
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

public class Jeyzon6 {
    private Set<Object> mapaClasses;
    
    public String toString(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if (null == obj)
            return "{}";
        return "{ "+getJson(obj)+"\n}";
    }    
    
    public String getJson(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        mapaClasses = new HashSet<>();
        String txt = " ";
        Class classe = obj.getClass();
        while(null!=classe){
            txt=txt+getKeyValorObjeto(classe,obj)+",";
            classe = classe.getSuperclass();
        }
        return txt.substring(0,txt.length()-2);
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
    
    public String getKeyValorObjeto(Class classe, Object obj) throws IllegalArgumentException, IllegalAccessException{
        String txt=" ";
        Field[] fields = classe.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class))
                continue;
            if (null==field.get(obj))
                txt = txt+"\n    \""+field.getName()+"\":"+null;
            else{
                if(null!=field.getDeclaredAnnotation(IgnoreJeyzon.class))
                    continue;
                if(field.getType().isPrimitive()||field.getType()==String.class||field.getType()==Date.class){
                    txt=txt+"\n    "+getKeyValorSimples(field,obj);        
                }else if(field.getType().isArray()){
                    txt = txt+"\n    \""+field.getName()+"\": \n    ["+getKeyValorArray(field.get(obj))+"\n     ]";
                }else{
                    if(!mapaClasses.contains(field.get(obj))){
                        mapaClasses.add(field.get(obj));                 
                        txt = txt+"\n    \""+field.getName()+"\": \n    {"+getKeyValorObjeto(field.get(obj).getClass(),field.get(obj))+"\n     }";
                    }else
                        txt = txt+"\n        \""+field.getName()+"\": { }";   
                }
            }
            txt=txt+",";      
        }
        return txt.substring(0,txt.length()-1);
    }
    
    public String getKeyValorArray(Object obj) throws IllegalArgumentException, IllegalAccessException{
        String txt=" ";
        for (int j = 0; j < Array.getLength(obj); j++) {
            txt=txt+"\n     {"+getKeyValorObjeto(Array.get(obj, j).getClass(),Array.get(obj,j))+"\n    },";
        }
        return txt.substring(0,txt.length()-1);
    }
}
