package com.kaikai.MyTomcat.pack;
/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午3:44:01 
* @Description 类说明  serletmapping实体类 分发请求需要的信息的封装
*/
public class ServletMapping {
	/**
	 * servlet的名字
	 */
	private String servletName;
	/**
	 * 在浏览器地址最后的访问url 即ip:port/url
	 */
	private String url;
	/**
	 * 该Servlet的全限定名用于反射
	 */
	private String clazz;
	/**
	 * 静态资源对象
	 */
	private MySrcServlet mysrcservlet;
	public MySrcServlet getMysrcservlet() {
		return mysrcservlet;
	}
	public void setMysrcservlet(MySrcServlet mysrcservlet) {
		this.mysrcservlet = mysrcservlet;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	/**
	 * 
	 * @param servletName servlet的名字 起描述作用
	 * @param url 在浏览器地址最后的访问url 即ip:port/url url作为分发的标识  是MyTomcat里的map里的key值
	 * @param clazz 该Servlet的位置 用于反射创建对象 是MyTomcat里的map里的value值
	 */
	public ServletMapping(String servletName, String url, String clazz) {
		super();
		this.servletName = servletName;
		this.url = url;
		this.clazz = clazz;
	}
	public ServletMapping(String servletName, String url, MySrcServlet  mysrcservlet) {
		super();
		this.servletName = servletName;
		this.url = url;
		this.mysrcservlet=mysrcservlet;
	}
	
	
}
