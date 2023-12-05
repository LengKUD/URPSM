package com.lengch.controller.dto;

import com.lengch.entity.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lengchenghao
 * @create : 2023-11-15-20:41
 */
@Data
public class UserDTO {
    private Integer id;
    private String loginName;
    private String password;
    private String nickname;
    private String name;
    private String avatar;
    private String token;
    private List<Menu> menuList = new ArrayList<>();
}
