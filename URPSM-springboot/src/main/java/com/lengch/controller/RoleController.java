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

import com.lengch.entity.Role;
import com.lengch.service.IRoleService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-23
 */
@RestController
@Api(value = "Role接口文档", tags = {"Role接口文档"})
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    // 根据id查询结果
    @ApiOperation(value = "ById查询Role")
    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable Integer id){
        return Result.success(roleService.getById(id));
    }

    // 查询所有结果
    @ApiOperation(value = "获得所有Role列表")
    @GetMapping("")
    public Result<List<Role>> getAllRole(){
        return Result.success(roleService.list());
    }


    // 分页查询
    @ApiOperation(value = "分页查询Role数据")
    @GetMapping("/page")
    public Result<IPage<Role>> getPageRole(@RequestParam Integer pageNum
        ,@RequestParam Integer pageSize
        ,@RequestParam(defaultValue = "") String s_name){
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!s_name.equals("")){
            wrapper.like("name",s_name);
        }
        wrapper.orderByDesc("id");
        return Result.success(roleService.page(page,wrapper));
    }

    // 根据实体参数进行新增和更新
    @ApiOperation(value = "新增和更新Role")
    @PostMapping("")
    public Result<Boolean> saveOrUpdateRole(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }


    // 根据id删除一条记录
    @ApiOperation(value = "ById删除Role")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteRoleById(@PathVariable Integer id){
        return Result.success(roleService.removeById(id));
    }


    // 根据ids批量删除记录
    @ApiOperation(value = "ByIds批量删除Role")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteRoleBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeBatchByIds(ids));
    }


}
