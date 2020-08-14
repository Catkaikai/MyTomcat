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
	 * 一键启动
	 */
	public void start() {
		initServerletMapping();
		Multithreading();
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

	public void NoMultithreading() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat is start...");
			System.out.println("2.以端口创建serverSocket");
			// serverSocket未关闭则可一直创建socket
			Socket socket = null;
			while (true) {
				System.out.println("-----------------");
				System.out.println("--------------开始分发流程-----------------");
				System.out.println("3.将IO流封装成request和response");
				socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				MyResponse myResponse = new MyResponse(outputStream);
				MyRequest myRequest = new MyRequest(inputStream);
				/**
				 * 分发前做判断，看所请求的url是否在ServerletMap里面 to do :封装该判断逻辑为方法调用
				 */
				if (urlServerletMap.containsKey(myRequest.getUrl())) {
					dispatch(myRequest, myResponse);
					System.out.println("5.请求已经被分发");
				} else {
					// System.out.println(myRequest);
					System.out.println("5.无效请求未参与分发");
				}
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
				System.out.println("2.以端口创建serverSocket");
				// serverSocket未关闭则可一直创建socket
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
	 * 通过请求（request）的url得到serlet实现类而反射并分发响应（response） 通过url的不同而创建的不同的对象而实现分发
	 * 
	 * @param myRequest
	 * @param myResponse
	 */
	public synchronized static void dispatch(MyRequest myRequest, MyResponse myResponse) {
		System.out.println("4.通过反射创建对应的MyServlet子类对象");
		// 获取反射需要的类路径
		String clazz = urlServerletMap.get(myRequest.getUrl());
		// 反射通过反射创建请求的MyServlet子类
		try {
			// 得到类
			Class<MyServlet> myservletClass = (Class<MyServlet>) Class.forName(clazz);
			// 创建Myservlet子类对象
			MyServlet myServlet = myservletClass.newInstance();
			// 父类MyServlet的service方法会帮子类区分请求方式是post还是get
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

	/**
	 * 链式调用设置线程池大小参数
	 * 
	 * @param poolsize 线程池大小(默认为0，表示不开启多线程)
	 * @return
	 */
	public MyTomcat setPoolsize(int poolsize) {
		this.poolsize = poolsize;
		return this;
	}

}
