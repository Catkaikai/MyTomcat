package com.kaikai.MyTomcat.pack;
/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����2:15:40 
* @Description ��˵��  Servlet���������
*/
public abstract class MyServlet {
	
	/**
	 * Tomcat������Servlet�淶����������ô��ȻTomcat��Ҫ�ṩAPI�����￴����Servlet������doGet/doPost/service������
	 * @param myRequest
	 * @param myResponse
	 */
	public abstract void doGet(MyRequest myRequest,MyResponse myResponse);
	public abstract void doPost(MyRequest myRequest,MyResponse myResponse);
	/**
	 * ����MyServlet��service������������������󷽷�
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
