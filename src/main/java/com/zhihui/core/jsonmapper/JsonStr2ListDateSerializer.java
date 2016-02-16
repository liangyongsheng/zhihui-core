package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonStr2ListDateSerializer extends JsonSerializer<List<Date>> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(List<Date> arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		try {
			if (arg0 == null)
				arg1.writeNull();
			else {
				arg1.writeStartArray();
				for (Date e : arg0)
					arg1.writeObject(sdf.format(e));
				arg1.writeEndArray();
			}
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}
}
