package com.wyz.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.chrono.IsoChronology;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/8 14 25
 * @Description: 自定义类加载器
 */
public class MyTest16 extends ClassLoader {
	private String classLoaderName;
	private final String fileExtention = ".class";
	private String path;// 加载的路径。

	public MyTest16(String classLoaderName) {
		super(); // 查看源码得知，该方法是将系统类加载器作为我们自定义加载器的双亲(父)加载器
		this.classLoaderName = classLoaderName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MyTest16(ClassLoader parent, String classLoaderName) {
		super(parent);// 显示指定该类加载器的父加载器。
		this.classLoaderName = classLoaderName;
	}

	@Override
	public String toString() {
		return "[" + this.classLoaderName + "]";
	}

	/**
	 * Finds the class with the specified <a href="#name">binary name</a>.
	 * This method should be overridden by class loader implementations that
	 * follow the delegation model for loading classes, and will be invoked by
	 * the {@link #loadClass <tt>loadClass</tt>} method after checking the
	 * parent class loader for the requested class.  The default implementation
	 * throws a <tt>ClassNotFoundException</tt>.
	 *
	 * @param className The <a href="#name">binary name</a> of the class
	 * @return The resulting <tt>Class</tt> object
	 * @throws ClassNotFoundException If the class could not be found
	 * @since 1.2
	 */
	/**
	 * loadclass 方法会调用 findclass方法，查看ClassLoader源码可知，classloader的findclass只是抛出了异常，也就是说
	 * 所有继承classloader的实现类都需要重写该方法。该方法主要是用来寻找class并且返回CLass对象。在这里我们选择了调用继承的defineClass方法，传入读取到的字节数组。
	 */
	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		System.out.println("findClass invoked :" + className);
		System.out.println("class loader name : " + this.classLoaderName);
		byte[] data = this.loadClassData(className);
		return this.defineClass(className, data, 0, data.length);
	}

