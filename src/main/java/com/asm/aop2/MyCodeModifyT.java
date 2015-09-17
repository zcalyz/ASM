package com.asm.aop2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class MyCodeModifyT extends ClassVisitor{

	public MyCodeModifyT(int api, ClassVisitor cv) {
		super(api, cv);
	}
	@Override
	 public MethodVisitor visitMethod(int access, String name, String desc,
	             String signature, String[] exceptions) {
		//拦截构造方法
		if("<init>".equals(name)){
			System.out.println("transform method " + name);
			MethodVisitor mv = cv.visitMethod(access, name, desc, signature, new String[] { "java/lang/Exception" }); // 给方法添加exception
			mv = new MyMethodVisitor(this.api, mv, access, name, desc);
			return mv;
		}
		 return cv.visitMethod(access, name, desc, signature, exceptions);
	 }
	
 	private class MyMethodVisitor extends AdviceAdapter{

		protected MyMethodVisitor(int api, MethodVisitor mv, int access,
				String name, String desc) {
			super(api, mv, access, name, desc);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		 protected void onMethodExit(int opcode) {
			 System.out.println("match success!");
				// 在匹配的方法前加入如下代码:
				
				// code:------- URL u =  new URL("file:/home/zc/jar/apm-trace")
				mv.visitTypeInsn(Opcodes.NEW, "java/net/URL");
				mv.visitInsn(Opcodes.DUP);
				mv.visitLdcInsn("file:/home/zc/jar/apm-trace.jar");
				mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/net/URL", "<init>", "(Ljava/lang/String;)V");
				//1 可用替换为newLocal(Type.getType(URL.class)) --->Ljava/net/URL;
				mv.visitVarInsn(ASTORE, 1); 
				
				// code:--------- addURL(u);
				mv.visitVarInsn(Opcodes.ALOAD, 0);
				mv.visitVarInsn(Opcodes.ALOAD, 1);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/tclassloader/MyUrlclassLoader", "addURL", "(Ljava/net/URL;)V");
		 }
	}
}
