package com.wyz.classloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 当一个接口在初始化时，并不要求其父接口都完成了初始化
 * 只有在真正使用到父接口的时候（如 引用接口中所定义的常量，且）才初始化。
 * @Author: WangYouzheng
 * @Date: 2019/12/30 20 21
 * @Description:
 */
public class MyTest5 {
	public static void main(String[] args) {
		// 当两个都是接口的时候，是不需要MyParent5的。 // 当mychild5是class的时候且myparent5是interface的时候 我们通过移除.class文件的时候， 那么至少能看到是需要加载的，并不能验证会初始化
		// 因为我们如果使用静态代码块的话 是可以验证初始化的 因为初始化阶段会给静态变量赋值，以及初始化静态代码块。
		// 结论：初始化一个类的时候 会初始化他的父类。初始化一个类的时候，并不会先初始化他所实现的接口。
		// System.out.println(MyChild5.b);
		// 初始化一个接口的时候，并不会先初始化他的父接口，删掉了编译后的class文件无法运行只是说jvm进行了 加载，然后我们通过定义静态的成员 对象的匿名对象 可以看到初始化的过程
		// 最终只是打印了MyParent5_1中的数据 因此 没有初始化的父接口们。
		System.out.println(MyParent5_1.THREAD);
	}
}

interface MyGrandpa {
	public static Thread THREAD = new Thread() {
		{
			System.out.println("MyGrandpa invoked");
		}
	};
}

// 主动使用 MyC5的继承子类的时候（调用静态的 非final的变量进行主动使用） MyParent5不会初始化：初始化一个类的时候，并不会先初始化他所实现的接口。
interface MyParent5 extends MyGrandpa {
	// public static final int a = 6;
	// 因为是interface 所以无法通过添加块的方式来验证类是否进行了初始化，所以给他增加了一个成员变量，在实例化成员变量的时候增加了匿名的方式
	public static Thread THREAD = new Thread() {
		{
			System.out.println("MyParent5 invoked");
		};
	};
}
// 主动使用 MyC5的继承子类的时候（调用静态的 非final的变量进行主动使用） MyC5会初始化
class MyC5 {
	public static Thread THREAD = new Thread() {
		{
			System.out.println("MyC5 invoked");
		}
	};
}
class MyChild5 extends MyC5 implements MyParent5 {
	static int b = 321;
}

interface MyGrandpa5_1 {
	public static Thread THREAD = new Thread() {
		{
			System.out.println("MyGrandpa5_1 invoked");
		};
	};
}

interface MyParent5_1 extends MyGrandpa5_1 {
	public static Thread THREAD = new Thread() {
		{
			System.out.println("MyParent5_thread1 invoked");
		};
	};

	public static Thread THREAD_2 = new Thread() {
		{
			System.out.println("MyParent5_thread2 invoked");
		};
	};
}
