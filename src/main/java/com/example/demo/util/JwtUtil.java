package com.example.demo.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private static final String SECRET_KEY = "mySecretKey123456789012345678901234"; // 替换为实际的密钥
  private static final long EXPIRATION_TIME = 86400000L; // 24小时的过期时间（毫秒）

  // 获取密钥
  private Key getKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  // 生成JWT
  public String createToken(Map<String, Object> claims) {
    return Jwts.builder()
    .setClaims(claims) // 设置自定义声明
    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
    .signWith(getKey(), SignatureAlgorithm.HS256) // 设置签名算法和密钥
    .compact(); // 生成JWT字符串
  }

  // 解析JWT并返回Claims对象
  public Claims parseToken(String token) {
    try {
      return Jwts.parserBuilder()
      .setSigningKey(getKey()) // 设置密钥
      .build()
      .parseClaimsJws(token) // 解析JWT
      .getBody(); // 获取JWT的主体部分
    } catch (Exception e) {
      return null;
    }
  }
}
