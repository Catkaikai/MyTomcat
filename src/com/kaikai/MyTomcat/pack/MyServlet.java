package com.kaikai.MyTomcat.pack;
/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午2:15:40 
* @Description 类说明  Servlet请求处理基类
*/
public abstract class MyServlet {
	
	/**
	 * Tomcat是满足Servlet规范的容器，那么自然Tomcat需要提供API。这里看到了Servlet常见的doGet/doPost/service方法。
	 * @param myRequest
	 * @param myResponse
	 */
	public abstract void doGet(MyRequest myRequest,MyResponse myResponse);
	public abstract void doPost(MyRequest myRequest,MyResponse myResponse);
	/**
	 * 父类MyServlet的service方法会帮子类区分请求方法
	 * @param myRequest
	 * @param myResponse
	 */
	public void service(MyRequest myRequest,MyResponse myResponse) {
		if(myRequest.getMethod().equalsIgnoreCase("POST")) {
			this.doPost(myRequest, myResponse);
		}else if(myRequest.getMethod().equalsIgnoreCase("GET")) {
			this.doGet(myRequest, myResponse);
		}
	}
}	
