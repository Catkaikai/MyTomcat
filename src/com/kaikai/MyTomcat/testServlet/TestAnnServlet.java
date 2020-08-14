package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyServlet;
import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��8��13�� ����9:50:26 
* @Description ��˵�� 
*/
@MyWebServlet(url="/t3")
public class TestAnnServlet extends MyServlet {
	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("����ע�� get��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("����ע�� post��ʽ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
