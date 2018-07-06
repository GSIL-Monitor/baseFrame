/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.13 : Database - framework
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`framework` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `framework`;

/*Table structure for table `cm_base_data` */

DROP TABLE IF EXISTS `cm_base_data`;

CREATE TABLE `cm_base_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `key` varchar(20) NOT NULL DEFAULT '' COMMENT 'Key',
  `value` varchar(20) NOT NULL DEFAULT '' COMMENT 'Value',
  `seq` int(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='基础数据配置表';

/*Table structure for table `cm_festival` */

DROP TABLE IF EXISTS `cm_festival`;

CREATE TABLE `cm_festival` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '节日名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节日表';

/*Table structure for table `cm_festival_date` */

DROP TABLE IF EXISTS `cm_festival_date`;

CREATE TABLE `cm_festival_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `fId` int(11) NOT NULL DEFAULT '0' COMMENT '节日id',
  `year` int(4) NOT NULL DEFAULT '0' COMMENT '年份',
  `fRestDate` varchar(100) NOT NULL DEFAULT '' COMMENT '放假日期',
  `fWorkDate` varchar(100) NOT NULL DEFAULT '' COMMENT '补工日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法定假日及补工日安排表';

/*Table structure for table `cm_resource` */

DROP TABLE IF EXISTS `cm_resource`;

CREATE TABLE `cm_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '资源名称，既操作释义',
  `widgetCode` varchar(50) NOT NULL DEFAULT '' COMMENT '界面控件编码，用于控制界面控件的显示，规则：控件类型_操作',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '关联访问地址（相对）',
  `seq` tinyint(4) NOT NULL DEFAULT '0',
  `menuFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单标识位，0：非菜单，1：菜单',
  `operType` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作类型，0：查询，1：增删改',
  `chkAuthFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '鉴权标志位，0：不鉴权，1：鉴权',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统资源表';

/*Table structure for table `cm_role` */

DROP TABLE IF EXISTS `cm_role`;

CREATE TABLE `cm_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据）',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

/*Table structure for table `cm_role_resource_rel` */

DROP TABLE IF EXISTS `cm_role_resource_rel`;

