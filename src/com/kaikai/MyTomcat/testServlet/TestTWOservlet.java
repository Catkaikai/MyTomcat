package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.pack.MyRequest;
import com.kaikai.MyTomcat.pack.MyResponse;
import com.kaikai.MyTomcat.pack.MyServlet;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����3:33:56 
* @Description ��˵��one������ ʵ��Myservlet
*/
public class TestTWOservlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("���Զ� get��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("���Զ� post��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
