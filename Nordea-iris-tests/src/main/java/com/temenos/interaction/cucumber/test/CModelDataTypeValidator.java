package com.temenos.interaction.cucumber.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.math.NumberUtils;

public class CModelDataTypeValidator {

	public static String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static boolean isValidTimestamp(String timestampValue) {
		try {
			new SimpleDateFormat(TIMESTAMP_FORMAT).parse(timestampValue);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean isValidNumber(String numberValue) {
		return NumberUtils.isNumber(numberValue)
				&& !numberValue.substring(numberValue.length() - 1).matches(
						"[dDfF]");
	}
}
