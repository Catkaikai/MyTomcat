package com.kaikai.MyTomcat.MyThread;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.kaikai.MyTomcat.MyTomcat;

public class Listner implements Runnable {
    private WatchService service;
    private String rootPath;
    
    public Listner(WatchService service,String rootPath) {
        this.service = service;
        this.rootPath = rootPath;
    }

    public void run() {
        try {
            while(true){
                WatchKey watchKey = service.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                MyTomcat.getCall(System.currentTimeMillis());
                for(WatchEvent<?> event : watchEvents){                
                    //TODO 根据事件类型采取不同的操作。。。。。。。
                    //System.out.println("目录["+rootPath+"]下的["+event.context()+"]文件发生了["+event.kind()+"]事件"+event.count()+"次");  
                }
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            System.out.println("监听已结束");
            try {
                service.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
