-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.5-m3 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for hardware_system
CREATE DATABASE IF NOT EXISTS `hardware_system` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hardware_system`;

-- Dumping structure for table hardware_system.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.brand: ~15 rows (approximately)
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` (`id`, `name`) VALUES
	(1, 'Lanva'),
	(2, 'Dulux'),
	(3, 'HILTI'),
	(4, 'EL TORO'),
	(5, 'Holcim'),
	(6, 'Insee'),
	(7, 'JAT'),
	(8, 'Kelani Cables'),
	(9, 'Melwa'),
	(10, 'Orange'),
	(11, 'Polycrome'),
	(12, 'Rhino'),
	(13, 'Rebco Fence Link'),
	(14, 'Rocell'),
	(15, 'Nestle');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;

-- Dumping structure for table hardware_system.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.category: ~12 rows (approximately)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Personal Protective  Equipment'),
	(2, 'House Hold Cleaning Product'),
	(3, 'Safety Items'),
	(4, 'Tools and Accessories'),
	(5, 'BNS Natural Pebbles'),
	(6, 'House Hold Items'),
	(7, 'Home Accessories'),
	(8, 'Building Materials'),
	(9, 'Roofing'),
	(10, 'Steel'),
	(11, 'Fencing'),
	(12, 'Electric');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- Dumping structure for table hardware_system.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.city: ~4 rows (approximately)
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` (`id`, `name`) VALUES
	(1, 'Colombo'),
	(2, 'Gampaha'),
	(3, 'Rathnapura'),
	(4, 'Sandalankawa');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- Dumping structure for table hardware_system.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.company: ~7 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `contact_number`) VALUES
	(1, 'Nestle', '0773423987'),
	(2, 'S-lon Lanka (Pvt) Ltd', '0114760100'),
	(3, 'Orange Electric Factory', '0114792100'),
	(4, 'Ceylon Steel Corporation Limited', '0711469999'),
	(5, 'Dulux', '0112368618'),
	(6, 'Hilti', '0711413644'),
	(7, 'Holcim (Lanka) Ltd', '0117800800');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Dumping structure for table hardware_system.company_branch
CREATE TABLE IF NOT EXISTS `company_branch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `branch_contact_number` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `company_branch_address_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_branch_company1_idx` (`company_id`),
  KEY `fk_company_branch_company_branch_address1_idx` (`company_branch_address_id`),
  CONSTRAINT `fk_company_branch_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_company_branch_company_branch_address1` FOREIGN KEY (`company_branch_address_id`) REFERENCES `company_branch_address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.company_branch: ~5 rows (approximately)
/*!40000 ALTER TABLE `company_branch` DISABLE KEYS */;
INSERT INTO `company_branch` (`id`, `company_id`, `branch_contact_number`, `name`, `company_branch_address_id`) VALUES
	(1, 2, '0374699100', 'Sandalankawa-S-Lon Lanka (Pvt) Ltd', 1),
	(2, 3, '0115706706', 'Orange IT- Meegoda', 2),
	(3, 1, '0112699991', 'Nestle Lanka PLC Colombo', 3),
	(4, 4, '0711469999', 'Lanwa Ceylon Steel Corparation-Oruwala', 4),
	(5, 6, '0711413644', 'Hilti-Nugegoda', 5);
/*!40000 ALTER TABLE `company_branch` ENABLE KEYS */;

-- Dumping structure for table hardware_system.company_branch_address
CREATE TABLE IF NOT EXISTS `company_branch_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line1` text NOT NULL,
  `line2` text NOT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_branch_address_city1_idx` (`city_id`),
  CONSTRAINT `fk_company_branch_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.company_branch_address: ~5 rows (approximately)
/*!40000 ALTER TABLE `company_branch_address` DISABLE KEYS */;
INSERT INTO `company_branch_address` (`id`, `line1`, `line2`, `city_id`) VALUES
	(1, 'Rata Mada Paara,', 'Sandalankawa 60176', 4),
	(2, 'Nippon Building,', 'Naduhena Meegoda,', 3),
	(3, 'NO-440', 'T.B. Jaya Mawatha, Colombo 00100', 1),
	(4, '10150', 'oruwala,', 1),
	(5, 'NO-207,', 'Nawala Rd, Nugegoda,', 1);
/*!40000 ALTER TABLE `company_branch_address` ENABLE KEYS */;

-- Dumping structure for table hardware_system.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_city1_idx` (`city_id`),
  CONSTRAINT `fk_customer_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.customer: ~3 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `contact_number`, `city_id`) VALUES
	(0, 'None', 'None', 1),
	(1, 'janidu', '0775676567', 2),
	(2, 'Milinda', '0778965456', 4);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table hardware_system.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `unique_id` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  KEY `fk_grn_user1_idx` (`user_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grn_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.grn: ~9 rows (approximately)