CREATE TABLE `cm_role_resource_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `roleId` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `resourceId` int(11) NOT NULL DEFAULT '0' COMMENT '资源ID',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色-资源关联表';

/*Table structure for table `cm_service_area` */

DROP TABLE IF EXISTS `cm_service_area`;

CREATE TABLE `cm_service_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` varchar(10) NOT NULL DEFAULT '' COMMENT '名称',
  `pinyin` varchar(100) NOT NULL DEFAULT '' COMMENT '拼音',
  `fpy` varchar(10) NOT NULL DEFAULT '' COMMENT '首字母拼音',
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '级别，1：省（+直辖市），2：地级市和直辖市，3：区县，4：商圈',
  `seq` int(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务区域配置表';

/*Table structure for table `cm_take_time` */

DROP TABLE IF EXISTS `cm_take_time`;

CREATE TABLE `cm_take_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `mcId` int(11) NOT NULL DEFAULT '0' COMMENT '餐别ID(基础数据表)',
  `value` varchar(5) NOT NULL DEFAULT '' COMMENT 'Value（例如：11:50）',
  `seq` int(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='取餐时间配置表';

/*Table structure for table `cm_user` */

DROP TABLE IF EXISTS `cm_user`;

CREATE TABLE `cm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `userName` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `realName` varchar(30) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '用户密码',
  `roleId` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='人员表';

/*Table structure for table `prd_cleandishes` */

DROP TABLE IF EXISTS `prd_cleandishes`;

CREATE TABLE `prd_cleandishes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `pinyin` varchar(200) NOT NULL DEFAULT '' COMMENT '拼音',
  `fpy` varchar(20) NOT NULL DEFAULT '' COMMENT '首字母拼音',
  `maId` int(11) NOT NULL DEFAULT '0' COMMENT '食材（物料）ID',
  `ratio` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '与食材转化比例，净菜为1',
  `nodeUseTime` int(8) NOT NULL DEFAULT '0' COMMENT '单节点制作用时（分/kg）',
  `note` varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='净菜表';

/*Table structure for table `prd_cleandishes_rel` */

DROP TABLE IF EXISTS `prd_cleandishes_rel`;

CREATE TABLE `prd_cleandishes_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `prdId` int(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
  `cdId` int(11) NOT NULL DEFAULT '0' COMMENT '净菜ID',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量，菜品为1份',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品-净菜关联表';

/*Table structure for table `prd_material` */

DROP TABLE IF EXISTS `prd_material`;

CREATE TABLE `prd_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `typeId` int(11) NOT NULL DEFAULT '0' COMMENT '物料类型ID',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '物料名称',
  `pinyin` varchar(200) NOT NULL DEFAULT '' COMMENT '拼音',
  `fpy` varchar(20) NOT NULL DEFAULT '' COMMENT '首字母拼音',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  `purchaseType` tinyint(4) NOT NULL DEFAULT '0' COMMENT '采购类型，0：非日常采购，1：日常采购',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物料表';

/*Table structure for table `prd_material_rel` */

DROP TABLE IF EXISTS `prd_material_rel`;

CREATE TABLE `prd_material_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `prdId` int(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
  `maId` int(11) NOT NULL DEFAULT '0' COMMENT '物料ID',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量，菜品为1份',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品-物料关联表';

/*Table structure for table `prd_material_type` */

DROP TABLE IF EXISTS `prd_material_type`;

CREATE TABLE `prd_material_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` varchar(10) NOT NULL DEFAULT '' COMMENT '名称',
  `pinyin` varchar(100) NOT NULL DEFAULT '' COMMENT '拼音',
  `fpy` varchar(10) NOT NULL DEFAULT '' COMMENT '首字母拼音',
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '级别',
  `seq` int(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物料类型配置表';

/*Table structure for table `prd_pd_cd` */

DROP TABLE IF EXISTS `prd_pd_cd`;

CREATE TABLE `prd_pd_cd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `pdId` int(11) NOT NULL DEFAULT '0' COMMENT '备菜ID',
  `cdId` int(11) NOT NULL DEFAULT '0' COMMENT '净菜ID',
  `ratio` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '比例关系，备菜为1000g',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备菜-净菜关联表';

/*Table structure for table `prd_pd_ma` */

DROP TABLE IF EXISTS `prd_pd_ma`;

CREATE TABLE `prd_pd_ma` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `prdId` int(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
  `maId` int(11) NOT NULL DEFAULT '0' COMMENT '物料ID',
  `ratio` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '比例关系，备菜为1000g',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备菜-物料关联表';

/*Table structure for table `prd_preparedishes` */

DROP TABLE IF EXISTS `prd_preparedishes`;

CREATE TABLE `prd_preparedishes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `pinyin` varchar(200) NOT NULL DEFAULT '' COMMENT '拼音',
  `fpy` varchar(20) NOT NULL DEFAULT '' COMMENT '首字母拼音',
  `nodeUseTime` int(8) NOT NULL DEFAULT '0' COMMENT '单节点制作用时（分，主要指腌制、泡发等时间）',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备菜表';

/*Table structure for table `prd_preparedishes_rel` */

DROP TABLE IF EXISTS `prd_preparedishes_rel`;

CREATE TABLE `prd_preparedishes_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `prdId` int(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
  `pdId` int(11) NOT NULL DEFAULT '0' COMMENT '备菜ID',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量，菜品为1份',
  `uomId` int(11) NOT NULL DEFAULT '0' COMMENT '计量单位ID(基础数据表)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品-备菜关联表';

/*Table structure for table `prd_product` */

DROP TABLE IF EXISTS `prd_product`;

CREATE TABLE `prd_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '产品名称',
  `description` text NOT NULL COMMENT '产品描述',
  `typeId` int(11) NOT NULL DEFAULT '0' COMMENT '类型ID(基础数据表)',
  `nodeUseTime` int(8) NOT NULL DEFAULT '0' COMMENT '单节点制作用时（分）',
  `maxMakeNum` int(8) NOT NULL DEFAULT '0' COMMENT '单次最多制作份数',
  `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '资源名称',
  `type` varchar(32) NOT NULL COMMENT '资源类型：menu,button,',
  `url` varchar(128) DEFAULT NULL COMMENT '访问url地址',
  `percode` varchar(128) DEFAULT NULL COMMENT '权限代码字符串',
  `parentid` bigint(20) DEFAULT NULL COMMENT '父结点id',
  `parentids` varchar(128) DEFAULT NULL COMMENT '父结点id列表串',
  `sortstring` varchar(128) DEFAULT NULL COMMENT '排序号',
  `available` char(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '''角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据）''',
  `available` char(1) DEFAULT '' COMMENT '是否可用,1：可用，0不可用',
  `addPerson` varchar(20) NOT NULL DEFAULT '',
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatePerson` varchar(2) NOT NULL DEFAULT '',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `sys_role_id` varchar(32) NOT NULL COMMENT '角色id',
  `sys_permission_id` varchar(32) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `usercode` varchar(32) NOT NULL COMMENT '账号',
  `username` varchar(64) NOT NULL COMMENT '姓名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `locked` char(1) DEFAULT NULL COMMENT '账号是否锁定，1：锁定，0未锁定',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `sys_user_id` varchar(32) NOT NULL,
  `sys_role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8;

CREATE TABLE `cm_oper_log` (
   `id` int(11) NOT NULL COMMENT 'PK',
   `clientIP` varchar(20) NOT NULL DEFAULT '' COMMENT '客户端IP',
   `operatorId` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
   `operatorName` varchar(30) NOT NULL DEFAULT '' COMMENT '操作人姓名',
   `resName` varchar(100) NOT NULL DEFAULT '' COMMENT '资源名称',
   `requestUri` varchar(100) NOT NULL DEFAULT '' COMMENT '访问地址',
   `requestArgs` text NOT NULL COMMENT '访问参数',
   `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE TABLE `bs_leave_bill` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `userId` int(11) NOT NULL DEFAULT '0' COMMENT '请假人FK',
   `billType` int(11) NOT NULL DEFAULT '0' COMMENT '请假类型',
   `days` int(11) NOT NULL DEFAULT '0' COMMENT '请假天数',
   `beginDate` timestamp DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
   `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
   `remark` text NOT NULL DEFAULT '' COMMENT '备注',
   `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '标识位，0：初始录入，1：审批中， 2：审批完成',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假单';
 
-- 创建秒杀库存表seckill.
create table if not exists `seckill` (
  seckill_id bigint not null AUTO_INCREMENT comment '商品库存id',
  name varchar(120) not null comment '商品名称',
  number int not null comment '商品库存',
  start_time TIMESTAMP not null DEFAULT '0000-00-00 00:00:00' comment '秒杀开始的时间',
  end_time TIMESTAMP not null DEFAULT '0000-00-00 00:00:00' comment '秒杀结束的时间',
  create_time TIMESTAMP not null default current_timestamp comment '秒杀创建的时间',
  -- 选择seckill_id作为秒杀系统表的主键id,primary key
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 default charset=utf8 comment='秒杀库存表';

-- 对于秒杀库存表的一些初始化的数据
insert into seckill(name, number, start_time, end_time)
  values('1000元秒杀iphone6', 100, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('500元秒杀小米6', 50, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('600元秒杀ipad', 120, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('550元秒杀小米6', 50, '2015-11-01 00:00:00', '2015-11-02 00:00:00');

-- 秒杀成功明细表,用户登陆认证相关的信息.
create table if not exists `success_killed`(
  seckill_id bigint not null comment '秒杀商品的id',
  user_phone bigint not null comment '用户手机号',
  state tinyint not null default '0' comment '状态表示：-1表示无效,0表示成功,1,已经付款',
  create_time timestamp not null DEFAULT '0000-00-00 00:00:00' comment '创建时间',
  primary key(seckill_id, user_phone), -- 创建的是联合主键
  key idx_create_time(create_time)
)ENGINE=InnoDB default charset='utf8' comment = '秒杀成功明细表';

DROP TABLE IF EXISTS `file_upload`;
CREATE TABLE IF NOT EXISTS `file_upload`(
	`id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'PK',
	`file_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '文件名',
	`file_type`  VARCHAR(120) NOT NULL DEFAULT '' COMMENT '文件类型',
	`file_size` BIGINT NOT NULL DEFAULT '0' COMMENT '文件大小(字节)',
	`url`  VARCHAR(255) NOT NULL DEFAULT '' COMMENT '上传地址',
	`upload_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间'
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET='utf8' COMMENT= '文件上传表';

ALTER TABLE file_upload ADD COLUMN `typeId` INT(11) NOT NULL DEFAULT '0' COMMENT '类别目录 - 外键'  AFTER `id`;
ALTER TABLE file_upload ADD COLUMN `categoryId` INT(11) NOT NULL DEFAULT '0' COMMENT '分类目录 - 外键'  AFTER `id`;

DROP TABLE IF EXISTS `file_category`;
CREATE TABLE `file_category` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `typeId` INT(11) NOT NULL DEFAULT '0' COMMENT '目录ID - 外键',
   `name` VARCHAR(128) NOT NULL COMMENT '目录名称',
   `icon` VARCHAR(128) DEFAULT NULL COMMENT '图标',
   `url` VARCHAR(128) DEFAULT NULL COMMENT '访问url地址',
   `parentid` BIGINT(20) DEFAULT NULL COMMENT '父结点id',
   `parentids` VARCHAR(128) DEFAULT NULL COMMENT '父结点id列表串',
   `sortstring` VARCHAR(128) DEFAULT NULL COMMENT '排序号',
   `available` CHAR(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;