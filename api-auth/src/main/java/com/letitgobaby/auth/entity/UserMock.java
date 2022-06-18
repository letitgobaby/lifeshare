package com.letitgobaby.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
public class UserMock {

  private String accountId = "test";
  private String password = "passwd"; 
  private String role = "ADMIN";
  
}
