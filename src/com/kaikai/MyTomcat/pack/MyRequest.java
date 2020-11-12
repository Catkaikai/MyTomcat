package com.kaikai.MyTomcat.pack;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ���� kaikai:
 * @version ����ʱ�䣺2020��7��15�� ����12:41:56
 * @Description ��˵�� ��װ�������
 */
public class MyRequest {
	private String url;
	private String method;

	
	public MyRequest(InputStream inputStream) throws IOException {
		//System.out.println("Request������");
		String httpRequest = "";
		byte[] httpRequsestbytes = new byte[1024];
		int length = 0;
		// ��������������ݶ�ȡ��byte������
		if ((length = inputStream.read(httpRequsestbytes)) > 0) {
			// ͨ��String�Ĺ��췽����byte������洢������ת�����ַ���
			httpRequest = new String(httpRequsestbytes, 0, length);
		}
		// ����ͷ����
//		GET /getData HTTP/1.1
//		Host: localhost:8089
//		User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0
//		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//		Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//		Accept-Encoding: gzip, deflate
//		Connection: keep-alive
//		Upgrade-Insecure-Requests: 1
//		Cache-Control: max-age=0

		// ��ȡ����ַ�
		String httpHead = httpRequest.split("\n")[0];
		//System.out.println(httpHead);
		this.method=httpHead.split(" ")[0];
		this.url=httpHead.split(" ")[1];
	}
	//getter����

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
