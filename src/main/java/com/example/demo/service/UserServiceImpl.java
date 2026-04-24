package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.UserWithRoleDTO;
import com.example.demo.entity.SystemRole;
import com.example.demo.entity.User;
import com.example.demo.mapper.SystemRoleMapper;
import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private SystemRoleMapper systemRoleMapper;

  @Override
  public boolean add(User user) {
      return userMapper.insert(user) > 0;
  }

  @Override
  public boolean delete(Long id) {
      return userMapper.deleteById(id) > 0;
  }

  @Override
  public boolean update(User user) {
      return userMapper.updateById(user) > 0;
  }

  @Override
  public User getById(Long id) {
      return userMapper.selectById(id);
  }

  @Override
  public IPage<User> page(Integer current, Integer size, String username, Integer minAge, Integer maxAge) {
    Page<User> page = new Page<>(current, size);
    LambdaQueryWrapper<User>  wrapper = new LambdaQueryWrapper<>();

    // 用户名模糊（不为空才拼接）
    if (username != null && !username.isBlank()) {
        wrapper.like(User::getUsername, username);
    }

    // 年龄 >= minAge
    if (minAge != null) {
        wrapper.ge(User::getAge, minAge);
    }

    // 年龄 <= maxAge
    if (maxAge != null) {
        wrapper.le(User::getAge, maxAge);
    }

    // 按ID倒序
    wrapper.orderByDesc(User::getId);

    return userMapper.selectPage(page, wrapper);
  }

  @Override
  public IPage<UserWithRoleDTO> pageWithRole(Integer current, Integer size, String username, Integer minAge, Integer maxAge) {
      Page<User> page = new Page<>(current, size);
      LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

      // 用户名模糊（不为空才拼接）
      if (username != null && !username.isBlank()) {
          wrapper.like(User::getUsername, username);
      }

      // 年龄 >= minAge
      if (minAge != null) {
          wrapper.ge(User::getAge, minAge);
      }

      // 年龄 <= maxAge
      if (maxAge != null) {
          wrapper.le(User::getAge, maxAge);
      }

      // 按ID倒序
      wrapper.orderByDesc(User::getId);

      IPage<User> userPage = userMapper.selectPage(page, wrapper);
      return userPage.convert(user -> {
          UserWithRoleDTO dto = new UserWithRoleDTO();
          dto.setId(user.getId());
          dto.setUsername(user.getUsername());
          dto.setAge(user.getAge());
          if (user.getRoleId() != null) {
            SystemRole role = systemRoleMapper.selectRoleById(user.getRoleId());
            dto.setRoleId(role.getId());
            dto.setRole(role);
          }
          return dto;
      });
  }

  /**
   * 登录
   * @param username 用户名
   * @param password 密码
   * @return 登录成功返回用户信息，失败返回null
   */
  @Override
  public User login(String username, String password) {
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getUsername, username).eq(User::getPassword, password);
    User user = userMapper.selectOne(wrapper);

    if (user != null) {
      return userMapper.selectOne(wrapper);
    } else {
      return null;
    }
  }

  @Override
  public int register(User user) {
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getUsername, user.getUsername());
    User existUser = userMapper.selectOne(wrapper);

    if (existUser != null) {
      return 2; // 用户名已存在，注册失败
    }

    return userMapper.insert(user) > 0 ? 1 : 0;
  }
}
