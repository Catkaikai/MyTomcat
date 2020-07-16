package com.kaikai.MyTomcat;
/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午4:37:27 
* @Description 类说明  ServletMapping的配置类 其实是通过该对象将所有的 ServletMapping信息保存在list集合里方便使用
*/

import java.util.ArrayList;
import java.util.List;

public class ServletMappingConfig {
	public static List<ServletMapping> servletMappinglist = new ArrayList<>();
	
	//
	static {
		servletMappinglist
		.add(new ServletMapping("test1", "/t1", 
			"com.kaikai.MyTomcat.testServlet.TestONEservlet"));
		servletMappinglist
		.add(new ServletMapping("test2", "/t2", 
				"com.kaikai.MyTomcat.testServlet.TestTWOservlet"));
		
	}
	
}
