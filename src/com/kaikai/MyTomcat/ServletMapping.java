package com.kaikai.MyTomcat;
/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����3:44:01 
* @Description ��˵��  serletmappingʵ���� �ַ�������Ҫ����Ϣ�ķ�װ
*/
public class ServletMapping {
	/**
	 * servlet������
	 */
	private String servletName;
	/**
	 * ���������ַ���ķ���url ��ip:port/url
	 */
	private String url;
	/**
	 * ��Servlet��ȫ�޶������ڷ���
	 */
	private String clazz;
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
	 * @param servletName servlet������ ����������
	 * @param url ���������ַ���ķ���url ��ip:port/url url��Ϊ�ַ��ı�ʶ  ��MyTomcat���map���keyֵ
	 * @param clazz ��Servlet��λ�� ���ڷ��䴴������ ��MyTomcat���map���valueֵ
	 */
	public ServletMapping(String servletName, String url, String clazz) {
		super();
		this.servletName = servletName;
		this.url = url;
		this.clazz = clazz;
	}
	
	
	
}
