package com.lengch.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.sql.Types;
import java.util.Collections;

/**
 * @author : Lengchenghao
 * @create : 2023-11-11-11:11
 */
public class CodeGenerator {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/urp?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "123456";
//        String path = "E:\\Program\\URPSM-springboot\\codegenerator";
        String path = "E:\\Program\\URPSM-springboot\\src\\main\\java";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("Lengchenghao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(path); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.lengch") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\Program\\URPSM-springboot\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名,// 设置需要生成的表名,多个逗号分隔 "123","abc"
                    builder.addInclude("dict")
                            // 设置过滤表前缀
                            .addTablePrefix("t_", "c_");
//                    设置实体类使用lombok注解
                    builder.entityBuilder().enableLombok()
                            //覆盖生产文件
                            .enableFileOverride();
//                    设置驼峰命名
                    builder.controllerBuilder().enableHyphenStyle()
                            //覆盖生产文件
//                            .enableFileOverride()
                            // 开启生成@RestController 控制器
                            .enableRestStyle();
//                    builder.mapperBuilder().enableFileOverride();
//                    builder.serviceBuilder().enableFileOverride();
                    ;

                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }


}
