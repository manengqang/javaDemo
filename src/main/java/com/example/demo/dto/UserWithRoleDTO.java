package com.example.demo.dto;

import com.example.demo.entity.SystemRole;

public class UserWithRoleDTO {
    private Long id;
    private String username;
    private Integer age;
    private String password;
    private Long role_id;
    private SystemRole role;

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

    public SystemRole getRole() { return role; }
    public void setRole(SystemRole role) { this.role = role; }
}