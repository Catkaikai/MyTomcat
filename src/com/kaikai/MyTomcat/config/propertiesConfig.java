package com.kaikai.MyTomcat.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 作者 kaikai:
 * @version 创建时间：2020年8月22日 下午8:42:51
 * @Description 类说明 读取jar包外的配置文件
 */
public class propertiesConfig {

	/**
	 * 服务运行的端口
	 */
	public static Integer port;

	static {
		Properties properties = new Properties();
		InputStream in = null;

		// 优先从项目同级路径获取连接信息
		String confPath = System.getProperty("user.dir");
		confPath = confPath + File.separator + "config\\config.properties";
		File file = new File(confPath);
		if (file.exists()) {
			System.out.println("配置文件路径---->>" + confPath);
			try {
				in = new FileInputStream(new File(confPath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// 没有外部路径时，读取classpath路径src下
		else {
			System.out.println("项目存储路径[" + confPath + "]下无配置信息，从classpath路径下加载");
			in = propertiesConfig.class.getClassLoader().getResourceAsStream("config.properties");
		}
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		port = new Integer(properties.getProperty("port"));

	}

}
