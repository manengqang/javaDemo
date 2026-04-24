package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.UserWithRoleDTO;
import com.example.demo.entity.User;

public interface UserService {
    // 新增
    boolean add(User user);

    // 删除
    boolean delete(Long id);

    // 修改
    boolean update(User user);

    // 根据id查询
    User getById(Long id);

    // 分页条件查询
    IPage<User> page(Integer current, Integer size, String username, Integer minAge, Integer maxAge);
    
    // 带角色信息的分页查询
    IPage<UserWithRoleDTO> pageWithRole(Integer current, Integer size, String username, Integer minAge, Integer maxAge);

    User login(String username, String password);

    int register(User user);
}
