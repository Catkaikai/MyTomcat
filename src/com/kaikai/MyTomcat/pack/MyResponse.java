package com.kaikai.MyTomcat.pack;
/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午1:30:30 
* @Description 类说明 封装响应对象
*/

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
	private OutputStream outputStream;
	public MyResponse(OutputStream outputStream) {
		this.outputStream=outputStream;
	}
	
	public void write(String content) throws IOException {
		//响应报文样例
//		HTTP/1.1 200 
//		Content-Type: application/json
//		Transfer-Encoding: chunked
//		Date: Wed, 15 Jul 2020 04:35:08 GMT
//		Keep-Alive: timeout=60
//		Connection: keep-alive
		//设置响应头编码
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
		//将响应头写入输出流 基于HTTP协议
		outputStream.write(httpResponse.toString().getBytes()); 
		System.out.println(this+"已经响应浏览器的请求");
		outputStream.close();
		
	}
	
}
