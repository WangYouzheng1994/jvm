package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/13 17 34
 * @Description:
 */
public class MyTest17 {
	public static void main(String[] args) throws Exception {
		MyTest16 loader1 = new MyTest16("loader1");

		Class<?> aClass = loader1.loadClass("com.wyz.classloader.MySample");
		System.out.println(aClass.hashCode());
		Object o = aClass.newInstance();// 通过反射创建这个class对象的实例
	}
}
