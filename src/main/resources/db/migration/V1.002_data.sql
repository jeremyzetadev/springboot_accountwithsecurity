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

-- Dumping data for table `account`

--


LOCK TABLES `account` WRITE;

/*!40000 ALTER TABLE `account`

    DISABLE KEYS */;

INSERT INTO `account`

VALUES (1, '1234-5678', 300.00, 'Mr. Anderson', 1),

       (2, '9103-2910', 1001.00, 'Mr. Smith', 1);

/*!40000 ALTER TABLE `account`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `account_received_invoices`

--


LOCK TABLES `account_received_invoices` WRITE;

/*!40000 ALTER TABLE `account_received_invoices`

    DISABLE KEYS */;

INSERT INTO `account_received_invoices`

VALUES (1, 2),

       (2, 1);

/*!40000 ALTER TABLE `account_received_invoices`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `account_sent_invoices`

--


LOCK TABLES `account_sent_invoices` WRITE;

/*!40000 ALTER TABLE `account_sent_invoices`

    DISABLE KEYS */;

INSERT INTO `account_sent_invoices`

VALUES (1, 2),

       (2, 1);

/*!40000 ALTER TABLE `account_sent_invoices`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `account_statements`

--


LOCK TABLES `account_statements` WRITE;

/*!40000 ALTER TABLE `account_statements`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `account_statements`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `account_transactions`

--


LOCK TABLES `account_transactions` WRITE;

/*!40000 ALTER TABLE `account_transactions`

    DISABLE KEYS */;

INSERT INTO `account_transactions`

VALUES (1, 1),

       (2, 2);

/*!40000 ALTER TABLE `account_transactions`

    ENABLE KEYS */;

UNLOCK TABLES;



--

-- Dumping data for table `auth_role`

--


LOCK TABLES `auth_role` WRITE;

/*!40000 ALTER TABLE `auth_role`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `auth_role`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `auth_user`

--


LOCK TABLES `auth_user` WRITE;

/*!40000 ALTER TABLE `auth_user`

    DISABLE KEYS */;

INSERT INTO `auth_user`

VALUES (1, 'testing@example.com', 'testing', 'testing', '$2a$10$YRigsLy4O5EUjXaocIWgaeo3omKmRJASopytpTxN0zCSJMNFgZ8Nq',

        'VERIFIED'),

       (2, 'mattijs@mattijs.com', 'mattijs', 'mattijs', '$2a$10$7zSpXcyn8BIGX3YBwwg/x.Pv.thdRBzAqI6w8dW3t9I1U0zp4zgFy',

        'VERIFIED');

/*!40000 ALTER TABLE `auth_user`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `auth_user_role`

--


LOCK TABLES `auth_user_role` WRITE;

/*!40000 ALTER TABLE `auth_user_role`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `auth_user_role`

    ENABLE KEYS */;

UNLOCK TABLES;


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

-- Dumping data for table `invoice`

--


LOCK TABLES `invoice` WRITE;

/*!40000 ALTER TABLE `invoice`

    DISABLE KEYS */;

INSERT INTO `invoice`

VALUES (1, 50.00, 'Test', '2022-11-18 12:47:50', _binary '\0', 1, 2),

       (2, 75.00, 'test2', '2022-11-18 12:48:08', _binary '', 2, 1),

       (11, 1.00, '12', '2022-11-20 23:00:00', _binary '\0', 2, 1);

/*!40000 ALTER TABLE `invoice`

    ENABLE KEYS */;

UNLOCK TABLES;



--

-- Dumping data for table `log`

--


LOCK TABLES `log` WRITE;

/*!40000 ALTER TABLE `log`

    DISABLE KEYS */;

INSERT INTO `log`

VALUES (1, 1, 1, 'hi', '2022-11-18 14:07:48', 1),

       (2, 1, 1, 'bye', '2022-11-18 14:08:00', 2);

