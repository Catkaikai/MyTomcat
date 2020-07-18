package com.kaikai.MyTomcat.MyThread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ���� kaikai:
 * @version ����ʱ�䣺2020��7��18�� ����5:03:14
 * @Description ��˵��
 */
public class testThread extends Thread {
	String host;
	
	@Override
	public void run() {
		test();
	}

	public void test() {
		try {
			int port = 8089;
			Socket socket = new Socket(host, port);
			OutputStream outputStream = socket.getOutputStream();
			StringBuffer Request = new StringBuffer();
			Request.append("GET /t1 HTTP/1.1\n").append("\r\n");
			// ����Ӧͷд������� ����HTTPЭ��
			outputStream.write(Request.toString().getBytes());
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public testThread() {

	}

	public testThread(String host) {
		this.host=host;
	}
	
}
