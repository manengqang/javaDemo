package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 检查请求路径是否为不需要验证的路径
    // 使用 servletPath，避免 context-path 导致白名单匹配失败
    response.setContentType("application/json;charset=utf-8");
    response.setCharacterEncoding("UTF-8");
    String path = request.getServletPath();
    log.info("请求路径为：{}", path);
    if ("/login2".equals(path) || "/register2".equals(path) || "/login2/".equals(path) || "/register2/".equals(path)
        || "/error".equals(path) || path.startsWith("/error/") || "/captcha".equals(path)) {
      return true;
    }
    
    // 从请求头或参数中获取token
    String token;
    if ("/ws".equals(path)) {
      token = request.getParameter("token");
      return true;
    } else {
      token = request.getHeader("Authorization");
      if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7); // 去掉Bearer前缀
      }
    }

    // 1. 没有 token
    if (token == null || token.isBlank()) {
      log.info("请先登录");
      try {
        response.getWriter().write("{\"code\":401,\"msg\":\"请先登录\"}");// 验证失败，返回401错误
      } catch (Exception e) {
        log.error("响应失败", e);
      }
      return false;
    }

    Claims claims = jwtUtil.parseToken(token);
    if (claims == null) {
      log.info("token无效或已过期");
      try {
        response.getWriter().write("{\"code\":401,\"msg\":\"token无效或已过期\"}");
      } catch (Exception e) {
        log.error("响应失败", e);
      }
      return false;
    }
    Long userId = Long.valueOf(claims.get("userId").toString());
    String redisKey = "login:" + userId;
    // 去 Redis 查真正的 token
    String redisToken = redisTemplate.opsForValue().get(redisKey);
    log.info("token: {}", token);
    log.info("redisToken: {}", redisToken);

    // Redis 里没有 或 不一致 → 强制下线/过期
    if (redisToken == null || !redisToken.equals(token)) {
      log.info("验证失败，返回401错误");
      response.getWriter().write("{\"code\":401,\"msg\":\"您已被强制下线或在别处登录\"}");// 验证失败，返回401错误
      return false; // 验证失败，不继续处理请求
    }

    return true; // 验证通过，继续处理请求
  }
}
