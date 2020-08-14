package com.kaikai.MyTomcat.MyAnnotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** 
 * @author 作者 kaikai: 
 * @version 创建时间：2020年8月13日 下午6:03:07 
 * @Description 类说明 
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface MyWebServlet {
	String url();
	String name() default "";
}
