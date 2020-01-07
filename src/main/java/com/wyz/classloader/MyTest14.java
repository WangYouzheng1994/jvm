package com.wyz.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/7 17 22
 * @Description:
 */
public class MyTest14 {
	public static void main(String[] args) throws IOException {
		// 获取当前正在执行的线程。 然后获取上下文类加载器。
		// 当前线程的创建者的Classloader，
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		String resourceName = "com/wyz/classloader/MyTest13.class";
		Enumeration<URL> urls = classLoader.getResources(resourceName); // 使用resourceName 去加载 编译后的class文件。

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			System.out.println(url);
		}

		/**
		 * 获取当前类的ClassLoader
		 * clazz.getClassLoader();
		 *
		 * 获取当前县城管上下文的classloader
		 * Thread.currentThread().getContextClassLoader();
		 *
		 * 获得系统的ClassLoader
		 * ClassLoader.getSystemClassLoader();
		 *
		 * 获得调用者的ClassLoader
		 * DriverManager.getCallerClassLoader();
		 */

		System.out.println("-------------");
		// 自定义的类是谁加载的
		Class<MyTest14> myTest14Class = MyTest14.class;
		System.out.println(myTest14Class.getClassLoader()); // sun.misc.Launcher$AppClassLoader@18b4aac2  系统类加载器（应用类加载器加载）
		// 尝试性的看看String类型是谁加载的。
		Class<String> stringClass = String.class;
		System.out.println(stringClass.getClassLoader()); // null （bootstrap 类加载器 去加载 rt.jar中的类。） 所以返回了null
	}
}
