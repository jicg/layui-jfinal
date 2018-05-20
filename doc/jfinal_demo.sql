/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : localhost:3306
 Source Schema         : jfinal_demo

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : 65001

 Date: 20/05/2018 09:34:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mean
-- ----------------------------
DROP TABLE IF EXISTS `mean`;
CREATE TABLE `mean` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `action` varchar(200) CHARACTER SET latin1 NOT NULL,
  `pid` int(11) DEFAULT '0',
  `level` int(11) DEFAULT '0',
  `issys` int(11) DEFAULT '1',
  `pname` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mean_action_uindex` (`action`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mean
-- ----------------------------
BEGIN;
INSERT INTO `mean` VALUES (1, '系统管理', '/sys', 0, 1, 0, NULL);
INSERT INTO `mean` VALUES (2, '菜单管理', '/sys/mean', 1, 2, 0, NULL);
INSERT INTO `mean` VALUES (3, '菜单列表', '/sys/meanlist', 2, 3, 0, NULL);
INSERT INTO `mean` VALUES (4, '官网管理', '/website', 0, 1, 1, '');
INSERT INTO `mean` VALUES (5, '首页管理', '/website/onemanager', 4, 2, 1, '官网管理');
INSERT INTO `mean` VALUES (7, '首页数据', '/website/one', 5, 3, 1, '首页管理');
COMMIT;

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET latin1 NOT NULL,
  `value` text CHARACTER SET latin1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET latin1 NOT NULL,
  `pwd` text CHARACTER SET latin1 NOT NULL,
  `email` text CHARACTER SET latin1 NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `active` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'admin', '123123', 'jicg@qq.com', NULL, 0);
INSERT INTO `user` VALUES (2, 'xia', '123123', 'xia@qq.com', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for website_data
-- ----------------------------
DROP TABLE IF EXISTS `website_data`;
CREATE TABLE `website_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `value` varchar(2000) DEFAULT NULL,
  `type` int(11) DEFAULT '1',
  `datatype` int(11) DEFAULT '0',
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of website_data
-- ----------------------------
BEGIN;
INSERT INTO `website_data` VALUES (1, 'page.one.welcome', '你好，12', 1, 0, '测试数据');
INSERT INTO `website_data` VALUES (2, 'page.one.logo', '/upload/image/1523349727253-QQ20180123-205105@2x.png', 1, 1, '首页左侧logo');
INSERT INTO `website_data` VALUES (3, 'page.one.video', '/upload/video/1523350932809-5645997a2eb5e.mp4', 1, 2, '首页背景视频');
INSERT INTO `website_data` VALUES (5, 'page.one.sound', '/upload/audio/1523353378296-6473.mp3', 1, 3, '测试数据');
INSERT INTO `website_data` VALUES (6, 'page.one.barcode', '/upload/image/1523365089889-wpay.png', 1, 1, '首页右侧二维码图片');
INSERT INTO `website_data` VALUES (7, 'page.one.title', '拥有属于自己更多的财富', 1, 0, '首页左侧logo标题');
INSERT INTO `website_data` VALUES (8, 'page.one.actionurl', 'http://www.baidu.com', 1, 0, '首页右侧二维码下的文字链接');
INSERT INTO `website_data` VALUES (9, 'page.one.actiontext', '体验5D商圈', 1, 0, '首页右侧二维码下的文字');
INSERT INTO `website_data` VALUES (10, 'page.one.nav1txt', '体验5D商圈', 1, 0, '右侧导航1-标题');
INSERT INTO `website_data` VALUES (11, 'page.one.nav1url', 'http://www.baidu.com', 1, 0, '右侧导航1-链接');
INSERT INTO `website_data` VALUES (12, 'page.one.nav2txt', '关注二维码', 1, 0, '右侧导航2-标题');
INSERT INTO `website_data` VALUES (13, 'page.one.nav2wxbarcode', '/upload/image/1523365720244-wpay.png', 1, 1, '右侧导航2-弹框二维码图片');
INSERT INTO `website_data` VALUES (14, 'page.one.nav2title', '欢迎关注公众号', 1, 0, '右侧导航2-弹框二维码标题');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
