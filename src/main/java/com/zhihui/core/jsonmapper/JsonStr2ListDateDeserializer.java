package com.zhihui.core.jsonmapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonStr2ListDateDeserializer extends JsonDeserializer<List<Date>> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@SuppressWarnings("unchecked")
	public List<Date> deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		List<Date> rs = null;
		List<String> tmps;
		try {
			List<String> justForClass = new ArrayList<String>();
			tmps = arg0.readValueAs(justForClass.getClass());
			if (tmps != null) {
				rs = new ArrayList<Date>();
				for (String e : tmps) {
					e = e.length() >= 10 ? e.substring(0, 10) : e;
					rs.add(df.parse(e));
				}
			}
		} catch (Throwable e) {
			throw new IOException(e);
		}
		return rs;
	}

}
