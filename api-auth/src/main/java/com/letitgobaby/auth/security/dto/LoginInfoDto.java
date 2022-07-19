package com.letitgobaby.auth.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LoginInfoDto {
  String userId;
  String userPw;
}
