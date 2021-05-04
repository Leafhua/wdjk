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
 * 话题操作表，保存用户和话题间的相关操作
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_topic_action")
@ApiModel(value="TopicAction对象", description="话题操作表，保存用户和话题间的相关操作")
public class TopicAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题行为 id")
    @TableId(value = "fta_id", type = IdType.AUTO)
    private Integer ftaId;

    @ApiModelProperty(value = "话题 id")
    private Integer ftId;

    @ApiModelProperty(value = "所有话题用户 id 数组（JSON int）")
    private String ftaReplyFuIdArray;

    @ApiModelProperty(value = "所有收藏话题用户 id 数组（JSON int）")
    private String ftaCollectFuIdArray;

    @ApiModelProperty(value = "所有关注话题用户 id 数组（JSON int）")
    private String ftaAttentionFuIdArray;

    @ApiModelProperty(value = "所有喜欢话题用户 id 数组（JSON int）")
    private String ftaLikeFuIdArray;


}