/*!40000 ALTER TABLE `grn` DISABLE KEYS */;
INSERT INTO `grn` (`id`, `supplier_id`, `date_time`, `user_id`, `unique_id`) VALUES
	(7, 2, '2022-06-29 17:02:14', 1, '1656502334689-1'),
	(8, 3, '2022-06-29 20:15:45', 1, '1656513945686-1'),
	(9, 4, '2022-06-29 20:28:03', 1, '1656514683383-1'),
	(10, 2, '2022-06-29 20:39:02', 1, '1656515342361-1'),
	(11, 2, '2022-06-30 18:51:57', 1, '1656595317953-1'),
	(12, 3, '2022-07-12 09:53:24', 1, '1657599804441-1'),
	(13, 1, '2022-07-20 00:42:19', 1, '1658257939238-1'),
	(14, 2, '2022-07-31 17:06:21', 1, '1659267381464-1'),
	(15, 6, '2022-08-01 00:18:07', 1, '1659293287151-1');
/*!40000 ALTER TABLE `grn` ENABLE KEYS */;

-- Dumping structure for table hardware_system.grn_item
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `qty_type_id` int(11) NOT NULL,
  `buying_price` double NOT NULL,
  `grn_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  KEY `fk_grn_item_stock1_idx` (`stock_id`),
  KEY `fk_grn_item_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grn_item_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grn_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.grn_item: ~13 rows (approximately)
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
INSERT INTO `grn_item` (`id`, `quantity`, `qty_type_id`, `buying_price`, `grn_id`, `stock_id`) VALUES
	(7, 10, 11, 1000, 7, 2),
	(8, 20, 11, 500, 7, 6),
	(9, 50, 11, 300, 8, 7),
	(10, 20, 11, 300, 8, 8),
	(11, 20, 11, 800, 9, 9),
	(12, 15, 11, 900, 9, 10),
	(13, 20, 11, 400, 10, 11),
	(14, 4, 11, 1000, 11, 12),
	(15, 20, 11, 500, 11, 13),
	(16, 20, 11, 300, 12, 14),
	(17, 10, 11, 1100, 13, 15),
	(18, 50, 11, 300, 14, 16),
	(19, 10, 11, 1500, 15, 17);
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;

-- Dumping structure for table hardware_system.grn_payment
CREATE TABLE IF NOT EXISTS `grn_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_type_id` int(11) NOT NULL,
  `grn_id` int(11) NOT NULL,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_payment_payment_type1_idx` (`payment_type_id`),
  KEY `fk_grn_payment_grn1_idx` (`grn_id`),
  CONSTRAINT `fk_grn_payment_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grn_payment_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.grn_payment: ~9 rows (approximately)
/*!40000 ALTER TABLE `grn_payment` DISABLE KEYS */;
INSERT INTO `grn_payment` (`id`, `payment_type_id`, `grn_id`, `payment`, `balance`) VALUES
	(7, 2, 7, 20000, 0),
	(8, 2, 8, 21000, 0),
	(9, 2, 9, 29500, 0),
	(10, 2, 10, 8000, 0),
	(11, 2, 11, 8500, 0),
	(12, 2, 12, 6000, 0),
	(13, 2, 13, 11000, 0),
	(14, 2, 14, 9000, 0),
	(15, 2, 15, 15000, 0);
/*!40000 ALTER TABLE `grn_payment` ENABLE KEYS */;

