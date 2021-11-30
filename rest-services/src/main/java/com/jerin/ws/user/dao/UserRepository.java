package com.jerin.ws.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jerin.ws.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
