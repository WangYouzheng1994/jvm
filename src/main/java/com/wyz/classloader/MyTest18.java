package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/15 19 45
 * @Description:
 */
public class MyTest18 {
	public static void main(String[] args) {
		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));
		System.out.println(System.getProperty("java.class.path"));
	}
}
