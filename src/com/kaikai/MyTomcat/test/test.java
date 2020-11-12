package com.kaikai.MyTomcat.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kaikai.MyTomcat.MyAnnotation.MyWebServlet;
import com.kaikai.MyTomcat.pack.MyServlet;
import com.kaikai.MyTomcat.utils.ScanPackageUtil;

/**
 * @author ���� kaikai:
 * @version ����ʱ�䣺2020��7��15�� ����12:29:08
 * @Description ��˵��
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
				Thread.currentThread().setName("�߳�1");
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
						System.out.println(Thread.currentThread().getName() + "��:" + i);
					} else
						System.out.println(Thread.currentThread().getName() + ":" + i);
				}
				System.out.println("�߳�1�ѽ���");
			}
		});
		pool.execute(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("�߳�2");
				// TODO Auto-generated method stub
				for (int i = 1; i <= 10; i++) {
					Thread.currentThread().interrupt();
				}
				System.out.println("�߳�2�ѽ���");
			}
		});
		pool.execute(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("�߳�3");
				// TODO Auto-generated method stub
				for (int i = 1; i <= 10; i++) {
					System.out.println(Thread.currentThread().getName() + ":" + i);
				}
				System.out.println("�߳�3�ѽ���");
			}
		});
	}

	/**
	 * ����ɨ�蹦��
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
					System.out.println("ע��"+m.url()+m.name());
				}
			}
		}
	}

}
