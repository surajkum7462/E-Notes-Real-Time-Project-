package com.suraj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
