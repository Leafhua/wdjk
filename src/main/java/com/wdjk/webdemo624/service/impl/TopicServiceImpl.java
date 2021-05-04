package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.entity.Topic;
import com.wdjk.webdemo624.mapper.TopicMapper;
import com.wdjk.webdemo624.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 话题表，保存话题基本信息 服务实现类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

}
