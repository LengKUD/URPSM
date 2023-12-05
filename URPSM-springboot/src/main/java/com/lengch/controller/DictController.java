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

import com.lengch.entity.Dict;
import com.lengch.service.IDictService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-27
 */
@RestController
@Api(value = "Dict接口文档", tags = {"Dict接口文档"})
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private IDictService dictService;

    // 根据id查询结果
    @ApiOperation(value = "ById查询Dict")
    @GetMapping("/{id}")
    public Result<Dict> getDictById(@PathVariable Integer id){
        return Result.success(dictService.getById(id));
    }

    // 查询所有结果
    @ApiOperation(value = "获得所有Dict列表")
    @GetMapping("")
    public Result<List<Dict>> getAllDict(){
        return Result.success(dictService.list());
    }


    // 分页查询
    @ApiOperation(value = "分页查询Dict数据")
    @GetMapping("/page")
    public Result<IPage<Dict>> getPageDict(@RequestParam Integer pageNum
        ,@RequestParam Integer pageSize
        ,@RequestParam(defaultValue = "") String id){
        IPage<Dict> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        if(!id.equals("")){
            wrapper.like("id",id);
        }
        wrapper.orderByDesc("id");
        return Result.success(dictService.page(page,wrapper));
    }

    // 根据实体参数进行新增和更新
    @ApiOperation(value = "新增和更新Dict")
    @PostMapping("")
    public Result<Boolean> saveOrUpdateDict(@RequestBody Dict dict){
        return Result.success(dictService.saveOrUpdate(dict));
    }


    // 根据id删除一条记录
    @ApiOperation(value = "ById删除Dict")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteDictById(@PathVariable Integer id){
        return Result.success(dictService.removeById(id));
    }


    // 根据ids批量删除记录
    @ApiOperation(value = "ByIds批量删除Dict")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteDictBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(dictService.removeBatchByIds(ids));
    }


}
