package com.lengch.service;

import com.lengch.controller.dto.UserDTO;
import com.lengch.entity.Menu;
import com.lengch.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-11
 */
public interface IUserService extends IService<User> {

       UserDTO login(UserDTO userDTO);

       Boolean regis(UserDTO userDTO);

//    List<Menu> getUserMenu();

    List<Menu> getUserMenu(User user);
}
