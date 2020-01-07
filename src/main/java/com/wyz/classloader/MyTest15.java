package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/7 19 03
 * @Description: 数组的类加载器
 */
public class MyTest15 {
	public static void main(String[] args) {
		String[] strings = new String[2];
		System.out.println(strings);
		System.out.println(strings.getClass());
		// 数组的类加载器与数组的类型有关系。当前数组类型为String， String的类加载器是启动类加载器  启动类加载器返回的是null
		System.out.println(strings.getClass().getClassLoader()); // null

		MyTest15[] myTest15s = new MyTest15[2];
		System.out.println(myTest15s);
		System.out.println(myTest15s.getClass().getClassLoader());

		int[] ints = new int[2];
		System.out.println(ints);
		System.out.println(ints.getClass().getClassLoader()); // 原生(基本)类型 没有类加载器
	}
}
