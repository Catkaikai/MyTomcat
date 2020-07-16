package com.kaikai.MyTomcat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
* @author 作者 kaikai: 
* @version 创建时间：2020年7月15日 下午12:29:08 
* @Description 类说明 
*/
public class test {
	public static void main(String[] args) {
		System.out.println("\n");
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.execute(new Runnable() {		
			@Override
			public void run() {
				Thread.currentThread().setName("线程1");
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
						System.out.println(Thread.currentThread().getName()+"后:"+i);
					}else
						System.out.println(Thread.currentThread().getName()+":"+i);
				}
				System.out.println("线程1已结束");
			}
		});
		pool.execute(new Runnable() {		
			@Override
			public void run() {
				Thread.currentThread().setName("线程2");
				// TODO Auto-generated method stub
				for(int i=1;i<=10;i++) {
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
				for(int i=1;i<=10;i++) {
						System.out.println(Thread.currentThread().getName()+":"+i);
				}
				System.out.println("线程3已结束");
			}
		});	
	}
}
