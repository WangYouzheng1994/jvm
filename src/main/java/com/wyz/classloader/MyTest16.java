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
		MyTest16 loader1 = new MyTest16("loader1");
		loader1.setPath("E:\\workspace-github\\jvm\\out\\production\\classes");

		Class<?> clazz = loader1.loadClass("com.wyz.classloader.MyTest1"); // 这个地方加载的是class文件。 从classpath读取的 所以现在这个读取 不是Mytest16里面的classloader 而是appclassloader加载的。
		System.out.println("class: " + clazz.hashCode());
		Object o = clazz.newInstance();
		System.out.println(o);

		// test(loader1);
	}

	public static void test(ClassLoader classLoader) throws  Exception {
		Class<?> clazz = classLoader.loadClass("com.wyz.classloader.MyTest1"); // 这个地方加载的是class文件。 从classpath读取的 所以现在这个读取 不是Mytest16里面的classloader 而是appclassloader加载的。
		System.out.println("class: " + clazz);
		Object o = clazz.newInstance();
		System.out.println(o);
	}
}
