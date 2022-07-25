package com.joje.dbee.common.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.joje.dbee.exception.BadRequestException;

public class ParamUtils {

//	#############################################################################################
//									          String
//	#############################################################################################
	public static String toStr(Object param) throws BadRequestException {
		try {
			if (param != null) {
				String result = (String) param;
				if (result.length() > 0) {
					return result;
				}
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toStr - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toStr - Invalid Param : data is null");
	}

	public static String toStr(Object param, String defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				String result = (String) param;
				if (result.length() > 0) {
					return result;
				}
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toStr - Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static String toStr(Map<String, Object> map, String key) throws BadRequestException {
		return toStr(map.get(key));
	}

	public static String toStr(Map<String, Object> map, String key, String defaultValue) throws BadRequestException {
		return toStr(map.get(key), defaultValue);
	}
	
	
//	#############################################################################################
//    										 Integer
//	#############################################################################################
	public static int toInt(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return Integer.parseInt((String) param);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toInt - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toInt - Invalid Param : data is null");
	}

	public static int toInt(Object param, int defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return Integer.parseInt((String) param);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toInt - Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static int toInt(Map<String, Object> map, String key) throws BadRequestException {
		return toInt(map.get(key));
	}
	
	public static int toInt(Map<String, Object> map, String key, int defaultValue) throws BadRequestException {
		return toInt(map.get(key), defaultValue);
	}
	
	
//	#############################################################################################
//	  										   Long
//	#############################################################################################
	public static long toLong(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return Long.parseLong((String) param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException("ParamUtil.toLong - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toLong - Invalid Param : data is null");
	}

	public static long toLong(Object param, long defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return Long.parseLong((String) param);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toLong - Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static long toLong(Map<String, Object> map, String key) throws BadRequestException {
		return toLong(map.get(key));
	}
	
	public static long toLong(Map<String, Object> map, String key, long defaultValue) throws BadRequestException {
		return toLong(map.get(key), defaultValue);
	}
	
	
//	#############################################################################################
//	   										   Double
//	#############################################################################################
	
	public static double toDouble(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return Double.parseDouble((String) param);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toDouble - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toDouble - Invalid Param : data is null");
	}

	public static double toDouble(Object param, double defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return Double.parseDouble((String) param);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toDouble - Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static double toDouble(Map<String, Object> map, String key) throws BadRequestException {
		return toDouble(map.get(key));
	}
	
	public static double toDouble(Map<String, Object> map, String key, double defaultValue) throws BadRequestException {
		return toDouble(map.get(key), defaultValue);
	}
	
	
//	#############################################################################################
//	   											Map
//	#############################################################################################
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return new Gson().fromJson(param.toString(), Map.class);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toMap - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toMap - Invalid Param : data is null");
	}

	public static Map<String, Object> toMap(Map<String, Object> map, String key) throws BadRequestException {
		return toMap(map.get(key));
	}
	
	
//	#############################################################################################
//												List
//	#############################################################################################
	@SuppressWarnings("unchecked")
	public static List<Object> toList(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return new Gson().fromJson(param.toString(), List.class);
			}
		} catch (Exception e) {
			throw new BadRequestException("ParamUtil.toList - Invalid Param : parsing failed");
		}
		throw new BadRequestException("ParamUtil.toList - Invalid Param : data is null");
	}
	
	public static List<?> toList(Map<String, Object> map, String key) throws BadRequestException {
		return toList(map.get(key));
	}
}
