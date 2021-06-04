package com.wdjk.webdemo624.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.*;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.mapper.*;
import com.wdjk.webdemo624.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdjk.webdemo624.utils.JsonUtil;
import com.wdjk.webdemo624.utils.MapFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private final TopicMapper topicMapper;
    private final TopicCategoryMapper topicCategoryMapper;
    private final TopicContentMapper topicContentMapper;
    private final TopicReplyMapper topicReplyMapper;
    private final UserMapper userMapper;
    private final HttpServiceImpl httpService;

    @Autowired
    public TopicServiceImpl(TopicMapper topicMapper, TopicCategoryMapper topicCategoryMapper, TopicContentMapper topicContentMapper, TopicReplyMapper topicReplyMapper, UserMapper userMapper, HttpServiceImpl httpService) {
        this.topicMapper = topicMapper;
        this.topicCategoryMapper = topicCategoryMapper;
        this.topicContentMapper = topicContentMapper;
        this.topicReplyMapper = topicReplyMapper;
        this.userMapper = userMapper;
        this.httpService = httpService;
    }


    @Override
    public Page<Topic> listTopics(int limit, int page, int anonymous, String username) {

        if (limit == 0){
            limit = 30;
        }
        this.confirmNoExceedTopicTotalNumber(limit,page);
        int categoryId = anonymous == 0 ? 0 : this.getTopicCategoryNotNullByAnonymous(anonymous-1).getFtcgId();
        int userId = username == null ? 0 : this.getUserNotNullByName(username).getFuId();

        //todo
        Page<Topic> dbQueryTopicListList = this.getTopicList(limit,page,categoryId,userId);
        if (dbQueryTopicListList.getSize() == 0){
            throw new ServiceException(ApiMessage.NO_QUERY_TOPICS).log(LogWarnEnum.TS16);
        }

        return dbQueryTopicListList;
    }

    @Override
    public Map<String, Object> getTopicContentModelMap(int topicId) {
        Topic topic = this.getTopicNotNull(topicId);
        TopicContent topicContent = this.getTopicContentNotNull(topicId);
        TopicCategory topicCategory = this.getTopicCategoryNotNullById(topic.getFtcgId());
        User topicAuthorUser = this.getUserNotNullById(topic.getFuId());

        Map<String,Object> topicInfoMap = this.getTopicInfoMap(topic);
        Map<String,Object> topicContentInfoMap = this.getTopicContentInfoMap(topicContent);
        Map<String,Object> topicCategoryInfoMap = this.getTopicCategoryInfoMap(topicCategory);
        Map<String,Object> authorUserInfoMap = this.getTopicUserInfoMap(topicAuthorUser);
        Map<String,Object> lastReplyUserInfoMap
                = this.getTopicUserInfoMap(this.getUserNotNullById(topic.getFtLastReplyuserid()));

        QueryWrapper<TopicReply> topicReplyQueryWrapper = new QueryWrapper<>();
        topicReplyQueryWrapper.eq("ft_id",topicId);
        List<TopicReply> listReply = topicReplyMapper.selectList(topicReplyQueryWrapper);
        List<Map<String,Object>> listReplyInfoMap = new ArrayList<>(listReply.size());
        for (TopicReply reply : listReply){
            Map<String,Object> replyInfoMap = this.getTopicReplyInfoMap(reply);
            replyInfoMap.put(ParamConst.USER,this.getTopicUserInfoMap(userMapper.selectById(reply.getFuId())));

            listReplyInfoMap.add(replyInfoMap);
        }

        topicInfoMap.putAll(topicContentInfoMap);
        topicInfoMap.put(ParamConst.CATEGORY,topicCategoryInfoMap);
        topicInfoMap.put(ParamConst.USER,authorUserInfoMap);
        topicInfoMap.put(ParamConst.LAST_REPLY_USER,lastReplyUserInfoMap);
        topicInfoMap.put(ParamConst.REPLY_LIST,lastReplyUserInfoMap);

        return topicInfoMap;
    }

    @Override
    public Map<String, Object> saveTopic(int userId, int anonymous, String title, String topicContent) {
        TopicCategory category = this.getTopicCategoryNotNullByAnonymous(anonymous);

        Topic topic = new Topic();
        topic.setFuId(userId);
        topic.setFtcgId(category.getFtcgId());
        topic.setFtTitle(title);
        topic.setFtLastReplyuserid(userId);

        if (topicMapper.insert(topic) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS1);
        }

        TopicContent topicContent1 = new TopicContent();
        topicContent1.setFtId(topic.getFtId());
        topicContent1.setFtcContent(topicContent);
        topicContent1.setFiId(httpService.savaIPAddress().getFiId());

        if (topicContentMapper.insert(topicContent1) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS2);
        }

        return MapFilterUtil.generateMap(ParamConst.TOPIC_ID,topic.getFtId());

    }

    @Override
    public Map<String, Object> saveAnonymousTopic(int anonymous, String title, String topicContent) {
        TopicCategory category = this.getTopicCategoryNotNullByAnonymous(anonymous);

        Topic topic = new Topic();
        topic.setFtcgId(category.getFtcgId());
        topic.setFtTitle(title);

        if (topicMapper.insert(topic) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS1);
        }

        TopicContent topicContent1 = new TopicContent();
        topicContent1.setFtId(topic.getFtId());
        topicContent1.setFtcContent(topicContent);
        topicContent1.setFiId(httpService.savaIPAddress().getFiId());

        if (topicContentMapper.insert(topicContent1) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS2);
        }

        return MapFilterUtil.generateMap(ParamConst.TOPIC_ID,topic.getFtId());
    }

    @Override
    public Map<String, Object> saveReply(int userId, int topicId, String replyContent) {
        this.getTopicNotNull(topicId);

        TopicReply topicReply = new TopicReply();
        topicReply.setFuId(userId);
        topicReply.setFtId(topicId);
        topicReply.setFtrContent(replyContent);
        topicReply.setFiId(httpService.savaIPAddress().getFiId());

        if (topicReplyMapper.insert(topicReply) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS3);
        }
        return MapFilterUtil.generateMap(ParamConst.REPLY_ID,topicReply.getFtrId());
    }

    @Override
    public Map<String, Object> saveAnonymousReply(int topicId, String replyContent) {
        this.getTopicNotNull(topicId);

        TopicReply topicReply = new TopicReply();
        topicReply.setFtId(topicId);
        topicReply.setFtrContent(replyContent);
        topicReply.setFiId(httpService.savaIPAddress().getFiId());

        if (topicMapper.selectById(topicId).getFtcgId() != 2 ){
            throw new ServiceException(ApiMessage.USER_OPERATE_TOPIC_FAIL).log(LogWarnEnum.TS3);
        }
        if (topicReplyMapper.insert(topicReply) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.TS3);
        }
        return MapFilterUtil.generateMap(ParamConst.REPLY_ID,topicReply.getFtrId());
    }

    @Override
    public Map<String, Object> getTopicReplyModelMap(int replyId) {
        TopicReply reply = this.getTopicReplyNotNull(replyId);

        if (topicCategoryMapper.selectById(topicMapper.selectById(reply.getFtId()).getFtcgId()).getFtcgIsAnonymous()==1){

            return this.getTopicReplyInfoMap(reply);
        }
        else {
            User replayUser = this.getUserNotNullById(reply.getFuId());

            Map<String,Object> replayInfoMap = this.getTopicReplyInfoMap(reply);
            replayInfoMap.put(ParamConst.USER,this.getTopicUserInfoMap(replayUser));
            return replayInfoMap;
        }
    }

    /**
     * 获取话题列表
     *      - 话题分类 id + 用户名 id
     *      - 话题分类 id
     *      - 用户 id
     *      - 默认
     *
     * @param limit 每页显示数量
     * @param page 跳转指定页数
     * @param categoryId 话题分类id
     * @param userId 用户id
     * @return 话题集合列表
     */
    private Page<Topic> getTopicList(int limit, int page, int categoryId, int userId) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ft_last_replytime");
        Page<Topic> topicPage = new Page<>(page,limit);
        if (categoryId != 0) {
            if (categoryId == 1 ){
                queryWrapper.eq("ftcg_id",1);
            }
            else {
                queryWrapper.eq("ftcg_id",2);
            }
        } else if (userId != 0) {
            queryWrapper.eq("fu_id",userId);
        } else {
            return topicMapper.selectPage(topicPage,queryWrapper);
        }
        return topicMapper.selectPage(topicPage,queryWrapper);
    }

    /**
     * （name）确认用户不能为空
     *      - forum_user
     *
     * @param username 用户名
     * @return UserDO 用户对象
     */
    private User getUserNotNullByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fu_name",username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throwNoUserExceptionByName(username);
        }

        return user;
    }

    /**
     * （id）确认话题分类不为空
     *      - forum_topic_category
     *
     * @param categoryId 分类Id
     * @return  TopicCategoryDO 话题分类对象
     */
    private TopicCategory getTopicCategoryNotNullById(int categoryId) {
        TopicCategory category = topicCategoryMapper.selectById(categoryId);
        if (category == null) {
            throwNotCategoryExceptionById(categoryId);
        }

        return category;
    }

    /**
     * 获取话题分类对象不能为空（通过 nick）
     *      - 对应 forum_topic_category - 'ftcg_nick'
     *
     * @param anonymous 话题分类（0普通，1匿名）
     * @return TopicCategoryDO 话题分类对象
     */
    private TopicCategory getTopicCategoryNotNullByAnonymous(int anonymous) {
        QueryWrapper<TopicCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ftcg_is_anonymous",anonymous);
        TopicCategory category = topicCategoryMapper.selectOne(queryWrapper);
        if (category == null) {
            throwNotCategoryExceptionByAnonymous(anonymous);
        }

        return category;
    }

    /*
     * ***********************************************
     * filter information map (use util/MapFilter.java)
     * ***********************************************
     */

    /**
     * 获取话题基本信息 Map
     *
     * @param topic 话题对象
     * @return Map 已处理的话题信息
     */
    private Map<String, Object> getTopicInfoMap(Topic topic) {
        if (topic == null) {
            return new HashMap<>(0);
        }

        Map<String, Object> topicInfoMap = JsonUtil.toMapByObject(topic);
        MapFilterUtil.filterTopicInfo(topicInfoMap);

        return topicInfoMap;
    }

    /**
     * 获取话题内容信息 Map
     *
     * @param topicContent 话题内容对象
     * @return Map 已处理的话题内容信息
     */
    private Map<String, Object> getTopicContentInfoMap(TopicContent topicContent) {
        if (topicContent == null) {
            return new HashMap<>(0);
        }

        Map<String, Object> topicContentInfoMap = JsonUtil.toMapByObject(topicContent);
        MapFilterUtil.filterTopicContentInfo(topicContentInfoMap);

        return topicContentInfoMap;
    }

    /**
     * 获取话题分类信息 Map
     *
     * @param category 话题分类对象
     * @return Map 已处理的话题分类信息
     */
    private Map<String, Object> getTopicCategoryInfoMap(TopicCategory category) {
        if (category == null) {
            return new HashMap<>(0);
        }

        Map<String, Object> topicCategoryInfoMap = JsonUtil.toMapByObject(category);
        MapFilterUtil.filterTopicCategory(topicCategoryInfoMap);

        return topicCategoryInfoMap;
    }

    /**
     * 获取话题用户信息 Map
     *
     * @param topicUser 话题用户对象
     * @return Map 已处理的话题用户信息
     */
    private Map<String, Object> getTopicUserInfoMap(User topicUser) {
        if (topicUser == null) {
            //build empty map
            LinkedHashMap<String, Object> topicUserInfoMap = new LinkedHashMap<>(SetConst.SIZE_TWO);
            topicUserInfoMap.put(ParamConst.USERNAME, "");
            topicUserInfoMap.put(ParamConst.AVATOR, "");

            return topicUserInfoMap;
        }

        //json -> Map, filter topic user information map
        Map<String, Object> topicUserInfoMap = JsonUtil.toMapByObject(topicUser);
        MapFilterUtil.filterTopicUserInfo(topicUserInfoMap);

        return topicUserInfoMap;
    }

    /**
     * 获取话题回复信息 Map
     *
     * @param topicReply 话题回复对象
     * @return Map 已处理的话题回复信息
     */
    private Map<String, Object> getTopicReplyInfoMap(TopicReply topicReply) {
        if (topicReply == null) {
            return new HashMap<>(0);
        }

        Map<String, Object> topicReplyInfoMap = JsonUtil.toMapByObject(topicReply);
        MapFilterUtil.filterTopicReply(topicReplyInfoMap);

        return topicReplyInfoMap;
    }


    /**
     * 获取话题，不能为空
     *      - forum_topic
     *
     * @param topicId 话题id
     * @return TopicDO 话题对象
     */
    private Topic getTopicNotNull(int topicId) {
        Topic topic = topicMapper.selectById(topicId);
        if (topic == null){
            throwNoTopicException(topicId);
        }

        return topic;
    }

    /**
     * 获取话题内容，不能为空
     *      - forum_topic_content
     *
     * @param topicId 话题id
     * @return TopicContentDO 话题内容对象
     */
    private TopicContent getTopicContentNotNull(int topicId) {
        QueryWrapper<TopicContent> wrapper = new QueryWrapper<>();
        wrapper.eq("ft_id",topicId);
        TopicContent topicContent = topicContentMapper.selectOne(wrapper);
        if (topicContent == null){
            throwNoTopicContentException(topicId);
        }
        return topicContent;
    }

    /**
     * 确认话题回复不为空
     *      - forum_topic_reply
     *
     * @param replyId 话题回复id
     * @return TopicReplyDO 话题回复对象
     */
    private TopicReply getTopicReplyNotNull(int replyId) {
        TopicReply reply = topicReplyMapper.selectById(replyId);
        if (reply == null) {
            throw new ServiceException(ApiMessage.NO_REPLY).log(LogWarnEnum.TS11);
        }

        return reply;
    }




    /**
     * （id）确认用户不能为空
     *
     * @param userId 用户名
     * @return UserDO 用户对象
     */
    private User getUserNotNullById(int userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throwNoUserExceptionById(userId);
        }

        return user;
    }

    /*
     * ***********************************************
     * confirm method
     * ***********************************************
     */

    /**
     * 确认不会超过话题数量
     *
     * @param limit 每页显示限制
     * @param page 跳转指定页数
     */
    private void confirmNoExceedTopicTotalNumber(int limit, int page) {


        int allTopicTotals = this.count();
        int maxPage = allTopicTotals % limit == 0
                ? allTopicTotals / limit : allTopicTotals / limit + 1;

        if (limit > allTopicTotals || page > maxPage) {
            throw new ServiceException(ApiMessage.QUERY_EXCEED_TOPIC_NUMBER).log(LogWarnEnum.TS12);
            //"（话题总数 = allTopicTotals，若 limit = limit，最多跳转至 maxPage 页）")
        }
    }

    /**
     * 确认用户和话题不能未空
     *
     * @param userId 用户id
     * @param topicId 话题id
     */
    private void confirmUserAndTopicNotNull(int userId, int topicId) {
        this.getUserNotNullById(userId);
        this.getTopicNotNull(topicId);
    }



    /*
     * ***********************************************
     * throw exception method
     * ***********************************************
     */

    /**
     * 抛出不存在话题异常
     *
     * @param topicId 话题id
     */
    private void throwNoTopicException(int topicId) {
        throw new ServiceException(ApiMessage.NO_TOPIC).log(LogWarnEnum.TS10);
    }

    /**
     * 抛出不存在话题内容异常
     *
     * @param topicId 话题id
     */
    private void throwNoTopicContentException(int topicId) {
        //"topic content topicId=" + topicId +
        throw new ServiceException(ApiMessage.NO_TOPIC).log(LogWarnEnum.TS10);
    }

    /**
     * (id)抛出不存在用户异常
     *
     * @param userId 用户id
     */
    private void throwNoUserExceptionById(int userId) {
        throw new ServiceException(ApiMessage.NO_USER).log(LogWarnEnum.US18);
    }

    /**
     * （username）抛出不存在用户异常
     *
     * @param username 用户名
     */
    private void throwNoUserExceptionByName(String username) {
        throw new ServiceException(ApiMessage.NO_USER).log(LogWarnEnum.US18);
    }

    /**
     * （id）抛出不存话题分类异常
     */
    private void throwNotCategoryExceptionById(int categoryId) {
        throw new ServiceException(ApiMessage.NO_CATEGORY).log(LogWarnEnum.TS13);
    }

    /**
     * （nick）抛出不存话题分类异常
     */
    private void throwNotCategoryExceptionByAnonymous(int anonymous) {
        throw new ServiceException(ApiMessage.NO_CATEGORY).log(LogWarnEnum.TS13);
    }

    /**
     * 抛出用户操作话题失败异常
     *
     * @param userOperate 用户操作
     */
    private void throwUserOperateTopicFailException(String userOperate) {
        throw new ServiceException(ApiMessage.USER_OPERATE_TOPIC_FAIL).log(LogWarnEnum.TS22);
    }

    /**
     * 抛出话题操作失败异常
     *
     * @param topicOperate 话题操作
     */
    private void throwTopicOperateFailException(String topicOperate) {
        throw new ServiceException(ApiMessage.TOPIC_RECORD_OPERATE_FAIL).log(LogWarnEnum.TS23);
    }
}
