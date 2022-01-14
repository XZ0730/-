CREATE TABLE IF NOT EXISTS `goods_information`(
	`goods_id` INT(20) NOT NULL AUTO_INCREMENT COMMENT '商品号',
	`goods_name` VARCHAR(20) NOT NULL COMMENT '商品名称',
	`goods_price` FLOAT NOT NULL COMMENT '商品价格',
	PRIMARY KEY(`goods_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '招牌牛肉面', '23'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '玉米猪肉饺', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '阿帕茶', '3'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '典明粥', '5'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '原味龙利鱼粉', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '原味黑鱼粉', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '原味草鱼粉', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '韭菜猪肉饺', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '时蔬猪肉饺', '16'); 
INSERT INTO `order`.`goods_information` (`goods_id`, `goods_name`, `goods_price`) VALUES (NULL, '虾仁猪心饺', '16'); 

CREATE TABLE IF NOT EXISTS `manager`(
	`manager_name` VARCHAR(20) NOT NULL COMMENT '管理员账户',
	`password` VARCHAR(20) NOT NULL COMMENT '密码',
	PRIMARY KEY(`manager_name`)
)ENGINE=INNODB DEFAULT CHARSET=utf8
INSERT INTO `order`.`manager` (`manager_name`, `password`) VALUES ('ZX', '123456'); 


CREATE TABLE IF NOT EXISTS `order_information`(
	`order_id` INT(20) NOT NULL AUTO_INCREMENT COMMENT '订单号',
	`goods_id` INT(20) NOT NULL COMMENT '商品号',
	`order_time` DATE NOT NULL COMMENT '下单时间',
	PRIMARY KEY(`order_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8
