package com.asm.aop;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;


public class MyCodeModify extends ClassVisitor{

	 public MyCodeModify(int api, ClassVisitor cv) {
		super(api, cv);
		// TODO Auto-generated constructor stub
	}
	 @Override
	public MethodVisitor visitMethod(int access, String name, String desc, String  signature, String[] exceptions) 
	 {
		   
	     if("sayHello".equals(name)){
	    	 MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
	         mv = new MyMethodVisitor(this.api, mv, access, name, desc);   
	    	 return mv;
	     }
		       
	      return cv.visitMethod(access, name, desc, signature, exceptions);
	  }
	 //自定义MethodVisitor
	 private class MyMethodVisitor extends AdviceAdapter{
		 int newLocal;
	
		protected MyMethodVisitor(int api, MethodVisitor mv, int access,
				String name, String desc) {
			super(api, mv, access, name, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		 protected void onMethodEnter() {
			String targetName = MyAdvice.class.getName().replaceAll("\\.", "/");
			mv.visitTypeInsn(Opcodes.NEW, targetName);		
			mv.visitInsn(Opcodes.DUP);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL,targetName	,"<init>", "()V");
			//创建一个局部变量***********************************
			newLocal = newLocal(Type.getType(Advice.class));
			//创建一个局部变量***********************************
			System.out.println(newLocal);
			
			mv.visitVarInsn(Opcodes.ASTORE, newLocal);			
			mv.visitVarInsn(Opcodes.ALOAD, newLocal);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetName, "before", "()V");
		 }
		
		  @Override
		  protected void onMethodExit(int paramInt) {
			  	String targetName = MyAdvice.class.getName().replaceAll("\\.", "/");		
				mv.visitVarInsn(Opcodes.ALOAD, newLocal);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetName, "after", "()V");
		  }		
	 }

}
