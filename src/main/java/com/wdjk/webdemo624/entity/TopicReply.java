package com.wdjk.webdemo624.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 话题内容回复表，保存回复内容
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_topic_reply")
@ApiModel(value="TopicReply对象", description="话题内容回复表，保存回复内容")
public class TopicReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题回复 id")
    @TableId(value = "ftr_id", type = IdType.AUTO)
    private Integer ftrId;

    @ApiModelProperty(value = "回复人 id")
    private Integer fuId;

    @ApiModelProperty(value = "对应话题 id")
    private Integer ftId;

    @ApiModelProperty(value = "论坛IP ID")
    private Integer fiId;

    @ApiModelProperty(value = "话题内容")
    private String ftrContent;

    @ApiModelProperty(value = "回复点赞数")
    private Integer ftrAgree;

    @ApiModelProperty(value = "回复反对数")
    private Integer ftrOppose;

    @ApiModelProperty(value = "回复创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date ftrCreatetime;


}
