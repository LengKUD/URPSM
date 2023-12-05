package com.lengch.service;

import com.lengch.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-24
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenuTerr();

    void removeEmptyChildren(List<Menu> children, List<String> list);

    Boolean delMenuById(Integer id);

    List<Menu> setMenuToTree(List<Menu> allList);
}
