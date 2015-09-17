package com.asm.aop2;

public class MyClassLoader extends ClassLoader{
	
	protected final Class<?> MyDefineClass(String name, byte[] b){
	  return defineClass(name, b, 0, b.length);
	}
}
