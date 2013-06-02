package com.blogspot.nurkiewicz.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/2/13, 7:22 PM
 */
@Configuration
public class RestSpringConfig extends WebMvcConfigurationSupport {

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(
				new ServletWebArgumentResolverAdapter(
						new PageableArgumentResolver()));
	}
}

