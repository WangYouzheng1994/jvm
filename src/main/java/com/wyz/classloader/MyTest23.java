package com.wyz.classloader;

/**
 * @author WangYouzheng
 * @version 1.0
 * @Date: 2020/1/31 20:42
 * @Description: 当我们用java 命令直接运行的时候 可以看到 应用类加载器所加载的目录 只是一个.， 如果再idea中运行的话可以看到有大量的目录这都是idea在运行的时候帮我们指定的
 * 在运行期，一个Java类是由该类的完全限定名（binary name，二进制名）和用于加载该类的定义类加载器（defining loader）所共同决定的。
 * 如果同样名字（即相同的完全限定名）的类是由两个不同的加载器所加载的，那么这些类就是不同的，即便.class文件的字节码完全一样，并且从相同的位置加载亦如此。-
 */

import sun.misc.Launcher;

/**
 * 如果我们对环境变量进行更改后运行MyTest23 --》 java -Dsun.boot.class.path=./ com.wyz.classloader.MyTest23
 * 在Oracle hotspot实现中会报错 ：Error occurred during initialization of VM java/lang/NoClassDefFoundError: java/lang/Object
 * 因为每个类初始化都是Object的子类。
 */
public class MyTest23 {
    public static void main(String[] args) {
        System.out.println("根加载器" + System.getProperty("sun.boot.class.path"));
        System.out.println("扩展类加载器" + System.getProperty("java.ext.dirs"));
        System.out.println("应用类加载器" + System.getProperty("java.class.path"));

        /**
         * 本质上来说 自带的ClassLoader也是一个Java类，既然是Java类那么是谁加载的？？
         * 内建于JVM的启动类加载器会加载java.lang.ClassLoader以及其他的Java平台类。
         * 当JVM启动时，一块特殊的机器码会运行，他会加载扩展类加载器和系统类加载器
         * 这块特殊的机器码叫做启动类加载器（BootStrap）；
         * 启动类加载器不是Java类，他是由C++编写的。他不属于Java 的范畴。其他类都是Java编写的。
         * 启动类加载器是特定于平台的机器指令，他负责开启整个的加载过程。
         * 所有的类加载器（除了启动类加载器），都被实现为Java类。不过，总归要有一个组件来加载第一个Java类加载器，从而让整个加载过程能够顺利进行，那么加载第一个纯Java类加载器就是启动类加载器的职责。
         * 启动类加载器还会加载供JRE正常运行的基本环境，这包括一些基本组件 如：java.util.* java.lang。*中的类。
         */

        System.out.println(ClassLoader.class.getClassLoader()); // 运行结果为null 根据之前的学习，以及源码注释得知，ClassLoader加载这个类的类加载器为启动类加载器

        /**
         * 无论是系统类加载器还是扩展类加载器 是位于sun.misc包下面的Launcher的类中。 并且他们是非public的静态内部类，那么我们如何知道 非启动类加载器的类加载器是通过 启动类加载器来加载的呢？
         * 其中有一个机制是，如果 A中有B  然后A的加载是通过AClassLoader加载的，那么B的加载也会先使用AClassLoader加载。
         * 因此如果Launcher这个是由启动类加载器加载的那么 他的内部类 ExtClassLoader和APPClassLoader也就是通过启动类加载器加载的。根本原因是因为他俩是内部类。
         */
        System.out.println(Launcher.class.getClassLoader()); // Launcher的类加载器 是启动类加载器加载的。因为返回了Null。 因此也间接的佐证了 扩展类加载器和系统类加载器 都是通过启动类加载器加载的。

        System.out.println("==============");
        /**
         * 通过反编译的源码 ExtClassLoader可知， 为什么获取扩展类加载器的路径参数是 "java.ext.dirs"
         * 获取AppClassLoader中加载的路径的参数是"java.class.path"
         */

        /**
         * 返回的是系统类加载器 默认情况下  系统类加载器就是APPClassLoader， 但是jdk也提供了一些机制可以让我们来指定系统类加载器 "java.system.class.loader"
         */
        System.out.println(ClassLoader.getSystemClassLoader());
        /**
         * 使用命令行的方式运行 java -Djava.system.class.loader=xxxxx
         * 可以看到java.system.class.loader已经变成了我们指定的类加载器
         *  但是上述类的加载还是Appclassloader 原因是 我们自定义的类加载器 的双亲 就是appClassloader，然后APPClassLoader 已经可以加载上述类了。
         */
        System.out.println(System.getProperty("java.system.class.loader")); // xxxxx



    }
}