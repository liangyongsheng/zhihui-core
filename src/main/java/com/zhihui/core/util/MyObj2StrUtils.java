package com.zhihui.core.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class MyObj2StrUtils {
	/**
	 * when (len <= 0) is original string
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String cutString(String str, int len) {
		if (str != null && len > 0 && str.length() > len)
			str = str.substring(0, len);
		return str;
	}

	/**
	 * when (len <= 0) is original string
	 * 
	 * @param obj
	 * @param len
	 * @return
	 */
	public static String toJson(Object obj, int len) {
		String rs = null;
		try {
			ObjectMapper om = new ObjectMapper();
			om.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			rs = om.writeValueAsString(obj);
			if (rs != null && len > 0 && rs.length() > len)
				rs = rs.substring(0, len);
		} catch (Throwable e) {
		}
		return rs;
	}

}
