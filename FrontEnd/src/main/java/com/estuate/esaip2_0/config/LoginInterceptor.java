package com.estuate.esaip2_0.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <ul>
 * <li>Title: LoginController</li>
 * <li>Description: This LoginInterceptor will perform these tasks:- 1) Check
 * user exist in session or not. 2) Runs before every action to check .
 * 
 * If someone try to access direct URL of welcome page and if he is not present
 * in session, then it will redirect towards login page. If user already in
 * session then, call the action called by user.</li>
 * 
 * <li>Created by: Radhika Gopalraj</li>
 * </ul>
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		/*if (!uri.endsWith("/") && !uri.endsWith("/validateLogin")) {*/
		if (!uri.endsWith("/") && !uri.endsWith("/validateLogin")) {
			String userName = (String) request.getSession().getAttribute("userName");
			if (userName == null) {
				response.sendRedirect("/esaip2_0/");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
}
