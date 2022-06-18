package com.letitgobaby.auth.apis;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

  @PostMapping("/login")
  public ResponseEntity<Object> login() {
    System.out.println("\n" + SecurityContextHolder.getContext().getAuthentication().getPrincipal() + "\n");
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
