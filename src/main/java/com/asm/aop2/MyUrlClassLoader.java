package com.asm.aop2;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyUrlClassLoader extends URLClassLoader{
	
	public MyUrlClassLoader() throws MalformedURLException{
		super(new URL[]{new URL("file:/home/zc/jar/apm-agent.jar")});
	}
	public MyUrlClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
		
/*	public void myAddUrl(URL url) throws Exception{
			 URL u = new URL("file:/home/zc/apm/lib/Execise.jar");
			 addURL(u);
	}*/

}
