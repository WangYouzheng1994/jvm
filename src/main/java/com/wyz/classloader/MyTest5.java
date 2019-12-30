package com.wyz.classloader;

/**
 * 当一个接口在初始化时，并不要求其父接口都完成了初始化
 * 只有在真正使用到父接口的时候（如 引用接口中所定义的常量，且）才初始化。
 * @Author: WangYouzheng
 * @Date: 2019/12/30 20 21
 * @Description:
 */
public class MyTest5 {
	public static void main(String[] args) {
		// 当两个都是接口的时候，是不需要MyParent5的。 // 当mychild5是class的时候且myparent5是interface的时候 那么至少能看到是需要加载的，并不能验证会初始化
		System.out.println(MyChild5.b);
		//
	}
}
interface MyParent5 {
	// public static final int a = 6;
	public static Thread THREAD = new Thread() {
		{
			System.out.println("abc");
		};
	};
}
class MyChild5 implements MyParent5 {
	public static int b = 6;
}