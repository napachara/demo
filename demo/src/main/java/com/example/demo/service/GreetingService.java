package com.example.demo.service;

import reactor.core.publisher.Mono;

public interface GreetingService {

	public Mono<String> greet();
}
