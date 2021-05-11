package com.wdjk.webdemo624.constant.api;

/**
 * 常量设置
 *      -地址
 *      -数字类型
 *
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-09 21:40
 **/
public final class SetConst {

    public static final int ACTIVE = 1;
    public static final int INACTIVE = 0;


    /**
     * 地址
     *
     */
    public static final String AVATAR_PATH = "/avatars/";

    /*
     * ***********************************************
     * 数字类型
     * ***********************************************
     */

    /**
     * 字符的 ASCII 码数字范围
     *      - 小写字母 ASCII 最小值，最大值
     *      - 大写字母 ASCII 最小值，最大值
     *      - 单个数字最小值，最大值
     */
    public static final int LOWERCASE_ASCII_MIN = 97;
    public static final int LOWERCASE_ASCII_MAX = 122;
    public static final int UPPERCASE_ASCII_MIN = 65;
    public static final int UPPERCASE_ASCII_MAX = 90;
    public static final int FIGURE_MIN = 0;
    public static final int FIGURE_MAX = 10;

    /**
     * 字母
     *      - 所有字母数量（26 个字母）
     */
    public static final int ALL_LETTER_AMOUNT = 26;

    /**
     * 指针
     *      - 适用于流程判断
     */
    public static final int POINT_ONE = 1;
    public static final int POINT_TWO = 2;
    public static final int POINT_THREE = 3;

    /*
     * ***********************************************
     *  时间
     * ***********************************************
     */

    /**
     * 时间
     *      1000 ms
     *      24 小时
     *      60 秒
     *      60 分钟
     */
    public static final int TIME_THOUSAND_MS = 1000;
    public static final int TIME_TWENTY_FOUR_HOUR = 24;
    public static final int TIME_SIXTY_S = 60;
    public static final int TIME_SIXTY_MIN = 60;

    /**
     * 日期时间
     *      - 1 天（ms，毫秒）
     */
    public static final long ONE_DAY_S  = 86400L;
    public static final long ONE_DAY_MS = 86400000L;

    /**
     * 过期时间
     *      - 60 秒 (ms)
     *      - 0 s
     *      - Redis 不存在 key ，值为 -2
     */
    public static final long EXPIRE_TIME_SIXTY_SECOND_MS = 60000L;
    public static final int EXPIRE_TIME_ZERO_S  = 0;
    public static final long EXPIRE_TIME_REDIS_NO_EXIST_KEY = -2L;
}