	private byte[] loadClassData(String className) {
		InputStream in = null;
		byte[] data = null;
		ByteArrayOutputStream byteArrayOutputStream = null;

		className = className.replace(".", "/"); // 包名转换成路径名，把.改成/
		try {
			// in = new FileInputStream(new File(className + this.fileExtention));
			in = new FileInputStream(new File(this.path + className + this.fileExtention));
			byteArrayOutputStream = new ByteArrayOutputStream();

			int ch = 0;
			while (-1 != (ch = in.read())) {
				byteArrayOutputStream.write(ch);
			}
			data = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				byteArrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static void main(String[] args) throws Exception {
		/*MyTest16 loader1 = new MyTest16("loader1");
		loader1.setPath("E:\\workspace-github\\jvm\\out\\production\\classes");

		Class<?> clazz = loader1.loadClass("com.wyz.classloader.MyTest1"); // 这个地方加载的是class文件。 从classpath读取的 所以现在这个读取 不是Mytest16里面的classloader 而是appclassloader加载的。
		System.out.println("class: " + clazz.hashCode());
		Object o = clazz.newInstance();
		System.out.println(o);*/

		// MyLoader3Test();
		// MyLoader4Test();
		MyLoader6Test();
	}

	/**
	 * 这个地方加载的是class文件。 但是从日志输出来看，是从classpath读取的 所以现在这个读取 不是Mytest16里面的classloader 而是appclassloader加载的。
	 *
	 * @throws Exception
	 */
	public static void Myloader1Test(ClassLoader mytest16) throws Exception {
		Class<?> clazz = mytest16.loadClass("com.wyz.classloader.MyTest1");
		System.out.println("class: " + clazz);
		Object o = clazz.newInstance();
		System.out.println(o);
		// 当MyTest16 准备加载这个MyTest1的时候，会先请求父加载器去加载，然后我们默认的父加载器是 系统类加载器（AppClassLoader）
		// 系统类加载器继续向上请求到 Extension ClassLoader 然后继续向上请求 BootStrap Classloader， 随后他们两个无法加载到该类，然后逐一返回至AppClassLoader，
		// APPClassLoader可以根据"com.wyz.classloader.MyTest1"加载到当前工程中的 MyTest1，因为MyTest1处于classPath下。
		// 因此mytest16 classLoader是没有进行加载的。 MyTest1 是由 APPClassLoader加载的。
	}

	/**
	 * 该例子也是为了说明类加载器的双亲委托机制
	 *
	 * @throws Exception
	 */
	public static void Myloader2Test(ClassLoader classLoader) throws Exception {
		// 执行该程序  本意是想我指定了MyTest16的加载位置，但是依然体现了双亲委托机制： 即AppClassLoader先从classpath去加载了。
		MyTest16 myTest16 = new MyTest16("testClassLoader");
		myTest16.setPath("E:\\workspace-github\\jvm\\out\\production\\classes"); // 指定类加载的位置。
		Class<?> aClass = myTest16.loadClass("com.wyz.classloader.MyTest1");//
		System.out.println("class: " + aClass);
	}

	/**
	 * 那么如何才能使用我们自己的类加载器？？？  我们把当前工程中的MyTest1.class文件及其目录剪切移动到E盘根目录再次加载
	 *
	 * @throws Exception
	 */
	public static void MyLoader3Test() throws Exception {
		MyTest16 myTest16 = new MyTest16("testClassLoader");
		myTest16.setPath("E:\\"); // 指定类加载的位置。
		Class<?> aClass = myTest16.loadClass("com.wyz.classloader.MyTest1");//
		System.out.println("class: " + aClass);
		// 可以看到如下执行结果
		/**
		 *   findClass invoked :com.wyz.classloader.MyTest1
		 class loader name : testClassLoader
		 class: class com.wyz.classloader.MyTest1
		 */
		// MyTest16 让appClassLoader 去加载 然后 App找 Extension  Extension 找Bootstrap  这样一级一级往上找 然后尝试加载。
		// 爹们发现都加载不了 然后一级一级的传回MyTest16 这个自定义类加载器。然后MyTest16 调用loadClass（） 其中loadClass 调用了我们重写的findClass 通过指定的path读取到了类。
	}

	/**
	 * 为了更好的体现classloader的构造方法 指定父级类加载器，实现委托加载的效果，以及类只加载一次的情况。
	 *
	 * @throws Exception
	 */
	public static void MyLoader4Test() throws Exception {
		MyTest16 myTest16 = new MyTest16("myLoader1");
		myTest16.setPath("E:\\");
		Class<?> aClass2 = myTest16.loadClass("com.wyz.classloader.MyTest1");
		System.out.println(aClass2.hashCode());
		MyTest16 myTest161 = new MyTest16(myTest16, "myLoader2");
		// myTest161.setPath("E:\\");
		Class<?> aClass1 = myTest161.loadClass("com.wyz.classloader.MyTest1");
		System.out.println(aClass1.hashCode());
		MyTest16 myTest162 = new MyTest16(myTest161, "myLoader3");
		// myTest162.setPath("E:\\");
		Class<?> aClass = myTest162.loadClass("com.wyz.classloader.MyTest1");
		System.out.println(aClass.hashCode());
		/**
		 * 执行结果说明
		 * findClass invoked :com.wyz.classloader.MyTest1
		 class loader name : myLoader1
		 class com.wyz.classloader.MyTest1
		 class com.wyz.classloader.MyTest1
		 class com.wyz.classloader.MyTest1
		 */
		/**
		 * 三个类加载器都进行了loadClass的操作。但是 只有myLoader1 是去加载的  myLoader2和myLoader3 都是依托于 双亲委派原则一级一级的走到了myLoader1那里
		 * 因为myLoader1已经加载过了，进行了直接返回给了myLoader2和myLoader3：这一结论可以通过打印hashCode一直来确定返回的Class对象是同一个对象。
		 */
	}

	/**
	 * 关于命名空间的说法，通过hashcode来观察加载后的Class对象。
	 *
	 * @throws Exception
	 */
	public static void MyLoader5Test() throws Exception {
		// 两个不同classloader的实例加载同一个类。
		MyTest16 myTest1 = new MyTest16("myLoader1");
		MyTest16 myTest2 = new MyTest16("myLoader2");

	}

	/**
	 * 关于类对象的卸载
	 * 新增jvm参数 : -XX:+TraceClassUnloading
	 * 利用java垃圾回收机制。
	 * @throws Exception
	 */
	public static void MyLoader6Test() throws Exception {
		MyTest16 myTest16 = new MyTest16("myLoader1");
		myTest16.setPath("E:\\");
		Class<?> aClass2 = myTest16.loadClass("com.wyz.classloader.MyTest1");
		System.out.println(aClass2.hashCode());
		aClass2 = null;
		myTest16 = null;
		System.gc(); // [Unloading class com.wyz.classloader.MyTest1 0x00000007c0061028]
		Thread.sleep(20000);
		myTest16 = new MyTest16("myloader2");
		myTest16.setPath("E:\\");
		aClass2 = myTest16.loadClass("com.wyz.classloader.MyTest1");
		System.out.println(aClass2.hashCode());
	}
}
