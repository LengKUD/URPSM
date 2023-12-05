package com.lengch;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lengch.entity.Menu;
import com.lengch.service.IMenuService;
import com.lengch.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;

@SpringBootTest
class UrpsmSpringbootApplicationTests {

    @Value("${file.upload.path}")
    String uploadPath;

    @Autowired
    IMenuService menuService;

    @Autowired
    IUserService userService;

    @Test
    void contextLoads() throws IOException {



        File file1 = new File("E:/FileServer/2.png");
        String file1Name = file1.getName();

        System.out.println("file1Name====>"+file1Name);

        String type = FileUtil.getType(file1);

        System.out.println("type====>"+type);


        String type1 = FileUtil.extName(file1Name);
        System.out.println("type1====>"+type1);

        long length = file1.length();

        System.out.println("length====>"+length);

        String uuid = IdUtil.fastSimpleUUID();
        //根据配置路径创建文件
        File filePath = new File(uploadPath);
        //        文件路径不存在就根据路径创建
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File uploadFile = new File(filePath,uuid+"."+type);
        uploadFile.createNewFile();

    }
    @Test
    void fileTest1() throws IOException {

        File file1 = new File("E:/FileServer/DB 01.PDF");

        URLConnection connection = file1.toURI().toURL().openConnection();
        String contentType = connection.getContentType();

        System.out.println("contentType====>"+contentType);



    }

    @Test
    void fileTest2() throws IOException {

        FileInputStream inputStream = new FileInputStream("E:/FileServer/DB 01.PDF");

        String contentType = URLConnection.guessContentTypeFromStream(new BufferedInputStream(inputStream));

        System.out.println("contentType====>"+contentType);

    }

    @Test
    void menuTest(){

        List<Menu> menuList;
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",0);
        menuList = menuService.list(queryWrapper);


        for (Menu m:
                menuList) {

            m.setChildren(new ArrayList<>());
            m.getChildren().add(m);
            System.out.println(m);
        }

    }

    @Test
    void testStringToList(){

        String a = "1,2,3,4";
        String[] aS = a.split(",");

        List<String> al = new ArrayList<>(Arrays.asList(aS));

        System.out.println("=====a");
        System.out.println(a);

        System.out.println("=====aS");
        System.out.println(aS);

        System.out.println("=====al");
        System.out.println(al);


    }

    @Test
    void getUserMenu(){
//        userService.getUserMenu();
    }
}
