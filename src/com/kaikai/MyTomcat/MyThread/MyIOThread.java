package com.kaikai.MyTomcat.MyThread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.kaikai.MyTomcat.MyRequest;
import com.kaikai.MyTomcat.MyResponse;
import com.kaikai.MyTomcat.MyTomcat;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��18�� ����2:14:01 
* @Description ��˵�� 
*/
public class MyIOThread extends Thread {
	public Socket socket;
	@Override
	public void run() {
		try {
			System.out.println("--------------��ʼ�ַ�����-----------------");
			System.out.println(Thread.currentThread().getName());
			System.out.println("3.��IO����װ��request��response");
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();
			MyResponse myResponse = new MyResponse(outputStream);
			MyRequest myRequest = new MyRequest(inputStream);
			/**
			 * �ַ�ǰ���жϣ����������url�Ƿ���ServerletMap���� to do :��װ���ж��߼�Ϊ��������
			 */
			if (MyTomcat.urlServerletMap.containsKey(myRequest.getUrl())) {
				MyTomcat.dispatch(myRequest, myResponse);
				System.out.println("5.�����Ѿ����ַ�");
			} else {
				// System.out.println(myRequest);
				System.out.println("5.��Ч����δ����ַ�");
			}
			socket.close();
			System.out.println("--------------�����ַ�����-----------------");
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
