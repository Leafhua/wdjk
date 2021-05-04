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
 * 话题内容表，保存论坛内容相关信息
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_topic_content")
@ApiModel(value="TopicContent对象", description="话题内容表，保存论坛内容相关信息")
public class TopicContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题内容 id")
    @TableId(value = "ftc_id", type = IdType.AUTO)
    private Integer ftcId;

    @ApiModelProperty(value = "对应话题 id （对应 forum_topic）")
    private Integer ftId;

    @ApiModelProperty(value = "论坛IP ID")
    private Integer fiId;

    @ApiModelProperty(value = "话题内容（longtext 类型，最大长度4294967295个字元 (2^32-1)）")
    private String ftcContent;

    @ApiModelProperty(value = "话题阅读数")
    private Integer ftcRead;

    @ApiModelProperty(value = "喜欢话题人数")
    private Integer ftcLike;

    @ApiModelProperty(value = "话题内容图片")
    private String ftcPicture;


}