-- Dumping structure for table hardware_system.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `unique_id` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_customer1_idx` (`customer_id`),
  KEY `fk_invoice_user1_idx` (`user_id`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.invoice: ~9 rows (approximately)
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` (`id`, `customer_id`, `date_time`, `user_id`, `unique_id`) VALUES
	(10, 0, '2022-06-29 20:50:09', 1, '1656516009129-1'),
	(11, 0, '2022-07-03 11:22:39', 1, '1656827559063-1'),
	(12, 0, '2022-07-20 00:38:52', 1, '1658257732971-1'),
	(13, 2, '2022-07-20 12:01:23', 1, '1658298683983-1'),
	(14, 0, '2022-07-21 11:01:32', 1, '1658381492692-1'),
	(15, 0, '2022-07-21 11:07:10', 1, '1658381830821-1'),
	(16, 0, '2022-07-23 01:24:43', 1, '1658519683604-1'),
	(17, 1, '2022-07-31 17:10:40', 1, '1659267640101-1'),
	(18, 0, '2022-08-01 00:20:10', 1, '1659293410063-1');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping structure for table hardware_system.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_id` int(11) NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `qty_type_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_stock1_idx` (`stock_id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_item_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.invoice_item: ~14 rows (approximately)
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
INSERT INTO `invoice_item` (`id`, `stock_id`, `qty`, `qty_type_id`, `invoice_id`) VALUES
	(7, 11, 1, 11, 10),
	(8, 7, 2, 11, 10),
	(9, 6, 2, 11, 10),
	(10, 7, 2, 11, 11),
	(11, 10, 2, 11, 12),
	(12, 12, 1, 11, 12),
	(13, 11, 2, 11, 13),
	(14, 12, 1, 11, 13),
	(15, 11, 1, 11, 14),
	(16, 15, 1, 11, 15),
	(17, 7, 1, 11, 16),
	(18, 11, 6, 11, 17),
	(19, 15, 5, 11, 17),
	(20, 10, 3, 11, 18);
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;

-- Dumping structure for table hardware_system.invoice_payment
CREATE TABLE IF NOT EXISTS `invoice_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) NOT NULL,
  `payment_type_id` int(11) NOT NULL,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_payment_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_payment_payment_type1_idx` (`payment_type_id`),
  CONSTRAINT `fk_invoice_payment_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_payment_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.invoice_payment: ~9 rows (approximately)
/*!40000 ALTER TABLE `invoice_payment` DISABLE KEYS */;
INSERT INTO `invoice_payment` (`id`, `invoice_id`, `payment_type_id`, `payment`, `balance`) VALUES
	(9, 10, 2, 2500, 0),
	(10, 11, 2, 800, 0),
	(11, 12, 2, 3100, 0),
	(12, 13, 2, 2100, 0),
	(13, 14, 2, 500, 0),
	(14, 15, 2, 1200, 0),
	(15, 16, 2, 400, 0),
	(16, 17, 2, 9000, 0),
	(17, 18, 2, 3000, 0);
/*!40000 ALTER TABLE `invoice_payment` ENABLE KEYS */;

-- Dumping structure for table hardware_system.item_rent
CREATE TABLE IF NOT EXISTS `item_rent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rental_item_stock_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `rent_price_per_day` double NOT NULL,
  `rent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_rent_rental_item_stock1_idx` (`rental_item_stock_id`),
  KEY `fk_item_rent_rent1_idx` (`rent_id`),
  CONSTRAINT `fk_item_rent_rent1` FOREIGN KEY (`rent_id`) REFERENCES `rent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_rent_rental_item_stock1` FOREIGN KEY (`rental_item_stock_id`) REFERENCES `rental_item_stock` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.item_rent: ~15 rows (approximately)
/*!40000 ALTER TABLE `item_rent` DISABLE KEYS */;
INSERT INTO `item_rent` (`id`, `rental_item_stock_id`, `qty`, `rent_price_per_day`, `rent_id`) VALUES
	(1, 1, 1, 100, 1),
	(2, 1, 1, 100, 4),
	(3, 1, 1, 100, 5),
	(4, 1, 2, 200, 6),
	(5, 1, 1, 100, 7),
	(6, 1, 1, 100, 8),
	(7, 1, 2, 200, 9),
	(8, 1, 2, 200, 10),
	(9, 1, 1, 100, 11),
	(10, 1, 1, 100, 12),
	(11, 1, 1, 100, 13),
	(12, 1, 2, 200, 14),
	(13, 2, 1, 100, 15),
	(14, 1, 1, 100, 16),
	(15, 2, 1, 100, 16);
