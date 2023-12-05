package com.lengch.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.lengch.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.lengch.entity.Files;
import com.lengch.service.IFilesService;


import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-22
 */
@RestController
@Api(value = "Files接口文档", tags = {"Files接口文档"})
@RequestMapping("/files")
public class FilesController {

    @Value("${file.upload.path}")
    String uploadPath;
    @Autowired
    private IFilesService filesService;

    // 根据id查询结果
    @ApiOperation(value = "ById查询Files")
    @GetMapping("/{id}")
    public Result<Files> getFilesById(@PathVariable Integer id){
        return Result.success(filesService.getById(id));
    }

    // 查询所有结果
    @ApiOperation(value = "获得所有Files列表")
    @GetMapping("")
    public Result<List<Files>> getAllFiles(){
        return Result.success(filesService.list());
    }


    // 分页查询
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
    @ApiOperation(value = "新增和更新Files")
    @PostMapping("")
    public Result saveOrUpdateFiles(@RequestBody Files files){
        return Result.success(filesService.saveOrUpdate(files));
    }


    // 根据id删除一条记录
    @ApiOperation(value = "ById删除Files")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteFilesById(@PathVariable Integer id){
        return Result.success(filesService.removeById(id));
    }


    // 根据ids批量删除记录
    @ApiOperation(value = "ByIds批量删除Files")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteFilesBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(filesService.removeBatchByIds(ids));
    }

    @ApiOperation(value = "文件上传接口")
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file",required = true) MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();//文件名
        String contentType = FileUtil.extName(originalFilename);//文件类型
        long size = multipartFile.getSize()/1024;//文件大小,单位KB
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid+ StrUtil.DOT +contentType;
        String url = "http://localhost:9090/files/download/"+fileName;
        //根据配置路径创建文件
        File filePath = new File(uploadPath);
        //文件路径不存在就根据路径创建
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File uploadFile = new File(filePath,fileName);

        multipartFile.transferTo(uploadFile);

        Files files = new Files();
        files.setName(fileName);
        files.setOriginalFilename(originalFilename);
        files.setSize(size);
        files.setType(contentType);
        files.setAddress(uploadFile.getPath());
        files.setUrl(url);
        filesService.save(files);

        return url;
    }
    //文件下载接口
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        找到根据文件名找到文件
        File uploadFile = new File(uploadPath,fileName);
//        设置文件流格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/octet-stream");

//        读取文件字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();


    }


}
