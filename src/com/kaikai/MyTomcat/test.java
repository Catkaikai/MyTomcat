package com.kaikai.MyTomcat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
* @author ���� kaikai: 
* @version ����ʱ�䣺2020��7��15�� ����12:29:08 
* @Description ��˵�� 
*/
public class test {
	public static void main(String[] args) {
		System.out.println("\n");
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.execute(new Runnable() {		
			@Override
			public void run() {
				Thread.currentThread().setName("�߳�1");
				// TODO Auto-generated method stub
				for(int i=1;i<=20;i++) {
					if(i==5) {
						System.out.println(Thread.currentThread().getName()+":"+i);
						try {
							Thread.currentThread().sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"��:"+i);
					}else
						System.out.println(Thread.currentThread().getName()+":"+i);
				}
				System.out.println("�߳�1�ѽ���");
			}
		});
		pool.execute(new Runnable() {		
			@Override
			public void run() {
				Thread.currentThread().setName("�߳�2");
				// TODO Auto-generated method stub
				for(int i=1;i<=10;i++) {
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
				for(int i=1;i<=10;i++) {
						System.out.println(Thread.currentThread().getName()+":"+i);
				}
				System.out.println("�߳�3�ѽ���");
			}
		});	
	}
}
