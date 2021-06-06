-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.23 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 wdjk 的数据库结构
CREATE DATABASE IF NOT EXISTS `wdjk` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wdjk`;

-- 导出  表 wdjk.forum_ipaddress 结构
DROP TABLE IF EXISTS `forum_ipaddress`;
CREATE TABLE IF NOT EXISTS `forum_ipaddress` (
  `fi_id` int NOT NULL AUTO_INCREMENT COMMENT '论坛IP ID',
  `fi_ipv4` varchar(16) DEFAULT NULL COMMENT 'ipv4地址',
  `fi_ipv6` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'ipv6地址',
  PRIMARY KEY (`fi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ip表，存储ip地址';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_topic 结构
DROP TABLE IF EXISTS `forum_topic`;
CREATE TABLE IF NOT EXISTS `forum_topic` (
  `ft_id` int NOT NULL AUTO_INCREMENT COMMENT '话题id',
  `fu_id` int DEFAULT NULL COMMENT '发表人 id （对应 forum_user）',
  `ftcg_id` int NOT NULL COMMENT '话题分类id(对应forum_topic_category)',
  `ft_title` varchar(50) NOT NULL COMMENT '话题标题',
  `ft_last_replytime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后回复时间(默认为当前时间，等同与发布时间)',
  `ft_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '话题创建时间（默认当前时间）',
  `ft_last_replyuserid` int DEFAULT NULL COMMENT '话题最后回复用户id',
  PRIMARY KEY (`ft_id`),
  KEY `FT_FU_ID` (`fu_id`),
  KEY `FT_L_FU_ID` (`ft_last_replyuserid`),
  KEY `FT_FTCG_ID` (`ftcg_id`),
  CONSTRAINT `FT_FTCG_ID` FOREIGN KEY (`ftcg_id`) REFERENCES `forum_topic_category` (`ftcg_id`),
  CONSTRAINT `FT_FU_ID` FOREIGN KEY (`fu_id`) REFERENCES `forum_user` (`fu_id`),
  CONSTRAINT `FT_L_FU_ID` FOREIGN KEY (`ft_last_replyuserid`) REFERENCES `forum_user` (`fu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题表，保存话题基本信息';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_topic_action 结构
DROP TABLE IF EXISTS `forum_topic_action`;
CREATE TABLE IF NOT EXISTS `forum_topic_action` (
  `fta_id` int NOT NULL AUTO_INCREMENT COMMENT '话题行为 id',
  `ft_id` int NOT NULL COMMENT '话题 id',
  `fta_reply_fu_id_array` json DEFAULT NULL COMMENT '所有话题用户 id 数组（JSON int）',
  `fta_collect_fu_id_array` json DEFAULT NULL COMMENT '所有收藏话题用户 id 数组（JSON int）',
  `fta_attention_fu_id_array` json DEFAULT NULL COMMENT '所有关注话题用户 id 数组（JSON int）',
  `fta_like_fu_id_array` json DEFAULT NULL COMMENT '所有喜欢话题用户 id 数组（JSON int）',
  PRIMARY KEY (`fta_id`),
  UNIQUE KEY `ft_id` (`ft_id`),
  CONSTRAINT `FTA_FT_ID` FOREIGN KEY (`ft_id`) REFERENCES `forum_topic` (`ft_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='话题操作表，保存用户和话题间的相关操作';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_topic_category 结构
DROP TABLE IF EXISTS `forum_topic_category`;
CREATE TABLE IF NOT EXISTS `forum_topic_category` (
  `ftcg_id` int NOT NULL AUTO_INCREMENT COMMENT '话题分类id',
  `ftcg_is_anonymous` int NOT NULL DEFAULT '0' COMMENT '话题类型(0-普通类型，1-匿名类型)',
  PRIMARY KEY (`ftcg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='话题分类表，0为普通话题，1为匿名话题';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_topic_content 结构
DROP TABLE IF EXISTS `forum_topic_content`;
CREATE TABLE IF NOT EXISTS `forum_topic_content` (
  `ftc_id` int NOT NULL AUTO_INCREMENT COMMENT '话题内容 id',
  `ft_id` int NOT NULL COMMENT '对应话题 id （对应 forum_topic）',
  `fi_id` int DEFAULT NULL COMMENT '论坛IP ID',
  `ftc_content` longtext COLLATE utf8_bin NOT NULL COMMENT '话题内容（longtext 类型，最大长度4294967295个字元 (2^32-1)）',
  `ftc_read` int DEFAULT '0' COMMENT '话题阅读数',
  `ftc_like` int DEFAULT '0' COMMENT '喜欢话题人数',
  `ftc_picture` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '话题内容图片',
  PRIMARY KEY (`ftc_id`),
  UNIQUE KEY `ft_id` (`ft_id`),
  KEY `FTC_FI_ID` (`fi_id`),
  CONSTRAINT `FTC_FI_ID` FOREIGN KEY (`fi_id`) REFERENCES `forum_ipaddress` (`fi_id`),
  CONSTRAINT `FTC_FT_ID` FOREIGN KEY (`ft_id`) REFERENCES `forum_topic` (`ft_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='话题内容表，保存论坛内容相关信息';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_topic_reply 结构
DROP TABLE IF EXISTS `forum_topic_reply`;
CREATE TABLE IF NOT EXISTS `forum_topic_reply` (
  `ftr_id` int NOT NULL AUTO_INCREMENT COMMENT '话题回复 id',
  `fu_id` int DEFAULT NULL COMMENT '回复人 id',
  `ft_id` int DEFAULT NULL COMMENT '对应话题 id',
  `fi_id` int DEFAULT NULL COMMENT '论坛IP ID',
  `ftr_content` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '话题内容',
  `ftr_agree` int DEFAULT '0' COMMENT '回复点赞数',
  `ftr_oppose` int DEFAULT '0' COMMENT '回复反对数',
  `ftr_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '回复创建时间',
  PRIMARY KEY (`ftr_id`),
  KEY `FTR_FU_ID` (`fu_id`),
  KEY `FTR_FT_ID` (`ft_id`),
  KEY `FTR_FI_ID` (`fi_id`),
  CONSTRAINT `FTR_FI_ID` FOREIGN KEY (`fi_id`) REFERENCES `forum_ipaddress` (`fi_id`),
  CONSTRAINT `FTR_FT_ID` FOREIGN KEY (`ft_id`) REFERENCES `forum_topic` (`ft_id`),
  CONSTRAINT `FTR_FU_ID` FOREIGN KEY (`fu_id`) REFERENCES `forum_user` (`fu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='话题内容回复表，保存回复内容';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_user 结构
DROP TABLE IF EXISTS `forum_user`;
CREATE TABLE IF NOT EXISTS `forum_user` (
  `fu_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `fi_id` int DEFAULT NULL COMMENT '论坛IP ID',
  `fu_name` varchar(15) DEFAULT NULL COMMENT '用户名称 ',
  `fu_password` varchar(200) NOT NULL COMMENT '用户密码',
  `fu_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户邮箱地址',
  `fu_birthday` varchar(20) DEFAULT NULL COMMENT '用户生日',
  `fu_avator` varchar(100) DEFAULT NULL COMMENT '用户头像地址',
  `fu_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户介绍',
  `fu_theme` int NOT NULL DEFAULT '0' COMMENT '用户主题，默认0号主题',
  `fu_rank` int NOT NULL DEFAULT '0' COMMENT '用户级别(0-普通用户，1-管理员)',
  `fu_state` int NOT NULL DEFAULT '0' COMMENT '激活状态（0-未激活，1-已激活）',
  `fu_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  PRIMARY KEY (`fu_id`),
  UNIQUE KEY `fu_email_UNIQUE` (`fu_email`),
  UNIQUE KEY `fu_name_UNIQUE` (`fu_name`),
  KEY `FU_FI_ID` (`fi_id`),
  CONSTRAINT `FU_FI_ID` FOREIGN KEY (`fi_id`) REFERENCES `forum_ipaddress` (`fi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表，保存用户相关信息';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_user_action 结构
DROP TABLE IF EXISTS `forum_user_action`;
CREATE TABLE IF NOT EXISTS `forum_user_action` (
  `fua_id` int NOT NULL AUTO_INCREMENT COMMENT '用户操作 id',
  `fu_id` int NOT NULL COMMENT '用户 id',
  `fua_like_ft_id_array` json DEFAULT NULL COMMENT '用户喜欢所有话题 id 数组（JSON int）',
  `fua_collect_ft_id_array` json DEFAULT NULL COMMENT '用户收藏话题 id 数组（JSON int）',
  `fua_attention_ft_id_array` json DEFAULT NULL COMMENT '用户关注话题 id 数组（JSON int）',
  `fua_following_fu_id_array` json DEFAULT NULL COMMENT '[主动]用户主动关注的用户 id 数组（JSON int）',
  `fua_followed_fu_id_array` json DEFAULT NULL COMMENT '[被动]关注该用户的用户 id 数组（JSON int）',
  PRIMARY KEY (`fua_id`),
  UNIQUE KEY `fu_id` (`fu_id`),
  CONSTRAINT `FUA_FU_ID` FOREIGN KEY (`fu_id`) REFERENCES `forum_user` (`fu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户操作表，用户相关操作';

-- 数据导出被取消选择。

-- 导出  表 wdjk.forum_user_dynamic 结构
DROP TABLE IF EXISTS `forum_user_dynamic`;
CREATE TABLE IF NOT EXISTS `forum_user_dynamic` (
  `fud_id` int NOT NULL AUTO_INCREMENT COMMENT '用户动态 id',
  `fu_id` int NOT NULL COMMENT '用户 id',
  `fud_public_info_array` json DEFAULT NULL COMMENT '用户动态信息,用户发布的所有消息JSON: {{''发布时间'', ''消息'', ''可见性限制''},{ ... }}',
  PRIMARY KEY (`fud_id`),
  KEY `FUD_FU_ID` (`fu_id`),
  CONSTRAINT `FUD_FU_ID` FOREIGN KEY (`fu_id`) REFERENCES `forum_user` (`fu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户动态表，保存用户动态信息';

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
