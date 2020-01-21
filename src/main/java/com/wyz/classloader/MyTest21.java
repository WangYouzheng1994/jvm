package com.wyz.classloader;

import java.lang.reflect.Method;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/21 14 21
 * @Description:
 */
public class MyTest21 {
	public static void main(String[] args) throws Exception {

	}

	/**
	 * 如果当前的ClassPath下面 有MyPerson.class文件。
	 *
	 * @throws Exception
	 */
	public static void Test1() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");
		MyTest16 loader2 = new MyTest16("loader2");

		loader1.setPath("");
		loader2.setPath("");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MyPerson");
		Class<?> aClass2 = loader2.loadClass("com.wyz.classloader.MyPerson");

		Object o = aClass.newInstance();
		Object o2 = aClass2.newInstance();
		Method method = aClass.getMethod("setMyPerson", Object.class);

		method.invoke(o, o2);
		/**
		 * loader1 和 loader2都会向上委托父加载器去尝试加载 直到委托到根加载器bootstrap 类加载器，然后依次返回到 系统类加载器，
		 * 系统类加载器 加载到了classpath目录下面的MyPerson.class。
		 */
	}

	/**
	 * 如果当前的classpath目录下面 没有Class文件，那么会通过自定义类加载器类加载MyPerson
	 *
	 * @throws Exception
	 */
	public static void Test2() throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");
		MyTest16 loader2 = new MyTest16("loader2");

		loader1.setPath("");
		loader2.setPath("");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MyPerson");
		Class<?> aClass2 = loader2.loadClass("com.wyz.classloader.MyPerson");

		Object o = aClass.newInstance();
		Object o2 = aClass2.newInstance();
		Method method = aClass.getMethod("setMyPerson", Object.class);

		method.invoke(o, o2);
	}
}