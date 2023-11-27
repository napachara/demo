package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GreetingService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class GreetingController {

	@Autowired
	private GreetingService greetingService;

	@GetMapping("/")
	public Mono<String> greet(Mono<Principal> principal) {
		log.info("greet : {}", principal);
		return principal.map(Principal::getName).map(name -> String.format("Hello, %s", name));
	}

	@GetMapping("/admin")
	public Mono<String> greetAdmin(Mono<Principal> principal) {
		log.info("greetAdmin : {}", principal);
		return principal.map(Principal::getName).map(name -> String.format("Admin access: %s", name));
	}

	@GetMapping("/greetingService")
	public Mono<String> greetingService() {
		return greetingService.greet();
	}
}
