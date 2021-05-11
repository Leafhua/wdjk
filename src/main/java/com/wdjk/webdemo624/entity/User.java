package com.wdjk.webdemo624.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表，保存用户相关信息
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_user")
@ApiModel(value="User对象", description="用户表，保存用户相关信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "fu_id", type = IdType.AUTO)
    private Integer fuId;

    @ApiModelProperty(value = "论坛IP ID")
    private Integer fiId;

    @ApiModelProperty(value = "用户名称 ")
    private String fuName;

    @ApiModelProperty(value = "用户密码")
    private String fuPassword;

    @ApiModelProperty(value = "用户邮箱地址")
    private String fuEmail;

    @ApiModelProperty(value = "用户生日")
    private String fuBirthday;

    @ApiModelProperty(value = "用户头像地址")
    private String fuAvator;

    @ApiModelProperty(value = "用户介绍")
    private String fuDescription;

    @ApiModelProperty(value = "用户主题，默认0号主题")
    private Integer fuTheme;

    @ApiModelProperty(value = "用户级别(0-普通用户，1-管理员)")
    private Integer fuRank;

    @ApiModelProperty(value = "激活状态(0-未激活，1-已激活)")
    private Integer fuState;

    @ApiModelProperty(value = "用户创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date fuCreatetime;


}
