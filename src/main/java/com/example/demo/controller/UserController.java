package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private DefaultKaptcha producer;

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, String uuid) throws Exception {
        log.info("User captcha uuid is {}", uuid);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        String redisKey = "captcha:" + uuid;
        redisTemplate.opsForValue().set(redisKey, code, 300, TimeUnit.SECONDS);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @PostMapping("/formSubmit")
    public Result<String> formSubmit(@RequestParam Map<String, String> formData) {
        log.info("Received form data: {}", formData);
        return Result.success("表单提交成功");
    }

    @PostMapping("/login2")
    public Result<String> login2(@RequestBody LoginDTO body) {
        log.info("User login username is {}, password is {}", body.getUsername(), body.getPassword());
        User user = userService.login(body.getUsername(), body.getPassword());
        
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("username", user.getUsername());
        log.info("User login map is {}", map);
        String token = jwtUtil.createToken(map);
        log.info("User login token is {}", token);

        
        try {
            String redisKey = "login:" + user.getId();
            redisTemplate.opsForValue().set(redisKey, token, 86400, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis set error", e);
        }
        

        return Result.success(token);
    }

    @PostMapping("/register2")
    public Result<String> register2(@RequestBody User body) {
        log.info("User register username is {}, password is {}", body.getUsername(), body.getPassword());
        int user = userService.register(body);
        
        if (user == 2) {
            return Result.error("用户名已存在");
        } else if (user == 3) {
            return Result.error("注册失败");
        }
      
        return Result.success();
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam("key") String key) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getUsername, key).orderByDesc(User::getAge);
        return userMapper.selectList(wrapper);
    }

    @GetMapping("/detail")
    public User detail(@RequestParam("id") int id) {
        log.info("User detail id is {}", id);
        return userMapper.selectById(id);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userMapper.selectList(null);
    }

    @GetMapping("/add")
    public String add() {
        User user = new User();
        user.setUsername("小明");
        user.setAge(18);
        userMapper.insert(user);
        return "添加成功";
    }
}
