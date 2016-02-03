package com.zhihui.core.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * special for apache-tomcat-7.0.67
 * 
 * @author sean
 *
 */
public class MyContext implements Filter, ServletContextAware, ApplicationContextAware {
	private static ServletRequest servletRequest;
	private static ServletResponse servletResponse;
	private static ServletContext servletContext;
	private static ApplicationContext applicationContext;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		MyContext.servletRequest = arg0;
		MyContext.servletResponse = arg1;
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		MyContext.servletContext = arg0;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		MyContext.applicationContext = arg0;
	}

	public static ApplicationContext getRootApplicationContext() {
		return MyContext.getApplicationContext();
	}

	public static ApplicationContext getApplicationContext() {
		ApplicationContext applicationContext = null;
		// way 1: use ApplicationContextAware, but you should configure it in beans like "<bean id="MyContext" class="*.MyContext" />"
		applicationContext = MyContext.applicationContext;
		if (applicationContext != null)
			return applicationContext;

		// way 2: use ServletContextAware, but you should configure it in beans like "<bean id="MyContext" class="*.MyContext" />"
		if (MyContext.servletContext != null)
			applicationContext = (ApplicationContext) MyContext.servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		if (applicationContext != null)
			return applicationContext;

		// way 3 : use org.springframework.web.context.request.RequestContextListener, but you should configure it in web.xml
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			applicationContext = (ApplicationContext) (attr.getRequest().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
		} catch (Throwable e) {
		}
		if (applicationContext != null)
			return applicationContext;

		return applicationContext;
	}

	public static ApplicationContext getClassPathXmlApplicationContext(String classPathFileName) {
		ApplicationContext applicationContext = null;

		// way 4 : use ClassPathXmlApplicationContext
		if (classPathFileName != null && !classPathFileName.equals(""))
			applicationContext = new ClassPathXmlApplicationContext("classpath:" + classPathFileName);
		if (applicationContext != null)
			return applicationContext;

		return applicationContext;
	}

	public static ServletContext getServletContext() {
		ServletContext servletContext = null;

		// way 1: use ServletContextAware, but you should configure it in beans like "<bean id="MyContext" class="*.MyContext" />"
		if (MyContext.servletContext != null)
			servletContext = MyContext.servletContext;
		if (servletContext != null)
			return servletContext;

		// way 2: use org.springframework.web.context.request.RequestContextListener, but you should configure it in web.xml
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			servletContext = attr.getRequest().getServletContext();
		} catch (Throwable e) {
		}
		if (servletContext != null)
			return servletContext;

		// way 3: use MockServletContext
		servletContext = new MockServletContext();
		return servletContext;
	}

	/**
	 * different client has different HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		HttpServletRequest httpServletRequest = null;

		// way 1:
		if (MyContext.servletRequest != null) {
			try {
				httpServletRequest = (HttpServletRequest) MyContext.servletRequest;
			} catch (Throwable e) {
			}
		}
		if (httpServletRequest != null)
			return httpServletRequest;

		// way 2: use org.springframework.web.context.request.RequestContextListener, but you should configure it in web.xml
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			httpServletRequest = attr.getRequest();
		} catch (Throwable e) {
		}
		if (httpServletRequest != null)
			return httpServletRequest;

		// way3: use MockHttpServletRequest
		httpServletRequest = new MockHttpServletRequest();
		return httpServletRequest;
	}

	public static HttpServletResponse getHttpServletResponse() {
		HttpServletResponse httpServletResponse = null;

		// way 1:
		if (MyContext.servletResponse != null) {
			try {
				httpServletResponse = (HttpServletResponse) MyContext.servletResponse;
			} catch (Throwable e) {
			}
		}
		if (httpServletResponse != null)
			return httpServletResponse;

		// way 2:
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			ServletWebRequest servletWebRequest = new ServletWebRequest(attr.getRequest());
			httpServletResponse = servletWebRequest.getResponse();
		} catch (Throwable e) {
		}
		if (httpServletResponse != null)
			return httpServletResponse;

		// way 3: MockHttpServletResponse
		httpServletResponse = new MockHttpServletResponse();
		return httpServletResponse;
	}

	/**
	 * different client has different HttpSession
	 * 
	 * @return HttpSession
	 */
	public static HttpSession getHttpSession() {
		HttpSession httpSession = null;

		// way 1: use org.springframework.web.context.request.RequestContextListener, but you should configure it in web.xml
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			httpSession = attr.getRequest().getSession();
		} catch (Throwable e) {
		}
		return httpSession;
	}

}
