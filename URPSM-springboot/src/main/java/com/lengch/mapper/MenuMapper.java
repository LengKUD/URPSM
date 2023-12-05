package com.lengch.mapper;

import com.lengch.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-24
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getAllMenuList();
}
