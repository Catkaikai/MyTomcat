package com.kaikai.MyTomcat.pack;

import java.io.IOException;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年11月12日 下午10:27:14 
* @Description 类说明 静态资源的servlet类 为静态资源创建不同的对象
*/
public class MySrcServlet extends MyServlet{
	private String src;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public MySrcServlet() {
		
	}
	
	public MySrcServlet(String src) {
		super();
		this.src = src;
	}
	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write(src);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		doGet(myRequest, myResponse);
	}
	
	
}
