package com.lengch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lengch.common.Constants;
import com.lengch.controller.dto.UserDTO;
import com.lengch.entity.Menu;
import com.lengch.entity.Role;
import com.lengch.entity.User;
import com.lengch.exception.ServiceException;
import com.lengch.mapper.UserMapper;
import com.lengch.service.IMenuService;
import com.lengch.service.IRoleService;
import com.lengch.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengch.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IRoleService roleService;

    @Autowired
    IMenuService menuService;
    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Login_name", userDTO.getLoginName());
        queryWrapper.eq("password", userDTO.getPassword());

        User user;
        try{
            user = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.Code_DB,"数据异常");
        }

        if(user !=null) {
            BeanUtil.copyProperties(user, userDTO, true);
            userDTO.setMenuList(this.getUserMenu(user));//加载用户菜单菜单
            String token = TokenUtils.getToken(user.getId().toString(),user.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else{
            throw new ServiceException(Constants.Code_DB,"用户名或密码错误");
        }

    }

    @Override
    public Boolean regis(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Login_name", userDTO.getLoginName());

        if (count(queryWrapper)!=0 ) {
            throw new ServiceException(Constants.Code_DB,"用户登录名已经存在！");
        }

        User user = new User();
        BeanUtil.copyProperties(userDTO,user,true);
        return save(user);

    }

    @Override
    public List<Menu> getUserMenu(User user) {
//        User user=TokenUtils.getCurrentUserByToken();
////        User user = getById("1");
        QueryWrapper<Role> qw =new QueryWrapper<>();
        qw.in("id", user.getRoles().split(","));
        List<Role> roles = roleService.list(qw);
        String menuIds = "";

        for (Role r: roles) {

            menuIds =  menuIds.concat(r.getMenus()).concat(",");
        }


        List<String> menuIdList = new ArrayList<>(Arrays.asList(menuIds.split(",")));


        //取出所有菜单
        List<Menu> allMenuTerr = menuService.getAllMenuTerr();

//        根据用户拥有的菜单权限，更新菜单列表
        menuService.removeEmptyChildren(allMenuTerr,menuIdList);

        return allMenuTerr;
    }



}
