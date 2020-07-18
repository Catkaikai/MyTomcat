package com.kaikai.MyTomcat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.text.AbstractDocument.BranchElement;

import com.kaikai.MyTomcat.MyThread.testThread;

/**
 * @author ���� kaikai:
 * @version ����ʱ�䣺2020��7��15�� ����12:29:08
 * @Description ��˵��
 */
public class test {
	public static void main(String[] args) throws IOException {
		test2();
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
	 * ���Ը߲�������
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
			System.out.println("����" + i++);
			if (i % 10 == 0) {
				//m++;
				//host = "192.168.98." + m;
				
			}
			if (i <= 100) {
				testThread thread = new testThread(host);
				thread.start();
			} else {
				break;
			}
		}
	}
}
