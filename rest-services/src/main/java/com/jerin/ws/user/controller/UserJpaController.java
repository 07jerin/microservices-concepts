package com.jerin.ws.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jerin.ws.user.dao.PostRepository;
import com.jerin.ws.user.dao.UserRepository;
import com.jerin.ws.user.exceptions.UserNotFoundException;
import com.jerin.ws.user.model.Post;
import com.jerin.ws.user.model.User;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

	@Autowired
	UserRepository respository;

	@Autowired
	PostRepository postRepository;

	@GetMapping(path = "/users", produces = { "application/json", "application/xml" })
	public List<User> getAllUsers() {
		return respository.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public EntityModel<User> getUserById(@PathVariable Integer id) {
		Optional<User> user = respository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id : " + id);
		}

		Link getAllUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
				.getAllUsers())
				.withRel("all-users");

		EntityModel<User> entityModel = EntityModel.of(user.get(), getAllUsers);

		return entityModel;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User newUser = respository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newUser.getId())
				.toUri();

		return ResponseEntity.created(uri)
				.build();

	}

	@DeleteMapping(path = "/users/{id}")
	public void deleteById(@PathVariable Integer id) {
		respository.deleteById(id);
	}

	@GetMapping(path = "/users/{id}/posts")
	public List<Post> getPosts(@PathVariable Integer id) {
		Optional<User> user = respository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id : " + id);
		}

		return user.get()
				.getPosts();

	}

	@GetMapping(path = "/users/{id}/posts/{postId}")
	public Post getPosts(@PathVariable Integer id, @PathVariable Integer postId) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new UserNotFoundException("id : " + id);
		}

		return post.get();

	}

	@PostMapping(path = "/users/{id}/posts")
	public ResponseEntity<Post> savePost(@PathVariable Integer id, @Valid @RequestBody Post post) {
		Optional<User> user = respository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id : " + id);
		}
		post.setUser(user.get());
		post = postRepository.save(post);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(uri)
				.build();

	}

}
