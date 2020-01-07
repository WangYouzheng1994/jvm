package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/7 13 42
 * @Description: 类加载器的层次关系
 */
public class MyTest13 {
	public static void main(String[] args) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader(); // 返回的是系统类加载器，系统类加载器 是用来加载启动应用的。

		System.out.println(classLoader);
		while (null != classLoader) {
			classLoader = classLoader.getParent();
			System.out.println(classLoader);
		}
		/**
		 * sun.misc.Launcher$AppClassLoader@18b4aac2  app类加载器  当前的程序入口是在MyTest13中，由app类加载器加载MyTest13
		 	sun.misc.Launcher$ExtClassLoader@1b6d3586 他爹是 扩展类加载器
			 null									  他爹是根类（启动类加载器）加载器，所以返回null
		 */
	}
}