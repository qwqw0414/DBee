package com.joje.dbee.common.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.joje.dbee.exception.BadRequestException;

public class ParamUtil {

	public static String getString(Object param) throws BadRequestException {
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

	public static String getString(Object param, String defaultValue) throws BadRequestException {
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

	public static int getInt(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (int) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static int getInt(Object param, int defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return (int) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static long getLong(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (long) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static long getLong(Object param, long defaultValue) throws BadRequestException {
		try {
			if (param != null) {
				return (long) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		return defaultValue;
	}

	public static double getDouble(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return (double) param;
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static double getDouble(Object param, double defaultValue) throws BadRequestException {
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
	public static Map<String, Object> getMap(Object param) throws BadRequestException {
		try {
			if (param != null) {
				return new Gson().fromJson(param.toString(), Map.class);
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Param : parsing failed");
		}
		throw new BadRequestException("Invalid Param : data is null");
	}

	public static List<?> getList(Object param) throws BadRequestException {
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
