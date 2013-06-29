package com.blogspot.nurkiewicz.switchuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.stereotype.Service;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/29/13, 9:51 PM
 */
@Service
public class SwitchUserListener implements ApplicationListener<AuthenticationSwitchUserEvent> {

	private static final Logger log = LoggerFactory.getLogger(SwitchUserListener.class);

	@Override
	public void onApplicationEvent(AuthenticationSwitchUserEvent event) {
		log.info("User switch from {} to {}",
				event.getAuthentication().getName(),
				event.getTargetUser().getUsername());
	}
}
