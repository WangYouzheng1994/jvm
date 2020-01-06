package com.wyz.classloader;

public class MyTest11 {
    public static void main(String[] args) {
        System.out.println(Child3.a);
        System.out.println("====");
        Child3.doSomeThing();
        /**
         * 执行结果
         * Parent3 Static Block 主动使用了Parent3 为什么？ 因为a变量是MyParent3那里声明的---->谁声明的就是主动使用谁。
         * 3 调用Child3.a
         * ====
         * do something 调用doSomeThing，不会打印static了，主动使用只有一次。
         */
    }
}

class Parent3 {
    static int a = 3;

    static {
        System.out.println("Parent3 Static Block");
    }

    static void doSomeThing() {
        System.out.println("do something");
    }
}

class Child3 extends Parent3 {
    static {
        System.out.println("Child3 Static Block");
    }
}