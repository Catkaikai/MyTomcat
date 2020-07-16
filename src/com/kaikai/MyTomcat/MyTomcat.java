package com.kaikai.MyTomcat;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午4:53:48 
* @Description 类说明 
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
	 * @param port Tomcat运行的端口，默认是8080
	 */
	public MyTomcat(int port) {
		this.port = port;
	}

	public MyTomcat() {

	}

	/**
	 * 一键启动
	 */
	public void start() {
		int i = 1;
		initServerletMapping();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.以端口创建serverSocket");
			// serverSocket未关闭则可一直创建socket
			while (true) {
				System.out.println("--------------开始分发流程-----------------");
				System.out.println("循环创建socket了" + i++ +"次");
				System.out.println("3.将IO流封装成request和response");
				Socket socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				dispatch(myRequest, myResponse);
				System.out.println("5.请求已经被分发");
				socket.close();
				System.out.println("--------------结束分发流程-----------------");
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
	 * 加载所有的ServerletMapping到MyTomcat里的hashmap里
	 */
	public void initServerletMapping() {
		System.out.println("1.读取并初始化ServerletMapping集合");
		for (ServletMapping servletMapping : ServletMappingConfig.servletMappinglist) {
			urlServerletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
	}

	/**
	 * 通过请求（request）的url得到serlet实现类而反射并分发响应（response）
	 * 
	 * @param myRequest
	 * @param myResponse
	 */
	public void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("4.通过反射创建对应的MyServlet子类对象");
		// 获取反射需要的类路径
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// 反射通过反射创建请求的MyServlet子类
		try {
			// 得到类
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// 创建对象
			MyServlet myServlet = myservletClass.newInstance();
			// 父类MyServlet的service方法会帮子类区分请求方法
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
