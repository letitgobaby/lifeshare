package com.letitgobaby.auth.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

  @GetMapping("/login")
  public ResponseEntity<Object> login() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/test")
  public ResponseEntity<Object> test_get() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/test")
  public ResponseEntity<Object> test_post(@RequestBody String name) {
    return ResponseEntity.ok().build();
  }
  
}
