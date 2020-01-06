package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/2 15 13
 * @Description: 类加载器的双亲委托：先往爹上找，看看能不能加载，一层一层的找，
 */
public class MyTest7 {
	public static void main(String[] args) throws Exception {
		Class clazz = Class.forName("java.lang.String");
		System.out.println(clazz.getClassLoader());

		Class clazz2 = Class.forName("com.wyz.classloader.C");
		System.out.println(clazz2);
	}
}
class C {

}