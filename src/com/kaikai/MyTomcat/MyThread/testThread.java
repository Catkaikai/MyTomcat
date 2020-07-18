package com.kaikai.MyTomcat.MyThread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 作者 kaikai:
 * @version 创建时间：2020年7月18日 下午5:03:14
 * @Description 类说明
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
			// 将响应头写入输出流 基于HTTP协议
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
