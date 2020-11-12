package com.kaikai.MyTomcat.pack;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 作者 kaikai:
 * @version 创建时间：2020年7月15日 下午12:41:56
 * @Description 类说明 封装请求对象
 */
public class MyRequest {
	private String url;
	private String method;

	
	public MyRequest(InputStream inputStream) throws IOException {
		//System.out.println("Request被创建");
		String httpRequest = "";
		byte[] httpRequsestbytes = new byte[1024];
		int length = 0;
		// 将输入流里的内容读取到byte数组中
		if ((length = inputStream.read(httpRequsestbytes)) > 0) {
			// 通过String的构造方法将byte数组里存储的数据转换成字符串
			httpRequest = new String(httpRequsestbytes, 0, length);
		}
		// 请求头例子
//		GET /getData HTTP/1.1
//		Host: localhost:8089
//		User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0
//		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//		Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//		Accept-Encoding: gzip, deflate
//		Connection: keep-alive
//		Upgrade-Insecure-Requests: 1
//		Cache-Control: max-age=0

		// 提取相关字符
		String httpHead = httpRequest.split("\n")[0];
		//System.out.println(httpHead);
		this.method=httpHead.split(" ")[0];
		this.url=httpHead.split(" ")[1];
	}
	//getter方法

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "MyRequest [url=" + url + ", method=" + method + "]";
	}
	
	
}
