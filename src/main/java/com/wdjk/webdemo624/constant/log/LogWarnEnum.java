package com.wdjk.webdemo624.constant.log;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-08 23:34
 **/
public enum LogWarnEnum {

    /**
     * 日志警告信息
     *      AI - Api Interceptor
     *      US - User Service
     *      ES - Email Service
     *      TS1 1- Topic Service
     *      FTPS - FTP Service
     *      VS - Validation Service
     *      UC - util class
     */

    AT1(1, "token 已经过期，需重新登录"),
    AT2(2, "无权访问 api，需登录"),
    AT3(3, "用户权限不足，非管理员用户无法访问此 api"),

    US1(1, "用户保存失败"),
    US2(2, "用户修改失败"),
    US3(3, "用户行为修改失败"),
    US33(33, "用户行为保存失败"),
    US4(4, " 授权口令已经过期"),
    US5(5, " 用户已经激活，无需再次激活"),
    US6(6, " 邮箱已被占用"),
    US7(7, " 用户密码不正确"),
    US8(8, " 未生成验证码，Session 中不存在，无法验证"),
    US9(9, " 用户输入验证码不匹配"),
    US10(10, "无权修改其余用户"),
    US11(11, " 用户名已被占用"),
    US12(12, " 口令无效"),
    US13(13, " 创建 jwt 加密 token 失败！"),
    US14(14, " IO异常，生成 captcha 图片验证码失败"),
    US15(15, " （用户）行为失败"),
    US16(16, " 数据库中不存在该用户 "),
    US17(17, " 账户未激活，无权调用 api"),
    US18(18, " 不存在用户"),

    ES1(1, " 配置文件 'account.api.validate.url' 字段为空，请补充完整！"),

    HS1(1,"ip地址保存失败"),

    TS1(1, " 话题保存失败"),
    TS2(2, " 话题内容保存失败"),
    TS3(3, " 话题回复保存失败"),
    TS4(4, " 话题删除失败"),
    TS5(5, " 话题内容删除失败"),
    TS6(6, " 话题回复删除失败"),
    TS7(7, " 话题内容修改失败"),
    TS8(8, " 话题内容修改失败"),
    TS9(9, " 话题回复修改失败"),
    TS10(10, " 不存在话题"),
    TS11(11, " 不存在回复"),
    TS12(12, "page 与 limit 参数有误， 获取范围超过话题数量，"),
    TS13(13, " 不存在话题分类"),
    TS14(14, " 已经存在话题昵称（英文 id），不要重复插入"),
    TS15(15, " 已经存在话题名称（中文名），不要重复插入"),
    TS16(16, " 根据筛选条件（limit, page, category, username），话题数为 0，获取的话题列表长度为 0"),
    TS17(17, " 根据筛选条件（limit, category, username）, 话题数为 0， 话题总页数为 0"),
    TS18(18, " 修改话题分类的描述失败！"),
    TS19(19, " 当前话题请勿重复点赞（重复输入 inc 命令）"),
    TS20(20, " 当前话题请勿重复取消点赞（重复输入 dec 命令）"),
    TS21(21, " 用户，无法取消点赞，并未点击喜欢该文章"),
    TS22(22, " （用户）行为失败"),
    TS23(23, " （话题）记录行为失败"),
    TS24(24, " 话题行为保存失败"),
    TS25(25, " 话题行为删除失败"),
    TS26(26, " 话题行为修改失败"),

    FTPS1(1, "注册用户，ftp 服务器新建 个人目录，抛出 IO 异常"),
    FTPS2(2, "用户头像图片，抛出 IO 异常"),

    VS1(1, " 需按规定输入相应参数，不能输入空参数"),
    VS2(2, " 指令无效，请根据相应约定，输入运行范围内指令"),
    VS3(3, "用户没有选择上传文件"),
    VS4(4, " 文件类型不符合头像类型（jpg | png | jpeg）"),
    VS5(5, "服务器不存在指定上传文件目录"),
    VS6(6, "IO异常，文件复制到服务器指定目录失败"),
    VS7(7, "用户上传头像过大（超过 5MB）"),

    UC1(1, " MD5 加密失败"),
    UC2(2, " Base64 转码失败"),
    UC3(3, " 读取 application 配置文件异常"),
    UC4(4, " FTP　上传文件失败"),
    UC5(5, " 删除 FTP 指定目录（or 文件）"),
    UC6(6, " FTP 服务器连接失败"),
    UC7(7, " FTP 服务器不存在指定路径！"),
    UC8(8, " 生成指定范围随机数，min > max，无法执行生成"),
    UC9(9, " 请求获取 Cookie 失败，未能从客户端 Request Headers 获取 Cookie"),
    UC10(10, " 解密用户信息 Token 失败（JWT 加密）"),
    UC11(11, " 用户对象转为 Authentication Token String 失败，缺少部分用户信息"),
    UC12(12, " 调用工具类函数异常，传入参数不能为空!");

    private Integer errorCode;
    private String errorMessage;

    LogWarnEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
