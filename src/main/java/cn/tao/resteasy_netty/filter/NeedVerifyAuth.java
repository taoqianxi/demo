package cn.tao.resteasy_netty.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaoming
 * @create 2017-07-12 17:24
 **/

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NeedVerifyAuth {

    boolean required() default true;

}
