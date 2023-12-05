package com.lengch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-24
 */
@Getter
@Setter
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("父菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("访问地址")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("页面路径")
    private String pagePath;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("父菜单id")
    private Integer pid;

    @ApiModelProperty("是否删除 0未删除，1删除")
    @TableLogic
    private Byte Deleted;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<Menu> children;

    @ApiModelProperty("图标类型")
    @TableField(exist = false)
    private String iconType;
}
