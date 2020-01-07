package com.wyz.classloader;

/**
 * 调用ClassLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化
 */
public class MyTest12 {
	public static void main(String[] args) throws Exception {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		Class<?> clazz = loader.loadClass("com.wyz.classloader.CL"); // 加载类。

		System.out.println(clazz);
		System.out.println("======");
		clazz = Class.forName("com.wyz.classloader.CL");
		System.out.println(clazz);
		/**
		 * 执行结果
			 class com.wyz.classloader.CL
			 ======
			 Class CL       Class.forName();
			 class com.wyz.classloader.CL
		 */
	}
}

class CL {
	static {
		System.out.println("Class CL");
	}
}