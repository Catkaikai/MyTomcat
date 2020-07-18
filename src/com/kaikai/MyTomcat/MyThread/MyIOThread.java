package com.kaikai.MyTomcat.MyThread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyTomcat;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月18日 下午2:14:01 
* @Description 类说明 
*/
public class MyIOThread extends Thread {
	public Socket socket;
	@Override
	public void run() {
		try {
			System.out.println("--------------开始分发流程-----------------");
			System.out.println(Thread.currentThread().getName());
			System.out.println("3.将IO流封装成request和response");
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();
			MyResponse myResponse = new MyResponse(outputStream);
			MyRequest myRequest = new MyRequest(inputStream);
			/**
			 * 分发前做判断，看所请求的url是否在ServerletMap里面 to do :封装该判断逻辑为方法调用
			 */
			if (MyTomcat.urlServerletMap.containsKey(myRequest.getUrl())) {
				MyTomcat.dispatch(myRequest, myResponse);
				System.out.println("5.请求已经被分发");
			} else {
				// System.out.println(myRequest);
				System.out.println("5.无效请求未参与分发");
			}
			socket.close();
			System.out.println("--------------结束分发流程-----------------");
			//Thread.currentThread().sleep(3000);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MyIOThread() {
		
	}
	public MyIOThread(Socket socket) {
		this.socket=socket;
	}	
}
