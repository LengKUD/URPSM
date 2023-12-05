package com.lengch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-27
 */
@Getter
@Setter
@ApiModel(value = "Dict对象", description = "字典表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("是否删除 0未删除，1删除")
    @TableLogic
    private Byte deleted;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
