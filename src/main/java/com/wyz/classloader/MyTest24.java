package com.wyz.classloader;

/**
 * @author WangYouzheng
 * @version 1.0
 * @Date: 2020/2/4 22:17
 * @Description: 关于线程上下文类加载器
 */

/**
 * Current ClassLoader 当前类加载器，加载当前类的加载器。
 * 每个类都会尝试使用自己的类加载器来加载他所依赖的其他的类。
 * 比如：Classx 引用了Classy 那么Classx的类加载器会去尝试加载Classy （Classy没被加载）
 */

/**
 * 线程上下文类加载器 （ContextClassLoader）
 * 是从JDK1.2 版本开始引入的。类Thread中的getContextClassLoader()和setContextClassLoader（ClassLoader cl）
 * 分别用来获取和设置上下文类加载器。
 * 如果没有通过和setContextClassLoader（ClassLoader cl）进行设置的话，线程将继承其父线程的上下文类加载器。
 * Java应用运行时的初始线程的上下文类加载器是系统类加载器。在线程中运行的代码可以通过该类加载器类加载类与资源。
 *
 * 线程上下文类加载器的重要性：
 * 以JDBC驱动为例。Java制订了连接的规范接口他们存在在rt.jar中的java.sql包下面。
 * 然后rt.jar是由根类加载器加载的。必然存在java核心库调用实现子类的过程。根类加载器是无法看到 classpath（由系统加载器或者自定义加载器加载的）下的类的。
 * 传统的双亲委托模型就存在这一问题。 比如某个规范用了模板方法。然后实现的子类只是进行重写某个方法。真正的调用是在java核心库中。 根类加载器加载的rt.jar 各个厂商的是 系统类加载器加载的。
 * 互相看不到就会报错 NoClassxx的那个错误。
 * 但是子类加载器是能看到父类加载器的加载的信息（类）的。
 *
 * 总结：
 * 在双亲委托模型下，类加载是由下而上的，即下层的类加载器会先委托上层进行加载，但是对于SPI（Service Provider Interface）
 * 有些接口是Java核心库所提供的，而Java核心库是由启动类加载器来家再来的，而这些接口的实现都来自于不同的Jar包（各个厂商根据接口进行的实现）
 * 但是Java的启动类加载器不会加载其他来源的jar（他是有指定的加载路径的）。这样传统的双亲委托模型就无法满足SPI的要求。
 * 而通过给当前线程设置上下文类加载器，就可以由设置的上下文类加载器来实现对于接口实现类的加载。
 */
public class MyTest24 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader()); // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(Thread.class.getClassLoader()); // null
    }
}
