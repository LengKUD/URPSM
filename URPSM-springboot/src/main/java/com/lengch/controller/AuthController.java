package com.lengch.controller;

import com.lengch.common.Result;
import com.lengch.controller.dto.UserDTO;
import com.lengch.entity.Menu;
import com.lengch.entity.User;
import com.lengch.service.IUserService;
import com.lengch.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lengchenghao
 * @create : 2023-11-15-20:33
 */
@Api(value = "认证接口", tags = {"认证接口文档"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IUserService userService;

    //  用户登录
    @ApiOperation(value = "用户名密码登录接口")
    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody UserDTO userDTO){
        return Result.success(userService.login(userDTO));
    }

    //    用户注册
    @ApiOperation(value = "用户注册接口")
    @PostMapping("/regis")
    public Result<Boolean> userRegis(@RequestBody UserDTO userDTO){
       return Result.success(userService.regis(userDTO));
    }

    //    用户注销
    @ApiOperation(value = "用户注销接口")
    @GetMapping("/logout")
    public Result<Boolean> logout(){
        return Result.success();
    }

    //    用户修改密码
    @ApiOperation(value = "用户名修改密码接口")
    @PostMapping("/CP")
    public Result<Boolean> changePassword(@RequestBody UserDTO userDTO){
        return Result.success();
    }


    //    获取当前用户的menu菜单列表
    @ApiOperation(value = "用户名修改密码接口")
    @PostMapping("/getUserMenu")
    public Result<List<Menu>> getUserMenu(){
        User user = TokenUtils.getCurrentUserByToken();
        return Result.success(userService.getUserMenu(user));
    }

}
