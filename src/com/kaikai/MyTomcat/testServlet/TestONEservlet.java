package com.kaikai.MyTomcat.testServlet;

import java.io.IOException;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyServlet;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午3:33:18 
* @Description 类说明two测试类 实现Myservlet
*/
public class TestONEservlet extends MyServlet {

	@Override
	public void doGet(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试一 get方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(MyRequest myRequest, MyResponse myResponse) {
		try {
			myResponse.write("测试一 post方式");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
