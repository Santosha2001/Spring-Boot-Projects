package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
