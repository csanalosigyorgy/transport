package com.webuni.transport.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageableConfig implements WebMvcConfigurer {

	@Autowired
	private TransportProperty pageableProperty;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable(PageRequest.of(
				pageableProperty.getAPageable().getPage(),
				pageableProperty.getAPageable().getSize(),
				Sort.by(
						pageableProperty.getAPageable().getSortDirection(),
						pageableProperty.getAPageable().getSortBy())));
		resolvers.add(resolver);
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
