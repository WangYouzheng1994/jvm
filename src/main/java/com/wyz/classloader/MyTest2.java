package com.wyz.jvm;

public class MyTest2 {
    public static void main(String[] args) {
        System.out.println(MyParent2.str);
        System.gc();
    }
}
class MyParent2 {
    public static final String str = "hello world";

    static {
        System.out.println("MyParent2 static block");
    }
}