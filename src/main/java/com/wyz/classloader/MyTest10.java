package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/6 16 02
 * @Description: 主动使用。
 */
public class MyTest10 {
	static {
		System.out.println("MyTest10 static block");
	}
	public static void main(String[] args) {
		Parent2 parent2;
		System.out.println("==========");

		parent2 = new Parent2();

		System.out.println("===========");

		System.out.println(parent2.a);

		System.out.println("===========");
		System.out.println(Child2.b); // 对child2 的主动使用 会导致 parent2的 主动使用，但是parent2已经在16行初始化过了。
		/**
		 * 执行结果
		 * MyTest10 static block  main主动使用
		 * ==========
		 * Parent2 static block new的方式 主动使用
		 * ===========
		 * 3
		 * ===========
		 * Child2 static block 调用了他的静态变量 主动使用。 父类已经被初始化了。
		 * 4
		 */
	}
}

class Parent2 {
	static int a = 3;
	static {
		System.out.println("Parent2 static block");
	}
}

class Child2 extends Parent2 {
	static int b = 4;
	static {
		System.out.println("Child2 static block");
	}
}