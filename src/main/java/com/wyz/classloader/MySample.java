package com.wyz.classloader;

/**
 * @Author: WangYouzheng
 * @Date: 2020/1/13 17 32
 * @Description:
 */
public class MySample {
	public MySample() {
		System.out.println("MySample is loaded by :" + this.getClass().getClassLoader());
		new MyCat(); // 主动使用 MyCat 进行类的加载
	}
}
