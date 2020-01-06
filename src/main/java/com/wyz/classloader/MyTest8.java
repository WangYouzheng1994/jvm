package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/6 11 28
 * @Description:
 */
public class MyTest8 {
	public static void main(String[] args) {
		// 因为是final的 所以不会打印静态代码块。
		System.out.println(FinalTest.x);
	}
}

class FinalTest {
	public static final int x = 3;
	static {
		System.out.println("FinalTest static ");
	}
}