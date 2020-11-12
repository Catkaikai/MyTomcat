package com.kaikai.MyTomcat.config;
/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����4:37:27 
* @Description ��˵��  ServletMapping�������� ��ʵ��ͨ���ö������е� ServletMapping��Ϣ������list�����﷽��ʹ��
*/

import java.util.ArrayList;
import java.util.List;

import com.kaikai.MyTomcat.pack.ServletMapping;
import com.kaikai.MyTomcat.utils.ScanPackageUtil;

public class ServletMappingConfig {
	public static List<ServletMapping> servletMappinglist = new ArrayList<>();
	
	//��������
	static {
		servletMappinglist
		.add(new ServletMapping("test1", "/t1", 
			"com.kaikai.MyTomcat.testServlet.TestONEservlet"));
		servletMappinglist
		.add(new ServletMapping("test2", "/t2", 
				"com.kaikai.MyTomcat.testServlet.TestTWOservlet"));
		
	}
	//ͨ����ȡ�޸������ļ��õ�servlet������� ����url ·��
	
	//ͨ��ɨ��ע��õ�servlet������� ����url ·��
	static {
		try {
			ScanPackageUtil.setServletMappingByAnnotation(servletMappinglist);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
