package com.lengch.controller;

import com.lengch.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.lengch.entity.Menu;
import com.lengch.service.IMenuService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-24
 */
@RestController
@Api(value = "Menu接口文档", tags = {"Menu接口文档"})
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    // 根据id查询结果
    @ApiOperation(value = "ById查询Menu")
    @GetMapping("/{id}")
    public Result<Menu> getMenuById(@PathVariable Integer id){
        return Result.success(menuService.getById(id));
    }

    // 查询所有结果
    @ApiOperation(value = "获得所有Menu列表")
    @GetMapping("")
    public Result<List<Menu>> getMenuList(){
        return Result.success(menuService.getAllMenuTerr());
    }


    // 分页查询
    @ApiOperation(value = "分页查询Menu数据")
    @GetMapping("/page")
    public Result<IPage<Menu>> getPageMenu(@RequestParam Integer pageNum
        ,@RequestParam Integer pageSize
        ,@RequestParam(defaultValue = "") String s_name){
        IPage<Menu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if(!s_name.equals("")){
            wrapper.like("name",s_name);
        }
        wrapper.orderByDesc("id");
        return Result.success(menuService.page(page,wrapper));
    }

    // 根据实体参数进行新增和更新
    @ApiOperation(value = "新增和更新Menu")
    @PostMapping("")
    public Result<Integer> saveOrUpdateMenu(@RequestBody Menu menu){

        //新增或者删除mybatispuls 在新增后会将主键id返回给实体类，
        menuService.saveOrUpdate(menu);
//        id返回前端直接使用
        return Result.success(menu.getId());
    }


    // 根据id删除一条记录
    @ApiOperation(value = "ById删除Menu")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteMenuById(@PathVariable Integer id){
        return Result.success(menuService.delMenuById(id));
    }


    // 根据ids批量删除记录
    @ApiOperation(value = "ByIds批量删除Menu")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteMenuBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(menuService.removeBatchByIds(ids));
    }


}
