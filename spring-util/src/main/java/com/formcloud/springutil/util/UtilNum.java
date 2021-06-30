package com.formcloud.springutil.util;

public class UtilNum {
    
    public static Integer parseToInt(String string) {
		try {
			if (string == null || string.trim().isEmpty())
				return 0;

			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static Integer toIntOrNull(Object object) {
		try {
			if (object == null)
				return null;
			return Integer.parseInt(String.valueOf(object));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer parseToIntIgnoreChars(String string) {
		try {
			if (string == null || string.trim().isEmpty())
				return 0;
			return Integer.parseInt(string.replaceAll("\\D+", ""));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static Long parseToLong(String string) {
		try {
			if (string == null || string.trim().isEmpty())
				return 0l;

			return Long.parseLong(string);
		} catch (NumberFormatException e) {
			return 0l;
		}
	}

	public static Float parseToFloat(String string) {
		try {
			if (string == null || string.trim().isEmpty())
				return 0f;

			return Float.parseFloat(string);
		} catch (NumberFormatException e) {
			return 0f;
		}
    }
    
}
