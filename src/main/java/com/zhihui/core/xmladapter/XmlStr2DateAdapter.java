package com.zhihui.core.xmladapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlStr2DateAdapter extends XmlAdapter<String, Date> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String marshal(Date arg0) throws Exception {
		return df.format(arg0);
	}

	@Override
	public Date unmarshal(String arg0) throws Exception {
		// 2099-12-31T00:00:00+08:00
		arg0 = arg0.length() >= 10 ? arg0.substring(0, 10) : arg0;
		return df.parse(arg0);
	}

}
