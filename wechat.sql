/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2015-06-29 14:06:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wechat_key
-- ----------------------------
DROP TABLE IF EXISTS `wechat_key`;
CREATE TABLE `wechat_key` (
  `wc_id` bigint(20) NOT NULL COMMENT '和wechat_key_value对应的wc_id',
  `key_cn` varchar(30) NOT NULL COMMENT '关键字',
  `weight` tinyint(4) NOT NULL DEFAULT '1' COMMENT '权重'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_key_value
-- ----------------------------
DROP TABLE IF EXISTS `wechat_key_value`;
CREATE TABLE `wechat_key_value` (
  `wc_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统ID',
  `wc_key_num` bigint(10) NOT NULL DEFAULT '-1' COMMENT '数字关键字匹配',
  `wc_key_cn` varchar(50) DEFAULT NULL COMMENT '中文关键字匹配',
  `wc_key_content` varchar(500) DEFAULT NULL COMMENT '关键字回复内容',
  `wc_key_enable` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y表示该关键字可以使用，N表示该关键字不可使用',
  PRIMARY KEY (`wc_id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_material
-- ----------------------------
DROP TABLE IF EXISTS `wechat_material`;
CREATE TABLE `wechat_material` (
  `mt_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mt_type` varchar(10) NOT NULL COMMENT 'image|voice|video',
  `mt_url_local` varchar(500) NOT NULL COMMENT '媒体文件本地路径',
  `mt_media_id` varchar(50) DEFAULT NULL COMMENT '微信服务器端的媒体文件素材ID',
  `mt_upload_time` bigint(64) DEFAULT NULL,
  `mt_video_title` varchar(200) DEFAULT NULL COMMENT '视频媒体标题',
  `mt_video_desc` varchar(500) DEFAULT NULL COMMENT '视频媒体描述内容',
  PRIMARY KEY (`mt_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_menu
-- ----------------------------
DROP TABLE IF EXISTS `wechat_menu`;
CREATE TABLE `wechat_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统ID',
  `menu_key` varchar(20) DEFAULT NULL COMMENT '按钮访问唯一标识key',
  `menu_type` varchar(10) NOT NULL DEFAULT 'click' COMMENT 'click ：会和服务器进行交互  visit ： 不和服务器交互，点击后直接访问链接',
  `menu_parent` bigint(20) DEFAULT NULL COMMENT '上一级按钮menu_id',
  `menu_name` varchar(50) NOT NULL COMMENT '按钮标签-一级按钮长度4个中文长度，2及按钮7个中文长度',
  `menu_url` varchar(500) DEFAULT NULL COMMENT '当menu_type 为visit时该项不能为空',
  `menu_enable` char(1) NOT NULL DEFAULT 'Y' COMMENT '按钮是否启用:Y启用 Ｎ不启用，当为Ｎ时其子菜单将不可用',
  `menu_idx` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_spec_content
-- ----------------------------
DROP TABLE IF EXISTS `wechat_spec_content`;
CREATE TABLE `wechat_spec_content` (
  `key_item` varchar(20) NOT NULL,
  `val` text,
  `enable` char(1) NOT NULL DEFAULT 'Y' COMMENT 'N/Y',
  PRIMARY KEY (`key_item`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user`;
CREATE TABLE `wechat_user` (
  `openid` varchar(50) NOT NULL COMMENT '微信用户OpenId',
  `wechat_url_token` varchar(100) NOT NULL COMMENT 'openId 加密，防止暴漏',
  `subscribe` varchar(1) NOT NULL COMMENT '是否已经关注官方服务号',
  `nickname` varbinary(255) DEFAULT NULL COMMENT '微信',
  `sex` varchar(1) DEFAULT NULL COMMENT '1''男''    ；2‘女’',
  `language` varchar(10) DEFAULT NULL COMMENT '用户语言',
  `city` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `subscribe_time` bigint(20) DEFAULT NULL COMMENT '微信服务器关注时间戳',
  `user_id` bigint(20) DEFAULT NULL COMMENT '平台账户ID',
  `wechat_bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  PRIMARY KEY (`openid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
