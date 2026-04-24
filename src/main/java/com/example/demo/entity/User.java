package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_user")  // 对应数据库表名
public class User {

    @TableId(type = IdType.AUTO)  // 主键自增
    private Long id;
    private String username;
    private Integer age;
    private String password;
    private Long role_id;

    // getter & setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Long getRoleId() { return role_id; }
    public void setRoleId(Long roleId) { this.role_id = roleId; }
}