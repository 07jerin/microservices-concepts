package com.jerin.ws.user.dao;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jerin.ws.user.model.User;

@Service
public class UserDaoService {

	private static List<User> users;
	private static int count = 3;

	static {
		users = new ArrayList<>();
		users.add(new User(1, "Jerin", LocalDate.of(1989, Month.FEBRUARY, 1)));
		users.add(new User(2, "Jeffin", LocalDate.of(1993, Month.DECEMBER, 9)));
		users.add(new User(3, "Jaisy", LocalDate.of(1985, Month.DECEMBER, 28)));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++count);
			users.add(user);
		} else {
			users.remove(user);
			users.add(user);
		}
		return user;
	}

	public User findOne(int id) {
		return users.stream()
				.filter(u -> u.getId() == id)
				.findFirst()
				.orElse(null);
	}

	public boolean delete(Integer id) {
		return users.removeIf(u -> u.getId() == id);
	}

}
