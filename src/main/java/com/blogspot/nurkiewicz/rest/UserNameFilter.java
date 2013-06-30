package com.blogspot.nurkiewicz.rest;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/29/13, 8:52 PM
 */
public class UserNameFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String userName = authentication.getName();
		final String realName = findSwitchedUser(authentication);
		final String fullName = userName + (realName != null ? " (" + realName + ")" : "");

		MDC.put("user", fullName);
		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove("user");
		}
	}

	private String findSwitchedUser(Authentication authentication) {
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth instanceof SwitchUserGrantedAuthority) {
				return ((SwitchUserGrantedAuthority)auth).getSource().getName();
			}
		}
		return null;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
