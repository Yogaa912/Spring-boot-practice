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
public class AccountFilter implements Filter{ // 这里修改了引入的包

	// 排除的 URI
	private final String[] IGNORE_URI = {"/account/login", "/account/register", "/css/", "/js/", "/images/", "/account/validateAccount", "/account/validateRegister", "/account/handleRegister", "/index", "/account/logOut", "/api/account/updatePassword"}; //
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		boolean pass = canPassIgnore(uri);
		// 1. 从 session 里面找到 Account: 找到就放行, 找不到则进入2
		// 2. 当前访问的 URI 是不是在白名单里: 如果在放行, 如果不在的话, 跳转登录页面
		if(pass) {
			chain.doFilter(request, response);
			return; // 起到一个分流的作用, 能进入这个代码块的情况直接退出执行, 不要再执行后面的内容
		}
		Object account = req.getSession().getAttribute("account");
		if(account == null) {
			// 没登录就跳转至登录页面
			res.sendRedirect("/account/login");//
			return;
		}
		// 登陆则直接通过
		chain.doFilter(request, response);
		System.out.println("----filter----" + uri);
	}

	private boolean canPassIgnore(String uri) {
		// 不可能能写成完成匹配的, 只需要判断	起始部分
		// 下级资源目录也能访问
		for(String val :IGNORE_URI) {
			if(uri.startsWith(val)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 加载 Filter 启动之前的资源
		System.out.println("Account Filter initiating ...");
		Filter.super.init(filterConfig);
	}


}
