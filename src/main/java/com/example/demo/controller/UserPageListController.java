package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Result;
import com.example.demo.dto.UserWithRoleDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserPageListController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody User user) {
        log.info("user=====:{}", user);
        boolean flag = userService.add(user);
        if (flag) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(Long id) {
        boolean flag = userService.delete(id);
        if (flag) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @PostMapping("/update")
    public Result<String> update(User user) {
        boolean flag = userService.update(user);
        if (flag) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @GetMapping("/get")
    public Result<User> getById(Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/page")
    public Result<IPage<UserWithRoleDTO>> page(
        @RequestParam(defaultValue = "1") Integer current, 
        @RequestParam(required = false, defaultValue = "10") Integer size, 
        @RequestParam(required = false) String username, 
        @RequestParam(required = false) Integer minAge, 
        @RequestParam(required = false) Integer maxAge
    ) {
        IPage<UserWithRoleDTO> page = userService.pageWithRole(current, size, username, minAge, maxAge);
        return Result.success(page);
    }
}
