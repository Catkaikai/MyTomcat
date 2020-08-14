package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyServlet;
import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年8月13日 下午9:50:26 
* @Description 类说明 
*/
@MyWebServlet(url="/t3")
public class TestAnnServlet extends MyServlet {
	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试注解 get方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试注解 post方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
