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
 * 话题分类表，0为普通话题，1为匿名话题
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_topic_category")
@ApiModel(value="TopicCategory对象", description="话题分类表，0为普通话题，1为匿名话题")
public class TopicCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题分类id")
    @TableId(value = "ftcg_id", type = IdType.AUTO)
    private Integer ftcgId;

    @ApiModelProperty(value = "话题类型(0-普通类型，1-匿名类型)")
    private Integer ftcgIsAnonymous;


}
