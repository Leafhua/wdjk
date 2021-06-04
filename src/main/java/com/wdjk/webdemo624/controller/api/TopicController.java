package com.wdjk.webdemo624.controller.api;

import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.controller.annotation.AccountActivation;
import com.wdjk.webdemo624.controller.annotation.LoginAuthorization;
import com.wdjk.webdemo624.dto.ApiJsonDTO;
import com.wdjk.webdemo624.entity.Ipaddress;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 话题api
 *      获取话题信息
 *      获取回复信息
 *      获取话题信息列表（搜索）
 *      发布话题
 *      发布匿名话题
 *      发布回复
 *      发布匿名回复
 *
 *
 *
 *
 *
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-31 22:27
 **/

@RestController
@RequestMapping(value = "/api")
public class TopicController {

    private final IValidationService validationService;
    private final TopicService topicService;
    private final UserService userService;
    private final IHttpService httpService;
    private final ISecretService secretService;

    @Autowired
    private TopicController(IValidationService validationService, TopicService topicService, UserService userService, IHttpService httpService, ISecretService secretService) {
        this.validationService = validationService;
        this.topicService = topicService;
        this.userService = userService;
        this.httpService = httpService;
        this.secretService = secretService;
    }

    /**
     * 获取话题信息
     *      - 能够获取当前用户是否点赞该文章信息（访客用户默认为 false）
     *      - hadread 参数决定是否增加阅读数（0 - 不增加, 1 - 增加）
     *
     * @param topicId 话题 id
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/topic",method = RequestMethod.GET)
    public ApiJsonDTO getTopicInfo(@RequestParam(value = "topicid",required = false) String topicId
                                   ){
        validationService.check(ParamConst.ID,topicId);
        int topicIdInt = Integer.parseInt(topicId);
        Map<String,Object> topicContentPageModelMap = topicService.getTopicContentModelMap(topicIdInt);

        return new ApiJsonDTO().success().model(topicContentPageModelMap);
    }

    /**
     * 获取回复信息
     *
     * @param replyId 回复 id
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/topic/reply",method = RequestMethod.GET)
    public ApiJsonDTO getTopicReplyInfo(@RequestParam(value = "replyid",required = false)String replyId){
        validationService.check(ParamConst.ID,replyId);
        return new ApiJsonDTO().success().model(topicService.getTopicReplyModelMap(Integer.parseInt(replyId)));
    }



    /**
     * 发布话题
     *
     * @param requestBodyParamsMap request-body 内requestBodyMap
     * @return ApiJsonDTO 接口 JSON 传输数据
     */
    @LoginAuthorization
    @AccountActivation
    @RequestMapping(value = "/topic", method = RequestMethod.POST, consumes = "application/json")
    public ApiJsonDTO releaseTopic(@RequestBody Map<String,Object> requestBodyParamsMap){
        String category = "0";
        String title = (String) requestBodyParamsMap.get(ParamConst.TITLE);
        String topicContent = (String) requestBodyParamsMap.get(ParamConst.CONTENT);

        validationService.check(ParamConst.TOPIC_CATEGORY_NICK,category)
                .check(ParamConst.TOPIC_TITLE,title)
                .check(ParamConst.TOPIC_CONTENT,topicContent);

        User cookieUser = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());

        return new ApiJsonDTO().success().model(topicService.saveTopic(cookieUser.getFuId(),Integer.parseInt(category),title,topicContent));
    }
    /**
     * 发布匿名话题
     *
     * @param requestBodyParamsMap request-body 内requestBodyMap
     * @return ApiJsonDTO 接口 JSON 传输数据
     */
    @RequestMapping(value = "/anonymous-topic", method = RequestMethod.POST, consumes = "application/json")
    public ApiJsonDTO anonymousReleaseTopic(@RequestBody Map<String,Object> requestBodyParamsMap){
        String category = "1";
        String title = (String) requestBodyParamsMap.get(ParamConst.TITLE);
        String topicContent = (String) requestBodyParamsMap.get(ParamConst.CONTENT);

        validationService.check(ParamConst.TOPIC_CATEGORY_NICK,category)
                .check(ParamConst.TOPIC_TITLE,title)
                .check(ParamConst.TOPIC_CONTENT,topicContent);

        return new ApiJsonDTO().success().model(topicService.saveAnonymousTopic(Integer.parseInt(category),title,topicContent));
    }


    /**
     * 发布匿名回复
     *
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/topic/anonymous-reply",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO releaseTopicAnonymousReply(@RequestBody Map<String,Object> requestBodyParamsMap){
        Integer topicId = (Integer) requestBodyParamsMap.get(ParamConst.TOPIC_ID);
        String replyContent = (String) requestBodyParamsMap.get(ParamConst.CONTENT);

        validationService
                .check(ParamConst.REPLY_LIST,replyContent);

        return new ApiJsonDTO().success().model(topicService.saveAnonymousReply(topicId,replyContent));
    }




    /**
     * 发布回复
     *
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @LoginAuthorization
    @AccountActivation
    @RequestMapping(value = "/topic/reply",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO releaseTopicReply(@RequestBody Map<String,Object> requestBodyParamsMap){
        Integer topicId = (Integer) requestBodyParamsMap.get(ParamConst.TOPIC_ID);
        String replyContent = (String) requestBodyParamsMap.get(ParamConst.CONTENT);

        validationService.check(ParamConst.ID,String.valueOf(topicId))
                .check(ParamConst.REPLY_LIST,replyContent);

        User cookieUser = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());

        return new ApiJsonDTO().success().model(topicService.saveReply(cookieUser.getFuId(),topicId,replyContent));
    }



    /**
     * 获取话题信息列表
     *      - 首页话题基本信息，最新发布，最新回复自动置顶
     *      - 可设置分页，每页显示数量
     *      - limit 参数具备默认值（neubbs.properties 配置文件中可设置）
     *      - 可设置筛选条件（category 和 username）
     *
     * @param limit 每页显示数量
     * @param page 跳转到指定页数
     * @param category 话题分类 id（英文昵称）
     * @param username 用户名
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "topics",method = RequestMethod.GET)
    public ApiJsonDTO listHomeTopics(@RequestParam(value = "limit",required = false)String limit,
                                     @RequestParam(value = "page",required = false)String page,
                                     @RequestParam(value = "category",required = false)String category,
                                     @RequestParam(value = "username",required = false)String username){
        validationService.check(ParamConst.NUMBER,page);
        validationService.checkOnlyNotNullParam(
                ParamConst.NUMBER,limit,
                ParamConst.TOPIC_CATEGORY_NICK,category,
                ParamConst.USERNAME,username
        );
        int anonymous = Integer.parseInt(category);
        int limitParam = limit == null ? 0 : Integer.parseInt(limit);
        return new ApiJsonDTO().success()
                .model(topicService.listTopics(limitParam,Integer.parseInt(page),anonymous,username));

    }

}
