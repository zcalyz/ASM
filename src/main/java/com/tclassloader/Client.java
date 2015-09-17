package com.tclassloader;

import java.io.File;
import java.net.URL;
import com.classloader.MyClassLoader;

public class Client {
	public static void main(String[] args) {
		
		MyClassLoader myClassLoader = new MyClassLoader();
		URL url;
		try {
			File file = new File("/home/zc/jar/module.jar");
			url = file.toURL();

			MyUrlclassLoader loader = new MyUrlclassLoader(new URL[]{url});
			//  扩展类加载路径
			file = new File("/home/zc/apm/lib/Execise.jar");
			// "file:/home/zc/apm/lib/Execise.jar"
			loader.myAddUrl(file.toURL());
			
			// 查看当前classloader的类加载路径
			URL[] urLs = loader.getURLs();
			for(URL u : urLs){
				System.out.println(u);
			}
			Class<?> clazz = loader.loadClass("com.entity.User"); //在路径/home/zc/apm/lib/Execise.jar 下
			//测试是否加载成功
			System.out.println(clazz);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
