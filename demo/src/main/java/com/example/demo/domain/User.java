package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private String id;

	private String username;

	@JsonIgnore
	private String password;

	@Email
	private String email;

	@Builder.Default()
	private boolean active = true;

	@Builder.Default()
	private List<String> roles = new ArrayList<>();

}