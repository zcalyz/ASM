package com.tclassloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyUrlclassLoader extends URLClassLoader{
	
	public MyUrlclassLoader() throws MalformedURLException{
		super(new URL[]{new URL("file:/home/zc/jar/apm-agent.jar")});
	}
	public MyUrlclassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
	
	public void myAddUrl(URL url) throws Exception{

			 URL u = new URL("file:/home/zc/apm/lib/Execise.jar");
			 addURL(u);
	}
	public void add(){
		 System.out.println("------");
	 }

}
