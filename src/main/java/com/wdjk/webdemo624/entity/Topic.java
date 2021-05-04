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
 * 话题表，保存话题基本信息
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_topic")
@ApiModel(value="Topic对象", description="话题表，保存话题基本信息")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题id")
    @TableId(value = "ft_id", type = IdType.AUTO)
    private Integer ftId;

    @ApiModelProperty(value = "发表人 id （对应 forum_user）")
    private Integer fuId;

    @ApiModelProperty(value = "话题分类id(对应forum_topic_category)")
    private Integer ftcgId;

    @ApiModelProperty(value = "话题标题")
    private String ftTitle;

    @ApiModelProperty(value = "最后回复时间(默认为当前时间，等同与发布时间)")
    @TableField(fill = FieldFill.INSERT)
    private Date ftLastReplytime;

    @ApiModelProperty(value = "话题创建时间（默认当前时间）")
    @TableField(fill = FieldFill.INSERT)
    private Date ftCreatetime;

    @ApiModelProperty(value = "话题最后回复用户id")
    private Integer ftLastReplyuserid;


}
