package com.wdjk.webdemo624.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户动态表，保存用户动态信息
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_user_dynamic")
@ApiModel(value="UserDynamic对象", description="用户动态表，保存用户动态信息")
public class UserDynamic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户动态 id")
    @TableId(value = "fud_id", type = IdType.AUTO)
    private Integer fudId;

    @ApiModelProperty(value = "用户 id")
    private Integer fuId;

    @ApiModelProperty(value = "用户动态信息,用户发布的所有消息JSON: {{'发布时间', '消息', '可见性限制'},{ ... }}")
    private String fudPublicInfoArray;


}
