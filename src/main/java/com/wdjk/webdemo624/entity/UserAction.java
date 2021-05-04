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
 * 用户操作表，用户相关操作
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_user_action")
@ApiModel(value="UserAction对象", description="用户操作表，用户相关操作")
public class UserAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户操作 id")
    @TableId(value = "fua_id", type = IdType.AUTO)
    private Integer fuaId;

    @ApiModelProperty(value = "用户 id")
    private Integer fuId;

    @ApiModelProperty(value = "用户喜欢所有话题 id 数组（JSON int）")
    private String fuaLikeFtIdArray;

    @ApiModelProperty(value = "用户收藏话题 id 数组（JSON int）")
    private String fuaCollectFtIdArray;

    @ApiModelProperty(value = "用户关注话题 id 数组（JSON int）")
    private String fuaAttentionFtIdArray;

    @ApiModelProperty(value = "[主动]用户主动关注的用户 id 数组（JSON int）")
    private String fuaFollowingFuIdArray;

    @ApiModelProperty(value = "[被动]关注该用户的用户 id 数组（JSON int）")
    private String fuaFollowedFuIdArray;


}
