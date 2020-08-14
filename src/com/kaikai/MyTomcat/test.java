package com.kaikai.MyTomcat;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.text.AbstractDocument.BranchElement;

import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;
import com.kaikai.MyTomcat.MyThread.testThread;
import com.kaikai.MyTomcat.utils.ScanPackageUtil;

/**
 * @author 作者 kaikai:
 * @version 创建时间：2020年7月15日 下午12:29:08
 * @Description 类说明
 */
public class test {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		test3();
	}

	public void test1() {
		System.out.println("\n");
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.execute(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("线程1");
				// TODO Auto-generated method stub
				for (int i = 1; i <= 20; i++) {
					if (i == 5) {
						System.out.println(Thread.currentThread().getName() + ":" + i);
						try {
							Thread.currentThread().sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "后:" + i);
					} else
						System.out.println(Thread.currentThread().getName() + ":" + i);
				}
				System.out.println("线程1已结束");
			}
		});
		pool.execute(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("线程2");
				// TODO Auto-generated method stub
				for (int i = 1; i <= 10; i++) {
					Thread.currentThread().interrupt();
				}
				System.out.println("线程2已结束");
			}
		});
		pool.execute(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("线程3");
				// TODO Auto-generated method stub
				for (int i = 1; i <= 10; i++) {
					System.out.println(Thread.currentThread().getName() + ":" + i);
				}
				System.out.println("线程3已结束");
			}
		});
	}

	/**
	 * 测试高并发性能
	 * 
	 * @throws IOException
	 */
	public static void test2() throws IOException {
//		GET /t1 HTTP/1.1
//		Host: localhost:8089
//		User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0
//		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//		Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//		Accept-Encoding: gzip, deflate
//		Connection: keep-alive
//		Upgrade-Insecure-Requests: 1
//		Cache-Control: max-age=0

		int i = 0;
		int m = 5;
		String host = null;
		while (true) {
			System.out.println("请求" + i++);
			if (i % 10 == 0) {
				// m++;
				// host = "192.168.98." + m;

			}
			if (i <= 100) {
				testThread thread = new testThread(host);
				thread.start();
			} else {
				break;
			}
		}
	}

	/**
	 * 测试扫描功能
	 * 
	 * @throws ClassNotFoundException
	 */
	public static void test3() throws ClassNotFoundException {
		List<String> fileList = new ArrayList<>();
		File baseFile = new File("src");
		ScanPackageUtil.getSubFileNameList(baseFile, fileList);
		System.out.println(fileList);
		for (String name : fileList) {
			if (ScanPackageUtil.isChildClass(name, MyServlet.class)) {
				Class<?> c = Class.forName(name);
				Class<?> myservlet = Class.forName(name);
				if (myservlet.isAnnotationPresent(MyWebServlet.class)) { 
					System.out.println(name);
					//myservlet.getAnnotation();
					MyWebServlet m = myservlet.getAnnotation(MyWebServlet.class);
					System.out.println("注解"+m.url()+m.name());
				}
			}
		}
	}

}
