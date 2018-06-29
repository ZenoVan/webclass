package com.zeno.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zeno.bean.User;

/**
 * 如果用户没登录，则返回登录页面
 * 
 * @author Light
 *
 */
public class VisitFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		User user = (User) req.getSession().getAttribute("user");
		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/") + 1, uri.length());

		if (user != null) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(contextPath + "/index.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
