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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kaikai.MyTomcat.MyThread.MyIOThread;

public class MyTomcat {
	private int port = 8080;
	private int poolsize = 0;
	public static Map<String, String> urlServerletMap = new HashMap<>();
	public static void main(String[] args) {
		new MyTomcat().setPort(8089).setPoolsize(5).start();
	}
	/**
	 * һ������
	 */
	public void start() {
		initServerletMapping();
		Multithreading();
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

	public void NoMultithreading() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.�Զ˿ڴ���serverSocket");
			// serverSocketδ�ر����һֱ����socket
			Socket socket = null;
			while (true) {
				System.out.println("-----------------");
				System.out.println("--------------��ʼ�ַ�����-----------------");
				System.out.println("3.��IO����װ��request��response");
				socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				/**
				 * �ַ�ǰ���жϣ����������url�Ƿ���ServerletMap���� to do :��װ���ж��߼�Ϊ��������
				 */
				if (urlServerletMap.containsKey(myRequest.getUrl())) {
					dispatch(myRequest, myResponse);
					System.out.println("5.�����Ѿ����ַ�");
				} else {
					// System.out.println(myRequest);
					System.out.println("5.��Ч����δ����ַ�");
				}
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
	public void Multithreading() {
		if (poolsize == 0) {
			NoMultithreading();
			return;
		} else {
			int i=1;
			//ExecutorService pool = Executors.newFixedThreadPool(poolsize);
			ExecutorService pool = Executors.newCachedThreadPool();
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("MyTomcat is start...");
				System.out.println("2.�Զ˿ڴ���serverSocket");
				// serverSocketδ�ر����һֱ����socket
				while(true) {
					Socket socket = serverSocket.accept();
					MyIOThread myIOThread = new MyIOThread(socket);
					pool.execute(myIOThread);
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ͨ������request����url�õ�serletʵ��������䲢�ַ���Ӧ��response�� ͨ��url�Ĳ�ͬ�������Ĳ�ͬ�Ķ����ʵ�ַַ�
	 * 
	 * @param myRequest
	 * @param myResponse
	 */
	public synchronized static void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("4.ͨ�����䴴����Ӧ��MyServlet�������");
		// ��ȡ������Ҫ����·��
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// ����ͨ�����䴴�������MyServlet����
		try {
			// �õ���
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// ����Myservlet�������
			MyServlet myServlet = myservletClass.newInstance();
			// ����MyServlet��service�������������������ʽ��post����get
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

	/**
	 * ��ʽ���������̳߳ش�С����
	 * 
	 * @param poolsize �̳߳ش�С(Ĭ��Ϊ0����ʾ���������߳�)
	 * @return
	 */
	public MyTomcat setPoolsize(int poolsize) {
		this.poolsize = poolsize;
		return this;
	}

}
