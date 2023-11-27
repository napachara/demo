package com.example.demo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class GreetingServiceImpl implements GreetingService {

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<String> greet() {
		return Mono.just("Hello from service!");
	}

}
