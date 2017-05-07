package cn.edu.zzia.career.utils;

import java.util.UUID;

public class IdUtils {

	/**
	 * 32位字符串 用 - 分割 24c4ddf4-8918-11e5-8877-adf959b0c13e
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 32位字符串 24c4ddf4891811e58877adf959b0c13e
	 * 
	 * @return
	 */
	public static String id() {
		return uuid().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(id());
	}
	
}
