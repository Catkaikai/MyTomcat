package com.kaikai.MyTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Port;

import com.kaikai.MyTomcat.config.ServletMappingConfig;
import com.kaikai.MyTomcat.config.propertiesConfig;
import com.kaikai.MyTomcat.pack.MyRequest;
import com.kaikai.MyTomcat.pack.MyResponse;
import com.kaikai.MyTomcat.pack.MyServlet;
import com.kaikai.MyTomcat.pack.MySrcServlet;
import com.kaikai.MyTomcat.pack.ServletMapping;

/** 
 * @author 作者 kaikai: 
 * @version 创建时间：2020年7月15日 下午4:53:48 
 * @Description 类说明 
 */
public class MyTomcat {
	/**
	 * 默认端口8080
	 */
	private int port = 8080;
	public static HashMap<String, String> urlServerletMap = new HashMap<>();
	public static HashMap<String, MySrcServlet> urlSrcServerletMap = new HashMap<>();
	/**
	 * main方法
	 * @param args
	 */
	public static void main(String[] args) {
		new MyTomcat().setPort(propertiesConfig.port).start();
	}
	/**
	 * 一键启动
	 */
	public void start() {
		initServerletMapping();
		initProcess();
	}

	/**
	 * 加载所有的ServerletMapping到MyTomcat里的hashmap里
	 */
	public void initServerletMapping() {
		System.out.println("1.读取并初始化ServerletMapping集合");
		for (ServletMapping servletMapping : ServletMappingConfig.servletMappinglist) {
			urlServerletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
		for (ServletMapping servletMapping : ServletMappingConfig.SrcservletMappinglist) {
			urlSrcServerletMap.put(servletMapping.getUrl(), servletMapping.getMysrcservlet());
		}
	}
	/**
	 * 创建socket并运行服务
	 */
	public void initProcess() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.以端口创建serverSocket");
			// serverSocket未关闭则可一直创建socket
			Socket socket = null;
			while (true) {
				socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				/**
				 * 分发前做判断，看所请求的url是否在ServerletMap里面 to do :封装该判断逻辑为方法调用
				 */
				if (urlServerletMap.containsKey(myRequest.getUrl())) {
					//System.out.println("--------------开始分发流程-----------------");
					dispatch(myRequest, myResponse);
					//System.out.println("请求已经被分发");
					//System.out.println("--------------结束分发流程-----------------");
				} else if(urlSrcServerletMap.containsKey(myRequest.getUrl())){
					urlSrcServerletMap.get(myRequest.getUrl()).service(myRequest, myResponse);
				}else {
					// System.out.println(myRequest);
					//System.out.println("非相关请求未参与分发");
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
	 * 通过请求（request）的url得到serlet实现类而反射并分发响应（response） 通过url的不同而创建的不同的对象而实现分发
	 * @param myRequest
	 * @param myResponse
	 */
	@SuppressWarnings("unchecked")
	public static void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("通过反射创建对应的MyServlet子类对象");
		// 获取反射需要的类路径
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// 反射通过反射创建请求的MyServlet子类
		try {
			// 得到类
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// 创建Myservlet子类对象
			MyServlet myServlet = myservletClass.newInstance();
			// 通过调用父类MyServlet的service方法会帮子类决定doGet还是doPost
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
	 * 无参构造器
	 */
	public MyTomcat() {

	}

	/**
	 * 链式调用设置端口参数
	 * @param port 运行端口(默认为8080)
	 * @return
	 */
	public MyTomcat setPort(int port) {
		this.port = port;
		return this;
	}
}
