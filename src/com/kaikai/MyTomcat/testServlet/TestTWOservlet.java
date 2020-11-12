package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.pack.MyRequest;
import com.kaikai.MyTomcat.pack.MyResponse;
import com.kaikai.MyTomcat.pack.MyServlet;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午3:33:56 
* @Description 类说明one测试类 实现Myservlet
*/
public class TestTWOservlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试二 get方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试二 post方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
