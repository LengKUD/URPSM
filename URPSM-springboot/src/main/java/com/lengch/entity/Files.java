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
 * 
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-22
 */
@Getter
@Setter
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称，保存到服务器上的名称")
    private String name;

    @ApiModelProperty("上传时原始文件名称")
    private String originalFilename;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小,对应java中long类型")
    private Long size;

    @ApiModelProperty("文件实际存储地址")
    private String address;

    @ApiModelProperty("文件下载地址")
    private String url;

    @ApiModelProperty("是否可用（0可用1不可用）")
    private Byte disable;

    @ApiModelProperty("是否删除 0未删除，1删除")
    @TableLogic
    private Byte deleted;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
