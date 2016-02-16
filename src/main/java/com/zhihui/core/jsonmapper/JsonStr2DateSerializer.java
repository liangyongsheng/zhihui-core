package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonStr2DateSerializer extends JsonSerializer<Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		arg1.writeString(sdf.format(arg0));
	}
}
