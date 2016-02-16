package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonStr2TimestampSerializer extends JsonSerializer<Timestamp> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(Timestamp arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		arg1.writeString(sdf.format(new Date(arg0.getTime())));
	}
}
