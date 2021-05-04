package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.entity.TopicCategory;
import com.wdjk.webdemo624.mapper.TopicCategoryMapper;
import com.wdjk.webdemo624.service.TopicCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 话题分类表，0为普通话题，1为匿名话题 服务实现类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Service
public class TopicCategoryServiceImpl extends ServiceImpl<TopicCategoryMapper, TopicCategory> implements TopicCategoryService {

}
