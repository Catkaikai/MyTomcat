package com.kaikai.MyTomcat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import com.kaikai.MyTomcat.config.propertiesConfig;
import com.kaikai.MyTomcat.pack.MySrcServlet;
import com.kaikai.MyTomcat.pack.ServletMapping;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年11月13日 下午6:27:58 
* @Description 类说明 
*/
public class ScanFolderUtil {
	
	/**
	 * 获取文本
	 * @param servletMapping
	 * @param path
	 */
	public static String getcontent(String path) {
		return path;		
	}

	/**
	 * 通过遍历目标文件夹，将其抽象为Srcservletmapping
	 * @param baseFile
	 * @param SrcservletMappinglist
	 */
	public static void setSrcServletMappingBypath(File baseFile, List<ServletMapping> SrcservletMappinglist) {
		// 递归遍历
		if (baseFile.isDirectory()) {
			File[] files = baseFile.listFiles();
			for (File tmpFile : files) {
				setSrcServletMappingBypath(tmpFile, SrcservletMappinglist);
			}
		}
		String path = baseFile.getPath();
		if (path.endsWith(".html")) {
			String s = path.substring(propertiesConfig.webappspath.length(), path.length());
			String url = s.replaceAll("\\\\","/");
			
			StringBuffer sb=null;
			FileInputStream fi=null;
			InputStreamReader ir=null;
			BufferedReader br=null;
			String line=null;
			try {
				sb = new  StringBuffer();
				fi = new FileInputStream(baseFile);
				ir = new InputStreamReader(fi);
				br = new BufferedReader(ir);
				while ((line = br.readLine()) != null) {
				     sb.append(line);
				     sb.append("\r\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					br.close();
					ir.close();
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.println(url);
			//System.out.println(sb.toString());
			SrcservletMappinglist.add(new ServletMapping("", url, new MySrcServlet(sb.toString())));
		}
	}
	public static void setSrcServletMappingBypath(String path, List<ServletMapping> SrcservletMappinglist) {
		File file = new File(path);
		setSrcServletMappingBypath(file, SrcservletMappinglist);
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// 优先从项目同级路径获取连接信息
				String confPath = System.getProperty("user.dir");
				confPath = confPath + File.separator + "config\\config.properties";
				//System.out.println(confPath);
				//System.out.println(File.separator);
				File file = new  File("D:/TestFolder/userwebapps/index.html");
				File file2 = new  File("D:/TestFolder/userwebapps/folder/one.html");
				File file3 = new  File("D:/TestFolder/userwebapps");
				String s = file.getPath();
				String s2 = file2.getPath();
				System.out.println(s);
				String m3 = s.substring(propertiesConfig.webappspath.length(), s.length());
				String m4 = m3.replaceAll("\\\\", "/");
				System.out.println(s.substring(propertiesConfig.webappspath.length(), s.length()));
				System.out.println(s2.substring(propertiesConfig.webappspath.length(), s2.length()));
				System.out.println(m4);
				
				String line=null;
				StringBuffer sb = new  StringBuffer();
				FileInputStream fi = new FileInputStream(file);
				InputStreamReader ir = new InputStreamReader(fi);
				BufferedReader br = new BufferedReader(ir);
				while ((line = br.readLine()) != null) {
				     sb.append(line);
				     sb.append("\r\n");
				}
				System.out.println("dadadd"+"\r\n"+"dd");
				setSrcServletMappingBypath(file3, null);
				
	}
}















