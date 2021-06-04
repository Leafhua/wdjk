package com.wdjk.webdemo624.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdjk.webdemo624.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 话题表，保存话题基本信息 服务类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
public interface TopicService extends IService<Topic> {

    /**
     * 获取话题内容 Model Map
     *      - topicId 需验证存在性
     *      - 话题信息（基本信息 + 内容 + 分类）
     *      - 回复信息
     *
     * @param topicId 话题 id
     * @return Map 话题内容页信息
     */
    Map<String, Object> getTopicContentModelMap(int topicId);

    /**
     * 保存话题
     *      - userId 无需判断存在性（用户必须登陆，才能访问相应接口，发布主题）
     *
     * @param userId 用户 id
     * @param anonymous 话题分类
     * @param title 话题标题
     * @param topicContent 话题内容
     * @return Map 保存（新增的话题 id）
     */
    Map<String, Object> saveTopic(int userId, int anonymous, String title, String topicContent);

    /**
     * 保存匿名话题
     *      - userId 无需判断存在性（用户必须登陆，才能访问相应接口，发布主题）
     *
     * @param anonymous 话题分类
     * @param title 话题标题
     * @param topicContent 话题内容
     * @return Map 保存（新增的话题 id）
     */
    Map<String, Object> saveAnonymousTopic(int anonymous, String title, String topicContent);

    /**
     * 获取话题回复 Model Map
     *      - 单条回复
     *
     * @param replyId 回复 id
     * @return Map 回复信息
     */
    Map<String, Object> getTopicReplyModelMap(int replyId);

    /**
     * 获取话题列表
     *      - username 和 categoryNick 需验证存在性
     *      - limit 可为 0，表示使用 neubbs.properties 默认配置文件
     *      - categoryNick 和 username 可以为 null（即表示不参与查询条件）
     *
     * @param limit 每页显示数量
     * @param page 跳转到指定页数
     * @param anonymous 话题分类昵称（1为普通类型，2为匿名类型）
     * @param username 用户名
     * @return List 话题列表
     */
    Page<Topic> listTopics(int limit, int page, int anonymous, String username);

    /**
     * 保存回复
     *      - userId 无需判断存在性
     *      - topicId 需判断存在性
     *
     * @param userId 用户 id
     * @param topicId 话题 id
     * @param replyContent 话题内容
     * @return Map 保存（新增的回复 id）
     */
    Map<String, Object> saveReply(int userId, int topicId, String replyContent);
    /**
     * 保存回复
     *      - userId 无需判断存在性
     *      - topicId 需判断存在性
     *
     * @param topicId 话题 id
     * @param replyContent 话题内容
     * @return Map 保存（新增的回复 id）
     */
    Map<String, Object> saveAnonymousReply(int topicId, String replyContent);

}
