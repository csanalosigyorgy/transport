package com.webuni.transport.configuration;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "transport")
@Data
public class TransportProperty {

	private APageable aPageable = new APageable();
	private Delay delay = new Delay();

	@Data
	public static class APageable {
		private int page;
		private int size;
		private Sort.Direction sortDirection = ASC;
		private String sortBy;
	}

	@Data
	public static class Delay {
		private TreeMap<Integer, Double> limits;
	}
}
