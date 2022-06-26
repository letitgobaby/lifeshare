package com.letitgobaby.auth.enums;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode {
  
  USER_NOT_FOUND("유저가 없습니다.", HttpStatus.BAD_REQUEST),
  PASSWORD_NOT_MATCH("비밀번호가 맞지 않습니다.", HttpStatus.BAD_REQUEST),
  FORBIDDEN("로그인해주세요.", HttpStatus.FORBIDDEN),
  UNAUTHORIZED("권한이 없습니다.", HttpStatus.UNAUTHORIZED),
  EXPIRED_TOKEN("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
  ;

  private final String message;
  private final HttpStatus status;

  AuthErrorCode(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return this.message;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

}
