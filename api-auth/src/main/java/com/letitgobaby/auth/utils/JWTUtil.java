package com.letitgobaby.auth.utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTUtil<T> {

  private final String issuer = "letitgobaby";
  private Algorithm algorithm;
  private int defaultTime;
  
  public JWTUtil(String secret, int miniute) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.defaultTime = miniute;
  }

  public String generate(T info, int minute) throws JWTCreationException {
    return JWT.create()
        .withIssuer(this.issuer)
        .withPayload(new ObjectMapper().convertValue(info, Map.class))
        .withExpiresAt(getExpireTime(minute))
        .sign(this.algorithm);
  }

  public boolean verify(String token) throws JWTDecodeException, JWTVerificationException {
    try {
      JWT.require(this.algorithm)
        .withIssuer(this.issuer)
        .build()
        .verify(token);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getPayload(String token) {
    return JWT.decode(token).getPayload();
  }

  private Date getExpireTime(int timeProperty) {
    int time = (timeProperty > 0) ? timeProperty : this.defaultTime;
    long expireDate = new Date().getTime() + (time * 60 * 1000);
    return new Date(expireDate);
  }

}
