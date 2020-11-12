package com.kaikai.MyTomcat.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;
import com.kaikai.MyTomcat.pack.MyServlet;
import com.kaikai.MyTomcat.pack.ServletMapping;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��8��13�� ����4:22:12 
* @Description ��˵�� ɨ�赱ǰjar���Լ�����jar�����ļ��Ĺ�����
*/
public class ScanPackageUtil {
	/**
	 * ������jar����ȡ���е�class�ļ���
	 */
	@SuppressWarnings("unused")
	private static List<String> getClassNameFrom(String jarName) {
		List<String> fileList = new ArrayList<String>();
		try {
			JarFile jarFile = new JarFile(new File(jarName));
			Enumeration<JarEntry> en = jarFile.entries(); // ö�ٻ��JAR�ļ��ڵ�ʵ��,�����·��
			while (en.hasMoreElements()) {
				String name1 = en.nextElement().getName();
				if (!name1.endsWith(".class")) {// ����class�ļ�
					continue;
				}
				String name2 = name1.substring(0, name1.lastIndexOf(".class"));
				String name3 = name2.replaceAll("/", ".");
				fileList.add(name3);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	/**
	 * �ݹ����ָ��Ŀ¼�µ����ļ���ȫ·��
	 * �ݹ�����õ����е�.java�ļ�·��
	 * @param baseFile �����ļ������
	 * @param fileList �����Ѿ����ҵ����ļ�����
	 */
	public static void getSubFileNameList(File baseFile, List<String> fileList) {
		//�ݹ�����õ����е�.java�ļ�·��
		if (baseFile.isDirectory()) {
			File[] files = baseFile.listFiles();
			for (File tmpFile : files) {
				getSubFileNameList(tmpFile, fileList);
			}
		}
		String path = baseFile.getPath();
		//·��תȫ�޶���
		if (path.endsWith(".java")) {
			String name1 = path.substring(path.indexOf("src") + 4, path.length());
			String name2 = name1.replaceAll("\\\\", ".");
			String name3 = name2.substring(0, name2.lastIndexOf(".java"));
			fileList.add(name3);
		}
	}
	/**
	 *  �ж�һ�����Ƿ�̳�ĳ�������ʵ��ĳ���ӿ�
	 */
	public static boolean isChildClass(String className,Class<MyServlet> parentClazz){
		if(className == null) return false;		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
			if(Modifier.isAbstract(clazz.getModifiers())){//���������
				return false;
			}
			if(Modifier.isInterface(clazz.getModifiers())){//�ӿں���
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return parentClazz.isAssignableFrom(clazz);	
	}

	/**
	 * ����jar��ͨ��ע��������MyServlet������ӵ�������
	 * @param servletMappinglist
	 * @throws ClassNotFoundException 
	 */
	public static void setServletMappingByAnnotation(List<ServletMapping> servletMappinglist) throws ClassNotFoundException{
		List<String> fileList=new ArrayList<>();
		File baseFile=new File("src");
		ScanPackageUtil.getSubFileNameList(baseFile,fileList);//ɨ��õ����е���
		for(String name:fileList){
			//�õ���ע�������
			if(ScanPackageUtil.isChildClass(name, MyServlet.class)) {
				Class<?> myservlet = Class.forName(name);
				if(myservlet.isAnnotationPresent(MyWebServlet.class)) {
					MyWebServlet m = myservlet.getAnnotation(MyWebServlet.class);
					servletMappinglist.add(new ServletMapping(m.name(),m.url(),name));
				}
			}
		}
		
	}
	
}


















