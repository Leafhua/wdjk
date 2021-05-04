package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.entity.Ipaddress;
import com.wdjk.webdemo624.mapper.IpaddressMapper;
import com.wdjk.webdemo624.service.IpaddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ip表，存储ip地址 服务实现类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Service
public class IpaddressServiceImpl extends ServiceImpl<IpaddressMapper, Ipaddress> implements IpaddressService {

}
