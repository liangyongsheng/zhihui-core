package com.zhihui.core.api;

import java.io.StringReader;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.zhihui.core.exception.AsignException;
import com.zhihui.core.exception.BusinessException;
import com.zhihui.core.exception.CheckException;
import com.zhihui.core.exception.InitException;

@Service
public abstract class ApiBo<T extends ApiRequest<?>> {
	protected T apiRequest;
	protected ApiResponse apiResponse;

	public ApiResponse getApiResponse() {
		return this.apiResponse;
	}

	public T getApiRequest() {
		return this.apiRequest;
	}

	public abstract Class<T> getRequestType();

	// 1.
	public void doInit(T apiRequest) throws InitException {
		try {
			this.apiRequest = apiRequest;
			if (this.apiRequest == null)
				throw new Exception("no such request-object.");
			this.apiResponse = this.apiRequest.getResponseType().newInstance();
			if (this.apiResponse == null)
				throw new Exception("no such response-object.");
		} catch (Throwable e) {
			throw new InitException(e);
		}
	}

	// 1.
	@SuppressWarnings("unchecked")
	public void doInit(String sign, String contentType, String messageBody) throws InitException {
		try {
			if (contentType.equalsIgnoreCase(MediaType.APPLICATION_XML)) {
				JAXBContext jc = JAXBContext.newInstance(this.getRequestType());
				this.apiRequest = (T) jc.createUnmarshaller().unmarshal(new StringReader(messageBody));
				if (this.apiRequest == null)
					throw new Exception("no such request-object.");
				this.apiResponse = this.apiRequest.getResponseType().newInstance();
				if (this.apiResponse == null)
					throw new Exception("no such response-object.");

				this.apiRequest.setSign(sign);
				this.apiRequest.setMessageBody(messageBody);

			} else if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON)) {
				ObjectMapper om = new ObjectMapper();
				om.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
				this.apiRequest = (T) om.readValue(messageBody, this.getRequestType());
				if (this.apiRequest == null)
					throw new Exception("no such request-object.");
				this.apiResponse = this.apiRequest.getResponseType().newInstance();
				if (this.apiResponse == null)
					throw new Exception("no such response-object.");

				this.apiRequest.setSign(sign);
				this.apiRequest.setMessageBody(messageBody);
			} else
				throw new Exception("do not support this content-type.");

		} catch (Exception e) {
			throw new InitException(e);
		}
	}

	// 2.
	public void doAsign() throws AsignException {
		this.apiRequest.asignSysParams();
		this.apiRequest.asignApiParams();
	}

	// 3.
	public void doCheck() throws CheckException {
		this.apiRequest.checkSign();
		this.apiRequest.checkSysParams();
		this.apiRequest.checkApiParams();
	}

	// 4.
	public abstract void doBusiness() throws BusinessException;

}
