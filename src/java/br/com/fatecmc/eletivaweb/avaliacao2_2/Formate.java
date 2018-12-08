package br.com.fatecmc.eletivaweb.avaliacao2_2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Formate {

    //public String padrao() default "dd/MM/yyyy";
    public String padrao() default "dd/MM/yyyy";
}
