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
	}
}
