package com.blogspot.nurkiewicz.switchuser;

import com.google.common.base.Throwables;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/29/13, 7:53 PM
 */
public class SwitchUserOnceFilter extends SwitchUserFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		final String switchUserHeader = request.getHeader("X-Switch-User-Once");
		if (switchUserHeader != null) {
			trySwitchingUserForThisRequest(chain, request, res, switchUserHeader);
		} else {
			super.doFilter(req, res, chain);
		}
	}

	private void trySwitchingUserForThisRequest(FilterChain chain, HttpServletRequest request, ServletResponse response, String switchUserHeader) throws IOException, ServletException {
		try {
			proceedWithSwitchedUser(chain, request, response, switchUserHeader);
		} catch (AuthenticationException e) {
			throw Throwables.propagate(e);
		}
	}

	private void proceedWithSwitchedUser(FilterChain chain, HttpServletRequest request, ServletResponse response, String switchUserHeader) throws IOException, ServletException {
		final Authentication targetUser = attemptSwitchUser(new SwitchUserRequest(request, switchUserHeader));
		SecurityContextHolder.getContext().setAuthentication(targetUser);

		try {
			chain.doFilter(request, response);
		} finally {
			final Authentication originalUser = attemptExitUser(request);
			SecurityContextHolder.getContext().setAuthentication(originalUser);
		}

	}

	private static class SwitchUserRequest extends HttpServletRequestWrapper {

		private final String switchUserHeader;

		public SwitchUserRequest(HttpServletRequest request, String switchUserHeader) {
			super(request);
			this.switchUserHeader = switchUserHeader;
		}

		@Override
		public String getParameter(String name) {
			switch (name) {
				case SPRING_SECURITY_SWITCH_USERNAME_KEY:
					return switchUserHeader;
				default:
					return super.getParameter(name);
			}
		}
	}
}
