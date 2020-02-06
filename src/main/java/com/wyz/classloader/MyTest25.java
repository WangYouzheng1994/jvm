package com.wyz.classloader;

/**
 * @author WangYouzheng
 * @version 1.0
 * @Date: 2020/2/5 0:34
 * @Description:
 */
public class MyTest25 implements Runnable {
    private Thread thread;
    public MyTest25() {
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        ClassLoader classLoader = this.thread.getContextClassLoader(); // 是应用类加载器。 可以通过Launcher中的加载得知。
        this.thread.setContextClassLoader(classLoader);
        System.out.println("Class: " + classLoader.getClass());
        System.out.println("Parent: " + classLoader.getParent().getClass());
    }

    public static void main(String[] args) {
        new MyTest25();
    }
}
