package com.letitgobaby.auth.service;

import org.springframework.stereotype.Service;

import com.letitgobaby.auth.entity.UserMock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  
  public UserMock loginByUserId(String userId) {
    return new UserMock();
  }

}
