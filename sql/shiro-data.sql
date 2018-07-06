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

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`type`,`url`,`percode`,`parentid`,`parentids`,`sortstring`,`available`) values (1,'系统管理','menu','','',0,'0','0','1'),(11,'资源管理','menu','common/resource/index','res:view',1,'0/1/','1','1'),(12,'资源新增','perm','','res:add',11,'0/1/11/','2','1'),(13,'资源修改','perm','','res:update',11,'0/1/11/','','1'),(14,'资源删除','perm','','res:delete',11,'0/1/11/','','1'),(21,'角色管理','menu','common/role/index','role:view',1,'0/1/','2','1'),(22,'角色新增','perm','','role:add',21,'0/1/21/','','1'),(23,'角色修改','perm','','role:update',21,'0/1/21/','','1'),(24,'角色删除','perm','','role:delete',21,'0/1/21/','','1'),(26,'角色授权','perm','','role:auth',21,NULL,'','1'),(27,'资源查看','perm','','res:view',11,NULL,'','1'),(28,'角色查看','perm','','role:view',21,NULL,'','1'),(29,'用户管理','menu','common/user/index','user:view',1,NULL,'','1'),(30,'用户新增','perm','','user:add',29,NULL,'','1'),(31,'用户修改','perm','','user:update',29,NULL,'','1'),(32,'用户删除','perm','','user:delete',29,NULL,'','1'),(33,'用户查看','perm','','user:view',29,NULL,'','1'),(34,'用户详情','perm','','user:detail',29,NULL,'','1'),(35,'用户锁定','perm','','user:lock',29,NULL,'','1'),(36,'设置密码','perm','','user:setPWD',29,NULL,'','1'),(37,'重置密码','perm','','user:resetPWD',29,NULL,'','1'),(39,'商品管理','menu','','',0,NULL,'','1'),(40,'商品新增','perm','','prod:add',39,NULL,'','1');

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`type`,`available`,`addPerson`,`addTime`,`updatePerson`,`updateTime`) values (1,'管理员',3,'1','','2016-11-02 18:56:55','','2016-11-02 19:20:28'),(2,'用户管理员',1,'1','','2016-11-02 18:56:55','','2016-11-02 19:20:39'),(7,'部门管理员',2,'1','','2016-11-02 19:20:48','','0000-00-00 00:00:00'),(17,'测试角色',2,'1','','2016-11-03 17:43:48','','2016-11-04 15:01:37'),(18,'项目经理',2,'1','','2016-11-03 18:28:33','','0000-00-00 00:00:00');

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`sys_role_id`,`sys_permission_id`) values (205,'2','21'),(206,'2','28'),(207,'7','11'),(208,'7','12'),(209,'7','13'),(210,'7','27'),(211,'7','21'),(212,'7','22'),(213,'7','23'),(214,'7','28'),(215,'17','11'),(216,'17','14'),(217,'17','21'),(218,'17','22'),(219,'17','23'),(220,'17','28'),(221,'17','29'),(222,'17','33'),(223,'17','35'),(224,'17','36'),(225,'17','37'),(226,'18','11'),(227,'18','27'),(228,'18','29'),(229,'18','30'),(230,'1','1'),(231,'1','11'),(232,'1','12'),(233,'1','13'),(234,'1','14'),(235,'1','27'),(236,'1','21'),(237,'1','22'),(238,'1','23'),(239,'1','24'),(240,'1','26'),(241,'1','28'),(242,'1','29'),(243,'1','30'),(244,'1','31'),(245,'1','32'),(246,'1','33'),(247,'1','34'),(248,'1','35'),(249,'1','36'),(250,'1','37'),(251,'1','39'),(252,'1','40');

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`usercode`,`username`,`password`,`salt`,`locked`,`updateTime`) values (1,'test','测试账号','bf07fd8bbc73b6f70b8319f2ebb87483','uiwueylm','0','2016-11-04 15:30:20'),(2,'admin','管理员','48e1a118a99e59fb09254e42a0335bc8','eteokues','0','2016-11-04 11:45:08'),(11,'user','用户','fe81a5dc1dd37853c96cd031e998f994','037634197b3cf4919be3c089299f9478874fc47806924977','1','2016-11-04 16:16:24');

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`sys_user_id`,`sys_role_id`) values (211,'2','1'),(231,'1','17'),(232,'1','18'),(233,'11','18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