/*!40000 ALTER TABLE `item_rent` ENABLE KEYS */;

-- Dumping structure for table hardware_system.payment_type
CREATE TABLE IF NOT EXISTS `payment_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.payment_type: ~4 rows (approximately)
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
INSERT INTO `payment_type` (`id`, `name`) VALUES
	(1, 'Card'),
	(2, 'Cash'),
	(3, 'Check'),
	(4, 'Credits');
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;

-- Dumping structure for table hardware_system.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `brand_id` int(11) NOT NULL,
  `name` text,
  PRIMARY KEY (`id`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_brand1_idx` (`brand_id`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.product: ~14 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `category_id`, `brand_id`, `name`) VALUES
	(2, 2, 15, 'Hand Gloves'),
	(3, 1, 15, 'Gum boot'),
	(4, 2, 15, 'Citrenella Oil 100ml'),
	(5, 1, 15, 'Hand wash 100ml'),
	(6, 1, 15, 'BK5 Multi Surface cleaner'),
	(7, 3, 15, 'Construction Safety Helmet'),
	(8, 3, 15, 'Overrall kit'),
	(9, 12, 10, 'Orange CFL 2U Print Type'),
	(10, 12, 10, 'Orange CFL 2U Screw Type'),
	(11, 12, 10, 'Orange CFL Full Spiral Pin Type'),
	(12, 10, 1, 'Lanwa Steel QT Bars'),
	(13, 10, 1, 'GI Pipes (DN 15)'),
	(14, 10, 1, 'GI Pipes (DN 25)'),
	(15, 1, 15, 'Hand wash 200ml');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table hardware_system.qty_type
CREATE TABLE IF NOT EXISTS `qty_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.qty_type: ~6 rows (approximately)
/*!40000 ALTER TABLE `qty_type` DISABLE KEYS */;
INSERT INTO `qty_type` (`id`, `name`) VALUES
	(1, 'kg'),
	(2, 'g'),
	(3, 'leter'),
	(4, 'ml'),
	(10, 'Cube'),
	(11, 'unit');
/*!40000 ALTER TABLE `qty_type` ENABLE KEYS */;

-- Dumping structure for table hardware_system.rent
CREATE TABLE IF NOT EXISTS `rent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `rent_date` date NOT NULL,
  `receve_date` date DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `stat` int(11) NOT NULL,
  `unique_id` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rent_customer1_idx` (`customer_id`),
  KEY `fk_rent_user1_idx` (`user_id`),
  CONSTRAINT `fk_rent_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rent_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.rent: ~14 rows (approximately)
/*!40000 ALTER TABLE `rent` DISABLE KEYS */;
INSERT INTO `rent` (`id`, `customer_id`, `rent_date`, `receve_date`, `user_id`, `stat`, `unique_id`) VALUES
	(1, 1, '2022-06-26', '2022-06-28', 1, 1, '1656260374223'),
	(4, 2, '2022-06-26', '2022-06-28', 1, 1, '1656262720944'),
	(5, 1, '2022-06-27', '2022-06-28', 1, 1, '1656333761545'),
	(6, 2, '2022-06-28', '2022-07-06', 1, 1, '1656360349599'),
	(7, 1, '2022-06-28', '2022-06-29', 1, 1, '1656361371546'),
	(8, 1, '2022-06-28', '2022-07-06', 1, 1, '1656362113665'),
	(9, 2, '2022-06-28', '2022-07-06', 1, 1, '1657083969299'),
	(10, 2, '2022-06-30', '2022-07-06', 1, 1, '1657084961181'),
	(11, 1, '2022-07-06', '2022-07-20', 1, 1, '1657089748522'),
	(12, 2, '2022-07-06', '2022-07-10', 1, 1, '1657089775044'),
	(13, 2, '2022-07-20', '2022-07-20', 1, 1, '1658256345161'),
	(14, 2, '2022-07-20', '2022-08-01', 1, 1, '1658298847525'),
	(15, 1, '2022-07-27', '2022-07-27', 1, 1, '1658907137977'),
	(16, 1, '2022-07-30', NULL, 1, 0, '1659196757567');
/*!40000 ALTER TABLE `rent` ENABLE KEYS */;

