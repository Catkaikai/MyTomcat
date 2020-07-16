package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyServlet;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����3:33:18 
* @Description ��˵��two������ ʵ��Myservlet
*/
public class TestONEservlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("����һ get��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("����һ post��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
