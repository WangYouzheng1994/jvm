package com.wyz.jvm;

/**
 * 数组的类型是运行期决定的。并且不会触发引用类型的主动使用，也就不会触发初始化
 */
public class MyTest4 {
    public static void main(String[] args) {
        MyParent4[] myParent4s = new MyParent4[1]; // anewarray 创建一个引用类型的助记符
        System.out.println(myParent4s);

        MyParent4[][] myParent4s1 = new MyParent4[1][1];
        System.out.println(myParent4s1);

        System.out.println(myParent4s.getClass().getSuperclass());
        System.out.println(myParent4s1.getClass().getSuperclass());

        System.out.println("=======");

        int[] ints = new int[1];// newarray 创建一个原始（基本）类型（不包括string）的助记符
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());

        String[] ss = new String[1]; // String是引用类型所以 是 anewarray
    }
}
class MyParent4 {
    static {
        System.out.println("hellow jvm");
    }
}