package com.kaikai.MyTomcat.MyAnnotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** 
 * @author ���� kaikai: 
 * @version ����ʱ�䣺2020��8��13�� ����6:03:07 
 * @Description ��˵�� 
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface MyWebServlet {
	String url();
	String name() default "";
}
