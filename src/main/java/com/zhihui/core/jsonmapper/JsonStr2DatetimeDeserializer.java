package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonStr2DatetimeDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		Date rs = null;
		try {
			// 2011-03-14T19:20:00+08:00
			String tmp = arg0.getText();
			if (tmp.indexOf("T") >= 0 && tmp.length() >= 19) {
				tmp = tmp.substring(0, 19);
				tmp = tmp.replace("T", " ");
			}
			rs = sdf.parse(tmp);
		} catch (Throwable e) {
			throw new IOException(e);
		}
		return rs;
	}

}
