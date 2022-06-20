package com.joje.dbee.common.utils;

public class StringUtil {
	public static boolean isBlank(String str) {
		return str == null || str.length() <= 0;
	}
	
	public static boolean isInclude(String target, String[] array) {
		for (String i : array) {
			if (target.contains(i))
				return false;
		}
		return true;
	}
}
