package com.joje.dbee.common.utils;

import java.util.List;

public class StringUtil {
	public static boolean isBlank(String str) {
		return str == null || str.length() <= 0;
	}
	
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
	
	public static boolean isInclude(String target, String[] array) {
		for (String i : array)
			if (target.contains(i))
				return false;
		return true;
	}
	
	public static String toStr(List<String> list, String dot) {
		String result = "";
		for (int i = 0; i < list.size(); i++)
			result += (i + 1) == list.size() ? list.get(i) : list.get(i) + dot;
		return result;
	}
}
