package com.kaikai.MyTomcat.config;
/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午4:37:27 
* @Description 类说明  ServletMapping的配置类 其实是通过该对象将所有的 ServletMapping信息保存在list集合里方便使用
*/

import java.util.ArrayList;
import java.util.List;

import com.kaikai.MyTomcat.pack.MySrcServlet;
import com.kaikai.MyTomcat.pack.ServletMapping;
import com.kaikai.MyTomcat.utils.ScanPackageUtil;

public class ServletMappingConfig {
	public static List<ServletMapping> servletMappinglist = new ArrayList<>();
	public static List<ServletMapping> SrcservletMappinglist = new ArrayList<>();
	
	//测试用例
	static {
		servletMappinglist
		.add(new ServletMapping("test1", "/t1", 
			"com.kaikai.MyTomcat.testServlet.TestONEservlet"));
		servletMappinglist
		.add(new ServletMapping("test2", "/t2", 
				"com.kaikai.MyTomcat.testServlet.TestTWOservlet"));
		SrcservletMappinglist.add(new ServletMapping("test4", "/t4", new MySrcServlet("测试静态资源")));
		
	}
	//通过读取修改配置文件得到servlet类的名字 请求url 路径
	
	//通过扫描注解得到servlet类的名字 请求url 路径
	static {
		try {
			ScanPackageUtil.setServletMappingByAnnotation(servletMappinglist);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
