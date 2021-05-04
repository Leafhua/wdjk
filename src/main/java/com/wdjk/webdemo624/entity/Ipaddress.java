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
 * ip表，存储ip地址
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("forum_ipaddress")
@ApiModel(value="Ipaddress对象", description="ip表，存储ip地址")
public class Ipaddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "论坛IP ID")
    @TableId(value = "fi_id", type = IdType.AUTO)
    private Integer fiId;

    @ApiModelProperty(value = "ipv4地址")
    private String fiIpv4;

    @ApiModelProperty(value = "ipv6地址")
    private String fiIpv6;


}
