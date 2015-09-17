package com.asm.aop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.asm.aop2.MyCodeModifyT;
import com.classloader.MyClassLoader;
import com.tclassloader.MyUrlclassLoader;



public class client {
	//拦截Target类的sayHello方法,并在方法执行前后加入自定义的逻辑
	public static void main(String[] args) {
		try {
			ClassReader classReader = new ClassReader(Target.class.getName());   //com.asm.aop.Target
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			MyCodeModify mv = new MyCodeModify(Opcodes.ASM4, cw);
			
			classReader.accept(mv, ClassReader.SKIP_DEBUG);
			byte[] byteArray = cw.toByteArray();
			//自定义classLoader,根据byte数组返回相应类型的class文件
			Class<?> clazz = new MyClassLoader().myDefineClass(byteArray, Target.class.getName());
			
		    Method method = clazz.getMethod("sayHello");
		    method.invoke(clazz.newInstance(),null);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
