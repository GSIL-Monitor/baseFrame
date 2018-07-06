DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `typeId` INT(11) NOT NULL DEFAULT '0' COMMENT '栏目ID - 外键',
   `name` VARCHAR(128) NOT NULL COMMENT '目录名称',
   `icon` VARCHAR(128) DEFAULT NULL COMMENT '图标',
   `url` VARCHAR(128) DEFAULT NULL COMMENT '访问url地址',
   `parentid` BIGINT(20) DEFAULT NULL COMMENT '父结点id',
   `parentids` VARCHAR(128) DEFAULT NULL COMMENT '父结点id列表串',
   `sortstring` VARCHAR(128) DEFAULT NULL COMMENT '排序号',
   `available` CHAR(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
 DROP TABLE IF EXISTS `cms_article`;
 CREATE TABLE `cms_article` (
 	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
 	`typeId` INT(11) NOT NULL DEFAULT '0' COMMENT '栏目ID - 外键',
 	`cid` INT(11) NOT NULL DEFAULT '0' COMMENT '目录ID - 外键',
 	`title` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '文件标题',
 	`summary` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '简介',
 	`content` TEXT NOT NULL DEFAULT '' COMMENT '内容',
 	`hits` INT(11) NOT NULL DEFAULT '0' COMMENT '点击量',
 	`available` char(1) DEFAULT '' COMMENT '是否可用,1：可用，0不可用',
 	`addPerson` varchar(20) NOT NULL DEFAULT '',
  	`addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	`updatePerson` varchar(2) NOT NULL DEFAULT '',
  	`updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  	PRIMARY KEY (`id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;