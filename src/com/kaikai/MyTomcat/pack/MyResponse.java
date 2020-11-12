package com.kaikai.MyTomcat.pack;
/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����1:30:30 
* @Description ��˵�� ��װ��Ӧ����
*/

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
	private OutputStream outputStream;
	public MyResponse(OutputStream outputStream) {
		this.outputStream=outputStream;
	}
	
	public void write(String content) throws IOException {
		//��Ӧ��������
//		HTTP/1.1 200 
//		Content-Type: application/json
//		Transfer-Encoding: chunked
//		Date: Wed, 15 Jul 2020 04:35:08 GMT
//		Keep-Alive: timeout=60
//		Connection: keep-alive
		//������Ӧͷ����
		StringBuffer httpResponse = new StringBuffer();
		httpResponse.append("HTTP/1.1 200 OK")
					.append("\n")
					.append("Content-Type: text/html")
					.append("\n")
					.append("\r\n")
					.append("<!DOCTYPE html>")
					.append("<html><body>")
					.append(content)
					.append("</boy></html>");
		//����Ӧͷд������� ����HTTPЭ��
		outputStream.write(httpResponse.toString().getBytes()); 
		System.out.println(this+"�Ѿ���Ӧ�����������");
		outputStream.close();
		
	}
	
}
