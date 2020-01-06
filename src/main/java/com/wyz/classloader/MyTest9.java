package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/6 16 01
 * @Description:  类的初始化，通过静态代码块的方式进行测试
 */
public class MyTest9 {
    static {
        System.out.println("MyTest9 Static Block");
    }
    public static void main(String[] args) {
        System.out.println(Child.b);
        /**
         * 执行结果
         * MyTest9 Static Block 访问main方法程序入口初始化了MyTest9
         * Parent Static Block 访问了子类的非常量静态变量 所以先初始化了他的父类。
         * Child Static Block
         * 4
         */
        // 通过-XX:+TraceClassLoading 查看类加载顺序 发现也是这样的。
    }
}

class Child extends Parent {
    static int b = 4;
    static {
        System.out.println("Child Static Block");
    }
}

class Parent {
    static int a = 3;
    static {
        System.out.println("Parent Static Block");
    }
}