-- Dumping structure for table hardware_system.rental_item_stock
CREATE TABLE IF NOT EXISTS `rental_item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `qty` int(11) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.rental_item_stock: ~2 rows (approximately)
/*!40000 ALTER TABLE `rental_item_stock` DISABLE KEYS */;
INSERT INTO `rental_item_stock` (`id`, `name`, `qty`, `description`) VALUES
	(1, 'Dizainlife Drill', 3, 'Keyless Drill Chuck hex and Round Shank Adapter'),
	(2, 'Small Chain Saw', 1, 'no');
/*!40000 ALTER TABLE `rental_item_stock` ENABLE KEYS */;

-- Dumping structure for table hardware_system.rental_payment
CREATE TABLE IF NOT EXISTS `rental_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment` varchar(45) DEFAULT NULL,
  `balance` varchar(45) DEFAULT NULL,
  `rent_id` int(11) NOT NULL,
  `payment_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rental_payment_rent1_idx` (`rent_id`),
  KEY `fk_rental_payment_payment_type1_idx` (`payment_type_id`),
  CONSTRAINT `fk_rental_payment_payment_type1` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_payment_rent1` FOREIGN KEY (`rent_id`) REFERENCES `rent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.rental_payment: ~13 rows (approximately)
/*!40000 ALTER TABLE `rental_payment` DISABLE KEYS */;
INSERT INTO `rental_payment` (`id`, `payment`, `balance`, `rent_id`, `payment_type_id`) VALUES
	(1, '200', '0.00', 1, 2),
	(2, '100', '0.00', 5, 2),
	(3, '200', '0.00', 4, 2),
	(4, '100', '0.00', 7, 2),
	(5, '1600', '0.00', 6, 2),
	(6, '800', '0.00', 8, 2),
	(7, '1600', '0.00', 9, 2),
	(8, '1200', '0.00', 10, 2),
	(9, '400', '0.00', 12, 2),
	(10, '1400', '0.00', 11, 2),
	(11, '100', '0.00', 13, 2),
	(12, '100', '0.00', 15, 2),
	(13, '2400', '0.00', 14, 2);
/*!40000 ALTER TABLE `rental_payment` ENABLE KEYS */;

-- Dumping structure for table hardware_system.return_status
CREATE TABLE IF NOT EXISTS `return_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.return_status: ~2 rows (approximately)
/*!40000 ALTER TABLE `return_status` DISABLE KEYS */;
INSERT INTO `return_status` (`id`, `name`) VALUES
	(1, 'Added'),
	(2, 'Not Added');
/*!40000 ALTER TABLE `return_status` ENABLE KEYS */;

