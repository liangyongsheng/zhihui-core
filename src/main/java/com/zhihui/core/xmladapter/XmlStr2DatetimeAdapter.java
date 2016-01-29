package com.zhihui.core.xmladapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlStr2DatetimeAdapter extends XmlAdapter<String, Date> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String marshal(Date arg0) throws Exception {
		return df.format(arg0);
	}

	@Override
	public Date unmarshal(String arg0) throws Exception {
		// 2011-03-14T19:20:00+08:00
		if (arg0.indexOf("T") >= 0 && arg0.length() >= 19) {
			arg0 = arg0.substring(0, 19);
			arg0 = arg0.replace("T", " ");
		}

		return df.parse(arg0);
	}

}
