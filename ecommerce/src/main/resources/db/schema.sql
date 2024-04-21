-- CREATE DATABASE with IF EXISTS
CREATE DATABASE IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET latin1 */;

-- ecommerce.shops definition with IF NOT EXISTS
CREATE TABLE IF NOT EXISTS `ecommerce`.`shops` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) NOT NULL,
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `UKar5yyuartm46e1brh920fpfiv` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ecommerce.products definition with IF NOT EXISTS
CREATE TABLE IF NOT EXISTS `ecommerce`.`products` (
                                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                          `external_id` bigint(20) NOT NULL,
                                          `name` varchar(255) DEFAULT NULL,
                                          `price` double NOT NULL,
                                          `quantity` int(11) NOT NULL,
                                          `uen` varchar(255) NOT NULL,
                                          `shop_id` bigint(20) NOT NULL,
                                          PRIMARY KEY (`id`),
                                          KEY `FK7kp8sbhxboponhx3lxqtmkcoj` (`shop_id`),
                                          CONSTRAINT `FK7kp8sbhxboponhx3lxqtmkcoj` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- ecommerce.orders definition with IF NOT EXISTS
CREATE TABLE IF NOT EXISTS `ecommerce`.`orders` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `canceled_at` datetime(6) DEFAULT NULL,
                                        `order_reference_id` varchar(255) NOT NULL,
                                        `status` tinyint(4) NOT NULL,
                                        `submitted_at` datetime(6) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `UK_8c6j41wam8bdityym60maivdq` (`order_reference_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ecommerce.order_items definition with IF NOT EXISTS
CREATE TABLE IF NOT EXISTS `ecommerce`.`order_items` (
                                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                             `quantity` int(11) NOT NULL,
                                             `total_price` double DEFAULT NULL,
                                             `order_id` bigint(20) NOT NULL,
                                             `product_id` bigint(20) NOT NULL,
                                             PRIMARY KEY (`id`),
                                             KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
                                             KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
                                             CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                                             CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
