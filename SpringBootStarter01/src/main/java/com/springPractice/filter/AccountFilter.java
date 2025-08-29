package com.springPractice.filter;

import java.io.IOException;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@WebFilter(urlPatterns = "/*")
public class AccountFilter implements Filter{
	// 设置白名单
	private final String[] IGNORE_URI = {"/account/login", "/account/logout", "/account/validateAccount", "/index", "/css/", "/js/", "/images"};
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;
		String URI = req.getRequestURI();
		boolean pass = canPass(URI);
		// 如果是白名单内部的URL就直接放行
		if(pass) {
			chain.doFilter(request, response);
			return;// 后面的不执行了
		}
		Object account = req.getSession().getAttribute("account");
		if(account == null) {
			res.sendRedirect("/account/login");
			return;
		}
		// 上述皆否
		chain.doFilter(request, response);
		System.out.println("----filter----" + URI);
	}

	private boolean canPass(String uri) {
		for(String val : IGNORE_URI) {
			if(uri.startsWith(val)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

}
