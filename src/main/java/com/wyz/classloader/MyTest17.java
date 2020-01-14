package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/13 17 34
 * @Description: 复杂类的加载。
 */
public class MyTest17 {
	public static void main(String[] args) throws Exception {
		Test1();
		Test2();
	}

	/**
	 * 类加载器先加载了MySample然后通过反射的方式对MySample进行了实例化，那么就对MyCat进行了主动使用，双亲委托机制下，由系统类加载器进行加载
	 *
	 * @throws Exception
	 */
	public static void Test1() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");

		// 先一级一级的向上委托  然后逐级返回到系统类加载器，系统类加载器发现是可以加载的。那么就加载了MySample
		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MySample");
		// 1163157884
		System.out.println(aClass.hashCode());
		/*
			MySample is loaded by :sun.misc.Launcher$AppClassLoader@18b4aac2
			MyCat is loaded by: sun.misc.Launcher$AppClassLoader@18b4aac2
			这两个类的对象是由同一个APPClassLoader加载的
		 */
		// 通过反射创建这个class对象的实例：创建实例就会调用构造方法。 MySample的构造方法 有对MyCat的 实例化动作进行了主动使用 然后开始加载MyCat
		Object o = aClass.newInstance();
	}

	/**
	 * 不对MySample进行实例化那么不会对MyCat进行实例化。
	 * 但是实际情况中MyCat有可能会被加载。因为“JVM规范是允许某个类进行预先加载的。类加载器必须在程序主动使用的时候才会报错。”
	 * 我们通过-XX:+TraceClassLoading 以后可以看到 MyCat是没有被加载的。
	 * @throws Exception
	 */
	public static void Test2() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MySample");
		// 1163157884
		System.out.println(aClass.hashCode());
		// Object o = aClass.newInstance();。
	}
}