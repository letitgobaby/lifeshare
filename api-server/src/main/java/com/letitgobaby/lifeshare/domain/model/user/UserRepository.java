package com.letitgobaby.lifeshare.domain.model.user;

import com.letitgobaby.lifeshare.system.persistence.BaseQueryDSLSupport;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, BaseQueryDSLSupport<User> { }
