package com.example.demo.r2dbc.mysql.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.r2dbc.mysql.domain.Tutorial;

import reactor.core.publisher.Flux;

@Repository
public interface TutorialRepository extends R2dbcRepository<Tutorial, Integer> {

	Flux<Tutorial> findByTitleContaining(String title);

	Flux<Tutorial> findByPublished(boolean isPublished);

}
