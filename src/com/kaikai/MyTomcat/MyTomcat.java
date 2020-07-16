package com.kaikai.MyTomcat;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����4:53:48 
* @Description ��˵�� 
*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {
	private int port = 8080;
	private Map<String, String> urlServerletMap = new HashMap<>();

	public static void main(String[] args) {
		new MyTomcat(8089).start();
	}

	/**
	 * 
	 * @param port Tomcat���еĶ˿ڣ�Ĭ����8080
	 */
	public MyTomcat(int port) {
		this.port = port;
	}

	public MyTomcat() {

	}

	/**
	 * һ������
	 */
	public void start() {
		int i = 1;
		initServerletMapping();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.�Զ˿ڴ���serverSocket");
			// serverSocketδ�ر����һֱ����socket
			while (true) {
				System.out.println("--------------��ʼ�ַ�����-----------------");
				System.out.println("ѭ������socket��" + i++ +"��");
				System.out.println("3.��IO����װ��request��response");
				Socket socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				dispatch(myRequest, myResponse);
				System.out.println("5.�����Ѿ����ַ�");
				socket.close();
				System.out.println("--------------�����ַ�����-----------------");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �������е�ServerletMapping��MyTomcat���hashmap��
	 */
	public void initServerletMapping() {
		System.out.println("1.��ȡ����ʼ��ServerletMapping����");
		for (ServletMapping servletMapping : ServletMappingConfig.servletMappinglist) {
			urlServerletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
	}

	/**
	 * ͨ������request����url�õ�serletʵ��������䲢�ַ���Ӧ��response��
	 * 
	 * @param myRequest
	 * @param myResponse
	 */
	public void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("4.ͨ�����䴴����Ӧ��MyServlet�������");
		// ��ȡ������Ҫ����·��
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// ����ͨ�����䴴�������MyServlet����
		try {
			// �õ���
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// ��������
			MyServlet myServlet = myservletClass.newInstance();
			// ����MyServlet��service������������������󷽷�
			myServlet.service(myRequest, myResponse);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
