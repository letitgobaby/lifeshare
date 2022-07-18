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

public class JWTUtil {

  private String issuer = "letitgobaby";
  private Algorithm algorithm;
  private int defaultTime;
  
  public JWTUtil(String secret, int miniute) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.defaultTime = miniute;
  }

  public String generate(String info, int minute) throws JWTCreationException {
    return JWT.create()
        .withIssuer(this.issuer)
        .withClaim("userInfo", new ObjectMapper().convertValue(info, Map.class))
        .withExpiresAt(getExpireTime(minute))
        .sign(this.algorithm);
  }

  public String verify(String token) {
    try {
      JWTVerifier verifier = JWT.require(this.algorithm)
          .withIssuer(this.issuer)
          .build();

      verifier.verify(token);
      return "PASS";
    } catch (JWTDecodeException e) {
      return null;
    } catch (JWTVerificationException ex) {
      return "REFRESH";
    } catch (Exception e) {
      return null;
    }
  }

  public Claim getClaim(String token, String claimKey) {
    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getClaims().get(claimKey);
  }

  private Date getExpireTime(int timeProperty) {
    int time = (timeProperty > 0) ? timeProperty : this.defaultTime;
    long expireDate = new Date().getTime() + (time * 60 * 1000);
    return new Date(expireDate);
  }

}
