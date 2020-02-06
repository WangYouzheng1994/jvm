package com.wyz.classloader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author WangYouzheng
 * @version 1.0
 * @Date: 2020/2/6 21:38
 * @Description:
 */
public class MyTest27 {
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("jdbc.drivers")); // 这里面是没有的 但是查看源码可以知道这也是加载的一种方式

        /**
         * Class.forname() 默认情况下 是触发了 类的初始化。 然后进而会执行静态代码块。 然后静态代码块里面会发现主动调用了DriverManager的静态方法（主动使用 触发DriverManager初始化）
         * DriverManager的静态代码块中loadInitialDrivers() 他先通过 使用了SPI标准的SErviceLoader的方式加载了 服务提供商的驱动（类，包）。
         * 实际上再往下跟代码以后通过源码注释也可以得知，在老版本的时候的确是需要CLass.forname的方式加载驱动，但是 有了SPI的方式后，实际上就不需要了。因为jdk的ServiceLoader已经给加载好了。
         */
        Class.forName("com.mysql.jdbc.Driver");
        // 这行代码里面断点可以看到，驱动列表的那个CopyOnWriteArrayList<DriverInfo> registeredDrivers 是加载了多个mysql实现的，是因为我们导入的mysql驱动包符合了SPI标准。
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytestdb", "username", "password");
    }
}
