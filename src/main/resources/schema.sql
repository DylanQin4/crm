-- CREATE DATABASE  IF NOT EXISTS `crm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `crm`;

-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: crm
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `users`
----

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `email` varchar(100) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    `hire_date` datetime DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `username` varchar(50) NOT NULL,
    `status` varchar(100) DEFAULT NULL,
    `token` varchar(500) DEFAULT NULL,
    `is_password_set` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_users`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `oauth_users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int DEFAULT NULL,
    `access_token` varchar(255) NOT NULL,
    `access_token_issued_at` datetime NOT NULL,
    `access_token_expiration` datetime NOT NULL,
    `refresh_token` varchar(255) NOT NULL,
    `refresh_token_issued_at` datetime NOT NULL,
    `refresh_token_expiration` datetime DEFAULT NULL,
    `granted_scopes` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    KEY `oauth_users_ibfk_1` (`user_id`),
    CONSTRAINT `oauth_users_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_profile`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `user_profile` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `first_name` varchar(50) DEFAULT NULL,
    `last_name` varchar(50) DEFAULT NULL,
    `phone` varchar(50) DEFAULT NULL,
    `department` varchar(255) DEFAULT NULL,
    `salary` decimal(10,2) DEFAULT NULL,
    `status` varchar(50) DEFAULT NULL,
    `oauth_user_image_link` varchar(255) DEFAULT NULL,
    `user_image` blob,
    `bio` text,
    `youtube` varchar(255) DEFAULT NULL,
    `twitter` varchar(255) DEFAULT NULL,
    `facebook` varchar(255) DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `country` varchar(100) DEFAULT NULL,
    `position` varchar(100) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `user_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `roles` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;

