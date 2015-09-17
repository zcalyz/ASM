package com.asm.aop;

public class MyAdvice extends Advice{
	@Override
	public void before(){
	   System.out.println("My before method !");
	}
	
	@Override
	public void after(){
		System.out.println("My after method!");
	}
}
