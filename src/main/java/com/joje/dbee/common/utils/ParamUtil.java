package com.joje.dbee.common.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.joje.dbee.exception.BadRequestException;

public class ParamUtil {

	public static String toStr(Object param) throws BadRequestException {
		try {
			if (param != null) {
				String result = (String) param;
				if (result.length() > 0) {
					return result;
				}
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
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
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static int toInt(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (int) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static int toInt(Object param, int defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return (int) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static long toLong(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (long) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static long toLong(Object param, long defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return (long) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static double toDouble(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (double) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static double toDouble(Object param, double defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return (double) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return new Gson().fromJson(param.toString(), Map.class);
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static List<?> toList(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return new Gson().fromJson(param.toString(), List.class);
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}
}
