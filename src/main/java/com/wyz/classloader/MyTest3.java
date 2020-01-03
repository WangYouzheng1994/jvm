package com.wyz.jvm;

import java.util.UUID;

/**
 * 当一个常量的值非编译器可以确定的，那么其值就不会被放到调用类的常量池中。
 * 这时在程序运行时，会导致主动使用这个常量所在的类。那么定义这个常量的类也就会被初始化了
 */
public class MyTest3 {
    public static void main(String[] args) {
        System.out.println(MyParent3.str);
    }
}

class MyParent3 {
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("MyParent3 static code");
    }
}
