package com.lengch.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.lengch.common.Result;
import com.lengch.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.lengch.entity.User;
import com.lengch.service.IUserService;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-11
 */
@RestController
@Api(value = "User接口文档", tags = {"User接口文档"})
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    // 获取当前用户信息
    @ApiOperation(value = "通过token获取当前用信息")
    @GetMapping("/current")
    public Result<User> getCurrentUser(){
        return Result.success(TokenUtils.getCurrentUserByToken());
    }


    // 根据id查询结果
    @ApiOperation(value = "ById查询User")
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Integer id){
        return Result.success(userService.getById(id));
    }

    // 查询所有结果
    @ApiOperation(value = "获得所有User列表")
    @GetMapping("")
    public Result<List<User>> getAllUser(){
        return Result.success(userService.list());
    }


    // 分页查询
    @ApiOperation(value = "分页查询User数据")
    @GetMapping("/page")
    public Result<IPage<User>> getPageUser(@RequestParam Integer pageNum
        ,@RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "") String s_username
            ,@RequestParam(defaultValue = "") String s_nickname
            ,@RequestParam(defaultValue = "") String s_address){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!s_username.equals("")){
            wrapper.like("name",s_username);
        }
        if(!s_nickname.equals("")){
            wrapper.like("nickname",s_nickname);
        }
        if(!s_address.equals("")){
            wrapper.like("address",s_address);
        }
        wrapper.orderByDesc("create_time");
        return Result.success(userService.page(page,wrapper));
    }

    // 根据实体参数进行新增和更新
    @ApiOperation(value = "新增和更新User")
    @PostMapping("")
    public Result<Boolean> saveOrUpdateUser(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }


    // 根据id删除一条记录
    @ApiOperation(value = "ById删除User")
    @DeleteMapping("/del/{id}")
    public Result<Boolean> deleteUserById(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }


    // 根据ids批量删除记录
    @ApiOperation(value = "ByIds批量删除User")
    @PostMapping("/del/batch")
    public Result<Boolean> deleteUserBatchByIds(@RequestBody List<Integer> ids){
        return Result.success(userService.removeBatchByIds(ids));
    }


    // 导出Excel
    @ApiOperation(value = "导出Excel")
    @GetMapping("/exp")
    public Boolean expUserToExcel(HttpServletResponse response) throws IOException {
        //先取出所有数据
        List<User> userList = userService.list();

        //写入本地磁盘
//        ExcelWriter writer = ExcelUtil.getWriter("/用户信息.xls");

        //内存操作，之后写入浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

//        自定义列名标题，User类属性和excel对应
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("loginName","登录名");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("address","地址");

//        一次性写入Excel对象，带上表头
        writer.write(userList,true);


//out为OutputStream，需要写出到的目标流

//response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");

        String fileName = URLEncoder.encode("用户信息","utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ fileName +".xls");
        ServletOutputStream out=response.getOutputStream();

        writer.flush(out, true);
// 关闭writer，释放内存
        writer.close();
//此处记得关闭输出Servlet流
        IoUtil.close(out);

        return true;
    }

    // 导入Excel
    @ApiOperation(value = "导入Excel")
    @PostMapping(value = "/imp",headers = "content-type=multipart/form-data")
    public Boolean impExcelToUser(@RequestParam(value = "file",required = true) MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

//        设置excel表头名称和类名称对应
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("登录名","loginName");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("地址","address");


        List<User> userList = reader.readAll(User.class);
        return userService.saveBatch(userList);


    }

}
