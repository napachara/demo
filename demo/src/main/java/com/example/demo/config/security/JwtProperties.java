package com.example.demo.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";
	// validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
