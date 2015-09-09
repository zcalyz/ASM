package com.asm.aop;

public class T {
	public void say(){
		MyAdvice advice;
		
		advice = new MyAdvice();
		advice.before();
	}
}
