package com.zhihui.core.xmladapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlStr2TimestampAdapter extends XmlAdapter<String, Timestamp> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String marshal(Timestamp arg0) throws Exception {
		return df.format(new Date(arg0.getTime()));
	}

	@Override
	public Timestamp unmarshal(String arg0) throws Exception {
		// 2011-03-14T19:20:00+08:00
		if (arg0.indexOf("T") >= 0 && arg0.length() >= 19) {
			arg0 = arg0.substring(0, 19);
			arg0 = arg0.replace("T", " ");
		}
		return new Timestamp(df.parse(arg0).getTime());
	}
}
