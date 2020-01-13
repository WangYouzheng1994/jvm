package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/13 17 29
 * @Description:
 */
public class MyCat {
	public MyCat() {
		System.out.println("MyCat is loaded by: " + this.getClass().getClassLoader()); // 看看MyCat这个类是哪个classLoader加载的。
	}
}
