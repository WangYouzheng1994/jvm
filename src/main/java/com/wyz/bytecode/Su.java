package com.wyz.bytecode;

import java.security.Key;

/**
 * @Author: WangYouzheng
 * @Date: 2020/3/2 17 56
 * @Description:
 */
public class Su {
	public static void main(String[] args) {
		// System.out.println("123");
		TestEnum a = TestEnum.valueOf("A");
		System.out.println(a.val);
	}
	enum TestEnum {
		A("A", "B");
		private TestEnum(String key, String val) {
			this.key = key;
			this.val = val;
		}
		private String key;
		private String val;
	}
}
