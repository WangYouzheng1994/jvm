package com.wyz.classloader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author WangYouzheng
 * @version 1.0
 * @Date: 2020/2/5 17:12
 * @Description:
 * 线程上下文类加载器的一般使用模式
 * 以下是伪代码
 * 1. 获取
 * ClassLoader cl = Thread.currentThread().getContextClassLoader();
 * 2. 使用
 * try {
 *     Thread.currentThread().setContextClassLoader(targetCll);
 *     xxxMethod();
 * } finally {
 *     3. 还原
 *     Thread.currentThread().setContextClassLoader(cl);
 * }
 * xxxMethod() 方法里面就是调用了Thread.currentThread().getContextClassLoader()获取当前线程上下文类加载器做一些事情
 * 如果一个类由类加载器A加载，那么这个类的依赖类也是由相同的类加载器加载的。（该依赖类没有被加载过的话）
 *
 * ContextClassLoader的作用是为了破坏Java的类加载委托机制。
 * 当高层（比如jdbc）提供了统一的接口让低层（比如Mysql Oracle的驱动）去实现,同时又要在高层加载（或者实例化）低层的类时，
 * 就必须要通过线程上下文类加载器来帮助高层的ClassLoader找到并加载这个类。原因是因为高层的类的类加载器和低层是不一样的。比如jdbc是根类加载器，mysql是系统类加载器。
 */
public class MyTest26 {
    public static void main(String[] args) {
        /**
         * ServiceLoader 是在jdk1.6加入的。他是一个SPI的一个解决方案，他的大意就是 一套标准，然后从
         * 服务提供商需要在自己的包里面的 "META-INF/services" 这个路径下写一个 自己服务对应的 高层包，然后里面对应了具体的高层类的实现的完整限定名。也就是自己的驱动的全路径类名
         * 然后ServiceLoader相当于是jdk去加载各个服务商所提供的包（驱动）用的。
         * 也就是说为什么要这么整？因为 双亲委托中有一个很重要的机制是，如果当前类是A加载器加载的，那么他所依赖的类也要通过A加载器这一根分支去加载！一层一层的往上找，而不是往下找或者说找兄弟。他只会往上面去找。
         */

        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = loader.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println("driver: " + driver.getClass() + ", loader: " + driver.getClass().getClassLoader());
        }
        System.out.println("当前线程上下文类加载器" + Thread.currentThread().getContextClassLoader());
        System.out.println("serviceLoader的类加载器" + ServiceLoader.class.getClassLoader());
    }
}
