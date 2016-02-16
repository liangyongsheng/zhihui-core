package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonStr2DateDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		Date rs = null;
		try {
			String tmp = arg0.getText();
			tmp = tmp.length() >= 10 ? tmp.substring(0, 10) : tmp;
			rs = sdf.parse(tmp);
		} catch (Throwable e) {
			throw new IOException(e);
		}
		return rs;
	}

}
