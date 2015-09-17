package com.asm.aop2;




import java.lang.reflect.Method;
import java.net.URL;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.classloader.MyClassLoader;

public class Client {
	public static void main(String[] args) {
		
		String targetName = MyUrlClassLoader.class.getName();  
		try {
			ClassReader classReader = new ClassReader(targetName);
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			MyCodeModifyT mv = new MyCodeModifyT(Opcodes.ASM4, classWriter);
			classReader.accept(mv, ClassReader.SKIP_DEBUG);
			
			byte[] byteArray = classWriter.toByteArray();
			//自定义classloader加载类
			MyClassLoader myClassLoader = new MyClassLoader();
			
		    Class<?> clazz = myClassLoader.myDefineClass( byteArray,MyUrlClassLoader.class.getName());
		    Object loaderObj = clazz.newInstance();
		   
			//查看当前的所有classpath
			Method getUrlMethod = clazz.getMethod("getURLs");
			URL[] urls = (URL[]) getUrlMethod.invoke(loaderObj, null);
			for(URL u : urls){
				System.out.println(u);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