-- Dumping structure for table hardware_system.return_stock
CREATE TABLE IF NOT EXISTS `return_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qty` int(11) NOT NULL,
  `date` varchar(45) DEFAULT NULL,
  `stock_id` int(11) NOT NULL,
  `return_status_id` int(11) NOT NULL,
  `qty_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_return_stock_stock1_idx` (`stock_id`),
  KEY `fk_return_stock_return_status1_idx` (`return_status_id`),
  KEY `fk_return_stock_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_return_stock_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_return_stock_return_status1` FOREIGN KEY (`return_status_id`) REFERENCES `return_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_return_stock_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.return_stock: ~4 rows (approximately)
/*!40000 ALTER TABLE `return_stock` DISABLE KEYS */;
INSERT INTO `return_stock` (`id`, `qty`, `date`, `stock_id`, `return_status_id`, `qty_type_id`) VALUES
	(1, 4, '2022-06-30 02:09:04', 2, 1, 11),
	(2, 3, '2022-06-30 02:23:49', 6, 1, 11),
	(3, 6, '2022-07-20 10:22:24', 2, 1, 11),
	(4, 10, '2022-07-20 10:34:59', 9, 1, 11);
/*!40000 ALTER TABLE `return_stock` ENABLE KEYS */;

-- Dumping structure for view hardware_system.selling_qty
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `selling_qty` (
	`name` TEXT NULL COLLATE 'utf8_general_ci',
	`qty` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for table hardware_system.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `mfd` date DEFAULT NULL,
  `exd` date DEFAULT NULL,
  `qty_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  KEY `fk_stock_qty_type1_idx` (`qty_type_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_qty_type1` FOREIGN KEY (`qty_type_id`) REFERENCES `qty_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.stock: ~13 rows (approximately)
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` (`id`, `product_id`, `quantity`, `selling_price`, `mfd`, `exd`, `qty_type_id`) VALUES
	(2, 3, 0, 1100, '2022-06-01', '3022-06-01', 11),
	(6, 2, 15, 600, '2022-06-01', '3022-06-01', 11),
	(7, 9, 20, 400, '2022-06-09', '2022-07-25', 11),
	(8, 10, 20, 400, '2022-06-08', '2022-08-04', 11),
	(9, 12, 10, 900, '2022-06-01', '2032-06-01', 11),
	(10, 14, 10, 1000, '2022-06-01', '2032-06-01', 11),
	(11, 4, 10, 500, '2022-06-01', '2023-11-21', 11),
	(12, 3, 2, 1100, '2022-06-01', '2032-06-01', 11),
	(13, 2, 20, 600, '2022-06-01', '2032-06-01', 11),
	(14, 10, 20, 350, '2022-07-01', '2032-07-02', 11),
	(15, 7, 4, 1200, '2022-07-01', '2032-07-02', 11),
	(16, 15, 50, 350, '2022-07-01', '2032-07-02', 11),
	(17, 7, 10, 1600, '2022-08-01', '2032-08-10', 11);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;

-- Dumping structure for table hardware_system.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  `company_branch_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_company_branch1_idx` (`company_branch_id`),
  CONSTRAINT `fk_supplier_company_branch1` FOREIGN KEY (`company_branch_id`) REFERENCES `company_branch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.supplier: ~6 rows (approximately)
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`id`, `name`, `contact_number`, `email`, `company_branch_id`) VALUES
	(1, 'Anura', '0778734690', 'anura@gmail.com', 1),
	(2, 'Ruwan', '0779845876', 'ruwan@gmail.com', 3),
	(3, 'Janith', '0773847657', 'janith@gmail.com', 2),
	(4, 'Sujith', '0778967456', 'sujith@gmail.com', 4),
	(5, 'Dineth', '0775654867', 'dineth@gmail.com', 5),
	(6, 'Nimal', '0779876456', 'nimal@gmail.com', 3);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;

-- Dumping structure for table hardware_system.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `contact_number` varchar(10) NOT NULL,
  `usertype_id` int(11) NOT NULL,
  `user_status_id` int(11) NOT NULL DEFAULT '1',
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_usertype_idx` (`usertype_id`),
  KEY `fk_user_user_status1_idx` (`user_status_id`),
  KEY `fk_user_city1_idx` (`city_id`),
  CONSTRAINT `fk_user_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_usertype` FOREIGN KEY (`usertype_id`) REFERENCES `user_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_status1` FOREIGN KEY (`user_status_id`) REFERENCES `user_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.user: ~5 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`, `username`, `password`, `contact_number`, `usertype_id`, `user_status_id`, `city_id`) VALUES
	(1, 'Oshada', 'oshada', '123', '0774956789', 1, 1, 2),
	(2, 'Dasun', 'dasun', '1100', '0771234234', 1, 1, 4),
	(3, 'Nimal', 'nimal', 'aaa', '0775645345', 2, 1, 3),
	(4, 'Kamal', 'kamal', '0000', '0773423543', 2, 1, 4),
	(5, 'Sadun', 'sadun', '111', '0774565345', 2, 1, 2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table hardware_system.user_status
CREATE TABLE IF NOT EXISTS `user_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.user_status: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Inactive');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;

-- Dumping structure for table hardware_system.user_type
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hardware_system.user_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` (`id`, `name`) VALUES
	(1, 'Admin'),
	(2, 'Cashier');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;

-- Dumping structure for view hardware_system.selling_qty
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `selling_qty`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `selling_qty` AS SELECT 
        `product`.`name` AS `name`, `invoice_item`.`qty` AS `qty`
    FROM
        (((`invoice_item`
        JOIN `invoice` ON ((`invoice_item`.`invoice_id` = `invoice`.`id`)))
        JOIN `stock` ON ((`invoice_item`.`stock_id` = `stock`.`id`)))
        JOIN `product` ON ((`stock`.`product_id` = `product`.`id`)))
    WHERE
        ((`invoice`.`date_time` >= '2022-06-03')
            AND (`invoice`.`date_time` <= '2022-07-04')) ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
