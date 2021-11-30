package com.jerin.ws.user.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jerin.ws.user.dao.UserDaoService;
import com.jerin.ws.user.exceptions.UserNotFoundException;
import com.jerin.ws.user.model.User;

@RestController
public class UserController {

	@Autowired
	UserDaoService service;

	@GetMapping(path = "/users", produces = { "application/json", "application/xml" })
	public List<User> getAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public EntityModel<User> getUserById(@PathVariable Integer id) {
		User user = service.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id : " + id);
		}

		Link getAllUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
				.getAllUsers())
				.withRel("all-users");

		EntityModel<User> entityModel = EntityModel.of(user, getAllUsers);

		return entityModel;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User newUser = service.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newUser.getId())
				.toUri();

		return ResponseEntity.created(uri)
				.build();

	}

	@DeleteMapping(path = "/users/{id}")
	public void deleteById(@PathVariable Integer id) {
		boolean isDeleted = service.delete(id);

		if (!isDeleted) {
			throw new UserNotFoundException("User not found with id " + id);
		}
	}

}
