-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: campus_connect
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `alumni_profiles`
--

DROP TABLE IF EXISTS `alumni_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumni_profiles` (
  `user_id` int NOT NULL,
  `graduation_year` int DEFAULT NULL,
  `company` varchar(150) DEFAULT NULL,
  `designation` varchar(150) DEFAULT NULL,
  `industry` varchar(100) DEFAULT NULL,
  `expertise` text,
  `verification_status` enum('PENDING','VERIFIED','REJECTED') DEFAULT 'PENDING',
  `college_id_number` varchar(50) DEFAULT NULL,
  `evidence_reference` varchar(500) DEFAULT NULL,
  `registration_number` varchar(50) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `degree` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `alumni_profiles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumni_profiles`
--

LOCK TABLES `alumni_profiles` WRITE;
/*!40000 ALTER TABLE `alumni_profiles` DISABLE KEYS */;
INSERT INTO `alumni_profiles` VALUES (2,2023,'Infosys','Software Engineer','Information Technology','Java, SQL, Backend Development','VERIFIED',NULL,NULL,NULL,NULL,NULL),(3,2022,'TCS','Data Analyst','Data Analytics','Python, SQL, Power BI','VERIFIED',NULL,NULL,NULL,NULL,NULL),(6,2024,'UST','AI Engineer','Artificial Intelligence','Python, Machine Learning, Deep Learning','VERIFIED',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `alumni_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumni_verification_documents`
--

DROP TABLE IF EXISTS `alumni_verification_documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumni_verification_documents` (
  `document_id` int NOT NULL AUTO_INCREMENT,
  `alumni_id` int NOT NULL,
  `document_type` varchar(100) NOT NULL,
  `document_reference` varchar(500) NOT NULL,
  `review_status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `admin_comments` varchar(500) DEFAULT NULL,
  `submitted_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `reviewed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `alumni_id` (`alumni_id`),
  CONSTRAINT `alumni_verification_documents_ibfk_1` FOREIGN KEY (`alumni_id`) REFERENCES `alumni_profiles` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumni_verification_documents`
--

LOCK TABLES `alumni_verification_documents` WRITE;
/*!40000 ALTER TABLE `alumni_verification_documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `alumni_verification_documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `career_roadmaps`
--

DROP TABLE IF EXISTS `career_roadmaps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `career_roadmaps` (
  `roadmap_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `target_role` varchar(150) NOT NULL,
  `steps` text,
  PRIMARY KEY (`roadmap_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `career_roadmaps`
--

LOCK TABLES `career_roadmaps` WRITE;
/*!40000 ALTER TABLE `career_roadmaps` DISABLE KEYS */;
INSERT INTO `career_roadmaps` VALUES (1,'Software Developer Roadmap','Software Developer','Learn Java; Practice DSA; Learn SQL; Build projects; Prepare for interviews.'),(2,'AI Engineer Roadmap','AI Engineer','Learn Python; Study mathematics fundamentals; Learn ML; Build AI projects; Practice interviews.'),(3,'Data Analyst Roadmap','Data Analyst','Learn SQL; Practice Python; Learn Excel; Study Power BI; Build analytics projects.');
/*!40000 ALTER TABLE `career_roadmaps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `alumni_id` int NOT NULL,
  `title` varchar(150) NOT NULL,
  `description` text,
  `event_time` datetime NOT NULL,
  `meeting_link` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `alumni_id` (`alumni_id`),
  CONSTRAINT `events_ibfk_1` FOREIGN KEY (`alumni_id`) REFERENCES `alumni_profiles` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,2,'Getting Started with Software Development','An online session about programming skills and software internships.','2026-11-20 17:00:00','https://example.com/event1'),(2,3,'Introduction to Data Analytics','Learn about SQL, Python and data analytics career opportunities.','2026-11-25 16:00:00','https://example.com/event2');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experience_posts`
--

DROP TABLE IF EXISTS `experience_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experience_posts` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `alumni_id` int NOT NULL,
  `company` varchar(150) DEFAULT NULL,
  `role` varchar(150) DEFAULT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`),
  KEY `alumni_id` (`alumni_id`),
  CONSTRAINT `experience_posts_ibfk_1` FOREIGN KEY (`alumni_id`) REFERENCES `alumni_profiles` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experience_posts`
--

LOCK TABLES `experience_posts` WRITE;
/*!40000 ALTER TABLE `experience_posts` DISABLE KEYS */;
INSERT INTO `experience_posts` VALUES (1,2,'Infosys','Software Engineer','Build strong programming fundamentals, practise DSA, and create projects before applying for internships.','2026-10-11 08:37:19'),(2,3,'TCS','Data Analyst','Start with SQL and Excel, then learn Python and data visualization tools. Practise using real datasets.','2026-10-11 08:37:19'),(3,6,'UST','AI Engineer','Develop Python skills, understand machine learning fundamentals, and build practical projects.','2026-10-11 08:37:19');
/*!40000 ALTER TABLE `experience_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mentorship_requests`
--

DROP TABLE IF EXISTS `mentorship_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mentorship_requests` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `alumni_id` int NOT NULL,
  `message` text,
  `status` enum('PENDING','ACCEPTED','REJECTED','CANCELLED') DEFAULT 'PENDING',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `student_id` (`student_id`,`alumni_id`),
  KEY `alumni_id` (`alumni_id`),
  CONSTRAINT `mentorship_requests_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_profiles` (`user_id`),
  CONSTRAINT `mentorship_requests_ibfk_2` FOREIGN KEY (`alumni_id`) REFERENCES `alumni_profiles` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mentorship_requests`
--

LOCK TABLES `mentorship_requests` WRITE;
/*!40000 ALTER TABLE `mentorship_requests` DISABLE KEYS */;
INSERT INTO `mentorship_requests` VALUES (1,1,2,'I would like guidance on preparing for software development internships.','PENDING','2026-10-11 08:37:19'),(2,5,3,'Could you guide me on starting a career in data analytics?','ACCEPTED','2026-10-11 08:37:19');
/*!40000 ALTER TABLE `mentorship_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mentorship_sessions`
--

DROP TABLE IF EXISTS `mentorship_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mentorship_sessions` (
  `session_id` int NOT NULL AUTO_INCREMENT,
  `request_id` int NOT NULL,
  `session_time` datetime NOT NULL,
  `meeting_mode` enum('ONLINE','OFFLINE') DEFAULT 'ONLINE',
  `meeting_link` varchar(500) DEFAULT NULL,
  `status` enum('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
  PRIMARY KEY (`session_id`),
  KEY `request_id` (`request_id`),
  CONSTRAINT `mentorship_sessions_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `mentorship_requests` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mentorship_sessions`
--

LOCK TABLES `mentorship_sessions` WRITE;
/*!40000 ALTER TABLE `mentorship_sessions` DISABLE KEYS */;
INSERT INTO `mentorship_sessions` VALUES (1,2,'2026-11-15 15:00:00','ONLINE','https://example.com/meeting1','SCHEDULED');
/*!40000 ALTER TABLE `mentorship_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` text NOT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,1,'Your mentorship request has been submitted.',0,'2026-10-11 08:37:19'),(2,5,'Your mentorship request has been accepted.',0,'2026-10-11 08:37:19'),(3,2,'You have received a new mentorship request.',0,'2026-10-11 08:37:19'),(4,3,'A mentorship session is scheduled.',0,'2026-10-11 08:37:19');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opportunities`
--

DROP TABLE IF EXISTS `opportunities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opportunities` (
  `opportunity_id` int NOT NULL AUTO_INCREMENT,
  `alumni_id` int NOT NULL,
  `title` varchar(150) NOT NULL,
  `company` varchar(150) NOT NULL,
  `type` enum('INTERNSHIP','JOB','REFERRAL') NOT NULL,
  `description` text,
  `eligibility` text,
  `deadline` date DEFAULT NULL,
  `application_url` varchar(500) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`opportunity_id`),
  KEY `alumni_id` (`alumni_id`),
  CONSTRAINT `opportunities_ibfk_1` FOREIGN KEY (`alumni_id`) REFERENCES `alumni_profiles` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opportunities`
--

LOCK TABLES `opportunities` WRITE;
/*!40000 ALTER TABLE `opportunities` DISABLE KEYS */;
INSERT INTO `opportunities` VALUES (1,2,'Java Developer Intern','Infosys','INTERNSHIP','Learn Java development and work on backend applications.','Students with basic Java and SQL knowledge.','2027-01-15','https://example.com/java-intern','2026-10-11 08:37:19'),(2,3,'Data Analyst Intern','TCS','INTERNSHIP','Assist with data analysis and reporting.','Students with Python and SQL knowledge.','2027-02-10','https://example.com/data-intern','2026-10-11 08:37:19'),(3,6,'Junior AI Engineer','UST','JOB','Work on machine learning models and AI applications.','Knowledge of Python and machine learning.','2027-03-01','https://example.com/ai-job','2026-10-11 08:37:19');
/*!40000 ALTER TABLE `opportunities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved_opportunities`
--

DROP TABLE IF EXISTS `saved_opportunities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_opportunities` (
  `student_id` int NOT NULL,
  `opportunity_id` int NOT NULL,
  `saved_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`student_id`,`opportunity_id`),
  KEY `opportunity_id` (`opportunity_id`),
  CONSTRAINT `saved_opportunities_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_profiles` (`user_id`),
  CONSTRAINT `saved_opportunities_ibfk_2` FOREIGN KEY (`opportunity_id`) REFERENCES `opportunities` (`opportunity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_opportunities`
--

LOCK TABLES `saved_opportunities` WRITE;
/*!40000 ALTER TABLE `saved_opportunities` DISABLE KEYS */;
INSERT INTO `saved_opportunities` VALUES (1,1,'2026-10-11 08:37:19'),(1,2,'2026-10-11 08:37:19'),(5,3,'2026-10-11 08:37:19');
/*!40000 ALTER TABLE `saved_opportunities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_profiles`
--

DROP TABLE IF EXISTS `student_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_profiles` (
  `user_id` int NOT NULL,
  `department` varchar(100) DEFAULT NULL,
  `semester` int DEFAULT NULL,
  `skills` text,
  `career_goal` varchar(150) DEFAULT NULL,
  `college_id_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `student_profiles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_profiles`
--

LOCK TABLES `student_profiles` WRITE;
/*!40000 ALTER TABLE `student_profiles` DISABLE KEYS */;
INSERT INTO `student_profiles` VALUES (1,'CSE AI',3,'Java, Python, SQL','Software Developer',NULL),(5,'Computer Science',5,'Python, Machine Learning, HTML, CSS','AI Engineer',NULL);
/*!40000 ALTER TABLE `student_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('STUDENT','ALUMNI','ADMIN') NOT NULL,
  `status` enum('ACTIVE','PENDING','BLOCKED') NOT NULL DEFAULT 'ACTIVE',
  `verification_notes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Ananya Nair','ananya@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000001$bf1ead2581f8caa488de1601932e80c49912e4ef73ccb79a04a8d2d83c67caa1','STUDENT','ACTIVE',NULL),(2,'Arjun Menon','arjun@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000002$088fa3853b3465d4865dabeb3330cca52cc0a19a7d99a60578781bbda7e0e7e1','ALUMNI','ACTIVE',NULL),(3,'Meera Thomas','meera@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000003$966a9aa92ea770c96876b7530325dfac148465821ee8289849f924c09c609734','ALUMNI','ACTIVE',NULL),(4,'Admin User','admin@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000004$8f6bfc97f1be540633a367fb4da23e00bf2c0d10b366ed5c4710814915688d1d','ADMIN','ACTIVE',NULL),(5,'Rahul Kumar','rahul@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000005$58a816ca6309c54fbab4b9a6458643b5fc443b0df34bcdd5cd14208078611659','STUDENT','ACTIVE',NULL),(6,'Neha Joseph','neha@example.com','PBKDF2_SHA256$210000$00000000000000000000000000000006$cc55029a0ec17da604983060542b2220020583faee3f6f7c63b2dd31a35f28df','ALUMNI','ACTIVE',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-11 17:05:18
