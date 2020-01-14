package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/13 17 34
 * @Description:
 */
public class MyTest17 {
	public static void main(String[] args) throws Exception {
		Test1();
		Test2();
	}

	/**
	 * 类加载器先加载了MySample然后通过反射的方式对MySample进行了实例化，那么就对MyCat进行了主动使用
	 *
	 * @throws Exception
	 */
	public static void Test1() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MySample");
		// 1163157884
		System.out.println(aClass.hashCode());
		/*
			MySample is loaded by :sun.misc.Launcher$AppClassLoader@18b4aac2
			MyCat is loaded by: sun.misc.Launcher$AppClassLoader@18b4aac2
			这两个类的对象是由同一个APPClassLoader加载的
		 */
		Object o = aClass.newInstance();// 通过反射创建这个class对象的实例：创建实例就会调用构造方法。
	}

	public static void Test2() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MySample");
		// 1163157884
		System.out.println(aClass.hashCode());
		/*
			MySample is loaded by :sun.misc.Launcher$AppClassLoader@18b4aac2
			MyCat is loaded by: sun.misc.Launcher$AppClassLoader@18b4aac2
			这两个类的对象是由同一个APPClassLoader加载的
		 */
		// Object o = aClass.newInstance();// 通过反射创建这个class对象的实例：创建实例就会调用构造方法。
	}
}