package com.lengch.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengch.common.Result;
import com.lengch.config.NoAuth;
import com.lengch.entity.Files;
import com.lengch.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-22
 */
@RestController
@Api(value = "Files缓存接口文档Demo", tags = {"Files缓存接口文档Demo"})
@RequestMapping("/filesCache")
public class FilesCacheController {

    @Value("${file.upload.path}")
    String uploadPath;
    @Autowired
    private IFilesService filesService;

    // 根据id查询结果
    @NoAuth
    @ApiOperation(value = "ById查询Files")
    @GetMapping("/{id}")
    public Result<Files> getFilesById(@PathVariable Integer id){
        return Result.success(filesService.getById(id));
    }

    // 查询所有结果
    @NoAuth
    @Cacheable(value = "fileCache", key="'alllist'")//加入缓存
    @ApiOperation(value = "获得所有Files列表放入缓存")
    @GetMapping("/cache")
    public Result<List<Files>> getAllFilesToCache(){
        return Result.success(filesService.list());
    }


    // 分页查询
    @NoAuth
    @Cacheable(value = "fileCache", key="''")//加入缓存
    @ApiOperation(value = "分页查询Files数据")
    @GetMapping("/page")
    public Result<IPage<Files>> getPageFiles(@RequestParam Integer pageNum
        , @RequestParam Integer pageSize
        , @RequestParam(defaultValue = "") String s_originalFilename
        , @RequestParam(defaultValue = "") String s_type){
        IPage<Files> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        if(!s_originalFilename.equals("")){
            wrapper.like("original_filename",s_originalFilename);
        }
        if(!s_type.equals("")){
            wrapper.like("type",s_type);
        }
        wrapper.orderByDesc("id");
        return Result.success(filesService.page(page,wrapper));
    }

    // 根据实体参数进行新增和更新
    @NoAuth
    @CachePut(value = "fileCache", key="'alllist'")//更新操作同时更新缓存
    @ApiOperation(value = "新增和更新Files同时更新缓存")
    @PostMapping("")
    public Result saveOrUpdateFiles(@RequestBody Files files){
        filesService.saveOrUpdate(files);
        List<Files> filelist = filesService.list();
        return Result.success(filelist);
    }


    // 根据id删除一条记录
    @NoAuth
    @CacheEvict(value = "fileCache", key="'alllist'")//删除操作同时清空缓存
    @ApiOperation(value = "ById删除Files同时清空缓存")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteFilesById(@PathVariable Integer id){
        return Result.success(filesService.removeById(id));
    }


    // 根据ids批量删除记录
    @NoAuth
    @ApiOperation(value = "ByIds批量删除Files")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteFilesBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(filesService.removeBatchByIds(ids));
    }

}
