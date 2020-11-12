package com.kaikai.MyTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/** 
 * @author ���� kaikai: 
 * @version ����ʱ�䣺2020��7��15�� ����4:53:48 
 * @Description ��˵�� 
 */
public class MyTomcat {
	private int port = 8080;
	private int poolsize = 0;
	public static Map<String, String> urlServerletMap = new HashMap<>();
	public static void main(String[] args) {
		new MyTomcat().setPort(8089).start();
	}
	/**
	 * һ������
	 */
	public void start() {
		initServerletMapping();
		initProcess();
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
	 * ����socket�����з���
	 */
	public void initProcess() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.�Զ˿ڴ���serverSocket");
			// serverSocketδ�ر����һֱ����socket
			Socket socket = null;
			while (true) {
				socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				/**
				 * �ַ�ǰ���жϣ����������url�Ƿ���ServerletMap���� to do :��װ���ж��߼�Ϊ��������
				 */
				if (urlServerletMap.containsKey(myRequest.getUrl())) {
					System.out.println("--------------��ʼ�ַ�����-----------------");
					dispatch(myRequest, myResponse);
					System.out.println("�����Ѿ����ַ�");
					System.out.println("--------------�����ַ�����-----------------");
				} else {
					// System.out.println(myRequest);
					//System.out.println("���������δ����ַ�");
				}
				socket.close();
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
	 * ͨ������request����url�õ�serletʵ��������䲢�ַ���Ӧ��response�� ͨ��url�Ĳ�ͬ�������Ĳ�ͬ�Ķ����ʵ�ַַ�
	 * @param myRequest
	 * @param myResponse
	 */
	@SuppressWarnings("unchecked")
	public synchronized static void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("ͨ�����䴴����Ӧ��MyServlet�������");
		// ��ȡ������Ҫ����·��
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// ����ͨ�����䴴�������MyServlet����
		try {
			// �õ���
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// ����Myservlet�������
			MyServlet myServlet = myservletClass.newInstance();
			// ͨ�����ø���MyServlet��service��������������doGet����doPost
			myServlet.service(myRequest, myResponse);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �޲ι�����
	 */
	public MyTomcat() {

	}

	/**
	 * ��ʽ�������ö˿ڲ���
	 * @param port ���ж˿�(Ĭ��Ϊ8080)
	 * @return
	 */
	public MyTomcat setPort(int port) {
		this.port = port;
		return this;
	}
}
