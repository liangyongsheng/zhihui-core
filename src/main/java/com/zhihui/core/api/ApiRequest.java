package com.zhihui.core.api;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zhihui.core.exception.AsignException;
import com.zhihui.core.exception.CheckEmptyException;
import com.zhihui.core.exception.CheckException;
import com.zhihui.core.exception.CheckIllicitValueException;
import com.zhihui.core.exception.CheckSignException;
import com.zhihui.core.jsonmapper.JsonStr2DatetimeDeserializer;
import com.zhihui.core.jsonmapper.JsonStr2DatetimeSerializer;
import com.zhihui.core.util.MySignUtils;
import com.zhihui.core.util.MyStringUtils;
import com.zhihui.core.xmladapter.XmlStr2DatetimeAdapter;

@XmlRootElement(name = "justChangeTheRootElementName")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public abstract class ApiRequest<T extends ApiResponse> {
	private String method;
	@XmlJavaTypeAdapter(value = XmlStr2DatetimeAdapter.class)
	@JsonSerialize(using = JsonStr2DatetimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = JsonStr2DatetimeDeserializer.class)
	private Date timestamp;
	private Integer oprtId;
	@XmlTransient
	@JsonIgnore
	public String sign;
	@XmlTransient
	@JsonIgnore
	public String messageBody;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getOprtId() {
		return oprtId;
	}

	public void setOprtId(Integer oprtId) {
		this.oprtId = oprtId;
	}

	// ---functions start here...
	public abstract Class<T> getResponseType();

	public void asignSysParams() throws AsignException {
	}

	public abstract void asignApiParams() throws AsignException;

	public void checkSign() throws CheckException {
		if (!MyStringUtils.isEmpty(this.sign)) {
			String signValid = "";
			try {
				signValid = MySignUtils.signRequest(this.messageBody);
				signValid = signValid == null ? "" : signValid;
			} catch (Exception e) {
				throw new CheckSignException(e);
			}
			if (!signValid.equalsIgnoreCase(this.sign))
				throw new CheckSignException("sign is incorrect");
		}
	}

	public void checkSysParams() throws CheckException {
		if (MyStringUtils.isEmpty(this.method))
			throw new CheckEmptyException("field: metho, value is empty");
		if (this.timestamp == null)
			throw new CheckEmptyException("field: timestamp, value is empty");
		if (this.oprtId == null || this.oprtId <= 0)
			throw new CheckIllicitValueException("field: oprtId is illicit");
	}

	public abstract void checkApiParams() throws CheckException;
}
