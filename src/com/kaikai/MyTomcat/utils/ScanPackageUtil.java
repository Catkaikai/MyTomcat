package com.kaikai.MyTomcat.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.kaikai.MyTomcat.MyServlet;
import com.kaikai.MyTomcat.ServletMapping;
import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年8月13日 下午4:22:12 
* @Description 类说明 扫描包，得到MyServlet的所有子类，以及通过注解信息得到对应的子类
*/
public class ScanPackageUtil {
	/**
	 * 从jar包读取所有的class文件名
	 */
	@SuppressWarnings("unused")
	private static List<String> getClassNameFrom(String jarName) {
		List<String> fileList = new ArrayList<String>();
		try {
			JarFile jarFile = new JarFile(new File(jarName));
			Enumeration<JarEntry> en = jarFile.entries(); // 枚举获得JAR文件内的实体,即相对路径
			while (en.hasMoreElements()) {
				String name1 = en.nextElement().getName();
				if (!name1.endsWith(".class")) {// 不是class文件
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
	 * 递归查找指定目录下的类文件的全路径
	 * @param baseFile 查找文件的入口
	 * @param fileList 保存已经查找到的文件集合
	 */
	public static void getSubFileNameList(File baseFile, List<String> fileList) {
		if (baseFile.isDirectory()) {
			File[] files = baseFile.listFiles();
			for (File tmpFile : files) {
				getSubFileNameList(tmpFile, fileList);
			}
		}
		String path = baseFile.getPath();
		if (path.endsWith(".java")) {
			String name1 = path.substring(path.indexOf("src") + 4, path.length());
			String name2 = name1.replaceAll("\\\\", ".");
			String name3 = name2.substring(0, name2.lastIndexOf(".java"));
			fileList.add(name3);
		}
	}
	/**
	 *  判断一个类是否继承某个父类或实现某个接口
	 */
	public static boolean isChildClass(String className,Class parentClazz){
		if(className == null) return false;		
		Class clazz = null;
		try {
			clazz = Class.forName(className);
			if(Modifier.isAbstract(clazz.getModifiers())){//抽象类忽略
				return false;
			}
			if(Modifier.isInterface(clazz.getModifiers())){//接口忽略
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return parentClazz.isAssignableFrom(clazz);	
	}

	/**
	 * 将通过注解声明的MyServlet子类
	 * @param servletMappinglist
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unused")
	public static void setServletMappingByAnnotation(List<ServletMapping> servletMappinglist) throws ClassNotFoundException{
		List<String> fileList=new ArrayList<>();
		File baseFile=new File("src");
		ScanPackageUtil.getSubFileNameList(baseFile,fileList);//扫描得到所有的类
		for(String name:fileList){
			//得到子类
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


















