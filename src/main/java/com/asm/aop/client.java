package com.asm.aop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.classloader.MyClassLoader;



public class client {
	public static void main(String[] args) {
		try {
			ClassReader classReader = new ClassReader(Target.class.getName());
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			MyCodeModify mv = new MyCodeModify(Opcodes.ASM4, cw);
			
			classReader.accept(mv, ClassReader.SKIP_DEBUG);
			byte[] byteArray = cw.toByteArray();
			
			Class<?> clazz = new MyClassLoader().myDefineClass(byteArray, Target.class.getName());
			
			Method method = clazz.getMethod("sayHello");
			method.invoke(clazz.newInstance(),null);
			
			FileOutputStream out = new FileOutputStream("/home/zc/Target.class");
			out.write(byteArray);
			out.close();
//			Object newInstance = clazz.newInstance();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
