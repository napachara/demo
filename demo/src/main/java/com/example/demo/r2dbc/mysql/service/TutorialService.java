package com.example.demo.r2dbc.mysql.service;

import com.example.demo.r2dbc.mysql.domain.Tutorial;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TutorialService {

	public Flux<Tutorial> findAll();

	public Flux<Tutorial> findByTitleContaining(String title);

	public Mono<Tutorial> findById(int id);

	public Mono<Tutorial> save(Tutorial tutorial);

	public Mono<Tutorial> update(int id, Tutorial tutorial);

	public Mono<Void> deleteById(int id);

	public Mono<Void> deleteAll();

	public Flux<Tutorial> findByPublished(boolean isPublished);
}