INSERT IGNORE INTO `roles` (id, name)
VALUES (1, 'ROLE_MANAGER'), (2, 'ROLE_EMPLOYEE'), (3, 'ROLE_CUSTOMER')
ON DUPLICATE KEY UPDATE id = id;

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `user_roles` (
    `user_id` int NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    KEY `role_id` (`role_id`),
    CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `employee` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(45) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name` varchar(45) NOT NULL,
    `email` varchar(45) NOT NULL,
    `password` varchar(80) NOT NULL,
    `provider` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_template`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `email_template` (
    `template_id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `content` text,
    `user_id` int DEFAULT NULL,
    `json_design` text,
    `created_at` datetime DEFAULT NULL,
    PRIMARY KEY (`template_id`),
    UNIQUE KEY `name` (`name`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `email_template_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_login_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `customer_login_info` (
    `id` int NOT NULL AUTO_INCREMENT,
    `password` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `token` varchar(500) DEFAULT NULL,
    `password_set` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `customer` (
    `customer_id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `phone` varchar(20) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `state` varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `description` text,
    `position` varchar(255) DEFAULT NULL,
    `twitter` varchar(255) DEFAULT NULL,
    `facebook` varchar(255) DEFAULT NULL,
    `youtube` varchar(255) DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `profile_id` int DEFAULT NULL,
    PRIMARY KEY (`customer_id`),
    KEY `user_id` (`user_id`),
    KEY `profile_id` (`profile_id`),
    CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`profile_id`) REFERENCES `customer_login_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trigger_lead`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `trigger_lead` (
    `lead_id` int unsigned NOT NULL AUTO_INCREMENT,
    `customer_id` int unsigned NOT NULL,
    `user_id` int DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `phone` varchar(20) DEFAULT NULL,
    `employee_id` int DEFAULT NULL,
    `status` varchar(50) DEFAULT NULL,
    `meeting_id` varchar(255) DEFAULT NULL,
    `google_drive` tinyint(1) DEFAULT NULL,
    `google_drive_folder_id` varchar(255) DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    PRIMARY KEY (`lead_id`),
    UNIQUE KEY `meeting_info` (`meeting_id`),
    KEY `customer_id` (`customer_id`),
    KEY `user_id` (`user_id`),
    KEY `employee_id` (`employee_id`),
    CONSTRAINT `trigger_lead_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    CONSTRAINT `trigger_lead_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `trigger_lead_ibfk_3` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trigger_ticket`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `trigger_ticket` (
    `ticket_id` int unsigned NOT NULL AUTO_INCREMENT,
    `subject` varchar(255) DEFAULT NULL,
    `description` text,
    `status` varchar(50) DEFAULT NULL,
    `priority` varchar(50) DEFAULT NULL,
    `customer_id` int unsigned NOT NULL,
    `manager_id` int DEFAULT NULL,
    `employee_id` int DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    PRIMARY KEY (`ticket_id`),
    KEY `fk_ticket_customer` (`customer_id`),
    KEY `fk_ticket_manager` (`manager_id`),
    KEY `fk_ticket_employee` (`employee_id`),
    CONSTRAINT `fk_ticket_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    CONSTRAINT `fk_ticket_employee` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_ticket_manager` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trigger_contract`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `trigger_contract` (
    `contract_id` int unsigned NOT NULL AUTO_INCREMENT,
    `subject` varchar(255) DEFAULT NULL,
    `status` varchar(100) DEFAULT NULL,
    `description` text,
    `start_date` date DEFAULT NULL,
    `end_date` date DEFAULT NULL,
    `amount` decimal(10,0) DEFAULT NULL,
    `google_drive` tinyint(1) DEFAULT NULL,
    `google_drive_folder_id` varchar(255) DEFAULT NULL,
    `lead_id` int unsigned DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `customer_id` int unsigned DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    PRIMARY KEY (`contract_id`),
    KEY `lead_id` (`lead_id`),
    KEY `user_id` (`user_id`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `trigger_contract_ibfk_1` FOREIGN KEY (`lead_id`) REFERENCES `trigger_lead` (`lead_id`),
    CONSTRAINT `trigger_contract_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `trigger_contract_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contract_settings`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `contract_settings` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `amount` tinyint(1) DEFAULT NULL,
    `subject` tinyint(1) DEFAULT NULL,
    `description` tinyint(1) DEFAULT NULL,
    `end_date` tinyint(1) DEFAULT NULL,
    `start_date` tinyint(1) DEFAULT NULL,
    `status` tinyint(1) DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `status_email_template` int unsigned DEFAULT NULL,
    `amount_email_template` int unsigned DEFAULT NULL,
    `subject_email_template` int unsigned DEFAULT NULL,
    `description_email_template` int unsigned DEFAULT NULL,
    `start_email_template` int unsigned DEFAULT NULL,
    `end_email_template` int unsigned DEFAULT NULL,
    `customer_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `status_email_template` (`status_email_template`),
    KEY `amount_email_template` (`amount_email_template`),
    KEY `subject_email_template` (`subject_email_template`),
    KEY `description_email_template` (`description_email_template`),
    KEY `start_email_template` (`start_email_template`),
    KEY `end_email_template` (`end_email_template`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `contract_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `contract_settings_ibfk_2` FOREIGN KEY (`status_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_3` FOREIGN KEY (`amount_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_4` FOREIGN KEY (`subject_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_5` FOREIGN KEY (`description_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_6` FOREIGN KEY (`start_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_7` FOREIGN KEY (`end_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `contract_settings_ibfk_8` FOREIGN KEY (`customer_id`) REFERENCES `customer_login_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lead_action`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `lead_action` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `lead_id` int unsigned NOT NULL,
    `action` varchar(255) DEFAULT NULL,
    `date_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `lead_id` (`lead_id`),
    CONSTRAINT `lead_action_ibfk_1` FOREIGN KEY (`lead_id`) REFERENCES `trigger_lead` (`lead_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `lead_settings`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `lead_settings` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `status` tinyint(1) DEFAULT NULL,
    `meeting` tinyint(1) DEFAULT NULL,
    `phone` tinyint(1) DEFAULT NULL,
    `name` tinyint(1) DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `status_email_template` int unsigned DEFAULT NULL,
    `phone_email_template` int unsigned DEFAULT NULL,
    `meeting_email_template` int unsigned DEFAULT NULL,
    `name_email_template` int unsigned DEFAULT NULL,
    `customer_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `status_email_template` (`status_email_template`),
    KEY `phone_email_template` (`phone_email_template`),
    KEY `meeting_email_template` (`meeting_email_template`),
    KEY `name_email_template` (`name_email_template`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `lead_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `lead_settings_ibfk_2` FOREIGN KEY (`status_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `lead_settings_ibfk_3` FOREIGN KEY (`phone_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `lead_settings_ibfk_4` FOREIGN KEY (`meeting_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `lead_settings_ibfk_5` FOREIGN KEY (`name_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `lead_settings_ibfk_6` FOREIGN KEY (`customer_id`) REFERENCES `customer_login_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;





--
-- Table structure for table `ticket_settings`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `ticket_settings` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `priority` tinyint(1) DEFAULT NULL,
    `subject` tinyint(1) DEFAULT NULL,
    `description` tinyint(1) DEFAULT NULL,
    `status` tinyint(1) DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `status_email_template` int unsigned DEFAULT NULL,
    `subject_email_template` int unsigned DEFAULT NULL,
    `priority_email_template` int unsigned DEFAULT NULL,
    `description_email_template` int unsigned DEFAULT NULL,
    `customer_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `status_email_template` (`status_email_template`),
    KEY `phone_email_template` (`subject_email_template`),
    KEY `priority_email_template` (`priority_email_template`),
    KEY `description_email_template` (`description_email_template`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `ticket_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `ticket_settings_ibfk_2` FOREIGN KEY (`status_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `ticket_settings_ibfk_3` FOREIGN KEY (`subject_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `ticket_settings_ibfk_4` FOREIGN KEY (`priority_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `ticket_settings_ibfk_5` FOREIGN KEY (`description_email_template`) REFERENCES `email_template` (`template_id`),
    CONSTRAINT `ticket_settings_ibfk_6` FOREIGN KEY (`customer_id`) REFERENCES `customer_login_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `file` (
    `file_id` int NOT NULL AUTO_INCREMENT,
    `file_name` varchar(100) DEFAULT NULL,
    `file_data` blob,
    `file_type` varchar(255) DEFAULT NULL,
    `lead_id` int unsigned DEFAULT NULL,
    `contract_id` int unsigned DEFAULT NULL,
    PRIMARY KEY (`file_id`),
    KEY `lead_id` (`lead_id`),
    KEY `contract_id` (`contract_id`),
    CONSTRAINT `file_ibfk_1` FOREIGN KEY (`lead_id`) REFERENCES `trigger_lead` (`lead_id`),
    CONSTRAINT `file_ibfk_2` FOREIGN KEY (`contract_id`) REFERENCES `trigger_contract` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `google_drive_file`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `google_drive_file` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `drive_file_id` varchar(255) DEFAULT NULL,
    `drive_folder_id` varchar(255) DEFAULT NULL,
    `lead_id` int unsigned DEFAULT NULL,
    `contract_id` int unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `lead_id` (`lead_id`),
    KEY `contract_id` (`contract_id`),
    CONSTRAINT `google_drive_file_ibfk_1` FOREIGN KEY (`lead_id`) REFERENCES `trigger_lead` (`lead_id`),
    CONSTRAINT `google_drive_file_ibfk_2` FOREIGN KEY (`contract_id`) REFERENCES `trigger_contract` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


CREATE TABLE budgets(
    id INT AUTO_INCREMENT,
    budget DECIMAL(18,2) NOT NULL,
    date_min DATE,
    date_max DATE,
    designation VARCHAR(250),
    status ENUM('ACTIVE', 'INACTIVE', 'SOLD_OUT') DEFAULT 'ACTIVE',
    customer_id INT UNSIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE expenses(
    id INT AUTO_INCREMENT,
    amount DECIMAL(18,2) NOT NULL,
    label VARCHAR(250),
    date_expense DATE,
    ticket_id INT UNSIGNED,
    lead_id INT UNSIGNED,
    budget_id INT UNSIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(ticket_id) REFERENCES trigger_ticket(ticket_id),
    FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
);
ALTER TABLE expenses
ADD CONSTRAINT check_ticket_or_lead
CHECK (
    ((ticket_id IS NULL) AND (lead_id IS NOT NULL)) OR
    ((ticket_id IS NOT NULL) AND (lead_id IS NULL))
);
-- Add unique constraints to ensure one-to-one relations
ALTER TABLE expenses
ADD CONSTRAINT unique_ticket_expense UNIQUE (ticket_id),
ADD CONSTRAINT unique_lead_expense UNIQUE (lead_id);

ALTER TABLE expenses
    DROP FOREIGN KEY expenses_ibfk_1;
ALTER TABLE expenses
ADD CONSTRAINT expenses_ibfk_1
FOREIGN KEY(ticket_id) REFERENCES trigger_ticket(ticket_id)
ON DELETE CASCADE;

ALTER TABLE expenses
    DROP FOREIGN KEY expenses_ibfk_2;
ALTER TABLE expenses
ADD CONSTRAINT expenses_ibfk_2
FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
ON DELETE CASCADE;


CREATE TABLE alert_rate(
    id INT AUTO_INCREMENT,
    rate DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);
INSERT INTO alert_rate (rate) VALUES (0.8); -- 80%



CREATE VIEW budget_customer AS
SELECT
    b.id AS budget_id,
    b.designation,
    b.date_min,
    b.date_max,
    b.status,
    c.customer_id,
    c.name AS customer_name,
    b.budget,
    b.budget - COALESCE(SUM(e.amount), 0) AS remaining_budget
FROM budgets b
         JOIN crm.customer c ON b.customer_id = c.customer_id
         LEFT JOIN expenses e ON e.budget_id = b.id
GROUP BY
    b.id,
    b.designation,
    b.date_min,
    b.date_max,
    b.status,
    c.customer_id,
    c.name,
    b.budget;


CREATE VIEW total_budget_customer AS
SELECT
    customer.customer_id,
    customer.name,
    COALESCE(SUM(b.budget), 0) AS total_budget,
    COALESCE(SUM(b.budget) - COALESCE(SUM(exp.amount), 0), 0) AS total_remaining_budget
FROM customer
     LEFT JOIN budgets b ON b.customer_id = customer.customer_id
     LEFT JOIN expenses exp ON exp.budget_id = b.id
GROUP BY customer.customer_id, customer.name;


# VIEWS GRAPH customer
DROP VIEW IF EXISTS customer_date_range;
CREATE OR REPLACE VIEW customer_date_range AS
SELECT
    DATE_FORMAT(MIN(created_at), '%Y-%m-01') AS date_start,
    DATE_FORMAT(MAX(created_at), '%Y-%m-01') AS date_end
FROM customer
WHERE created_at IS NOT NULL;

DROP VIEW IF EXISTS customer_all_months;
CREATE OR REPLACE VIEW customer_all_months AS
WITH RECURSIVE all_months AS (
    SELECT date_start AS month_date FROM customer_date_range
    UNION ALL
    SELECT DATE_ADD(month_date, INTERVAL 1 MONTH)
    FROM all_months, customer_date_range
    WHERE month_date < customer_date_range.date_end
)
SELECT
    month_date,
    YEAR(month_date) AS year,
    MONTH(month_date) AS month,
    DATE_FORMAT(month_date, '%Y-%m') AS period
FROM all_months;

DROP VIEW IF EXISTS customer_monthly_stats;
CREATE OR REPLACE VIEW customer_monthly_stats AS
SELECT
    DATE_FORMAT(created_at, '%Y-%m') AS period,
    YEAR(created_at) AS year,
    MONTH(created_at) AS month,
    COUNT(*) AS new_customers
FROM
    customer
WHERE
    created_at IS NOT NULL
GROUP BY
    period, year, month;

DROP VIEW IF EXISTS v_customer_evolution_stats;
CREATE OR REPLACE VIEW v_customer_evolution_stats AS
SELECT
    am.year,
    am.month,
    am.period,
    COALESCE(ms.new_customers, 0) AS new_customers,
    SUM(COALESCE(ms.new_customers, 0)) OVER (ORDER BY am.year, am.month) AS cumulative_customers
FROM
    customer_all_months am
        LEFT JOIN
    customer_monthly_stats ms ON am.period = ms.period
ORDER BY
    am.year, am.month;

SELECT * FROM v_customer_evolution_stats;


# VIEWS GRAPH TICKET
-- View 1: All possible ticket statuses <= Entity Ticket
CREATE OR REPLACE VIEW v_ticket_all_statuses AS
SELECT 'open' AS status UNION ALL
SELECT 'assigned' UNION ALL
SELECT 'on-hold' UNION ALL
SELECT 'in-progress' UNION ALL
SELECT 'resolved' UNION ALL
SELECT 'closed' UNION ALL
SELECT 'reopened' UNION ALL
SELECT 'pending-customer-response' UNION ALL
SELECT 'escalated' UNION ALL
SELECT 'archived';

-- View 2: Actual ticket status counts
CREATE OR REPLACE VIEW v_ticket_status_counts AS
SELECT
    status,
    COUNT(*) AS count
FROM
    trigger_ticket
WHERE
    status IS NOT NULL
GROUP BY
    status;

-- View 3: Final distribution view combining counts and calculating percentages
CREATE OR REPLACE VIEW v_ticket_status_distribution AS
SELECT
    a.status,
    COALESCE(s.count, 0) AS count,
    ROUND(
        COALESCE(s.count, 0) * 100.0 /
        NULLIF((SELECT COUNT(*) FROM trigger_ticket WHERE status IS NOT NULL), 0),
        2
    ) AS percentage
FROM
    v_ticket_all_statuses a
        LEFT JOIN
    v_ticket_status_counts s ON a.status = s.status
ORDER BY
    count DESC, a.status;

# SELECT * FROM v_ticket_status_distribution;


# VIEWS GRAPH LEAD
# Ce graphique montre l'évolution dans le temps des tickets créés, des leads générés et des dépenses associées,
# permettant d'identifier des tendances saisonnières ou des corrélations
-- Vue 1: Déterminer la plage de dates totale pour tous les éléments
CREATE OR REPLACE VIEW v_all_activities_date_range AS
SELECT
    DATE_FORMAT(
        LEAST(
            COALESCE(MIN(t.created_at), CURDATE()),
            COALESCE(MIN(l.created_at), CURDATE()),
            COALESCE(MIN(e.date_expense), CURDATE())
        ),
        '%Y-%m-01'
    ) AS date_start,
    DATE_FORMAT(
        GREATEST(
            COALESCE(MAX(t.created_at), CURDATE()),
            COALESCE(MAX(l.created_at), CURDATE()),
            COALESCE(MAX(e.date_expense), CURDATE())
        ),
        '%Y-%m-01'
    ) AS date_end
FROM
    trigger_ticket t,
    trigger_lead l,
    expenses e;

-- Vue 2: Générer tous les mois dans la plage de dates
CREATE OR REPLACE VIEW v_all_activities_months AS
WITH RECURSIVE all_months AS (
    SELECT date_start AS month_date FROM v_all_activities_date_range
    UNION ALL
    SELECT DATE_ADD(month_date, INTERVAL 1 MONTH)
    FROM all_months, v_all_activities_date_range
    WHERE month_date < v_all_activities_date_range.date_end
)
SELECT
    month_date,
    YEAR(month_date) AS year,
    MONTH(month_date) AS month,
    DATE_FORMAT(month_date, '%Y-%m') AS period
FROM all_months;

-- Vue 3: Statistiques mensuelles pour les tickets
CREATE OR REPLACE VIEW v_ticket_monthly_stats AS
SELECT
    DATE_FORMAT(created_at, '%Y-%m-01') AS period,
    COUNT(*) AS ticket_count
FROM
    trigger_ticket
WHERE
    created_at IS NOT NULL
GROUP BY
    period;

-- Vue 4: Statistiques mensuelles pour les leads
CREATE OR REPLACE VIEW v_lead_monthly_stats AS
SELECT
    DATE_FORMAT(created_at, '%Y-%m-01') AS period,
    COUNT(*) AS lead_count
FROM
    trigger_lead
WHERE
    created_at IS NOT NULL
GROUP BY
    period;

-- Vue 5: Statistiques mensuelles pour les dépenses
CREATE OR REPLACE VIEW v_expense_monthly_stats AS
SELECT
    DATE_FORMAT(date_expense, '%Y-%m-01') AS period,
    SUM(amount) AS total_expenses
FROM
    expenses
WHERE
    date_expense IS NOT NULL
GROUP BY
    period;

-- Vue finale: Évolution mensuelle combinée
DROP VIEW IF EXISTS v_monthly_activities_evolution;
CREATE OR REPLACE VIEW v_monthly_activities_evolution AS
SELECT
    am.year,
    am.month,
    am.period,
    CONCAT(MONTHNAME(am.month_date), ' ', am.year) AS month_year_label,
    (
        SELECT COUNT(*)
        FROM trigger_ticket
        WHERE DATE_FORMAT(created_at, '%Y-%m') = am.period
    ) AS ticket_count,
    (
        SELECT COUNT(*)
        FROM trigger_lead
        WHERE DATE_FORMAT(created_at, '%Y-%m') = am.period
    ) AS lead_count,
    (
        SELECT COALESCE(SUM(amount), 0)
        FROM expenses
        WHERE DATE_FORMAT(date_expense, '%Y-%m') = am.period
    ) AS total_expenses
FROM
    v_all_activities_months am
ORDER BY
    am.year, am.month;

SELECT * FROM v_monthly_activities_evolution;


# TYPE TOTAL
# Ticket
CREATE OR REPLACE VIEW v_ticket_expenses_detail AS
SELECT
    t.ticket_id,
    t.subject,
    t.status,
    t.priority,
    c.name AS customer_name,
    e.id AS expense_id,
    COUNT(e.id) AS expense_count,
    COALESCE(SUM(e.amount), 0) AS total_expense_amount
FROM trigger_ticket t
    INNER JOIN
    expenses e ON t.ticket_id = e.ticket_id
    INNER JOIN
    customer c on t.customer_id = c.customer_id
GROUP BY
    t.ticket_id, t.subject, t.status
ORDER BY
    total_expense_amount DESC;

SELECT  * FROM v_ticket_expenses_detail;

# Lead
CREATE OR REPLACE VIEW v_lead_expenses_detail AS
SELECT
    l.lead_id,
    l.name AS lead_name,
    l.status,
    c.name AS customer_name,
    e.id AS expense_id,
    COUNT(e.id) AS expense_count,
    COALESCE(SUM(e.amount), 0) AS total_expense_amount
FROM trigger_lead l
         INNER JOIN
     expenses e ON l.lead_id = e.lead_id
         INNER JOIN
     customer c on l.customer_id = c.customer_id
GROUP BY
    l.lead_id, l.name, l.status, c.name, e.id
ORDER BY
    total_expense_amount DESC;




