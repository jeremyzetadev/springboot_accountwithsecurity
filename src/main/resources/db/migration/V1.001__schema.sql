-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)

--

-- Host: 127.0.0.1    Database: demoapp

-- ------------------------------------------------------

-- Server version	8.0.31-0ubuntu0.22.04.1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;

/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;

/*!50503 SET NAMES utf8mb4 */;

/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;

/*!40103 SET TIME_ZONE = '+00:00' */;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;

/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


--

-- Table structure for table `account`

--


DROP TABLE IF EXISTS `account`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `account`

(

    `id`             bigint NOT NULL AUTO_INCREMENT,

    `account_number` varchar(255)   DEFAULT NULL,

    `balance`        decimal(19, 2) DEFAULT NULL,

    `name`           varchar(255)   DEFAULT NULL,

    `user_id`        bigint         DEFAULT NULL,

    PRIMARY KEY (`id`),

    UNIQUE KEY `index_accountnumber_unique` (`account_number`),

    KEY `fk_account_user` (`user_id`),

    CONSTRAINT `constraint_fk_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `account_received_invoices`

--


DROP TABLE IF EXISTS `account_received_invoices`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `account_received_invoices`

(

    `account_id`           bigint NOT NULL,

    `received_invoices_id` bigint NOT NULL,

    UNIQUE KEY `unique_receivedinvoices_index` (`received_invoices_id`),

    KEY `key_account_id` (`account_id`),

    CONSTRAINT `fk_receivedinvoice_invoice` FOREIGN KEY (`received_invoices_id`) REFERENCES `invoice` (`id`),

    CONSTRAINT `fk_receivedinvoice_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `account_sent_invoices`

--


DROP TABLE IF EXISTS `account_sent_invoices`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `account_sent_invoices`

(

    `account_id`       bigint NOT NULL,

    `sent_invoices_id` bigint NOT NULL,

    UNIQUE KEY `unique_sentinvoices_index` (`sent_invoices_id`),

    KEY `sent_invoices_fk_account` (`account_id`),

    CONSTRAINT `fk_sent_invoices_invoice` FOREIGN KEY (`sent_invoices_id`) REFERENCES `invoice` (`id`),

    CONSTRAINT `fk_sent_invoices_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



--

-- Table structure for table `account_statements`

--


DROP TABLE IF EXISTS `account_statements`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `account_statements`

(

    `account_id`    bigint NOT NULL,

    `statements_id` bigint NOT NULL,

    UNIQUE KEY `unique_statements_id` (`statements_id`),

    KEY `unique_account_id` (`account_id`),

    CONSTRAINT `fk_accountstatements_statement` FOREIGN KEY (`statements_id`) REFERENCES `statement` (`id`),

    CONSTRAINT `fk_accountstatements_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `account_transactions`

--


DROP TABLE IF EXISTS `account_transactions`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `account_transactions`

(

    `account_id`      bigint NOT NULL,

    `transactions_id` bigint NOT NULL,

    UNIQUE KEY `account_transactions_to_transactions` (`transactions_id`),

    KEY `account_id_unique_accounttransactions` (`account_id`),

    CONSTRAINT `fk_accounttransactions_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),

    CONSTRAINT `fk_accounttransactions_transactions` FOREIGN KEY (`transactions_id`) REFERENCES `table_transaction` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `auth_role`

--


DROP TABLE IF EXISTS `auth_role`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `auth_role`

(

    `auth_role_id` int NOT NULL AUTO_INCREMENT,

    `role_desc`    varchar(255) DEFAULT NULL,

    `role_name`    varchar(255) DEFAULT NULL,

    PRIMARY KEY (`auth_role_id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `auth_user`

--


DROP TABLE IF EXISTS `auth_user`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `auth_user`

(

    `auth_user_id` int          NOT NULL AUTO_INCREMENT,

    `email`        varchar(255) NOT NULL,

    `last_name`    varchar(255) NOT NULL,

    `first_name`   varchar(255) NOT NULL,

    `password`     varchar(255) NOT NULL,

    `status`       varchar(255) DEFAULT NULL,

    PRIMARY KEY (`auth_user_id`)

) ENGINE = InnoDB

  AUTO_INCREMENT = 3

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



--

-- Table structure for table `auth_user_role`

--


DROP TABLE IF EXISTS `auth_user_role`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `auth_user_role`

(

    `auth_user_id` int NOT NULL,

    `auth_role_id` int NOT NULL,

    PRIMARY KEY (`auth_user_id`, `auth_role_id`),

    KEY `key_authroleid` (`auth_role_id`),

    CONSTRAINT `fk_to_authrole` FOREIGN KEY (`auth_role_id`) REFERENCES `auth_role` (`auth_role_id`),

    CONSTRAINT `fk_to_userid` FOREIGN KEY (`auth_user_id`) REFERENCES `auth_user` (`auth_user_id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `hibernate_sequence`

--


DROP TABLE IF EXISTS `hibernate_sequence`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `hibernate_sequence`

(

    `next_val` bigint DEFAULT NULL

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Dumping data for table `hibernate_sequence`

--


LOCK TABLES `hibernate_sequence` WRITE;

/*!40000 ALTER TABLE `hibernate_sequence`

    DISABLE KEYS */;

INSERT INTO `hibernate_sequence`

VALUES (12);

/*!40000 ALTER TABLE `hibernate_sequence`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Table structure for table `invoice`

--


DROP TABLE IF EXISTS `invoice`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `invoice`

(

    `id`                bigint NOT NULL AUTO_INCREMENT,

    `amount`            integer      DEFAULT NULL,

    `description`       varchar(255) DEFAULT NULL,

    `due_date`          datetime     DEFAULT NULL,

    `payment_status`    bit(1) NOT NULL,

    `buyer_account_id`  bigint       DEFAULT NULL,

    `seller_account_id` bigint       DEFAULT NULL,

    PRIMARY KEY (`id`),

    KEY `invoice_key_buyeraccountid` (`buyer_account_id`),

    KEY `invoice_key_selleraccountid` (`seller_account_id`),

    CONSTRAINT `constraint_invoice_selleraccount` FOREIGN KEY (`seller_account_id`) REFERENCES `account` (`id`),

    CONSTRAINT `constraint_invoice_buyeraccount` FOREIGN KEY (`buyer_account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `log`

--


DROP TABLE IF EXISTS `log`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `log`

(

    `id`        bigint NOT NULL AUTO_INCREMENT,

    `level`     int          DEFAULT NULL,

    `log_type`  int          DEFAULT NULL,

    `message`   varchar(255) DEFAULT NULL,

    `timestamp` datetime     DEFAULT NULL,

    `user_id`   bigint       DEFAULT NULL,

    PRIMARY KEY (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



--

-- Table structure for table `principle_groups`

--


DROP TABLE IF EXISTS `principle_groups`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `principle_groups`

(

    `id`   bigint       NOT NULL AUTO_INCREMENT,

    `code` varchar(255) NOT NULL,

    `name` varchar(255) DEFAULT NULL,

    PRIMARY KEY (`id`),

    UNIQUE KEY `code_unique_principle_groups` (`code`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `secure_tokens`

--


DROP TABLE IF EXISTS `secure_tokens`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `secure_tokens`

(

    `id`          bigint   NOT NULL AUTO_INCREMENT,

    `expire_at`   datetime NOT NULL,

    `time_stamp`  datetime     DEFAULT NULL,

    `token`       varchar(255) DEFAULT NULL,

    `customer_id` bigint       DEFAULT NULL,

    PRIMARY KEY (`id`),

    UNIQUE KEY `token_unique_securetokens` (`token`),

    KEY `fk_customer_id_secure_tokens` (`customer_id`),

    CONSTRAINT `constraint_tokens_customerid` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



--

-- Table structure for table `statement`

--


DROP TABLE IF EXISTS `statement`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `statement`

(

    `id`         bigint NOT NULL AUTO_INCREMENT,

    `data`       varchar(255) DEFAULT NULL,

    `end_date`   datetime     DEFAULT NULL,

    `account_id` bigint       DEFAULT NULL,

    PRIMARY KEY (`id`),

    KEY `statement_to_account` (`account_id`),

    CONSTRAINT `fk_statement_to_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `table_transaction`

--


DROP TABLE IF EXISTS `table_transaction`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `table_transaction`

(

    `id`                 bigint NOT NULL AUTO_INCREMENT,

    `amount`             decimal(19, 2) DEFAULT NULL,

    `description`        varchar(255)   DEFAULT NULL,

    `timestamp`          datetime       DEFAULT NULL,

    `transaction_type`   int            DEFAULT NULL,

    `foreign_account_id` bigint         DEFAULT NULL,

    `invoice_id`         bigint         DEFAULT NULL,

    `owner_account_id`   bigint         DEFAULT NULL,

    PRIMARY KEY (`id`),

    KEY `fk_foreignaccount_transaction` (`foreign_account_id`),

    KEY `fk_foreignaccount_invoice` (`invoice_id`),

    KEY `fk_foreignaccount_owneraccount` (`owner_account_id`),

    CONSTRAINT `constraint_transaction_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),

    CONSTRAINT `constraint_transaction_foreignaccount` FOREIGN KEY (`foreign_account_id`) REFERENCES `account` (`id`),

    CONSTRAINT `constraint_transaction_owneraccount` FOREIGN KEY (`owner_account_id`) REFERENCES `account` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



--

-- Table structure for table `tb_role`

--


DROP TABLE IF EXISTS `tb_role`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `tb_role`

(

    `id`   bigint NOT NULL AUTO_INCREMENT,

    `name` varchar(255) DEFAULT NULL,

    PRIMARY KEY (`id`)

) ENGINE = InnoDB

  AUTO_INCREMENT = 3

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `tb_user`

--


DROP TABLE IF EXISTS `tb_user`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `tb_user`

(

    `id`              bigint NOT NULL AUTO_INCREMENT,

    `email`           varchar(255) DEFAULT NULL,

    `first_name`      varchar(255) DEFAULT NULL,

    `last_name`       varchar(255) DEFAULT NULL,

    `password`        varchar(255) DEFAULT NULL,

    `email_confirmed` bit(1) NOT NULL,

    `enabled`         bit(1) NOT NULL,

    `is_active`       bit(1) NOT NULL,

    `profile_picture` varchar(255) DEFAULT NULL,

    `salt`            varchar(255) DEFAULT NULL,

    PRIMARY KEY (`id`),

    UNIQUE KEY `user_email_unique` (`email`)

) ENGINE = InnoDB

  AUTO_INCREMENT = 2

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `tb_users_roles`

--


DROP TABLE IF EXISTS `tb_users_roles`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `tb_users_roles`

(

    `user_id`  bigint NOT NULL,

    `roler_id` bigint NOT NULL,

    KEY `role_key_userroles` (`roler_id`),

    KEY `user_key_userroles` (`user_id`),

    CONSTRAINT `constraint_roleid` FOREIGN KEY (`roler_id`) REFERENCES `tb_role` (`id`),

    CONSTRAINT `constraint_userroles_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


--

-- Table structure for table `user`

--


DROP TABLE IF EXISTS `user`;

/*!40101 SET @saved_cs_client = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `user`

(

    `id`                    bigint NOT NULL AUTO_INCREMENT,

    `account_verified`      bit(1) NOT NULL,

    `email`                 varchar(255) DEFAULT NULL,

    `failed_login_attempts` int    NOT NULL,

    `first_name`            varchar(255) DEFAULT NULL,

    `last_name`             varchar(255) DEFAULT NULL,

    `login_disabled`        bit(1) NOT NULL,

    `mfa_enabled`           bit(1) NOT NULL,

    `password`              varchar(255) DEFAULT NULL,

    `secret`                varchar(255) DEFAULT NULL,

    `token`                 varchar(255) DEFAULT NULL,

    PRIMARY KEY (`id`),

    UNIQUE KEY `unique_email_user` (`email`)

) ENGINE = InnoDB

  DEFAULT CHARSET = utf8mb4

  COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;


/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;


-- Dump completed on 2022-11-20 23:06:35