/*!40000 ALTER TABLE `log`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `principle_groups`

--


LOCK TABLES `principle_groups` WRITE;

/*!40000 ALTER TABLE `principle_groups`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `principle_groups`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `secure_tokens`

--


LOCK TABLES `secure_tokens` WRITE;

/*!40000 ALTER TABLE `secure_tokens`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `secure_tokens`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `statement`

--


LOCK TABLES `statement` WRITE;

/*!40000 ALTER TABLE `statement`

    DISABLE KEYS */;

INSERT INTO `statement`

VALUES (1, 'test', '2022-11-18 12:49:19', 1),

       (2, 'test2', '2022-11-18 12:49:27', 2);

/*!40000 ALTER TABLE `statement`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `table_transaction`

--


LOCK TABLES `table_transaction` WRITE;

/*!40000 ALTER TABLE `table_transaction`

    DISABLE KEYS */;

INSERT INTO `table_transaction`

VALUES (1, 20.00, 'hi', '2022-11-18 12:49:45', 0, 1, 1, 2),

       (2, 1.00, '1', '2022-11-18 12:09:01', 0, 1, NULL, NULL),

       (3, 1.00, '1', '2022-11-18 12:12:03', 0, 2, NULL, NULL),

       (4, 1.00, '1', '2022-11-18 12:12:16', 0, 2, NULL, NULL),

       (5, 1.00, '1', '2022-11-18 12:16:43', 0, 2, NULL, NULL),

       (6, 1.00, '1', '2022-11-18 12:20:35', 0, 2, NULL, 1),

       (7, 1.00, '1', '2022-11-18 12:21:35', 0, 2, NULL, 1),

       (8, 1.00, '1', '2022-11-18 12:21:55', 0, 2, NULL, 1),

       (9, 1.00, '1', '2022-11-18 12:25:01', 0, 2, NULL, 1),

       (10, 11.00, '1', '2022-11-20 14:47:57', 0, 2, NULL, 1);

/*!40000 ALTER TABLE `table_transaction`

    ENABLE KEYS */;

UNLOCK TABLES;



--

-- Dumping data for table `tb_role`

--


LOCK TABLES `tb_role` WRITE;

/*!40000 ALTER TABLE `tb_role`

    DISABLE KEYS */;

INSERT INTO `tb_role`

VALUES (1, 'ROLE_USER'),

       (2, 'ROLE_ADMIN');

/*!40000 ALTER TABLE `tb_role`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `tb_user`

--


LOCK TABLES `tb_user` WRITE;

/*!40000 ALTER TABLE `tb_user`

    DISABLE KEYS */;

INSERT INTO `tb_user`

VALUES (1, 'example@example.tld', 'example', 'example', '$2a$10$BBFv9OIHTZA0y0Ig66fjs.u.rw25ObMQLsc0YAJPj7uPSSQ61r.3y',

        true, true, true, 'dotnet_running.png', NULL),

       (2, 'test@test.tld', 'test', 'test', '$2a$10$zn1Jee7RCYmSqbzByMvGa.UD2Rjs.OWLMBB5f7BdkqwyiTKcB7mIa', true, true,

        true, 'default.png', NULL);


/*!40000 ALTER TABLE `tb_user`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `tb_users_roles`

--


LOCK TABLES `tb_users_roles` WRITE;

/*!40000 ALTER TABLE `tb_users_roles`

    DISABLE KEYS */;

INSERT INTO `tb_users_roles`

VALUES (1, 1),

       (1, 2);

/*!40000 ALTER TABLE `tb_users_roles`

    ENABLE KEYS */;

UNLOCK TABLES;


--

-- Dumping data for table `user`

--


LOCK TABLES `user` WRITE;

/*!40000 ALTER TABLE `user`

    DISABLE KEYS */;

/*!40000 ALTER TABLE `user`

    ENABLE KEYS */;

UNLOCK TABLES;


/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;


/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;


-- Dump completed on 2022-11-20 23:06:35
